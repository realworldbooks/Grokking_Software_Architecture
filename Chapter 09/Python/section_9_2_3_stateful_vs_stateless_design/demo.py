import shutil
from moto import mock_aws

from section_9_2_3_stateful_vs_stateless_design.services.user_service import UserService
from section_9_2_3_stateful_vs_stateless_design.infrastructure.local_storage import LocalStorage
from section_9_2_3_stateful_vs_stateless_design.infrastructure.s3_storage import S3Storage

class Demo:
    @staticmethod
    def run_stateful_scenario() -> None:
        print("\n=== Scenario 1: Stateful Design (The Fragile Monolith) ===")
        print("THE SETUP: Two web servers running behind a Load Balancer.")
        print("THE ARCHITECTURE: Using LocalStorage (Stateful).\n")

        try:
            # 1. Setup: We simulate two separate servers, each with their own isolated hard drive.
            server_a_service = UserService(LocalStorage("server_A"))
            server_b_service = UserService(LocalStorage("server_B"))

            print("--- Request 1: User uploads a profile picture ---")
            print("  [Load Balancer] Routing traffic to Server A...")
            
            # The file gets saved physically onto Server A's disk.
            server_a_service.upload_avatar("user_123", "face_data_001")
            print("  [Result] Upload Successful (Saved to Server A's local drive).\n")

            print("--- Request 2: User refreshes to view their profile ---")
            print("  [Load Balancer] Server A is busy. Routing traffic to Server B...")
            
            # Server B attempts to read the file. It checks its own drive, but the file isn't there!
            server_b_service.view_avatar("user_123")
                
        except FileNotFoundError:
            # This is the exact moment horizontal scaling breaks.
            print("\n  [Result] FATAL CRASH: FileNotFoundError!")
            print("  [Lesson] Stateful design breaks horizontal scaling. Server B has no idea")
            print("           what Server A did. The state is trapped on a single machine.\n")
        finally:
            # Clean up our simulated local directories
            shutil.rmtree("server_A_drive", ignore_errors=True)
            shutil.rmtree("server_B_drive", ignore_errors=True)

    @staticmethod
    @mock_aws  # Intercepts boto3 calls and safely simulates AWS S3 on the local machine
    def run_stateless_scenario() -> None:
        print("\n=== Scenario 2: Stateless Design (Cloud Native) ===")
        print("THE SETUP: Two web servers running behind a Load Balancer.")
        print("THE ARCHITECTURE: Using S3Storage (Stateless).\n")

        # 1. Setup: Both server instances now point to the exact same external infrastructure.
        # We have successfully separated the 'Compute' (servers) from the 'State' (storage).
        shared_s3 = S3Storage("grokking-app-bucket")
        server_a_service = UserService(shared_s3)
        server_b_service = UserService(shared_s3)

        print("--- Request 1: User uploads a profile picture ---")
        print("  [Load Balancer] Routing traffic to Server A...")
        
        # Server A processes the logic, but immediately hands the data off to the external cloud.
        server_a_service.upload_avatar("user_123", "face_data_001")
        print("  [Result] Upload Successful (Pushed to S3).\n")

        print("--- Request 2: User refreshes to view their profile ---")
        print("  [Load Balancer] Routing traffic to Server B...")
        
        # Server B fetches the data from the central cloud adapter. It doesn't matter that 
        # Server B wasn't the one who originally handled the upload!
        data = server_b_service.view_avatar("user_123")
        
        print(f"  [Result] SUCCESS! Server B downloaded the file. Data: '{data}'")
        print("  [Lesson] Stateless servers are interchangeable. Any server can handle")
        print("           any request because the 'state' lives safely in the cloud.\n")
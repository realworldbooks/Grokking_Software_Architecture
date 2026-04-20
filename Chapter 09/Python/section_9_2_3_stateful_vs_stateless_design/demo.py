import shutil
from moto import mock_aws

from .services.user_service import UserService
from .infrastructure.local_storage import LocalStorage
from .infrastructure.s3_storage import S3Storage

class Demo:
    """
    THE ARCHITECTURAL COMPARATOR:
    * @description
    This class orchestrates two distinct design philosophies to demonstrate 
    the "Horizontal Scaling Fallacy." It contrasts local file-system 
    dependency with external cloud persistence.
    """

    @staticmethod
    def run() -> None:
        """
        Executes the full laboratory suite:
        1. Scenario 1: The Stateful Failure (Local Storage)
        2. Scenario 2: The Stateless Success (External S3)
        """
        # --- SCENARIO 1: THE FRAGILE MONOLITH ---
        Demo._run_stateful_scenario()

        print("-" * 70)

        # --- SCENARIO 2: THE CLOUD NATIVE RECOVERY ---
        # We wrap this in the mock to intercept boto3 calls without needing real AWS keys.
        with mock_aws():
            Demo._run_stateless_scenario()

    @staticmethod
    def _run_stateful_scenario() -> None:
        print("\n=== Scenario 1: Stateful Design (The Fragile Monolith) ===")
        print("THE SETUP: Two web servers running behind a Load Balancer.")
        print("THE ARCHITECTURE: Using LocalStorage (Stateful).\n")

        try:
            # 1. Setup: We simulate two separate servers with isolated hard drives.
            server_a_service = UserService(LocalStorage("server_A"))
            server_b_service = UserService(LocalStorage("server_B"))

            print("--- Request 1: User uploads a profile picture ---")
            print("  [Load Balancer] Routing traffic to Server A...")
            
            # The file is trapped physically on Server A's disk.
            server_a_service.upload_avatar("user_123", "face_data_001")
            print("  [Result] Upload Successful (Saved to Server A's local drive).\n")

            print("--- Request 2: User refreshes to view their profile ---")
            print("  [Load Balancer] Server A is busy. Routing traffic to Server B...")
            
            # Server B attempts to read the file, but it doesn't exist on its drive!
            server_b_service.view_avatar("user_123")
                
        except FileNotFoundError:
            # This is the moment horizontal scaling breaks.
            print("\n  [Result] FATAL ERROR: FileNotFoundError!")
            print("  [Lesson] Stateful design breaks horizontal scaling. Server B has no idea")
            print("           what Server A did. The state is trapped on a single machine.")
        finally:
            shutil.rmtree("server_A_drive", ignore_errors=True)
            shutil.rmtree("server_B_drive", ignore_errors=True)

    @staticmethod
    def _run_stateless_scenario() -> None:
        print("\n=== Scenario 2: Stateless Design (Cloud Native) ===")
        print("THE SETUP: Two web servers running behind a Load Balancer.")
        print("THE ARCHITECTURE: Using S3Storage (Stateless).\n")

        # 1. Setup: Both servers point to the same external infrastructure.
        # We have separated 'Compute' (servers) from 'State' (storage).
        shared_s3 = S3Storage("grokking-app-bucket")
        server_a_service = UserService(shared_s3)
        server_b_service = UserService(shared_s3)

        print("--- Request 1: User uploads a profile picture ---")
        print("  [Load Balancer] Routing traffic to Server A...")
        
        # Server A hands the data off to the external cloud immediately.
        server_a_service.upload_avatar("user_123", "face_data_001")
        print("  [Result] Upload Successful (Pushed to S3).\n")

        print("--- Request 2: User refreshes to view their profile ---")
        print("  [Load Balancer] Routing traffic to Server B...")
        
        # Server B fetches the data from the central cloud. It is interchangeable!
        data = server_b_service.view_avatar("user_123")
        
        print(f"  [Result] SUCCESS! Server B downloaded the file. Data: '{data}'")
        print("  [Lesson] Stateless servers are interchangeable. Any server can handle")
        print("           any request because the 'state' lives safely in the cloud.\n")
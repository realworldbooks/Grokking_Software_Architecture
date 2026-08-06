from .infrastructure.mock_database import MockDatabaseConnection

class Demo:
    """
    THE HARDCODED DISASTER - Listing 11.1
    
    DESIGN NOTE:
    This example demonstrates the WRONG way to handle secrets. We are showing
    this anti-pattern to highlight why it's a catastrophic security risk.
    
    ARCHITECTURAL CRITIQUE:
    1. SOURCE CONTROL LEAKAGE: These credentials are now in git history forever.
       Anyone with repo access has the keys to the kingdom.
    
    2. THE "GOD ACCOUNT" PROBLEM: Using the admin user violates the Principle of
       Least Privilege. If this service is breached, the attacker can drop every
       table in the database.
    
    3. STATIC VULNERABILITY: If you need to change the password, you have to
       recompile, re-test, and re-deploy the entire application.
    """

    @staticmethod
    def run():
        print("\n=== Section 11.4.1: The Hardcoded Disaster (Python) ===")
        print("THE SETUP: An Order Service that needs to connect to the Customer Database.")
        print("THE PROBLEM: Credentials are hardcoded directly in the source code.\n")

        # ---------------------------------------------------------
        # THE DISASTER UNFOLDS
        # ---------------------------------------------------------
        print("--- SCENARIO: Developer commits code with hardcoded credentials ---")
        print("  [Developer] Writing connection code...")
        print("  [Developer] 'I'll just put the password here for now...'")
        print("  [Developer] Commits to git and pushes to GitHub...\n")

        # This is the ANTI-PATTERN we're demonstrating
        # In a real scenario, this would be:
        # conn = psycopg2.connect(
        #     host="prod-db.internal.castle.com",
        #     database="orders_db",
        #     user="admin",
        #     password="Password123!"
        # )
        
        print("  [Code Review] Missed! The credentials are buried in the implementation.")
        print("  [CI/CD] Pipeline builds and deploys successfully.")
        print("  [Production] Service is now running with hardcoded admin credentials.\n")

        # Attempt to use the hardcoded credentials
        print("--- Attempting database connection with hardcoded credentials ---")
        
        try:
            # THE DISASTER: Hardcoded credentials in source code
            db_connection = MockDatabaseConnection(
                host="prod-db.internal.castle.com",
                database="orders_db",
                user="admin",  # God mode account
                password="Password123!"  # Hardcoded string - DISASTER!
            )
            
            connection_successful = db_connection.connect()
            
            if connection_successful:
                print("  [Result] Connection SUCCESSFUL (but at what cost?)")
                print("  [Data] Querying orders...")
                orders = db_connection.execute_query("SELECT * FROM orders")
                print(f"  [Data] Retrieved {len(orders)} orders")
                db_connection.close()
        
        except Exception as e:
            print(f"  [Result] Connection failed: {e}")

        # ---------------------------------------------------------
        # THE ARCHITECTURAL VERDICT
        # ---------------------------------------------------------
        print("\n" + "=" * 70)
        print("ARCHITECTURAL VERDICT: CATASTROPHIC SECURITY FAILURE")
        print("-" * 70)
        print("VULNERABILITY #1: SOURCE CONTROL LEAKAGE")
        print("  - Credentials are now in git history forever")
        print("  - Even if deleted later, they remain in commit history")
        print("  - Anyone with read access to the repo has database access")
        print()
        print("VULNERABILITY #2: THE GOD ACCOUNT PROBLEM")
        print("  - Using 'admin' user violates Principle of Least Privilege")
        print("  - If service is breached, attacker can drop all tables")
        print("  - No audit trail of who did what with the admin account")
        print()
        print("VULNERABILITY #3: STATIC PASSWORD ROTATION")
        print("  - Changing password requires full code deployment")
        print("  - All environments (dev/staging/prod) use same password")
        print("  - No way to rotate without downtime")
        print()
        print("VULNERABILITY #4: NO AUDIT OR MONITORING")
        print("  - Cannot track who accessed the database")
        print("  - Cannot revoke access without code deployment")
        print("  - Credentials shared across all service instances")
        print()
        print("REALITY CHECK: This code will be committed, pushed, and deployed.")
        print("Those credentials are now compromised. The question is not IF they")
        print("will be leaked, but WHEN. This is an architectural disaster.")
        print("=" * 70 + "\n")
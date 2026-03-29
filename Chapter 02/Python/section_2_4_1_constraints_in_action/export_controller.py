import sys
from .database import Database

class ExportController:
    """
    Simulates a 'Controller' in a web framework like FastAPI or Flask.
    Orchestrates business logic and formats web responses.
    """

    def __init__(self):
        self._db = Database()

    async def export_user_data_async(self, user_id: str):
        """
        Demonstrates how architectural constraints dictate code flow.
        """
        try:
            # 1. ORCHESTRATION: Call service to get data
            user_data = await self._db.fetch_user_data_async(user_id)

            # 2. BUSINESS CONSTRAINT: Handle missing users
            # Technical implementation: Return an HTTP 404 status equivalent.
            if user_data is None:
                print("  [HTTP 404] User not found.")
                return

            # 3. TECHNICAL CONSTRAINT: Format output as CSV
            headers = "id,name,email\n"
            csv_row = f"{user_data.id},{user_data.name},{user_data.email}\n"
            csv_data = headers + csv_row

            # 4. TECHNICAL CONSTRAINT: Adhere to HTTP protocol simulation
            print("  [HTTP 200] OK")
            print("  [Headers] Content-Type: text/csv")
            print(f"  [Headers] Content-Disposition: attachment; filename=\"user_data_{user_id}.csv\"")
            print("\n--- File Body ---")
            sys.stdout.write(csv_data)
            print("-----------------")

        except Exception as ex:
            # 5. BUSINESS/TECHNICAL CONSTRAINT: Graceful error handling
            # Return a generic server error (HTTP 500) equivalent.
            print(f"  [HTTP 500] Export failed: {str(ex)}")
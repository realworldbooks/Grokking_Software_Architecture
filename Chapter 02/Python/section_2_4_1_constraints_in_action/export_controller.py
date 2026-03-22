from database import Database

class ExportController:
    """
    Simulates a "Controller" in a web framework like Flask or FastAPI.
    Its primary responsibility is to handle incoming web requests, orchestrate
    the necessary business logic, and then format and return a proper web response.
    """
    def __init__(self):
        # A real controller would use Dependency Injection.
        self.db = Database()

    def export_user_data(self, user_id):
        """
        Simulates handling a `GET /export-user-data` request.
        
        Args:
            user_id (str): The ID of the user to export.
        """
        # Note: In a real Python web framework, this would be an async function
        # to handle I/O without blocking the server.
        try:
            # 1. ORCHESTRATION: The controller calls other services.
            user_data = self.db.fetch_user_data(user_id)

            # 2. BUSINESS CONSTRAINT: Handle the case where the user does not exist.
            if not user_data:
                print("  [HTTP 404] User not found.")
                return

            # 3. TECHNICAL CONSTRAINT: Format data as CSV.
            headers = "id,name,email\\n"
            csv_row = f"{user_data['id']},{user_data['name']},{user_data['email']}\\n"
            csv_data = headers + csv_row

            # 4. TECHNICAL CONSTRAINT: Adhere to the HTTP protocol.
            print("  [HTTP 200] OK")
            print("  [Headers] Content-Type: text/csv")
            print(f'  [Headers] Content-Disposition: attachment; filename="user_data_{user_id}.csv"')
            print("\\n--- File Body ---")
            print(csv_data, end="")
            print("-----------------")

        except Exception as e:
            # 5. BUSINESS/TECHNICAL CONSTRAINT: Handle unexpected errors gracefully.
            print(f"  [HTTP 500] Export failed: {e}")
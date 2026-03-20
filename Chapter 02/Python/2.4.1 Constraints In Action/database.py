class Database:
    def fetch_user_data(self, user_id):
        # Simulating an async/synchronous database call
        if user_id == "User123":
            return {
                "id": "User123",
                "name": "Alice",
                "email": "alice@example.com"
            }
        return None
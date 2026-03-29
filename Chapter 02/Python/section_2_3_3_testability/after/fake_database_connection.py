from .database_connection import IDatabaseConnection

class FakeDatabaseConnection(IDatabaseConnection):
    """
    A 'Test Double' used exclusively for testing.
    It returns predictable, hardcoded data to verify the ReportGenerator 
    in complete isolation.
    """

    def get_data(self, query: str) -> list[str]:
        # Return exactly 3 rows to satisfy our test case
        return ["fake_row1", "fake_row2", "fake_row3"]
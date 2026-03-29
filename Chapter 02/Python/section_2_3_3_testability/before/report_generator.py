from .real_database_connection import RealDatabaseConnection

class ReportGenerator:
    """
    Demonstrates a class that is difficult to test due to tight coupling.
    """

    def __init__(self):
        # PROBLEM: Hardcoded Dependency (Tight Coupling)
        # The __init__ method creates its own instance of RealDatabaseConnection.
        # This is "tight coupling." The ReportGenerator class is permanently 
        # and directly tied to the RealDatabaseConnection class.
        #
        # WHY IS THIS BAD FOR TESTABILITY?
        # 1. No Isolation: You cannot test ReportGenerator without also 
        #    testing RealDatabaseConnection.
        # 2. Real External Services: Unit tests would need to connect to an 
        #    actual database, making them slow and unreliable.
        # 3. No "Fakes" or "Mocks": We can't substitute a "fake" database 
        #    for testing purposes.
        self._db_connection = RealDatabaseConnection("live_connection_string")

    def generate(self, report_name: str) -> str:
        """
        Generates a report using data from the database.
        """
        # This method's logic is dependent on the concrete RealDatabaseConnection.
        data = self._db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
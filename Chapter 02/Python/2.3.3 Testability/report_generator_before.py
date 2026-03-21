from database_connection import DatabaseConnection

class ReportGeneratorBefore:
    """
    Demonstrates a class that is difficult to test due to tight coupling.
    """
    def __init__(self):
        # PROBLEM: Hardcoded Dependency (Tight Coupling)
        # The constructor creates its own instance of `DatabaseConnection`.
        # This is called "tight coupling." The `ReportGeneratorBefore` class is
        # permanently and directly tied to the `DatabaseConnection` class.
        #
        # WHY IS THIS BAD FOR TESTABILITY?
        # 1. No Isolation: You cannot test `ReportGeneratorBefore` without also
        #    testing `DatabaseConnection`.
        # 2. Real External Services: Unit tests should be fast and self-contained.
        #    Because we are forced to use `DatabaseConnection`, our tests would
        #    need to connect to an actual database. This is slow and unreliable.
        # 3. No "Fakes" or "Mocks": We can't substitute a "fake" database
        #    connection for testing purposes.
        self.db_connection = DatabaseConnection("live_connection_string")

    def generate(self, report_name):
        """
        Generates a report using data from the database.
        
        Args:
            report_name (str): The name of the report to generate.
            
        Returns:
            str: A string representing the generated report.
        """
        data = self.db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
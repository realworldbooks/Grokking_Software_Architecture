from .database_connection import IDatabaseConnection

class ReportGenerator:
    """
    Demonstrates a class that is easy to test by using Dependency Injection.
    """

    def __init__(self, db_connection: IDatabaseConnection):
        # IMPROVEMENT: Dependency is Injected (Loose Coupling)
        # The class no longer creates its own database connection.
        # It receives any object that satisfies the IDatabaseConnection contract.
        self._db_connection = db_connection

    def generate(self, report_name: str) -> str:
        data = self._db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
from database_connection import DatabaseConnection

class ReportGeneratorBefore:
    def __init__(self):
        self.db_connection = DatabaseConnection("live_connection_string")

    def generate(self, report_name):
        data = self.db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
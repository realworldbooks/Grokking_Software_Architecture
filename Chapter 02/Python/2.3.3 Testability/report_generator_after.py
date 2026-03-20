class ReportGeneratorAfter:
    def __init__(self, db_connection):
        self.db_connection = db_connection

    def generate(self, report_name):
        data = self.db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
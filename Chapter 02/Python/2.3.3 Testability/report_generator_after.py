class ReportGeneratorAfter:
    """
    Demonstrates a class that is easy to test by using Dependency Injection.
    """
    def __init__(self, db_connection):
        """
        Initializes the report generator with a database connection.
        
        Args:
            db_connection: An object that has a `get_data` method.
        """
        # IMPROVEMENT: Dependency is Injected (Loose Coupling)
        # Instead of creating its own dependency, the class receives it as a
        # constructor parameter. This is a common form of "Dependency Injection."
        #
        # WHY IS THIS GOOD FOR TESTABILITY?
        # 1. Loose Coupling: The class is no longer tightly coupled to a specific
        #    database implementation. It just needs *any* object that has a 
        #    `get_data` method (this is "Duck Typing").
        # 2. Control Inversion: The control of which database connection to use
        #    has been "inverted." It's now the responsibility of the code that
        #    creates this object.
        # 3. Mocking is Now Possible: In a test, we can pass a "fake" object
        #    to the constructor and test the class in complete isolation.
        self.db_connection = db_connection

    def generate(self, report_name):
        """
        Generates a report using data from the injected database connection.
        
        Args:
            report_name (str): The name of the report to generate.
            
        Returns:
            str: A string representing the generated report.
        """
        data = self.db_connection.get_data(report_name)
        return f"Report '{report_name}' generated with {len(data)} rows."
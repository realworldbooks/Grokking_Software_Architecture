from abc import ABC, abstractmethod

class IDatabaseConnection(ABC):
    """
    Defines the 'contract' for a database connection.
    In Python, we use an Abstract Base Class (ABC) to ensure that any 
    subclass implements the required methods.
    """

    @abstractmethod
    def get_data(self, query: str) -> list[str]:
        """
        Fetches data from the database based on a query.
        """
        pass
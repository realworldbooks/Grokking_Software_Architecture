class Customer:
    """
    ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
    Because the core business rules for this bounded context revolve 
    around the Order, this Customer class can remain a simple data 
    entity holding state.
    """
    def __init__(self, id: int = 0, type: str = "", email: str = ""):
        self.id = id
        self.type = type
        self.email = email
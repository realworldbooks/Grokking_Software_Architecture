class Customer:
    """
    ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
    Because the core business rules for this bounded context revolve 
    around the Order, this Customer class can remain a simple data 
    entity holding state.
    """
    def __init__(self):
        self.id = 0
        self.type = ""  # e.g., "Gold"
        self.email = ""
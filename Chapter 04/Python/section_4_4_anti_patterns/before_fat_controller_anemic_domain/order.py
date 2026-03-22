"""
THE ANEMIC DOMAIN MODEL.
A simple data class with no behavior #A, #B.
"""
class Order:
    def __init__(self):
        self.id = None
        self.total = 0.0
        self.customer_email = ""
        self.items = []
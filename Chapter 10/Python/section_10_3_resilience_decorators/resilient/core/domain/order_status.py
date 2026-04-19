from enum import Enum

class OrderStatus(Enum):
    """
    DESIGN NOTE:
    Strictly enforcing system states via Enums prevents "Magic String" contamination. 
    By defining this inside 'core/domain', we ensure the entire system speaks the 
    same language, regardless of which external cloud provider we use.
    """
    PENDING_PAYMENT = "PENDING_PAYMENT"
    PAID = "PAID"
    FAILED = "FAILED"
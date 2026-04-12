from abc import ABC
from datetime import datetime
import uuid

class Event(ABC):
    """
    THE VIP BADGE: Abstract base class representing architectural constraints for events.
    
    By forcing all events to inherit from this base class, we ensure that every
    message moving through our pipeline has a standard shape. The infrastructure
    can rely on every event having an ID, a timestamp, and a correlation ID, 
    even if it doesn't understand the specific data payload.
    """
    event_id: uuid.UUID
    correlation_id: uuid.UUID
    occurred_on: datetime
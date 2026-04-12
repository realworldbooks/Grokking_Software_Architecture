# shared/order_placed.py

from dataclasses import dataclass, field
from datetime import datetime
import uuid
from .event import Event

@dataclass(frozen=True) 
class OrderPlaced(Event):
    """
    THE POCO: An immutable fact representing a completed action.
    
    In Event-Driven Architecture, events are named in the past tense because 
    they represent things that have already happened. 
    
    We use Python's @dataclass(frozen=True) decorator to guarantee immutability. 
    An event is a historical fact; once created, it must never be altered by 
    downstream consumers. If it could be altered, we would lose our system's 
    single source of truth.
    """
    correlation_id: uuid.UUID
    order_id: uuid.UUID
    user_id: uuid.UUID
    total_amount: float
    
    # We use default_factory so these are generated dynamically upon instantiation,
    # rather than being shared across all instances of the class.
    event_id: uuid.UUID = field(default_factory=uuid.uuid4)
    occurred_on: datetime = field(default_factory=datetime.utcnow)
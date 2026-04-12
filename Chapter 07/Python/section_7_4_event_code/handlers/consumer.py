# handlers/consumer.py

from abc import ABC, abstractmethod
from typing import Generic, TypeVar
from ..shared.event import Event

# T is a generic type variable that must be a subclass of Event
T = TypeVar('T', bound=Event)

class Consumer(ABC, Generic[T]):
    """
    THE HANDLER CONTRACT: The abstraction for all background workers.
    
    This ensures every background worker has a standard entry point for incoming 
    messages. We use Python's ABC (Abstract Base Class) module to enforce that 
    any class inheriting from this must implement the handle_async method.
    """
    
    @abstractmethod
    async def handle_async(self, event: T) -> None:
        """
        Processes the incoming event asynchronously.
        """
        pass
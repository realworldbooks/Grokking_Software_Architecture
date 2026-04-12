from abc import ABC, abstractmethod
from ..shared.event import Event

class EventPublisher(ABC):
    """
    THE ABSTRACTION: This keeps your domain logic completely decoupled.
    
    The Controller will depend on this abstraction so it has no idea whether 
    it is publishing to an asyncio Queue, a RabbitMQ cluster, or AWS EventBridge.
    It only knows that when it calls publish_async, the system will handle it.
    """
    
    @abstractmethod
    async def publish_async(self, event: Event) -> None:
        pass
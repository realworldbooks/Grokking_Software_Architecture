using System;

namespace Chapter07.EventCode.Shared
{
    /// <summary>
    /// THE POCO: An immutable fact representing a completed action.
    /// Notice the past-tense naming ("OrderPlaced") and the use of the 'record' 
    /// keyword, which guarantees this object cannot be changed once created.
    /// </summary>
    public record OrderPlaced : IEvent
    {
        // The unique ID of this specific message occurrence in the system
        public Guid EventId { get; init; } = Guid.NewGuid(); 

        // The "Thread" ID that tracks the user's request across multiple distributed services
        public Guid CorrelationId { get; init; }

        // Minimal payload details. We do not include the entire Customer Profile here.
        public Guid OrderId { get; init; }
        public Guid UserId { get; init; }
        public decimal TotalAmount { get; init; }
        
        // The exact timestamp the fact occurred
        public DateTime OccurredOn { get; init; } = DateTime.UtcNow;
    }
}
using System;

namespace Chapter07.EventCode.Shared
{
    /// <summary>
    /// THE VIP BADGE: An empty marker interface used for architectural constraints.
    /// We use this to enforce that our message broker only accepts valid events, 
    /// preventing developers from accidentally publishing random strings or database models.
    /// </summary>
    public interface IEvent
    {
        Guid EventId { get; init; }
        Guid CorrelationId { get; init; }
        DateTime OccurredOn { get; init; }
    }
}
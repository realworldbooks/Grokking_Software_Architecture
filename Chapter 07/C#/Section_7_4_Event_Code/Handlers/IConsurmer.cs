using System.Threading.Tasks;
using Chapter07.EventCode.Shared;

namespace Chapter07.EventCode.Handlers
{
    /// <summary>
    /// THE HANDLER CONTRACT: The abstraction for all background workers.
    /// It ensures every consumer has a standard entry point for incoming messages.
    /// </summary>
    public interface IConsumer<T> where T : IEvent
    {
        Task HandleAsync(T @event);
    }
}
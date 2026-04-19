using System;
using System.Threading.Tasks;
using Chapter10.Resilience.Core.Ports;

namespace Chapter10.Resilience.Infrastructure.Adapters;

/// <summary>
/// THE INFRASTRUCTURE ADAPTER (The Implementation):
/// 
/// DESIGN NOTE:
/// This adapter fulfills the IMessageQueue contract. In a production 
/// environment, this would encapsulate the logic for a library like 
/// 'MassTransit' or the 'AWS SDK'. 
/// </summary>
public class MockMessageQueueAdapter : IMessageQueue
{
    public Task EnqueueAsync(object payload)
    {
        // ARCHITECTURAL NOTE:
        // We simulate the handoff to a persistent broker.
        Console.WriteLine("      [Queue Adapter] Physical connection established...");
        Console.WriteLine("      [Queue Adapter] Data Secured: Transaction saved for background retry.");
        
        return Task.CompletedTask;
    }
}
using System.Collections.Generic;

namespace Chapter03.OrderProcessorRefactor.After;

/// <summary>
/// A Simple Data Transfer Object (DTO).
/// 
/// ARCHITECTURE NOTE: Just like in the "Before" state, this remains a pure 
/// data container. It holds the state that our new, highly-cohesive services 
/// will operate on.
/// </summary>
public class Order
{
    public required List<string> Items { get; set; }
    public decimal Total { get; set; }
    public required string CustomerEmail { get; set; }
}
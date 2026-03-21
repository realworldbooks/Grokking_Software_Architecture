using System.Collections.Generic;

namespace Chapter03.OrderProcessorRefactor.Before;

/// <summary>
/// A Simple Data Transfer Object (DTO).
/// 
/// ARCHITECTURE NOTE: This class is actually fine! It contains no logic, just state. 
/// The problem isn't the data; the problem is how the data is processed.
/// </summary>
public class Order
{
    public required List<string> Items { get; set; }
    public decimal Total { get; set; }
    public required string CustomerEmail { get; set; }
}
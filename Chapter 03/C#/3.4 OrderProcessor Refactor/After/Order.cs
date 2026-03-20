using System.Collections.Generic;

namespace Chapter03.OrderProcessorRefactor.After;

public class Order
{
    public required List<string> Items { get; set; }
    public decimal Total { get; set; }
    public required string CustomerEmail { get; set; }
}
namespace After.DomainModels
{
    /// <summary>
    /// ARCHITECTURE NOTE: A simple data entity. The behavior regarding 
    /// how items are priced and discounted is encapsulated inside the 
    /// Rich 'Order' model, not here.
    /// </summary>
    public class Item
    {
        public decimal Price { get; set; }
        public int Quantity { get; set; }
    }
}
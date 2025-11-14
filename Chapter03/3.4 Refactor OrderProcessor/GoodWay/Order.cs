namespace Refactor.GoodWay
{
    // The anemic data class (unchanged, as the logic
    // was moved to service classes in this example)
    public class Order
    {
        public List<string> Items { get; set; } = new List<string>();
        public decimal Total { get; set; }
        public string CustomerEmail { get; set; }
    }
}
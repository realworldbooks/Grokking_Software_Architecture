namespace Refactor.BadWay
{
    // This is the anemic data class
    public class Order
    {
        public List<string> Items { get; set; } = new List<string>();
        public decimal Total { get; set; }
        public string CustomerEmail { get; set; }
    }
}
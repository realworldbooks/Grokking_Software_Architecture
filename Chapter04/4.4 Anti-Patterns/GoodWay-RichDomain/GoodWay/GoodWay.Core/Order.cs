namespace GoodWay.Core
{
    // Rich Domain Model
    public class Order
    {
        public int Id { get; private set; }
        public decimal Total { get; private set; }
        public string CustomerEmail { get; private set; }
        private readonly List<Item> _items = new List<Item>();
        public IReadOnlyList<Item> Items => _items;

        public Order(string customerEmail)
        {
            if (string.IsNullOrEmpty(customerEmail))
                throw new ArgumentNullException(nameof(customerEmail));
            CustomerEmail = customerEmail;
            Id = new Random().Next(1000, 9999); // Fake ID
        }

        public void AddItem(Item item, Customer customer)
        {
            if (item.Price <= 0)
            {
                throw new InvalidOperationException("Item price must be positive.");
            }
            _items.Add(item);
            RecalculateTotal(customer);
        }

        private void RecalculateTotal(Customer customer)
        {
            Console.WriteLine("(DOMAIN) Calculating total...");
            Total = _items.Sum(item => item.Price * item.Quantity);
            if (customer.Type == "Gold")
            {
                Console.WriteLine("(DOMAIN) Applying Gold discount.");
                Total *= 0.9m; // 10% discount logic lives here!
            }
        }
    }
}
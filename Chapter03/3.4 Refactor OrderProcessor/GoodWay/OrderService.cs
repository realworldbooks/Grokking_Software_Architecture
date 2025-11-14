namespace Refactor.GoodWay
{
    // --- Step 2: The High-Level Coordinator Class ---
    public class OrderService
    {
        // These are concrete dependencies, which we'll improve on
        // when we learn about DIP!
        private readonly OrderValidator _validator;
        private readonly PaymentService _paymentService;
        private readonly InventoryManager _inventoryManager;
        private readonly NotificationService _notificationService;

        // Dependencies are "injected" through the constructor
        public OrderService(
            OrderValidator validator,
            PaymentService payment,
            InventoryManager inventory,
            NotificationService notifier
        )
        {
            _validator = validator;
            _paymentService = payment;
            _inventoryManager = inventory;
            _notificationService = notifier;
        }

        public string ProcessOrder(Order order)
        {
            _validator.Validate(order);

            if (_paymentService.ProcessPayment(order))
            {
                _inventoryManager.UpdateInventory(order);
                _notificationService.SendConfirmationEmail(order);
                return "Order processed successfully.";
            }
            else
            {
                return "Payment failed.";
            }
        }
    }
}
namespace Chapter03.OrderProcessorRefactor.After;

public class OrderService
{
    private readonly OrderValidator _validator;
    private readonly PaymentService _paymentService;
    private readonly InventoryManager _inventoryManager;
    private readonly NotificationService _notificationService;

    public OrderService(
       OrderValidator validator, PaymentService payment, 
       InventoryManager inventory, NotificationService notifier)
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
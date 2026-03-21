namespace Chapter03.OrderProcessorRefactor.After;

/// <summary>
/// THE SOLUTION: The Coordinator / Facade.
/// 
/// ARCHITECTURE NOTE: Look closely at this class. Notice what it DOESN'T do:
/// It doesn't validate, it doesn't charge credit cards, and it doesn't send emails. 
/// 
/// Instead, it acts as a high-level "Coordinator". It orchestrates the flow of 
/// the transaction by delegating the actual work to the injected services. 
/// 
/// By using Constructor Injection (DIP), we can easily pass in "Mock" versions 
/// of the PaymentService and NotificationService to completely unit test this 
/// entire checkout flow in milliseconds without ever hitting a real database 
/// or charging a real credit card!
/// </summary>
public class OrderService
{
    // Dependencies are explicitly declared.
    private readonly OrderValidator _validator;
    private readonly PaymentService _paymentService;
    private readonly InventoryManager _inventoryManager;
    private readonly NotificationService _notificationService;

    /// <summary>
    /// Dependencies are injected from the outside (Constructor Injection).
    /// </summary>
    public OrderService(
       OrderValidator validator, PaymentService payment, 
       InventoryManager inventory, NotificationService notifier)
    {
        _validator = validator;
        _paymentService = payment;
        _inventoryManager = inventory;
        _notificationService = notifier;
    }

    /// <summary>
    /// The high-level transaction script is now clean, readable, and safe.
    /// </summary>
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
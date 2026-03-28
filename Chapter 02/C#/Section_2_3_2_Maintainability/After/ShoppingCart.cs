namespace Chapter02.ShoppingCart.After;

/// <summary>
/// Manages shopping cart operations.
/// ARCHITECTURAL NOTE: This "After" class is now highly focused. 
/// Because the CartItem model was extracted to its own file, this file 
/// only contains the pure business logic and is much easier to read.
/// </summary>
public class ShoppingCart
{
    // IMPROVEMENT 1: Use Named Constants
    private const decimal DISCOUNT_RATE = 0.10m;
    private const decimal TAX_RATE = 0.08m;

    private decimal CalculateSubtotal(List<CartItem> items)
    {
        // Single responsibility: calculating the subtotal.
        return items.Sum(item => item.Price);
    }

    private decimal ApplyDiscount(decimal amount, decimal rate)
    {
        // Single responsibility: applying discounts.
        return amount * (1 - rate);
    }

    private decimal AddTax(decimal amount, decimal rate)
    {
        // Single responsibility: applying tax rules.
        return amount * (1 + rate);
    }

    public string ProcessOrder(List<CartItem> cartItems)
    {
        // IMPROVEMENT 2: Method Decomposition
        // This method now reads like a high-level summary of the business process.
        decimal subtotal = CalculateSubtotal(cartItems);
        decimal totalAfterDiscount = ApplyDiscount(subtotal, DISCOUNT_RATE);
        decimal finalTotal = AddTax(totalAfterDiscount, TAX_RATE);

        return $"Order processed! Your final total is ${finalTotal:F2}";
    }
}

public class Demo
{
    public static void Run()
    {
        Console.WriteLine("--- Maintainability: Shopping Cart (AFTER) ---");
        Console.WriteLine("Notice how easy it is to read the decomposed ProcessOrder() method.\n");
        
        var cart = new List<CartItem>
        {
            new CartItem { Name = "Laptop", Price = 1000.00m },
            new CartItem { Name = "Mouse", Price = 50.00m }
        };

        var cartSystem = new ShoppingCart();
        Console.WriteLine(cartSystem.ProcessOrder(cart));
        Console.WriteLine("\n-----------------------------------------");
    }
}
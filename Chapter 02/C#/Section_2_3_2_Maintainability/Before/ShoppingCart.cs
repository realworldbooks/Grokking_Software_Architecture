namespace Chapter02.ShoppingCart.Before;

// ARCHITECTURAL NOTE: The "File Dump" Anti-Pattern
// In messy codebases, developers often dump data models (like CartItem) 
// into the exact same file as the business logic. As the app grows, 
// this file will become thousands of lines long and impossible to navigate.
public class CartItem
{
    public required string Name { get; set; }
    public decimal Price { get; set; }
}

/// <summary>
/// Manages shopping cart operations.
/// ARCHITECTURAL NOTE: This "Before" class demonstrates poor maintainability 
/// due to magic numbers and a lack of Separation of Concerns.
/// </summary>
public class ShoppingCart
{
    public string ProcessOrder(List<CartItem> cartItems)
    {
        // 1. Calculating the subtotal.
        decimal subtotal = 0;
        foreach (var item in cartItems)
        {
            subtotal += item.Price;
        }

        // PROBLEM 1: "Magic Numbers"
        // The numbers 0.10 and 0.08 are hardcoded values without any explanation.
        decimal discount = subtotal * 0.10m; 
        decimal totalAfterDiscount = subtotal - discount;
        
        decimal tax = totalAfterDiscount * 0.08m; 
        decimal finalTotal = totalAfterDiscount + tax;

        // PROBLEM 2: Lack of Separation of Concerns
        // This method does everything: calculates subtotal, applies discount, and adds tax.
        return $"Order processed! Your final total is ${finalTotal:F2}";
    }
}

public class Demo
{
    public static void Run()
    {
        Console.WriteLine("--- Maintainability: Shopping Cart (BEFORE) ---");
        Console.WriteLine("Notice the 'magic numbers' and the rigid 'God Method' design.\n");
        
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
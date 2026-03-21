using System.Collections.Generic;
using System.Linq;

namespace Chapter02.ShoppingCartExample;

/// <summary>
/// Manages shopping cart operations, including calculating the final total for an order.
/// This class demonstrates the concept of maintainability by showing a "before" and "after" refactoring.
/// </summary>
public class ShoppingCart
{
    // --- BEFORE REFACTOR ---

    /// <summary>
    /// Processes the order in a single, hard-to-maintain method.
    /// </summary>
    /// <param name="cartItems">The list of items in the cart.</param>
    /// <returns>A string summarizing the final total.</returns>
    public string ProcessOrderBefore(List<CartItem> cartItems)
    {
        // 1. Calculating the subtotal.
        decimal subtotal = 0;
        foreach (var item in cartItems)
        {
            subtotal += item.Price;
        }

        // PROBLEM 1: "Magic Numbers"
        // The numbers 0.10 and 0.08 are "magic numbers." They are hardcoded values
        // without any explanation. If the discount or tax rate changes, a developer
        // has to hunt down these numbers in the code. In a large application, this
        // can be error-prone and time-consuming. What does 0.10 mean? Is it a discount? A fee?
        decimal discount = subtotal * 0.10m; // Magic number for discount rate
        decimal totalAfterDiscount = subtotal - discount;
        
        decimal tax = totalAfterDiscount * 0.08m; // Magic number for tax rate
        decimal finalTotal = totalAfterDiscount + tax;

        // PROBLEM 2: Lack of Separation of Concerns
        // This method does everything: calculates subtotal, applies a discount, and adds tax.
        // If the logic for any of these steps changes, we have to modify this entire method.
        // This makes the method rigid and harder to test or reuse individual pieces of logic.
        return $"Order processed! Your final total is ${finalTotal:F2}";
    }

    // --- AFTER REFACTOR ---
    
    // IMPROVEMENT 1: Use Named Constants
    // By defining the discount and tax rates as constants, we give them meaningful names.
    // This makes the code self-documenting. If a rate needs to change, we only have to
    // update it in one place, reducing the risk of errors.
    private const decimal DISCOUNT_RATE = 0.10m;
    private const decimal TAX_RATE = 0.08m;

    /// <summary>
    /// Calculates the subtotal of all items in the cart.
    /// </summary>
    /// <param name="items">A list of cart items.</param>
    /// <returns>The calculated subtotal.</returns>
    private decimal CalculateSubtotal(List<CartItem> items)
    {
        // This method now has a single responsibility: calculating the subtotal.
        // It's easy to understand, test, and reuse.
        return items.Sum(item => item.Price);
    }

    /// <summary>
    /// Applies a discount to a given amount.
    /// </summary>
    /// <param name="amount">The original amount.</param>
    /// <param name="rate">The discount rate to apply.</param>
    /// <returns>The amount after the discount is applied.</returns>
    private decimal ApplyDiscount(decimal amount, decimal rate)
    {
        // This is another single-responsibility method. If the discount logic changes
        // (e.g., becomes a fixed amount instead of a percentage), we only need to change it here.
        return amount * (1 - rate);
    }

    /// <summary>
    /// Adds tax to a given amount.
    /// </summary>
    /// <param name="amount">The original amount.</param>
    /// <param name="rate">The tax rate to apply.</param>
    /// <returns>The amount after tax is added.</returns>
    private decimal AddTax(decimal amount, decimal rate)
    {
        // The tax calculation is also isolated. If tax rules change, this is the only
        // place that needs to be updated.
        return amount * (1 + rate);
    }

    /// <summary>
    /// Processes the order using a more maintainable, modular approach.
    /// </summary>
    /// <param name="cartItems">The list of items in the cart.</param>
    /// <returns>A string summarizing the final total.</returns>
    public string ProcessOrderAfter(List<CartItem> cartItems)
    {
        // IMPROVEMENT 2: Method Decomposition
        // The business logic is now broken down into small, well-named methods.
        // The `ProcessOrderAfter` method reads like a high-level summary of the steps involved.
        // This makes the code much more readable and easier to follow for new developers.
        // Each smaller method can be tested independently, improving testability.
        decimal subtotal = CalculateSubtotal(cartItems);
        decimal totalAfterDiscount = ApplyDiscount(subtotal, DISCOUNT_RATE);
        decimal finalTotal = AddTax(totalAfterDiscount, TAX_RATE);

        return $"Order processed! Your final total is ${finalTotal:F2}";
    }

    public class ShoppingCartDemo
{
    public static void Run()
    {
        Console.WriteLine("--- Maintainability Example: Shopping Cart Refactor ---");
        
        var cart = new List<CartItem>
        {
            new CartItem { Name = "Laptop", Price = 1000.00m },
            new CartItem { Name = "Mouse", Price = 50.00m }
        };

        var cartSystem = new ShoppingCart();
        
        Console.WriteLine("Before Refactor:");
        Console.WriteLine(cartSystem.ProcessOrderBefore(cart));

        Console.WriteLine("\nAfter Refactor:");
        Console.WriteLine(cartSystem.ProcessOrderAfter(cart));
        Console.WriteLine("-----------------------------------------\n");
    }
}
}
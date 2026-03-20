using System.Collections.Generic;
using System.Linq;

namespace Chapter02.ShoppingCartExample;

public class ShoppingCart
{
    // --- BEFORE REFACTOR ---
    public string ProcessOrderBefore(List<CartItem> cartItems)
    {
        decimal subtotal = 0;
        foreach (var item in cartItems)
        {
            subtotal += item.Price;
        }
        
        decimal discount = subtotal * 0.10m;
        decimal totalAfterDiscount = subtotal - discount;
        
        decimal tax = totalAfterDiscount * 0.08m;
        decimal finalTotal = totalAfterDiscount + tax;

        return $"Order processed! Your final total is ${finalTotal:F2}";
    }

    // --- AFTER REFACTOR ---
    private const decimal DISCOUNT_RATE = 0.10m;
    private const decimal TAX_RATE = 0.08m;

    private decimal CalculateSubtotal(List<CartItem> items)
    {
        return items.Sum(item => item.Price);
    }

    private decimal ApplyDiscount(decimal amount, decimal rate)
    {
        return amount * (1 - rate);
    }

    private decimal AddTax(decimal amount, decimal rate)
    {
        return amount * (1 + rate);
    }

    public string ProcessOrderAfter(List<CartItem> cartItems)
    {
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
namespace Chapter02.ShoppingCartExample;

/// <summary>
/// Represents a single item within a shopping cart.
/// This class is a simple data structure (also known as a Plain Old CLR Object or POCO).
/// Its only job is to hold data about a cart item, not to perform any business logic.
/// </summary>
public class CartItem
{
    /// <summary>
    /// The name of the product.
    /// </summary>
    public required string Name { get; set; }

    /// <summary>
    /// The price of a single unit of the product.
    /// </summary>
    public decimal Price { get; set; }
}
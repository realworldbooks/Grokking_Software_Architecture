namespace Chapter02.ShoppingCart.After;

/// <summary>
/// ARCHITECTURAL NOTE: Clean File Separation
/// By moving our data structures (POCOs) into their own dedicated files, 
/// we make the codebase vastly easier to navigate. If another developer 
/// needs to see the shape of a CartItem, they don't have to hunt through 
/// business logic to find it.
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
namespace Chapter02.ConstraintsInAction;

/// <summary>
/// Represents a user entity.
/// This is a simple data-holding class, often called a POCO (Plain Old CLR Object)
/// or a DTO (Data Transfer Object). Its job is to represent the structure of our data
/// as it moves between different layers of the application (e.g., from the database
/// to the controller).
/// </summary>
public class User
{
    /// <summary>
    /// The unique identifier for the user.
    /// The `required` keyword is a C# feature that enforces a constraint:
    /// this property MUST be initialized when a new User object is created.
    /// </summary>
    public required string Id { get; set; }

    /// <summary>
    /// The user's full name.
    /// </summary>
    public required string Name { get; set; }

    /// <summary>
    /// The user's email address.
    /// </summary>
    public required string Email { get; set; }
}
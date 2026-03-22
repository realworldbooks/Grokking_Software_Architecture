namespace After.DomainModels
{
    /// <summary>
    /// ARCHITECTURE NOTE: Not every domain model needs complex behavior. 
    /// Because the core business rules for this bounded context revolve 
    /// around the Order, this Customer class can remain a simple data 
    /// entity holding state.
    /// </summary>
    public class Customer
    {
        public int Id { get; set; }
        public string Type { get; set; } // e.g., "Gold"
        public string Email { get; set; }
    }
}
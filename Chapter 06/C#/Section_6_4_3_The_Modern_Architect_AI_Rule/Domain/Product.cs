namespace Chapter06.AiApiExample.Domain
{
    /// <summary>
    /// THE DOMAIN MODEL (Entity).
    /// Represents the internal business reality (e.g., a Database Table).
    /// This should NEVER be sent directly over the HTTP API.
    /// </summary>
    public class Product
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public bool IsDigital { get; set; }
        public decimal WeightInLbs { get; set; }
        public decimal Price { get; set; }
    }
}
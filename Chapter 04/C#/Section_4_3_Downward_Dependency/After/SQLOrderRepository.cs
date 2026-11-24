namespace Chapter04.Section_4_3.After
{
    // DataAccessLayer.cs  
	public class SqlOrderRepository : IOrderRepository // Implements interface 
	{
	    public void Save(Order order)
	    {
	        Console.WriteLine("(After Refactor) Saving order to SQL...");
	    }
	}
}
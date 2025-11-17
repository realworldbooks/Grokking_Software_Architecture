namespace GoodWay
{
    // DataAccessLayer.cs  
	public class SqlOrderRepository : IOrderRepository // Implements interface 
	{
	    public void Save(Order order)
	    {
	        Console.WriteLine("(GOOD) Saving order to SQL...");
	    }
	}
}
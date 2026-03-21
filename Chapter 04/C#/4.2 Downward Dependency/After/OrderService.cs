namespace After
{
	public class OrderService
	{
	    private readonly IOrderRepository _repo; // Depends on abstraction 
	    public OrderService(IOrderRepository repo) 
        { 
            _repo = repo; 
        }
	    public void SaveOrder(Order order)
	    {
	        // Calls DOWNWARDS via interface 
	        _repo.Save(order);
	    }
	}
}
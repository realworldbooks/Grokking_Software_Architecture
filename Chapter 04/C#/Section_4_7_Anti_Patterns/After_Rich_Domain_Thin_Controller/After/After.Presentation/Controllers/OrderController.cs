using Microsoft.AspNetCore.Mvc;
using After.Application;

namespace After.Presentation.Controllers
{
    [ApiController]
    [Route("[controller]")]
    /// <summary>
    /// THE THIN CONTROLLER
    /// ARCHITECTURE NOTE: This controller is finally cured of the "Fat 
    /// Controller" anti-pattern. It has zero business logic, zero 
    /// database logic, and zero validation rules. Its ONLY job is to 
    /// translate an HTTP POST request into a Business Logic method call, 
    /// and return an HTTP response (200 OK).
    /// </summary>
    public class OrderController : ControllerBase
    {
        private readonly IOrderService _orderService;

        public OrderController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost]
        public IActionResult CreateOrder(OrderRequest request)
        {
            try
            {
                // 'response' is already an OrderResponse object
                OrderResponse response = _orderService.CreateOrder(request);

                // Return the object directly for a clean JSON structure
                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
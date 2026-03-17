using Microsoft.AspNetCore.Mvc;

namespace AiApi.Demo.Controllers
{
    // 1. THE DTO (The AI's Input Form)
    public class ShippingRequest 
    {
        /// <summary>
        /// The unique ID of the physical product. 
        /// Do NOT send digital product IDs (like MP3s or eBooks).
        /// </summary>
        public string ProductId { get; set; }

        /// <summary>
        /// The destination zip code. Must be exactly 5 digits.
        /// </summary>
        public string ZipCode { get; set; }
    }

    // 2. THE CONTROLLER (The AI's Tool)
    [ApiController]
    [Route("api/[controller]")]
    public class ShippingController : ControllerBase
    {
        /// <summary>
        /// Calculates the shipping cost for a specific physical item.
        /// USE THIS ENDPOINT whenever the user asks "How much is shipping?"
        /// Do NOT use this endpoint for digital items.
        /// </summary>
        [HttpPost("calculate-shipping")]
        public ActionResult<decimal> GetShipping([FromBody] ShippingRequest request) 
        {
            // Simulated business logic
            if (request.ProductId.StartsWith("DIGITAL"))
            {
                return BadRequest("Digital items do not require shipping.");
            }

            return Ok(5.99m); 
        }
    }
}

// Chapter 04/CSharp/4.4-Rich-After/After.BusinessLogic/OrderService.cs
using System;
using After.DomainModels;
using After.DataAccess; // <-- THE DOWNWARD DEPENDENCY

namespace After.BusinessLogic
{
    /// <summary>
    /// THE SERVICE LAYER (Orchestrator)
    /// ARCHITECTURE NOTE: This class replaces the massive "God Method" 
    /// from the Fat Controller. It doesn't write to the DB, nor does 
    /// it calculate math. It simply coordinates the flow of data 
    /// between the Data Access layer and the Rich Domain Models.
    /// </summary>
    public class OrderService : IOrderService
    {
        // Dependencies on the Data Access layer below it
        private readonly IOrderRepository _orderRepo;
        private readonly ICustomerRepository _customerRepo;
        private readonly IItemRepository _itemRepo; // Added to facilitate secure lookups
        private readonly IEmailService _emailService;

        public OrderService(
            IOrderRepository orderRepo,
            ICustomerRepository customerRepo,
            IItemRepository itemRepo,
            IEmailService emailService)
        {
            _orderRepo = orderRepo;
            _customerRepo = customerRepo;
            _itemRepo = itemRepo;
            _emailService = emailService;
        }

        public int CreateOrder(OrderRequest request)
        {
            // 1. Fetch data from lower layer
            var customer = _customerRepo.GetById(request.CustomerId);
            if (customer == null)
                throw new InvalidOperationException("Not found.");

            // 2. Instantiate the Rich Domain Model
            var order = new Order(customer.Email); 

            // 3. Delegate business logic to the Rich Model
            foreach (var itemRequest in request.Items)
            {
                // SECURITY NOTE: We look up the item from the repository 
                // to get the true price, rather than trusting the price 
                // provided in the HTTP request DTO.
                var actualItem = _itemRepo.GetById(itemRequest.ItemId);
                if (actualItem == null)
                    throw new InvalidOperationException($"Item {itemRequest.ItemId} not found.");

                // Map the quantity from the request to the domain object
                actualItem.Quantity = itemRequest.Quantity;

                // The service doesn't care about discount rules; 
                // the Order model handles that internally.
                order.AddItem(actualItem, customer);
            }

            // 4. Send the updated model back down to Data Access
            _orderRepo.Save(order);
            _emailService.Send(
                order.CustomerEmail, "Confirmed!", "Success."
            );

            return order.Id;
        }
    }
}
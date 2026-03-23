# 4.4 After: Rich Domain Refactor

This is a multi-project .NET solution demonstrating the correct, layered refactor of the "Fat Controller" example. It also implements the Secure Item Lookup pattern to prevent price tampering.

## Projects

* **After.Domain:** The Domain Layer. Contains the "rich" `Order` model.
* **After.Application:** The Application Layer. Contains services and interfaces (`IOrderService`, `IOrderRepository`, `IItemRepository`).
* **After.Infrastructure:** The Infrastructure Layer. Contains concrete implementations (`SqlOrderRepository`, `SqlItemRepository`).
* **After.Presentation:** The Presentation Layer. This is the runnable startup project.

## How to Run the Server

1.  Make sure you have the .NET 8 SDK installed.
2.  You can open `After.sln` in Visual Studio or VS Code.
3.  You have a few ways to run this: 
	a) From the command line (in this `After_Rich_Domain_Thin_Controller` directory), run:
       ```bash
       dotnet run --project After.Presentation/After.Presentation.csproj
       ```
	b) Press the green triangle button in Visual Studio or Visual Code to start the application.
4.  The server will start. Your console will show it's listening on `http://localhost:7200`.

## How to Test with Swagger UI (Recommended)

This is the easiest way to test your API. The `dotnet new webapi` template includes a user-friendly "Swagger" interface by default.

1.  After the server is running, open your web browser (like Chrome or Firefox).
2.  In the address bar, go to:
    **`http://localhost:7200/swagger`**
3.  You will see the Swagger UI page, showing your "Order" API.
4.  Click on the `POST /Order` endpoint to expand it.
5.  Click the **"Try it out"** button (on the right).
6.  The "Request body" text box will become editable. Replace the contents with this JSON:
    ```json
    {
      "customerId": 123,
      "items": [
        {
          "itemId": 1,
          "quantity": 1
        },
        {
           "itemId": 2,
           "quantity": 2
        }
      ]
    }
    ```
7.  Click the big blue **"Execute"** button.

### Expected Result

* **In your browser:** You will see a "Server response" with a `200` code and a response body showing your new order ID (e.g., `{"orderId": 8264}`).
* **In your terminal:** You will see all the console logs from the different layers, proving the flow worked!
    ```
    (API) Received request...
    (INFRA) Getting customer from SQL DB...
    (INFRA) Fetching official data for Item ID: 1 from SQL...
    (INFRA) Fetching official data for Item ID: 2 from SQL...
    (DOMAIN) Calculating total...
    (DOMAIN) Applying Gold discount.
    (INFRA) Saving order to SQL DB...
    (INFRA) Sending email to archie@example.com...
    (API) Request finished successfully.
    ```
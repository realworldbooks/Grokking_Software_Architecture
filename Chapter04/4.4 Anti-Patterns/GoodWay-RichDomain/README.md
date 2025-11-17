# 4.4 Good Way: Rich Domain Refactor

This is a multi-project .NET solution demonstrating the correct, layered refactor of the "Fat Controller" example.

## Projects

* **GoodWay.Core:** The Domain Layer. Contains the "rich" `Order` model.
* **GoodWay.Application:** The Application Layer. Contains services and interfaces (`IOrderService`, `IOrderRepository`).
* **GoodWay.Infrastructure:** The Infrastructure Layer. Contains concrete implementations (`SqlOrderRepository`).
* **GoodWay.WebAPI:** The Presentation Layer. This is the runnable startup project.

## How to Run the Server

1.  Make sure you have the .NET 8 SDK installed.
2.  You can open `GoodWay.sln` in Visual Studio or VS Code.
3.  You have a few ways to run this: 
	a) From the command line (in this `GoodWay-RichDomain` directory), run:
       ```bash
       dotnet run --project GoodWay.WebAPI/GoodWay.WebAPI.csproj
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
          "price": 100,
          "quantity": 1
        },
        {
           "price": 25,
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
    (DOMAIN) Calculating total...
    (DOMAIN) Applying Gold discount.
    (INFRA) Saving order to SQL DB...
    (INFRA) Sending email to archie@example.com...
    (API) Request finished successfully.
    ```
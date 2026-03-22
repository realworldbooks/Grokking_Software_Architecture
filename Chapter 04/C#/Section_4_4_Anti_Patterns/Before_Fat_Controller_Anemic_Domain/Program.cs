using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Even though our "Fat Controller" doesn't use DI for its logic,
// the ASP.NET Core framework still needs these lines to know about Controllers.
builder.Services.AddControllers();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}

app.UseRouting();
app.MapControllers(); // This finds OrderController and maps the route

Console.WriteLine("--- FAT CONTROLLER APP RUNNING ---");
Console.WriteLine("Send a POST request to: http://localhost:5000/api/order");
Console.WriteLine("------------------------------------");

app.Run();
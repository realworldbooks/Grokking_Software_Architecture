using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

// Add standard Controller support.
builder.Services.AddControllers();

// ARCHITECTURAL NOTE: We add Swagger to make the anti-pattern runnable 
// and testable for the reader.
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.SwaggerDoc("v1", new OpenApiInfo 
    { 
        Title = "Grokking Software Architecture: The Fat Controller", 
        Version = "v1",
        Description = "Demonstrating the pitfalls of tight coupling and anemic models."
    });
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "Fat Controller V1");
        c.RoutePrefix = string.Empty; // Access at http://localhost:5000/
    });
}

// ARCHITECTURAL NOTE: Middleware Pipeline Order.
// 1. Static Files: Necessary if Swagger UI assets are treated as static content.
app.UseStaticFiles();

// 2. Routing: Matches the incoming request to an internal endpoint.
app.UseRouting();

// 3. Authorization: standard middleware to prevent pipeline fall-through issues.
app.UseAuthorization();

// 4. Endpoints: Explicitly maps the OrderController routes.
app.MapControllers();

Console.WriteLine("--- FAT CONTROLLER APP RUNNING ---");
Console.WriteLine("Swagger UI available at: http://localhost:5000/");
Console.WriteLine("---------------------------------------------");

app.Run();
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

var builder = WebApplication.CreateBuilder(args);

// 1. Add services to the container (Controllers and Swagger)
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// 2. Configure the HTTP request pipeline
// We remove the "if (IsDevelopment())" check so Swagger ALWAYS loads for your demo
app.UseSwagger();
app.UseSwaggerUI(c => 
{
// 1. Explicitly tell the UI where the JSON file is
    c.SwaggerEndpoint("/swagger/v1/swagger.json", "Fat Controller API v1"); 
    
    // 2. Keep the UI at the root
    c.RoutePrefix = string.Empty;
});

// 3. Map your Fat Controller
app.MapControllers();

// 4. Start the server!
await app.RunAsync();
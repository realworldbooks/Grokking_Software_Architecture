using After.Application;
using After.Infrastructure;

var builder = WebApplication.CreateBuilder(args);

// --- Add services to the container ---

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// This is the "Composition Root" / Dependency Injection setup
// We map the abstractions (interfaces) to the concrete implementations.
builder.Services.AddScoped<IOrderService, OrderService>();
builder.Services.AddScoped<IOrderRepository, SqlOrderRepository>();
builder.Services.AddScoped<ICustomerRepository, SqlCustomerRepository>();
builder.Services.AddScoped<IEmailService, SmtpEmailService>();

var app = builder.Build();

// --- Configure the HTTP request pipeline ---
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseAuthorization();
app.MapControllers();

Console.WriteLine("--- Running 'After Refactoring' Rich Domain API ---");
Console.WriteLine("Send a POST request to /Order to test.");

app.Run();
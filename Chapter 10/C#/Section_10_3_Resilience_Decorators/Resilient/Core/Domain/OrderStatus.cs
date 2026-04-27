namespace Chapter10.Resilient.Core.Domain;

/// <summary>
/// DESIGN NOTE:
/// Enums provide the type-safety required for a robust system. By defining
/// this in the Core, we ensure that every adapter (FlakyPayments, Stripe, or SQL)
/// must translate its proprietary codes into our system's language.
/// </summary>
public enum OrderStatus
{
    PendingPayment,
    Paid,
    Failed
}
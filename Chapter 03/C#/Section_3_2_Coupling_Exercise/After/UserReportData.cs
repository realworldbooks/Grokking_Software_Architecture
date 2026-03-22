namespace Chapter03.CouplingTest.After;

/// <summary>
/// A Data Transfer Object (DTO).
/// The sole purpose of this class is to be a simple container for data that needs
/// to be passed across an architectural boundary (in this case, from the
/// `UserDataService` to the `UserReportGenerator`).
/// 
/// By creating this "chunky" object, we can reduce the number of calls between
/// the two components from four to just one, which significantly lowers their coupling.
/// </summary>
public class UserReportData
{
    public required string Name { get; set; }
    public required string Email { get; set; }
    public decimal TotalSpent { get; set; }
}
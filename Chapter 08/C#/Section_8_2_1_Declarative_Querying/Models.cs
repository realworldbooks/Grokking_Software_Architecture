// Section2_DeclarativeQuerying/Models.cs
using Microsoft.EntityFrameworkCore;

namespace Chapter08.DeclarativeQuerying
{
/// &lt;summary&gt;
/// THE DOMAIN MODEL (Declarative Mapping):
///
/// TEACHING NOTE:
/// In the old days, you had to write a 'CREATE TABLE' SQL string by hand.
/// With an ORM like Entity Framework, you simply define a native C\# class (an Entity).
/// The ORM reads this class and automatically generates the perfect database schema for you.
/// &lt;/summary&gt;
    public class User
    {
        public int Id { get; set; }
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
        public int Age { get; set; }
        public bool IsActive { get; set; }
    }
}
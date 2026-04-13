// Infrastructure/NoSqlDocument.cs
using System.Collections.Generic;

namespace Chapter08.DatabaseCode.Infrastructure
{
    public class NoSqlDocument
    {
        public string Name { get; set; } = string.Empty;
        public List<string> Tags { get; set; } = new List<string>();
        
        /// <summary>
        /// In C#, objects are strictly typed. To simulate the schema-less, 
        /// flexible nature of a JSON Document DB (like MongoDB), we use a 
        /// dictionary to hold arbitrary data added at runtime.
        /// </summary>
        public Dictionary<string, object> FlexibleData { get; set; } = new Dictionary<string, object>();
    }
}
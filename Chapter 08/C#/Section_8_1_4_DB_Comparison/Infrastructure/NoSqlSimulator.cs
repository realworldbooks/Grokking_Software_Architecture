// Infrastructure/NoSqlSimulator.cs
using System.Collections.Generic;
using System.Linq;

namespace Chapter08.DatabaseCode.Infrastructure
{
    /// <summary>
    /// THE DOCUMENT WAY (INFRASTRUCTURE LAYER): Fast, loose. Like a messy desk.
    /// Simulates MongoDB's document storage using standard C# lists and objects.
    /// </summary>
    public class NoSqlSimulator
    {
        public readonly List<NoSqlDocument> Collection = new List<NoSqlDocument>();

        public void InsertOne(NoSqlDocument document)
        {
            Collection.Add(document);
        }

        // The naive literal search
        public List<string> FindByName(string name)
        {
            return Collection
                .Where(doc => doc.Name == name)
                .Select(doc => doc.Name)
                .ToList();
        }

        public List<string> FindByTag(string tag)
        {
            // Contains Match: Better, but still relies on exact spelling of the tag.
            return Collection
                .Where(doc => doc.Tags.Contains(tag))
                .Select(doc => doc.Name)
                .ToList();
        }
    }
}
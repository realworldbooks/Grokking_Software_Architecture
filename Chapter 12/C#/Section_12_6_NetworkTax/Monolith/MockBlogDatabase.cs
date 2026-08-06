namespace Chapter12.Section_12_6_NetworkTax.Monolith;

public class DatabaseException : Exception
{
    public DatabaseException(string message) : base(message) { }
}

public class MockBlogDatabase
{
    public bool IsAvailable { get; set; } = true;

    public BlogDetailsViewModel GetBlogDetails(int blogId, int simulateMs = 5)
    {
        if (!IsAvailable) throw new DatabaseException("Database unavailable.");
        Thread.Sleep(simulateMs);
        var authors = new Dictionary<int, string> { { 1, "Ada Lovelace" }, { 2, "Alan Turing" } };
        var posts = new Dictionary<int, (string Title, int AuthorId)>
        {
            { 1, ("The Analytical Engine: A Deep Dive", 1) },
            { 2, ("Can Machines Think?", 2) }
        };
        var (title, authorId) = posts[blogId];
        return new BlogDetailsViewModel(blogId, title, authors[authorId], 0);
    }
}

public record BlogDetailsViewModel(int Id, string Title, string AuthorName, int CommentCount);

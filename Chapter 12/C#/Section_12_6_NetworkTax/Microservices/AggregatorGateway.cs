using System.Collections.Generic;

namespace Chapter12.Section_12_6_NetworkTax.Microservices;

/// <summary>
/// Thrown when the core Post service is unavailable — the page cannot be rendered.
/// </summary>
public class PostServiceUnavailableException : Exception
{
    public PostServiceUnavailableException(string msg) : base(msg) { }
}

/// <summary>
/// The result returned to the web page by the aggregator gateway.
/// Mirrors the Python <c>BlogDetailsResult</c> and the JS return object.
/// </summary>
public class BlogDetailsResult
{
    public Dictionary<string, object> Post { get; set; } = new();
    public Dictionary<string, object> Author { get; set; } = new();
    public List<Dictionary<string, object>> Comments { get; set; } = new();
}

/// <summary>
/// The Microservice Aggregator Gateway — Listing 12.2.
///
/// TAX 1: The Latency Tax — fires 3 HTTP calls across the wire.
/// TAX 2: The Reliability Tax — waits for the network.
/// TAX 3: Partial Failures — serves the page without comments when the
///        comment service is down, but aborts entirely when the post service
///        is down.
/// </summary>
public class AggregatorGateway
{
    private readonly MockPostServiceClient _p;
    private readonly MockAuthorServiceClient _a;
    private readonly MockCommentServiceClient _c;

    public AggregatorGateway(MockPostServiceClient p, MockAuthorServiceClient a, MockCommentServiceClient c)
    {
        _p = p;
        _a = a;
        _c = c;
    }

    public async Task<BlogDetailsResult> GetBlogDetailsAsync(int blogId)
    {
        // TAX 1: The Latency Tax — fire 3 HTTP calls across the wire.
        var postTask = _p.GetPostAsync(blogId);
        var authorTask = _a.GetAuthorAsync(blogId);
        var commentsTask = _c.GetCommentsAsync(blogId);

        // TAX 2: The Reliability Tax — wait for the network.
        await Task.WhenAll(postTask, authorTask, commentsTask);

        var postResponse = postTask.Result;
        if (!postResponse.IsSuccess)
            throw new PostServiceUnavailableException("Core post data unavailable.");

        var authorResponse = authorTask.Result;
        var commentsResponse = commentsTask.Result;

        var post = postResponse.Payload;
        var author = authorResponse.IsSuccess ? authorResponse.Payload : new Dictionary<string, object>();

        // TAX 3: Partial Failures — serve page without comments.
        List<Dictionary<string, object>> comments = new();
        if (commentsResponse.IsSuccess && commentsResponse.Payload.TryGetValue("comments", out var c))
        {
            comments = (List<Dictionary<string, object>>)c;
        }

        return new BlogDetailsResult
        {
            Post = post,
            Author = author,
            Comments = comments
        };
    }
}

using System.Collections.Generic;

namespace Chapter12.Section_12_6_NetworkTax.Microservices;

/// <summary>
/// Simulates an HTTP response object.
/// The Aggregator checks <c>IsSuccess</c> like it would check
/// <c>response.IsSuccessStatusCode</c> in a real HTTP client.
/// </summary>
public class MockHttpResponse
{
    public int StatusCode { get; }
    public Dictionary<string, object> Payload { get; }
    public bool IsSuccess => StatusCode >= 200 && StatusCode < 300;

    public MockHttpResponse(int statusCode, Dictionary<string, object>? payload = null)
    {
        StatusCode = statusCode;
        Payload = payload ?? new Dictionary<string, object>();
    }
}

/// <summary>
/// Simulates <c>GET http://post-service/api/posts/{id}</c> (async).
/// </summary>
public class MockPostServiceClient
{
    public bool IsAvailable { get; set; } = true;

    public async Task<MockHttpResponse> GetPostAsync(int id)
    {
        await Task.Delay(30);
        if (!IsAvailable)
            return new MockHttpResponse(503, new Dictionary<string, object> { ["error"] = "Service Unavailable" });

        var posts = new Dictionary<int, Dictionary<string, object>>
        {
            [1] = new Dictionary<string, object>
            {
                ["id"] = 1,
                ["title"] = "The Analytical Engine: A Deep Dive",
                ["body"] = "Charles Babbage's vision was centuries ahead..."
            },
            [2] = new Dictionary<string, object>
            {
                ["id"] = 2,
                ["title"] = "Can Machines Think?",
                ["body"] = "The question that launched a thousand papers..."
            }
        };

        return posts.TryGetValue(id, out var post)
            ? new MockHttpResponse(200, post)
            : new MockHttpResponse(404, new Dictionary<string, object> { ["error"] = "Not Found" });
    }
}

/// <summary>
/// Simulates <c>GET http://author-service/api/authors/{blogId}</c> (async).
/// </summary>
public class MockAuthorServiceClient
{
    public bool IsAvailable { get; set; } = true;

    public async Task<MockHttpResponse> GetAuthorAsync(int blogId)
    {
        await Task.Delay(30);
        if (!IsAvailable)
            return new MockHttpResponse(503, new Dictionary<string, object> { ["error"] = "Service Unavailable" });

        var authors = new Dictionary<int, Dictionary<string, object>>
        {
            [1] = new Dictionary<string, object>
            {
                ["id"] = 1,
                ["name"] = "Ada Lovelace",
                ["email"] = "ada@byte-blog.dev"
            },
            [2] = new Dictionary<string, object>
            {
                ["id"] = 2,
                ["name"] = "Alan Turing",
                ["email"] = "alan@byte-blog.dev"
            }
        };

        return authors.TryGetValue(blogId, out var author)
            ? new MockHttpResponse(200, author)
            : new MockHttpResponse(404, new Dictionary<string, object> { ["error"] = "Not Found" });
    }
}

/// <summary>
/// Simulates <c>GET http://comment-service/api/comments/{blogId}</c> (async).
/// </summary>
public class MockCommentServiceClient
{
    public bool IsAvailable { get; set; } = true;

    public async Task<MockHttpResponse> GetCommentsAsync(int blogId)
    {
        await Task.Delay(30);
        if (!IsAvailable)
            return new MockHttpResponse(503, new Dictionary<string, object> { ["error"] = "Service Unavailable" });

        var comments = new Dictionary<int, List<Dictionary<string, object>>>
        {
            [1] = new List<Dictionary<string, object>>
            {
                new Dictionary<string, object> { ["id"] = 1, ["authorName"] = "Grace Hopper", ["text"] = "Lovely write-up on analytical engines!" },
                new Dictionary<string, object> { ["id"] = 2, ["authorName"] = "Linus Torvalds", ["text"] = "Now do the same for kernel scheduling." }
            },
            [2] = new List<Dictionary<string, object>>
            {
                new Dictionary<string, object> { ["id"] = 3, ["authorName"] = "Ada Lovelace", ["text"] = "The Turing Test section is a classic." }
            }
        };

        return new MockHttpResponse(200, new Dictionary<string, object>
        {
            ["comments"] = comments.TryGetValue(blogId, out var c) ? c : new List<Dictionary<string, object>>()
        });
    }
}

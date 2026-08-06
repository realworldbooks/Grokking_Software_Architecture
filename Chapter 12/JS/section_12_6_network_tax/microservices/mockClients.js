class MockHttpResponse {
    constructor(statusCode, payload = {}) {
        this.statusCode = statusCode;
        this.payload = payload;
        this.isSuccess = statusCode >= 200 && statusCode < 300;
    }
}

class MockPostServiceClient {
    constructor(latencyMs = 30) {
        this.latencyMs = latencyMs;
        this.isAvailable = true;
    }

    async getPost(blogId) {
        await new Promise(r => setTimeout(r, this.latencyMs));
        if (!this.isAvailable) return new MockHttpResponse(503, { error: "Unavailable" });

        const posts = {
            1: { id: 1, title: "The Analytical Engine: A Deep Dive", body: "..." },
            2: { id: 2, title: "Can Machines Think?", body: "..." }
        };

        return posts[blogId]
            ? new MockHttpResponse(200, posts[blogId])
            : new MockHttpResponse(404, { error: "Not Found" });
    }
}

class MockAuthorServiceClient {
    constructor(latencyMs = 30) {
        this.latencyMs = latencyMs;
        this.isAvailable = true;
    }

    async getAuthor(blogId) {
        await new Promise(r => setTimeout(r, this.latencyMs));
        if (!this.isAvailable) return new MockHttpResponse(503, { error: "Unavailable" });

        const authors = {
            1: { id: 1, name: "Ada Lovelace", email: "ada@byte-blog.dev" },
            2: { id: 2, name: "Alan Turing", email: "alan@byte-blog.dev" }
        };

        return authors[blogId]
            ? new MockHttpResponse(200, authors[blogId])
            : new MockHttpResponse(404, { error: "Not Found" });
    }
}

class MockCommentServiceClient {
    constructor(latencyMs = 30) {
        this.latencyMs = latencyMs;
        this.isAvailable = true;
    }

    async getComments(blogId) {
        await new Promise(r => setTimeout(r, this.latencyMs));
        if (!this.isAvailable) return new MockHttpResponse(503, { error: "Unavailable" });

        const comments = {
            1: [
                { id: 1, authorName: "Grace Hopper", text: "Lovely!" },
                { id: 2, authorName: "Linus Torvalds", text: "Nice!" }
            ],
            2: [
                { id: 3, authorName: "Ada Lovelace", text: "Classic." }
            ]
        };

        return new MockHttpResponse(200, { comments: comments[blogId] || [] });
    }
}

export { MockHttpResponse, MockPostServiceClient, MockAuthorServiceClient, MockCommentServiceClient };

import { MockHttpResponse, MockPostServiceClient, MockAuthorServiceClient, MockCommentServiceClient } from "./mockClients.js";

export class PostServiceUnavailableError extends Error {
  constructor(message) { super(message); this.name = "PostServiceUnavailableError"; }
}

export class AggregatorGateway {
  constructor(postClient, authorClient, commentClient) {
    this._postClient = postClient;
    this._authorClient = authorClient;
    this._commentClient = commentClient;
  }

  async getBlogDetailsAsync(blogId) {
    // TAX 1: The Latency Tax - fire 3 HTTP calls across the wire.
    const postTask = this._postClient.getPost(blogId);
    const authorTask = this._authorClient.getAuthor(blogId);
    const commentsTask = this._commentClient.getComments(blogId);

    // TAX 2: The Reliability Tax - wait for the network.
    const [postResponse, authorResponse, commentsResponse] = await Promise.all([postTask, authorTask, commentsTask]);

    if (!postResponse.isSuccess) throw new PostServiceUnavailableError("Core post data unavailable.");

    const post = postResponse.payload;
    const author = authorResponse.isSuccess ? authorResponse.payload : {};

    // TAX 3: Partial Failures - serve page without comments.
    const comments = commentsResponse.isSuccess ? commentsResponse.payload.comments : [];

    return { post, author, comments };
  }
}

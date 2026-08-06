"""The Microservice Aggregator - Listing 12.2."""
import asyncio
import logging
from ..services.mock_clients import (
    MockAuthorServiceClient, MockCommentServiceClient,
    MockHttpResponse, MockPostServiceClient,
)

logger = logging.getLogger(__name__)

class PostServiceUnavailableError(Exception):
    pass

class BlogDetailsResult:
    def __init__(self, post=None, author=None, comments=None):
        self.post = post or {}
        self.author = author or {}
        self.comments = comments or []

class AggregatorGateway:
    def __init__(self, post_client, author_client, comment_client):
        self._post_client = post_client
        self._author_client = author_client
        self._comment_client = comment_client

    async def get_blog_details_async(self, blog_id: int) -> BlogDetailsResult:
        # TAX 1: The Latency Tax - fire 3 HTTP calls across the wire.
        post_task = asyncio.create_task(self._post_client.get_post(blog_id))
        author_task = asyncio.create_task(self._author_client.get_author(blog_id))
        comments_task = asyncio.create_task(self._comment_client.get_comments(blog_id))

        # TAX 2: The Reliability Tax - wait for the network.
        await asyncio.gather(post_task, author_task, comments_task)

        post_response = post_task.result()
        if not post_response.is_success:
            logger.error("Core post data unavailable.")
            raise PostServiceUnavailableError("Core post data unavailable.")

        author_response = author_task.result()
        comments_response = comments_task.result()

        post = post_response.payload
        author = author_response.payload if author_response.is_success else {}

        # TAX 3: Partial Failures - serve page without comments.
        comments = []
        if comments_response.is_success:
            comments = comments_response.payload.get("comments", [])
        else:
            logger.warning("Comment service is down. Serving page without comments.")

        return BlogDetailsResult(post=post, author=author, comments=comments)

"""Mock HTTP service clients for the Microservice Aggregator demo.

DESIGN NOTE:
In the book (Listing 12.2) the gateway calls three separate HTTP
services, each owning its own database:

    GET http://post-service/api/posts/{blogId}
    GET http://author-service/api/authors/{blogId}
    GET http://comment-service/api/comments/{blogId}

We are NOT spinning up real HTTP servers — that would be heavy and
unreliable in a teaching repo. Instead, each `MockXServiceClient`
simulates the network call:

  * Every method is `async` (mirroring the C# `GetAsync`).
  * Each awaits `asyncio.sleep(...)` to simulate network round-trip
    latency — the "Latency Tax".
  * It returns a `MockHttpResponse` (status code + JSON payload) just
    like a real HTTP endpoint, so the Aggregator can ONLY see what a
    real HTTP client would see.
  * Each client can be toggled `is_available = False` to simulate the
    service being down (the "Reliability Tax" / partial failures).

Because the calls are async, the Aggregator can `asyncio.gather` them
so they run CONCURRENTLY — exactly like the C# `Task.WhenAll` fires
three HTTP requests across the wire at the same time.
"""

import asyncio
from typing import Optional


class MockHttpResponse:
    """Simulates an HTTP response object.

    The Aggregator checks `.is_success` like it would check
    `response.IsSuccessStatusCode` in the C# version (Listing 12.2).
    """

    def __init__(self, status_code: int, payload: Optional[dict] = None) -> None:
        self.status_code = status_code
        self.payload: dict = payload or {}
        self.is_success = 200 <= status_code < 300


class MockPostServiceClient:
    """Simulates `http://post-service/api/posts/{id}` (async)."""

    def __init__(self, latency_ms: int = 30) -> None:
        self.latency_ms = latency_ms
        self.is_available: bool = True
        self._data = {
            1: {"id": 1, "title": "The Analytical Engine: A Deep Dive",
                "body": "Charles Babbage's vision was centuries ahead..."},
            2: {"id": 2, "title": "Can Machines Think?",
                "body": "The question that launched a thousand papers..."},
        }

    async def get_post(self, blog_id: int) -> MockHttpResponse:
        """'GET http://post-service/api/posts/{blogId}'"""
        await asyncio.sleep(self.latency_ms / 1000.0)
        if not self.is_available:
            # Simulates a 503 Service Unavailable
            return MockHttpResponse(status_code=503, payload={"error": "Service Unavailable"})
        post = self._data.get(blog_id)
        if post is None:
            return MockHttpResponse(status_code=404, payload={"error": "Not Found"})
        return MockHttpResponse(status_code=200, payload=post)


class MockAuthorServiceClient:
    """Simulates `http://author-service/api/authors/{blogId}` (async)."""

    def __init__(self, latency_ms: int = 30) -> None:
        self.latency_ms = latency_ms
        self.is_available: bool = True
        # Keyed by blog_id (the C# book uses authorId = blogId)
        self._data = {
            1: {"id": 1, "name": "Ada Lovelace", "email": "ada@byte-blog.dev"},
            2: {"id": 2, "name": "Alan Turing", "email": "alan@byte-blog.dev"},
        }

    async def get_author(self, blog_id: int) -> MockHttpResponse:
        """'GET http://author-service/api/authors/{blogId}'"""
        await asyncio.sleep(self.latency_ms / 1000.0)
        if not self.is_available:
            return MockHttpResponse(status_code=503, payload={"error": "Service Unavailable"})
        author = self._data.get(blog_id)
        if author is None:
            return MockHttpResponse(status_code=404, payload={"error": "Not Found"})
        return MockHttpResponse(status_code=200, payload=author)


class MockCommentServiceClient:
    """Simulates `http://comment-service/api/comments/{blogId}` (async)."""

    def __init__(self, latency_ms: int = 30) -> None:
        self.latency_ms = latency_ms
        self.is_available: bool = True
        self._data = {
            1: [
                {"id": 1, "author_name": "Grace Hopper",
                 "text": "Lovely write-up on analytical engines!"},
                {"id": 2, "author_name": "Linus Torvalds",
                 "text": "Now do the same for kernel scheduling."},
            ],
            2: [
                {"id": 3, "author_name": "Ada Lovelace",
                 "text": "The Turing Test section is a classic."},
            ],
        }

    async def get_comments(self, blog_id: int) -> MockHttpResponse:
        """'GET http://comment-service/api/comments/{blogId}'"""
        await asyncio.sleep(self.latency_ms / 1000.0)
        if not self.is_available:
            return MockHttpResponse(status_code=503, payload={"error": "Service Unavailable"})
        comments = self._data.get(blog_id, [])
        return MockHttpResponse(status_code=200, payload={"comments": comments})
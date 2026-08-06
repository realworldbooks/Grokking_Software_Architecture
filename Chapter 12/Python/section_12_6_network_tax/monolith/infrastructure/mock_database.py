"""Simulated monolith database."""

import time
from typing import Optional

from ..domain.models import Author, BlogPost, BlogDetailsViewModel, Comment


class DatabaseException(Exception):
    """Simulates a database outage."""


class MockBlogDatabase:
    """An in-memory database with pre-seeded Posts, Authors, and Comments."""

    def __init__(self) -> None:
        self.is_available: bool = True
        self._authors: dict[int, Author] = {
            1: Author(id=1, name="Ada Lovelace", email="ada@byte-blog.dev"),
            2: Author(id=2, name="Alan Turing", email="alan@byte-blog.dev"),
        }
        self._comments: dict[int, list[Comment]] = {
            1: [
                Comment(id=1, post_id=1, author_name="Grace Hopper",
                        text="Lovely write-up on analytical engines!"),
                Comment(id=2, post_id=1, author_name="Linus Torvalds",
                        text="Now do the same for kernel scheduling."),
            ],
            2: [
                Comment(id=3, post_id=2, author_name="Ada Lovelace",
                        text="The Turing Test section is a classic."),
            ],
        }
        self._posts: dict[int, tuple[BlogPost, int]] = {
            1: (BlogPost(
                    id=1,
                    title="The Analytical Engine: A Deep Dive",
                    body="Charles Babbage's vision was centuries ahead..."),
                1),
            2: (BlogPost(
                    id=2,
                    title="Can Machines Think?",
                    body="The question that launched a thousand papers..."),
                2),
        }

    def get_blog_details(self, blog_id: int, simulate_ms: int = 5) -> BlogDetailsViewModel:
        """The monolith's single "SQL JOIN" — one query, zero network."""
        if not self.is_available:
            raise DatabaseException("Database unavailable.")
        time.sleep(simulate_ms / 1000.0)
        post, author_id = self._posts[blog_id]
        post.author = self._authors[author_id]
        post.comments = self._comments.get(blog_id, [])
        return BlogDetailsViewModel(post=post)

    def find_post(self, blog_id: int) -> Optional[BlogPost]:
        """Small helper used by the demo to pre-check the post exists."""
        if blog_id not in self._posts:
            return None
        return self._posts[blog_id][0]

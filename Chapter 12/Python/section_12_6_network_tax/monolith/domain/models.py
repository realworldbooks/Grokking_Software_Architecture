"""Business entities shared by the Monolith demonstration.

DESIGN NOTE:
These are plain data holders (Python dataclasses). In a real monolith
these would live alongside an ORM (like SQLAlchemy or Django), but we
keep them framework-free so the teaching focus stays on the ARCHITECTURE,
not the ORM.
"""

from dataclasses import dataclass, field


@dataclass
class Author:
    """The writer of a blog post.

    In the monolith this row lives in the SAME database as the post,
    so fetching it costs zero extra network round-trips.
    """
    id: int
    name: str
    email: str


@dataclass
class Comment:
    """A reader's response to a blog post.

    In the monolith this row also lives in the SAME database,
    so it arrives in the exact same SQL query as the post itself.
    """
    id: int
    post_id: int
    author_name: str
    text: str


@dataclass
class BlogPost:
    """The main content entity.

    `author` and `comments` are populated by the ORM through SQL JOINs,
    which is the entire point of the monolith: one query, one round trip.
    """
    id: int
    title: str
    body: str
    author: Author = field(default=None)  # type: ignore[assignment]
    comments: list[Comment] = field(default_factory=list)


@dataclass
class BlogDetailsViewModel:
    """The shape returned to the web page.

    The naming mirrors the C# `BlogDetailsViewModel` from Listing 12.1
    so the reader can map the two side by side.
    """
    post: BlogPost
"""The Monolith Blog Endpoint - Listing 12.1."""
import logging
from ..domain.models import BlogDetailsViewModel
from ..infrastructure.mock_database import DatabaseException, MockBlogDatabase

logger = logging.getLogger(__name__)

class NotFoundException(Exception):
    def __init__(self, blog_id: int) -> None:
        super().__init__(f"Blog post with id {blog_id} was not found.")
        self.blog_id = blog_id

class MonolithBlogEndpoint:
    def __init__(self, db: MockBlogDatabase) -> None:
        self._db = db

    def get_blog_details(self, blog_id: int) -> BlogDetailsViewModel:
        try:
            view_model = self._db.get_blog_details(blog_id, simulate_ms=5)
            if view_model.post is None:
                raise NotFoundException(blog_id)
            return view_model
        except DatabaseException as ex:
            logger.error("Database unavailable.")
            raise

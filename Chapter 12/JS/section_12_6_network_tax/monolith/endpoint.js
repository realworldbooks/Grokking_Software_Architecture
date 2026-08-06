import { DatabaseException } from "./mockDatabase.js";

export class NotFoundException extends Error {
  constructor(blogId) { super("Blog post with id " + blogId + " was not found."); this.name = "NotFoundException"; }
}

export class MonolithBlogEndpoint {
  constructor(db) { this._db = db; }

  getBlogDetails(blogId) {
    try {
      return this._db.getBlogDetails(blogId, 5);
    } catch (ex) {
      if (ex instanceof DatabaseException) { console.error("Database unavailable."); throw ex; }
      throw ex;
    }
  }
}

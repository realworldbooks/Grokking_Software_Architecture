export class DatabaseException extends Error {
  constructor(message) { super(message); this.name = "DatabaseException"; }
}

export class MockBlogDatabase {
  constructor() {
    this.isAvailable = true;
    this._authors = {
      1: { id: 1, name: "Ada Lovelace", email: "ada@byte-blog.dev" },
      2: { id: 2, name: "Alan Turing", email: "alan@byte-blog.dev" }
    };
    this._comments = {
      1: [
        { id: 1, authorName: "Grace Hopper", text: "Lovely write-up on analytical engines!" },
        { id: 2, authorName: "Linus Torvalds", text: "Now do the same for kernel scheduling." }
      ],
      2: [{ id: 3, authorName: "Ada Lovelace", text: "The Turing Test section is a classic." }]
    };
    this._posts = {
      1: { post: { id: 1, title: "The Analytical Engine: A Deep Dive", body: "..." }, authorId: 1 },
      2: { post: { id: 2, title: "Can Machines Think?", body: "..." }, authorId: 2 }
    };
  }

  getBlogDetails(blogId, simulateMs = 5) {
    if (!this.isAvailable) throw new DatabaseException("Database unavailable.");
    const { post, authorId } = this._posts[blogId];
    return { ...post, author: this._authors[authorId], comments: this._comments[blogId] || [] };
  }
}

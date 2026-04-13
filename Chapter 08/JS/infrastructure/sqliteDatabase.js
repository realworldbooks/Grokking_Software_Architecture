// infrastructure/sqliteDatabase.js
import Database from 'better-sqlite3';

export class SqliteDatabase {
    constructor() {
        this.db = new Database(':memory:');
        this.db.exec("CREATE TABLE Recipes (id INTEGER, name TEXT, type TEXT)");
    }

    insert(id, name, type) {
        const stmt = this.db.prepare("INSERT INTO Recipes (id, name, type) VALUES (?, ?, ?)");
        stmt.run(id, name, type);
    }

    queryByType(type) {
        const stmt = this.db.prepare("SELECT name FROM Recipes WHERE type = ?");
        return stmt.all(type).map(row => row.name);
    }
}
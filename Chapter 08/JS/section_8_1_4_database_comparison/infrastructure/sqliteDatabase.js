// infrastructure/sqliteDatabase.js
import Database from 'better-sqlite3';

/**
 * ARCHITECTURAL NOTE: THE QUARANTINE ZONE
 * This class belongs in the 'infrastructure' folder because it represents 
 * the "Dirty" Outside World. 
 * * This is a raw ENGINE. It understands technical implementation details 
 * like SQL syntax and memory allocation. Crucially, it knows absolutely 
 * NOTHING about our business rules. 
 * * THE DATABASE (INFRASTRUCTURE LAYER): Strict, organized. Like a filing cabinet.
 */
export class SqliteDatabase {
    constructor() {
        // We use an in-memory SQLite database so it runs instantly without file setup
        this.connection = new Database(':memory:');
        this.connection.exec("CREATE TABLE Recipes (id INTEGER, name TEXT, type TEXT)");
    }

    /**
     * @param {number} id 
     * @param {string} name 
     * @param {string} type 
     */
    insert(id, name, type) {
        const stmt = this.connection.prepare("INSERT INTO Recipes (id, name, type) VALUES (?, ?, ?)");
        stmt.run(id, name, type);
    }

    /**
     * The naive literal search
     * @param {string} name 
     * @returns {string[]}
     */
    queryByName(name) {
        const stmt = this.connection.prepare("SELECT name FROM Recipes WHERE name = ?");
        const rows = stmt.all(name);
        return rows.map(row => row.name);
    }

    /**
     * Exact keyword match required. If you search for "Italian", you find NOTHING.
     * @param {string} type 
     * @returns {string[]}
     */
    queryByType(type) {
        const stmt = this.connection.prepare("SELECT name FROM Recipes WHERE type = ?");
        const rows = stmt.all(type);
        return rows.map(row => row.name);
    }

    /**
     * @param {string} query 
     */
    executeRaw(query) {
        this.connection.exec(query);
    }

    close() {
        this.connection.close();
    }
}
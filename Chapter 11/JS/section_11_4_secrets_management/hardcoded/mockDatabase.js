/**
 * MOCK DATABASE CONNECTION - FOR DEMONSTRATION ONLY
 * 
 * This simulates a database connection to show the hardcoded credentials problem.
 * In reality, this would be pg.connect() or similar.
 */

export class MockDatabaseConnection {
    constructor(host, database, user, password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this._isConnected = false;
    }

    connect() {
        // Simulate connection logic
        if (this.user === 'admin' && this.password === 'Password123!') {
            this._isConnected = true;
            return true;
        } else {
            throw new Error(`Authentication failed for user ${this.user}`);
        }
    }

    executeQuery(query) {
        if (!this._isConnected) {
            throw new Error('Not connected to database');
        }
        
        // Return mock data
        return [
            { order_id: 'ORD-001', customer_id: 'CUST-123', total: 99.99 }
        ];
    }

    close() {
        this._isConnected = false;
    }
}
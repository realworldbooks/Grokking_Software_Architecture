// infrastructure/noSqlSimulator.js
export class NoSqlSimulator {
    constructor() {
        this.collection = [];
    }

    insertOne(document) {
        this.collection.push(document);
    }

    findByTag(tag) {
        return this.collection
            .filter(doc => doc.tags && doc.tags.includes(tag))
            .map(doc => doc.name);
    }
}
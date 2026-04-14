// infrastructure/noSqlSimulator.js

/**
 * THE DOCUMENT WAY (INFRASTRUCTURE LAYER): Fast, loose. Like a messy desk.
 * Simulates MongoDB's document storage using standard JavaScript arrays and objects.
 * * JS ARCHITECTURAL NOTE:
 * Unlike C# or Java, JavaScript is dynamically typed. A JS Object IS a JSON Document.
 * We don't need a special class with a "flexible dictionary" workaround here. 
 * You can just pass any arbitrary object into this simulator, proving why JS 
 * developers historically fell in love with NoSQL (MongoDB): there is zero friction.
 */
export class NoSqlSimulator {
    constructor() {
        /** @type {Object[]} */
        this.collection = [];
    }

    /**
     * @param {Object} document 
     */
    insertOne(document) {
        this.collection.push(document);
    }

    /**
     * The naive literal search
     * @param {string} name 
     * @returns {string[]}
     */
    findByName(name) {
        return this.collection
            .filter(doc => doc.name === name)
            .map(doc => doc.name);
    }

    /**
     * Contains Match: Better, but still relies on exact spelling of the tag.
     * @param {string} tag 
     * @returns {string[]}
     */
    findByTag(tag) {
        return this.collection
            .filter(doc => doc.tags && doc.tags.includes(tag))
            .map(doc => doc.name);
    }
}
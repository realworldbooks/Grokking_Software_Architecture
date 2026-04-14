// infrastructure/vectorDbSimulator.js

/**
 * THE VECTOR WAY (INFRASTRUCTURE LAYER): Math, not Magic.
 * Calculates intent using high-dimensional distance rather than exact spelling.
 */
export class VectorDbSimulator {
    constructor() {
        /** @type {{id: string, vector: number[], name: string}[]} */
        this.vectors = [];
    }

    /**
     * @param {string} id 
     * @param {number[]} vector 
     * @param {string} name 
     */
    upsert(id, vector, name) {
        this.vectors.push({ id, vector, name });
    }

    /**
     * @param {number[]} queryVector 
     * @param {number} topK 
     * @returns {string[]}
     */
    query(queryVector, topK = 1) {
        // Sort the database by the shortest mathematical distance to the user's query
        const sorted = [...this.vectors].sort((a, b) => {
            return this._getDistance(a.vector, queryVector) - this._getDistance(b.vector, queryVector);
        });
        
        return sorted.slice(0, topK).map(v => v.name);
    }

    /**
     * Standard Euclidean Distance: Calculates how far apart the two meanings are
     * @param {number[]} vec1 
     * @param {number[]} vec2 
     * @returns {number}
     */
    _getDistance(vec1, vec2) {
        let sum = 0;
        for (let i = 0; i < vec1.length; i++) {
            sum += Math.pow(vec1[i] - vec2[i], 2);
        }
        return Math.sqrt(sum);
    }
}
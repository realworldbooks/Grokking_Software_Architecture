// infrastructure/vectorDbSimulator.js
export class VectorDbSimulator {
    constructor() {
        this.vectors = [];
    }

    upsert(id, vector, metadata) {
        this.vectors.push({ id, vector, metadata });
    }

    query(queryVector, topK = 1) {
        const getDistance = (vec1, vec2) => {
            let sum = 0;
            for (let i = 0; i < vec1.length; i++) {
                sum += Math.pow(vec1[i] - vec2[i], 2);
            }
            return Math.sqrt(sum);
        };

        this.vectors.sort((a, b) => getDistance(a.vector, queryVector) - getDistance(b.vector, queryVector));
        return this.vectors.slice(0, topK).map(v => v.metadata.name);
    }
}
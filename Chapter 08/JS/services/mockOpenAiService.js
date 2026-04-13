// services/mockOpenAiService.js
export class MockOpenAiService {
    static createEmbedding(text) {
        if (text === "Lasagna") return [0.9, 0.9, 0.1];
        if (text === "Comfort Food") return [0.8, 0.9, 0.2];
        if (text === "Healthy Salad") return [0.1, 0.1, 0.9];
        return [0.0, 0.0, 0.0];
    }
}
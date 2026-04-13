// Services/MockOpenAiService.cs
namespace Chapter08.DatabaseCode.Services
{
    /// <summary>
    /// EXTERNAL API SIMULATOR: 
    /// In a real application, you don't manually calculate vectors. You call an 
    /// AI service (OpenAI, Cohere, etc.) that returns the vector array for you.
    /// 
    /// This simulates converting concepts into a 3-dimensional array: 
    /// [Food_Score, Comfort_Score, Health_Score]
    /// </summary>
    public static class MockOpenAiService
    {
        public static double[] CreateEmbedding(string text)
        {
            // Perfect spelling baseline
            if (text == "Lasagna") return new[] { 0.9, 0.9, 0.1 };
            
            // Categorical relationship: Pasta is mathematically very close to Lasagna
            if (text == "Pasta") return new[] { 0.85, 0.85, 0.15 };
            
            // Typo tolerance
            if (text == "Lasnga") return new[] { 0.88, 0.85, 0.12 };
            
            // Other distinct concepts
            if (text == "Healthy Salad") return new[] { 0.1, 0.1, 0.9 };
            if (text == "Comfort Food") return new[] { 0.8, 0.9, 0.2 };
            if (text == "Mac & Cheese") return new[] { 0.85, 0.95, 0.05 };
            if (text == "Beef Stew") return new[] { 0.75, 0.85, 0.3 };
            
            return new[] { 0.0, 0.0, 0.0 };
        }
    }
}
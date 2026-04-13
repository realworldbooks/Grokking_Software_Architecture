// Infrastructure/VectorDbSimulator.cs
using System;
using System.Collections.Generic;
using System.Linq;

namespace Chapter08.DatabaseCode.Infrastructure
{
    /// <summary>
    /// THE VECTOR WAY (INFRASTRUCTURE LAYER): Math, not Magic.
    /// Calculates intent using high-dimensional distance rather than exact spelling.
    /// </summary>
    public class VectorDbSimulator
    {
        private readonly List<VectorRecord> _vectors = new List<VectorRecord>();

        public void Upsert(string id, double[] vector, string name)
        {
            _vectors.Add(new VectorRecord { Id = id, Vector = vector, Name = name });
        }

        public List<string> Query(double[] queryVector, int topK = 1)
        {
            // Sort the database by the shortest mathematical distance to the user's query
            return _vectors
                .OrderBy(v => GetDistance(v.Vector, queryVector))
                .Take(topK)
                .Select(v => v.Name)
                .ToList();
        }

        private double GetDistance(double[] vec1, double[] vec2)
        {
            // Standard Euclidean Distance: Calculates how far apart the two meanings are
            double sum = 0;
            for (int i = 0; i < vec1.Length; i++)
            {
                sum += Math.Pow(vec1[i] - vec2[i], 2);
            }
            return Math.Sqrt(sum);
        }
    }
}
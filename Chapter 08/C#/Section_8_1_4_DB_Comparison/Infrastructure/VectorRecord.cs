// Infrastructure/VectorRecord.cs
using System;

namespace Chapter08.DatabaseCode.Infrastructure
{
    public class VectorRecord
    {
        public string Id { get; set; } = string.Empty;
        public double[] Vector { get; set; } = Array.Empty<double>();
        public string Name { get; set; } = string.Empty;
    }
}
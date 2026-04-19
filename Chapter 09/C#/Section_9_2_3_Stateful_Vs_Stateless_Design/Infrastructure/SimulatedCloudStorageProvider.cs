using System.Collections.Generic;
using System.IO;
using Chapter09.Section1_StatefulVsStateless.Services;

namespace Chapter09.Section1_StatefulVsStateless.Infrastructure;

/// <summary>
/// THE STATELESS ADAPTER (Cloud Native Design):
/// 
/// TEACHING NOTE:
/// This adapter moves the "state" out of the individual web servers and into 
/// a centralized, external location (simulating Amazon S3). 
/// 
/// Because our UserService depends entirely on the IStorageProvider interface, 
/// we can swap from the fragile LocalStorageProvider to this robust Cloud provider 
/// without changing a single line of our business logic!
/// </summary>
public class SimulatedCloudStorageProvider : IStorageProvider
{
    // The static dictionary acts as our "External Cloud". 
    // Even if we instantiate 100 web servers, they all point to this exact same data store.
    private static readonly Dictionary<string, string> _s3BucketSimulator = new();
    private readonly string _bucketName;

    public SimulatedCloudStorageProvider(string bucketName)
    {
        _bucketName = bucketName;
    }

    public void Save(string fileName, string data)
    {
        string s3Key = $"{_bucketName}/{fileName}";
        _s3BucketSimulator[s3Key] = data;
    }

    public string Get(string fileName)
    {
        string s3Key = $"{_bucketName}/{fileName}";
        if (!_s3BucketSimulator.ContainsKey(s3Key))
        {
            throw new FileNotFoundException($"404 Not Found in S3 Bucket: {s3Key}");
        }
        return _s3BucketSimulator[s3Key];
    }
}
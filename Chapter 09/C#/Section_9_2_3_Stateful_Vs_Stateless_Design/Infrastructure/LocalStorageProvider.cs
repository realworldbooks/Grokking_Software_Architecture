using System.IO;
using Chapter09.StatefulVsStateless.Services;

namespace Chapter09.StatefulVsStateless.Infrastructure;

/// <summary>
/// THE STATEFUL ADAPTER (The Fragile Monolith):
/// 
/// ARCHITECTURAL NOTE:
/// This implements our IStorageProvider by writing directly to the server's local disk.
/// WARNING: This is an anti-pattern for modern cloud applications!
/// If we put a Load Balancer in front of two servers using this code, Server B will 
/// have no idea about the files saved on Server A's local C: drive. 
/// If Server A crashes, that user's profile picture is gone forever.
/// </summary>
public class LocalStorageProvider : IStorageProvider
{
    private readonly string _drivePath;

    public LocalStorageProvider(string serverName)
    {
        _drivePath = $"{serverName}_drive";
        Directory.CreateDirectory(_drivePath);
    }

    public void Save(string fileName, string data)
    {
        File.WriteAllText(Path.Combine(_drivePath, fileName), data);
    }

    public string Get(string fileName)
    {
        string filePath = Path.Combine(_drivePath, fileName);
        if (!File.Exists(filePath))
        {
            throw new FileNotFoundException($"File not found on local drive: {filePath}");
        }
        return File.ReadAllText(filePath);
    }
}
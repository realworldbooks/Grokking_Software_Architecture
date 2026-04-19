namespace Chapter09.Section1_StatefulVsStateless.Services;

/// <summary>
/// THE PORT (Dependency Inversion Principle):
/// 
/// TEACHING NOTE:
/// This interface is the "Airlock" between our core business logic and the outside world.
/// Notice that it says absolutely nothing about local hard drives, file paths, 
/// or Amazon S3 buckets. It only defines WHAT the application needs (Save and Get), 
/// leaving the HOW to the infrastructure layer. 
/// </summary>
public interface IStorageProvider
{
    void Save(string fileName, string data);
    string Get(string fileName);
}
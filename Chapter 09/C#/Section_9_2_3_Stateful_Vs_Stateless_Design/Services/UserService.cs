namespace Chapter09.Section1_StatefulVsStateless.Services;

/// <summary>
/// THE APPLICATION LAYER (Core Business Logic):
/// 
/// TEACHING NOTE:
/// Look at the 'using' statements at the top of this file. There is no 'using System.IO;' 
/// and no AWS SDK imports. This class is blissfully ignorant of where the files actually go.
/// By keeping infrastructure out of our domain, this class becomes incredibly easy to test 
/// and completely cloud-agnostic.
/// </summary>
public class UserService
{
    private readonly IStorageProvider _storage;

    // We inject the dependency (The Adapter) through the constructor.
    // The UserService doesn't build its own database; it asks for one to be provided.
    public UserService(IStorageProvider storage)
    {
        _storage = storage;
    }

    public void UploadAvatar(string userId, string imageData)
    {
        string fileName = $"profile_{userId}.jpg";
        _storage.Save(fileName, imageData);
    }

    public string ViewAvatar(string userId)
    {
        string fileName = $"profile_{userId}.jpg";
        return _storage.Get(fileName);
    }
}
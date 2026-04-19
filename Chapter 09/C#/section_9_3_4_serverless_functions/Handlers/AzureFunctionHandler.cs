using System;
using System.IO;
using Chapter09.Section3_ServerlessFunctions.Infrastructure;

namespace Chapter09.Section3_ServerlessFunctions.Handlers;

public class AzureFunctionHandler
{
    /// <summary>
    /// CLOUD 2: AZURE FUNCTIONS (The Declarative App)
    /// 
    /// THE ARCHITECTURAL LESSON: 
    /// Azure uses "Bindings" to abstract away the network plumbing, but it 
    /// "Owns" your method signature in exchange for that convenience.
    /// 
    /// TEACHING NOTE:
    /// Look at the 'Stream myBlob' parameter. Unlike the AWS example, we don't 
    /// receive a JSON event; we receive the actual file content! Azure's host 
    /// process performed the download for us before our code even started. 
    /// While this is "cleaner," notice the trade-off: this method signature 
    /// is now proprietary to the Azure Functions runtime. The infrastructure 
    /// has "Leaked" into our method arguments, making it difficult to run 
    /// this code anywhere else without a wrapper.
    /// </summary>
    
    // In real production code, you would see: 
    // [FunctionName("ResizeImage")]
    // public static void Run([BlobTrigger("uploads/{name}")] Stream myBlob...)
    public string Handle(Stream myBlob, string name, MockAzureLogger log)
    {
        // 1. THE CLOUD CONTRACT & DECLARATIVE FETCH: Combined by the platform
        // The data is already present in a Stream object handed to us.
        long fileSize = myBlob.Length;

        log.LogInformation($"      [Azure Function] Stream injected via bindings: {name}");
        log.LogInformation($"      [Azure Function] Azure handled the network fetch automatically.");
        
        // 2. THE LOGIC:
        log.LogInformation($"      [Azure Function] Processing image resize...");

        // 3. THE RESPONSE: Azure allows simple return types that map back to the cloud
        return $"Azure processed {name}";
    }
}
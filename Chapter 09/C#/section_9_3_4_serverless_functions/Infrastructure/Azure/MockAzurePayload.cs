using System;

namespace Chapter09.ServerlessFunctions.Infrastructure.Azure;

/**
 * THE AZURE INFRASTRUCTURE CONTRACT (Blob Metadata):
 * * TEACHING NOTE:
 * While Azure Functions often bind directly to a Stream, they also provide 
 * metadata about the trigger (e.g., the blob's URI, properties, and path). 
 * This represents the "Declarative" data context provided by the platform.
 */
public record MockAzurePayload(
    string Name, 
    long Size, 
    string ContentType, 
    DateTime LastModified
);
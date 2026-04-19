using System;
using System.IO;
using System.Reactive.Linq;

namespace Chapter09.Section3_ServerlessFunctions.Infrastructure.Azure;

/// <summary>
/// THE AZURE INFRASTRUCTURE CONTRACT (Reactive Blob Stream):
/// 
/// DESIGN NOTE:
/// Azure Functions use "Declarative Bindings." In this reactive model, we treat 
/// the pre-downloaded file stream as the source of our Observable.
/// 
/// ARCHITECTURAL CRITIQUE:
/// This represents a "Signature Leak." While Azure offers high convenience by 
/// handling the network plumbing (the download) for you, it dictates that your 
/// handler must accept a 'Stream' or 'byte[]'. You have traded control for 
/// convenience, but your method signature is now proprietary to the Azure runtime. 
/// You cannot move this reactive pipe to a standard ASP.NET Core project without 
/// a translation layer.
/// </summary>
public static class AzureStreamFactory
{
    public static IObservable<Stream> CreateBlobStream(byte[] data)
    {
        return Observable.Return(new MemoryStream(data));
    }
}
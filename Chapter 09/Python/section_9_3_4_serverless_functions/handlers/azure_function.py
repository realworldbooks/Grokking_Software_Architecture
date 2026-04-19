from section_9_3_4_serverless_functions.infrastructure.mock_payloads import MockAzureBlob

def azure_handler(myblob: MockAzureBlob) -> str:
    """
    CLOUD 2: AZURE FUNCTIONS (The Declarative App)
    
    THE ARCHITECTURAL LESSON: 
    Azure uses "Bindings" to hide the network calls, but it "Owns" your 
    function's signature in exchange.
    
    TEACHING NOTE:
    Compare this to the AWS example. We didn't have to parse a JSON event or 
    manually download the file. Azure's infrastructure did the work for us 
    and handed us 'myblob' (the file itself). While this is more convenient, 
    notice the new problem: our function signature is now proprietary to Azure. 
    You can't just take this method and run it inside a standard Flask app. 
    The platform has "Leaked" into our method arguments.
    """
    
    # 1. THE CLOUD CONTRACT & FETCH: Combined by the platform
    # The data is already present in memory when the function starts.
    file_name = myblob.name
    file_size = myblob.length

    print(f"      [Azure Function] Blob injected via bindings: {file_name}")
    print(f"      [Azure Function] Azure already performed the download for us.")
    
    # 2. THE LOGIC:
    print(f"      [Azure Function] Processing image resize...")

    # 3. THE RESPONSE: Mapped back to the cloud via return value
    return f"Azure processed {file_name}"
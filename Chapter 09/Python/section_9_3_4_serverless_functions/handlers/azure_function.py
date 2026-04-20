from rx import operators as ops

class AzureFunctionHandler:
    """
    CLOUD 2: AZURE FUNCTIONS (The Declarative App - Reactive)
    
    THE ARCHITECTURAL LESSON: 
    Azure uses "Bindings" to abstract away the network plumbing, but it 
    "Owns" your method signature in exchange for that convenience.
    
    TEACHING NOTE:
    Unlike the AWS example, we don't receive a JSON event metadata object; 
    we receive the actual file content stream! Azure's host process performed 
    the download for us before our code even started. While this is "cleaner," 
    the trade-off is a "Signature Leak"—this method signature is now 
    proprietary to the Azure Functions runtime.
    """
    def handle_stream(self, blob_stream_obs):
        """
        Processes the declaratively injected blob stream.
        """
        return blob_stream_obs.pipe(
            ops.map(lambda blob: self._process_azure_logic(blob))
        )

    def _process_azure_logic(self, blob):
        # 1. THE CLOUD CONTRACT & DECLARATIVE FETCH: Combined by the platform.
        # The data is already present in a stream object handed to us.
        file_name = getattr(blob, 'name', 'rx_azure.png')
        
        print(f"      [Azure Function] Blob stream injected via bindings.")
        print(f"      [Azure Function] Azure already performed the download.")
        
        # 2. THE LOGIC:
        print(f"      [Azure Function] Processing image resize...")

        # 3. THE RESPONSE: Azure allows simple return types that map to the cloud.
        return f"Azure reactive processed {file_name}"
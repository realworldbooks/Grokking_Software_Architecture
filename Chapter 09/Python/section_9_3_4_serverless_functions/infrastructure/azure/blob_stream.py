from rx import of
import io

class AzureStreamFactory:
    """
    THE AZURE INFRASTRUCTURE CONTRACT (Reactive Blob Stream):
    
    DESIGN NOTE:
    Azure Functions utilize "Declarative Bindings." Instead of receiving a JSON 
    event dictionary (like AWS), Azure's infrastructure pre-fetches the data 
    and hands you a file-like object or stream.
    
    ARCHITECTURAL CRITIQUE:
    This is a "Signature Leak." While Azure is more convenient than AWS (it 
    handles the network fetch for you), it dictates the signature of your 
    reactive pipeline. Your stream is now initialized with a 'BytesIO' or 
    'InputStream' object defined by the Azure runtime. You have traded 
    imperative control for declarative convenience, but your code is now 
    proprietary to the Azure host process. You cannot easily move this 
    pipeline to a standard FastAPI app without a translation layer.
    """
    @staticmethod
    def create_blob_stream(data: bytes):
        """
        Wraps raw binary data into a reactive stream, simulating 
        Azure's binding injection.
        """
        # In a reactive model, the file content itself is the source of the stream
        return of(io.BytesIO(data))
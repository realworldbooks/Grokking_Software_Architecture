class UserService:
    """
    THE APPLICATION LAYER (Core Business Logic):
    
    TEACHING NOTE:
    Look at the imports at the top of this file. There are no 'os' or 'boto3' imports. 
    This class is blissfully ignorant of where the files actually go. 
    By keeping infrastructure out of our domain, this class becomes incredibly easy 
    to test and completely cloud-agnostic.
    """
    
    def __init__(self, storage_provider):
        # We inject the dependency (The Adapter) through the constructor.
        # Python uses "duck typing" here: as long as the provider object passed in 
        # has 'save' and 'get' methods, this service will work perfectly!
        self._storage = storage_provider

    def upload_avatar(self, user_id: str, image_data: str) -> None:
        file_name = f"profile_{user_id}.jpg"
        self._storage.save(file_name, image_data)

    def view_avatar(self, user_id: str) -> str:
        file_name = f"profile_{user_id}.jpg"
        return self._storage.get(file_name)
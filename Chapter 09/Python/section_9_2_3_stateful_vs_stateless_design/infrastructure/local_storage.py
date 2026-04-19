import os

class LocalStorage:
    """
    THE STATEFUL ADAPTER (The Fragile Monolith):
    
    TEACHING NOTE:
    This fulfills our storage contract by writing directly to the server's local disk.
    WARNING: This is an anti-pattern for modern cloud applications!
    If we put a Load Balancer in front of two servers using this code, Server B will 
    have no idea about the files saved on Server A's local drive. 
    If Server A crashes, that user's profile picture is gone forever.
    """
    
    def __init__(self, server_name: str):
        self.drive_path = f"{server_name}_drive"
        os.makedirs(self.drive_path, exist_ok=True)

    def save(self, file_name: str, data: str) -> None:
        with open(f"{self.drive_path}/{file_name}", 'w') as f:
            f.write(data)

    def get(self, file_name: str) -> str:
        file_path = f"{self.drive_path}/{file_name}"
        if not os.path.exists(file_path):
            raise FileNotFoundError(f"File not found on local drive: {file_path}")
            
        with open(file_path, 'r') as f:
            return f.read()
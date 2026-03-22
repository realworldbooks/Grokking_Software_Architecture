from dataclasses import dataclass

"""
THE DATA TRANSFER OBJECT (DTO).
* ARCHITECTURE NOTE: Using a Python dataclass is the ideal way to implement 
a DTO. It provides a lightweight, immutable-friendly structure that 
acts as a stable contract between the service and its consumers.
"""
@dataclass
class UserReportData:
    name: str
    email: str
    total_spent: float
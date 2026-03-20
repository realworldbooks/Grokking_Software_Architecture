from dataclasses import dataclass

@dataclass
class UserReportData:
    name: str
    email: str
    total_spent: float
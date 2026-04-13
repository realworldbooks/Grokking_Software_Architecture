# infrastructure/databases.py
import sqlite3
import math
from dataclasses import dataclass, field
from typing import List

"""
ARCHITECTURAL NOTE: THE QUARANTINE ZONE
This entire module belongs in the 'infrastructure' folder because it represents 
the "Dirty" Outside World. 

These classes are raw ENGINES. They understand technical implementation details 
like SQL syntax, memory allocation, and Euclidean mathematics. Crucially, they 
know absolutely NOTHING about our business rules. 

By keeping them here, we ensure our core business logic never accidentally 
relies on a specific database technology.
"""

# --- RELATIONAL (SQL) ---
class SqliteDatabase:
    """
    THE DATABASE (INFRASTRUCTURE LAYER): Strict, organized. Like a filing cabinet.
    """
    def __init__(self):
        # We use an in-memory SQLite database so it runs instantly without file setup
        self._connection = sqlite3.connect(':memory:')
        self._connection.execute("CREATE TABLE Recipes (id INTEGER, name TEXT, type TEXT)")

    def insert(self, recipe_id: int, name: str, recipe_type: str) -> None:
        self._connection.execute(
            "INSERT INTO Recipes (id, name, type) VALUES (?, ?, ?)", 
            (recipe_id, name, recipe_type)
        )

    # The naive literal search
    def query_by_name(self, recipe_name: str) -> List[str]:
        cursor = self._connection.execute(
            "SELECT name FROM Recipes WHERE name = ?", 
            (recipe_name,)
        )
        return [row[0] for row in cursor.fetchall()]

    def query_by_type(self, recipe_type: str) -> List[str]:
        # Exact keyword match required. If you search for "Italian", you find NOTHING.
        cursor = self._connection.execute(
            "SELECT name FROM Recipes WHERE type = ?", 
            (recipe_type,)
        )
        return [row[0] for row in cursor.fetchall()]

    def close(self) -> None:
        self._connection.close()


# --- DOCUMENT (NoSQL) ---
@dataclass
class NoSqlDocument:
    name: str = ""
    tags: List[str] = field(default_factory=list)

class NoSqlSimulator:
    """
    THE DOCUMENT WAY (INFRASTRUCTURE LAYER): Fast, loose. Like a messy desk.
    Simulates MongoDB's document storage using standard Python lists and objects.
    """
    def __init__(self):
        self._collection: List[NoSqlDocument] = []

    def insert_one(self, document: NoSqlDocument) -> None:
        self._collection.append(document)

    # The naive literal search
    def find_by_name(self, name: str) -> List[str]:
        return [doc.name for doc in self._collection if doc.name == name]

    def find_by_tag(self, tag: str) -> List[str]:
        # Contains Match: Better, but still relies on exact spelling of the tag.
        return [doc.name for doc in self._collection if tag in doc.tags]


# --- VECTOR (AI) ---
@dataclass
class VectorRecord:
    id: str = ""
    vector: List[float] = field(default_factory=list)
    name: str = ""

class VectorDbSimulator:
    """
    THE VECTOR WAY (INFRASTRUCTURE LAYER): Math, not Magic.
    Calculates intent using high-dimensional distance rather than exact spelling.
    """
    def __init__(self):
        self._vectors: List[VectorRecord] = []

    def upsert(self, record_id: str, vector: List[float], name: str) -> None:
        self._vectors.append(VectorRecord(id=record_id, vector=vector, name=name))

    def query(self, query_vector: List[float], top_k: int = 1) -> List[str]:
        # Sort the database by the shortest mathematical distance to the user's query
        sorted_vectors = sorted(
            self._vectors, 
            key=lambda v: self._get_distance(v.vector, query_vector)
        )
        return [v.name for v in sorted_vectors[:top_k]]

    def _get_distance(self, vec1: List[float], vec2: List[float]) -> float:
        # Standard Euclidean Distance: Calculates how far apart the two meanings are
        total_sum = 0.0
        for i in range(len(vec1)):
            total_sum += math.pow(vec1[i] - vec2[i], 2)
        return math.sqrt(total_sum)
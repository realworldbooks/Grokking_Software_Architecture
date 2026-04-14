# models.py
from sqlalchemy.orm import declarative_base
from sqlalchemy import Column, Integer, String, Boolean

Base = declarative_base()

class User(Base):
    """
    THE DOMAIN MODEL (Declarative Mapping):
    
    TEACHING NOTE:
    In the old days, you had to write a 'CREATE TABLE' SQL string by hand. 
    With an ORM, you simply define a native class. The ORM reads this class 
    and automatically generates the perfect database schema for you. 
    
    If you add a new property here, the ORM knows how to update the database.
    """
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    first_name = Column(String)
    last_name = Column(String)
    age = Column(Integer)
    is_active = Column(Boolean)
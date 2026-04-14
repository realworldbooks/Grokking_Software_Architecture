# demo.py
from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker
from .models import Base, User

class Demo2:
    """
    THE SCENARIO REPOSITORY:
    Demonstrates the architectural shift from Imperative (Raw SQL) 
    to Declarative (ORM) querying.
    """

    @staticmethod
    def run_query_comparison() -> None:
        print("\n=== Section 8.2.1: Declarative Querying (Raw SQL vs ORM) ===")
        print("SCENARIO: The database contains 4 users. We need to find all active users over age 21, sorted alphabetically.")

        # 1. Setup the in-memory database and ORM Session
        engine = create_engine('sqlite:///:memory:', echo=False)
        Base.metadata.create_all(engine) # The ORM automatically builds the tables!
        Session = sessionmaker(bind=engine)
        session = Session()

        # 2. Seed the database with test data
        session.add_all([
            User(first_name="Alice", last_name="Smith", age=25, is_active=True),
            User(first_name="Bob", last_name="Jones", age=19, is_active=True),      # Fails: Too young
            User(first_name="Charlie", last_name="Brown", age=30, is_active=False), # Fails: Inactive
            User(first_name="Diana", last_name="Prince", age=28, is_active=True)
        ])
        session.commit()
        print("SETUP: 4 Users inserted into the database.\\n")


        # --- THE OLD WAY (IMPERATIVE) ---
        print("--- 1. The Old Way (Imperative / Raw SQL) ---")
        raw_sql = "SELECT * FROM users WHERE age > 21 AND is_active = 1 ORDER BY last_name;"
        print(f"  [Action] Executing Raw String: {raw_sql}")
        
        # We use SQLAlchemy's text() wrapper just to execute the raw string safely
        raw_result = session.execute(text(raw_sql))
        
        found_raw = []
        for row in raw_result:
            # We have to access rows by exact column string names, which is brittle
            found_raw.append(f"{row.first_name} {row.last_name}")
            
        print(f"  [Result] Found: [{', '.join(found_raw)}]")
        print("  [Lesson] The burden is on you. If you mistyped 'is_active' as 'isActive' inside that string,")
        print("           your code would compile perfectly, but crash in production.\\n")


        # --- THE MODERN WAY (DECLARATIVE) ---
        print("--- 2. The Modern Way (Declarative / ORM) ---")
        print("  [Action] Building a query object using native Python syntax...")
        
        # This is Listing 8.5 from the textbook!
        users = session.query(User).filter(User.age > 21, User.is_active == True).order_by(User.last_name).all()

        found_orm = []
        for u in users:
            # We access data using strongly-typed object properties
            found_orm.append(f"{u.first_name} {u.last_name}")

        print(f"  [Result] Found: [{', '.join(found_orm)}]")
        print("  [Lesson] The ORM translates your Python code into SQL safely behind the scenes.")
        print("           If you rename 'User.age' to 'User.years_old', your IDE will instantly flag the error.")

        session.close()
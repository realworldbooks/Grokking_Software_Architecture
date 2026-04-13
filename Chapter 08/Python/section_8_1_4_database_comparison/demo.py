# demo.py
from .infrastructure.databases import SqliteDatabase, NoSqlSimulator, NoSqlDocument, VectorDbSimulator
from .services.mock_openai_service import MockOpenAiService

class Demo:
    """
    THE SCENARIO REPOSITORY:
    This class holds the execution logic for our database comparisons.
    Notice how it no longer contains any interactive UI or menu logic!
    
    ARCHITECTURAL DISCLAIMER (Pedagogy vs. Production):
    As Tommy Norris says in Landman: "You have to know the rules of the game 
    to bend them. And you really have to know them to break them."
    
    In this educational example, we are intentionally breaking the rules. We have 
    placed all scenario executions into this single 'Demo' class to keep the concepts 
    easy to read top-to-bottom without forcing you to jump between multiple files. 
    
    If this were a real-life production application, this would be a violation of 
    the Single Responsibility Principle (SRP). In the real world, we would use the 
    Command Pattern to separate the isolated Scenario classes.
    """

    @staticmethod
    def run_scenario_0_literal_search() -> None:
        """
        SCENARIO 0: THE LITERAL SEARCH (The Naive Baseline)
        
        TEACHING NOTE:
        This demonstrates why traditional databases require so much planning. 
        If you search a SQL database for "Pasta", but the recipe is named "Lasagna", 
        it fails. Traditional DBs match strings, not concepts.
        """
        print("\n=== Example 0: The Literal Search (The Naive Baseline) ===")
        print("SCENARIO: We save 'Lasagna' into our databases.")
        print("THE TASK: The user searches the database for 'Pasta'. Let's see what happens.\n")

        print("--- 1. The SQL Way (Relational) ---")
        sql_db = SqliteDatabase()
        try:
            sql_db.insert(1, "Lasagna", "Unknown") # We don't have a type yet
            
            print("  [Action] Running: SELECT name FROM Recipes WHERE name = 'Pasta'")
            sql_results = sql_db.query_by_name("Pasta")
            print(f"  [Result] Found: {sql_results}")
            print("  [Lesson] SQL fails. It is a literal filing cabinet. 'Pasta' != 'Lasagna'.\n")
        finally:
            sql_db.close()

        print("--- 2. The NoSQL Way (Document) ---")
        nosql_db = NoSqlSimulator()
        nosql_db.insert_one(NoSqlDocument(name="Lasagna")) # No tags yet
        
        print("  [Action] Searching for document where name == 'Pasta'")
        nosql_results = nosql_db.find_by_name("Pasta")
        print(f"  [Result] Found: {nosql_results}")
        print("  [Lesson] NoSQL also fails. It strictly matches keys and values.\n")

        print("--- 3. The Vector Way (AI Embeddings) ---")
        vector_db = VectorDbSimulator()
        vector_db.upsert("r1", MockOpenAiService.create_embedding("Lasagna"), "Lasagna")

        print("  [Action] Converting user search 'Pasta' into math and finding nearest neighbor...")
        query_vector = MockOpenAiService.create_embedding("Pasta")
        vector_results = vector_db.query(query_vector, top_k=1)
        print(f"  [Result] Found: {vector_results}")
        print("  [Lesson] Vector DB wins! It knows that mathematically, Lasagna IS a type of Pasta.")
        print("           It requires zero manual categorization to figure this out.\n")


    @staticmethod
    def run_scenario_1_metadata_workaround() -> None:
        """
        SCENARIO 1: THE METADATA WORKAROUND (Columns & Tags)
        
        TEACHING NOTE:
        This shows how developers historically solved the limitation discovered 
        in Scenario 0. Because SQL/NoSQL are literal, we have to manually attach 
        metadata (columns/tags) to our records to group them.
        """
        print("\n=== Example 1: The Metadata Workaround (Columns & Tags) ===")
        print("SCENARIO: Since traditional databases failed the literal search in Example 0,")
        print("we must now alter our schema to manually 'tag' our data with categories.\n")

        print("--- 1. The SQL Way (Relational) ---")
        sql_db = SqliteDatabase()
        try:
            print("  [Action] Adding a 'type' column: INSERT INTO Recipes VALUES (1, 'Lasagna', 'Pasta')")
            sql_db.insert(1, "Lasagna", "Pasta") # Explicitly categorizing it
            
            print("  [Action] Running: SELECT name FROM Recipes WHERE type = 'Pasta'")
            sql_results = sql_db.query_by_type("Pasta")
            print(f"  [Result] Found: {sql_results}")
            print("  [Lesson] SQL succeeds, but ONLY because we explicitly added a 'type' column.\n")
        finally:
            sql_db.close()

        print("--- 2. The NoSQL Way (Document) ---")
        nosql_db = NoSqlSimulator()
        print("  [Action] Adding a 'tags' array to our JSON object...")
        doc = NoSqlDocument(name="Lasagna", tags=["pasta", "cheese", "italian"])
        nosql_db.insert_one(doc)
        
        print("  [Action] Searching for documents where tags array contains 'pasta'")
        nosql_results = nosql_db.find_by_tag("pasta")
        print(f"  [Result] Found: {nosql_results}")
        print("  [Lesson] NoSQL succeeds, but ONLY because a human remembered to tag it.\n")


    @staticmethod
    def run_scenario_2_fat_finger() -> None:
        """
        SCENARIO 2: THE "FAT FINGER" TEST (Fuzzy Intent)
        
        TEACHING NOTE:
        This demonstrates how AI/Vector databases handle human unpredictability. 
        SQL and NoSQL are rigid—if the spelling isn't perfect, they fail.
        Vector databases understand that the meaning of a typo is mathematically 
        almost identical to the correct spelling.
        """
        print("\n=== Example 2: The 'Fat Finger' Test (Fuzzy Intent) ===")
        print("SCENARIO: All databases contain the perfectly spelled word 'Lasagna'.")
        print("THE TWIST: The user makes a typo and searches for 'Lasnga'. Let's see who survives.\n")

        sql_db = SqliteDatabase()
        nosql_db = NoSqlSimulator()
        vector_db = VectorDbSimulator()

        try:
            sql_db.insert(1, "Lasagna", "Pasta")
            nosql_db.insert_one(NoSqlDocument(name="Lasagna", tags=["pasta", "lasagna"]))
            vector_db.upsert("r1", MockOpenAiService.create_embedding("Lasagna"), "Lasagna")

            typo = "Lasnga"

            print("--- 1. SQL Test ---")
            print(f"  [Action] Executing: SELECT * FROM Recipes WHERE type = '{typo}'")
            sql_results = sql_db.query_by_type(typo)
            print(f"  [Result] Found: {sql_results}")
            print("  [Lesson] SQL requires exact string matches. It fails completely.\n")

            print("--- 2. NoSQL Test ---")
            print(f"  [Action] Searching for tag array containing '{typo}'")
            nosql_results = nosql_db.find_by_tag(typo.lower())
            print(f"  [Result] Found: {nosql_results}")
            print("  [Lesson] NoSQL also requires exact key/value matches. It fails completely.\n")

            print("--- 3. Vector Test ---")
            print(f"  [Action] Converting typo '{typo}' into a mathematical vector...")
            query_vector = MockOpenAiService.create_embedding(typo)
            vector_results = vector_db.query(query_vector, top_k=1)
            print(f"  [Result] Found: {vector_results}")
            print("  [Lesson] The AI converted the typo into math. The distance between 'Lasnga' and 'Lasagna' is tiny!")
            print("           Vector DB wins because it searches for intent, not spelling.\n")

        finally:
            sql_db.close()

    @staticmethod
    def run_scenario_3_schema_agility() -> None:
        """
        SCENARIO 3: THE "BUSINESS REQUIREMENTS CHANGED" TEST
        
        TEACHING NOTE:
        This scenario demonstrates the fundamental difference between Schema-On-Write 
        (SQL) and Schema-On-Read (NoSQL). When business requirements change rapidly, 
        relational databases require structural migrations, while document databases 
        simply absorb the new data shapes.
        """
        print("\n=== Example 3: The Schema Agility Test (Business Pivot) ===")
        print("SCENARIO: The business has been running smoothly with our Recipe tables.")
        print("THE TWIST: The PM kicks down the door: 'We must track Dietary Allergens immediately!'\n")

        sql_db = SqliteDatabase()
        nosql_db = NoSqlSimulator()

        try:
            print("--- 1. The SQL Way (Relational) ---")
            try:
                # ARCHITECTURAL NOTE: SQL enforces a strict schema. The 'Recipes' table 
                # was created with only (id, name, type). Trying to shove an 'allergens' 
                # list into it will cause a fatal crash.
                print("  [Action] Attempting to INSERT a row with a brand new 'allergens' column...")
                sql_db._connection.execute(
                    "INSERT INTO Recipes (id, name, type, allergens) VALUES (?, ?, ?, ?)", 
                    (2, "Peanut Butter Jelly", "Sandwich", "Peanuts, Gluten")
                )
            except Exception as e:
                print(f"  [Result] FATAL CRASH -> {e}")
                print("  [Lesson] SQL uses 'Schema-On-Write'. We must halt production, lock the database,")
                print("           and run an ALTER TABLE migration before we can save this new data shape.\n")

            print("--- 2. The NoSQL Way (Document) ---")
            # ARCHITECTURAL NOTE: NoSQL does not care about schemas. We can dynamically 
            # add a brand new 'allergens' property to our Document object on the fly, 
            # and the database will happily ingest it.
            print("  [Action] Dynamically adding a new 'allergens' JSON array to the object in code...")
            new_document_shape = NoSqlDocument(name="Peanut Butter Jelly", tags=["lunch"])
            new_document_shape.allergens = ["Peanuts", "Gluten"]
            
            print("  [Action] Sending the new, unapproved JSON shape to the NoSQL database...")
            nosql_db.insert_one(new_document_shape)
            
            saved_doc = nosql_db._collection[0]
            print(f"  [Result] SUCCESS! Saved {saved_doc.name} with allergens: {getattr(saved_doc, 'allergens', None)}")
            print("  [Lesson] NoSQL uses 'Schema-On-Read'. It happily accepts any JSON shape you throw at it.")
            print("           It allows rapid prototyping without locking the database.\n")

        finally:
            sql_db.close()

    @staticmethod
    def run_scenario_4_aggregation() -> None:
        """
        SCENARIO 4: THE "GIVE ME THE MATH" TEST (Aggregations)
        
        TEACHING NOTE:
        This scenario demonstrates why SQL is still the undisputed king of reporting, 
        analytics, and financial math. 
        """
        print("\n=== Example 4: The Aggregation Test (Give Me The Math) ===")
        print("SCENARIO: We have 2 Pasta recipes and 1 Soup recipe in the database.")
        print("THE TASK: The Analytics team asks: 'Generate a report of recipe counts grouped by type.'\n")

        sql_db = SqliteDatabase()
        nosql_db = NoSqlSimulator()

        try:
            sql_db.insert(1, "Lasagna", "Pasta")
            sql_db.insert(2, "Spaghetti", "Pasta")
            sql_db.insert(3, "Chicken Noodle", "Soup")

            nosql_db.insert_one(NoSqlDocument(name="Lasagna", tags=["pasta"]))
            nosql_db.insert_one(NoSqlDocument(name="Spaghetti", tags=["pasta"]))
            nosql_db.insert_one(NoSqlDocument(name="Chicken Noodle", tags=["soup"]))

            print("--- 1. The SQL Way (Relational) ---")
            # ARCHITECTURAL NOTE: SQL handles complex grouping and counting directly 
            # on the database server.
            print("  [Action] Running: SELECT type, COUNT(*) FROM Recipes GROUP BY type")
            cursor = sql_db._connection.execute("SELECT type, COUNT(*) FROM Recipes GROUP BY type")
            sql_results = {row[0]: row[1] for row in cursor.fetchall()}
            
            print(f"  [Result] {sql_results}")
            print("  [Lesson] SQL handles complex grouping and counting directly on the database server.")
            print("           It uses highly optimized C-level code. It is incredibly fast.\n")

            print("--- 2. The NoSQL Way (Document) ---")
            # ARCHITECTURAL NOTE: To do this in NoSQL, we either have to pull ALL the 
            # documents into memory or write a complex Aggregation Pipeline.
            print("  [Action] Application pulling ALL documents into memory and running a manual loop...")
            nosql_results = {}
            for doc in nosql_db._collection:
                main_tag = doc.tags[0] if doc.tags else "unknown"
                nosql_results[main_tag] = nosql_results.get(main_tag, 0) + 1
                
            print(f"  [Result] {nosql_results}")
            print("  [Lesson] NoSQL requires manual counting in code, or extremely complex 'Aggregation Pipelines'.")
            print("           It is not naturally built for relational reporting.\n")

            print("--- 3. The Vector Way (AI Embeddings) ---")
            print("  [Result] FAILED")
            print("  [Lesson] Vector databases calculate semantic distance. They literally cannot do arithmetic,")
            print("           grouping, or counting. Don't ask an AI to do a calculator's job.\n")

        finally:
            sql_db.close()

    @staticmethod
    def run_scenario_5_hybrid_search() -> None:
        """
        SCENARIO 5: THE HYBRID ENTERPRISE SEARCH (The Holy Grail)
        
        TEACHING NOTE:
        This scenario demonstrates how modern enterprise architecture actually works. 
        We use the Vector DB for the "Semantic Brain" and NoSQL/SQL for the "Hard Logic".
        """
        print("\n=== Example 5: The Hybrid Search (The Holy Grail) ===")
        print("SCENARIO: User searches for 'Comfort Food', but applies a hard filter: 'Under 30 mins'.")
        print("THE ARCHITECTURE: We use Vector for the 'Brain' and NoSQL for the 'Math'.\n")

        nosql_db = NoSqlSimulator()
        vector_db = VectorDbSimulator()

        # Lasagna: 90 minutes
        lasagna_doc = NoSqlDocument(name="Lasagna", tags=["pasta", "dinner"])
        lasagna_doc.prep_time = 90 
        nosql_db.insert_one(lasagna_doc)
        vector_db.upsert("r1", MockOpenAiService.create_embedding("Lasagna"), "Lasagna")

        # Beef Stew: 120 minutes
        stew_doc = NoSqlDocument(name="Beef Stew", tags=["soup", "dinner"])
        stew_doc.prep_time = 120
        nosql_db.insert_one(stew_doc)
        vector_db.upsert("r2", MockOpenAiService.create_embedding("Beef Stew"), "Beef Stew")

        # Mac & Cheese: 20 minutes
        mac_doc = NoSqlDocument(name="Mac & Cheese", tags=["pasta", "quick"])
        mac_doc.prep_time = 20
        nosql_db.insert_one(mac_doc)
        vector_db.upsert("r3", MockOpenAiService.create_embedding("Mac & Cheese"), "Mac & Cheese")

        print("--- Step 1: The Semantic Brain (Vector DB) ---")
        print("  [Action] Asking AI to find top 3 dishes that mathematically mean 'Comfort Food'...")
        query_vector = MockOpenAiService.create_embedding("Comfort Food")
        semantic_results = vector_db.query(query_vector, top_k=3)
        print(f"  [Result] AI suggests: {semantic_results}")
        print("  [Notice] The AI returned all three. It understands 'comfort', but is completely blind to 'prep_time'.\n")

        print("--- Step 2: The Hard Logic (NoSQL DB) ---")
        print("  [Action] Taking the AI's suggestions and filtering them through our NoSQL Database to enforce the < 30m rule...")
        final_results = []
        for doc in nosql_db._collection:
            if doc.name in semantic_results and getattr(doc, 'prep_time', 999) < 30:
                final_results.append(doc.name)

        print(f"  [Result] Final Filtered List: {final_results}")
        print("  [Lesson] We used AI to understand the *vibe* of the user's request, and traditional databases")
        print("           to enforce the *business logic*. This is how modern enterprise architecture is built!\n")
// Demo.cs
using System;
using System.Collections.Generic;
using System.Linq;
using Chapter08.DatabaseCode.Infrastructure;
using Chapter08.DatabaseCode.Services;

namespace Chapter08.DatabaseCode
{
    /// <summary>
    /// THE SCENARIO REPOSITORY:
    /// This class holds the execution logic for our database comparisons.
    /// Notice how it no longer contains any interactive UI or menu logic!
    /// 
    /// ARCHITECTURAL DISCLAIMER (Pedagogy vs. Production):
    /// As Tommy Norris says in Landman: "You have to know the rules of the game 
    /// to bend them. And you really have to know them to break them."
    /// 
    /// In this educational example, we are intentionally breaking the rules. We have 
    /// placed all scenario executions into this single 'Demo' class to keep the concepts 
    /// easy to read top-to-bottom without forcing you to jump between multiple files. 
    /// 
    /// If this were a real-life production application, this would be a violation of 
    /// the Single Responsibility Principle (SRP). In the real world, we would use the 
    /// Command Pattern to separate the isolated Scenario classes.
    /// </summary>
    public class Demo
    {
        /// <summary>
        /// SCENARIO 0: THE LITERAL SEARCH (The Naive Baseline)
        /// 
        /// TEACHING NOTE:
        /// This demonstrates why traditional databases require so much planning. 
        /// If you search a SQL database for "Pasta", but the recipe is named "Lasagna", 
        /// it fails. Traditional DBs match strings, not concepts.
        /// </summary>
        public static void RunScenario0LiteralSearch()
        {
            Console.WriteLine("\n=== Example 0: The Literal Search (The Naive Baseline) ===");
            Console.WriteLine("SCENARIO: We save 'Lasagna' into our databases.");
            Console.WriteLine("THE TASK: The user searches the database for 'Pasta'. Let's see what happens.\n");

            Console.WriteLine("--- 1. The SQL Way (Relational) ---");
            using (var sqlDb = new SqliteDatabase())
            {
                sqlDb.Insert(1, "Lasagna", "Unknown"); // We don't have a type yet
                
                Console.WriteLine("  [Action] Running: SELECT name FROM Recipes WHERE name = 'Pasta'");
                var sqlResults = string.Join(", ", sqlDb.QueryByName("Pasta"));
                Console.WriteLine($"  [Result] Found: [{sqlResults}]");
                Console.WriteLine("  [Lesson] SQL fails. It is a literal filing cabinet. 'Pasta' != 'Lasagna'.\n");
            }

            Console.WriteLine("--- 2. The NoSQL Way (Document) ---");
            var nosqlDb = new NoSqlSimulator();
            nosqlDb.InsertOne(new NoSqlDocument { Name = "Lasagna" }); // No tags yet
            
            Console.WriteLine("  [Action] Searching for document where name == 'Pasta'");
            var nosqlResults = string.Join(", ", nosqlDb.FindByName("Pasta"));
            Console.WriteLine($"  [Result] Found: [{nosqlResults}]");
            Console.WriteLine("  [Lesson] NoSQL also fails. It strictly matches keys and values.\n");

            Console.WriteLine("--- 3. The Vector Way (AI Embeddings) ---");
            var vectorDb = new VectorDbSimulator();
            vectorDb.Upsert("r1", MockOpenAiService.CreateEmbedding("Lasagna"), "Lasagna");

            Console.WriteLine("  [Action] Converting user search 'Pasta' into math and finding nearest neighbor...");
            var queryVector = MockOpenAiService.CreateEmbedding("Pasta");
            var vectorResults = string.Join(", ", vectorDb.Query(queryVector, topK: 1));
            Console.WriteLine($"  [Result] Found: [{vectorResults}]");
            Console.WriteLine("  [Lesson] Vector DB wins! It knows that mathematically, Lasagna IS a type of Pasta.");
            Console.WriteLine("           It requires zero manual categorization to figure this out.\n");
        }

        /// <summary>
        /// SCENARIO 1: THE METADATA WORKAROUND (Columns & Tags)
        /// 
        /// TEACHING NOTE:
        /// This shows how developers historically solved the limitation discovered 
        /// in Scenario 0. Because SQL/NoSQL are literal, we have to manually attach 
        /// metadata (columns/tags) to our records to group them.
        /// </summary>
        public static void RunScenario1MetadataWorkaround()
        {
            Console.WriteLine("\n=== Example 1: The Metadata Workaround (Columns & Tags) ===");
            Console.WriteLine("SCENARIO: Since traditional databases failed the literal search in Example 0,");
            Console.WriteLine("we must now alter our schema to manually 'tag' our data with categories.\n");

            Console.WriteLine("--- 1. The SQL Way (Relational) ---");
            using (var sqlDb = new SqliteDatabase())
            {
                Console.WriteLine("  [Action] Adding a 'type' column: INSERT INTO Recipes VALUES (1, 'Lasagna', 'Pasta')");
                sqlDb.Insert(1, "Lasagna", "Pasta"); // Explicitly categorizing it
                
                Console.WriteLine("  [Action] Running: SELECT name FROM Recipes WHERE type = 'Pasta'");
                var sqlResults = string.Join(", ", sqlDb.QueryByType("Pasta"));
                Console.WriteLine($"  [Result] Found: [{sqlResults}]");
                Console.WriteLine("  [Lesson] SQL succeeds, but ONLY because we explicitly added a 'type' column.\n");
            }

            Console.WriteLine("--- 2. The NoSQL Way (Document) ---");
            var nosqlDb = new NoSqlSimulator();
            Console.WriteLine("  [Action] Adding a 'tags' array to our JSON object...");
            nosqlDb.InsertOne(new NoSqlDocument 
            { 
                Name = "Lasagna", 
                Tags = new List<string> { "pasta", "cheese", "italian" } 
            });
            
            Console.WriteLine("  [Action] Searching for documents where tags array contains 'pasta'");
            var nosqlResults = string.Join(", ", nosqlDb.FindByTag("pasta"));
            Console.WriteLine($"  [Result] Found: [{nosqlResults}]");
            Console.WriteLine("  [Lesson] NoSQL succeeds, but ONLY because a human remembered to tag it.\n");
        }

        /// <summary>
        /// SCENARIO 2: THE "FAT FINGER" TEST (Fuzzy Intent)
        /// 
        /// TEACHING NOTE:
        /// This demonstrates how AI/Vector databases handle human unpredictability. 
        /// SQL and NoSQL are rigid—if the spelling isn't perfect, they fail.
        /// Vector databases understand that the meaning of a typo is mathematically 
        /// almost identical to the correct spelling.
        /// </summary>
        public static void RunScenario2FatFinger()
        {
            Console.WriteLine("\n=== Example 2: The 'Fat Finger' Test (Fuzzy Intent) ===");
            Console.WriteLine("SCENARIO: All databases contain the perfectly spelled word 'Lasagna'.");
            Console.WriteLine("THE TWIST: The user makes a typo and searches for 'Lasnga'. Let's see who survives.\n");

            using var sqlDb = new SqliteDatabase();
            var nosqlDb = new NoSqlSimulator();
            var vectorDb = new VectorDbSimulator();

            sqlDb.Insert(1, "Lasagna", "Pasta");
            nosqlDb.InsertOne(new NoSqlDocument { Name = "Lasagna", Tags = new List<string> { "pasta", "lasagna" } });
            vectorDb.Upsert("r1", MockOpenAiService.CreateEmbedding("Lasagna"), "Lasagna");

            string typo = "Lasnga";

            Console.WriteLine("--- 1. SQL Test ---");
            Console.WriteLine($"  [Action] Executing: SELECT * FROM Recipes WHERE type = '{typo}'");
            var sqlResults = string.Join(", ", sqlDb.QueryByType(typo));
            Console.WriteLine($"  [Result] Found: [{sqlResults}]");
            Console.WriteLine("  [Lesson] SQL requires exact string matches. It fails completely.\n");

            Console.WriteLine("--- 2. NoSQL Test ---");
            Console.WriteLine($"  [Action] Searching for tag array containing '{typo}'");
            var nosqlResults = string.Join(", ", nosqlDb.FindByTag(typo.ToLower()));
            Console.WriteLine($"  [Result] Found: [{nosqlResults}]");
            Console.WriteLine("  [Lesson] NoSQL also requires exact key/value matches. It fails completely.\n");

            Console.WriteLine("--- 3. Vector Test ---");
            Console.WriteLine($"  [Action] Converting typo '{typo}' into a mathematical vector...");
            var queryVector = MockOpenAiService.CreateEmbedding(typo);
            var vectorResults = string.Join(", ", vectorDb.Query(queryVector, topK: 1));
            Console.WriteLine($"  [Result] Found: [{vectorResults}]");
            Console.WriteLine("  [Lesson] The AI converted the typo into math. The distance between 'Lasnga' and 'Lasagna' is tiny!");
            Console.WriteLine("           Vector DB wins because it searches for intent, not spelling.\n");
        }

        /// <summary>
        /// SCENARIO 3: THE "BUSINESS REQUIREMENTS CHANGED" TEST
        /// 
        /// TEACHING NOTE:
        /// This scenario demonstrates the fundamental difference between Schema-On-Write 
        /// (SQL) and Schema-On-Read (NoSQL). When business requirements change rapidly, 
        /// relational databases require structural migrations, while document databases 
        /// simply absorb the new data shapes.
        /// </summary>
        public static void RunScenario3SchemaAgility()
        {
            Console.WriteLine("\n=== Example 3: The Schema Agility Test (Business Pivot) ===");
            Console.WriteLine("SCENARIO: The business has been running smoothly with our Recipe tables.");
            Console.WriteLine("THE TWIST: The PM kicks down the door: 'We must track Dietary Allergens immediately!'\n");

            using var sqlDb = new SqliteDatabase();
            var nosqlDb = new NoSqlSimulator();

            Console.WriteLine("--- 1. The SQL Way (Relational) ---");
            try
            {
                // ARCHITECTURAL NOTE: SQL enforces a strict schema. The 'Recipes' table 
                // was created with only (id, name, type). Trying to shove an 'allergens' 
                // list into it will cause a fatal crash.
                Console.WriteLine("  [Action] Attempting to INSERT a row with a brand new 'allergens' column...");
                sqlDb.ExecuteRaw("INSERT INTO Recipes (id, name, type, allergens) VALUES (2, 'Peanut Butter Jelly', 'Sandwich', 'Peanuts, Gluten')");
            }
            catch (Exception e)
            {
                Console.WriteLine($"  [Result] FATAL CRASH -> {e.Message}");
                Console.WriteLine("  [Lesson] SQL uses 'Schema-On-Write'. We must halt production, lock the database,");
                Console.WriteLine("           and run an ALTER TABLE migration before we can save this new data shape.\n");
            }

            Console.WriteLine("--- 2. The NoSQL Way (Document) ---");
            // ARCHITECTURAL NOTE: NoSQL does not care about schemas. We can dynamically 
            // add a brand new 'allergens' property to our Document's flexible data on the fly, 
            // and the database will happily ingest it.
            Console.WriteLine("  [Action] Dynamically adding a new 'allergens' JSON array to the object in code...");
            
            var newDocumentShape = new NoSqlDocument 
            { 
                Name = "Peanut Butter Jelly", 
                Tags = new List<string> { "lunch" } 
            };
            // Adding a property that doesn't strictly exist in a relational schema
            newDocumentShape.FlexibleData["allergens"] = new List<string> { "Peanuts", "Gluten" };
            
            Console.WriteLine("  [Action] Sending the new, unapproved JSON shape to the NoSQL database...");
            nosqlDb.InsertOne(newDocumentShape);
            
            var savedDoc = nosqlDb.Collection[0];
            var allergensList = savedDoc.FlexibleData.ContainsKey("allergens") 
                ? string.Join(", ", (List<string>)savedDoc.FlexibleData["allergens"]) 
                : "None";

            Console.WriteLine($"  [Result] SUCCESS! Saved {savedDoc.Name} with allergens: [{allergensList}]");
            Console.WriteLine("  [Lesson] NoSQL uses 'Schema-On-Read'. It happily accepts any JSON shape you throw at it.");
            Console.WriteLine("           It allows rapid prototyping without locking the database.\n");
        }

        /// <summary>
        /// SCENARIO 4: THE "GIVE ME THE MATH" TEST (Aggregations)
        /// 
        /// TEACHING NOTE:
        /// This scenario demonstrates why SQL is still the undisputed king of reporting, 
        /// analytics, and financial math. 
        /// </summary>
        public static void RunScenario4Aggregation()
        {
            Console.WriteLine("\n=== Example 4: The Aggregation Test (Give Me The Math) ===");
            Console.WriteLine("SCENARIO: We have 2 Pasta recipes and 1 Soup recipe in the database.");
            Console.WriteLine("THE TASK: The Analytics team asks: 'Generate a report of recipe counts grouped by type.'\n");

            var nosqlDb = new NoSqlSimulator();

            using (var sqlDb = new SqliteDatabase())
            {
                sqlDb.Insert(1, "Lasagna", "Pasta");
                sqlDb.Insert(2, "Spaghetti", "Pasta");
                sqlDb.Insert(3, "Chicken Noodle", "Soup");

                nosqlDb.InsertOne(new NoSqlDocument { Name = "Lasagna", Tags = new List<string> { "pasta" } });
                nosqlDb.InsertOne(new NoSqlDocument { Name = "Spaghetti", Tags = new List<string> { "pasta" } });
                nosqlDb.InsertOne(new NoSqlDocument { Name = "Chicken Noodle", Tags = new List<string> { "soup" } });

                Console.WriteLine("--- 1. The SQL Way (Relational) ---");
                // ARCHITECTURAL NOTE: SQL handles complex grouping and counting directly 
                // on the database server.
                Console.WriteLine("  [Action] Running: SELECT type, COUNT(*) FROM Recipes GROUP BY type");
                
                // For demonstration, we simply format the results as string output
                Console.WriteLine("  [Result] {'Pasta': 2, 'Soup': 1}");
                Console.WriteLine("  [Lesson] SQL handles complex grouping and counting directly on the database server.");
                Console.WriteLine("           It uses highly optimized C-level code. It is incredibly fast.\n");
            }

            Console.WriteLine("--- 2. The NoSQL Way (Document) ---");
            // ARCHITECTURAL NOTE: To do this in NoSQL, we either have to pull ALL the 
            // documents into memory or write a complex Aggregation Pipeline.
            Console.WriteLine("  [Action] Application pulling ALL documents into memory and running a manual LINQ loop...");
            
            var nosqlResults = nosqlDb.Collection
                .GroupBy(doc => doc.Tags.FirstOrDefault() ?? "unknown")
                .ToDictionary(g => g.Key, g => g.Count());
            
            var formattedNoSqlResults = "{" + string.Join(", ", nosqlResults.Select(kv => $"'{kv.Key}': {kv.Value}")) + "}";
            
            Console.WriteLine($"  [Result] {formattedNoSqlResults}");
            Console.WriteLine("  [Lesson] NoSQL requires manual counting in code, or extremely complex 'Aggregation Pipelines'.");
            Console.WriteLine("           It is not naturally built for relational reporting.\n");

            Console.WriteLine("--- 3. The Vector Way (AI Embeddings) ---");
            Console.WriteLine("  [Result] FAILED");
            Console.WriteLine("  [Lesson] Vector databases calculate semantic distance. They literally cannot do arithmetic,");
            Console.WriteLine("           grouping, or counting. Don't ask an AI to do a calculator's job.\n");
        }

        /// <summary>
        /// SCENARIO 5: THE HYBRID ENTERPRISE SEARCH (The Holy Grail)
        /// 
        /// TEACHING NOTE:
        /// This scenario demonstrates how modern enterprise architecture actually works. 
        /// We use the Vector DB for the "Semantic Brain" and NoSQL/SQL for the "Hard Logic".
        /// </summary>
        public static void RunScenario5HybridSearch()
        {
            Console.WriteLine("\n=== Example 5: The Hybrid Search (The Holy Grail) ===");
            Console.WriteLine("SCENARIO: User searches for 'Comfort Food', but applies a hard filter: 'Under 30 mins'.");
            Console.WriteLine("THE ARCHITECTURE: We use Vector for the 'Brain' and NoSQL for the 'Math'.\n");

            var nosqlDb = new NoSqlSimulator();
            var vectorDb = new VectorDbSimulator();

            // Lasagna: 90 minutes
            var lasagnaDoc = new NoSqlDocument { Name = "Lasagna", Tags = new List<string> { "pasta", "dinner" } };
            lasagnaDoc.FlexibleData["prep_time"] = 90;
            nosqlDb.InsertOne(lasagnaDoc);
            vectorDb.Upsert("r1", MockOpenAiService.CreateEmbedding("Lasagna"), "Lasagna");

            // Beef Stew: 120 minutes
            var stewDoc = new NoSqlDocument { Name = "Beef Stew", Tags = new List<string> { "soup", "dinner" } };
            stewDoc.FlexibleData["prep_time"] = 120;
            nosqlDb.InsertOne(stewDoc);
            vectorDb.Upsert("r2", MockOpenAiService.CreateEmbedding("Beef Stew"), "Beef Stew");

            // Mac & Cheese: 20 minutes
            var macDoc = new NoSqlDocument { Name = "Mac & Cheese", Tags = new List<string> { "pasta", "quick" } };
            macDoc.FlexibleData["prep_time"] = 20;
            nosqlDb.InsertOne(macDoc);
            vectorDb.Upsert("r3", MockOpenAiService.CreateEmbedding("Mac & Cheese"), "Mac & Cheese");

            Console.WriteLine("--- Step 1: The Semantic Brain (Vector DB) ---");
            Console.WriteLine("  [Action] Asking AI to find top 3 dishes that mathematically mean 'Comfort Food'...");
            var queryVector = MockOpenAiService.CreateEmbedding("Comfort Food");
            var semanticResults = vectorDb.Query(queryVector, topK: 3);
            Console.WriteLine($"  [Result] AI suggests: [{string.Join(", ", semanticResults)}]");
            Console.WriteLine("  [Notice] The AI returned all three. It understands 'comfort', but is completely blind to 'prep_time'.\n");

            Console.WriteLine("--- Step 2: The Hard Logic (NoSQL DB) ---");
            Console.WriteLine("  [Action] Taking the AI's suggestions and filtering them through our NoSQL Database to enforce the < 30m rule...");
            
            var finalResults = new List<string>();
            foreach (var doc in nosqlDb.Collection)
            {
                if (semanticResults.Contains(doc.Name) && 
                    doc.FlexibleData.ContainsKey("prep_time") && 
                    (int)doc.FlexibleData["prep_time"] < 30)
                {
                    finalResults.Add(doc.Name);
                }
            }

            Console.WriteLine($"  [Result] Final Filtered List: [{string.Join(", ", finalResults)}]");
            Console.WriteLine("  [Lesson] We used AI to understand the *vibe* of the user's request, and traditional databases");
            Console.WriteLine("           to enforce the *business logic*. This is how modern enterprise architecture is built!\n");
        }
    }
}
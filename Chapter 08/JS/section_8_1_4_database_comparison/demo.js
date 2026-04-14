// demo.js
import { SqliteDatabase } from './infrastructure/sqliteDatabase.js';
import { NoSqlSimulator } from './infrastructure/noSqlSimulator.js';
import { VectorDbSimulator } from './infrastructure/vectorDbSimulator.js';
import { MockOpenAiService } from './services/mockOpenAiService.js';

/**
 * THE SCENARIO REPOSITORY:
 * This class holds the execution logic for our database comparisons.
 * Notice how it no longer contains any interactive UI or menu logic!
 * * ARCHITECTURAL DISCLAIMER (Pedagogy vs. Production):
 * As Tommy Norris says in Landman: "You have to know the rules of the game 
 * to bend them. And you really have to know them to break them."
 * * In this educational example, we are intentionally breaking the rules. We have 
 * placed all scenario executions into this single 'Demo' class to keep the concepts 
 * easy to read top-to-bottom without forcing you to jump between multiple files. 
 * * If this were a real-life production application, this would be a violation of 
 * the Single Responsibility Principle (SRP). In the real world, we would use the 
 * Command Pattern to separate the isolated Scenario classes.
 */
export class Demo {

    /**
     * SCENARIO 0: THE LITERAL SEARCH (The Naive Baseline)
     * * TEACHING NOTE:
     * This demonstrates why traditional databases require so much planning. 
     * If you search a SQL database for "Pasta", but the recipe is named "Lasagna", 
     * it fails. Traditional DBs match strings, not concepts.
     */
    static runScenario0LiteralSearch() {
        console.log("\n=== Example 0: The Literal Search (The Naive Baseline) ===");
        console.log("SCENARIO: We save 'Lasagna' into our databases.");
        console.log("THE TASK: The user searches the database for 'Pasta'. Let's see what happens.\n");

        console.log("--- 1. The SQL Way (Relational) ---");
        const sqlDb = new SqliteDatabase();
        try {
            sqlDb.insert(1, "Lasagna", "Unknown"); // We don't have a type yet
            
            console.log("  [Action] Running: SELECT name FROM Recipes WHERE name = 'Pasta'");
            const sqlResults = sqlDb.queryByName("Pasta").join(", ");
            console.log(`  [Result] Found: [${sqlResults}]`);
            console.log("  [Lesson] SQL fails. It is a literal filing cabinet. 'Pasta' != 'Lasagna'.\n");
        } finally {
            sqlDb.close();
        }

        console.log("--- 2. The NoSQL Way (Document) ---");
        const nosqlDb = new NoSqlSimulator();
        nosqlDb.insertOne({ name: "Lasagna" }); // No tags yet
        
        console.log("  [Action] Searching for document where name === 'Pasta'");
        const nosqlResults = nosqlDb.findByName("Pasta").join(", ");
        console.log(`  [Result] Found: [${nosqlResults}]`);
        console.log("  [Lesson] NoSQL also fails. It strictly matches keys and values.\n");

        console.log("--- 3. The Vector Way (AI Embeddings) ---");
        const vectorDb = new VectorDbSimulator();
        vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

        console.log("  [Action] Converting user search 'Pasta' into math and finding nearest neighbor...");
        const queryVector = MockOpenAiService.createEmbedding("Pasta");
        const vectorResults = vectorDb.query(queryVector, 1).join(", ");
        console.log(`  [Result] Found: [${vectorResults}]`);
        console.log("  [Lesson] Vector DB wins! It knows that mathematically, Lasagna IS a type of Pasta.");
        console.log("           It requires zero manual categorization to figure this out.\n");
    }

    /**
     * SCENARIO 1: THE METADATA WORKAROUND (Columns & Tags)
     * * TEACHING NOTE:
     * This shows how developers historically solved the limitation discovered 
     * in Scenario 0. Because SQL/NoSQL are literal, we have to manually attach 
     * metadata (columns/tags) to our records to group them.
     */
    static runScenario1MetadataWorkaround() {
        console.log("\n=== Example 1: The Metadata Workaround (Columns & Tags) ===");
        console.log("SCENARIO: Since traditional databases failed the literal search in Example 0,");
        console.log("we must now alter our schema to manually 'tag' our data with categories.\n");

        console.log("--- 1. The SQL Way (Relational) ---");
        const sqlDb = new SqliteDatabase();
        try {
            console.log("  [Action] Adding a 'type' column: INSERT INTO Recipes VALUES (1, 'Lasagna', 'Pasta')");
            sqlDb.insert(1, "Lasagna", "Pasta"); // Explicitly categorizing it
            
            console.log("  [Action] Running: SELECT name FROM Recipes WHERE type = 'Pasta'");
            const sqlResults = sqlDb.queryByType("Pasta").join(", ");
            console.log(`  [Result] Found: [${sqlResults}]`);
            console.log("  [Lesson] SQL succeeds, but ONLY because we explicitly added a 'type' column.\n");
        } finally {
            sqlDb.close();
        }

        console.log("--- 2. The NoSQL Way (Document) ---");
        const nosqlDb = new NoSqlSimulator();
        console.log("  [Action] Adding a 'tags' array to our JSON object...");
        nosqlDb.insertOne({ 
            name: "Lasagna", 
            tags: ["pasta", "cheese", "italian"] 
        });
        
        console.log("  [Action] Searching for documents where tags array contains 'pasta'");
        const nosqlResults = nosqlDb.findByTag("pasta").join(", ");
        console.log(`  [Result] Found: [${nosqlResults}]`);
        console.log("  [Lesson] NoSQL succeeds, but ONLY because a human remembered to tag it.\n");
    }

    /**
     * SCENARIO 2: THE "FAT FINGER" TEST (Fuzzy Intent)
     * * TEACHING NOTE:
     * This demonstrates how AI/Vector databases handle human unpredictability. 
     * SQL and NoSQL are rigid—if the spelling isn't perfect, they fail.
     * Vector databases understand that the meaning of a typo is mathematically 
     * almost identical to the correct spelling.
     */
    static runScenario2FatFinger() {
        console.log("\n=== Example 2: The 'Fat Finger' Test (Fuzzy Intent) ===");
        console.log("SCENARIO: All databases contain the perfectly spelled word 'Lasagna'.");
        console.log("THE TWIST: The user makes a typo and searches for 'Lasnga'. Let's see who survives.\n");

        const sqlDb = new SqliteDatabase();
        const nosqlDb = new NoSqlSimulator();
        const vectorDb = new VectorDbSimulator();

        try {
            sqlDb.insert(1, "Lasagna", "Pasta");
            nosqlDb.insertOne({ name: "Lasagna", tags: ["pasta", "lasagna"] });
            vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

            const typo = "Lasnga";

            console.log("--- 1. SQL Test ---");
            console.log(`  [Action] Executing: SELECT * FROM Recipes WHERE type = '${typo}'`);
            const sqlResults = sqlDb.queryByType(typo).join(", ");
            console.log(`  [Result] Found: [${sqlResults}]`);
            console.log("  [Lesson] SQL requires exact string matches. It fails completely.\n");

            console.log("--- 2. NoSQL Test ---");
            console.log(`  [Action] Searching for tag array containing '${typo}'`);
            const nosqlResults = nosqlDb.findByTag(typo.toLowerCase()).join(", ");
            console.log(`  [Result] Found: [${nosqlResults}]`);
            console.log("  [Lesson] NoSQL also requires exact key/value matches. It fails completely.\n");

            console.log("--- 3. Vector Test ---");
            console.log(`  [Action] Converting typo '${typo}' into a mathematical vector...`);
            const queryVector = MockOpenAiService.createEmbedding(typo);
            const vectorResults = vectorDb.query(queryVector, 1).join(", ");
            console.log(`  [Result] Found: [${vectorResults}]`);
            console.log("  [Lesson] The AI converted the typo into math. The distance between 'Lasnga' and 'Lasagna' is tiny!");
            console.log("           Vector DB wins because it searches for intent, not spelling.\n");
        } finally {
            sqlDb.close();
        }
    }

    /**
     * SCENARIO 3: THE "BUSINESS REQUIREMENTS CHANGED" TEST
     * * TEACHING NOTE:
     * This scenario demonstrates the fundamental difference between Schema-On-Write 
     * (SQL) and Schema-On-Read (NoSQL). When business requirements change rapidly, 
     * relational databases require structural migrations, while document databases 
     * simply absorb the new data shapes.
     */
    static runScenario3SchemaAgility() {
        console.log("\n=== Example 3: The Schema Agility Test (Business Pivot) ===");
        console.log("SCENARIO: The business has been running smoothly with our Recipe tables.");
        console.log("THE TWIST: The PM kicks down the door: 'We must track Dietary Allergens immediately!'\n");

        const sqlDb = new SqliteDatabase();
        const nosqlDb = new NoSqlSimulator();

        try {
            console.log("--- 1. The SQL Way (Relational) ---");
            try {
                // ARCHITECTURAL NOTE: SQL enforces a strict schema. The 'Recipes' table 
                // was created with only (id, name, type). Trying to shove an 'allergens' 
                // list into it will cause a fatal crash.
                console.log("  [Action] Attempting to INSERT a row with a brand new 'allergens' column...");
                sqlDb.executeRaw("INSERT INTO Recipes (id, name, type, allergens) VALUES (2, 'Peanut Butter Jelly', 'Sandwich', 'Peanuts, Gluten')");
            } catch (e) {
                console.log(`  [Result] FATAL CRASH -> ${e.message}`);
                console.log("  [Lesson] SQL uses 'Schema-On-Write'. We must halt production, lock the database,");
                console.log("           and run an ALTER TABLE migration before we can save this new data shape.\n");
            }

            console.log("--- 2. The NoSQL Way (Document) ---");
            // ARCHITECTURAL NOTE: NoSQL does not care about schemas. Because JS objects 
            // are entirely dynamic, we just append a new array property on the fly.
            console.log("  [Action] Dynamically adding a new 'allergens' JSON array to the object in code...");
            
            const newDocumentShape = { 
                name: "Peanut Butter Jelly", 
                tags: ["lunch"] 
            };
            
            // Appending a property that doesn't strictly exist in any schema
            newDocumentShape.allergens = ["Peanuts", "Gluten"];
            
            console.log("  [Action] Sending the new, unapproved JSON shape to the NoSQL database...");
            nosqlDb.insertOne(newDocumentShape);
            
            const savedDoc = nosqlDb.collection[0];
            const allergensList = savedDoc.allergens ? savedDoc.allergens.join(", ") : "None";

            console.log(`  [Result] SUCCESS! Saved ${savedDoc.name} with allergens: [${allergensList}]`);
            console.log("  [Lesson] NoSQL uses 'Schema-On-Read'. It happily accepts any JSON shape you throw at it.");
            console.log("           It allows rapid prototyping without locking the database.\n");
        } finally {
            sqlDb.close();
        }
    }

    /**
     * SCENARIO 4: THE "GIVE ME THE MATH" TEST (Aggregations)
     * * TEACHING NOTE:
     * This scenario demonstrates why SQL is still the undisputed king of reporting, 
     * analytics, and financial math. 
     */
    static runScenario4Aggregation() {
        console.log("\n=== Example 4: The Aggregation Test (Give Me The Math) ===");
        console.log("SCENARIO: We have 2 Pasta recipes and 1 Soup recipe in the database.");
        console.log("THE TASK: The Analytics team asks: 'Generate a report of recipe counts grouped by type.'\n");

        const nosqlDb = new NoSqlSimulator();
        const sqlDb = new SqliteDatabase();

        try {
            sqlDb.insert(1, "Lasagna", "Pasta");
            sqlDb.insert(2, "Spaghetti", "Pasta");
            sqlDb.insert(3, "Chicken Noodle", "Soup");

            nosqlDb.insertOne({ name: "Lasagna", tags: ["pasta"] });
            nosqlDb.insertOne({ name: "Spaghetti", tags: ["pasta"] });
            nosqlDb.insertOne({ name: "Chicken Noodle", tags: ["soup"] });

            console.log("--- 1. The SQL Way (Relational) ---");
            // ARCHITECTURAL NOTE: SQL handles complex grouping and counting directly 
            // on the database server.
            console.log("  [Action] Running: SELECT type, COUNT(*) FROM Recipes GROUP BY type");
            
            console.log("  [Result] { 'Pasta': 2, 'Soup': 1 }");
            console.log("  [Lesson] SQL handles complex grouping and counting directly on the database server.");
            console.log("           It uses highly optimized C-level code. It is incredibly fast.\n");

            console.log("--- 2. The NoSQL Way (Document) ---");
            // ARCHITECTURAL NOTE: To do this in NoSQL, we either have to pull ALL the 
            // documents into memory or write a complex Aggregation Pipeline.
            console.log("  [Action] Application pulling ALL documents into memory and running a manual Array.reduce()...");
            
            const nosqlResults = nosqlDb.collection.reduce((acc, doc) => {
                const tag = (doc.tags && doc.tags[0]) ? doc.tags[0] : "unknown";
                acc[tag] = (acc[tag] || 0) + 1;
                return acc;
            }, {});
            
            // Format for display
            const formattedNoSqlResults = JSON.stringify(nosqlResults).replace(/"/g, "'");
            
            console.log(`  [Result] ${formattedNoSqlResults}`);
            console.log("  [Lesson] NoSQL requires manual counting in code, or extremely complex 'Aggregation Pipelines'.");
            console.log("           It is not naturally built for relational reporting.\n");

            console.log("--- 3. The Vector Way (AI Embeddings) ---");
            console.log("  [Result] FAILED");
            console.log("  [Lesson] Vector databases calculate semantic distance. They literally cannot do arithmetic,");
            console.log("           grouping, or counting. Don't ask an AI to do a calculator's job.\n");
        } finally {
            sqlDb.close();
        }
    }

    /**
     * SCENARIO 5: THE HYBRID ENTERPRISE SEARCH (The Holy Grail)
     * * TEACHING NOTE:
     * This scenario demonstrates how modern enterprise architecture actually works. 
     * We use the Vector DB for the "Semantic Brain" and NoSQL/SQL for the "Hard Logic".
     */
    static runScenario5HybridSearch() {
        console.log("\n=== Example 5: The Hybrid Search (The Holy Grail) ===");
        console.log("SCENARIO: User searches for 'Comfort Food', but applies a hard filter: 'Under 30 mins'.");
        console.log("THE ARCHITECTURE: We use Vector for the 'Brain' and NoSQL for the 'Math'.\n");

        const nosqlDb = new NoSqlSimulator();
        const vectorDb = new VectorDbSimulator();

        // Lasagna: 90 minutes
        nosqlDb.insertOne({ name: "Lasagna", tags: ["pasta", "dinner"], prep_time: 90 });
        vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

        // Beef Stew: 120 minutes
        nosqlDb.insertOne({ name: "Beef Stew", tags: ["soup", "dinner"], prep_time: 120 });
        vectorDb.upsert("r2", MockOpenAiService.createEmbedding("Beef Stew"), "Beef Stew");

        // Mac & Cheese: 20 minutes
        nosqlDb.insertOne({ name: "Mac & Cheese", tags: ["pasta", "quick"], prep_time: 20 });
        vectorDb.upsert("r3", MockOpenAiService.createEmbedding("Mac & Cheese"), "Mac & Cheese");

        console.log("--- Step 1: The Semantic Brain (Vector DB) ---");
        console.log("  [Action] Asking AI to find top 3 dishes that mathematically mean 'Comfort Food'...");
        const queryVector = MockOpenAiService.createEmbedding("Comfort Food");
        const semanticResults = vectorDb.query(queryVector, 3);
        console.log(`  [Result] AI suggests: [${semanticResults.join(", ")}]`);
        console.log("  [Notice] The AI returned all three. It understands 'comfort', but is completely blind to 'prep_time'.\n");

        console.log("--- Step 2: The Hard Logic (NoSQL DB) ---");
        console.log("  [Action] Taking the AI's suggestions and filtering them through our NoSQL Database to enforce the < 30m rule...");
        
        const finalResults = nosqlDb.collection
            .filter(doc => semanticResults.includes(doc.name) && doc.prep_time < 30)
            .map(doc => doc.name);

        console.log(`  [Result] Final Filtered List: [${finalResults.join(", ")}]`);
        console.log("  [Lesson] We used AI to understand the *vibe* of the user's request, and traditional databases");
        console.log("           to enforce the *business logic*. This is how modern enterprise architecture is built!\n");
    }
}
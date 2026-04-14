package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison;

import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.infrastructure.*;
import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison.services.MockOpenAiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class Demo {

    /**
     * SCENARIO 0: THE LITERAL SEARCH (The Naive Baseline)
     * * TEACHING NOTE:
     * This demonstrates why traditional databases require so much planning. 
     * If you search a SQL database for "Pasta", but the recipe is named "Lasagna", 
     * it fails. Traditional DBs match strings, not concepts.
     */
    public static void runScenario0LiteralSearch() {
        System.out.println("\n=== Example 0: The Literal Search (The Naive Baseline) ===");
        System.out.println("SCENARIO: We save 'Lasagna' into our databases.");
        System.out.println("THE TASK: The user searches the database for 'Pasta'. Let's see what happens.\n");

        System.out.println("--- 1. The SQL Way (Relational) ---");
        // JAVA NOTE: The 'try-with-resources' block ensures the database is safely closed.
        try (SqliteDatabase sqlDb = new SqliteDatabase()) {
            sqlDb.insert(1, "Lasagna", "Unknown"); // We don't have a type yet
            
            System.out.println("  [Action] Running: SELECT name FROM Recipes WHERE name = 'Pasta'");
            String sqlResults = String.join(", ", sqlDb.queryByName("Pasta"));
            System.out.println("  [Result] Found: [" + sqlResults + "]");
            System.out.println("  [Lesson] SQL fails. It is a literal filing cabinet. 'Pasta' != 'Lasagna'.\n");
        }

        System.out.println("--- 2. The NoSQL Way (Document) ---");
        NoSqlSimulator nosqlDb = new NoSqlSimulator();
        NoSqlDocument doc = new NoSqlDocument();
        doc.name = "Lasagna";
        nosqlDb.insertOne(doc); // No tags yet
        
        System.out.println("  [Action] Searching for document where name == 'Pasta'");
        String nosqlResults = String.join(", ", nosqlDb.findByName("Pasta"));
        System.out.println("  [Result] Found: [" + nosqlResults + "]");
        System.out.println("  [Lesson] NoSQL also fails. It strictly matches keys and values.\n");

        System.out.println("--- 3. The Vector Way (AI Embeddings) ---");
        VectorDbSimulator vectorDb = new VectorDbSimulator();
        vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

        System.out.println("  [Action] Converting user search 'Pasta' into math and finding nearest neighbor...");
        double[] queryVector = MockOpenAiService.createEmbedding("Pasta");
        String vectorResults = String.join(", ", vectorDb.query(queryVector, 1));
        System.out.println("  [Result] Found: [" + vectorResults + "]");
        System.out.println("  [Lesson] Vector DB wins! It knows that mathematically, Lasagna IS a type of Pasta.");
        System.out.println("           It requires zero manual categorization to figure this out.\n");
    }

    /**
     * SCENARIO 1: THE METADATA WORKAROUND (Columns & Tags)
     * * TEACHING NOTE:
     * This shows how developers historically solved the limitation discovered 
     * in Scenario 0. Because SQL/NoSQL are literal, we have to manually attach 
     * metadata (columns/tags) to our records to group them.
     */
    public static void runScenario1MetadataWorkaround() {
        System.out.println("\n=== Example 1: The Metadata Workaround (Columns & Tags) ===");
        System.out.println("SCENARIO: Since traditional databases failed the literal search in Example 0,");
        System.out.println("we must now alter our schema to manually 'tag' our data with categories.\n");

        System.out.println("--- 1. The SQL Way (Relational) ---");
        try (SqliteDatabase sqlDb = new SqliteDatabase()) {
            System.out.println("  [Action] Adding a 'type' column: INSERT INTO Recipes VALUES (1, 'Lasagna', 'Pasta')");
            sqlDb.insert(1, "Lasagna", "Pasta"); // Explicitly categorizing it
            
            System.out.println("  [Action] Running: SELECT name FROM Recipes WHERE type = 'Pasta'");
            String sqlResults = String.join(", ", sqlDb.queryByType("Pasta"));
            System.out.println("  [Result] Found: [" + sqlResults + "]");
            System.out.println("  [Lesson] SQL succeeds, but ONLY because we explicitly added a 'type' column.\n");
        }

        System.out.println("--- 2. The NoSQL Way (Document) ---");
        NoSqlSimulator nosqlDb = new NoSqlSimulator();
        System.out.println("  [Action] Adding a 'tags' array to our JSON object...");
        NoSqlDocument doc = new NoSqlDocument();
        doc.name = "Lasagna";
        doc.tags.addAll(Arrays.asList("pasta", "cheese", "italian"));
        nosqlDb.insertOne(doc);
        
        System.out.println("  [Action] Searching for documents where tags array contains 'pasta'");
        String nosqlResults = String.join(", ", nosqlDb.findByTag("pasta"));
        System.out.println("  [Result] Found: [" + nosqlResults + "]");
        System.out.println("  [Lesson] NoSQL succeeds, but ONLY because a human remembered to tag it.\n");
    }

    /**
     * SCENARIO 2: THE "FAT FINGER" TEST (Fuzzy Intent)
     * * TEACHING NOTE:
     * This demonstrates how AI/Vector databases handle human unpredictability. 
     * SQL and NoSQL are rigid—if the spelling isn't perfect, they fail.
     * Vector databases understand that the meaning of a typo is mathematically 
     * almost identical to the correct spelling.
     */
    public static void runScenario2FatFinger() {
        System.out.println("\n=== Example 2: The 'Fat Finger' Test (Fuzzy Intent) ===");
        System.out.println("SCENARIO: All databases contain the perfectly spelled word 'Lasagna'.");
        System.out.println("THE TWIST: The user makes a typo and searches for 'Lasnga'. Let's see who survives.\n");

        try (SqliteDatabase sqlDb = new SqliteDatabase()) {
            NoSqlSimulator nosqlDb = new NoSqlSimulator();
            VectorDbSimulator vectorDb = new VectorDbSimulator();

            sqlDb.insert(1, "Lasagna", "Pasta");
            
            NoSqlDocument doc = new NoSqlDocument();
            doc.name = "Lasagna";
            doc.tags.addAll(Arrays.asList("pasta", "lasagna"));
            nosqlDb.insertOne(doc);
            
            vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

            String typo = "Lasnga";

            System.out.println("--- 1. SQL Test ---");
            System.out.println("  [Action] Executing: SELECT * FROM Recipes WHERE type = '" + typo + "'");
            String sqlResults = String.join(", ", sqlDb.queryByType(typo));
            System.out.println("  [Result] Found: [" + sqlResults + "]");
            System.out.println("  [Lesson] SQL requires exact string matches. It fails completely.\n");

            System.out.println("--- 2. NoSQL Test ---");
            System.out.println("  [Action] Searching for tag array containing '" + typo + "'");
            String nosqlResults = String.join(", ", nosqlDb.findByTag(typo.toLowerCase()));
            System.out.println("  [Result] Found: [" + nosqlResults + "]");
            System.out.println("  [Lesson] NoSQL also requires exact key/value matches. It fails completely.\n");

            System.out.println("--- 3. Vector Test ---");
            System.out.println("  [Action] Converting typo '" + typo + "' into a mathematical vector...");
            double[] queryVector = MockOpenAiService.createEmbedding(typo);
            String vectorResults = String.join(", ", vectorDb.query(queryVector, 1));
            System.out.println("  [Result] Found: [" + vectorResults + "]");
            System.out.println("  [Lesson] The AI converted the typo into math. The distance between 'Lasnga' and 'Lasagna' is tiny!");
            System.out.println("           Vector DB wins because it searches for intent, not spelling.\n");
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
    public static void runScenario3SchemaAgility() {
        System.out.println("\n=== Example 3: The Schema Agility Test (Business Pivot) ===");
        System.out.println("SCENARIO: The business has been running smoothly with our Recipe tables.");
        System.out.println("THE TWIST: The PM kicks down the door: 'We must track Dietary Allergens immediately!'\n");

        try (SqliteDatabase sqlDb = new SqliteDatabase()) {
            NoSqlSimulator nosqlDb = new NoSqlSimulator();

            System.out.println("--- 1. The SQL Way (Relational) ---");
            try {
                // ARCHITECTURAL NOTE: SQL enforces a strict schema. The 'Recipes' table 
                // was created with only (id, name, type). Trying to shove an 'allergens' 
                // list into it will cause a fatal crash.
                System.out.println("  [Action] Attempting to INSERT a row with a brand new 'allergens' column...");
                sqlDb.executeRaw("INSERT INTO Recipes (id, name, type, allergens) VALUES (2, 'Peanut Butter Jelly', 'Sandwich', 'Peanuts, Gluten')");
            } catch (Exception e) {
                System.out.println("  [Result] FATAL CRASH -> " + e.getMessage());
                System.out.println("  [Lesson] SQL uses 'Schema-On-Write'. We must halt production, lock the database,");
                System.out.println("           and run an ALTER TABLE migration before we can save this new data shape.\n");
            }

            System.out.println("--- 2. The NoSQL Way (Document) ---");
            // ARCHITECTURAL NOTE: NoSQL does not care about schemas. We can dynamically 
            // add a brand new 'allergens' property to our Document's flexible data map on the fly.
            System.out.println("  [Action] Dynamically adding a new 'allergens' JSON array to the object in code...");
            
            NoSqlDocument newDocumentShape = new NoSqlDocument();
            newDocumentShape.name = "Peanut Butter Jelly";
            newDocumentShape.tags.add("lunch");
            
            // Adding a property that doesn't strictly exist in a relational schema
            newDocumentShape.flexibleData.put("allergens", Arrays.asList("Peanuts", "Gluten"));
            
            System.out.println("  [Action] Sending the new, unapproved JSON shape to the NoSQL database...");
            nosqlDb.insertOne(newDocumentShape);
            
            NoSqlDocument savedDoc = nosqlDb.collection.get(0);
            
            @SuppressWarnings("unchecked")
            List<String> allergens = (List<String>) savedDoc.flexibleData.getOrDefault("allergens", new ArrayList<>());
            String allergensList = allergens.isEmpty() ? "None" : String.join(", ", allergens);

            System.out.println("  [Result] SUCCESS! Saved " + savedDoc.name + " with allergens: [" + allergensList + "]");
            System.out.println("  [Lesson] NoSQL uses 'Schema-On-Read'. It happily accepts any JSON shape you throw at it.");
            System.out.println("           It allows rapid prototyping without locking the database.\n");
        }
    }

    /**
     * SCENARIO 4: THE "GIVE ME THE MATH" TEST (Aggregations)
     * * TEACHING NOTE:
     * This scenario demonstrates why SQL is still the undisputed king of reporting, 
     * analytics, and financial math. 
     */
    public static void runScenario4Aggregation() {
        System.out.println("\n=== Example 4: The Aggregation Test (Give Me The Math) ===");
        System.out.println("SCENARIO: We have 2 Pasta recipes and 1 Soup recipe in the database.");
        System.out.println("THE TASK: The Analytics team asks: 'Generate a report of recipe counts grouped by type.'\n");

        NoSqlSimulator nosqlDb = new NoSqlSimulator();

        try (SqliteDatabase sqlDb = new SqliteDatabase()) {
            sqlDb.insert(1, "Lasagna", "Pasta");
            sqlDb.insert(2, "Spaghetti", "Pasta");
            sqlDb.insert(3, "Chicken Noodle", "Soup");

            NoSqlDocument doc1 = new NoSqlDocument(); doc1.name = "Lasagna"; doc1.tags.add("pasta");
            NoSqlDocument doc2 = new NoSqlDocument(); doc2.name = "Spaghetti"; doc2.tags.add("pasta");
            NoSqlDocument doc3 = new NoSqlDocument(); doc3.name = "Chicken Noodle"; doc3.tags.add("soup");
            
            nosqlDb.insertOne(doc1);
            nosqlDb.insertOne(doc2);
            nosqlDb.insertOne(doc3);

            System.out.println("--- 1. The SQL Way (Relational) ---");
            // ARCHITECTURAL NOTE: SQL handles complex grouping and counting directly 
            // on the database server.
            System.out.println("  [Action] Running: SELECT type, COUNT(*) FROM Recipes GROUP BY type");
            
            // For demonstration, we simply format the results as string output
            System.out.println("  [Result] {'Pasta': 2, 'Soup': 1}");
            System.out.println("  [Lesson] SQL handles complex grouping and counting directly on the database server.");
            System.out.println("           It uses highly optimized C-level code. It is incredibly fast.\n");
        }

        System.out.println("--- 2. The NoSQL Way (Document) ---");
        // ARCHITECTURAL NOTE: To do this in NoSQL, we either have to pull ALL the 
        // documents into memory or write a complex Aggregation Pipeline.
        System.out.println("  [Action] Application pulling ALL documents into memory and running a manual Java Stream loop...");
        
        Map<String, Long> nosqlResults = nosqlDb.collection.stream()
            .collect(Collectors.groupingBy(
                doc -> doc.tags.isEmpty() ? "unknown" : doc.tags.get(0),
                Collectors.counting()
            ));
        
        String formattedNoSqlResults = nosqlResults.entrySet().stream()
            .map(e -> "'" + e.getKey() + "': " + e.getValue())
            .collect(Collectors.joining(", ", "{", "}"));
        
        System.out.println("  [Result] " + formattedNoSqlResults);
        System.out.println("  [Lesson] NoSQL requires manual counting in code, or extremely complex 'Aggregation Pipelines'.");
        System.out.println("           It is not naturally built for relational reporting.\n");

        System.out.println("--- 3. The Vector Way (AI Embeddings) ---");
        System.out.println("  [Result] FAILED");
        System.out.println("  [Lesson] Vector databases calculate semantic distance. They literally cannot do arithmetic,");
        System.out.println("           grouping, or counting. Don't ask an AI to do a calculator's job.\n");
    }

    /**
     * SCENARIO 5: THE HYBRID ENTERPRISE SEARCH (The Holy Grail)
     * * TEACHING NOTE:
     * This scenario demonstrates how modern enterprise architecture actually works. 
     * We use the Vector DB for the "Semantic Brain" and NoSQL/SQL for the "Hard Logic".
     */
    public static void runScenario5HybridSearch() {
        System.out.println("\n=== Example 5: The Hybrid Search (The Holy Grail) ===");
        System.out.println("SCENARIO: User searches for 'Comfort Food', but applies a hard filter: 'Under 30 mins'.");
        System.out.println("THE ARCHITECTURE: We use Vector for the 'Brain' and NoSQL for the 'Math'.\n");

        NoSqlSimulator nosqlDb = new NoSqlSimulator();
        VectorDbSimulator vectorDb = new VectorDbSimulator();

        // Lasagna: 90 minutes
        NoSqlDocument lasagnaDoc = new NoSqlDocument();
        lasagnaDoc.name = "Lasagna"; lasagnaDoc.tags.addAll(Arrays.asList("pasta", "dinner"));
        lasagnaDoc.flexibleData.put("prep_time", 90);
        nosqlDb.insertOne(lasagnaDoc);
        vectorDb.upsert("r1", MockOpenAiService.createEmbedding("Lasagna"), "Lasagna");

        // Beef Stew: 120 minutes
        NoSqlDocument stewDoc = new NoSqlDocument();
        stewDoc.name = "Beef Stew"; stewDoc.tags.addAll(Arrays.asList("soup", "dinner"));
        stewDoc.flexibleData.put("prep_time", 120);
        nosqlDb.insertOne(stewDoc);
        vectorDb.upsert("r2", MockOpenAiService.createEmbedding("Beef Stew"), "Beef Stew");

        // Mac & Cheese: 20 minutes
        NoSqlDocument macDoc = new NoSqlDocument();
        macDoc.name = "Mac & Cheese"; macDoc.tags.addAll(Arrays.asList("pasta", "quick"));
        macDoc.flexibleData.put("prep_time", 20);
        nosqlDb.insertOne(macDoc);
        vectorDb.upsert("r3", MockOpenAiService.createEmbedding("Mac & Cheese"), "Mac & Cheese");

        System.out.println("--- Step 1: The Semantic Brain (Vector DB) ---");
        System.out.println("  [Action] Asking AI to find top 3 dishes that mathematically mean 'Comfort Food'...");
        double[] queryVector = MockOpenAiService.createEmbedding("Comfort Food");
        List<String> semanticResults = vectorDb.query(queryVector, 3);
        System.out.println("  [Result] AI suggests: [" + String.join(", ", semanticResults) + "]");
        System.out.println("  [Notice] The AI returned all three. It understands 'comfort', but is completely blind to 'prep_time'.\n");

        System.out.println("--- Step 2: The Hard Logic (NoSQL DB) ---");
        System.out.println("  [Action] Taking the AI's suggestions and filtering them through our NoSQL Database to enforce the < 30m rule...");
        
        List<String> finalResults = new ArrayList<>();
        for (NoSqlDocument doc : nosqlDb.collection) {
            if (semanticResults.contains(doc.name) && doc.flexibleData.containsKey("prep_time")) {
                int prepTime = (int) doc.flexibleData.get("prep_time");
                if (prepTime < 30) {
                    finalResults.add(doc.name);
                }
            }
        }

        System.out.println("  [Result] Final Filtered List: [" + String.join(", ", finalResults) + "]");
        System.out.println("  [Lesson] We used AI to understand the *vibe* of the user's request, and traditional databases");
        System.out.println("           to enforce the *business logic*. This is how modern enterprise architecture is built!\n");
    }
}
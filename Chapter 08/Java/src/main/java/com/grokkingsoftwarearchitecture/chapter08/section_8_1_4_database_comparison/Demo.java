// Demo.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_1_4_database_comparison;

import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4.infrastructure.MockSqlDatabase;
import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4.infrastructure.NoSqlSimulator;
import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4.infrastructure.VectorDbSimulator;
import com.grokkingsoftwarearchitecture.chapter08.section_8_1_4.services.MockOpenAiService;

import java.util.List;

public class Demo {
    public static void run() {
        System.out.println("=== Section 8.1.4: SQL vs. NoSQL vs. Vector ===\n");

        System.out.println("--- The SQL Way (Relational) ---");
        MockSqlDatabase sqlDb = new MockSqlDatabase();
        sqlDb.insert(1, "Lasagna", "Pasta");
        
        System.out.println("Querying for 'Pasta' -> Found: " + sqlDb.queryByType("Pasta"));
        System.out.println("Querying for 'Comfort Food' -> Found: " + sqlDb.queryByType("Comfort Food") + "\n");

        System.out.println("--- The NoSQL Way (Document) ---");
        NoSqlSimulator nosqlDb = new NoSqlSimulator();
        nosqlDb.insertOne(new NoSqlSimulator.Document("Lasagna", List.of("pasta", "cheese", "italian", "dinner")));
        
        System.out.println("Querying tags for 'pasta' -> Found: " + nosqlDb.findByTag("pasta") + "\n");

        System.out.println("--- The Vector Way (AI Embeddings) ---");
        VectorDbSimulator vectorDb = new VectorDbSimulator();
        
        double[] lasagnaVector = MockOpenAiService.createEmbedding("Lasagna");
        vectorDb.upsert("recipe_1", lasagnaVector, "Lasagna");

        double[] queryVector = MockOpenAiService.createEmbedding("Comfort Food");
        System.out.println("Vector DB found closest meaning -> " + vectorDb.query(queryVector, 1) + "\n");
    }
}
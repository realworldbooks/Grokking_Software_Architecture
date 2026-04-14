// src/main/java/com/grokking/section2_declarativequerying/Demo2.java
package com.grokkingsoftwarearchitecture.chapter08.section_8_2_1_declarative_querying;

import org.hibernate.Session;
import java.util.List;

public class Demo2 {
    public static void runQueryComparison() {
        System.out.println("\n=== Section 8.2.1: Declarative Querying (Raw SQL vs ORM) ===");
        System.out.println("SCENARIO: The database contains 4 users. We need to find all active users over age 21, sorted alphabetically.");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // 1. Seed the database with test data
            session.persist(new User("Alice", "Smith", 25, true));
            session.persist(new User("Bob", "Jones", 19, true));      // Fails: Too young
            session.persist(new User("Charlie", "Brown", 30, false)); // Fails: Inactive
            session.persist(new User("Diana", "Prince", 28, true));
            
            session.getTransaction().commit();
            System.out.println("SETUP: 4 Users inserted into the database.\n");

            
            // --- THE OLD WAY (IMPERATIVE) ---
            System.out.println("--- 1. The Old Way (Imperative / Raw SQL) ---");
            String rawSql = "SELECT * FROM users WHERE age > 21 AND is_active = 1 ORDER BY last_name";
            System.out.println("  [Action] Executing Raw String: " + rawSql);
            
            // Forcing Hibernate to execute a raw SQL string
            List<User> rawUsers = session.createNativeQuery(rawSql, User.class).list();
            
            System.out.print("  [Result] Found: [");
            for (int i = 0; i < rawUsers.size(); i++) {
                System.out.print(rawUsers.get(i).getFirstName() + " " + rawUsers.get(i).getLastName());
                if (i < rawUsers.size() - 1) System.out.print(", ");
            }
            System.out.println("]");
            System.out.println("  [Lesson] The burden is on you. If you mistyped 'is_active' inside that string,");
            System.out.println("           your code would compile perfectly, but crash in production.\n");


            // --- THE MODERN WAY (DECLARATIVE) ---
            System.out.println("--- 2. The Modern Way (Declarative / ORM) ---");
            System.out.println("  [Action] Building a query object using native Hibernate Query Language (HQL)...");
            
            // Declarative approach using HQL (Hibernate Query Language)
            String hql = "FROM User u WHERE u.age > 21 AND u.isActive = true ORDER BY u.lastName";
            List<User> ormUsers = session.createQuery(hql, User.class).list();

            System.out.print("  [Result] Found: [");
            for (int i = 0; i < ormUsers.size(); i++) {
                System.out.print(ormUsers.get(i).getFirstName() + " " + ormUsers.get(i).getLastName());
                if (i < ormUsers.size() - 1) System.out.print(", ");
            }
            System.out.println("]");
            System.out.println("  [Lesson] The ORM translates your HQL into safe SQL behind the scenes.");
            System.out.println("           It maps the results directly into strongly-typed Java objects.");
        }
    }
}
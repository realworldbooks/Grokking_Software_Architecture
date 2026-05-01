// Section2_DeclarativeQuerying/demo2.js
import { PrismaClient } from '@prisma/client';

// Initialize the Prisma Client (This replaces 'new AppDbContext()')
const prisma = new PrismaClient();

export class Demo2 {
    static async runQueryComparison() {
        console.log("\n=== Section 8.2.1: Declarative Querying (Raw SQL vs ORM) ===");
        console.log("SCENARIO: The database contains 4 users. We need to find all active users over age 21, sorted alphabetically.");

        // 1. Setup: Clean the database for the demo
        await prisma.user.deleteMany({});

        // 2. Seed the database with test data
        await prisma.user.createMany({
            data: [
                { firstName: "Alice", lastName: "Smith", age: 25, isActive: true },
                { firstName: "Bob", lastName: "Jones", age: 19, isActive: true },      // Fails: Too young
                { firstName: "Charlie", lastName: "Brown", age: 30, isActive: false }, // Fails: Inactive
                { firstName: "Diana", lastName: "Prince", age: 28, isActive: true }
            ]
        });
        console.log("SETUP: 4 Users inserted into the database.\n");

        // --- THE OLD WAY (IMPERATIVE) ---
        console.log("--- 1. The Old Way (Imperative / Raw SQL) ---");
        const rawSql = `SELECT * FROM users WHERE age > 21 AND is_active = 1 ORDER BY last_name ASC`;
        console.log(`  [Action] Executing Raw String: ${rawSql}`);
        
        // We force Prisma to execute a raw string
        const rawUsers = await prisma.$queryRawUnsafe(rawSql);
        
        const foundRaw = rawUsers.map(u => `${u.first_name} ${u.last_name}`).join(", ");
        console.log(`  [Result] Found: [${foundRaw}]`);
        console.log("  [Lesson] The burden is on you. If you mistyped 'is_active' as 'active' inside that string,");
        console.log("           your code would compile perfectly, but crash in production.\n");

        // --- THE MODERN WAY (DECLARATIVE) ---
        console.log("--- 2. The Modern Way (Declarative / ORM) ---");
        console.log("  [Action] Building a query object using Prisma's declarative syntax...");
        
        // This is the Prisma equivalent of Listing 8.4 from the textbook!
        const ormUsers = await prisma.user.findMany({
            where: {
                age: { gt: 21 },
                isActive: true
            },
            orderBy: {
                lastName: 'asc'
            }
        });

        const foundOrm = ormUsers.map(u => `${u.firstName} ${u.lastName}`).join(", ");
        console.log(`  [Result] Found: [${foundOrm}]`);
        console.log("  [Lesson] The ORM translates your JS object into SQL safely behind the scenes.");
        console.log("           If you rename 'age' to 'yearsOld' in your schema, this code");
        console.log("           will instantly throw a compilation error, saving you from a crash.");
    }
}

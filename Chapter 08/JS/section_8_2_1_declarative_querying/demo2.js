import { PrismaClient } from '@prisma/client';

export class Demo2 {
    static async runQueryComparison() {
        console.log("\n=== Section 8.2.1: Declarative Querying (Raw SQL vs ORM) ===");
        console.log("SCENARIO: The database contains 4 users. We need to find all active users over age 21, sorted alphabetically.");

        const prisma = new PrismaClient();

        try {
            // 1. Setup: Wipe the table clean so the demo can run multiple times
            await prisma.user.deleteMany();

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
            const rawSql = "SELECT * FROM User WHERE age > 21 AND isActive = 1 ORDER BY lastName";
            console.log(`  [Action] Executing Raw String: ${rawSql}`);
            
            // We force Prisma to execute a raw, unverified string
            const rawUsers = await prisma.$queryRawUnsafe(rawSql);
            
            const foundRaw = rawUsers.map(u => `${u.firstName} ${u.lastName}`).join(", ");
            console.log(`  [Result] Found: [${foundRaw}]`);
            console.log("  [Lesson] The burden is on you. If you mistyped 'isActive' as 'active' inside that string,");
            console.log("           your code would compile perfectly, but crash in production.\n");

            // --- THE MODERN WAY (DECLARATIVE) ---
            console.log("--- 2. The Modern Way (Declarative / ORM) ---");
            console.log("  [Action] Building a query object using native JavaScript syntax...");
            
            // This is Listing 8.3 from the textbook!
            const ormUsers = await prisma.user.findMany({
                where: {
                    age: { gt: 21 },
                    isActive: true,
                },
                orderBy: {
                    lastName: 'asc',
                },
            });

            const foundOrm = ormUsers.map(u => `${u.firstName} ${u.lastName}`).join(", ");
            console.log(`  [Result] Found: [${foundOrm}]`);
            console.log("  [Lesson] The ORM translates your JSON object into safe SQL behind the scenes.");
            console.log("           Because Prisma is strongly typed, your IDE will flag any typos before you run the code.");

        } finally {
            // Always cleanly disconnect from the database!
            await prisma.$disconnect();
        }
    }
}
const SomeRepository = require('./someRepository');

/**
 * Entry point for the Node.js application.
 */
function main() {
    console.log("--- Running 'Before' (Upward Dep) ---");
    
    const beforeRepo = new SomeRepository();
    beforeRepo.updateData(123, "New Data");
    
    console.log("------------------------------------");
}

main();
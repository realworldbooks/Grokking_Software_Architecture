class UserDataService {
    getUserName(userId) {
        console.log("    [Service] Fetching Name...");
        return "Jane Doe";
    }

    getUserEmail(userId) {
        console.log("    [Service] Fetching Email...");
        return "jane.doe@example.com";
    }

    getUserOrderIds(userId) {
        console.log("    [Service] Fetching Order IDs...");
        return ["A123", "B456"];
    }

    getOrderTotal(orderId) {
        console.log(`    [Service] Fetching Total for Order ${orderId}...`);
        return 99.95;
    }
}

module.exports = UserDataService;
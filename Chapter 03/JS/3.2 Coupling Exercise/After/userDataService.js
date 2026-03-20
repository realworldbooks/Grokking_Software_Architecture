class UserDataService {
    getUserReport(userId) {
        console.log("    [Service] Building chunky report payload internally...");
        return {
            name: "Jane Doe",
            email: "jane.doe@example.com",
            totalSpent: 199.90
        };
    }
}

module.exports = UserDataService;
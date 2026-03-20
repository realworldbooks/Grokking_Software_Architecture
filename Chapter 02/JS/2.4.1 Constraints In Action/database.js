const database = {
    fetchUserData: async (userId) => {
        if (userId === "User123") {
            return {
                id: "User123",
                name: "Alice",
                email: "alice@example.com"
            };
        }
        return null;
    }
};

module.exports = database;
const databaseService = {
    getProfile: (id) => { 
        console.log(`    [DB] Fetching Profile for ${id}... (takes 500ms)`);
        return "User_Profile_Data";
    },
    getOrders: (id) => { 
        console.log(`    [DB] Fetching Orders for ${id}... (takes 500ms)`);
        return "User_Orders_Data";
    },
    getActivity: (id) => { 
        console.log(`    [DB] Fetching Activity for ${id}... (takes 500ms)`);
        return "User_Activity_Data";
    }
};

module.exports = databaseService;
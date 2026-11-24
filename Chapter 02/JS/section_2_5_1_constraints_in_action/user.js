/**
 * Represents a user entity.
 * This is a simple data-holding class, often called a DTO (Data Transfer Object).
 * * ARCHITECTURAL NOTE: Structural Constraints
 * By isolating this model into its own file, we ensure that the shape of our data 
 * is completely decoupled from how it is retrieved or processed.
 */
class User {
    /**
     * @param {string} id 
     * @param {string} name 
     * @param {string} email 
     */
    constructor(id, name, email) {
        // In JavaScript, we simulate the 'required' constraint by 
        // validating that essential fields are present.
        if (!id || !name || !email) {
            throw new Error("User must be initialized with an id, name, and email.");
        }
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

module.exports = User;
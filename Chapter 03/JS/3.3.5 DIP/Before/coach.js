const Forward = require('./forward');
const Midfielder = require('./midfielder');

class Coach {
    constructor() {
        this.forward = new Forward();
        this.midfielder = new Midfielder();
    }

    executeGamePlan() {
        this.forward.attack();
        this.midfielder.controlMidfield();
    }
}
module.exports = Coach;
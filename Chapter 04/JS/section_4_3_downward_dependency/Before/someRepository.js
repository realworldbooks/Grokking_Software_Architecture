const PresentationLayer = require('./presentationLayer');

/**
 * ARCHITECTURE WARNING: Upward Dependency Violation.
 */
class SomeRepository {
    constructor() {
        // 🚨 VIOLATION: Lower layer holds reference to upper layer.
        // This makes it impossible to use this repo in a CLI or worker.
        this.uiLayer = PresentationLayer.instance;
    }

    updateData(id, newData) {
        console.log("(Before) Saving data to database...");
        
        // 🚨 VIOLATION: Calling upwards to the UI Layer.
        this.uiLayer.updateStatusLabel(`(Before) Data ${id} Saved!`);
    }
}

module.exports = SomeRepository;
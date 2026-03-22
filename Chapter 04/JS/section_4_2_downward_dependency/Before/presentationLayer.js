/**
 * A fake UI layer class to illustrate the bad dependency.
 */
class PresentationLayer {
    static instance = new PresentationLayer();

    updateStatusLabel(text) {
        console.log(`[UI UPDATE]: ${text}`);
    }
}

module.exports = PresentationLayer;
class Coach {
    directFieldPlay(fieldPlayer) {
        console.log("  [Coach] Alright player, execute your field assignment!");
        fieldPlayer.playFieldPosition();
    }
}

module.exports = Coach;
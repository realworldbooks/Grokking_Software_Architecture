class Midfielder {
    executePlay(play) {
        if (typeof play.execute !== 'function') {
            throw new Error("Play must implement an execute() method!");
        }
        play.execute();
    }
}

module.exports = Midfielder;
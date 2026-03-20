package grokkingsoftwarearchitecture.chapter03.lsp.before;

public class Coach {
    public void directFieldPlay(Player fieldPlayer) {
        System.out.println("  [Coach] Alright player, execute your field assignment!");
        fieldPlayer.playFieldPosition();
    }
}
package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class DiscardHand {

    public static void playersDiscardHand(ArrayList<PokerPlayer> players) {
        for (PokerPlayer player : players) {
            player.discardHand();
        }
    }
}

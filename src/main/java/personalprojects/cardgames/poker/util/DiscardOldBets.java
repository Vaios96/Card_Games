package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class DiscardOldBets {
    public static void discardOldBets(ArrayList<PokerPlayer> players) {
        for (PokerPlayer player : players) {
            player.setCurrentBet(0.0);
        }
    }
}

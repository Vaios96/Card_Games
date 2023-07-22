package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class ShowPlayersInfo {

    public static void showPlayersInfo(ArrayList<PokerPlayer> players) {
        for (PokerPlayer player : players) {
            System.out.println();
            System.out.print(player.getUsername());
            System.out.print(" has a balance of: ");
            System.out.print(player.getChips());
            System.out.println(" chips!");
        }
        System.out.println();
    }
}

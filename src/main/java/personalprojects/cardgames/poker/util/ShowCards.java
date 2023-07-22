package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class ShowCards {

    public static void showCardsOfPlayers(ArrayList<PokerPlayer> players) {
        for (PokerPlayer player : players) {
            System.out.print(player);
            System.out.println(player.showCards());
        }
    }
}

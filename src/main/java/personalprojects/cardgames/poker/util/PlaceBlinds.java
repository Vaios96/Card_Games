package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class PlaceBlinds {

    public static double placeBlinds(double big, double small, int pointer, ArrayList<PokerPlayer> players) {
        double pot = 0.00;

        /*
        In poker the big and small blind bid change every round. So pointer can point whose turn is to place the big blind and the next one will place the small.
        There is the possibility that the player's chips are not enough to bid the big/small blind, so he automatically goes all in.
         */
        if (players.get(pointer).getChips() >= big) {
            players.get(pointer).setChips(players.get(pointer).getChips() - big);
            players.get(pointer).setCurrentBet(big);
            pot += big;
        } else {
            pot += players.get(pointer).getChips();
            players.get(pointer).setAllIn(true);
            players.get(pointer).setChips(0);
        }

        if (pointer == players.size()) {
            if (players.get(0).getChips() >= small) {
                players.get(0).setChips(players.get(0).getChips() - small);
                players.get(0).setCurrentBet(small);
                pot += small;
            } else {
                pot += players.get(0).getChips();
                players.get(0).setAllIn(true);
                players.get(0).setChips(0);
            }
        } else {
            if (players.get((pointer + 1) % players.size()).getChips() >= small) {
                players.get((pointer + 1) % players.size()).setChips(players.get((pointer + 1) % players.size()).getChips() - small);
                players.get((pointer + 1) % players.size()).setCurrentBet(small);
                pot += small;
            } else {
                pot += players.get((pointer + 1) % players.size()).getChips();
                players.get((pointer + 1) % players.size()).setAllIn(true);
                players.get((pointer + 1) % players.size()).setChips(0);
            }
        }

        return pot;
    }
}

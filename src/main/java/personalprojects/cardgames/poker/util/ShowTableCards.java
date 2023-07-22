package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.model.Hand;

public class ShowTableCards {

    public static void showCardsOnTable(int i, Hand hand) {
        for (int j = 0; j < i; j++) {
            System.out.println(hand.getCard(j));
        }
    }
}

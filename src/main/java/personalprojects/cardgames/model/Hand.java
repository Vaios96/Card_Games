package personalprojects.cardgames.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;


    public Hand() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void discardCard(Card card) {
        hand.remove(card);
    }

    public void discardHand() {
        hand.clear();
    }

    public Card getCard(int i) {
        return hand.get(i);
    }

    @Override
    public String toString() {
        return " hand = " + hand;
    }
}

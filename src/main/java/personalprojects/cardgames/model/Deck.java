package personalprojects.cardgames.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends AbstractDeck{
    private final ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    @Override
    public void create() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        deck.ensureCapacity(52);

        for (String suit : suits) {
            for (String rank : ranks) {
                Card currentCard = new Card(suit, rank);
                deck.add(currentCard);
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(deck);
    }

    @Override
    public Card dealCard(int i) {
        return deck.get(i);
    }

    @Override
    public int getCounter() {
        return super.getCounter();
    }

    @Override
    protected void setCounter(int gamesPlayed) {
        int counter = super.getCounter();
        counter++;
        super.setCounter(counter);
    }
}

package personalprojects.cardgames.poker;

import personalprojects.cardgames.model.Card;
import personalprojects.cardgames.model.Player;

import java.util.ArrayList;

public class PokerPlayer extends Player {
    private int tableId;
    private double chips;
    private double currentBet;
    private boolean in;
    private boolean allIn;
    private final ArrayList<Card> hand = new ArrayList<>();
    private int winningCondition = 0;
    private int tieBreaker = 0;
    private int secondTieBreaker = 0;
    private int kicker = 0;

    public PokerPlayer(String username, int tableId, double chips) {
        super(username);
        this.tableId = tableId;
        this.chips = chips;
        this.in = true;
    }

    public int getSecondTieBreaker() {
        return secondTieBreaker;
    }

    public void setSecondTieBreaker(int secondTieBreaker) {
        this.secondTieBreaker = secondTieBreaker;
    }

    public int getTableId() {
        return tableId;
    }

    public boolean isAllIn() {
        return allIn;
    }

    public void setAllIn(boolean allIn) {
        this.allIn = allIn;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(double currentBet) {
        this.currentBet = currentBet;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public double getChips() {
        return chips;
    }

    public void setChips(double chips) {
        this.chips = chips;
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

    public int getWinningCondition() {
        return winningCondition;
    }

    public void setWinningCondition(int winningCondition) {
        this.winningCondition = winningCondition;
    }

    public int getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(int tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public int getKicker() {
        return kicker;
    }

    public void setKicker(int kicker) {
        this.kicker = kicker;
    }

    public String showCards() {
        if (hand.size() == 0) return null;
        StringBuilder cards = new StringBuilder();
        for (Card card : hand) {
            cards.append(card).append(" ");
        }

        return cards.toString();
    }

    @Override
    public String toString() {
        return super.getUsername() + ": ";
    }
}

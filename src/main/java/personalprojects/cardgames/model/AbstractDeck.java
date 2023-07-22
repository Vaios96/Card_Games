package personalprojects.cardgames.model;

public class AbstractDeck implements  IDeck{
    private int gamesPlayed;

    public int getCounter() {
        return gamesPlayed;
    }

    protected void setCounter(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public void create() {

    }

    @Override
    public void shuffle() {

    }

    @Override
    public Card dealCard(int i) {

        return null;
    }
}

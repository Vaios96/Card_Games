package personalprojects.cardgames.model;

public interface IDeck {
    void create();

    void shuffle();

    Card dealCard(int i);
}

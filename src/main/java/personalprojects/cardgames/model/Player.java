package personalprojects.cardgames.model;

public class Player {
    private int id;
    private String username;
    private String password;
    private int gamesPlayed;
    private int gamesWon;

    public Player() {}

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
    }

    public Player(int id, String username, String password, int gamesPlayed, int gamesWon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Override
    public String toString() {
        return "Player: " + username + " has id = " + id + " , has played " + gamesPlayed + " games and won " + gamesWon + " games!";
    }
}

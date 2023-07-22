package personalprojects.cardgames.exceptions;

public class PlayerNotFoundException extends Exception {
    private final static long serialVersionUID = 1L;

    public PlayerNotFoundException(String s) {
        super(s);
    }
}

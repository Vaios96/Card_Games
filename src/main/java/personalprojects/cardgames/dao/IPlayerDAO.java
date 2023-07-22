package personalprojects.cardgames.dao;


import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.model.Player;

public interface IPlayerDAO {
    Player insert(Player player) throws PlayerDAOException;
    Player update(Player player) throws PlayerDAOException;
    void delete(Player player) throws PlayerDAOException;
    Player getByUsername(String username) throws PlayerDAOException;
    Player getById (int id) throws PlayerDAOException;
    Player updateGamesPlayedOrWon(Player player) throws PlayerDAOException;
}

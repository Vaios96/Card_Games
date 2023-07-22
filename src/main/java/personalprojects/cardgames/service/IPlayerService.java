package personalprojects.cardgames.service;

import personalprojects.cardgames.dto.PlayerDTO;
import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.exceptions.PlayerNotFoundException;
import personalprojects.cardgames.model.Player;

public interface IPlayerService {

    /**
     * Inserts a {@link Player} based on the data carried by the {@link PlayerDTO}
     *
     * @param playerToInsert
     *          DTO object that contains the data
     * @return
     *          The inserted player instance
     * @throws PlayerDAOException
     *          if any DAO exception happens
     */
    Player insertPlayer(PlayerDTO playerToInsert) throws PlayerDAOException;

    /**
     * Updates a {@link Player} based on the data carried by the {@link PlayerDTO}
     *
     * @param playerToUpdate
     *          DTO Object containing the new username and password for the {@link Player}
     * @return
     *          the updated {@link Player}
     * @throws PlayerDAOException
     *          if any DAO exception occurs
     * @throws PlayerNotFoundException
     *          if there was not found a {@link Player} with the provided {@link Player}'s id
     */
    Player updatePlayer(PlayerDTO playerToUpdate) throws PlayerDAOException, PlayerNotFoundException;


    /**
     * Deletes a {@link Player} based on the data carried by the {@link PlayerDTO}
     *
     * @param username
     *          the username of the {@link Player} to be deleted
     * @throws PlayerDAOException
     *          if any DAO exception happens
     * @throws PlayerNotFoundException
     *          if the {@link Player} to be deleted is not found
     */
    void deletePlayer(String username) throws PlayerDAOException, PlayerNotFoundException;

    /**
     * Searches and gets back to the caller a {@link Player} object identified by their username
     * @param username
     *          a String object that contains the username of a {@link Player}
     * @return
     *          the {@link Player} object that matches the given username
     * @throws PlayerDAOException
     *          if any DAO error happens
     */
    Player getPlayerByUsername(String username) throws PlayerDAOException;

    /**
     * Searches and gets back to the caller a {@link Player} object identified by their id
     *
     * @param id
     *          an int that contains the id of a {@link Player}
     * @return
     *          the {@link Player} object that matches the given id
     * @throws PlayerDAOException
     *          if any DAO error happens
     */
    Player getPlayerById(int id) throws PlayerDAOException;

    /**
     * Searches if there is already a {@link Player} with the given username
     * @param username
     *          the given username
     * @return
     *          true if the username is taken, false if not
     * @throws PlayerDAOException
     *          if any DAO error happens
     */
    boolean playerExists(String username) throws PlayerDAOException;

    /**
     * Searches if there is already a {@link Player} with the given id
     * @param id
     *          the given id
     * @return
     *          true if there is a {@link Player} with this id, false if not
     * @throws PlayerDAOException
     *          if any DAO error occurs
     */
    boolean playerExists(int id) throws PlayerDAOException;

    /**
     * Updates a {@link Player}'s games played and won
     *
     * @param player
     *              the {@link Player} whose games should be updated
     *
     * @return
     *              the {@link Player} whose games should were updated
     * @throws PlayerDAOException
     *              if any DAO error occurs
     */
    Player updateGamesPlayedOrWon(Player player) throws PlayerDAOException;
}

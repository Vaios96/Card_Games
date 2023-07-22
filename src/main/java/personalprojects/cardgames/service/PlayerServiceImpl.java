package personalprojects.cardgames.service;

import personalprojects.cardgames.dao.IPlayerDAO;
import personalprojects.cardgames.dto.PlayerDTO;
import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.exceptions.PlayerNotFoundException;
import personalprojects.cardgames.model.Player;

public class PlayerServiceImpl implements IPlayerService {
    private final IPlayerDAO playerDAO;

    public PlayerServiceImpl(IPlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public Player insertPlayer(PlayerDTO playerToInsert) throws PlayerDAOException {
        if (playerToInsert == null) return null;

        try {
            Player player = mapPlayer(playerToInsert);
            playerDAO.insert(player);
            return player;
        } catch (PlayerDAOException e) {
            throw e;
        }
    }

    // This one requires debugging
    @Override
    public Player updatePlayer(PlayerDTO playerToUpdate) throws PlayerDAOException, PlayerNotFoundException {
        if (playerToUpdate == null) return null;

        try {
            Player existingPlayer = playerDAO.getById(playerToUpdate.getId());
            if (existingPlayer == null) {
                throw new PlayerNotFoundException("Player with id " + playerToUpdate.getId() + " was not found!");
            }

            Player updatedPlayer = new Player(
                    existingPlayer.getId(),
                    playerToUpdate.getUsername(),
                    playerToUpdate.getPassword(),
                    existingPlayer.getGamesPlayed(),
                    existingPlayer.getGamesWon()
            );

            playerDAO.update(updatedPlayer);
            return updatedPlayer;
        } catch (PlayerDAOException | PlayerNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    @Override
    public void deletePlayer(String username) throws PlayerDAOException, PlayerNotFoundException {
        try {
            if (playerDAO.getByUsername(username) == null) {
                throw new PlayerNotFoundException("Player with username " + username + "not found");
            }
            playerDAO.delete(getPlayerByUsername(username));
        } catch (PlayerDAOException | PlayerNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Player getPlayerByUsername(String username) throws PlayerDAOException {
        if (username == null) return null;

        try {
            Player player = playerDAO.getByUsername(username);
            return player;
        } catch (PlayerDAOException e) {
            throw new PlayerDAOException("Username does not exist in the database!");
        }
    }

    @Override
    public Player getPlayerById(int id) throws PlayerDAOException {
        if (id <= 0) return null;

        try {
            Player player = playerDAO.getById(id);
            return player;
        } catch (PlayerDAOException e) {
            throw e;
        }
    }

    @Override
    public boolean playerExists(String username) {
        if (username == null) throw new IllegalArgumentException("Given username was null!");

        try {
            Player player = getPlayerByUsername(username);
            return player != null;
        } catch (PlayerDAOException e) {
            return false;
        }
    }

    @Override
    public boolean playerExists(int id) throws PlayerDAOException {
        try {
            Player player = getPlayerById(id);
            return player != null;
        } catch (PlayerDAOException e) {
            return false;
        }
    }

    @Override
    public Player updateGamesPlayedOrWon(Player player) throws PlayerDAOException {
        Player existingInDb = playerDAO.getById(player.getId());
        existingInDb.setGamesPlayed(player.getGamesPlayed());
        existingInDb.setGamesWon(player.getGamesWon());

        playerDAO.updateGamesPlayedOrWon(existingInDb);
        return existingInDb;
    }

    private Player mapPlayer(PlayerDTO dto) {
        return new Player(dto.getUsername(), dto.getPassword());
    }
}

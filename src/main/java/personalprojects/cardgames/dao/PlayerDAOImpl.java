package personalprojects.cardgames.dao;

import personalprojects.cardgames.dbutil.DbUtil;
import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAOImpl implements IPlayerDAO {
    @Override
    public Player insert(Player player) throws PlayerDAOException {
        String sql = "INSERT INTO PLAYERS (username, user_password, games_played, games_won) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            String username = player.getUsername();
            String password = player.getPassword();

            if (username.equals("") || password.equals("")) {
                return null;
            }

            p.setString(1, username);
            p.setString(2, password);
            p.setInt(3, 0);
            p.setInt(4, 0);
            p.executeUpdate();
            return player;
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error in Player " + player + " insertion");
        }
    }

    @Override
    public Player update(Player player) throws PlayerDAOException {
        String sql = "UPDATE PLAYERS SET username = ?, user_password = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            String username = player.getUsername();
            String password = player.getPassword();
            int id = player.getId();

            if (username.equals("") || password.equals("")) {
                return null;
            }

            p.setString(1, username);
            p.setString(2, password);
            p.setInt(3, id);
            p.executeUpdate();
            return player;
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error about " + player + " credentials update");
        }
    }

    @Override
    public void delete(Player player) throws PlayerDAOException {
        String sql = "DELETE FROM PLAYERS WHERE id = ?";
        int id = player.getId();

        try (Connection conn = DbUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error in delete Player with id = " + id);
        }
    }

    @Override
    public Player getByUsername(String username) throws PlayerDAOException {
        String sql = "SELECT id, username, user_password, games_played, games_won FROM PLAYERS WHERE username = ?";
        ResultSet rs;
        Player player = null;

        try (Connection conn = DbUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, username);
            rs = p.executeQuery();

            if (rs.next()) {
                player = new Player(rs.getInt("id"), rs.getString("username"), rs.getString("user_password"), rs.getInt("games_played"), rs.getInt("games_won"));            
            }
            
            return player;
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error in finding player with username: " + username);
        }
    }

    @Override
    public Player getById(int id) throws PlayerDAOException {
        String sql = "SELECT id, username, user_password, games_played, games_won FROM PLAYERS WHERE id = ?";
        ResultSet rs;
        Player player = null;

        try (Connection conn = DbUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql)) {

            p.setInt(1, id);
            rs = p.executeQuery();

            if (rs.next()) {
                player = new Player(rs.getInt("id"), rs.getString("username"), rs.getString("user_password"), rs.getInt("games_played"), rs.getInt("games_won"));
            }

            return player;
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error in finding player with id: " + id);
        }
    }

    @Override
    public Player updateGamesPlayedOrWon(Player player) throws PlayerDAOException {
        String sql = "UPDATE PLAYERS SET games_played = ?, games_won = ? WHERE ID = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {

            int gamesPlayed = player.getGamesPlayed();
            int gamesWon = player.getGamesWon();
            int id = player.getId();


            p.setInt(1, gamesPlayed);
            p.setInt(2, gamesWon);
            p.setInt(3, id);
            p.executeUpdate();
            return player;
        } catch (SQLException e) {
            throw new PlayerDAOException("SQL Error in Player " + player + " update");
        }
    }
}

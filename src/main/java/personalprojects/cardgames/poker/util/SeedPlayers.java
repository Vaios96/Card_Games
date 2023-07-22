package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.dao.PlayerDAOImpl;
import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.model.Player;
import personalprojects.cardgames.poker.PokerPlayer;
import personalprojects.cardgames.service.IPlayerService;
import personalprojects.cardgames.service.PlayerServiceImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static personalprojects.cardgames.Main.*;

public class SeedPlayers {

    static Scanner in = new Scanner(System.in);
    public static ArrayList<PokerPlayer> seedPlayers(int numberOfPlayers) {
        ArrayList<PokerPlayer> players = new ArrayList<>();
        IPlayerService serv = new PlayerServiceImpl(new PlayerDAOImpl());

        System.out.println("This table has a 20 euro minimum buy-in, the maximum is 100 euro! The small and big blind bets begin from 1 and 2 euro respectively. " +
                "Each time a player leaves the amount is increased by 1 and 2, also respectively! You should also know that this table considers luck a great gift!" +
                "There is a single pot and it does not split regarding the bets of each player, so consider this while raising the stakes!");

        for (int i = 0; i < numberOfPlayers; i++ ) {
            int choice;
            Player player;
            double chips;

            System.out.println("Would you like to provide the username or the id of the Player that would like to join the table?");
            System.out.println("1. Username");
            System.out.println("2. ID");

            choice = getChoice(1, 2);

            switch (choice) {
                case 1:
                    player = getAccountByUsername();
                    while (true) {
                        if (validatePlayer(player)) {
                            System.out.println("Amount of buy-in for player " + (i + 1) + ": ");
                            while (true) {
                                try {
                                    chips = in.nextDouble();
                                    if (chips < 20.00 || chips > 100.00) {
                                        System.out.println("The buy-in must be between 20-100 euro");
                                        continue;
                                    }
                                    break;
                                } catch (InputMismatchException e) {
                                    in.nextLine();
                                    System.out.println("Invalid amount, please provide an amount of the form: XX.XX!");
                                }
                            }
                            player.setGamesPlayed(player.getGamesPlayed() + 1);
                            try {
                                serv.updateGamesPlayedOrWon(player);
                            } catch (PlayerDAOException e) {
                                System.out.println("Database error forcing System to terminate!");
                                System.exit(42);
                            }
                            PokerPlayer newPokerPlayer = new PokerPlayer(player.getUsername(), i, chips);
                            players.add(newPokerPlayer);
                            break;
                        }
                    }
                    break;
                case 2:
                    player = getAccountById();
                    while (true) {
                        if (validatePlayer(player)) {
                            System.out.println("Amount of buy-in for player " + (i + 1) + ": ");
                            while (true) {
                                try {
                                    chips = in.nextDouble();
                                    if (chips < 20.00 || chips > 100.00) {
                                        System.out.println("The buy-in must be between 20-100 euro");
                                        continue;
                                    }
                                    break;
                                } catch (InputMismatchException e) {
                                    in.nextLine();
                                    System.out.println("Invalid amount, please provide an amount of the form: XX.XX!");
                                }
                            }
                            PokerPlayer newPokerPlayer = new PokerPlayer(player.getUsername(), i, chips);
                            players.add(newPokerPlayer);
                            break;
                        }
                    }
                    break;
            }
        }

        return players;
    }
}

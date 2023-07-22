package personalprojects.cardgames.poker.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GetPlayersCount {

    static Scanner in = new Scanner(System.in);

    public static int getPlayersCount() {
        int playersCount = 0;
        System.out.println("Welcome to Poker! This game requires 2-8 players, how many would like to play?");
        while (playersCount < 2 || playersCount > 8) {
            try {
                playersCount = in.nextInt();
                if (playersCount < 2 || playersCount > 8) {
                    System.out.println("Invalid input, to play Poker you need 2-8 players!");
                }
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid input, to play Poker you need 2-8 players!");
            }
        }

        return playersCount;
    }
}

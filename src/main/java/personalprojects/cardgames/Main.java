package personalprojects.cardgames;

import personalprojects.cardgames.dao.PlayerDAOImpl;
import personalprojects.cardgames.dto.PlayerDTO;

import java.util.InputMismatchException;
import java.util.Scanner;

import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.exceptions.PlayerNotFoundException;
import personalprojects.cardgames.model.Player;
import personalprojects.cardgames.service.IPlayerService;
import personalprojects.cardgames.service.PlayerServiceImpl;

import static personalprojects.cardgames.poker.PlayPoker.playPoker;


public class Main {

    public static Scanner in = new Scanner(System.in);
    public static IPlayerService serv = new PlayerServiceImpl(new PlayerDAOImpl());

    public static void main(String[] args) {
        int choice;
        boolean stayInMenu = true;

        while (stayInMenu) {

            showMenu();
            choice = getChoice(1, 3);

            switch (choice) {
                case 1:
                    showAccountActions();
                    choice = getChoice(1, 5);

                    switch (choice) {
                        case 1:
                            createAccount();
                            break;
                        case 2:
                            showAccountInformation();
                            break;
                        case 3:
                            updateAccount();
                            break;
                        case 4:
                            deleteAccount();
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("I do not believe that you will ever see this... Though a lot of my believes have been ruined after running the code...");
                    }

                    break;
                case 2:
                    showGames();
                    choice = getChoice(1, 2);

                    switch (choice) {
                        case 1:
                            playPoker();
                            break;
                        case 2:
                            System.out.println("This is not an interesting choice for now...");
                            break;
                        default:
                            System.out.println("-Again? \n - Yeah...Error...");
                    }
                    break;
                case 3:
                    stayInMenu = false;
                    break;
                default:
                    System.out.println("Unexpected error... I probably messed this up, sorry!");
            }
        }

        System.out.println("Thanks for playing!");
    }

    public static void showMenu() {
        System.out.println("Welcome to Card Games Application!");
        System.out.println("What would you like to do?");
        System.out.println("1. Account actions");
        System.out.println("2. Play a game");
        System.out.println("3. Exit");
    }

    public static void showAccountActions() {
        System.out.println("What would you like to do?");
        System.out.println("1. Create an account");
        System.out.println("2. See account information");
        System.out.println("3. Change account's username and password");
        System.out.println("4. Delete account");
        System.out.println("5. Exit");
    }

    public static void showGames() {
        System.out.println("What would you like to play?");
        System.out.println("1. Texas HoldEm Poker");
        System.out.println("2. Exit");
        System.out.println("As you can see there is only one game available at the moment, but more are coming soon!");
    }

    public static int getChoice(int low, int high) {
        int choice = 0;

        while (choice < low || choice > high) {
            try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid choice! You should choose between " + low + " and " + high + " depending on the action you would like to do!");
            }
        }

        return choice;
    }

    public static void createAccount() {
        PlayerDTO playerDTO;
        String usernameDTO = "";
        String passwordDTO = "";


        while (usernameDTO.equals("")) {
            System.out.println("What will your username be?");
            try {
                usernameDTO = in.next();
                if (usernameDTO.length() > 45 || usernameDTO.length() < 1) {
                    usernameDTO = "";
                    throw new IllegalArgumentException("Username must not be longer than 45 characters and of course no less than 1 character");
                }
                if (serv.playerExists(usernameDTO)) {
                    System.out.println("Username already exists!");
                    usernameDTO = "";
                }
            } catch (PlayerDAOException | IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        while (passwordDTO.equals("")) {
            System.out.println("What will your password be?");

            passwordDTO = in.next();
            if (passwordDTO.length() > 45) {
                System.out.println("Password must be between 1 and 45 characters!");
                passwordDTO = "";
            }
        }

        playerDTO = new PlayerDTO(usernameDTO, passwordDTO);

        try {
            serv.insertPlayer(playerDTO);
            System.out.println("Player created!");
        } catch (PlayerDAOException e) {
            System.out.println("Error! Please try again!");
        }
    }

    public static void showAccountInformation() {
        int choice;
        Player player;

        System.out.println("Would you like to provide the username or the id of the account that interests you?");
        System.out.println("1. Username");
        System.out.println("2. ID");
        System.out.println("3. Exit");

        choice = getChoice(1, 3);

        switch (choice) {
            case 1:
                player = getAccountByUsername();
                if (validatePlayer(player)) {
                    System.out.println(player);
                }
                break;
            case 2:
                player = getAccountById();
                if (validatePlayer(player)) {
                    System.out.println(player);
                }
                break;
            case 3:
                break;
        }
    }

    public static boolean validatePlayer(Player player) {
        String password;
        int counter = 0;
        boolean correctPassword = false;

        while (counter < 5) {
            System.out.println("What is your password?");
            password = in.next();
            if (password.equals(player.getPassword())) {
                correctPassword = true;
                break;
            } else {
                System.out.println("Wrong password!");
                counter++;
            }
        }

        if (counter == 5) {
            System.out.println("Too many unsuccessful attempts, terminating action!");
        }

        return correctPassword;
    }

    public static Player getAccountByUsername() {
        String usernameDTO;
        Player player = new Player();

        System.out.println("What is your username?");
        usernameDTO = in.next();
        if (usernameDTO.length() > 45 || usernameDTO.length() < 1) {
            System.out.println("Invalid username!");
        } else {
            try {
                if (serv.playerExists(usernameDTO)) {
                    player = serv.getPlayerByUsername(usernameDTO);
                }
            } catch (PlayerDAOException e) {
                System.out.println("Unexpected error, please try again!");
            }
        }

        return player;
    }

    public static Player getAccountById() {
        int idDTO;
        Player player = new Player();

        System.out.println("What is your id?");
        try {
            idDTO = in.nextInt();
            if (idDTO <= 0) {
                System.out.println();
            } else {
                try {
                    if (serv.playerExists(idDTO)) {
                        player = serv.getPlayerById(idDTO);
                    }
                } catch (PlayerDAOException e) {
                    System.out.println("Error in retrieving the account's information!");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        }

        return player;
    }

    public static void updateAccount() {
        int choice;
        Player player;
        PlayerDTO playerDTO = new PlayerDTO();
        String usernameDTO;
        String passwordDTO;

        System.out.println("Would you like to provide the username or the id of the account that you would like to update?");
        System.out.println("1. Username");
        System.out.println("2. ID");
        System.out.println("3. Exit");

        choice = getChoice(1, 3);

        switch (choice) {
            case 1:
                player = getAccountByUsername();
                if (validatePlayer(player)) {
                    while (true) {
                        try {
                            System.out.println("What would you like the new username to be?");
                            usernameDTO = in.next();
                            if (usernameDTO.length() > 0 && usernameDTO.length() < 46) {
                                if (!serv.playerExists(usernameDTO) || player.getUsername().equals(usernameDTO)) {
                                    break;
                                }
                            }
                        } catch (PlayerDAOException e) {
                            System.out.println("Error, try again!");
                        }
                    }

                    do {
                        System.out.println("What would you like the new password to be?");
                        passwordDTO = in.next();
                    } while (passwordDTO.length() < 1 || passwordDTO.length() > 45);

                    playerDTO.setId(player.getId());
                    playerDTO.setPassword(passwordDTO);
                    playerDTO.setUsername(usernameDTO);

                    try {
                        serv.updatePlayer(playerDTO);
                    } catch (PlayerDAOException | PlayerNotFoundException e) {
                        System.out.println("ERROR!");
                    }
                }
                break;
            case 2:
                player = getAccountById();
                if (validatePlayer(player)) {
                    System.out.println(player);
                }
                break;
            case 3:
                break;
        }
    }

    public static void deleteAccount() {
        int choice;
        Player player;

        System.out.println("Would you like to provide the username or the id of the account that you would like to delete?");
        System.out.println("1. Username");
        System.out.println("2. ID");
        System.out.println("3. Exit");

        choice = getChoice(1, 3);

        switch (choice) {
            case 1:
                player = getAccountByUsername();
                if (validatePlayer(player)) {
                    try {
                        serv.deletePlayer(player.getUsername());
                        System.out.println("Player deleted!");
                    } catch (PlayerDAOException | PlayerNotFoundException e) {
                        System.out.println("Not again... Sorry, error!");
                    }
                }
                break;
            case 2:
                player = getAccountById();
                if (validatePlayer(player)) {
                    try {
                        serv.deletePlayer(player.getUsername());
                        System.out.println("Player deleted!");
                    } catch (PlayerDAOException | PlayerNotFoundException e) {
                        System.out.println("Why, oh why?... Sorry, error!");
                    }
                }
                break;
            case 3:
                break;
        }
    }
}

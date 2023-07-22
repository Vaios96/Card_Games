package personalprojects.cardgames.poker;


import personalprojects.cardgames.dao.PlayerDAOImpl;
import personalprojects.cardgames.exceptions.PlayerDAOException;
import personalprojects.cardgames.model.Deck;
import personalprojects.cardgames.model.Hand;
import personalprojects.cardgames.model.Player;
import personalprojects.cardgames.service.IPlayerService;
import personalprojects.cardgames.service.PlayerServiceImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ListIterator;
import java.util.Scanner;

import static personalprojects.cardgames.poker.util.CheckWinner.getPlayersHighestWinningCondition;
import static personalprojects.cardgames.poker.util.CheckWinner.getWinnersId;
import static personalprojects.cardgames.poker.util.DealCards.dealAllPlayers;
import static personalprojects.cardgames.poker.util.DiscardHand.playersDiscardHand;
import static personalprojects.cardgames.poker.util.DiscardOldBets.discardOldBets;
import static personalprojects.cardgames.poker.util.GetPlayersCount.getPlayersCount;
import static personalprojects.cardgames.poker.util.PlaceBlinds.placeBlinds;
import static personalprojects.cardgames.poker.util.SeedPlayers.seedPlayers;
import static personalprojects.cardgames.poker.util.ShowCards.showCardsOfPlayers;
import static personalprojects.cardgames.poker.util.ShowPlayersInfo.showPlayersInfo;
import static personalprojects.cardgames.poker.util.ShowTableCards.showCardsOnTable;

public class PlayPoker {

    static Scanner in = new Scanner(System.in);

    public static void playPoker() {
        int numberOfPlayers;
        int blindPointer = 0;
        double bigBlind = 2.00;
        double smallBlind = 1.00;
        double pot;
        Hand cardsOnTable = new Hand();
        ArrayList<PokerPlayer> playersInfo;
        Deck deck = new Deck();
        deck.create();
        ArrayList<Integer> winnersId;
        IPlayerService serv = new PlayerServiceImpl(new PlayerDAOImpl());
        Player winner;
        int winnersGamesWonCounter;

        numberOfPlayers = getPlayersCount();
        playersInfo = seedPlayers(numberOfPlayers);

        while (numberOfPlayers != 1) {
            System.out.println();
            System.out.println();
            System.out.println("NEW ROUND!");
            pot = 0;

            // Placing blinds and dealing the cards
            pot += placeBlinds(bigBlind, smallBlind, blindPointer, playersInfo);
            dealAllPlayers(deck, playersInfo, cardsOnTable);
            showCardsOfPlayers(playersInfo);
            System.out.println();

            // First round call the big blind, raise, fold etc.
            pot += placeBets(playersInfo, (blindPointer + 1) % numberOfPlayers, true, bigBlind);
            System.out.println();


            // Flop & second round bets
            discardOldBets(playersInfo);
            showCardsOnTable(3, cardsOnTable);
            System.out.println();
            pot += placeBets(playersInfo, (blindPointer + 1) % numberOfPlayers, false, 0.0);
            System.out.println();

            // Turn & third round bets
            discardOldBets(playersInfo);
            showCardsOnTable(4, cardsOnTable);
            System.out.println();
            pot += placeBets(playersInfo, (blindPointer + 1) % numberOfPlayers, false, 0.0);
            System.out.println();

            // River & fourth round bets
            discardOldBets(playersInfo);
            showCardsOnTable(5, cardsOnTable);
            System.out.println();
            pot += placeBets(playersInfo, (blindPointer + 1) % numberOfPlayers, false, 0.0);
            System.out.println();

            // Winner gets the pot & info display
            getPlayersHighestWinningCondition(playersInfo, cardsOnTable);
            winnersId = getWinnersId(playersInfo);

            for (PokerPlayer player : playersInfo) {
                if (winnersId.contains(player.getTableId())) {
                    player.setChips(player.getChips() + (pot / winnersId.size()));
                    System.out.println(player + "won!");
                }
            }

            System.out.println("For confirmation here are the cards on the table:");
            showCardsOnTable(5, cardsOnTable);
            System.out.println("And here are the players hands:");
            showCardsOfPlayers(playersInfo);


            // Clearing everything for the next round and setting the next blinds
            winnersId.clear();
            showPlayersInfo(playersInfo);
            playersDiscardHand(playersInfo);
            cardsOnTable.discardHand();

            ListIterator<PokerPlayer> iterator = playersInfo.listIterator();
            while (iterator.hasNext()) {
                PokerPlayer player = iterator.next();
                if (player.getChips() <= 0) {
                    iterator.remove();
                    numberOfPlayers--;
                    bigBlind += 2;
                    smallBlind++;
                } else {
                    player.setIn(true);
                    player.setAllIn(false);
                    player.setWinningCondition(0);
                    player.setTieBreaker(0);
                    player.setSecondTieBreaker(0);
                    player.setKicker(0);
                }
            }

            blindPointer++;
            blindPointer = blindPointer % numberOfPlayers;
        }

        try {
            winner = serv.getPlayerByUsername(playersInfo.get(0).getUsername());
            winnersGamesWonCounter = winner.getGamesWon() + 1;
            winner.setGamesWon(winnersGamesWonCounter);
            serv.updateGamesPlayedOrWon(winner);
        } catch (PlayerDAOException e) {
            System.out.println("Unexpected error, terminating System");
            System.exit(24);
        }

        System.out.println("Winner was: " + playersInfo.get(0).getUsername() + "!");
        System.out.println();
        System.out.println();
    }



    public static double placeBets(ArrayList<PokerPlayer> players, int playerToCall, boolean raised, double raiseAmount) {
        int countChecksCallsOrFolds = 0;
        int action;
        double bets = 0.0;
        double newRaiseAmount = 0.0;
        double callAmount;
        double chipsToBeAddedForReraise;

        if (raised) {
            while (countChecksCallsOrFolds < players.size()) {
                if (players.get(playerToCall).isIn() && !players.get(playerToCall).isAllIn()) {
                    if (players.get(playerToCall).getChips() > raiseAmount - players.get(playerToCall).getCurrentBet()) {
                        System.out.println(players.get(playerToCall) + " your turn!");
                        System.out.println("What will your action be? ");
                        System.out.println("1. Call: " + (raiseAmount - players.get(playerToCall).getCurrentBet()));
                        System.out.println("2. Raise");
                        System.out.println("3. Fold");
                        action = 0;

                        while (action < 1 || action > 3) {
                            System.out.println("Choose one of the actions mentioned above: ");
                            try {
                                action = in.nextInt();
                            } catch (InputMismatchException e) {
                                in.nextLine();
                                System.out.println("Invalid action! Choose one of the actions mentioned above: ");
                            }
                        }

                        switch (action) {
                            case 1:
                                callAmount = raiseAmount - players.get(playerToCall).getCurrentBet();
                                players.get(playerToCall).setChips(players.get(playerToCall).getChips() - callAmount);
                                bets += callAmount;
                                players.get(playerToCall).setCurrentBet(raiseAmount);
                                playerToCall++;
                                playerToCall = playerToCall % players.size();
                                countChecksCallsOrFolds++;
                                break;
                            case 2:
                                while (newRaiseAmount <= 0 || newRaiseAmount > players.get(playerToCall).getChips() - (raiseAmount - players.get(playerToCall).getCurrentBet())) {
                                    System.out.println("How much would you like to raise the bet? Current bet: " + raiseAmount);
                                    try {
                                        newRaiseAmount = in.nextDouble();
                                    } catch (InputMismatchException e) {
                                        in.nextLine();
                                        System.out.println("Invalid amount!");
                                    }
                                }
                                chipsToBeAddedForReraise = newRaiseAmount + (raiseAmount - players.get(playerToCall).getCurrentBet());
                                raiseAmount += newRaiseAmount;
                                players.get(playerToCall).setChips(players.get(playerToCall).getChips() - chipsToBeAddedForReraise);
                                players.get(playerToCall).setCurrentBet(raiseAmount);
                                playerToCall++;
                                playerToCall = playerToCall % players.size();
                                bets += chipsToBeAddedForReraise;
                                bets += placeBets(players, playerToCall,true, raiseAmount);
                                return bets;
                            case 3:
                                players.get(playerToCall).setIn(false);
                                playerToCall++;
                                playerToCall = playerToCall % players.size();
                                countChecksCallsOrFolds++;
                                break;
                            default:
                                System.out.println("I have no idea what went wrong... Please contact me, so that I can fix it!");
                                System.exit(404);
                        }
                    } else {
                        System.out.println(players.get(playerToCall) + " your turn!");
                        System.out.println("What will your action be? ");
                        System.out.println("1. Go All-in");
                        System.out.println("2. Fold");
                        action = 0;

                        while (action < 1 || action > 2) {
                            System.out.println("Choose one of the actions mentioned above: ");
                            try {
                                action = in.nextInt();
                            } catch (InputMismatchException e) {
                                in.nextLine();
                                System.out.println("Invalid action! Choose one of the actions mentioned above: ");
                            }
                        }

                        switch (action) {
                            case 1:
                                bets += players.get(playerToCall).getChips();
                                players.get(playerToCall).setChips(0.0);
                                players.get(playerToCall).setAllIn(true);
                                playerToCall++;
                                playerToCall = playerToCall % players.size();
                                countChecksCallsOrFolds++;
                                break;
                            case 2:
                                players.get(playerToCall).setIn(false);
                                playerToCall++;
                                playerToCall = playerToCall % players.size();
                                countChecksCallsOrFolds++;
                                break;
                            default:
                                System.out.println("I have no idea what went wrong... Please contact me, so that I can fix it!");
                                System.exit(404);
                        }
                    }
                } else {
                    countChecksCallsOrFolds++;
                }
            }
        } else {
            while (countChecksCallsOrFolds < players.size()) {
                if (players.get(playerToCall).isIn() && !players.get(playerToCall).isAllIn()) {
                    System.out.println(players.get(playerToCall) + " your turn!");
                    System.out.println("What will your action be? ");
                    System.out.println("1. Check");
                    System.out.println("2. Raise");
                    System.out.println("3. Fold");
                    action = 0;

                    while (action < 1 || action > 3) {
                        System.out.println("Choose one of the actions mentioned above: ");
                        try {
                            action = in.nextInt();
                        } catch (InputMismatchException e) {
                            in.nextLine();
                            System.out.println("Invalid action! Choose one of the actions mentioned above: ");
                        }
                    }

                    switch (action) {
                        case 1:
                            countChecksCallsOrFolds++;
                            playerToCall++;
                            playerToCall = playerToCall % players.size();
                            break;
                        case 2:
                            while (newRaiseAmount <= 0 || newRaiseAmount > players.get(playerToCall).getChips() - (raiseAmount - players.get(playerToCall).getCurrentBet())) {
                                System.out.println("How much would you like to raise the bet? Current bet: " + raiseAmount);
                                try {
                                    newRaiseAmount = in.nextDouble();
                                } catch (InputMismatchException e) {
                                    in.nextLine();
                                    System.out.println("Invalid amount!");
                                }
                            }
                            raiseAmount += newRaiseAmount;
                            players.get(playerToCall).setChips(players.get(playerToCall).getChips() - (raiseAmount - players.get(playerToCall).getCurrentBet()));
                            players.get(playerToCall).setCurrentBet(raiseAmount);
                            playerToCall++;
                            playerToCall = playerToCall % players.size();
                            bets += raiseAmount;
                            bets += placeBets(players, playerToCall,true, raiseAmount);
                            return bets;
                        case 3:
                            players.get(playerToCall).setIn(false);
                            playerToCall++;
                            playerToCall = playerToCall % players.size();
                            countChecksCallsOrFolds++;
                            break;
                        default:
                            System.out.println("I have no idea what went wrong... Please contact me, so that I can fix it!");
                            System.exit(404);
                    }
                } else {
                    countChecksCallsOrFolds++;
                }
            }
        }
        return bets;
    }
}
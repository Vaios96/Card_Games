package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.model.Card;
import personalprojects.cardgames.model.Hand;
import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

import static personalprojects.cardgames.poker.util.CheckWinningConditions.*;

public class CheckWinner {

    public static void getPlayersHighestWinningCondition(ArrayList<PokerPlayer> pokerPlayers, Hand cardsOnTable) {
        int[] winningConditions;
        Hand currentHand = new Hand();

        for (PokerPlayer player : pokerPlayers) {
            if(!player.isIn()) continue;

            currentHand.discardHand();
            for (Card card : player.getHand()) {
                currentHand.addCard(card);
            }
            for (int i = 0; i < 5; i++) {
                currentHand.addCard(cardsOnTable.getCard(i));
            }

            if (checkRoyalFlush(currentHand)) {
                player.setWinningCondition(10);
                continue;
            }

            winningConditions = checkStraightFlush(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(9);
                player.setTieBreaker(winningConditions[1]);
            }

            winningConditions = checkFourOfAKind(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(8);
                player.setTieBreaker(winningConditions[1]);
                continue;
            }

            winningConditions = checkFullHouse(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(7);
                player.setTieBreaker(winningConditions[1]);
                continue;
            }

            winningConditions = checkFlush(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(6);
                player.setTieBreaker(winningConditions[1]);
                continue;
            }

            winningConditions = checkStraight(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(5);
                player.setTieBreaker(winningConditions[1]);
                continue;
            }

            winningConditions = checkThreeOfAKind(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(4);
                player.setTieBreaker(winningConditions[1]);
                continue;
            }

            winningConditions = checkTwoPair(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(3);
                player.setTieBreaker(winningConditions[1]);
                player.setSecondTieBreaker(winningConditions[2]);
                player.setKicker(winningConditions[3]);
                continue;
            }

            winningConditions = checkPair(currentHand);
            if (winningConditions[0] == 1) {
                player.setWinningCondition(2);
                player.setTieBreaker(winningConditions[1]);
                player.setSecondTieBreaker(winningConditions[2]);
                continue;
            }

            player.setWinningCondition(1);
            player.setTieBreaker(checkHighCard(currentHand));
        }
    }

    public static ArrayList<Integer> getWinnersId(ArrayList<PokerPlayer> players) {
        int maxWinningCondition = 0;
        int winnerCounter = 0;
        ArrayList<Integer> winnersId = new ArrayList<>();

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() > maxWinningCondition) {
                maxWinningCondition = player.getWinningCondition();
            }
        }

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition) {
                winnerCounter++;
            }
        }

        if (winnerCounter == 1) {
            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition) {
                    winnersId.add(player.getId());
                }
            }
        } else {
            switch (maxWinningCondition) {
                // There can be multiple winners with a Royal Flush, if it consists of cards placed on the table (flop + turn + river)
                case (10):
                    for (PokerPlayer player : players) {
                        if (player.getWinningCondition() == 10) {
                            winnersId.add(player.getId());
                        }
                    }
                    break;
                case (9):
                    winnersId = oneTieBreakerWinningCondition(players, 9);
                    break;
                case (8):
                    winnersId = oneTieBreakerWinningCondition(players, 8);
                    break;
                case (7):
                    winnersId = twoTieBreakersWinningCondition(players, 7);
                    break;
                case (6):
                    winnersId = oneTieBreakerWinningCondition(players, 6);
                    break;
                case (5):
                    winnersId = oneTieBreakerWinningCondition(players, 5);
                    break;
                case (4):
                    winnersId = twoTieBreakersWinningCondition(players, 4);
                    break;
                case (3):
                    winnersId = threeTieBreakersWinningCondition(players, 3);
                    break;
                case (2):
                    winnersId = twoTieBreakersWinningCondition(players, 2);
                    break;
                case (1):
                    winnersId = oneTieBreakerWinningCondition(players, 1);
                    break;
                default:
                    System.out.println("Error overflow!");
                    System.exit(101);
            }
        }

        return winnersId;
    }

    public static ArrayList<Integer> oneTieBreakerWinningCondition(ArrayList<PokerPlayer> players, int maxWinningCondition) {
        int maxTieBreaker = 0;
        ArrayList<Integer> winnersId = new ArrayList<>();

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition) {
                if (maxTieBreaker < player.getTieBreaker()) {
                    maxTieBreaker = player.getTieBreaker();
                }
            }
        }

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker) {
                winnersId.add(player.getId());
            }
        }

        return winnersId;
    }

    public static ArrayList<Integer> twoTieBreakersWinningCondition(ArrayList<PokerPlayer> players, int maxWinningCondition) {
        int maxTieBreaker = 0;
        int maxSecondTieBreaker = 0;
        int winnerCounter = 0;
        ArrayList<Integer> winnersId = new ArrayList<>();

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition) {
                if (maxTieBreaker < player.getTieBreaker()) {
                    maxTieBreaker = player.getTieBreaker();
                }
            }
        }

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker) {
                winnerCounter++;
            }
        }

        if (winnerCounter == 1) {
            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker) {
                    winnersId.add(player.getId());
                    break;
                }
            }
        } else {
            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() > maxSecondTieBreaker) {
                    maxSecondTieBreaker = player.getSecondTieBreaker();
                }
            }

            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() == maxSecondTieBreaker) {
                    winnersId.add(player.getId());
                }
            }
        }

        return winnersId;
    }

    public static ArrayList<Integer> threeTieBreakersWinningCondition(ArrayList<PokerPlayer> players, int maxWinningCondition) {
        int maxTieBreaker = 0;
        int maxSecondTieBreaker = 0;
        int maxThirdTieBreaker = 0;
        int winnerCounter = 0;
        ArrayList<Integer> winnersId = new ArrayList<>();

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition) {
                if (maxTieBreaker < player.getTieBreaker()) {
                    maxTieBreaker = player.getTieBreaker();
                }
            }
        }

        for (PokerPlayer player : players) {
            if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker) {
                winnerCounter++;
            }
        }

        if (winnerCounter == 1) {
            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker) {
                    winnersId.add(player.getId());
                    break;
                }
            }
        } else {
            winnerCounter = 0;

            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() > maxSecondTieBreaker) {
                    maxSecondTieBreaker = player.getSecondTieBreaker();
                }
            }

            for (PokerPlayer player : players) {
                if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() == maxSecondTieBreaker) {
                    winnerCounter++;
                }
            }

            if (winnerCounter == 1) {
                for (PokerPlayer player : players) {
                    if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() == maxSecondTieBreaker) {
                        winnersId.add(player.getId());
                        break;
                    }
                }
            } else {
                for (PokerPlayer player : players) {
                    if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() == maxSecondTieBreaker && player.getKicker() > maxThirdTieBreaker) {
                        maxThirdTieBreaker = player.getKicker();
                    }
                }

                for (PokerPlayer player : players) {
                    if (player.getWinningCondition() == maxWinningCondition && player.getTieBreaker() == maxTieBreaker && player.getSecondTieBreaker() == maxSecondTieBreaker && player.getKicker() == maxThirdTieBreaker) {
                        winnersId.add(player.getId());
                    }
                }
            }
        }

        return winnersId;
    }
}
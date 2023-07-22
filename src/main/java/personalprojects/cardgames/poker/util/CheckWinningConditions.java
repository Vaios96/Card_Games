package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.model.Card;
import personalprojects.cardgames.model.Hand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class CheckWinningConditions {

    public static int getRankValue(Card card) {
        int i = 0;

        switch (card.getRank()) {
            case "2":
                i = 2;
                break;
            case "3":
                i = 3;
                break;
            case "4":
                i = 4;
                break;
            case "5":
                i = 5;
                break;
            case "6":
                i = 6;
                break;
            case "7":
                i = 7;
                break;
            case "8":
                i = 8;
                break;
            case "9":
                i = 9;
                break;
            case "10":
                i = 10;
                break;
            case "J":
                i = 11;
                break;
            case "Q":
                i = 12;
                break;
            case "K":
                i = 13;
                break;
            case "A":
                i = 14;
                break;
            default:
                System.out.println("Get Rank Value Default call!");
                break;
        }
        return i;
    }

    public static boolean checkRoyalFlush(Hand hand) {
        boolean tenExists = false;
        boolean jExists = false;
        boolean qExists = false;
        boolean kExists = false;
        boolean aExists = false;
        boolean royalFlush = false;
        int clubsCounter = 0;
        int diamondsCounter = 0;
        int heartsCounter = 0;
        int spadesCounter = 0;

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            switch (currentCard.getRank()) {
                case "10":
                    tenExists = true;
                    switch (currentCard.getSuit()) {
                        case "Clubs":
                            clubsCounter++;
                            break;
                        case "Diamonds":
                            diamondsCounter++;
                            break;
                        case "Hearts":
                            heartsCounter++;
                            break;
                        case "Spades":
                            spadesCounter++;
                            break;
                        default:
                            System.out.println("This was a mistake!!!");
                    }
                    break;
                case "J":
                    jExists = true;
                    switch (currentCard.getSuit()) {
                        case "Clubs":
                            clubsCounter++;
                            break;
                        case "Diamonds":
                            diamondsCounter++;
                            break;
                        case "Hearts":
                            heartsCounter++;
                            break;
                        case "Spades":
                            spadesCounter++;
                            break;
                        default:
                            System.out.println("This was a mistake!!!");
                    }
                    break;
                case "Q":
                    qExists = true;
                    switch (currentCard.getSuit()) {
                        case "Clubs":
                            clubsCounter++;
                            break;
                        case "Diamonds":
                            diamondsCounter++;
                            break;
                        case "Hearts":
                            heartsCounter++;
                            break;
                        case "Spades":
                            spadesCounter++;
                            break;
                        default:
                            System.out.println("This was a mistake!!!");
                    }
                    break;
                case "K":
                    kExists = true;
                    switch (currentCard.getSuit()) {
                        case "Clubs":
                            clubsCounter++;
                            break;
                        case "Diamonds":
                            diamondsCounter++;
                            break;
                        case "Hearts":
                            heartsCounter++;
                            break;
                        case "Spades":
                            spadesCounter++;
                            break;
                        default:
                            System.out.println("This was a mistake!!!");
                    }
                    break;
                case "A":
                    aExists = true;
                    ;
                    switch (currentCard.getSuit()) {
                        case "Clubs":
                            clubsCounter++;
                            break;
                        case "Diamonds":
                            diamondsCounter++;
                            break;
                        case "Hearts":
                            heartsCounter++;
                            break;
                        case "Spades":
                            spadesCounter++;
                            break;
                        default:
                            System.out.println("This was a mistake!!!");
                    }
                    break;
            }
        }

        if (tenExists && jExists && qExists && kExists && aExists) {
            if (clubsCounter >= 5 || diamondsCounter >= 5 || heartsCounter >= 5 || spadesCounter >= 5) {
                System.out.println("YOU LUCKY ...!!!!");
                royalFlush = true;
            }
        }

        return royalFlush;
    }

    public static int[] checkStraightFlush(Hand hand) {
        int[] straightFlush = {0, 0};
        int straightFlushMaxValue = 0;
        ArrayList<Integer> integerValues = new ArrayList<>();
        ArrayList<String> suitValues = new ArrayList<>();
        int clubsCounter = 0;
        int diamondsCounter = 0;
        int heartsCounter = 0;
        int spadesCounter = 0;
        int tmpContinuous = 1;
        int maxContinuous = 1;


        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
            suitValues.add(currentCard.getSuit());
        }

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            switch (currentCard.getSuit()) {
                case "Clubs":
                    clubsCounter++;
                    break;
                case "Diamonds":
                    diamondsCounter++;
                    break;
                case "Hearts":
                    heartsCounter++;
                    break;
                case "Spades":
                    spadesCounter++;
                    break;
                default:
                    System.out.println("This was a mistake!!!");
            }
        }

        if (clubsCounter >= 5 || diamondsCounter >= 5 || heartsCounter >= 5 || spadesCounter >= 5) {
            if (clubsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Clubs")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        i--;
                        counter--;
                    }
                    counter++;
                }
            } else if (diamondsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Diamonds")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        i--;
                        counter--;
                    }
                    counter++;
                }
            } else if (heartsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Hearts")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        i--;
                        counter--;
                    }
                    counter++;
                }
            } else {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Spades")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        i--;
                        counter--;
                    }
                    counter++;
                }
            }

            for (int j = 0; j < integerValues.size() - 1; j++) {
                if (integerValues.get(j) + 1 == integerValues.get(j + 1)) {
                    tmpContinuous++;
                    if (tmpContinuous > maxContinuous) {
                        maxContinuous = tmpContinuous;
                        straightFlushMaxValue = integerValues.get(j);
                    }
                } else {
                    tmpContinuous = 1;
                }
            }

            if (maxContinuous >= 5) {
                straightFlush[0] = 1;
                straightFlush[1] = straightFlushMaxValue;
            }
        }

        return straightFlush;
    }

    public static int[] checkFourOfAKind(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();
        int[] counter = new int[13];
        int[] fourOfAKind = {0, 0};

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 14; j++) {
                if (integerValues.get(i) == j) {
                    counter[j - 2]++;
                }
            }
        }

        for (int k = 0; k < counter.length; k++) {
            if (counter[k] == 4) {
                fourOfAKind[0] = 1;
                fourOfAKind[1] = k + 2;
            }
        }

        return fourOfAKind;
    }

    public static int[] checkFullHouse(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();
        int[] counter = new int[13];
        boolean threeOfARank = false;
        boolean twoOfARank = false;
        int[] fullHouse = {0, 0, 0};

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 14; j++) {
                if (integerValues.get(i) == j) {
                    counter[j - 2]++;
                }
            }
        }

        for (int i = 0; i < counter.length; i++) {
            if (counter[i] == 3) {
                threeOfARank = true;
                fullHouse[1] = i + 2;
            }
            if (counter[i] == 2) {
                twoOfARank = true;
                fullHouse[2] = i + 2;
            }
        }

        if (twoOfARank && threeOfARank) {
            fullHouse[0] = 1;
        }

        return fullHouse;
    }

    public static int[] checkFlush(Hand hand) {
        int[] flush = {0, 0};
        int flushMaxValue = 0;
        ArrayList<Integer> integerValues = new ArrayList<>();
        ArrayList<String> suitValues = new ArrayList<>();
        int clubsCounter = 0;
        int diamondsCounter = 0;
        int heartsCounter = 0;
        int spadesCounter = 0;

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
            suitValues.add(currentCard.getSuit());
        }

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            switch (currentCard.getSuit()) {
                case "Clubs":
                    clubsCounter++;
                    break;
                case "Diamonds":
                    diamondsCounter++;
                    break;
                case "Hearts":
                    heartsCounter++;
                    break;
                case "Spades":
                    spadesCounter++;
                    break;
                default:
                    System.out.println("This was a mistake!!!");
            }
        }

        if (clubsCounter >= 5 || diamondsCounter >= 5 || heartsCounter >= 5 || spadesCounter >= 5) {
            if (clubsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Clubs")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        i--;
                        counter--;
                    }
                    counter++;
                }
                flush[0] = 1;
                for (Integer integerValue : integerValues) {
                    if (integerValue > flushMaxValue) {
                        flushMaxValue = integerValue;
                    }
                }
                flush[1] = flushMaxValue;
            } else if (diamondsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Diamonds")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        counter--;
                        i--;
                    }
                    counter++;
                }
                flush[0] = 1;
                for (Integer integerValue : integerValues) {
                    if (integerValue > flushMaxValue) {
                        flushMaxValue = integerValue;
                    }
                }
                flush[1] = flushMaxValue;
            } else if (heartsCounter >= 5) {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Hearts")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        counter--;
                        i--;
                    }
                    counter++;
                }
                flush[0] = 1;
                for (Integer integerValue : integerValues) {
                    if (integerValue > flushMaxValue) {
                        flushMaxValue = integerValue;
                    }
                }
                flush[1] = flushMaxValue;
            } else {
                int counter = 0;
                for (int i = 0; i < integerValues.size(); i++) {
                    if (!Objects.equals(suitValues.get(i), "Spades")) {
                        integerValues.remove(counter);
                        suitValues.remove(counter);
                        counter--;
                        i--;
                    }
                    counter++;
                }
                flush[0] = 1;
                for (Integer integerValue : integerValues) {
                    if (integerValue > flushMaxValue) {
                        flushMaxValue = integerValue;
                    }
                }
                flush[1] = flushMaxValue;
            }

        }

        return flush;
    }

    public static int[] checkStraight(Hand hand) {
        int[] straight = {0, 0};
        int straightMaxValue = 0;
        int tmpContinuous = 1;
        int maxContinuous = 0;
        ArrayList<Integer> integerValues = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        integerValues.sort(Comparator.naturalOrder());

        for (int j = 0; j < integerValues.size() - 1; j++) {
            if (integerValues.get(j) + 1 == integerValues.get(j + 1)) {
                tmpContinuous++;
                if (tmpContinuous > maxContinuous) {
                    maxContinuous = tmpContinuous;
                    straightMaxValue = integerValues.get(j);
                }
            } else {
                tmpContinuous = 1;
            }
        }

        if (maxContinuous >= 5) {
            straight[0] = 1;
            straight[1] = straightMaxValue;
        }

        return straight;
    }

    public static int[] checkThreeOfAKind(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();
        int[] counter = new int[13];
        int[] threeOfAKind = {0, 0, 0};
        int kicker = 0;

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 14; j++) {
                if (integerValues.get(i) == j) {
                    counter[j - 2]++;
                }
            }
        }

        for (int k = 0; k < counter.length; k++) {
            if (counter[k] == 3) {
                threeOfAKind[0] = 1;
                threeOfAKind[1] = k + 2;
            }
        }

        for (Integer integerValue : integerValues) {
            if (integerValue > kicker && integerValue != threeOfAKind[1]) {
                kicker = integerValue;
            }
        }

        threeOfAKind[2] = kicker;

        return threeOfAKind;
    }

    public static int[] checkTwoPair(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();
        int[] counter = new int[13];
        int pairCounter = 0;
        int highPair = 0;
        int lowPair = 0;
        int[] twoPair = {0, 0, 0, 0};
        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        integerValues.sort(Comparator.naturalOrder());

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 14; j++) {
                if (integerValues.get(i) == j) {
                    counter[j - 2]++;
                    if (counter[j-2] == 2) {
                        pairCounter++;
                        if (j > highPair) {
                            lowPair = highPair;
                            highPair = j;
                        }
                    }
                }
            }
        }

        if (pairCounter >= 2) {
            twoPair[0] = 1;
            twoPair[1] = highPair;
            twoPair[2] = lowPair;

            for (int i = 0; i < integerValues.size(); i++) {
                if (integerValues.get(i) == highPair || integerValues.get(i) == lowPair) {
                    integerValues.remove(i);
                    i--;
                }
            }

            twoPair[3] = integerValues.get(integerValues.size() - 1);
        }


        return twoPair;
    }

    public static int[] checkPair(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();
        int[] counter = new int[13];
        boolean found = false;
        int[] pair = {0, 0, 0};

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        integerValues.sort(Comparator.naturalOrder());

        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 14; j++) {
                if (integerValues.get(i) == j) {
                    counter[j - 2]++;
                    if (counter[j-2] == 2) {
                        pair[0] = 1;
                        pair[1] = j;
                        found = true;
                        break;
                    }
                }
            }
            if (found) break;
        }

        if (found) {
            for (int i = 0; i < integerValues.size(); i++) {
                if (integerValues.get(i) == pair[1]) {
                    integerValues.remove(i);
                    i--;
                }
            }
            pair[2] = integerValues.get(integerValues.size() - 1);
        }

        return pair;
    }

    public static int checkHighCard(Hand hand) {
        ArrayList<Integer> integerValues = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Card currentCard = hand.getCard(i);
            integerValues.add(getRankValue(currentCard));
        }

        integerValues.sort(Comparator.naturalOrder());

        return integerValues.get(integerValues.size() - 1);
    }
}

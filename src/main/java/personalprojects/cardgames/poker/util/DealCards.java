package personalprojects.cardgames.poker.util;

import personalprojects.cardgames.model.Card;
import personalprojects.cardgames.model.Deck;
import personalprojects.cardgames.model.Hand;
import personalprojects.cardgames.poker.PokerPlayer;

import java.util.ArrayList;

public class DealCards {

    public static void dealAllPlayers(Deck deck, ArrayList<PokerPlayer> playersInfo, Hand cardsOnTable) {
        int cardToDeal = 0;
        Card card;
        deck.create();
        deck.shuffle();
        for (int i = 0; i < 2; i++) {
            for (PokerPlayer pokerPlayer : playersInfo) {
                card = deck.dealCard(cardToDeal);
                pokerPlayer.addCard(card);
                cardToDeal++;
            }
        }

        // Dealer has to burn one card after dealing cards to players and now deals the flop
        card = deck.dealCard(++cardToDeal);
        cardsOnTable.addCard(card);
        cardToDeal++;
        card = deck.dealCard(cardToDeal);
        cardsOnTable.addCard(card);
        cardToDeal++;
        card = deck.dealCard(cardToDeal);
        cardsOnTable.addCard(card);

        //Burn one and deal turn
        card = deck.dealCard(++cardToDeal);
        cardsOnTable.addCard(card);
        cardToDeal++;

        //Burn one and deal river
        card = deck.dealCard(++cardToDeal);
        cardsOnTable.addCard(card);
    }
}

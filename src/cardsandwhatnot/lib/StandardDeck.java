/*
 * The MIT License
 *
 * Copyright 2018 William Gollinger.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cardsandwhatnot.lib;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author William Gollinger
 */
public class StandardDeck extends Hand implements Deck {
    
  // inherits from Hand: List<Card> cards
  
  public StandardDeck() {
    cards = new ArrayList<>();
    refresh();
  }
  
  // implementing Deck 
  // methods implemented by Hand: sort(), get/addCard(s)
  @Override
  public Map<Integer, Hand> deal(int players, int cardsEach)
           throws IllegalArgumentException 
  {
    // code is from Oracle tutorial Default Methods
    // used Hands instead of Decks
    int cardsDealt = players * cardsEach;
    int sizeOfDeck = cards.size();
    if (cardsDealt > sizeOfDeck) {
      throw new IllegalArgumentException(
        "Number of players (" + players +
        ") times number of cards to be dealt (" + cardsEach +
        ") is greater than the number of cards in the deck (" +
        sizeOfDeck + ").");
    }
    
    // sets up the correspondance "Player -> Hand"
    Map<Integer, List<Card>> dealtDeck = cards
            .stream()
            .collect(
              Collectors.groupingBy(
                card -> {
                  int cardIndex = cards.indexOf(card);
                  if (cardIndex >= cardsDealt) return (players + 1);
                  else return (cardIndex % players) + 1;
                }));
    Map<Integer, Hand> mapToReturn = new HashMap<>();
    
    // turns "hands" into genuine Hands
    for (int i = 1; i <= players; i++) {
      Hand currentHand = new Hand();
      currentHand.addCards(dealtDeck.get(i));
      mapToReturn.put(i, currentHand);
    }
    // remove the cards you just dealt
    for (int i = cardsDealt - 1; i>=0; i--) {
      cards.remove(i);
    }
    return mapToReturn;
  }
  @Override
  public void shuffle(){
    Collections.shuffle(cards);
  }
  @Override
  public void refresh() { 
    cards = new ArrayList<>();
    for (StandardCard.Suit suit : StandardCard.Suit.values()) {
      for (StandardCard.Rank rank : StandardCard.Rank.values() ) {
        addCard(new StandardCard(rank, suit));
      }
    }
  }
}

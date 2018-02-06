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

/**
 *
 * @author William Gollinger
 */
public class Hand {
  List<Card> cards;  // Maintains a sorted list of cards.

  public Hand() {
    cards = new ArrayList<>();
  }
  public Hand(List<Card> cards) { // Have to be careful about Array.asList() not returning ArrayList
    this.cards = new ArrayList<>(cards);
    sort();
  }

  public String toString() {
    String hand = "";
    for (Card card : cards) {
      hand += card.toString() + "\n";
    }
    return (hand.equals("")) ? "EMPTY" : hand;
  }
  public List<Card> getCards() {
    return cards;
  }
  public int size() {
    return cards.size();
  }
  public void addCard(Card card) {
    addCards(Arrays.asList(card));
  }
  public void addCards(List<Card> otherCards) {
    for (Card card : otherCards) {
      cards.add(card);
    }
    sort();
  }
  // For removeCards it is important that subCards be a subset of cards.
  // It will return null otherwise.
  public List<Card> removeCards(List<Card> subCards) {
    List<Card> cardsCopy = cards;
    for (Card card : subCards) {
      if (!cardsCopy.remove(card)) {
        return null;
      }
    }
    cards = cardsCopy;
    return subCards;
  }
  public Card removeCard(Card card) {
    return (cards.remove(card)) ? card : null;
  }
  public void sort() {
    Collections.sort(cards);
  }
  
  public boolean hasSuit(ValueTextEnum suit){
    for (Card card : cards) {
      if (card.getSuit() == suit) {
        return true;
      }
    }
    return false;
  }
  public boolean hasRank(ValueTextEnum rank){
    for (Card card : cards) {
      if (card.getRank() == rank) {
        return true;
      }
    }
    return false;
  }
  public boolean hasCard(Card other) {
    for (Card card : cards) {
      if (card.equals(other)) {
        return true;
      }
    }
    return false;
  }
  public Card getFirst(ValueTextEnum suit) {
    for (Card card : cards) {
      if (card.getSuit() == suit) {
        return card;
      }
    }
    return null;
  }
  public Card getLast(ValueTextEnum suit) {
    boolean found = false;
    for (int i=0; i < cards.size(); i++) {
      if (cards.get(i).getSuit() == suit) {
        found = true;
      } else if (found == true) {
        return cards.get(i-1);
      }
    }
    return cards.get(cards.size()-1);
  }
}

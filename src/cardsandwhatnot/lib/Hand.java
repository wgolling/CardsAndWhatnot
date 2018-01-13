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
  List<Card> cards; 

  public Hand() {
    cards = new ArrayList<>();
  }
  public Hand(List<Card> cards) { // Have to be careful about Array.asList() not returning ArrayList
    this.cards = new ArrayList<>(cards);
  }

  public List<Card> getCards() {
    return cards;
  }
  void addCard(Card card) {
    cards.add(card);
  }
  void addCards(List<Card> otherCards) {
    for (Card card : otherCards) {
      cards.add(card);
    }
  }
  // For removeCards it is important that subCards be a subset of cards.
  // It will return null otherwise.
  boolean removeCards(List<Card> subCards) {
    List<Card> cardsCopy = cards;
    for (Card card : subCards) {
      if (!cardsCopy.remove(card)) {
        return false;
      }
    }
    cards = cardsCopy;
    return true;
  }
  public void sort() {
    Collections.sort(cards);
  }
 
}

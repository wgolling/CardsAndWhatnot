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
public class Player {
  String name;
  String password;  
  boolean isHuman;
  Map<String, Integer> wins;
  Hand hand;
    
  public Player(String name, String password, boolean isHuman) {
    this.name = name;
    this.password = password;
    this.isHuman = isHuman;
    wins = new HashMap<>();
    hand = new Hand();
  }
  
  public String getName() {
    return name;
  }
  public boolean isHuman() {
    return isHuman;
  }
  public void winGame(String gameName) {
    if (wins.get(gameName) == null) {
      wins.put(gameName, 1);
    } else {
      wins.put(gameName, wins.get(gameName) + 1);
    }
  }
  public int getWins(String gameName) {
    return (wins.get(gameName) == null) ? 0 : wins.get(gameName);
  }
  public Hand getHand() {
    return hand;
  }
  public int drawCards(Deck deck, int amount) {
    int drawAmount = Math.min(amount, deck.size());
    Hand newHand = deck.deal(1, drawAmount).get(1);
    hand.addCards(newHand.getCards());
    return drawAmount;
  }
}

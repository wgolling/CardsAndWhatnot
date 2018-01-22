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

/**
 *
 * @author William Gollinger
 */
public class DisplayData {
  // Indexes players in turn order;
  String[] players; 
  int[] scores;
  // Indexes hands, first variable indexes players, second cards in hand
  // Each card must is represented as "Rank.symbol()Suit.symbol()"
  String[][] hands; 
  // Indexes cards on table, first variable indexes piles
  String[][] tableCards; 
  
  public DisplayData(String[] players, int[] scores, 
                     String[][] hands, String[][] tableCards ) {
    this.players = players;
    this.scores = scores;
    this.hands = hands;
    this.tableCards = tableCards;
  }
  public DisplayData() {
    this(new String[0], new int[0], new String[0][0], new String[0][0]);
  }

  public String[] getPlayers() {
    return players;
  }
  public void setPlayers(String[] players) {
    this.players = players;
  }
  public int[] getScores() {
    return scores;
  }
  public void setScores(int[] scores) {
    this.scores = scores;
  }
  public String[][] getHands() {
    return hands;
  }
  public void setHands(String[][] hands) {
    this.hands = hands;
  }
  public String[][] getTableCards() {
    return tableCards;
  }
  public void setTableCards(String[][] tableCards) {
    this.tableCards = tableCards;
  }
}

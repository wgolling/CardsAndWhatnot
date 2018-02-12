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
package cardsandwhatnot.io;

import cardsandwhatnot.lib.Card;
import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class DisplayData {
  // TODO? amalgamate into a List of PlayerState objects
  // Indexes players in turn order;
  List<String>     players; 
  List<String>     scores;
  List<String>     roundScores;
  List<List<Card>> hands; 
  List<List<Card>> takenCards;
  List<Card>       tableCards; 
  int currentPlayer;
  
  public DisplayData(List<String> players, List<String> scores, 
                     List<List<Card>> hands, List<Card> tableCards ) {
    this.players = players;
    this.scores = scores;
    this.hands = hands;
    this.tableCards = tableCards;
    currentPlayer = 0;
  }
  public DisplayData() {
    this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
  }

  /*
  DisplayData instances may contain references to delicate information, like a Player's cards.
  Its getting functions therefore only return a copy of the list.
  */
  public List<String> getPlayers() {
    return new ArrayList<>(players);
  }
  public List<String> getScores() {
    return new ArrayList<>(scores);
  }
  public List<String> getRoundScores() {
    return new ArrayList<>(roundScores);
  }
  public List<List<Card>> getHands() {
    return new ArrayList<>(hands);
  }
  public List<Card> getTableCards() {
    return new ArrayList<>(tableCards);
  }
  public int getCurrentPlayer() {
    return currentPlayer;
  }
  // special getter methods => non-public fields => setter methods
  public void setPlayers(List<String> players) {
    this.players = players;
  }
  public void setScores(List<String> scores) {
    this.scores = scores;
  }
  public void setRoundScores(List<String> roundScores) {
    this.roundScores = roundScores;
  }
  public void setHands(List<List<Card>> hands) {
    this.hands = hands;
  }
  public void setTableCards(List<Card> tableCards) {
    this.tableCards = tableCards;
  }
  public void setCurrentPlayer(int currentPlayer) {
    this.currentPlayer = currentPlayer;
  }
}

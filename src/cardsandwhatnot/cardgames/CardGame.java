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
package cardsandwhatnot.cardgames;

import java.util.*;
import cardsandwhatnot.lib.*;

/**
 *
 * @author William Gollinger
 */
public class CardGame {
  String name;
  List<Player> players;
  int currentPlayer;
  Deck gameDeck;
  boolean roundOver;
  boolean gameOver;
  
  public CardGame(List<Player> players) {
    name = "BLANK";
    this.players = players;
    currentPlayer = 0;
    gameDeck = null; // Should be set by game.
    roundOver = (gameOver = false); // If your game has no rounds, set roundOver = true in your initializer.
  }
  @Override
  public String toString() {return name;}
  List<Player> getPlayers() {return players;}
  int getCurrentPlayer() {return currentPlayer;}
  // To write new game, just have to override these methods.
  private void setupGame(){}
  private void resolveGame(){}
  private void setupRound(){}
  private void resolveRound(){}
  private void setupPlay(){}
  private void executePlay(){}
  private void resolvePlay(){}
  public void run() {
    setupGame();
    while (!gameOver) {
      setupRound();
      do {
        setupPlay();
        executePlay();
        resolvePlay();
      } while(!roundOver); // if roundOver always true, each "Round" only has one Play
      resolveRound();
    }
    resolveGame(); 
  }
  
}

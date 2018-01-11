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

import cardsandwhatnot.lib.Player;

/**
 *
 * @author William Gollinger
 */
public interface CardGame {
  Player[] getPlayers();
  Player getCurrentPlayer();
  
  boolean hasRounds();
  String getGameType(); //?
  
  boolean validatePlay(String card);
  
  void setupGame();
  void resolveGame();
  void setupRound();
  boolean resolveRound();
  void setupPlay();
  void executePlay();
  boolean resolvePlay();
  
  default void run() {
    boolean gameOver = false;
    setupGame();
    while (!gameOver) {
      if (hasRounds()) {
        boolean roundOver = false;
        setupRound();
        while(!roundOver) {
          setupPlay();
          executePlay();
          roundOver = resolvePlay();
        }
        gameOver = resolveRound();
      } else {
        setupPlay();
        executePlay();
        gameOver = resolvePlay();
        
      }
    }
    resolveGame();
  }
  
}

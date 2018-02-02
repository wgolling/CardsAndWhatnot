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

import cardsandwhatnot.io.DisplayData;
import java.util.*;
import cardsandwhatnot.lib.*;
import cardsandwhatnot.io.CardGameUI;

/**
 *
 * @author William Gollinger
 */
public class CardGame {
  String name;
  String cardType;
  String UIType;
  List<Player> players;
  List<Hand> hands;
  List<Integer> scores;
  int currentPlayer;
  public Deck gameDeck;
  List<Card> tableCards;
  boolean roundOver;
  boolean gameOver;
  
  CardGameUI UI;
  DisplayData data; // A data package to send to the UI.
  
  public CardGame(List<Player> players) {
    name = "BLANK";
    cardType = "BLANK";
    UIType = "BLANK";
    this.players = players;
    hands = new ArrayList<>();
    for (Player player : players) {
      hands.add(player.getHand());
    }
    currentPlayer = 0;
    gameDeck = null; // Should be set by game; e.g. Hearts and Uno use different decks.
    tableCards = new ArrayList<>();
    roundOver = (gameOver = false); 
    data = new DisplayData();
  }
  @Override
  public String toString() {return name;}
  public void setName(String name) {this.name = name;}
  public String getCardType() {return cardType;}
  List<Player> getPlayers() {return players;}
  int getCurrentPlayer() {return currentPlayer;}
  
  // move these two to GameEngine?
  // returns a valid card, according to the game's validatePlay method
  Card requestCard(Player player) {
    Card card = null;
    if (player.isHuman()) {
      card = UI.promptCard();
      while (!validatePlay(card)) {
        card = UI.promptCard();
      }
    } else {
      defaultCard();
    }
    return card;
  }
  // The CardGame is played by calling its run() method. 
  public void run() {
    setupGame();
    while (!gameOver) {
      setupRound();
      do {
        setupPlay();
        Card play = requestCard(players.get(currentPlayer));
        resolvePlay(play);
      } while(!roundOver); // If roundOver always true, each "Round" only has one Play.
      resolveRound();
    }
    resolveGame(); 
    // display final results
  }
  /* 
  * To write new CardGame, just have to override the methods below.
  * If your game has no rounds then set roundOver = true in your initializer,
  *   and leave setup/resolveRound() blank.
  */  
  boolean validatePlay(Card card) {
    // test if a card is valid in the current game state
    return false;
  }
  Card defaultCard() {
    // implement AI for chosing a card
    return null;
  }
  void setupGame(){}
  void resolveGame(){}
  void setupRound(){}
  void resolveRound(){}
  void setupPlay(){}
  void resolvePlay(Card card){}
  
}

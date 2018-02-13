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
package cardsandwhatnot.games;

import cardsandwhatnot.graphics.DisplayData;
import cardsandwhatnot.lib.*;
import cardsandwhatnot.graphics.GraphicsEngine;
import cardsandwhatnot.input.InputProcessor;
import java.util.stream.*;

/**
 *
 * @author William Gollinger
 */
public class CardGameEngine {
  
  CardGame game;
  DisplayData data;
  GraphicsEngine graphics;
  InputProcessor parser;
  
  public CardGameEngine(CardGame game, GraphicsEngine graphics, InputProcessor parser) {
    this.game = game;
    data = new DisplayData();
    this.graphics = graphics;
    this.graphics.setData(data);
    this.parser = parser;
  }
  /**
   * Runs through the "game", "round", and "trick" loops, executing "plays". 
   * Calls the Game's corresponding setup- and resolve- methods.
   */
  public void playGame() {
    game.setupGame();
    while (game.gameOver == false) {
      game.setupRound();
      do {
        game.setupTrick();
        do {
          game.setupPlay();
          Card play = requestCard(game.players.get(game.currentPlayer));
          game.resolvePlay(play);
        } while(game.trickOver == false);
        game.resolveTrick();
        promptTrickOver("Trick Over\n");
      } while(game.roundOver == false); // If roundOver always true, each "Round" only has one Play.
      game.resolveRound();
      promptRoundOver("Round Over\n");
    }
    game.resolveGame(); 
    promptGameOver("Game Over\n");
  }
  /**
   * Updates the DisplayData field.  
   * This automatically updates graphics' data as well, since it refers to 
   * the same object.
   */
  void updateData() {
    // TODO: rewrite with a generic helper method that takes a List<E> and a lambda
    data.setPlayers(game.players
            .stream().map(e -> e.getName()).collect(Collectors.toList()) );   // java y u no hav succinct functional operations
    data.setScores(game.scores
            .stream().map(e -> Integer.toString(e)).collect(Collectors.toList()) );
    data.setRoundScores(game.roundScores
            .stream().map(e -> Integer.toString(e)).collect(Collectors.toList()) );
    data.setHands(game.players
            .stream().map(e -> e.getHand().getCards() ).collect(Collectors.toList()) );
    data.setTableCards(game.tableCards);
    data.setCurrentPlayer(game.currentPlayer);
    data.setLeadPlayer(game.leadPlayer);
    data.setWinners(game.winners);
  }
  /**
   * Returns a card from the Player's hand, which is valid according to
   * the Game's validatePlay method.
   * @param player
   * @return 
   */
  Card requestCard(Player player) {
    Card card;
    if (player.isHuman()) {
      card = promptCard();
      String message = "I think you said " + card.toString() + "\n";
      while (!game.validatePlay(card) || !(player.getHand().hasCard(card))) {
        if ( !(player.getHand().hasCard(card)) ) {
          message += player.getName() + " doesn't have " + card.toString() +".\n";
        }
        if (!game.validatePlay(card)) {
          message += "Not a valid Play.\n";
        }
        card = promptCard(message);
      }
    } else {
      card = game.defaultCard();
    }
    player.getHand().removeCard(card);
    return card;
  }
  
  /*
  prompts
  */
  
  /**
   * A basic prompt to put breaks in the action.
   * @param message 
   */
  void promptContinue(String message) {
    updateData();
    graphics.drawTable();
    parser.promptContinue(message);
  }
  void promptTrickOver(String message) {
    updateData();
    graphics.drawTrickResults();
    parser.promptContinue(message);
  }
  void promptRoundOver(String message) {
    updateData();
    graphics.drawRoundResults();
    parser.promptContinue(message);
  }
  void promptGameOver(String message) {
    updateData();
    graphics.drawGameResults();
    parser.promptContinue(message);
  }
  /**
   * Attempts to get a Card from the user.
   * Will not return a non-null value.
   * @param message
   * @return 
   */
  Card promptCard(String message) {
    updateData();
    graphics.drawCardPrompt();
    Card card = validateCard(parser.promptCard(message));
    while (card == null) {
      card = validateCard(parser.promptCard("Card not recognized."));
    }
    return card;
  }
  Card promptCard() {
    return promptCard("");
  }
  /**
   * Determines if cardParts represent a card of the given type.
   * Returns null if it does not.
   * @param cardParts
   * @return 
   */
  Card validateCard(String[] cardParts) {
    switch (game.cardType) {
      case "STANDARD" : return StandardCard.makeCard(cardParts[0], cardParts[1]);
      //case "UNO" : return UnoCard.makeCard(card[0], card[1]);
      default : return StandardCard.makeCard(cardParts[0], cardParts[1]);
    }
  }
  
}

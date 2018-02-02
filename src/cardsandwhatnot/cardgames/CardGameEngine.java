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

import cardsandwhatnot.io.*;
import cardsandwhatnot.lib.Card;
import cardsandwhatnot.lib.Player;
import cardsandwhatnot.lib.StandardCard;

/**
 *
 * @author William Gollinger
 */
public class CardGameEngine {
  
  CardGame game;
  GraphicsEngine graphics;
  InputProcessor parser;
  
  public CardGameEngine(CardGame game, GraphicsEngine graphics, InputProcessor parser) {
    this.game = game;
    this.graphics = graphics;
    this.graphics.setData(game.data);
    this.parser = parser;
  }
  
  public void playGame() {
    game.setupGame();
    while (!game.gameOver) {
      game.setupRound();
      do {
        game.setupPlay();
        Card play = requestCard(game.players.get(game.currentPlayer));
        game.resolvePlay(play);
      } while(!game.roundOver); // If roundOver always true, each "Round" only has one Play.
      game.resolveRound();
    }
    game.resolveGame(); 
    // display final results
  }
  // returns a valid card, according to the game's validatePlay method
  Card requestCard(Player player) {
    Card card;
    if (player.isHuman()) {
      card = promptCard();
      while (!game.validatePlay(card)) {
        card = promptCard();
      }
    } else {
      card = game.defaultCard();
    }
    return card;
  }
  // Use graphics and parser to get a Card from a human user.
  Card promptCard() {
    graphics.drawTable();
    Card card = validateCard(parser.promptCard(""));
    while (card == null) {
      card = validateCard(parser.promptCard("Card not recognized."));
    }
    return card;
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

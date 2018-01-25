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
package cardsandwhatnot.io.console;

import cardsandwhatnot.lib.Card;
import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class ConsoleCardGraphics {
  LayeredCharCanvas canvas;
  Map<String, LayeredCharCanvas.Box> hands;
  
  // card dimensions
  private final int CARD_HEIGHT = 6;
  private final int CARD_WIDTH = 5;
  // basic card graphics
  private final char TOP_BORDER = '-';
  private final char SIDE_BORDER = '|';
  private final char UL_CORNER = '/';
  private final char UR_CORNER = '\\';
  private final char BL_CORNER = '\\';
  private final char BR_CORNER = '/';
  private final char WHITE_SPACE = ' ';
  // card templates
  private final char[][] CARD_SPACE = new char[CARD_HEIGHT][CARD_WIDTH];           // A transparent card-sized box, representing the empty-hand.
  private final char[][] CARD;
  private char[][] PARTIAL_CARD;                                             // Partial cards are for fanned hands.
  private char[][] PARTIAL_CARD_WIDE;                                        // Unfortunately, some ranks have 2 characters.
    
//  class HandCards {
//    int y; // coordinates representing the center of the hand
//    int x;
//    List<Card> cards; 
//    
//    public CardGroup(List<Card> cards) {
//      this.cards = cards;
//    }
//    public CardGroup() {
//      this(new ArrayList<>());
//    }
//    
//    List<LayeredCharCanvas.Box> makeBoxes() {
//      
//    } 
//  }
  
  public ConsoleCardGraphics(int windowHeight, int windowWidth) {
    // Construct canvas with specified layers.
    canvas = new LayeredCharCanvas(windowHeight, windowWidth);
    canvas.addLayer("BACKGROUND");
    canvas.addLayer("PLAYERS");
    canvas.addLayer("SCORES");
    canvas.addLayer("HANDS");
    canvas.addLayer("TABLE");
    // Construct card templates.
    CARD = new char[CARD_HEIGHT][CARD_WIDTH];
    CARD[0][0] = UL_CORNER;
    CARD[0][CARD_WIDTH-1] = UR_CORNER;
    CARD[CARD_HEIGHT-1][0] = BL_CORNER;
    CARD[CARD_HEIGHT-1][CARD_WIDTH-1] = BR_CORNER;
    for (int j=1; j < CARD_HEIGHT-1; j++) {
      CARD[j][0] = CARD[j][CARD_WIDTH-1] = SIDE_BORDER;
    }
    for (int i=1; i < CARD_WIDTH-1; i++) {
      CARD[0][i] = CARD[CARD_HEIGHT-1][i] = TOP_BORDER;
      for (int j=1; j < CARD_HEIGHT-1; j++) {
        CARD[j][i] = WHITE_SPACE;
      }
    }
    PARTIAL_CARD = new char[CARD_HEIGHT][2];                                  // Partial cards are just sub-regions; assignment automatically truncates sizes.
    PARTIAL_CARD = CARD;
    PARTIAL_CARD_WIDE = new char[CARD_HEIGHT][3];
    PARTIAL_CARD_WIDE = CARD;
  }
  /**
   * Draws a partial card template to a target canvas at given coordinates.
   * @param card
   * @param target
   * @param y
   * @param x 
   */
  void drawPartialCard(Card card, char[][] target, int y, int x) {
    String rank = card.getRank().symbol();
    String suit = card.getSuit().symbol();
    if (rank.length() == 2) {
      LayeredCharCanvas.copyCanvas(PARTIAL_CARD_WIDE, target, y, x);
      target[y+1][x+2] = rank.charAt(1);
    } else {
      LayeredCharCanvas.copyCanvas(PARTIAL_CARD, target, y, x);
    }
    target[y+1][x+1] = rank.charAt(0);
    target[y+2][x+1] = suit.charAt(1);
  }
  /**
   * Draws a full card template to a target canvas at given coordinates.
   * @param card
   * @param target
   * @param y
   * @param x 
   */
  void drawCard(Card card, char[][] target, int y, int x) {
    LayeredCharCanvas.copyCanvas(CARD, target, y, x);
    String rank = card.getRank().symbol();
    String suit = card.getSuit().symbol();
    if (rank.length() == 2) {
      target[y+1][x+2] = target[CARD_HEIGHT-2][CARD_WIDTH-2] = rank.charAt(1);
      target[CARD_HEIGHT-2][CARD_WIDTH-3] = rank.charAt(0);
    } else {
      target[CARD_HEIGHT-2][CARD_WIDTH-2] = rank.charAt(0);
    }
    target[y+1][x+1] = rank.charAt(0);
    target[y+2][x+1] = target[CARD_HEIGHT-3][CARD_WIDTH-2] = suit.charAt(0);
  }
  /**
   * Given a list of cards, returns a char[][] depicting them as a fan.
   * @param cards
   * @return 
   */
  char[][] makeHand(Card[] cards) {
    if (cards.length == 1) {
      return makeCard(cards[0]);
    }
    Card[] fanCards = new Card[cards.length-1];
    fanCards = cards;
    int fanWidth = 0;
    for (Card card : fanCards) {
      fanWidth += 1 + card.rankWidth();
    }
    char[][] hand = new char[CARD_HEIGHT][fanWidth+CARD_WIDTH];
    int offset = 0;
    for (Card card : fanCards) {
      drawPartialCard(card, hand, 0, offset);
      offset += 1 + card.rankWidth();
    }
    drawCard(cards[cards.length-1], hand, 0, offset);
    return hand;
  }
  /**
   * Makes a char[][] depicting the given card.
   * @param card
   * @return 
   */
  char[][] makeCard(Card card) {
    char[][] cardBox = new char[CARD_HEIGHT][CARD_WIDTH];
    drawCard(card, cardBox, 0, 0);
    return cardBox;
  }
  /**
   * Updates a box with a new hand display.  Assumes the box was being used 
   * as a hand to begin with.
   * @param hand
   * @param cards 
   */
  void updateHand(LayeredCharCanvas.Box hand, Card[] cards) {
    hand.setContent(makeHand(cards));
  }
  
}

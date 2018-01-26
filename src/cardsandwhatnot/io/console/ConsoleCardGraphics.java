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
 * ConsoleCardGraphics has special functions for drawing card boxes, and
 * uses a LayeredCanvas to arrange and display them.
 * @author William Gollinger
 */
public class ConsoleCardGraphics {
  // TODO: use DisplayData to produce table view, score view etc.
  
  LayeredCharCanvas canvas;
  Map<String, LayeredCharCanvas.Box> hands;
  String[] players;
  int currentPlayer;
  
  // card dimensions
  final int CARD_HEIGHT = 6;
  final int CARD_WIDTH = 5;
  // basic card graphics
  final char TOP_BORDER = '-';
  final char SIDE_BORDER = '|';
  final char UL_CORNER = ' ';
  final char UR_CORNER = ' ';
  final char BL_CORNER = ' ';
  final char BR_CORNER = ' ';
  final char WHITE_SPACE = ' ';
  // card templates
  final char[][] CARD_SPACE = new char[CARD_HEIGHT][CARD_WIDTH];           // A transparent card-sized box, representing the empty-hand.
  final char[][] CARD;
  char[][] PARTIAL_CARD;                                             // Partial cards are for fanned hands.
  char[][] PARTIAL_CARD_WIDE;                                        // Unfortunately, some ranks have 2 characters.
  // background
  final char[][] BACKGROUND;
  // special coordinates
  //   north, east, south, west, centre, etc.
  // 
  
  /**
   * Constructor takes a window size, which is unchangeable.
   * It adds the layers the class will use to the LayeredCanvas, and 
   * constructs the card templates.
   * @param windowHeight
   * @param windowWidth 
   */    
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
    // Draw default background
    // Background "holds the window open", in the sense that it fills it with non-null characters.
    BACKGROUND = new char[windowHeight][windowWidth];
    for (int i=1; i< windowWidth-1; i++) {
      BACKGROUND[0][i] = BACKGROUND[windowHeight-1][i] = TOP_BORDER;
    }
    for (int j=1; j< windowHeight-1; j++) {
      BACKGROUND[j][0] = BACKGROUND[j][windowWidth-1] = SIDE_BORDER;
      for (int i=1; i<windowWidth-1; i++) {
        BACKGROUND[j][i] = WHITE_SPACE;
      }
    }
    BACKGROUND[0][0] = BACKGROUND[windowHeight-1][0] 
                     = BACKGROUND[0][windowWidth-1]
                     = BACKGROUND[windowHeight-1][windowWidth-1] = WHITE_SPACE;
    canvas.pinContentToLayer(BACKGROUND, "BACKGROUND", 0, 0);
  }
  public char[][] makeOutput() {
    return canvas.draw();
  }
  /**
   * Draws a partial card template to a target char[][] at given coordinates.
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
    target[y+2][x+1] = suit.charAt(0);
  }
  /**
   * Draws a full card template to a target char[][] at given coordinates.
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
      target[y+1][x+2] = target[y+CARD_HEIGHT-2][x+CARD_WIDTH-3] = rank.charAt(1);
    }
    target[y+1][x+1] = target[y+CARD_HEIGHT-2][x+CARD_WIDTH-2] = rank.charAt(0);
    target[y+2][x+1] = target[y+CARD_HEIGHT-3][x+CARD_WIDTH-2] = suit.charAt(0);
  }
  /**
   * Given a list of cards, returns a char[][] depicting them as a fan.
   * @param cards
   * @return 
   */
  public char[][] makeHand(List<Card> cards) {
    if (cards.size() == 1) {
      return makeCard(cards.get(0));
    }
    Card lastCard = cards.remove(cards.size()-1);
    int fanWidth = 0;
    for (Card card : cards) {
      fanWidth += 1 + card.rankWidth();
    }
    char[][] hand = new char[CARD_HEIGHT][fanWidth+CARD_WIDTH];
    int offset = 0;
    for (Card card : cards) {
      drawPartialCard(card, hand, 0, offset);
      offset += 1 + card.rankWidth();
    }
    drawCard(lastCard, hand, 0, offset);
    return hand;
  }
  public LayeredCharCanvas.Box pinHand(List<Card> cards, int y, int x) {
    return canvas.pinContentToLayer(makeHand(cards), "HANDS", y, x);
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
  void updateHand(LayeredCharCanvas.Box hand, List<Card> cards) {
    hand.setContent(makeHand(cards));
  }
}

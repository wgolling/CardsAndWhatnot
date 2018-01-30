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
import cardsandwhatnot.io.DisplayData;
import java.util.*;

/**
 * ConsoleCardGraphics has special functions for drawing card boxes, and
 * uses a LayeredCanvas to arrange and display them.
 * @author William Gollinger
 */
public class ConsoleCardGraphics {
  // TODO: use DisplayData to produce table view, score view etc.
  
  public enum Direction {
    SOUTH (1), WEST (2), NORTH (3), EAST (4);
    
    private final int player;
    Direction(int player) {
      this.player = player;
    }
  }
  
  // The graphics engine utilizes a LayeredCanvas to organize its elements.
  LayeredCharCanvas canvas;
  // The information used to construct views comes in a DisplayData capsule.
  DisplayData data;
  // conveninent box collections
  Map<Direction, LayeredCharCanvas.Box> players;
  Map<Direction, LayeredCharCanvas.Box> scores;
  Map<Direction, LayeredCharCanvas.Box> hands;
  Map<Direction, LayeredCharCanvas.Box> tableCards;  
  // card dimensions
  final int CARD_HEIGHT = 6;
  final int CARD_WIDTH = 5;
  // basic graphic elements
  final char TOP_BORDER = '-';
  final char SIDE_BORDER = '|';
  final char UL_CORNER = ' ';
  final char UR_CORNER = ' ';
  final char BL_CORNER = ' ';
  final char BR_CORNER = ' ';
  final char WHITE_SPACE = ' ';
  // card templates
  final char[][] CARD_SPACE = new char[CARD_HEIGHT][CARD_WIDTH];             // A transparent card-sized box, representing the empty-hand.
  final char[][] CARD;
  char[][] PARTIAL_CARD;                                                     // Partial cards are for fanned hands.
  char[][] PARTIAL_CARD_WIDE;                                                // Unfortunately, some ranks have 2 characters.
  // background
  final char[][] BACKGROUND;
  // special coordinates
  public final int[] CENTRE;
  Map<Direction, int[]> handCoordinates;
  Map<Direction, int[]> tableCoordinates;

  /**
   * Constructor takes a window size, which is unchangeable.
   * It adds the layers the class will use to the LayeredCanvas, and 
   * constructs the card templates.
   * @param windowHeight
   * @param windowWidth 
   */    
  public ConsoleCardGraphics(int windowHeight, int windowWidth) {
    // Construct canvas with named layers.
    canvas = new LayeredCharCanvas(windowHeight, windowWidth);
    canvas.addLayer("BACKGROUND");
    canvas.addLayer("PLAYERS");
    canvas.addLayer("SCORES");
    canvas.addLayer("HANDS");
    canvas.addLayer("TABLE");
    // Initialize DisplayData object. 
    // TODO: take as param in the constructor, so we can have same reference as CardGameEngine?
    data = new DisplayData();
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
    // Set special coordinates.
    CENTRE = new int[]{windowHeight/2, windowWidth/2};
    handCoordinates.put(Direction.NORTH, new int[]{windowHeight/4, windowWidth/2});
    handCoordinates.put(Direction.EAST, new int[]{windowHeight/2, (3*windowWidth)/4});
    handCoordinates.put(Direction.SOUTH, new int[]{(3*windowHeight)/4, windowWidth/2});
    handCoordinates.put(Direction.WEST, new int[]{windowHeight/2, windowWidth/4});
    tableCoordinates.put(Direction.NORTH, new int[]{CENTRE[0]-CARD_HEIGHT, CENTRE[1]});
    tableCoordinates.put(Direction.EAST,  new int[]{CENTRE[0], CENTRE[1]+CARD_WIDTH});
    tableCoordinates.put(Direction.SOUTH, new int[]{CENTRE[0+CARD_HEIGHT], CENTRE[1]});
    tableCoordinates.put(Direction.WEST,  new int[]{CENTRE[0], CENTRE[1]-CARD_WIDTH});
  }

  public void setData(DisplayData data) {
    this.data = data;
  }
  
  /*
  Helpful drawing functions.
  */
  
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
  
  /*
  Canvas producers.
  */
  
  private char[][] makeString(String string) {
    char[][] result = new char[0][string.length()];
    for (int i=0; i<string.length(); i++) {
      result[0][i] = string.charAt(i);
    }
    return result;
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
  public char[][] makeHiddenHand(int cards) {
    char[][] hand = new char[CARD_HEIGHT][CARD_WIDTH + 2*(cards-1)];
    for (int i=0; i<cards-1; i++) {
      LayeredCharCanvas.copyCanvas(PARTIAL_CARD, hand, 0, 2*i);
    }
    LayeredCharCanvas.copyCanvas(CARD, hand, 0, 2*(cards-1));
    return hand;
  }
  
  /*
  Pinning methods.
  These functions add a box to the layered canvas, and return a reference.
  */
  
  LayeredCharCanvas.Box pinPlayer(String player, int y, int x) {
    if (player.length() > 8) {
      player = "TOO LONG";
    }
    return canvas.pinContentToLayer(makeString(player), "PLAYERS", y, x, new int[]{0,1});// One line tall, so new vertical alignment needed.
  }
  LayeredCharCanvas.Box pinScore(String score, int y, int x) {
    return canvas.pinContentToLayer(makeString(score), "SCORES", y, x, new int[]{0,1});// One line tall, so new vertical alignment needed.
  }
  LayeredCharCanvas.Box pinHand(List<Card> cards, int y, int x) {
    return canvas.pinContentToLayer(makeHand(cards), "HANDS", y, x, new int[]{1,1});
  }
  LayeredCharCanvas.Box pinTableCard(Card card, int y, int x) {
    return canvas.pinContentToLayer(makeCard(card), "TABLE", y, x, new int[]{1,1});
  }
  
  /*
  Views.
  The view builder methods are used when a view must be built from scratch.
  The update methods are used when an established view needs a small change.
  */
  
  /**
   * Draws the table view using the DisplayData, which is managed by GameEngine.
   * This method is used when you want to build the view from scratch.
   */
  void buildTableView() {
    // Reset hash tables.
    players = new HashMap<>();
    scores = new HashMap<>();
    hands = new HashMap<>();
    tableCards = new HashMap<>();
    // Reset canvas and re-add the background.
    canvas.clearBoxes();
    canvas.pinContentToLayer(BACKGROUND, "BACKGROUND", 0, 0);
    // Construct boxes for each player.
    for (Direction direction : Direction.values()) {
      // build hand box
      List<Card> hand = data.getHands().get(direction.player - 1);
      int[] handCoords = handCoordinates.get(direction);                     // hand, player, and score are all based on the same coordinates
      hands.put(direction, pinHand(hand, handCoords[0], handCoords[1]));
      // build player box
      String player = data.getPlayers().get(direction.player-1);
      players.put(direction, pinPlayer(player, handCoords[0]+(int)Math.ceil(CARD_HEIGHT/2), 
                                               handCoords[1]));
      // build score box
      String score = data.getScores().get(direction.player-1);
      scores.put(direction, pinScore(score, handCoords[0]+(int)Math.ceil(CARD_HEIGHT/2)+1, 
                                            handCoords[1]));
      // build table card box
      Card card = data.getTableCards().get(direction.player - 1);
      int[] tableCoords = tableCoordinates.get(direction);                   // coordinates for table cards are independent from hands
      tableCards.put(direction, pinTableCard(card, tableCoords[0], tableCoords[1]));
    }
  }
  void updateHands() {
    for (Direction direction : Direction.values()) {
      List<Card> newHand = data.getHands().get(direction.player-1);
      hands.get(direction).setContent(makeHand(newHand));
    }
  }
  void updateScores() {
    for (Direction direction : Direction.values()) {
      String newScore = data.getScores().get(direction.player-1);
      scores.get(direction).setContent(makeString(newScore));
    }
  }
  void updateTableCards() {
    for (Direction direction : Direction.values()) {
      Card newCard = data.getTableCards().get(direction.player-1);
      tableCards.get(direction).setContent(makeCard(newCard));
    }
  }
  
  /*
  Printing
  */
  
  String produceOutput() {
    String output = "";
    for (char[] row : canvas.draw()) {
      for (int i=0; i<row.length; i++) {
        output+= String.valueOf(row[i]);
      }
      output += "\n";
    }
    return output;
  }
  public void drawTable() {
    buildTableView();
    System.out.println(produceOutput());
  }
}

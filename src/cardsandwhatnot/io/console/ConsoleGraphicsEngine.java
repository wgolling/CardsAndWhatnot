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

import java.util.*;
import java.lang.Math;
import cardsandwhatnot.lib.DisplayData;
import cardsandwhatnot.lib.Card;

/**
 *
 * @author William Gollinger
 */
public class ConsoleGraphicsEngine {
  // CharBox is a wrapper around char[][]
  class CharBox {
    static final char DEFAULT_CHAR = '\u0000'; // treated as transparent
                                               // NOTE: \u0000 is java's default char value, so this is currently redundant
    int x;
    int y;
    int height;
    int width;
    char[][] content;
    
    public CharBox(char[][] content) {
      this(content, 0, 0);
    }
    public CharBox(char[][] content, int x, int y) {
      this.x=x;
      this.y=y;
      this.height = content.length;
      this.width = ( (this.height == 0) ? 0 : content[0].length );
      this.content = content;
    }
    public void setX(int x) {
      this.x = x;
    }
    public void setY(int y) {
      this.y = y;
    }
    public void setXY(int x, int y) {
      setX(x);
      setY(y);
    }
    public int getX() {
      return x;
    }
    public int getY() {
      return y;
    }
    public int getHeight() {
      return height;
    }
    public int getWidth() {
      return width;
    }
    public void setContent(char[][] content) {
      this.width = content[0].length;
      this.height = content.length;
      this.content = new char[this.height][this.width];
      this.content = content;
    }
    public char[][] getContent() {
      return content;
    }
  }
  class Layer {
    String name;  // a name for convenience 
    List<CharBox> boxes;
    
    public Layer() {
      this("BLANK");
    }
    public Layer(String name) {
      this.name = name;
      boxes = new ArrayList<>();
    }
    void addBox(CharBox box) {
      boxes.add(box);
    }
    void addBox(CharBox box, int x, int y) {
      box.setXY(x,y);
      addBox(box);
    }
    boolean removeBox(CharBox box) {
      return boxes.remove(box);
    }
    void clear() {
      boxes = new ArrayList<>();
    }
    List<CharBox> getBoxes() {
      return boxes;
    }
    void draw() {
      for (CharBox box : boxes) {
        drawBox(box);
      }
    }
    void drawBox(CharBox box) {
      int iStart = (box.x < 0) ? -box.x : 0;                                   // Determine end points of for loops
      int iEnd = (box.x + box.width > windowWidth) ? windowWidth - box.x 
                                                   : box.width;
      int jStart = (box.y < 0) ? -box.y : 0;
      int jEnd = (box.y + box.height > windowHeight) ? windowHeight - box.y 
                                                     : box.height;
      for (int j = jStart; j < jEnd; j++) {
        for (int i = iStart; i < iEnd; i++) {
          if (box.content[j][i] != CharBox.DEFAULT_CHAR) {                     // Check for transparency.
            display[j+box.y][i+box.x] = box.content[j][i];
          }
        }
      }
    }
  }

  int windowHeight;
  int windowWidth;
  char[][] display;
  DisplayData data;
//  Map<String, Layer> layers;
  List<Layer> layers;
  
  private final String CARD_SEPARATOR = ",";                                 // Input strings should take the form "Rank.getSymbol(),Suit.getSymbol()"
  // card dimensions
  private final int CARD_HEIGHT = 6;
  private final int CARD_WIDTH = 5;
  // card graphics
  private final char TOP_BORDER = '-';
  private final char SIDE_BORDER = '|';
  private final char UL_CORNER = '/';
  private final char UR_CORNER = '\\';
  private final char BL_CORNER = '\\';
  private final char BR_CORNER = '/';
  private final char WHITE_SPACE = ' ';
  // blank card canvases
  private char[][] CARD_SPACE = new char[CARD_HEIGHT][CARD_WIDTH];           // A transparent card-sized box, representing the empty-hand.
  private char[][] CARD;
  private char[][] PARTIAL_CARD;                                             // Partial cards are for fanned hands.
  private char[][] PARTIAL_CARD_WIDE;                                        // Unfortunately, some ranks have 2 characters.
  
  public ConsoleGraphicsEngine(int windowHeight, int windowWidth) {
    // Initialize variables.
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    display = new char[windowHeight][windowWidth];
    data = new DisplayData();
    layers = new ArrayList<>();
    layers.add(new Layer("BASE"));
    layers.add(new Layer("PLAYERS"));
    layers.add(new Layer("SCORES"));
    layers.add(new Layer("TABLE"));
    layers.add(new Layer("HANDS"));
    // Initialize blank card canvases.
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
  void updateData(DisplayData newData) {
    data = newData;
  }
  CharBox makeBox(char[][] content, int x, int y) {
    return new CharBox(content, x, y);
  }
  CharBox makeBox(char[][] content) {
    return new CharBox(content);
  }
  void resetDisplay() {
    for (char[] row : display) {
      Arrays.fill(row, CharBox.DEFAULT_CHAR);
    }
  }
  // Updating display involves drawing each layer in sequence.
  void updateDisplay() {
    resetDisplay();
    for (Layer layer : layers) {
      layer.draw();
    }
  }
  // Attaching Box to Layer at a coordinate, with an optional alignment parameter.
  // Alignment specifies wether the coordinates denote upper-left corner (default), center, etc.
  void addLayer(String layerName) {
    layers.add(new Layer(layerName));
  }
  Layer findLayer(String layerName) {
    for (Layer layer : layers) {
      if (layerName.equals(layer.name)) {
        return layer;
      }
    }
    return null;
  }
  void addBoxToLayer(CharBox box, String layerName) {
    findLayer(layerName).addBox(box);
  }
  // "Pinning" a box gives it new coordinates.
  void pinBoxToLayer(CharBox box, int x, int y, String layerName) {
    pinBoxToLayer(box, x, y, layerName, "UL");
  }
  void pinBoxToLayer(CharBox box, int x, int y, String layerName, String alignment) {
    Layer layer = findLayer(layerName);
    if (layer == null) {
      return; // ? throw exception?
    }
    int yOffset;
    switch (alignment.charAt(0)) {
      case 'U': yOffset = 0; break;
      case 'C': yOffset = -(int)Math.floor(box.height/2); break;
      case 'D': yOffset = -box.height + 1; break;
      default: yOffset = 0;
    }
    int xOffset;
    switch (alignment.charAt(1)) {
      case 'L': xOffset = 0; break;
      case 'C': xOffset = -(int)Math.floor(box.width/2); break;
      case 'R': xOffset = -box.width + 1; break;
      default: xOffset = 0;
    }
    layer.addBox(box, x+xOffset, y+yOffset);
  }
  /**
  * Helpful drawing functions.
  */
  boolean copyCanvas(char[][] source, char[][] target, int x, int y) {
    int sourceHeight = source.length;                                        // readability variables
    int sourceWidth = source[0].length;
    if (y + sourceHeight > target.length || x + sourceWidth > target[0].length) {
      return false;
    }
    for (int j=0; j < sourceHeight; j++) {
      for (int i=0; i < sourceWidth; i++) {
        target[j+y][i+x] = source[j][i];
      }
    }
    return true;
  }
  boolean drawPartialCardOnCanvas(char[][] canvas, int x, int y, String rank, String suit) {
    if (x + CARD_WIDTH > canvas[0].length || y + CARD_HEIGHT > canvas.length) {
      return false;
    }
    // Draw a wide partial card if the rank is two chars, and place the extra char.
    if (rank.length() == 2) {
      copyCanvas(PARTIAL_CARD_WIDE, canvas, x, y);
      canvas[y+1][x+2] = rank.charAt(1);
    } else {
      copyCanvas(PARTIAL_CARD, canvas, x, y);
    }
    canvas[y+1][x+1] = rank.charAt(0);
    canvas[y+2][x+1] = suit.charAt(0);
    return true;
  }
  boolean drawCardOnCanvas(char[][] canvas, int x, int y, String rank, String suit) {
    if (x + CARD_WIDTH > canvas[0].length || y + CARD_HEIGHT > canvas.length) {
      return false;
    }
    // Draw blank card.
    copyCanvas(CARD, canvas, x, y);
    // Place symbols.
    if (rank.length() == 2) {
      canvas[y+CARD_HEIGHT-1][x+CARD_WIDTH-2] = rank.charAt(0);
      canvas[y+1][x+2] = canvas[y+CARD_HEIGHT-1][x+CARD_WIDTH-1] = rank.charAt(1);
    } else {
      canvas[y+CARD_HEIGHT-1][x+CARD_WIDTH-1] = rank.charAt(0);
    }
    canvas[y+1][x+1] = rank.charAt(0);
    canvas[y+2][x+1] = canvas[y+CARD_HEIGHT-2][x+CARD_WIDTH-1] = suit.charAt(0);
    return true;
  }
  /**
  * Making special boxes.
  */
  CharBox makeHandBox(String[] cards) {
    if (cards.length == 0) { 
      return new CharBox(CARD_SPACE);
    }
    // First process the input array and compute dimensions for box.
    int length = cards.length;
    int fanWidth = 0;
    String[][] splitCards = new String[length][2];                           // Convert String->String[] by "Rank,Suit"->{"Rank", "Suit")
    for (int i=0; i < length-1; i++) { // TODO: functional operation/stream
      splitCards[i] = cards[i].split(CARD_SEPARATOR); // TODO? pass Cards to GraphicsEngine instead of strings?
      fanWidth += 1 + splitCards[i][0].length();                             // Widen depending on the length of Rank.
    }
    splitCards[length-1] = cards[length-1].split(CARD_SEPARATOR);
    char[][] box = new char[CARD_HEIGHT][CARD_WIDTH + fanWidth];
    // Draw cards in box: first the partial cards, then the top card.
    int offset=0;
    for (int i=0; i < length-1; i++) {
      drawPartialCardOnCanvas(box, offset, 0, splitCards[i][0], splitCards[i][1]);
      offset += 1 + splitCards[i][0].length();
    }
    drawCardOnCanvas(box, offset, 0, splitCards[length-1][0], splitCards[length-1][1]);
    return new CharBox(box);
  }
  CharBox makeCardBox(String card) {
    return makeHandBox(new String[]{card});
  }
  CharBox makePlayerBox(String player, String score) { 
    int boxWidth = Math.max(player.length(), 3);
    String[] rows = new String[]{ player, score }; // TODO? might want to display more info
    char[][] canvas = new char[rows.length][boxWidth];
    for (int row=0; row < rows.length; row++) {                              // Print strings to char[].
      for (int i=0; i < rows[row].length(); i++) {
        canvas[row][i] = rows[row].charAt(i);
      }
    }
    return new CharBox(canvas);
  }
}
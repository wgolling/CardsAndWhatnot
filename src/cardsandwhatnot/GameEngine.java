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
package cardsandwhatnot;

import java.util.*;
import cardsandwhatnot.cardgames.*;

/**
 *
 * @author William Gollinger
 */
public class GameEngine {
  // window size
  static final int MIN_HEIGHT = 50;
  static final int MIN_WIDTH = 50;
  public int height;
  public int width;
  // UI variables
  static final String DEFAULT_UI = "CONSOLE";
  String uiType;
  // lists
  List<String> games;
  List<String> players;
  
  public GameEngine(int height, int width, String uiType) {
    setWindowSize(height, width);
    this.uiType = DEFAULT_UI;
    switch (uiType) {
      case "CONSOLE": this.uiType = "CONSOLE";
      break;
    }
  }
  
  String mainMenu() {return "";}
  String playGame(CardGame game) {
    switch (game.getCardType()) {
      case "BLANK" : break;
      case "FOUR" : HeartsUI UI = new HeartsUI();   
    }
    
    return "";
  }
  int[] getWindowDimensions() {
    int[] dimensions = {height, width};
    return dimensions;
  }
  void setWindowSize(int height, int width) {
    this.height = (height > MIN_HEIGHT) ? height : MIN_HEIGHT;
    this.width = (width > MIN_WIDTH) ? width : MIN_WIDTH;
  }
  void setUIType(String ui) {this.uiType = ui;}
}

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
import cardsandwhatnot.lib.StandardCard;
import cardsandwhatnot.lib.StandardDeck;
import cardsandwhatnot.GameEngine;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author William Gollinger
 */
public class ConsoleCardGraphicsTest {
  
  ConsoleCardGraphics graphics;
  final int CARD_WIDTH;
  final int CARD_HEIGHT;
  Card ACE_OF_SPADES;
  Card TEN_OF_DIAMONDS;
  Card TWO_OF_CLUBS;
  Card QUEEN_OF_HEARTS;
  List<Card> hand;
  char[][] testCanvas;
  
  public ConsoleCardGraphicsTest() {
    graphics = new ConsoleCardGraphics(GameEngine.MIN_HEIGHT, GameEngine.MIN_WIDTH);
    this.CARD_HEIGHT = graphics.CARD_HEIGHT;
    this.CARD_WIDTH = graphics.CARD_WIDTH;
    ACE_OF_SPADES = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
    TEN_OF_DIAMONDS = new StandardCard(StandardCard.Rank.TEN, StandardCard.Suit.DIAMONDS);
    TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS);
    QUEEN_OF_HEARTS = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.HEARTS);
    hand = new ArrayList<>();
    hand.add(ACE_OF_SPADES);
    hand.add(TEN_OF_DIAMONDS);
    hand.add(TWO_OF_CLUBS);
    hand.add(QUEEN_OF_HEARTS);
  }
    
  @Before
  public void setUp() {
    graphics = new ConsoleCardGraphics(GameEngine.MIN_HEIGHT, GameEngine.MIN_WIDTH);
    testCanvas = new char[50][50];
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of drawPartialCard method, of class ConsoleCardGraphics.
   */
  @Test
  public void testDrawPartialCard() {
    System.out.println("drawPartialCard");
    graphics.drawPartialCard(ACE_OF_SPADES, testCanvas, 2, 3);
    assertEquals('A', testCanvas[3][4]);
    assertEquals('\u2660', testCanvas[4][4]);
    graphics.drawPartialCard(TEN_OF_DIAMONDS, testCanvas, 0, 0);
    assertEquals(graphics.TOP_BORDER, testCanvas[0][2]);
  }

  /**
   * Test of drawCard method, of class ConsoleCardGraphics.
   */
  @Test
  public void testDrawCard() {
    System.out.println("drawCard");
    graphics.drawCard(QUEEN_OF_HEARTS, testCanvas, 0, 0);
    assertEquals('Q', testCanvas[CARD_HEIGHT-2][CARD_WIDTH-2]);
    graphics.drawCard(TEN_OF_DIAMONDS, testCanvas, 0, 0);
    assertEquals('0', testCanvas[CARD_HEIGHT-2][CARD_WIDTH-3]);
  }

  /**
   * Test of makeHand method, of class ConsoleCardGraphics.
   */
  @Test
  public void testMakeHand() {
    System.out.println("makeHand");
    testCanvas = graphics.makeHand(hand);
    assertEquals('A', testCanvas[1][1]);
    assertEquals(graphics.SIDE_BORDER,testCanvas[1][5]);
    assertEquals(graphics.BR_CORNER, testCanvas[CARD_HEIGHT-1][CARD_WIDTH-1 + 7]);
  }

  /**
   * Test of makeCard method, of class ConsoleCardGraphics.
   */
  @Test
  public void testMakeCard() {
    System.out.println("makeCard");
    testCanvas = graphics.makeCard(TEN_OF_DIAMONDS);
    assertEquals('0', testCanvas[CARD_HEIGHT-2][CARD_WIDTH-3]);
  }

  /**
   * Test of updateHand method, of class ConsoleCardGraphics.
   */
  @Test
  public void testUpdateHand() {
    System.out.println("updateHand");
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}

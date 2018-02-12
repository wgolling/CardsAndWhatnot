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

import cardsandwhatnot.lib.Card;
import cardsandwhatnot.lib.StandardCard;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author William Gollinger
 */
public class HeartsTest {
  
  StandardCard QUEEN_OF_SPADES = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.SPADES);
  public HeartsTest() {
  }
    
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of validatePlay method, of class Hearts.
   */
  @Test
  public void testValidatePlay() {
    System.out.println("validatePlay");
    Card card = null;
    Hearts instance = null;
    boolean expResult = false;
    boolean result = instance.validatePlay(QUEEN_OF_SPADES);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of defaultCard method, of class Hearts.
   */
  @Test
  public void testDefaultCard() {
    System.out.println("defaultCard");
    Hearts instance = null;
    Card expResult = null;
    Card result = instance.defaultCard();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setupGame method, of class Hearts.
   */
  @Test
  public void testSetupGame() {
    System.out.println("setupGame");
    Hearts instance = null;
    instance.setupGame();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of resolveGame method, of class Hearts.
   */
  @Test
  public void testResolveGame() {
    System.out.println("resolveGame");
    Hearts instance = null;
    instance.resolveGame();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setupRound method, of class Hearts.
   */
  @Test
  public void testSetupRound() {
    System.out.println("setupRound");
    Hearts instance = null;
    instance.setupRound();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of resolveRound method, of class Hearts.
   */
  @Test
  public void testResolveRound() {
    System.out.println("resolveRound");
    Hearts instance = null;
    instance.resolveRound();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of setupPlay method, of class Hearts.
   */
  @Test
  public void testSetupPlay() {
    System.out.println("setupPlay");
    Hearts instance = null;
    instance.setupPlay();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of resolvePlay method, of class Hearts.
   */
  @Test
  public void testResolvePlay() {
    System.out.println("resolvePlay");
    Card card = null;
    Hearts instance = null;
    instance.resolvePlay(card);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
  
}

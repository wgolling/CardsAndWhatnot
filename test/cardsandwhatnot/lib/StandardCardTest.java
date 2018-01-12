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
package cardsandwhatnot.lib;

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
public class StandardCardTest {
  
  StandardCard testCard;
  
  public StandardCardTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    testCard = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
  }
  
  @After
  public void tearDown() {
    testCard = null;
  }

  /**
   * Test of getRank method, of class StandardCard.
   */
  @Test
  public void testGetRank() {
    System.out.println("getRank");
    StandardCard.Rank expResult = StandardCard.Rank.ACE;
    StandardCard.Rank result = testCard.getRank();
    assertEquals(expResult, result);
  }

  /**
   * Test of getSuit method, of class StandardCard.
   */
  @Test
  public void testGetSuit() {
    System.out.println("getSuit");
    StandardCard.Suit expResult = StandardCard.Suit.SPADES;
    StandardCard.Suit result = testCard.getSuit();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class StandardCard.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    String expResult = "Ace of Spades";
    String result = testCard.toString();
    assertEquals(expResult, result);
  }

  /**
   * Test of equals method, of class StandardCard.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    Object other = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.HEARTS);
    boolean expResult = false;
    boolean result = testCard.equals(other);
    assertEquals(expResult, result);
    Object same = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
    expResult = true;
    result = testCard.equals(same);
    assertEquals(expResult, result);
  }

  /**
   * Test of hashCode method, of class StandardCard.
   */
  @Test
  public void testHashCode() {
    System.out.println("hashCode");
    int expResult = 40;
    int result = testCard.hashCode();
    assertEquals(expResult, result);
  }

  /**
   * Test of compareTo method, of class StandardCard.
   */
  @Test
  public void testCompareTo() {
    System.out.println("compareTo");
    Card smaller = new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS);
    Card same = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
    Card bigger = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.HEARTS);
    int result = testCard.compareTo(smaller);
    assert(result > 0);
    result = testCard.compareTo(same);
    assert(result == 0);
    result = testCard.compareTo(bigger);
    assert(result < 0);
  }
  
}

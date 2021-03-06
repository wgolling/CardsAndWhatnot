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
  
  StandardCard ACE_OF_SPADES = new StandardCard(StandardCard.Rank.ACE,
                                                StandardCard.Suit.SPADES);
  StandardCard TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO,
                                               StandardCard.Suit.CLUBS);
  StandardCard QUEEN_OF_HEARTS = new StandardCard(StandardCard.Rank.QUEEN,
                                                  StandardCard.Suit.HEARTS);
  
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
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of getRank method, of class StandardCard.
   */
  @Test
  public void testGetRank() {
    System.out.println("getRank");
    StandardCard.Rank expResult = StandardCard.Rank.ACE;
    StandardCard.Rank result = ACE_OF_SPADES.getRank();
    assertEquals(expResult, result);
  }

  /**
   * Test of getSuit method, of class StandardCard.
   */
  @Test
  public void testGetSuit() {
    System.out.println("getSuit");
    StandardCard.Suit expResult = StandardCard.Suit.SPADES;
    StandardCard.Suit result = ACE_OF_SPADES.getSuit();
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class StandardCard.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    String expResult = "Ace of Spades";
    String result = ACE_OF_SPADES.toString();
    assertEquals(expResult, result);
  }

  /**
   * Test of equals method, of class StandardCard.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    boolean expResult = false;
    boolean result = ACE_OF_SPADES.equals(QUEEN_OF_HEARTS);
    assertEquals(expResult, result);
    Object same = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
    expResult = true;
    result = ACE_OF_SPADES.equals(same);
    assertEquals(expResult, result);
  }

  /**
   * Test of hashCode method, of class StandardCard.
   */
  @Test
  public void testHashCode() {
    System.out.println("hashCode");
    int expResult = 40;
    int result = ACE_OF_SPADES.hashCode();
    assertEquals(expResult, result);
  }

  /**
   * Test of compareTo method, of class StandardCard.
   */
  @Test
  public void testCompareTo() {
    System.out.println("compareTo");
    int result = ACE_OF_SPADES.compareTo(TWO_OF_CLUBS);
    assert(result > 0);
    StandardCard same = new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES);
    result = ACE_OF_SPADES.compareTo(same);
    assert(result == 0);
    result = ACE_OF_SPADES.compareTo(QUEEN_OF_HEARTS);
    assert(result < 0);
  }
  
}

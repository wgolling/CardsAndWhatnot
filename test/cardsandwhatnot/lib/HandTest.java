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

import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
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
public class HandTest {
  StandardCard ACE_OF_SPADES = new StandardCard(StandardCard.Rank.ACE,
                                                StandardCard.Suit.SPADES);
  StandardCard TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO,
                                               StandardCard.Suit.CLUBS);
  StandardCard QUEEN_OF_HEARTS = new StandardCard(StandardCard.Rank.QUEEN,
                                                  StandardCard.Suit.HEARTS);
  
  public HandTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    Hand hand = new Hand(Arrays.asList(ACE_OF_SPADES, TWO_OF_CLUBS, QUEEN_OF_HEARTS));
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of getCards method, of class Hand.
   */
  @Test
  public void testGetCards() {
    System.out.println("getCards");
    Hand instance = new Hand(Arrays.asList(ACE_OF_SPADES, TWO_OF_CLUBS, QUEEN_OF_HEARTS));
    StandardCard.Suit expResult = StandardCard.Suit.CLUBS;
    StandardCard.Suit result = (StandardCard.Suit)instance.getCards().get(1).getSuit();
    assertEquals(expResult, result);
  }

  /**
   * Test of addCard method, of class Hand.
   */
  @Test
  public void testAddCard() {
    System.out.println("addCard");
    Hand instance = new Hand(Arrays.asList(ACE_OF_SPADES));
    instance.addCard(QUEEN_OF_HEARTS);
    StandardCard.Suit expResult = StandardCard.Suit.HEARTS;
    StandardCard.Suit result = (StandardCard.Suit)instance.getCards().get(1).getSuit();
    assertEquals(expResult, result);
  }

  /**
   * Test of addCards method, of class Hand.
   */
  @Test
  public void testAddCards() {
    System.out.println("addCards");
    List<Card> otherCards = Arrays.asList(QUEEN_OF_HEARTS, TWO_OF_CLUBS);
    Hand instance = new Hand(Arrays.asList(ACE_OF_SPADES));
    instance.addCards(otherCards);
    StandardCard.Suit expResult = StandardCard.Suit.CLUBS;
    StandardCard.Suit result = (StandardCard.Suit)instance.getCards().get(2).getSuit();
    assertEquals(expResult, result);
  }

  /**
   * Test of removeCards method, of class Hand.
   */
  @Test
  public void testRemoveCards() {
    System.out.println("removeCards");
    Hand instance = new Hand(Arrays.asList(ACE_OF_SPADES, TWO_OF_CLUBS, QUEEN_OF_HEARTS));
    List<Card> subCards = Arrays.asList(QUEEN_OF_HEARTS, ACE_OF_SPADES);
    boolean expResult = true;
    boolean result = instance.removeCards(subCards);
    assertEquals(expResult, result);
    StandardCard.Suit suit = StandardCard.Suit.CLUBS;
    StandardCard.Suit test = (StandardCard.Suit)instance.getCards().get(0).getSuit();
    assertEquals(suit, test);
    expResult = false;
    result = instance.removeCards(subCards);
    assertEquals(expResult, result);
    }

  /**
   * Test of sort method, of class Hand.
   */
  @Test
  public void testSort_0args() {
    System.out.println("sort");
    Hand instance = new Hand(Arrays.asList(ACE_OF_SPADES, TWO_OF_CLUBS, QUEEN_OF_HEARTS));
    instance.sort();
    StandardCard.Suit expResult = StandardCard.Suit.CLUBS;
    StandardCard.Suit result = (StandardCard.Suit)instance.getCards().get(0).getSuit();
    assertEquals(expResult, result);
  }
  
}

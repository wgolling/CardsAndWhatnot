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

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class StandardDeckTest {
  
  StandardDeck testDeck;
  StandardCard TWO_OF_CLUBS;
  
  public StandardDeckTest() {
    testDeck = new StandardDeck();
    TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS);
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    testDeck.refresh();
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of deal method, of class StandardDeck.
   */
  @Test
  public void testDeal() {
    System.out.println("deal");
    int players = 2;
    int cardsEach = 10;
    StandardDeck instance = testDeck;
    Card expResult = TWO_OF_CLUBS;
    Map<Integer, Hand> result = instance.deal(players, cardsEach);
    assert(expResult.equals(result.get(1).getCards().get(0)));
    expResult = new StandardCard(StandardCard.Rank.EIGHT, StandardCard.Suit.DIAMONDS);
    assert(expResult.equals(result.get(2).getCards().get(9)));
  }

  /**
   * Test of shuffle method, of class StandardDeck.
   */
  @Test
  public void testShuffle() {
    System.out.println("shuffle");
    // can't really test something random
  }

  /**
   * Test of refresh method, of class StandardDeck.
   */
  @Test
  public void testRefresh() {
    System.out.println("refresh");
    StandardDeck instance = testDeck;
    testDeck.removeCards(Arrays.asList(TWO_OF_CLUBS));
    Card card = testDeck.deal(1,1).get(1).getCards().get(0);
    Card expResult = new StandardCard(StandardCard.Rank.THREE, StandardCard.Suit.CLUBS);
    Card result = testDeck.getCards().get(0);
    assertEquals(expResult, result);
    instance.refresh();
    result = testDeck.getCards().get(0);
    assertEquals(TWO_OF_CLUBS, result);
  }
  
}

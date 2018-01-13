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
import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class PlayerTest {
  Player testPlayer;
  
  public PlayerTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    testPlayer = new Player("TEST", "PASSWORD", true);
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of getName method, of class Player.
   */
  @Test
  public void testGetName() {
    System.out.println("getName");
    Player instance = testPlayer;
    String expResult = "TEST";
    String result = instance.getName();
    assertEquals(expResult, result);
  }

  /**
   * Test of isHuman method, of class Player.
   */
  @Test
  public void testIsHuman() {
    System.out.println("isHuman");
    Player instance = testPlayer;
    assert(instance.isHuman());
  }

  /**
   * Test of winGame method, of class Player.
   */
  @Test
  public void testWinGame_GetWins() {
    System.out.println("winGame");
    String gameName = "TESTGAME";
    Player instance = testPlayer;
    instance.winGame(gameName);
    int expResult = 1;
    int result = instance.getWins(gameName);
    assertEquals(expResult, result);
    assertEquals(0, instance.getWins("BUTT"));
    Map<String, Integer> wins = new HashMap<>();
    wins.put("BUTT", 7);
    Player testPlayer2 = new Player("TEST2", "PASS2", true, wins);
    assertEquals(7, testPlayer2.getWins("BUTT"));
  }

  /**
   * Test of getHand method, of class Player.
   */
  @Test
  public void testGetHand() {
    System.out.println("getHand");
    Player instance = testPlayer;
    Hand expResult = null;
    Hand result = instance.getHand();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of drawCards method, of class Player.
   */
  @Test
  public void testDrawCards() {
    System.out.println("drawCards");
    Deck deck = new StandardDeck();
    Player instance = testPlayer;
    int expResult = 7;
    int result = instance.drawCards(deck, 7);
    assertEquals(expResult, result);
    result = testPlayer.drawCards(deck, 52);
    assertEquals(52-7, result);
  }
  
}

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
public class ConsoleInputProcessorTest {
    
  public ConsoleInputProcessorTest() {
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
   * Test of parseCard method, of class ConsoleInputProcessor.
   */
  @Test
  public void testParseCard() {
    System.out.println("parseCard");
    ConsoleInputProcessor instance = new ConsoleInputProcessor(null,null);
    String[] cardParts = new String[]{"2", "cLuBs"};
    String expRank = "Two";
    String expSuit = "Clubs";
    String[] result = instance.parseCard(cardParts);
    assert(result[0].equals(expRank));
    assert(result[1].equals(expSuit));
  }

  /**
   * Test of parseGame method, of class ConsoleInputProcessor.
   */
  @Test
  public void testParseGame() {
    System.out.println("parseGame");
    String game = "hEaRtS";
    List<String> games = Arrays.asList("Hearts");
    ConsoleInputProcessor instance = new ConsoleInputProcessor(games, null);
    String expResult = "Hearts";
    String result = instance.parseGame(game);
    assert(expResult.equals(result));
  }

  /**
   * Test of parsePlayer method, of class ConsoleInputProcessor.
   */
  @Test
  public void testParsePlayer() {
    System.out.println("parsePlayer");
    String player = "Jerry";
    List<String> players = Arrays.asList("Jerry");
    ConsoleInputProcessor instance = new ConsoleInputProcessor(null, players);
    String expResult = "Jerry";
    String result = instance.parsePlayer(player);
    assert(expResult.equals(result));
    assertNull(instance.parsePlayer("jerry"));
  }
  
}

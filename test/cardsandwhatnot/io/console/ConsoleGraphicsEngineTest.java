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

import cardsandwhatnot.lib.DisplayData;
import cardsandwhatnot.GameEngine;
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
public class ConsoleGraphicsEngineTest {
  
  ConsoleGraphicsEngine engine;
  ConsoleGraphicsEngine.CharBox testBox;
  char[][] testCanvas;
  char[][] dot;
  
  public ConsoleGraphicsEngineTest() {
    engine = new ConsoleGraphicsEngine(GameEngine.MIN_HEIGHT, GameEngine.MIN_WIDTH);
    testBox = engine.makeBox(new char[0][0]);
    testCanvas = new char[9][9];
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        testCanvas[4*j][4*i] = '+';
      }
    }
    dot = new char[1][1];
    dot[0][0] = '.';
  }
    
  @Before
  public void setUp() {
    engine = new ConsoleGraphicsEngine(GameEngine.MIN_HEIGHT, GameEngine.MIN_WIDTH);
    engine.addLayer("TEST");
    testBox = engine.makeBox(new char[0][0]);
  }
  @After
  public void tearDown() {
  }

  /**
   * Test of updateData method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testUpdateData() {
    //...
  }

  /**
   * Test of addLayer and drawLayer
   */
  @Test
  public void testAddLayer() {
    System.out.println("addLayer");
    engine.addLayer("TEST2");
    assert("TEST2".equals(engine.findLayer("TEST2").name));
  }
  /**
   * Test of drawBox method, of class ConsoleGraphicsEngine.
   * drawBox is contained in the Layers nested class.
   */
  @Test
  public void testDrawBox() {
    System.out.println("drawBox");
    ConsoleGraphicsEngine.Layer layer = engine.findLayer("TEST");
    testBox = engine.makeBox(testCanvas);
    layer.drawBox(testBox);
    assertEquals('+', engine.display[0][0]);
    assertEquals('+', engine.display[4][4]);
    // move the box
    testBox.setXY(1, 2);
    layer.drawBox(testBox);
    assertEquals('+', engine.display[2][1]);
    assertEquals('+', engine.display[10][9]);
    // go over bounds
    int topBound = engine.windowHeight - 1;
    int rightBound = engine.windowWidth - 1;
    testBox.setXY(rightBound - 4, topBound - 4);
    layer.drawBox(testBox);
    assertEquals('+', engine.display[topBound][rightBound]);
    assertEquals('+', engine.display[topBound - 4][rightBound - 4]);
    // go under bounds
    testBox.setXY(-1, -2);
    layer.drawBox(testBox);
    assertEquals('+', engine.display[6][7]);
    assertEquals('+', engine.display[2][3]);
  }

  /**
   * Test of drawLayer method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testDrawLayer() {
    System.out.println("drawLayer");
    ConsoleGraphicsEngine.Layer layer = engine.findLayer("TEST");
    layer.addBox(engine.makeBox(dot));
    layer.addBox(engine.makeBox(dot,5,6));
    assertEquals(2, engine.findLayer("TEST").getBoxes().size());
    layer.draw();
    assertEquals('.', engine.display[0][0]);
    assertEquals('.', engine.display[6][5]);
  }
  
  /**
   * Test of updateDisplay method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testUpdateDisplay() {
    System.out.println("updateDisplay");
    ConsoleGraphicsEngine.Layer layer = engine.findLayer("TEST");
    engine.addLayer("TEST2");
    ConsoleGraphicsEngine.Layer layer2 = engine.findLayer("TEST2");
    layer.addBox(engine.makeBox(testCanvas));
    layer2.addBox(engine.makeBox(dot));
    engine.updateDisplay();
    assertEquals('.', engine.display[0][0]); // check that layer2 overwrites layer
    assertEquals('+', engine.display[4][4]); 
  }
  
  /**
   * Test of pinBoxToLayer method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testPinBoxToLayer_4args() {
    System.out.println("pinBoxToLayer");
    engine.pinBoxToLayer(engine.makeBox(testCanvas), 1, 2, "TEST");
    engine.updateDisplay();
    assertEquals('+', engine.display[2][1]);
  }

  /**
   * Test of pinBoxToLayer method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testPinBoxToLayer_5args() {
    System.out.println("pinBoxToLayer");
    engine.pinBoxToLayer(engine.makeBox(testCanvas), 1, 1, "TEST", "UL");
    engine.updateDisplay();
    assertEquals('+', engine.display[1][1]);
    assertEquals('+', engine.display[5][5]);
    assertEquals('+', engine.display[9][9]);
    engine.findLayer("TEST").clear();
    engine.pinBoxToLayer(engine.makeBox(testCanvas), 4, 4, "TEST", "CC");
    engine.updateDisplay();
    assertEquals('+', engine.display[0][0]);
    assertEquals('+', engine.display[4][4]);
    assertEquals('+', engine.display[8][8]);
    engine.findLayer("TEST").clear();
    engine.pinBoxToLayer(engine.makeBox(testCanvas), 8, 8, "TEST", "DR");
    engine.updateDisplay();
    assertEquals('+', engine.display[0][0]);
    assertEquals('+', engine.display[4][4]);
    assertEquals('+', engine.display[8][8]);
  }

  /**
   * Test of copyCanvas method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testCopyCanvas() {
    System.out.println("copyCanvas");
    char[][] source = testCanvas;
    char[][] target = new char[10][10];
    int x = 1;
    int y = 1;
    engine.copyCanvas(source, target, x, y);
    assertEquals('+', target[1][1]);
    assertEquals('+', target[5][5]);
    assertEquals('+', target[9][9]);
  }

  /**
   * Test of drawPartialCardOnCanvas method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testDrawPartialCardOnCanvas() {
    System.out.println("drawPartialCardOnCanvas");
    char[][] canvas = new char[engine.windowHeight][engine.windowWidth];
    int x = 1;
    int y = 1;
    String rank = "10";
    String suit = "B";
    engine.drawPartialCardOnCanvas(canvas, x, y, rank, suit);
    assertEquals('0', canvas[2][3]);
  }

  /**
   * Test of drawCardOnCanvas method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testDrawCardOnCanvas() {
    System.out.println("drawCardOnCanvas");
    char[][] canvas = new char[engine.windowHeight][engine.windowWidth];
    int x = 1;
    int y = 1;
    String rank = "10";
    String suit = "B";
    engine.drawCardOnCanvas(canvas, x, y, rank, suit);
    assertEquals('0', canvas[2][3]);
  }

  /**
   * Test of makeHandBox method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testMakeHandBox() {
    System.out.println("makeHandBox");
    String[] cards = new String[]{"A,S", "10,B", "4,X"};
    engine.findLayer("TEST").addBox(engine.makeHandBox(cards));
    engine.updateDisplay();
    assertEquals('A', engine.display[1][1]);
    assertEquals('0', engine.display[1][4]);
    assertEquals('X', engine.display[2][6]);
  }

  /**
   * Test of makeCardBox method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testMakeCardBox() {
    System.out.println("makeCardBox");
    engine.findLayer("TEST").addBox(engine.makeCardBox("10,B"));
    engine.updateDisplay();
    assertEquals('0', engine.display[1][2]);
  }

  /**
   * Test of makePlayerBox method, of class ConsoleGraphicsEngine.
   */
  @Test
  public void testMakePlayerBox() {
    System.out.println("makePlayerBox");
    String player = "Player 1";
    String score = "69";
    engine.findLayer("TEST").addBox(engine.makePlayerBox(player, score));
    engine.updateDisplay();
    assertEquals('P', engine.display[0][0]);
    assertEquals('9', engine.display[1][1]);
  }
  
}

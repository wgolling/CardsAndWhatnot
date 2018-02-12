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


/**
 *
 * @author William Gollinger
 */
public class LayeredCharCanvasTest {
  
  LayeredCharCanvas testCanvas;
  LayeredCharCanvas.Box testBox;
  LayeredCharCanvas.Layer testLayer;
  final char[][] TEST_CONTENT;
  final char[][] DOT;
  
  public LayeredCharCanvasTest() {
    testCanvas = new LayeredCharCanvas(ConsoleCardGraphics.WINDOW_HEIGHT, 
                                       ConsoleCardGraphics.WINDOW_WIDTH);
    testCanvas.addLayer("TEST");
    TEST_CONTENT = new char[9][9];
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        TEST_CONTENT[4*j][4*i] = '+';
      }
    }
    testBox = testCanvas.new Box(TEST_CONTENT, "TEST_BOX");
    DOT = new char[1][1];
    DOT[0][0] = '.';
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
    testCanvas = new LayeredCharCanvas(ConsoleCardGraphics.WINDOW_HEIGHT, 
                                       ConsoleCardGraphics.WINDOW_WIDTH);
    testCanvas.addLayer("TEST");
    testBox = testCanvas.new Box(TEST_CONTENT, "TEST_BOX");
  }
  
  @After
  public void tearDown() {
  }

  /**
   * Test of draw method, of class LayeredCharCanvas.
   */
  @Test
  public void testDraw() {
    System.out.println("draw");
    testCanvas.addBoxToLayer(testBox, "TEST");
    testBox.setX(3);
    testCanvas.draw();
    assertEquals('+', testCanvas.canvas[0][3]);
    testBox.setYX(1,1);
    testBox.align(new int[2]);
    testCanvas.draw();
    assertEquals('+', testCanvas.canvas[1][1]);
    assertEquals('+', testCanvas.canvas[5][5]);
    assertEquals('+', testCanvas.canvas[9][9]);
    testBox.setYX(4, 4);
    testBox.align(new int[]{1,1});
    assertEquals(1, testBox.getAlignment()[0]);
    assertEquals(-4, testBox.yOffset());
    testCanvas.draw();
    assertEquals('+', testCanvas.canvas[0][0]);
    assertEquals('+', testCanvas.canvas[4][4]);
    assertEquals('+', testCanvas.canvas[8][8]);
    testBox.setYX(8,8);
    testBox.align(new int[]{2,2});
    testCanvas.draw();
    assertEquals('+', testCanvas.canvas[0][0]);
    assertEquals('+', testCanvas.canvas[4][4]);
    assertEquals('+', testCanvas.canvas[8][8]);
    testCanvas.addLayer("TEST2");
    testCanvas.pinContentToLayer(DOT, "TEST2", 4, 4);
    testCanvas.draw();
    assertEquals('+', testCanvas.canvas[0][0]);
    assertEquals('.', testCanvas.canvas[4][4]);
  }

  /**
   * Test of clearCanvas method, of class LayeredCharCanvas.
   */
  @Test
  public void testClearCanvas() {
    System.out.println("clearCanvas");
    testCanvas.addBoxToLayer(testBox, "TEST");
    testBox.setX(3);
    char[][] result = testCanvas.draw();
    assertEquals('+', result[0][3]);
    testCanvas.clearCanvas();
    assertEquals(LayeredCharCanvas.TRANSPARENT, testCanvas.canvas[0][3]);
  }

  /**
   * Test of addLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testAddLayer() {
    System.out.println("addLayer");
    assertNull(testCanvas.findLayer("TEST2"));
    testCanvas.addLayer("TEST2");
    assertEquals("TEST2", testCanvas.findLayer("TEST2").name);
  }

  /**
   * Test of findLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testFindLayer() {
    assertNotNull(testCanvas.findLayer("TEST"));
  }

  /**
   * Test of addBoxToLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testAddBoxToLayer() {
    System.out.println("addBoxToLayer");
    assertNull(testCanvas.addBoxToLayer(testBox, "FOO"));
    assertNotNull(testCanvas.addBoxToLayer(testBox, "TEST"));
  }

  /**
   * Test of pinContentToLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testPinContentToLayer() {
    System.out.println("pinContentToLayer");
    testBox = testCanvas.pinContentToLayer(TEST_CONTENT, "TEST", 2, 3);
    testBox.setName("FOO");
    assertEquals(3, testBox.getX());
    assertEquals('+', testBox.content[0][0]);
    assertNotNull(testCanvas.findBox("FOO"));
  }

  /**
   * Test of findBox method, of class LayeredCharCanvas.
   */
  @Test
  public void testFindBox() {
    System.out.println("findBox");
    testCanvas.addBoxToLayer(testBox, "TEST");
    assertEquals('+', testCanvas.findBox("TEST_BOX").content[0][0]);
  }

  /**
   * Test of findBoxOnLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testFindBoxOnLayer_String_LayeredCharCanvasLayer() {
    System.out.println("findBoxOnLayer");
    testCanvas.addLayer("TEST2");
    testCanvas.addBoxToLayer(testBox, "TEST2");
    LayeredCharCanvas.Layer layer = testCanvas.findLayer("TEST");
    assertNull(testCanvas.findBoxOnLayer("TEST_BOX", layer));
    layer = testCanvas.findLayer("TEST2");
    assertNotNull(testCanvas.findBoxOnLayer("TEST_BOX", layer));
  }

  /**
   * Test of findBoxOnLayer method, of class LayeredCharCanvas.
   */
  @Test
  public void testFindBoxOnLayer_String_String() {
    System.out.println("findBoxOnLayer");
    testCanvas.addLayer("TEST2");
    testCanvas.addBoxToLayer(testBox, "TEST2");
    assertNull(testCanvas.findBoxOnLayer("TEST_BOX", "TEST"));
    assertNotNull(testCanvas.findBoxOnLayer("TEST_BOX", "TEST2"));
  }

  /**
   * Test of copyCanvas method, of class LayeredCharCanvas.
   */
  @Test
  public void testCopyCanvas() {
    System.out.println("copyCanvas");
    char[][] source = TEST_CONTENT;
    char[][] target = new char[10][10];
    int x = 1;
    int y = 1;
    LayeredCharCanvas.copyCanvas(source, target, x, y);
    assertEquals('+', target[1][1]);
    assertEquals('+', target[5][5]);
    assertEquals('+', target[9][9]);
  }
  
}

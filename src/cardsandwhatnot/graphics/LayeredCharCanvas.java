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
package cardsandwhatnot.graphics;

import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class LayeredCharCanvas { 
  // TODO: remove dependency on char.  Maybe if I use Character instead I can use a generic type.
  
  public static final char TRANSPARENT = '\u0000';  // The default char is treated as transparent.  Note this is java's default char value, so this variable is currently redundant.

  /*
   * The Box class is the elementary graphical object for the LayeredCharCanvas.
   * It is essentially a wrapper around char[][], with drawing functions.
   */
  class Box {
    String name;
    int y;            // The virtual coordinates of the Box.
    int x;            // y comes before x so that the first coordinate represents the row.
    int[] alignment = new int[2];  // Two ints from {0,1,2} which determine wether coordinates are upper-left, centre-right, etc.
    int height;       // height and width are redundant but convenient, as they are derived from content.  
    int width;
    char[][] content; // content contains the "graphics".  The default char \u0000 is treated as transparent. 
    
    // Constructors always take a char[][], but other parameters are optional.
    // Alignment is not currently a possible parameter.
    public Box(char[][] content, String name, int y, int x) {
      this.name = name;
      this.y=y;
      this.x=x;
      this.height = content.length;
      this.width = ( (this.height == 0) ? 0 : content[0].length );
      this.content = content;
    }
    public Box(char[][] content, String name) {
      this(content, name, 0,0);
    }
    public Box(char[][] content, int y, int x) {
      this(content, "", y, x);
    }
    public Box(char[][] content) {
      this(content, "", 0, 0);
    }
    // getting and setting
    // most of these are probably optional, could just use instance.param
    void setName(String name) {
      this.name = name;
    }
    void setY(int y) {
      this.y = y;
    }
    void setX(int x) {
      this.x = x;
    }
    void setYX(int y, int x) {
      setY(y);
      setX(x);
    }
    int getY() {
      return y;
    }
    int getX() {
      return x;
    }
    void align(int[] alignment) {
      this.alignment[0] = alignment[0];
      this.alignment[1] = alignment[1];
    }
    int[] getAlignment() {
      return alignment;
    }
    int getHeight() {
      return height;
    }
    int getWidth() {
      return width;
    }
    void setContent(char[][] content) {
      this.height = content.length;
      this.width = ( (this.height == 0) ? 0 : content[0].length );
      this.content = new char[this.height][this.width]; // TODO do I need this?
      this.content = content;
    }
    char[][] getContent() {
      return content;
    }
    // Computing offsets from alignment.
    // 0 -> no shift;  1 -> shift half the range;  2 -> shift the whole range -1
    // the -1 is so that alignment of {2,2} has x,y at bottom right entry.
    int yOffset() {
      return (alignment[0] < 2) ? -(alignment[0]* height)/2 : -height+1 ;
    }
    int xOffset() {
      return (alignment[1] < 2) ? -(alignment[1]* width)/2 : -width+1;
    }
    // the copyContent function takes alignment of the box into account, even if you specify new coordinates.
    void copyContent(char[][] target) {
      copyContent(target, y, x);
    }
    void copyContent(char[][] target, int y, int x) {              // copyContent superimposes content onto target at co-ords (y,x), which can be <0
      copyCanvas(content, target, y+yOffset(), x+xOffset());
    }
  }
  /*
   * Boxes are organized in Layers.
   * 
   */
  class Layer {
    String name;                                                             // Helps the user keep track of their layers, it is not strictly necessary.
    List<Box> boxes;
    
    public Layer(String name) {
      this.name = name;
      boxes = new ArrayList<>();
    }
    public Layer() {
      this("");
    }
    
    void addBox(Box box) {
      boxes.add(box);
    }
    // "pinning" a box is a convenience function which also sets coordinates.
    void pinBox(Box box, int y, int x) {
      box.setYX(y, x);
      boxes.add(box);
    }
    void clearBoxes() {
      boxes = new ArrayList<>();
    }
    void drawOn(char[][] target) {
      for (Box box : boxes) { //TODO: use functional operations?
        box.copyContent(target);
      }
    }
  }
  
  char[][] canvas;
  List<Layer> layers;
  int numberOfLayers;
    
  public LayeredCharCanvas(int height, int width) {
    canvas = new char[height][width];
    layers = new ArrayList<>();
  }
  public char[][] draw() {
    clearCanvas();
    for (Layer layer : layers) {
      layer.drawOn(canvas);
    }
    return canvas;
  }
  void clearCanvas() {
    for (char[] row : canvas) {
      Arrays.fill(row, TRANSPARENT);
    }
  }
  public void clearBoxes() {
    for (Layer layer : layers) {
      layer.clearBoxes();
    }
  }
  void addLayer(String layerName) {
    layers.add(new Layer(layerName));
  }
  Layer findLayer(String layerName) {
    for (Layer layer : layers) {
      if (layer.name.equals(layerName)) {
        return layer;
      }
    }
    return null;
  }
  Box addBoxToLayer(Box box, String layerName) {
    Layer layer = findLayer(layerName);
    if (layer == null) {
      return null;
    }
    layer.addBox(box);
    return box;
  }
  Box pinContentToLayer(char[][] content, String layerName, int y, int x, int[] alignment) {
    Box box = new Box(content, y, x);
    box.align(alignment);
    return addBoxToLayer(box, layerName);
  }
  Box pinContentToLayer(char[][] content, String layerName, int y, int x) {
    return addBoxToLayer(new Box(content, y, x), layerName);
  }
  Box findBox(String boxName) {
    Box box;
    for (Layer layer : layers) {
      if ((box = findBoxOnLayer(boxName, layer)) != null) {
        return box;
      }
    }
    return null;
  }
  Box findBoxOnLayer(String boxName, Layer layer) {
    for (Box box : layer.boxes) {
      if (box.name.equals(boxName)) {
        return box;
      }
    }
    return null;
  }
  Box findBoxOnLayer(String boxName, String layerName) {
    Layer layer = findLayer(layerName);
    if (layer != null) {
      return findBoxOnLayer(boxName, layer);
    }
    return null;
  }
  // Very usefull function for copying the contents of one char[][] to another
  public static void copyCanvas(char[][] source, char[][] target, int y, int x) {
    if (target.length == 0 || target[0].length == 0 || source.length == 0 
                                                    || source[0].length == 0) {
      return ;
    }
    // Determine which subregion of source will be copied, based on displacement (x,y).
    int jStart = (y < 0) ? -y : 0;
    int jEnd = (y + source.length > target.length) ? target.length - y 
                                                   : source.length;
    int iStart = (x < 0) ? -x : 0;                                   
    int iEnd = (x + source[0].length > target[0].length) ? target[0].length - x 
                                                 : source[0].length;
    for (int j = jStart; j < jEnd; j++) {
      for (int i = iStart; i < iEnd; i++) {
        if (source[j][i] != TRANSPARENT) {                               // Check for transparency.
          target[j+y][i+x] = source[j][i];
        }
      }
    }
  }
}

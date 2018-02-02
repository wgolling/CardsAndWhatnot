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
package cardsandwhatnot;

import cardsandwhatnot.io.console.ConsoleCardGraphics;
import cardsandwhatnot.lib.*;
import cardsandwhatnot.io.DisplayData;
import java.util.*;

/**
 *
 * @author William Gollinger
 */
public class CardsAndWhatnot {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    trySomeGraphics();
  }
  
  static void trySomeGraphics() {
    ConsoleCardGraphics graphics = new ConsoleCardGraphics(40,125);
    StandardDeck deck = new StandardDeck();
    deck.shuffle();
    Map<Integer, Hand> dealtHands = deck.deal(4, 13);
    List<String> players = Arrays.asList("Burt", "Doug", "Alphonse", "Terry");
    List<String> scores = Arrays.asList("20", "40", "4", "6");
    List<List<Card>> hands = new ArrayList<>();
    for (int i=1; i<5; i++) {
      hands.add(dealtHands.get(i).getCards());
    }
    List<Card> tableCards = Arrays.asList(new StandardCard(StandardCard.Rank.ACE, StandardCard.Suit.SPADES),
                                          new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.SPADES),
                                          new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS),
                                          new StandardCard(StandardCard.Rank.TEN, StandardCard.Suit.DIAMONDS));
    DisplayData data = new DisplayData(players, scores, hands, tableCards);
    graphics.setData(data);
    graphics.drawTable();
//    graphics.pinHand( hands.get(1).getCards(), graphics.NORTH[0], graphics.NORTH[1]);
//    graphics.pinHand( hands.get(2).getCards(), graphics.EAST[0], graphics.EAST[1]);
//    graphics.pinHand( hands.get(3).getCards(), graphics.SOUTH[0], graphics.SOUTH[1]);
//    graphics.pinHand( hands.get(4).getCards(), graphics.WEST[0], graphics.WEST[1]);
//    String output = "";
//    for (char[] row : graphics.makeOutput()) {
//      for (int i=0; i<row.length; i++) {
//        output+= String.valueOf(row[i]);
//      }
//      output += "\n";
//    }
//    System.console().format("%s", output);
  }
  
}

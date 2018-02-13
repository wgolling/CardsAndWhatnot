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

/**
 *
 * @author William Gollinger
 */
public class StandardCard implements Card {
  public enum Suit implements ValueTextEnum {
    CLUBS    (1, "Clubs",    "\u2663"),
    DIAMONDS (2, "Diamonds", "\u2666"),
    SPADES   (3, "Spades",   "\u2660"),
    HEARTS   (4, "Hearts",   "\u2665");
    			
    private final int value;
    private final String text;
    private final String symbol;
    Suit(int value, String text, String symbol) {
      this.value = value;
      this.text = text;
      this.symbol = symbol;
    }
    @Override
    public int value() {
      return value;
    }
    @Override
    public String text() {
      return text;
    }
    @Override
    public String symbol() {
      return symbol;
    }
    static public Suit intToSuit(int i) {
      switch (i) {
        case 1  : return CLUBS;
        case 2  : return DIAMONDS;
        case 3  : return SPADES;
        case 4  : return HEARTS;
        default : return null;
      }
    }
  }
  public enum Rank implements ValueTextEnum {
    TWO   (2,  "Two",   "2"),  
    THREE (3,  "Three", "3"),
    FOUR  (4,  "Four",  "4"),
    FIVE  (5,  "Five",  "5"),
    SIX   (6,  "Six",   "6"),
    SEVEN (7,  "Seven", "7"),
    EIGHT (8,  "Eight", "8"),
    NINE  (9,  "Nine",  "9"),
    TEN   (10, "Ten",   "10"),
    JACK  (11, "Jack",  "J"),
    QUEEN (12, "Queen", "Q"),
    KING  (13, "King",  "K"),
    ACE   (14, "Ace",   "A");
    
    private final int value;
    private final String text;
    private final String symbol;
    Rank(int value, String text, String symbol) {
      this.value = value;
      this.text = text;
      this.symbol = symbol;
    }
    @Override
    public int value() {
      return value;
    }
    @Override
    public String text() {
      return text;
    }
    @Override
    public String symbol() {
      return symbol;
    }
  }
  
  public static Card makeCard(String rank, String suit){
    for (Suit s : Suit.values()) {
      if (suit.equals(s.text())) {
        for (Rank r : Rank.values()) {
          if (rank.equals(r.text())) {
            return new StandardCard(r,s);
          }
        }
        break;
      }
    }
    return null;
  }
  
  private Rank rank;
  private Suit suit;
  public StandardCard(Rank rank, Suit suit) {
   this.rank = rank;
   this.suit = suit;
  }
  @Override
  public Rank getRank() {
    return rank;
  }
  @Override 
  public Suit getSuit() {
    return suit;
  }
  @Override 
  public String toString() {
    return this.rank.text() + " of " + this.suit.text();
  }
  @Override
  public boolean equals(Object other) {
    if (other instanceof Card) {
      if ( ((StandardCard)other).getRank() == this.rank &&
           ((StandardCard)other).getSuit() == this.suit) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  @Override
  public int hashCode() {
    return ((suit.value()-1)*13)+rank.value();
  }
  @Override
  public int compareTo(Card other) {
    return hashCode() - other.hashCode();
  }
}

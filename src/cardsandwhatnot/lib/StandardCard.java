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
  public enum Suit implements valueTextEnum {
    DIAMONDS (1, "Diamonds"),
    CLUBS    (2, "Clubs"),
    HEARTS   (3, "Hearts"),
    SPADES   (4, "Spades");
    
    private final int value;
    private final String text;
    Suit(int value, String text) {
      this.value = value;
      this.text = text;
    }
    @Override
    public int value() {
      return value;
    }
    @Override
    public String text() {
      return text;
    }
  }
  public enum Rank implements valueTextEnum {
    TWO (2, "Two"),  
    THREE (3, "Three"),
    FOUR (4, "Four"),
    FIVE (5, "Five"),
    SIX (6, "Six"),
    SEVEN (7, "Seven"),
    EIGHT (8, "Eight"),
    NINE (9, "Nine"),
    TEN (10, "Ten"),
    JACK (11, "Jack"),
    QUEEN (12, "Queen"),
    KING (13, "King"),
    ACE (14, "Ace");
    
    private final int value;
    private final String text;
    Rank(int value, String text) {
      this.value = value;
      this.text = text;
    }
    @Override
    public int value() {
      return value;
    }
    @Override
    public String text() {
      return text;
    }
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
    return (other instanceof Card && (rank == ((Card)other).getRank())
                                  && (suit == ((Card)other).getSuit()) ) ? true : false;
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

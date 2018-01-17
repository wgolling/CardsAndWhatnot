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

import java.util.*;
import cardsandwhatnot.io.InputProcessor;

/**
 *
 * @author William Gollinger
 */
public class ConsoleInputProcessor implements InputProcessor {
  
  List<String> games;
  List<String> players;
  
  public ConsoleInputProcessor(List<String> games,List<String> players) {
    this.games = games;
    this.players = players;
  }
    
  // Takes input strings and parses them into a Rank or Suit id if it can.
  @Override
  public String[] parseCard(String rank, String suit) {
    return new String[] {parseRank(rank), parseSuit(suit)};
  }
  private String parseSuit(String input) {
    switch (input.toUpperCase()) {
      // Standard Suits
      case "HEARTS"  : return "Hearts";
      case "SPADES"  : return "Spades";
      case "CLUBS"   : return "Clubs";
      case "DIAMONDS": return "Diamonds";
      // Uno Suits
      case "RED"    : return "Red";
      case "BLUE"   : return "Blue";
      case "GREEN"  : return "Green";
      case "YELLOW" : return "Yellow";
      case "BLACK"  : return "Black";
    }
    return null;
  }
  private String parseRank(String input) {
    switch (input.toUpperCase()) {
      // Standard Ranks
      case "ACE"  : return "Ace";
      case "A"    : return "Ace";
      case "DEUCE": return "Two";
      case "TWO"  : return "Two";
      case "2"    : return "Two";
      case "THREE": return "Three";
      case "3"    : return "Three";
      case "FOUR" : return "Four";
      case "4"    : return "Four";
      case "FIVE" : return "Five";
      case "5"    : return "Five";
      case "SIX"  : return "Six";
      case "6"    : return "Six";
      case "SEVEN": return "Seven";
      case "7"    : return "Seven";
      case "EIGHT": return "Seven";
      case "8"    : return "Seven";
      case "NINE" : return "Nine";
      case "9"    : return "Nine";
      case "TEN"  : return "Ten";
      case "10"   : return "Ten";
      case "JACK" : return "Jack";
      case "J"    : return "Jack";
      case "QUEEN": return "Queen";
      case "Q"    : return "Queen";
      case "KING" : return "King";
      case "K"    : return "King";
      // Uno ranks
    }
    return null;
  }
  // Checks if Game exists and returns its id string if it does.
  // Not case sensitve.
  @Override
  public String parseGame(String input){
    for (String game : games) {
      if (input.toUpperCase().equals(game.toUpperCase()) ) {
        return game;
      }
    }
    return null;
  }
  // Checks if Player exists and returns its id string if it does. 
  // Case sensitive.
  @Override
  public String parsePlayer(String input) {
    for (String player : players) {
      if (input.equals(player) ) {
        return player;
      }
    }
    return null;
  }
}

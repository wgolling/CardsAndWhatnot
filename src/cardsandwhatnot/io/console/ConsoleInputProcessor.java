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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import cardsandwhatnot.io.InputProcessor;

/**
 * The ConsoleUnputProcessor only deals with strings.
 * It takes console input from the user and determines if it represents an appropriate object.
 * If there is such an object, the processor will return a string identifier.
 * The prompts will recursively re-prompt if they find null values.
 * @author William Gollinger
 */
public class ConsoleInputProcessor implements InputProcessor {
  
  BufferedReader br;
  List<String> games; 
  List<String> players;
  
  public ConsoleInputProcessor(List<String> games, List<String> players) {
    this.games = games;
    this.players = players;
    br = new BufferedReader(new InputStreamReader(System.in));
  }
  
  /*
  Input-getting methods.
  */
  
  /**
   * Basic prompt, gets a string from the user.
   * @param message
   * @return 
   */
  public String prompt(String message) {
   System.out.println(message);
   String s = "";
   try {
     s = br.readLine();
   } catch(IOException e) {
     
   }
   return s;
  }
  /**
   * Prompts user for a card, with an optional message.
   * A null entry occurs when an input word does not express an expected string. 
   * @param extraMessage
   * @return 
   */
  @Override
  public String[] promptCard(String extraMessage) {
    String s = prompt(extraMessage + "\nEnter Card with format 'Rank Suit':");
    String[] cardParts = s.split(" ");
    cardParts = parseCard(cardParts);
    String newMessage = "";
    if (cardParts[0] == null) {
      newMessage += "\nRank not recognized.";
    }
    if (cardParts[1] == null) {
      newMessage += "\nSuit not recognized.";
    }
    if (newMessage.equals("")) {
      return cardParts;
    }
    return promptCard(newMessage);
  }
  public String[] promptCard(){
    return promptCard("");
  }
  /** 
   * Prompts user for a game, with an optional message.
   * Returns null if input does not represent any expected Game.
   * @param extraMessage
   * @return 
   */
  @Override
  public String promptGame(String extraMessage) {
    String input = prompt(extraMessage + "\nWhat game do you want to play?");
    for (String game : games) {
      if (input.toUpperCase().equals(game.toUpperCase()) ) {
        return game;
      }
    }
    // If no valid game was found, reprompt with error message;
    return promptGame("Game not recognized.");
  }
  /**
   * Prompts user for a player, with an optional message.
   * Returns null if input does not represent any expected Player.
   * @param extraMessage
   * @return 
   */
  @Override
  public String promptPlayer(String extraMessage) {
    String input = prompt(extraMessage + "\nChoose a player.");
    for (String player : players) {
      if (input.equals(player) ) {
        return player;
      }
    }
    // If no valid player was found, reprompt with error message;
    return promptPlayer("Player not recognized.");
  }
  /**
   * "Press the Any key."
   * @param message 
   */
  @Override
  public void promptContinue(String message) {
    prompt(message + "Press Enter to continue.");
  }
  
  // Helper function for processing card input.
  // The CardGameEngine class determines if the Rank and Suit make a valid Card (depends on card type).
  String[] parseCard(String[] cardParts) {
    cardParts[0] = parseRank(cardParts[0]);
    cardParts[1] = parseSuit(cardParts[1]);
    return cardParts;
  }
  String parseRank(String input) {
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
      case "EIGHT": return "Eight";
      case "8"    : return "Eight";
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
      //..
    }
    return null;
  }
  String parseSuit(String input) {
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
}

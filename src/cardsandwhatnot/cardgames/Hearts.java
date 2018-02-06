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
package cardsandwhatnot.cardgames;

import cardsandwhatnot.lib.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author William Gollinger
 */
public class Hearts extends CardGame {
  
  Card QUEEN_OF_SPADES = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.SPADES);
  Card TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS);
  ValueTextEnum HEARTS = StandardCard.Suit.HEARTS;
  ValueTextEnum trickSuit;
  List<Integer> roundScores;
  List<Hand> takenCards;
  int leadPlayer=0;
  boolean firstTrick = true;
  boolean heartsBled = false;
  final static int MAX_SCORE = 100;
  
  public Hearts(List<Player> players) {
    super(players);
    name = "HEARTS";
    cardType = "STANDARD";
    gameDeck = new StandardDeck();
    scores = new ArrayList<>();
    roundScores = new ArrayList<>();
    takenCards = new ArrayList<>();
    for (int i=0; i<this.players.size(); i++) {
      scores.add(0);
      takenCards.add(new Hand());
    }
    gameOver = (roundOver = (trickOver = false));
  }
  void forEachPlayer(Runnable action) {
    repeat(players.size(), action);
  }
  void repeat(int times, Runnable action) {
    IntStream.rangeClosed(1, times).forEach(i -> action.run());
  }
  /**
   * Test if a card is valid in the current game state.
   * @param card
   * @return 
   */
  @Override
  boolean validatePlay(Card card) {
    if (firstTrick && (currentPlayer == leadPlayer && !(card.equals(TWO_OF_CLUBS)) 
                    || card.equals(QUEEN_OF_SPADES)) ){                      // If it's the first trick, you must lead TWO_OF_CUBS and no one can play QUEEN_OF_SPADES
      return false; 
    }
    if (currentPlayer == leadPlayer) {                                       // If you lead, the only invalid play is a heart when hearts aren't bled and you have other suits.
      if (card.getSuit()==HEARTS && heartsBled==false && !onlyHasHearts(hands.get(currentPlayer))){
        return false;
      }
      trickSuit = card.getSuit();
      return true;
    } 
    return ( card.getSuit() == trickSuit 
          || !(hands.get(currentPlayer).hasSuit(trickSuit)) );               // If you don't lead, you only need to match suit or have none of it.
  }
  /**
   * Returns true iff the card is a Heart or the Queen of Spades.
   * @param card
   * @return 
   */
  boolean isPointCard(Card card) {
    return ( (card.getSuit() == HEARTS) || card.equals(QUEEN_OF_SPADES) );
  }
  int pointValue(Card card) {
    if (card.getSuit() == HEARTS) {
      return 1;
    } else if (card.equals(QUEEN_OF_SPADES)) {
      return 13;
    } else {
      return 0;
    }
  }
  private boolean onlyHasHearts(Hand hand) {
    for (Card card : hand.getCards()) { // TODO? use functional operation to just AND over the array
      if (card.getSuit() != HEARTS) {
        return false;
      }
    }
    return true;
  }
  @Override
  Card defaultCard() {
    Hand hand = hands.get(currentPlayer);
    // TODO choose random suit
    if (currentPlayer == leadPlayer) {
      return hand.getCards().get(0);
    } else if (hand.hasSuit(trickSuit)) {
      return hand.getLast(trickSuit);
    } else if (hand.hasSuit(HEARTS)) {
      return hand.getLast(HEARTS);
    } else {
      return hand.getCards().get(0);
    }
  }
  /**
   * At the start of the Game, the scores are zeroed.
   */
  @Override  
  void setupGame(){
    gameOver = roundOver = trickOver = false;
    for (int i=0; i<players.size();i++) {
      scores.set(i, 0);
    }
  }
  /**
   * At the start of a round: Hearts have not been bled, round scores are 0,
   * cards have been dealt, no one has taken tricks, and the 2 of Clubs leads.
   */
  @Override
  void setupRound(){
    // un-bleed Hearts
    heartsBled = false;
    firstTrick = true;
    trickOver = false;
    roundOver = false;
    // reset roundScores list
    roundScores = new ArrayList<>();
    forEachPlayer(() -> roundScores.add(0));
    // deal cards
    Deck deck = new StandardDeck();
    deck.shuffle();
    List<Hand> newHands = new ArrayList<>(deck.deal(4, 13).values());
    for (int i=0; i < players.size(); i++) {
      players.get(i).getHand().addCards(newHands.get(i).getCards()); //TODO rewrite this
    }
    // zero the takenCards, and determine who has the 2 of clubs
    for (int i=0; i < players.size(); i++) {
      takenCards.set(i, new Hand());
      if (hands.get(i).hasCard(TWO_OF_CLUBS)) {
        currentPlayer = (leadPlayer = i);
      }
    }  
  }
  /**
   * At the start of a trick the suit has not been determined and
   * there are no cards on the table.
   */
  @Override
  public void setupTrick() {
    trickOver = false;
    trickSuit = null;
    //reset table cards
    tableCards = new ArrayList<>();
    forEachPlayer(() -> tableCards.add(null));
  }
  /**
   * At the start of a play nothing special happens.
   */
  @Override
  void setupPlay(){
    //diagnostic info
    System.out.println("Hearts bled:" + heartsBled);
    System.out.println("Trick suit:" + trickSuit);
    System.out.println("Trick over?:" + trickOver);
    System.out.println("Round over?:" + roundOver);
    System.out.println("Game over?:" + gameOver);
  }
  /**
   * After a card has been played it is placed on the table, 
   * hearts are bled if it was a point card, focus moves to the next player.
   * If focus is back on the lead player, the trick is over.
   * @param card 
   */
  @Override
  void resolvePlay(Card card){
    tableCards.set(currentPlayer, card);
    if (isPointCard(card)) {
      heartsBled = true;
    }
    // if current player led the trick, set trickSuit
    if (currentPlayer == leadPlayer) {
      trickSuit = card.getSuit();
    }
    // increment current player
    currentPlayer = (currentPlayer+1)%players.size();
    // if we're back at the start, the trick is over
    if (currentPlayer == leadPlayer) {
      trickOver = true;
    }
  }
  /**
   * When the trick is over the winner is determined, who then takes the kitty
   * and is set as the new lead player.
   * If all players are out of cards, the round is over.
   */
  @Override
  public void resolveTrick() {
    firstTrick = false;
    // determine the winner
    int winner = leadPlayer;
    for (int i=0; i<players.size(); i++) {
      if (tableCards.get(i).getSuit() == tableCards.get(winner).getSuit()
       && tableCards.get(i).compareTo(tableCards.get(winner)) > 0){
        winner = i;
      }
    }
    // distribute table cards and set leadPlayer
    takenCards.get(winner).addCards(tableCards);
    currentPlayer = leadPlayer = winner;
    // if noone has cards left, the round is over
    if(hands.get(currentPlayer).size() == 0) {
      roundOver = true;
    }  
  }
  /**
   * At the end of the round, points are allocated. 
   * If anyone has 26 they "Shoot the Moon" (everyone else gets 26 points), 
   * otherwise everyone adds to their scores as usual.
   * If someone has enough points, the Game is over.
   */
  @Override
  void resolveRound(){
    // determine player scores, taking moon-shooting into account
    int moonShooter = -1;
    int score;
    for (int i=0; i < players.size(); i++) {
      score = 0;
      for (Card card : takenCards.get(i).getCards()) {
        score += pointValue(card);
      }
      if (score == 26) {
        moonShooter = i;
        break;
      }
      roundScores.set(i, score);
    }
    if (moonShooter >= 0) {
      shootTheMoon(moonShooter);
    } else {
      updateScores();
    }
    // if someone's score is over the threshold, the game is over
    for (int j : scores) {
      if (j > MAX_SCORE) {
        gameOver = true;
      }
    }
  }
  private void shootTheMoon(int player) {
    if (player < 0 || player >= players.size()) {
      // TODO throw exception 
    }
    for (int i=0; i < players.size(); i++) {
      if (!(i==player)) {
        scores.set(i-1, scores.get(i-1) + 26);
      }
    }
  }
  private void updateScores() {
    for (int i=0; i<players.size(); i++) {
      scores.set(i, scores.get(i) + roundScores.get(i)); 
    }
  }
  /**
   * When the game is over, those players with minimal score get a "win".
   */
  @Override
  void resolveGame(){
    // Distribute "win" to player(s) with lowest score.
    int winner = 0;
    for (int i=1; i < players.size(); i++) {
      if (scores.get(i) < scores.get(winner)) {
        winner = i;
      }
    }
    // Make list of players with winning score, increment their wins.
    List<Player> winners = new ArrayList<>();
    for (int i=0; i < players.size(); i++) {
      if (scores.get(i).equals(scores.get(winner))) {
        winners.add(players.get(i));
      }
    }
    for (Player player : winners) {
      player.winGame(name);
    }
  }
}

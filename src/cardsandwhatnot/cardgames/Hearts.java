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

/**
 *
 * @author William Gollinger
 */
public class Hearts extends CardGame {
  
  Card QUEEN_OF_SPADES = new StandardCard(StandardCard.Rank.QUEEN, StandardCard.Suit.HEARTS);
  Card TWO_OF_CLUBS = new StandardCard(StandardCard.Rank.TWO, StandardCard.Suit.CLUBS);
  ValueTextEnum HEARTS = StandardCard.Suit.HEARTS;
  ValueTextEnum trickSuit;
  List<Integer> roundScores;
  List<Hand> takenCards;
  int leadPlayer=0;
  boolean heartsBled = false;
  boolean trickOver = false;
  final static int MAX_SCORE = 100;
  
  public Hearts(List<Player> players) {
    super(players);
    name = "HEARTS";
    cardType = "STANDARD";
    gameDeck = new StandardDeck();
    roundScores = new ArrayList<>();
    takenCards = new ArrayList<>();
    for (int i=0; i<this.players.size(); i++) {
      takenCards.add(new Hand());
    }
  }
  @Override
  boolean validatePlay(Card card) {
    // test if a card is valid in the current game state
    if (trickSuit == null) {
      // If you lead, the only invalid play is a point card when hearts aren't bled and you have other suits.
      if ( (isPointCard(card)) && heartsBled == false && !(onlyHasHearts(hands.get(currentPlayer))) ) {
        return false;
      }
      trickSuit = card.getSuit();
      if (isPointCard(card)) {
        heartsBled = true;
      }
      return true;
    } 
    // If you don't lead, you only need to match suit or have none of it.
    if (card.getSuit() == trickSuit || !(hands.get(currentPlayer).hasSuit(trickSuit))) {
      if (isPointCard(card)) {
        heartsBled = true;
      }
      return true;
    }
    return false;
  }
  private boolean isPointCard(Card card) {
    return (card.getSuit() == HEARTS || card.equals(QUEEN_OF_SPADES) );
  }
  private boolean onlyHasHearts(Hand hand) {
    for (Card card : hand.getCards()) { // TODO: use functional operation to just AND over the array
      if (card.getSuit() != HEARTS) {
        return false;
      }
    }
    return true;
  }
  @Override
  Card defaultCard() {
    // implement AI for chosing a card
    return null;
  }
  @Override  
  void setupGame(){
    for (Integer i : scores) {
      i=0;
    }
  }
  @Override
  void resolveGame(){
    // Distribute "win" to player(s) with lowest score.
    int winner = Integer.MAX_VALUE;
    for (int i=0; i < players.size(); i++) {
      if (scores.get(i) < scores.get(winner)) {
        winner = i;
      }
    }
    // Make list of players with winning score, increment their wins.
    List<Player> winners = new ArrayList<>();
    for (int i=0; i < players.size(); i++) {
      if (scores.get(i) == scores.get(winner)) {
        winners.add(players.get(i));
      }
    }
    for (Player player : players) {
      player.winGame(name);
    }
  }
  @Override
  void setupRound(){
    // zero roundScores list
    roundScores = new ArrayList<>();
    for (Player player : players) {
      roundScores.add(0);
    }
    // un-bleed Hearts
    heartsBled = false;
    // deal cards
    Deck deck = new StandardDeck();
    deck.shuffle();
    hands = new ArrayList<>(deck.deal(4, 13).values());
    // zero the takenCards, and determine who has the 2 of clubs
    for (int i=0; i < players.size(); i++) {
      takenCards.set(i, new Hand());
      if (hands.get(i).hasCard(TWO_OF_CLUBS)) {
        currentPlayer = leadPlayer = i;
      }
    }  
  }
  @Override
  void resolveRound(){
    // determine player scores, taking moon-shooting into account
    int shotTheMoon = 0;
    for (int i=1; i <= players.size(); i++) {
      if (roundScores.get(i-1) == 26) {
        shotTheMoon = i;
        break;
      }
    }
    if (shotTheMoon > 0) {
      shootTheMoon(shotTheMoon);
    } else {
      updateScores();
    }
    // if someone's score is over the threshold, the game is over
    for (int score : scores) {
      if (score > MAX_SCORE) {
        gameOver = true;
      }
    }
  }
  private void shootTheMoon(int player) {
    if (player < 1 || player > players.size()) {
      // throw exception 
    }
    for (int i=1; i <= players.size(); i++) {
      if (!(i==player)) {
        Integer newScore = scores.get(i-1);
        newScore += 26;
        scores.set(i-1, newScore);
      }
    }
  }
  private void updateScores() {
    for (int i=0; i<players.size(); i++) {
      scores.set(i, scores.get(i) + roundScores.get(i)); 
    }
  }
  @Override
  void setupPlay(){
    // if it's the first play of the trick, set up the trick
    if (currentPlayer == leadPlayer) {
      setupTrick();
    }
  }
  @Override
  void resolvePlay(Card card){
    tableCards.set(currentPlayer, card);
    // if current player led the trick, set trickSuit
    if (currentPlayer == leadPlayer) {
      trickSuit = card.getSuit();
    }
    // increment current player
    currentPlayer = (currentPlayer+1)%players.size();
    // if we're back at the start, the trick is over
    if (currentPlayer == leadPlayer) {
      resolveTrick();
    }
    // if noone has cards left, the round is over
    if(hands.get(0).size() == 0) {
      roundOver = true;
    }
  }
  private void setupTrick() {
    trickSuit = null;
    //reset table cards
    tableCards = new ArrayList<>();
  }
  private void resolveTrick() {
    // determine the winner
    int winner = 0;
    for (int i=1; i<players.size(); i++) {
      if (tableCards.get(i).getSuit() == tableCards.get(winner).getSuit() 
       && tableCards.get(i).compareTo(tableCards.get(winner)) > 0){
        winner = i;
      }
    }
    // distribute table cards and set leadPlayer
    takenCards.get(winner).addCards(tableCards);
    currentPlayer = leadPlayer = winner;
  }
}

package application;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	ArrayList<Card> deck = new ArrayList<Card>();
	
	ArrayList<Card> waste = new ArrayList<Card>();
	
	private int size;
	
	Deck() {
		for(Rank rank : Rank.values()) {
			for(Suit suit : Suit.values()) {
				deck.add(new Card(rank,suit));
				size++;
			}
		}
	}
	/**
	 * whether the deck is empty
	 * @return true/false
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * size of the deck
	 * @return int
	 */
	public int size() {
		return size;
	}
	/**
	 * shuffled the deck 
	 */
	public void shuffle() {
		for (int k = deck.size() - 1; k > 0; k--) {
			int howMany = k + 1;
			int start = 0;
			int randPos = (int) (Math.random() * howMany) + start;
			Card temp = deck.get(k);
			deck.set(k, deck.get(randPos));
			deck.set(randPos, temp);
		}
		size = deck.size();
	}
	/**
	 * returns top card and removes it
	 * @return Card
	 */
	public Card pop() {
		
		Card temp = deck.get(deck.size() - 1);
		deck.remove(deck.size() - 1);
		size--;
		return temp;
	}
	/**
	 * moves one card from the deck to waste
	 */
	public void moveToWaste() {
		
		waste.add(pop());
		waste.get(waste.size() - 1).faceUp();
		waste.get(waste.size() - 1).translateBy(140, 60);
	}
	/**
	 * move all the cards from waste back to the deck
	 */
	public void moveBackToDeck() {
		
		deck.addAll(waste);
		waste.clear();
		Collections.reverse(deck);
		for(int i = 0; i < deck.size(); i++) {
			deck.get(i).faceDown();
			deck.get(i).setImageView();
			deck.get(i).translateBy(20, 60);
		}
	}
	
	public String toString() {
		return "deck size:" + size + "waste size:" + waste.size();
	}
}
package application;

import java.util.ArrayList;
import java.util.List;

public class Tableau {
	
	List<Card> pile = new ArrayList<Card>();
	
	Tableau() {}
	/**
	 * removes last item of tableau
	 * @return Card
	 */
	public Card pop() {
		
		Card temp = pile.get(pile.size() - 1);
		pile.remove(pile.size() - 1);
		return temp;
	}
	
	/**
	 * Removes the lastfew indexes of the pile
	 * @param lastfew
	 */
	public void pop(int lastfew) {
		
		if(lastfew < pile.size()) {
			for(int i = 1; i <= lastfew; i++) {
				pile.remove(pile.size() - i);
			}
		}
	}
	/**
	 * Whether the tableau is droppable for a specific card
	 * @param card
	 * @return true/false
	 */
	public boolean droppable(Card card) {
		
		if(pile.size() == 0 && card.getRank() == Rank.KING) {
			
			return true;
			
		} else if( pile.size() > 0 && !(pile.get(pile.size() - 1).getSuit().getColor().equals(card.getSuit().getColor()))
				&& (pile.get(pile.size() - 1).getRank().getPointValue() == card.getRank().getPointValue() + 1)) {

			return true;
		} else {
			
			return false;
		}
	}
	/**
	 * whether the card in the tableau is pressable
	 * @param card
	 * @return true/false
	 */
	public boolean pressable(Card card) {
		
		if(pile.size() == 0) {
			return false;
		}
		int index = 0;
		
		for(int i = 0; i < pile.size(); i++) {
			if(pile.get(i) ==  card) {
				index = i;
				break;
			}
		}
		if(index == pile.size() - 1) {
			return true;
		}
		
		for(int i = index; i < pile.size() - 1; i++) {
			if (pile.get(i).getFace()==(false)) {
				return false;
			}
			if (pile.get(i).getRank().getPointValue() != pile.get(i + 1).getRank().getPointValue() + 1) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		return "pile size = " + pile.size();
	}
	
}

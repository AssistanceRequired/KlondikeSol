package application;

import java.util.ArrayList;
import java.util.List;

public class Foundation {
	
	List<Card> pile = new ArrayList<Card>();

	Foundation() {}
	/**
	 * Checks to see if the foundation is droppable for a specific card
	 * @param card
	 * @return true/false
	 */
	public boolean droppable(Card card) {
		
		if(pile.size() == 0 && card.getRank() == Rank.ACE) {
			
			return true;
			
		} else if(pile.size() > 0) {
			
			if(pile.get(pile.size() - 1).getSuit() == card.getSuit() && 
					pile.get(pile.size() - 1).getRank().getPointValue() + 1 == card.getRank().getPointValue()) {
				return true;
			}
		}
		return false;
	}
}

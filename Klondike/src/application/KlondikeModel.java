package application;

public class KlondikeModel {
	
	Deck game;
	Foundation[] foundationF = new Foundation[4];
	Tableau[] tableauT = new Tableau[7];
	
	KlondikeModel() {
		
		game = new Deck();
		game.shuffle();
		
		
		for(int i = 0; i < 4; i++) {
			foundationF[i] = new Foundation();
		}
		
		for(int i = 0; i < 7; i++) {
			tableauT[i] = new Tableau();
		}
		setUpTableau(game, tableauT);
		setUpFaceOfTableau(tableauT);
		
	}
	/**
	 * sets up the tableau by dealing the cards
	 * @param deck
	 * @param tableau
	 */
	
	public void setUpTableau(Deck deck, Tableau[] tableau) {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j <= i; j++) {
				tableau[i].pile.add(deck.pop());
			}
		}
	}
	/**
	 * sets up the face of the tableaus (faceUp)
	 * @param tableau
	 */
	public void setUpFaceOfTableau(Tableau[] tableau) {
		for(int i = 0; i < tableau.length; i++) {
			tableau[i].pile.get(i).faceUp();
		}
	}
	
}

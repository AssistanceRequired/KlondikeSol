/*
 * Jack Dai
 * Brooklyn Technical Highschool
 * Klondike Cards: IDK project extension
 * Implement Klondike Solitaire
 */
package application;
	
import javafx.application.Application;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
	
	KlondikeModel model = new KlondikeModel();
	/*
	 * Pointer Card, Tableau, Foundation, tableauindex, and foundationindex to keep track of card that is being clicked on
	 */
	Card cardtemp;
	Tableau tableautemp;
	Tableau draggedtotableau;
	Foundation foundationtemp;
	boolean tableaudragged = false;
	boolean deckdragged = false;
	int tableauindex;
	int foundationindex;
	int cardindex;
	
	@FXML private Pane field;
	@FXML private Pane tableaufield;
	@FXML private Pane deckfield;
	@FXML private Button reset;
	@FXML private Pane foundationfield;
	@FXML private ImageView foundation1;
	@FXML private ImageView foundation2;
	@FXML private ImageView foundation3;
	@FXML private ImageView foundation4;
	@FXML private ImageView wastepile;
	
	@Override
	public void initialize(URL url, ResourceBundle resourcebundle) {
		
		setUpGame();
		
		deckFieldClicked();
		
		deckfield.setOnMouseDragged(deckfieldsetOnMouseDragged);
		
		tableaufield.setOnMouseClicked(tableaufieldsetOnMouseClicked);
		
		tableaufield.setOnMouseDragged(tableaufieldsetOnMouseDragged);
		
		tableaufield.setOnDragOver(tableaufieldsetOnDragOver);
		
		foundationfield.setOnDragOver(new EventHandler<DragEvent>() {
			
			@Override
			public void handle(DragEvent e) {
				if (foundationClickedOn(e.getX(), e.getY())) {
					foundationtemp = getFoundationFrom(e.getX(),e.getY());
					if (foundationtemp.droppable(cardtemp)) {
						if (tableaudragged && 
								(cardindex == tableautemp.pile.size() - 1)) {
							cardFromTableauToFoundation(foundationtemp);
						} 
						if (deckdragged) {
							cardFromWasteToFoundation(foundationtemp);
						}
					}
				}
			}
		});
		
	}
	/*
	 * MouseEvent and DragEvent for visuals 
	 */
	EventHandler<MouseEvent> deckfieldsetOnMouseDragged = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent e) {
			deckdragged = true;
			tableaudragged = false;
			if (model.game.waste.size() > 0 && e.getY() > 60 && e.getY() < 180 &&
					e.getX() > 140 && e.getX() < 240) {
				cardtemp = model.game.waste.get(model.game.waste.size() - 1);
				model.game.waste.get(model.game.waste.size() - 1).mouseDragged();
			}
		}
	};
	EventHandler<MouseEvent> tableaufieldsetOnMouseClicked = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent e) {
			deckdragged = false;
			tableaudragged = true;
			if (tableauClickedOn(e.getX(), e.getY()) && getTableauFrom(e.getX(), e.getY()).pile.size() > 0) {
				if (cardInTableauDraggable(tableautemp,e.getX(), e.getY()) 
						== getTableauFrom(e.getX(), e.getY()).pile.get(getTableauFrom(e.getX(), e.getY()).pile.size() - 1)) {
					cardInTableauDraggable(tableautemp,e.getX(), e.getY()).faceUp();
					cardInTableauDraggable(tableautemp,e.getX(), e.getY()).setImageView();
				}
			}
		}
	};
	EventHandler<MouseEvent> tableaufieldsetOnMouseDragged = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent e) {
			deckdragged = false;
			tableaudragged = true;
			if (tableauClickedOn(e.getX(), e.getY()) && getTableauFrom(e.getX(), e.getY()).pile.size() > 0) {
				tableautemp = getTableauFrom(e.getX(), e.getY());
				if (tableautemp.pile.size() != 0) {
					cardtemp = cardInTableauDraggable(tableautemp,e.getX(), e.getY());
					if (tableautemp.pressable(cardtemp)) {
						cardtemp.mouseDragged();
					}
				}
			}
		}
	};
	EventHandler<DragEvent> tableaufieldsetOnDragOver = new EventHandler<DragEvent>() {
		
		@Override
		public void handle(DragEvent e) {
			
			if (tableauClickedOn(e.getX(), e.getY())) {
				draggedtotableau = getTableauFrom(e.getX(), e.getY());
				if (cardtemp != null && draggedtotableau.droppable(cardtemp)) {
					if (deckdragged) {
						cardFromWasteToTableau();
					} 
					if (tableaudragged) {
						cardFromTableauToTableau();
					}
				}
			}
			
		}
	};
	/**
	 * resets the game by clearing all the imageviews then recreating a new instance of klondikeModel
	 */
	@FXML protected void reset() {
		
		for (int i = 0; i < model.game.deck.size(); i++) {
			model.game.deck.get(i).setImageToNull();
		}
		for (int i = 0; i < model.game.waste.size(); i++) {
			model.game.waste.get(i).setImageToNull();
		}
		for (Tableau t: model.tableauT) {
			for (int i = 0; i < t.pile.size(); i++ ) {
				t.pile.get(i).setImageToNull();
			}
		}
		for (Foundation f:model.foundationF) {
			for (int i = 0; i < f.pile.size(); i++) {
				f.pile.get(i).setImageToNull();
			}
		}
		
		model = new KlondikeModel();
		setUpGame();
		
	}
	/**
	 * sets up the standard field 
	 */
	private void setUpGame() {
		for (int i = 0; i < model.tableauT.length; i++) {
			for (int j = 0; j < model.tableauT[i].pile.size(); j++) {
				tableaufield.getChildren().add(model.tableauT[i].pile.get(j).cardImage());
				model.tableauT[i].pile.get(j).translateBy(10 + 110 * i,15 + 20 * j);
			}
		}
		for (int k = 0; k < model.game.deck.size(); k++) {
			deckfield.getChildren().add(model.game.deck.get(k).cardImage());
			model.game.deck.get(k).translateBy(20, 60);
		}
	}
	/**
	 * checks to see if deck has been clicked when mouse clicks on a deckfield region
	 */
	private void deckFieldClicked() {
		deckfield.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if ((e.getX() > 20 && e.getX() < 120) 
						&& e.getY() > 60 && e.getY() < 180) {
					if (model.game.deck.size() > 0) {
						if (model.game.waste.size() > 0) {
							model.game.waste.get(model.game.waste.size() - 1).setImageToNull();
						}
						model.game.moveToWaste();
						model.game.waste.get(model.game.waste.size() - 1).setImageView();
					} else {
						model.game.moveBackToDeck();
					}
				}
			}
		});
	}
	/**
	 * finds the tableau from x and y coordinates 
	 * @param x
	 * @param y
	 * @return Tableau
	 */
	private Tableau getTableauFrom(double x, double y) {
		
		if ( x > 10 && x < 110 && y > 15 && y < (model.tableauT[0].pile.size() - 1) * 20 + 135 ) {
			tableauindex = 0;
			return model.tableauT[0];
		} else if ( x > 120 && x < 220 && y > 15 && y < (model.tableauT[1].pile.size() - 1) * 20 + 135) {
			tableauindex = 1;
			return model.tableauT[1];
		} else if ( x > 230 && x < 330 && y > 15 && y < (model.tableauT[2].pile.size() - 1) * 20 + 135) {
			tableauindex = 2;
			return model.tableauT[2];
		} else if( x > 340 && x < 440 && y > 15 && y < (model.tableauT[3].pile.size() - 1) * 20 +  135) {
			tableauindex = 3;
			return model.tableauT[3];
		} else if ( x > 450 && x < 550 && y > 15 && y < (model.tableauT[4].pile.size() - 1) * 20 +  135 ) {
			tableauindex = 4;
			return model.tableauT[4];
		} else if( x > 560 && x < 660 && y > 15 && y < (model.tableauT[5].pile.size() - 1) * 20 +  135) {
			tableauindex = 5;
			return model.tableauT[5];
		} else if ( x > 670 && x < 770 && y > 15 && y < (model.tableauT[6].pile.size() - 1)* 20 + 135) {
			tableauindex = 6;
			return model.tableauT[6];
		} 
		return null;
	}
	/**
	 * finds foundation from x and y coordinates
	 * @param x
	 * @param y
	 * @return Foundation
	 */
	private Foundation getFoundationFrom(double x, double y) {
		
		if (x > 10 && x < 110 && y > 60 && y < 180 ) {
			foundationindex = 0;
			return model.foundationF[0];
		} else if (x > 120 && x < 220 && y > 60 && y < 180) {
			foundationindex = 1;
			return model.foundationF[1];
		} else if (x > 230 && x < 330 && y > 60 && y < 180 ) {
			foundationindex = 2;
			return model.foundationF[2];
		} else if(x > 340 && x < 440 && y > 60 && y < 180) {
			foundationindex = 3;
			return model.foundationF[3];
		} else {
			return null;
		}
	}
	/**
	 * checks to see if foundation is clicked on or not
	 * @param x
	 * @param y
	 * @return true/false
	 */
	private boolean foundationClickedOn(double x, double y) {
		
		return getFoundationFrom(x,y) != null;
	}
	/**
	 * checks to see if tableau is clicked on or not
	 * @param x
	 * @param y
	 * @return true/false
	 */
	private boolean tableauClickedOn(double x, double y) {
		
		return (getTableauFrom(x, y) != null);
	}
	/**
	 * gets card from tableau, x, and y coordinates
	 * @param tableau
	 * @param x
	 * @param y
	 * @return card
	 */
	private Card cardInTableauDraggable(Tableau tableau, double x, double y) {
		if ( tableau.pile.size() != 0) {
			for (int i = tableau.pile.size() - 1; i >= 0 ; i--) {
				if ( y > (15 + 20 * i) && y < ((15 + 20 * i) + 120) ) {
					//System.out.println(i);
					cardindex = i;
					return tableau.pile.get(i);
				}
			}
		}
		return null;
	}
	/**
	 * card moved from tableau to foundation
	 * @param foundation
	 */
	private void cardFromTableauToFoundation(Foundation foundation) {
		for (int i = tableautemp.pile.size() - 1; i >= 0; i--) {
			if (cardtemp != tableautemp.pile.get(i)) {
				foundation.pile.remove(i);
			} else {
				break;
			}
		}
		tableautemp.pile.get(tableautemp.pile.size() - 1).setImageToNull();
		foundation.pile.add(tableautemp.pile.remove(tableautemp.pile.size() - 1));
		foundation.pile.get(foundation.pile.size() - 1).faceUp();
		foundationfield.getChildren().add(foundation.pile.get(foundation.pile.size() - 1).cardImage());
		foundation.pile.get(foundation.pile.size() - 1).translateBy(10 + 110 * foundationindex ,60);
		
	}
	/**
	 * card moved from waste to foundation
	 * @param foundation
	 */
	private void cardFromWasteToFoundation(Foundation foundation) {
		model.game.waste.get(model.game.waste.size() - 1).setImageToNull();
		foundation.pile.add(model.game.waste.remove(model.game.waste.size() - 1));
		foundation.pile.get(foundation.pile.size() - 1).faceUp();
		foundationfield.getChildren().add(foundation.pile.get(foundation.pile.size() - 1).cardImage());
		foundation.pile.get(foundation.pile.size() - 1).translateBy(10 + 110 * foundationindex ,60);
		
		if (model.game.waste.size() > 0) {
			model.game.waste.get(model.game.waste.size() - 1).faceUp();
			model.game.waste.get(model.game.waste.size() - 1).setImageView();
		}
		cardtemp = null;
	}
	/**
	 * card moved from tableau to tableau
	 */
	private void cardFromTableauToTableau() {
			
		int beforecardsmovedsize = draggedtotableau.pile.size();
		int tableautempsize = tableautemp.pile.size();
		ArrayList<Card> tableautemplistholder = new ArrayList<Card>();
		for (int i = tableautempsize - 1; i >= cardindex; i--) {
			tableautemp.pile.get(i).setImageToNull();
			tableautemplistholder.add(tableautemp.pile.remove(i));
		}
		Collections.reverse(tableautemplistholder);
		draggedtotableau.pile.addAll(tableautemplistholder);
		
		for (int i = beforecardsmovedsize; i < draggedtotableau.pile.size(); i++) {
			tableaufield.getChildren().add(draggedtotableau.pile.get(i).cardImage());
			draggedtotableau.pile.get(i).translateBy(10 + tableauindex * 110, 
					15 + (i) * 20);
		}
	}
	/**
	 * card moved from waste to tableau
	 */
	private void cardFromWasteToTableau() {
		draggedtotableau.pile.add(model.game.waste.remove(model.game.waste.size() - 1));
		draggedtotableau.pile.get(draggedtotableau.pile.size() - 1).setImageToNull();
		tableaufield.getChildren().add(draggedtotableau.pile.get(draggedtotableau.pile.size() - 1).cardImage());
		draggedtotableau.pile.get(draggedtotableau.pile.size() - 1).translateBy(10 + tableauindex * 110, 
				15 + (draggedtotableau.pile.size() - 1) * 20);
		
		if (model.game.waste.size() > 0) {
			model.game.waste.get(model.game.waste.size() - 1).faceUp();
			model.game.waste.get(model.game.waste.size() - 1).setImageView();
		}
		cardtemp = null;
	}
	/**
	 * loads the fxml file
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Klondike.fxml"));
		field = (Pane)loader.load();
		
		Scene scene = new Scene(field);
		primaryStage.setScene(scene);
		primaryStage.show();
			
	}
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
}

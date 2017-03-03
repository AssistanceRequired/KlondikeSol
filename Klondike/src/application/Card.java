package application;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Card {
	
	private Rank rank;
	private Suit suit;
	private boolean face;
	private ImageView cardimage;
	
	Card(Rank rank, Suit suit) {
		
		this.rank = rank;
		this.suit = suit;
		face = false;
	}
	/**
	 * sets the rank of the card if change is needed
	 * @param rank
	 */
	public void setRank(Rank rank) {
		
		this.rank = rank;
	}
	/**
	 * sets the suit of the card if change is needed
	 * @param suit
	 */
	public void setSuit(Suit suit) {
		
		this.suit = suit;
	}
	/**
	 * gets the rank of the card
	 * @return rank
	 */
	public Rank getRank() {
		
		return rank;
	}
	/**
	 * gets suit of card
	 * @return the enum suit
	 */
	public Suit getSuit() {
		
		return suit;
	}
	/**
	 * gets the face of the card
	 * @return true for the face is up and false for the face is down
	 */
	public boolean getFace() {
		
		return face;
	}
	/**
	 * turns up the face of the card
	 */
	public void faceUp() {
		
		face = true;
	}
	/**
	 * turns down the face of the card
	 */
	public void faceDown() {
		
		face = false;
	}
	
	public String toString() {
		
		return "Rank: " + rank + " and suit: " + suit;
	}
	/**
	 * gets the imageview of the card
	 * @return imageview of card
	 */
	public ImageView getCardImage() {
		return cardimage;
	}
	
	public void translateBy(double x, double y) {
		cardimage.setLayoutX(x);
		cardimage.setLayoutY(y);
	}
	/**
	 * instantiates an image for this card
	 * @return the first created imageview for this card
	 */
	public ImageView cardImage() {
		
		if(face == true) {
			cardimage = new ImageView(new Image("Cards/" + rank.name() + suit.name() + ".gif"));
		} else {
			cardimage = new ImageView(new Image("Cards/BACK.gif"));
		}
		cardimage.setFitHeight(120);
		cardimage.setFitWidth(100);
		return cardimage;
		
	}
	/**
	 * sets the imageview to the current face 
	 */
	public void setImageView() {
		
		if(face == true) {
			cardimage.setImage(new Image(("Cards/" + rank.name() + suit.name() + ".gif")));
		} else {
			cardimage.setImage(new Image("Cards/BACK.gif"));
		}
	}
	/**
	 * sets the image to null so image can be reset for hierarchy
	 */
	public void setImageToNull() {
		cardimage.setImage(null);
	}
	/**
	 * drag the image when this method is called
	 */
	public void mouseDragged() {
		
		cardimage.setOnDragDetected(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent e) {
				 Dragboard dragboard = cardimage.startDragAndDrop(TransferMode.ANY);
				 ClipboardContent content = new ClipboardContent();
				 content.putImage(cardimage.getImage());
				 dragboard.setContent(content);
				 e.consume();
			}
		});
	}

}

package application;

public enum Suit {
	
	DIAMONDS("RED"), CLUBS("BLACK"),
	HEARTS("RED"), SPADES("BLACK");
	
	private String color;
	
	Suit(String color) {
		
		this.color = color;
	}
	/**
	 * the color of the suit
	 * @return the color of the suit as a string
	 */
	public String getColor() {
		
		return color;
	}
	
}

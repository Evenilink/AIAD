package utils;

public class Coordinates {

	private int x;
	private int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(!(other instanceof Coordinates)) return false;
		Coordinates otherCoordinates = (Coordinates) other;
		return (x == otherCoordinates.getX() && y == otherCoordinates.getY());
	}
}

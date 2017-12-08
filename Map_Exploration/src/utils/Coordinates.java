package utils;

import jade.util.leap.Serializable;
import repast.simphony.space.grid.GridPoint;

public class Coordinates implements Serializable {
	public static Coordinates FromGridPoint(GridPoint point) {
		return new Coordinates(point.getX(), point.getY());
	}

	private int x;
	private int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public GridPoint toGridPoint() {
		return new GridPoint(x, y);
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

	@Override
	public String toString() {
		return "Coordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	public boolean equals(Coordinates c) {
		return c.getX() == getX() && c.getY() == getY();
	}
}

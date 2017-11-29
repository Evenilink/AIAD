package entities;

import utils.Coordinates;

public class Entity {

	private int code;
	private Coordinates coordinates;

	public Entity(int posX, int posY, int code) {
		coordinates = new Coordinates(posX, posY);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinates newCoordinates) {
		coordinates = newCoordinates;
	}
}

package entities;

import utils.Coordinates;

public class Entity {

	private Coordinates coordinates;
	
	public Entity(int posX, int posY) {
		coordinates = new Coordinates(posX, posY);
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinates newCoordinates) {
		coordinates = newCoordinates;
	}
}

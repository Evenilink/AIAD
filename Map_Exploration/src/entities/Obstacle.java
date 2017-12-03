package entities;

public class Obstacle extends Entity {
	private final boolean seeThrough;

	public Obstacle(int posX, int posY, boolean seeThrough) {
		super(posX, posY, 3);
		this.seeThrough = seeThrough;
	}

	public boolean isSeeThrough() {
		return seeThrough;
	}
}

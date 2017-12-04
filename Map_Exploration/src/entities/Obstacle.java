package entities;

public class Obstacle extends Entity {
	
	private final boolean seeThrough;
	private final int neededAgentsForRemoving;

	public Obstacle(int posX, int posY) {
		super(posX, posY, 3);
		this.seeThrough = false;
		this.neededAgentsForRemoving = 1;
	}

	public Obstacle(int posX, int posY, int entityCode, boolean seeThrough, int neededAgentsForRemoving) {
		super(posX, posY, entityCode);
		this.seeThrough = seeThrough;
		this.neededAgentsForRemoving = neededAgentsForRemoving;
	}

	public boolean isSeeThrough() {
		return seeThrough;
	}

	public int getNeededAgentsForRemoving() {
		return neededAgentsForRemoving;
	}
}

package entities;

import utils.Utils;

public class Obstacle extends Entity {
	
	private final boolean seeThrough;
	private final int neededAgentsForRemoving;

	public Obstacle(int posX, int posY) {
		super(posX, posY, Utils.CODE_OBSTACLE_CELL);
		this.seeThrough = false;
		this.neededAgentsForRemoving = 99999;
	}

	public Obstacle(int posX, int posY, boolean seeThrough) {
		super(posX, posY, Utils.CODE_OBSTACLE_CELL);
		this.seeThrough = seeThrough;
		this.neededAgentsForRemoving = 99999;
	}
	
	public Obstacle(int posX, int posY, boolean seeThrough, int neededAgentsForRemoving) {
		super(posX, posY, Utils.CODE_OBSTACLE_DOOR);
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

package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.IndividualMessage;
import entities.Obstacle;
import repast.simphony.query.space.grid.GridCell;
import utils.Utils;
import utils.Utils.MessageType;

public class ObstacleGuardian implements IAgentState {
	private Exploration behaviour;
	private int numberAgentsNeeded;
	private int numberAgentsReached;
	private Obstacle obstacle;

	@Override
	public void enter(Exploration behaviour) {
		System.out.println("ENTERED GUARDIAN OBSTACLE");
		this.behaviour = behaviour;
		this.numberAgentsReached = 1;

		// Gets the number of agents needed to destroy the obstacle
		List<GridCell<Object>> cells = behaviour.getNeighborhoodCells();
		for (GridCell<Object> gridCell : cells) {
			Iterator<Object> it = gridCell.items().iterator();

			while (it.hasNext()) {
				Object oneCell = it.next();
				if (oneCell instanceof Obstacle && ((Obstacle) oneCell).getCode() == 3) {
					this.numberAgentsNeeded = ((Obstacle) oneCell).getNeededAgentsForRemoving();
					this.obstacle = ((Obstacle) oneCell);
				}
			}
		}
	}

	@Override
	public void execute() {

		// Each iteration get agents around and asks for help
		communicateAroundMe(MessageType.HELP);

		// When we have enough agents, break wall and search the inside
		if (this.numberAgentsNeeded == this.numberAgentsReached) {
			communicateAroundMe(MessageType.OBSTACLEDOOR_DESTROYED);
			this.behaviour.getAgent().removeObstacleCell(obstacle);
			this.behaviour.changeState(new TravelNearestUndiscovered());
		} else if (this.behaviour.getAgent().getMatrix().getValue(obstacle.getCoordinates().getY(), obstacle.getCoordinates().getX()) != Utils.CODE_OBSTACLE_DOOR) {
			// Another agent has broken the wall
			communicateAroundMe(MessageType.OBSTACLEDOOR_DESTROYED);
			this.behaviour.changeState(new TravelNearestUndiscovered());
		}
	}

	public void communicateAroundMe(MessageType msgType) {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorers();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(msgType, obstacle, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
				if (msgType == MessageType.HELP)
					numberAgentsReached++;
			}
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

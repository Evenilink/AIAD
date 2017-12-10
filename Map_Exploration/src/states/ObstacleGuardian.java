package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.IndividualMessage;
import entities.Obstacle;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import repast.simphony.query.space.grid.GridCell;
import utils.Coordinates;
import utils.Matrix;
import utils.Utils;
import utils.Utils.MessageType;

public class ObstacleGuardian implements IAgentState {
	
	private Exploration behaviour;
	private int numberAgentsNeeded;
	private Obstacle obstacle;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;

		// Gets the number of agents needed to destroy the obstacle
		List<GridCell<Object>> cells = behaviour.getNeighborhoodCells();
		for (GridCell<Object> gridCell : cells) {
			Iterator<Object> it = gridCell.items().iterator();

			while (it.hasNext()) {
				Object oneCell = it.next();
				if (oneCell instanceof Obstacle && ((Obstacle) oneCell).getCode() == Utils.CODE_OBSTACLE_DOOR) {
					this.numberAgentsNeeded = ((Obstacle) oneCell).getNeededAgentsForRemoving();
					this.obstacle = ((Obstacle) oneCell);
					break;
				}
			}
		}
		
		// This can happens when an agent is travelling to an obstacle and before
		// reaching it, the obstacle is already destroyed.
		if(obstacle == null) {
			behaviour.getAStar().setNodeWalkable(new Coordinates(2, 4), true);
			behaviour.changeState(new TravelNearestUndiscovered());
		}

		// AGENTS THAT SPAWN INSIDE OBSTACLE MAY GET STUCK, CHECK LATER
	}

	@Override
	public void execute() {

		// Each iteration get agents around and asks for help
		communicateAroundMe(MessageType.HELP, null);

		// When we have enough agents, break wall and search the inside
		if (getNumberAgentsAroundMe() >= numberAgentsNeeded) {
			// System.out.println("Entrou if 1");
			communicateAroundMe(MessageType.OBSTACLEDOOR_DESTROYED, obstacle.getCoordinates());
			this.behaviour.getAgent().removeObstacleCell(obstacle, behaviour);
			Coordinates matrixCoordinates = Utils.matrixFromWorldPoint(obstacle.getCoordinates(), behaviour.getAgent().getGrid().getDimensions().getHeight());
			// behaviour.getAgent().getMatrix().setValue(matrixCoordinates.getY(), matrixCoordinates.getX(), Utils.CODE_UNDISCOVERED);
			behaviour.getAStar().setNodeWalkable(obstacle.getCoordinates(), true);
			behaviour.getAgent().getMatrix().updateMatrix(behaviour, behaviour.getAgent().getGrid(), obstacle.getCoordinates(), behaviour.getAgent().getRadious());
			behaviour.getPledge().addVisitedCoordinates(obstacle.getCoordinates());
			// this.behaviour.getAgent().moveAgent(obstacle.getCoordinates());
			System.err.println("Obstacle guardian changed to TravelNearestUndiscovered.");
			this.behaviour.changeState(new TravelNearestUndiscovered());
		}
	}

	private int getNumberAgentsAroundMe() {
		int count = 0;
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				it.next();
				count++;
			}
		}
		System.out.println("Count = " + count);
		return count;
	}

	public void communicateAroundMe(MessageType msgType, Object content) {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(msgType, content, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

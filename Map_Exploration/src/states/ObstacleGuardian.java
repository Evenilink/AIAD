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
import utils.Matrix;
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
				if (oneCell instanceof Obstacle && ((Obstacle) oneCell).getCode() == Utils.CODE_OBSTACLE_DOOR) {
					this.numberAgentsNeeded = ((Obstacle) oneCell).getNeededAgentsForRemoving();
					this.obstacle = ((Obstacle) oneCell);
					break;
				}
			}
		}
		// AGENTS THAT SPAWN INSIDE OBSTACLE MAY GET STUCK, CHECK LATER
	}

	@Override
	public void execute() {

		// Each iteration get agents around and asks for help
		communicateAroundMe(MessageType.HELP);

		// Tries to receive WAITING_TO_BREAK messages
		getNumberAgentsAroundMe();
		System.out.println("Agents reached: " + this.numberAgentsReached);

		// When we have enough agents, break wall and search the inside
		if (this.numberAgentsNeeded == this.numberAgentsReached) {
			// System.out.println("Entrou if 1");
			communicateAroundMe(MessageType.OBSTACLEDOOR_DESTROYED);
			this.behaviour.getAgent().removeObstacleCell(obstacle, behaviour);
			this.behaviour.getAgent().getMatrix().printMatrix();
			this.behaviour.getAgent().moveAgent(obstacle.getCoordinates());
			this.behaviour.changeState(new TravelNearestUndiscovered());
		}
	}

	private void getNumberAgentsAroundMe() {
		int count = 0;
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				it.next();
				count++;
			}
		}
		this.numberAgentsReached = count;
	}

	public void communicateAroundMe(MessageType msgType) {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(msgType, null, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

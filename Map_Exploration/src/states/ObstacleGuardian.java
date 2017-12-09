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
		//AGENTS THAAT SPAWN INSIDE OBSTACLE MAY GET STUCK, CHECK LATER
	}

	@Override
	public void execute() {

		// Each iteration get agents around and asks for help
		communicateAroundMe(MessageType.HELP);

		//Tries to receive WAITING_TO_BREAK messages
		//receiveMessagesHandler();
		 getNumberAgentsAroundMe();
		 System.out.println(this.numberAgentsReached);
		
		// When we have enough agents, break wall and search the inside
		if (this.numberAgentsNeeded == this.numberAgentsReached) {
			System.out.println("Entrou if 1");
			communicateAroundMe(MessageType.OBSTACLEDOOR_DESTROYED);
			this.behaviour.getAgent().removeObstacleCell(obstacle);
			this.behaviour.changeState(new TravelNearestUndiscovered());
		}
	}

	private void getNumberAgentsAroundMe()
	{
		int count = 1;
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				count++;
			}
		}
		this.numberAgentsReached = count;
	}
	
	/*private void receiveMessagesHandler() {
		ACLMessage acl;
		while ((acl = behaviour.getAgent().receiveMessage()) != null) {
			try {
				Object obj = acl.getContentObject();
				if (obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) obj;
					switch (message.getMessageType()) {
					case MATRIX:
						Matrix otherMatrix = (Matrix) message.getContent();
						behaviour.getAgent().getMatrix().mergeMatrix(otherMatrix, behaviour);
						break;
					case HELP:
						behaviour.changeState(new WaitingForObstacleDestroy());
						break;
					case OBSTACLEDOOR_DESTROYED:
						behaviour.changeState(new TravelNearestUndiscovered());
						break;
					case WAITING_TO_BREAK:
						System.out.println("one more agent");
						this.numberAgentsReached++;
						break;
					case OTHER_GUARDING:
						boolean isToExit = (boolean) message.getContent();
						if (isToExit)
							behaviour.getAgent().exitFromSimulation();
						else {
							System.out.println(behaviour.getAgent().getLocalName() + " keeps recuiting.");
							behaviour.changeState(new Recruiting());
						}
						break;
					default:
						break;
					}
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}*/

	public void communicateAroundMe(MessageType msgType) {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorersCommunicationLimit();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(msgType, 2, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

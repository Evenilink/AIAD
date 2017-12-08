package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.*;
import entities.Obstacle;
import jade.lang.acl.ACLMessage;
import repast.simphony.query.space.grid.GridCell;
import sajas.core.AID;
import utils.Coordinates;
import utils.Utils;
import utils.Utils.MessageType;

public class ObstacleGuardian implements IAgentState {
	private Exploration behaviour;
	private int numberAgentsNeeded;
	private int numberAgentsReached;
	private Coordinates obstacleCoords;
	
	@Override
	public void enter(Exploration behaviour) {
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
					this.obstacleCoords = ((Obstacle) oneCell).getCoordinates();
				}
			}
		}
		
	}

	@Override
	public void execute() {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorers();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while (it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(MessageType.HELP, behaviour.getAgentCoordinates(), otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
				numberAgentsReached++;
			}
		}

		if (this.numberAgentsNeeded == this.numberAgentsReached) {
			
		}

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

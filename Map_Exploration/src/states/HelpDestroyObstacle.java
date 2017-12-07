package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.*;
import jade.lang.acl.ACLMessage;
import repast.simphony.query.space.grid.GridCell;
import sajas.core.AID;
import utils.Utils;
import utils.Utils.MessageType;

public class HelpDestroyObstacle implements IAgentState {
	private Exploration behaviour;
	private int numberAgentsNeeded;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		// Get minimum number of agents to destroy obstacle
		// Check if around me there's that number of agents, that can help
		// if not, go to a random position on the map and recruit
		// Come back, destroy, and search the obstacle
	}

	@Override
	public void execute() {
		List<GridCell<Explorer>> explorers = behaviour.getNeighborhoodCellsWithExplorers();
		for (GridCell<Explorer> gridCell : explorers) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while(it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(MessageType.HELP, behaviour.getAgentCoordinates(), otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		}
		
		// TODO Auto-generated method stub
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}

package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.IndividualMessage;
import repast.simphony.query.space.grid.GridCell;
import utils.Utils.MessageType;

public class Guarding implements IAgentState {

	private Exploration behaviour;
	private int numAgentOut;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		numAgentOut = 0;
	}

	@Override
	public void execute() {
		List<GridCell<Explorer>> neighborhoodExplorersCells = behaviour.getNeighborhoodCellsWithExplorers();
		for (GridCell<Explorer> gridCell : neighborhoodExplorersCells) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while(it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(MessageType.OTHER_GUARDING, numAgentOut, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		}
	}

	@Override
	public void exit() {
		
	}
}

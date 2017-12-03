package behaviours;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.AStar;
import algorithms.dfs.DFS;
import algorithms.pledge.Pledge;
import communication.IndividualMessage;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.GridPoint;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.AgentType;
import utils.Utils.Algorithm;
import utils.Utils.MessageType;

public class Exploration extends CyclicBehaviour {

	private Explorer agent;
	private Algorithm state;
		
	private DFS dfs;
	private AStar astar;
	private Pledge pledge;
	
	public Exploration(Explorer agent) {
		super(agent);
		this.agent = agent;
		state = Algorithm.DFS;
		dfs = new DFS(agent, this);
		astar = new AStar(agent, this);
		pledge = new Pledge(agent);
	}

	@Override
	public void action() {
		GridPoint pt = agent.getGrid().getLocation(agent);
		GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(agent.getGrid(), pt, Object.class, agent.getRadious(), agent.getRadious());
		List<GridCell<Object>> neighborhoodCells = nghCreator.getNeighborhood(false);
		
		switch(state) {
			case DFS:
				dfs.run(neighborhoodCells);
				break;
			case A_STAR:
				astar.run();
				break;
			case PLEDGE:
				pledge.run();
				break;
			default: break;
		}
		
		sendMessagesHandler(neighborhoodCells);
	}
	
	/**
	 * Searches for other explorers in the neighborhood and sends them his matrix.
	 * @param neighborhoodCells
	 */
	private void sendMessagesHandler(List<GridCell<Object>> neighborhoodCells) {
		for (GridCell<Object> gridCell : neighborhoodCells) {
            Iterator<Object> it = gridCell.items().iterator();
            while(it.hasNext()) {
            	Object obj = it.next();
            	if(obj instanceof Explorer) {
            		Explorer otherExplorer = (Explorer) obj;
            		sendMessageToNeighbor(otherExplorer);
            	}
            }
		}
	}
	
	private void sendMessageToNeighbor(Explorer otherExplorer) {
		// Super agents don't need to exchange matrix messages when they're close to each other.
		if(agent.getAgentType() == AgentType.SUPER_AGENT && otherExplorer.getAgentType() == AgentType.SUPER_AGENT)
			return;
		
		IndividualMessage message = new IndividualMessage(MessageType.MATRIX, agent.getMatrix(), otherExplorer.getAID());
		agent.sendMessage(message);
	}
	
	public void changeState(Algorithm newState) {
		switch(newState) {
			case A_STAR:
				astar.init();
				break;
			case PLEDGE:
				pledge.init();
				break;
			default: break;
		}
		state = newState;
	}
}

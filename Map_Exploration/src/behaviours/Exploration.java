package behaviours;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.AStar;
import algorithms.dfs.DFS;
import algorithms.pledge.Pledge;
import communication.IndividualMessage;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.GridPoint;
import sajas.core.behaviours.CyclicBehaviour;
import states.Explore;
import states.IAgentState;
import states.Recruiting;
import utils.Coordinates;
import utils.Matrix;
import utils.Utils.AgentType;
import utils.Utils.MessageType;

public class Exploration extends CyclicBehaviour {
	private static final long serialVersionUID = 7526472295622776147L;  // unique id

	private Explorer agent;
	private DFS dfs;
	private AStar astar;
	private Pledge pledge;
	private IAgentState currState;
	
	public Exploration(Explorer agent) {
		super(agent);
		this.agent = agent;
		dfs = new DFS(agent);
		astar = new AStar(agent);
		pledge = new Pledge(agent);
		changeState(new Explore());
	}
	
	@Override
	public void action() {
		if (currState != null) currState.execute();
		receiveMessagesHandler();
		sendMessagesHandler(getNeighborhoodCells());
	}
	
	private void receiveMessagesHandler() {
		ACLMessage acl;
		while((acl = agent.receiveMessage()) != null) {
			try {
				Object obj = acl.getContentObject();
				if(obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) obj;
					switch(message.getMessageType()) {
						case MATRIX:
							Matrix otherMatrix = (Matrix) message.getContent();
							agent.getMatrix().mergeMatrix(otherMatrix);
							break;
						case OTHER_GUARDING:
							int numAgentsOut = (int) message.getContent();
							if(numAgentsOut < 1)
								changeState(new Recruiting());
							break;
						case HELP:
							//TODO not implemented yet
							break;
					}
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changeState(IAgentState newState) {
		if(currState != null)
			currState.exit();
		currState = newState;
		currState.enter(this);
	}
	
	public GridPoint getAgentPoint() {
		return agent.getGrid().getLocation(agent);
	}
	
	public Coordinates getAgentCoordinates() {
		GridPoint pt = getAgentPoint();
		return new Coordinates(pt.getX(), pt.getY());
	}
	
	public List<GridCell<Object>> getNeighborhoodCells() {
		GridPoint pt = getAgentPoint();
		GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(agent.getGrid(), pt, Object.class, agent.getRadious(), agent.getRadious());
		return nghCreator.getNeighborhood(false);
	}
	
	public List<GridCell<Explorer>> getNeighborhoodCellsWithExplorers() {
		GridPoint pt = getAgentPoint();
		GridCellNgh<Explorer> nghCreator = new GridCellNgh<Explorer>(agent.getGrid(), pt, Explorer.class, agent.getRadious(), agent.getRadious());
		return nghCreator.getNeighborhood(false);
	}
	
	public void moveAgentToCoordinate(Coordinates targetCoordinates) {
		agent.moveAgent(targetCoordinates);
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
	
	public DFS getDFS() {
		return dfs;
	}
	
	public AStar getAStar() {
		return astar;
	}
	
	public Explorer getAgent() {
		return agent;
	}

	public Pledge getPledge() {
		return pledge;
	}
}

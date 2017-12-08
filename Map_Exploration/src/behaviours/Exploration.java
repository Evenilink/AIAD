package behaviours;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.AStar;
import algorithms.dfs.DFS;
import algorithms.pledge.Pledge;
import communication.IndividualMessage;
import entities.UndiscoveredCell;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.grid.GridPoint;
import sajas.core.behaviours.CyclicBehaviour;
import states.Explore;
import states.Guarding;
import states.IAgentState;
import states.IAgentTemporaryState;
import states.Recruiting;
import states.TravelNearestUndiscovered;
import states.WaitingForObstacleDestroy;
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
	private IAgentState pausedState;
	
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
		if (currState != null) {
			if (currState instanceof IAgentTemporaryState && ((IAgentTemporaryState) currState).canResume())
				this.resumeState();
			else
				currState.execute();
		}
		if(agent != null) {
			receiveMessagesHandler();
			List<GridCell<Object>> a = getNeighborhoodCells();
			if(a != null)
				sendMessagesHandler(a);	
		}
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
						case HELP:
							this.changeState(new WaitingForObstacleDestroy());
							break;
						case OBSTACLEDOOR_DESTROYED:
							this.changeState(new TravelNearestUndiscovered());
							break;
						case OTHER_GUARDING:
							boolean isToExit = (boolean) message.getContent();
							if(isToExit)
								agent.exitFromSimulation();
							else {
								System.out.println(agent.getLocalName() + " keeps recuiting.");
								changeState(new Recruiting());
							}
							break;
						default: break;
					}
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void changeState(IAgentState newState) {
		if (newState instanceof IAgentTemporaryState) {
			this.pauseState();
		} else if(currState != null)
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
		System.out.println("Comecando");
		System.out.println(agent);
		System.out.println(agent.getGrid());
		System.out.println(agent.getRadious());
		GridPoint pt = getAgentPoint();
		GridCellNgh<Object> nghCreator = new GridCellNgh<Object>(agent.getGrid(), pt, Object.class, agent.getRadious(), agent.getRadious());
		return nghCreator.getNeighborhood(false);
	}
	
	public List<GridCell<Explorer>> getNeighborhoodCellsWithExplorers() {
		GridPoint pt = getAgentPoint();
		GridCellNgh<Explorer> nghCreator = new GridCellNgh<Explorer>(agent.getGrid(), pt, Explorer.class, agent.getRadious(), agent.getRadious());
		return nghCreator.getNeighborhood(false);
	}
	
	public boolean moveAgentToCoordinate(Coordinates targetCoordinates) {
		return agent.moveAgent(targetCoordinates);
		// TODO: it's possible 2 agents stop moving if they want to go to each other's place.
	}
	
	public void discoverCell(UndiscoveredCell cell) {
	    agent.discoverCell(cell);
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

	private void pauseState() {
		this.pausedState = this.currState;
		this.currState = null;
	}

	private void resumeState() {
		this.currState = this.pausedState;
		this.pausedState = null;
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

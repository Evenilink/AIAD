package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.Node;
import behaviours.Exploration;
import communication.IndividualMessage;
import utils.Coordinates;
import utils.Utils.MessageType;

public class TravelExit implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		path = behaviour.getAStar().getPathToExit();
		if(path == null) System.err.println("The exit should have been found already.");
		pathNode = 0;
	}

	@Override
	public void execute() {
		if(pathNode < path.size())
			if(behaviour.moveAgentToCoordinate(path.get(pathNode).getWorldPosition()))
				pathNode++;	
		
		if(pathNode == path.size()) {
			boolean becameMasterAgent = true;
			System.out.println("tamanho: " + path.size());
			Coordinates target = behaviour.getAgentCoordinates();
			Iterator it = behaviour.getAgent().getGrid().getObjectsAt(target.getX(), target.getY()).iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Explorer) {
					Explorer otherExplorer = (Explorer) obj;
					// Check for Explorer that is not himself.
					if(!behaviour.getAgent().getLocalName().equals(otherExplorer.getLocalName())) {
						IndividualMessage message = new IndividualMessage(MessageType.REACHED_EXIT, behaviour.getAgent().getLocalName(), otherExplorer.getAID());
						behaviour.getAgent().sendMessage(message);
						becameMasterAgent = false;
						break;
					}
				}
			}
			
			if(becameMasterAgent) {
				System.out.println(behaviour.getAgent().getLocalName() + " is guarding the exit.");
				behaviour.changeState(new Guarding());
			}
		}
	}

	@Override
	public void exit() {
		
	}
}

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
	private Coordinates exit;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		exit = behaviour.getAgent().getMatrix().getExit();
	}

	@Override
	public void execute() {
		path = behaviour.getAStar().getPathToExit();		
		
		/*if(path == null) {
			behaviour.changeState(new TravelNearestUndiscovered());
			return;
		}*/
		
		if(path == null || path.size() == 0) {
			boolean becameMasterAgent = true;
			Coordinates target = behaviour.getAgentCoordinates();
			if(!target.equals(exit)) {
				// Couldn't find path to exit
				return;
			}
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
				//System.out.println(behaviour.getAgent().getLocalName() + " is guarding the exit.");
				behaviour.changeState(new Guarding());
			}
		} else behaviour.moveAgentToCoordinate(path.get(0).getWorldPosition());
	}

	@Override
	public void exit() {
		
	}
}

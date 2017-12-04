package states;

import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import algorithms.astar.Node;
import behaviours.Exploration;
import communication.IndividualMessage;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import utils.Coordinates;
import utils.Matrix;
import utils.Utils.MessageType;

public class TravelExit implements IAgentState {

	private Exploration behaviour;
	private List<Node> path;
	private int pathNode;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		path = behaviour.getAStar().getPathToExit();
		pathNode = 0;
		if(path == null) System.err.println("The exit should have been found already.");
	}

	@Override
	public void execute() {
		behaviour.moveAgentToCoordinate(path.get(pathNode).getWorldPosition());
		pathNode++;
		
		if(pathNode == path.size()) {
			/* boolean becameMasterAgent = true;
			Iterator it = behaviour.getAgent().getGrid().getObjectsAt(target.getX(), target.getY()).iterator();
			while(it.hasNext()) {
				Object obj = it.next();
				if(obj instanceof Explorer) {
					IndividualMessage message = new IndividualMessage(MessageType.AUX, null, receiver);
					becameMasterAgent = false;
				}
			} */
			
			// if(becameMasterAgent) {
				System.out.println(behaviour.getAgent().getLocalName() + " is guarding the exit.");
				behaviour.changeState(new Guarding());
			// }
		} else {
			
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
}

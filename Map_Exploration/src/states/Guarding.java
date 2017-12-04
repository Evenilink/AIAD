package states;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import agents.Explorer;
import behaviours.Exploration;
import communication.IndividualMessage;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import repast.simphony.query.space.grid.GridCell;
import utils.Matrix;
import utils.Utils.MessageType;

public class Guarding implements IAgentState {

	private Exploration behaviour;
	private int numAgentOut;
	private int totalNumAgents;
	private List<String> agentsReachedExit;
	
	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		numAgentOut = 0;
		// totalNumAgents = 
		agentsReachedExit = new ArrayList<>();
	}

	@Override
	public void execute() {
		receiveMessagesHandler();
		/*ACLMessage acl = null;
		while((acl = behaviour.getAgent().receiveMessage()) != null) {
			Object obj;
			try {
				obj = acl.getContentObject();
				if(obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) obj;
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}*/
		
		/* List<GridCell<Explorer>> neighborhoodExplorersCells = behaviour.getNeighborhoodCellsWithExplorers();
		for (GridCell<Explorer> gridCell : neighborhoodExplorersCells) {
			Iterator<Explorer> it = gridCell.items().iterator();
			while(it.hasNext()) {
				Explorer otherExplorer = it.next();
				IndividualMessage message = new IndividualMessage(MessageType.OTHER_GUARDING, numAgentOut, otherExplorer.getAID());
				behaviour.getAgent().sendMessage(message);
			}
		} */
	}

	@Override
	public void exit() {
		
	}
	
	private void receiveMessagesHandler() {
		ACLMessage acl = null;
		while((acl = behaviour.getAgent().receiveMessage()) != null) {
			try {
				Object obj = acl.getContentObject();
				if(obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) obj;
					switch(message.getMessageType()) {
						case MATRIX:
							Matrix otherMatrix = (Matrix) message.getContent();
							behaviour.getAgent().getMatrix().mergeMatrix(otherMatrix);
							break;
						case REACHED_EXIT:
							String agentName = (String) message.getContent();
							if(!agentsReachedExit.contains(agentName))
								agentsReachedExit.add(agentName);
							
							if(agentsReachedExit.size() == 1) {
								System.out.println("Eeryone reached exit!");
								message = new IndividualMessage(MessageType.OTHER_GUARDING, true, acl.getSender());
								behaviour.getAgent().sendMessage(message);	
							} else {
								System.out.println("Still lacking agents!");
								message = new IndividualMessage(MessageType.OTHER_GUARDING, false, acl.getSender());
								behaviour.getAgent().sendMessage(message);	
							}
						default: break;
					}
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}
}

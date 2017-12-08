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
import sun.management.resources.agent;
import utils.Matrix;
import utils.Utils.MessageType;

public class Guarding implements IAgentState {

	private Exploration behaviour;
	private int totalNumAgents;
	private boolean everyAgentInstructed;
	private List<String> agentsReachedExit;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		totalNumAgents = behaviour.getAgent().getTotalNumAgents();
		everyAgentInstructed = false;
		agentsReachedExit = new ArrayList<>();
		agentsReachedExit.add(behaviour.getAgent().getLocalName());
	}

	@Override
	public void execute() {
		receiveMessagesHandler();
	}

	@Override
	public void exit() {

	}

	private void receiveMessagesHandler() {
		ACLMessage acl = null;
		while ((acl = behaviour.getAgent().receiveMessage()) != null) {
			try {
				Object obj = acl.getContentObject();
				if (obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) obj;
					switch (message.getMessageType()) {
					case MATRIX:
						Matrix otherMatrix = (Matrix) message.getContent();
						behaviour.getAgent().getMatrix().mergeMatrix(otherMatrix, behaviour);
						break;
					case REACHED_EXIT:
						String agentName = (String) message.getContent();
						if (!agentsReachedExit.contains(agentName)) {
							agentsReachedExit.add(agentName);
							if (agentsReachedExit.size() == totalNumAgents)
								everyAgentInstructed = true;
						}

						if (everyAgentInstructed) {
							System.out.println(behaviour.getAgent().getLocalName() + ": you're free to exit!");
							message = new IndividualMessage(MessageType.OTHER_GUARDING, true, acl.getSender());
							behaviour.getAgent().sendMessage(message);
							totalNumAgents--;
							// If this agent is the only one left.
							if (totalNumAgents == 1) {
								System.out.println(behaviour.getAgent().getLocalName()
										+ ": I'm alone in the simulation, and since I'm the guarding, I will also leave. Bye!");
								behaviour.getAgent().exitFromSimulation();
							}
						} else {
							System.out.println(behaviour.getAgent().getLocalName() + ": go look for more agents!");
							message = new IndividualMessage(MessageType.OTHER_GUARDING, false, acl.getSender());
							behaviour.getAgent().sendMessage(message);
						}
					default:
						break;
					}
				}
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}
}

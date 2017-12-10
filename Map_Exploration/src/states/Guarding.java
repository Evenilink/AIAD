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
	private List<Integer> numberOfTimesReachedExit;
	private static final int MaxNumberOfTimesBeforeBreakingObject = 10;

	@Override
	public void enter(Exploration behaviour) {
		this.behaviour = behaviour;
		totalNumAgents = behaviour.getAgent().getTotalNumAgents();
		if(totalNumAgents == 1) {
			System.out.println(behaviour.getAgent().getLocalName()
					+ ": I'm alone in the simulation, and since I'm the guardian, I will also leave. Bye!");
			behaviour.getAgent().exitFromSimulation();	
		}
		everyAgentInstructed = false;
		agentsReachedExit = new ArrayList<>();
		numberOfTimesReachedExit = new ArrayList<>();
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
							numberOfTimesReachedExit.add(0);
							if (agentsReachedExit.size() == totalNumAgents)
								everyAgentInstructed = true;
						}

						if (everyAgentInstructed) {
							message = new IndividualMessage(MessageType.OTHER_GUARDING, true, acl.getSender());
							behaviour.getAgent().sendMessage(message);
							agentsReachedExit.remove(acl.getSender().getLocalName());
							// If this agent is the only one left.
							if (agentsReachedExit.size() == 1) {
								System.out.println(behaviour.getAgent().getLocalName()
										+ ": I'm alone in the simulation, and since I'm the guardian, I will also leave. Bye!");
								behaviour.getAgent().exitFromSimulation();
							}
						} else {
							System.out.println(behaviour.getAgent().getLocalName() + ": go look for more agents!");
							int agentIdx = agentsReachedExit.indexOf(agentName);
							if (agentsReachedExit.contains(agentName) && numberOfTimesReachedExit.get(agentIdx) >= Guarding.MaxNumberOfTimesBeforeBreakingObject) {
								message = new IndividualMessage(MessageType.OTHER_GUARDING, true, acl.getSender());
								behaviour.getAgent().sendMessage(message);
							} else {
								message = new IndividualMessage(MessageType.OTHER_GUARDING, false, acl.getSender());
								behaviour.getAgent().sendMessage(message);
								numberOfTimesReachedExit.set(agentIdx, numberOfTimesReachedExit.get(agentIdx) + 1);
							}
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

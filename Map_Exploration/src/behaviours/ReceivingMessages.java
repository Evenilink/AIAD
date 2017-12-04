package behaviours;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Matrix;
import utils.Utils.MessageType;

import agents.Explorer;
import communication.GroupMessage;
import communication.IndividualMessage;

public class ReceivingMessages extends CyclicBehaviour {

	private Explorer agent;
	
	public ReceivingMessages(Explorer agent) {
		this.agent = agent;
	}
	
	@Override
	public void action() {
		ACLMessage acl = agent.receiveMessage();
		if(acl != null) {
			AID sender = acl.getSender();
			
			try {
				Object obj = acl.getContentObject();
				if(obj instanceof GroupMessage) {
					GroupMessage message = (GroupMessage) obj;
					// If this agent is not sending messages to this sender, then we'll add it to this agent messages receivers.
					agent.getSendingMessagesBehaviour().getMessage().checkReceiver(sender);
					
					if(message.getMessageType() == MessageType.MATRIX) {
						Matrix otherMatrix = (Matrix) message.getContent();
						agent.getMatrix().mergeMatrix(otherMatrix);
					}
				} else if(obj instanceof IndividualMessage) {
					IndividualMessage message = (IndividualMessage) acl.getContentObject();
					switch(message.getMessageType()) {
						case MATRIX:
							Matrix otherMatrix = (Matrix) message.getContent();
							agent.getMatrix().mergeMatrix(otherMatrix);
							break;
						case HELP:
							break;
						case OTHER_GUARDING:
							break;
						default:
							break;
					}
				} else System.err.println("Unrecognizable message received.");
			} catch (UnreadableException e1) {
				e1.printStackTrace();
			}
		}
	}
}

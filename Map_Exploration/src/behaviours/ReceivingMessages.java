package behaviours;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Matrix;
import utils.Utils.MessageType;

import com.sun.media.ui.Group;

import agents.Explorer;
import communication.GroupMessage;
import communication.IndividualMessage;
import communication.Message;

public class ReceivingMessages extends CyclicBehaviour {

	private Explorer agent;
	private IndividualMessage individualMessage;
	
	public ReceivingMessages(Explorer agent) {
		this.agent = agent;
	}
	
	@Override
	public void action() {
		ACLMessage acl = agent.receiveMessage();
		if(acl != null) {
			AID sender = acl.getSender();
			
			// If this agent is not sending messages to this sender, then we'll add it to this agent messages receivers.
			agent.getSendingMessagesBehaviour().getMessage().checkReceiver(sender);
			
			GroupMessage message = null;
			try {
				message = (GroupMessage) acl.getContentObject();
				if(message.getMessageType() == MessageType.MATRIX) {
					Matrix otherMatrix = (Matrix) message.getContent();
					agent.getMatrix().mergeMatrix(otherMatrix);
				}
				// System.out.println(agent.getLocalName() + " received group message of type '" + message.getMessageType() + "'");
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
		}
	}
}

package behaviours;

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

public class Messaging extends CyclicBehaviour {

	private Explorer agent;
	private IndividualMessage individualMessage;
	
	public Messaging(Explorer agent) {
		this.agent = agent;
	}
	
	@Override
	public void action() {
		ACLMessage acl = agent.receiveMessage();
		if(acl != null) {
				String message = acl.getContent();
				System.out.println(agent.getLocalName() + " received GroupMessage: " + message);
		}
	}
}

package communication;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import utils.Utils.MessageType;

public class Message extends Agent {
	
	private String str;
	private Object obj;
	private AID destination;	// Agent identifier;
	private MessageType messageType;
	
	public Message(MessageType messageType, Object obj, AID destination) {
		this.messageType = messageType;
		this.obj = obj;
		this.destination = destination;
	}
	
	public Message(MessageType messageType, String str, AID destination) {
		this.messageType = messageType;
		this.str = str;
		this.destination = destination;
	}
	
	public Message() {
		
	}
	
	public void sendMessage() throws IOException {
		if (obj != null) {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.setContentObject((Serializable) this.obj);
		    msg.addReceiver(destination);
		    send(msg);
	    } else {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.setContentObject((Serializable) this.str);
		    msg.addReceiver(destination);
		    send(msg);
		}
	}
	
	public Object receiveMessage() throws UnreadableException {
		ACLMessage msg = blockingReceive();
		Object obj = msg.getContentObject();
		return obj;
	}
}

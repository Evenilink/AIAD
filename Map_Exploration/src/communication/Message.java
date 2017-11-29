package communication;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
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
	
	/**
	 * Constructor used for messages that are always being received.
	 */
	public Message() {
		
	}
	
	/**
	 * Constructor for messages sent by Super Agents that are constantly
	 * sharing location information.
	 * @param messageType
	 * @param str
	 * @param receivers
	 * @throws IOException 
	 */
	public Message(MessageType messageType, Object obj, DFAgentDescription[] receivers) throws IOException {
		this.messageType = messageType;
		this.obj = obj;
		
		msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContentObject((Serializable) this.obj);
		for (int i = 0; i < receivers.length; i++)
			msg.addReceiver(receivers[i].getName());
	}
	
	private ACLMessage msg;
	
	public void sendMessageToMany() {
		send(msg);
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
		ACLMessage msg = receive();
		if(msg != null)
			return msg.getContentObject();
		else return null;
	}
	
	/*******************************/
	/***** Getters and setters *****/
	/*******************************/
	
	public MessageType getMessageType() {
		return messageType;
	}
	
	public Object getObject() {
		return obj;
	}
}

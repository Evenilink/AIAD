package communication;

import java.io.IOException;
import java.io.Serializable;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;

public class transport extends Agent
{
	private String str;
	private Object obj;
	private AID destination;
	
	public transport(Object obj, AID destination)
	{
		this.obj = obj;
		this.destination = destination;
	}
	
	public transport(String str, AID destination)
	{
		this.str = str;
		this.destination = destination;
	}
	
	public void sendMessage() throws IOException
	{
		if (str == null){
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.setContentObject((Serializable) this.obj);
		    msg.addReceiver(destination);
		    send(msg);
	    }
		else {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.setContentObject((Serializable) this.str);
		    msg.addReceiver(destination);
		    send(msg);
		}
	}
	
	public Object receiveMessage() throws UnreadableException
	{
		ACLMessage msg = receive();
		if (msg != null) {
			Object obj = msg.getContentObject();
		} else {
			blockingReceive();
		}
		return obj;
	}
}

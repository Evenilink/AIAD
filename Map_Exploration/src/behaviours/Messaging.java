package behaviours;

import jade.lang.acl.UnreadableException;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Matrix;
import utils.Utils.MessageType;
import agents.Explorer;
import communication.Message;

public class Messaging extends CyclicBehaviour {

	private Explorer agent;
	private Message msgReceive;
	
	public Messaging(Explorer agent) {
		this.agent = agent;
		msgReceive = new Message();
	}

	@Override
	public void action() {
		try {
			Object obj = msgReceive.receiveMessage();
			
			if(obj instanceof Message) {
				Message msg = (Message) obj;
				MessageType msgType = msg.getMessageType();
				
				switch(msgType) {
					case MATRIX:
						obj = msg.getObject();
						if(obj instanceof Matrix) {
							Matrix otherMatrix = (Matrix) msg.getObject();
							agent.getMatrix().mergeMatrix(otherMatrix);
						}
						break;
					case HELP:
						break;
				}
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
}

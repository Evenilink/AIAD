package behaviours;

import jade.lang.acl.UnreadableException;
import sajas.core.behaviours.CyclicBehaviour;
import communication.Message;

public class Messaging extends CyclicBehaviour{

	private Message msgReceive;
	
	public Messaging() {
		msgReceive = new Message();
	}

	@Override
	public void action() {
		try {
			msgReceive.receiveMessage();
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
}

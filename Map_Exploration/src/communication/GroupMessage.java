package communication;

import java.util.ArrayList;
import java.util.List;


import jade.core.AID;
import utils.Utils.MessageType;

public class GroupMessage extends Message {

	private List<AID> receivers;
	
	public GroupMessage(MessageType messageType, Object content, List<AID> receivers) {
		super(messageType, content);
		this.receivers = new ArrayList<>();
		for(int i = 0; i < receivers.size(); i++)
			this.receivers.add(receivers.get(i));
	}

	/**
	 * Checks if this message has the sender as one of the receivers of this message.
	 * If not, adds it.
	 * @param sender
	 */
	public void checkReceiver(AID sender) {
		if(!receivers.contains(sender))
			receivers.add(sender);
	}
	
	/*******************************/
	/***** Getters and setters *****/
	/*******************************/
	
	public List<AID> getReceivers() {
		return receivers;
	}
}

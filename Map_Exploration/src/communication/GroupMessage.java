package communication;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import utils.Utils.MessageType;

public class GroupMessage extends Message {

	private List<AID> receivers;
	
	public GroupMessage(MessageType messageType, Object content, DFAgentDescription[] receivers) {
		super(messageType, content);
		
		this.receivers = new ArrayList<>();
		for(int i = 0; i < receivers.length; i++)
			this.receivers.add(receivers[i].getName());
	}

	public List<AID> getReceivers() {
		return receivers;
	}
}

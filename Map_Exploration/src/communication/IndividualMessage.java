package communication;

import jade.core.AID;
import utils.Utils.MessageType;

public class IndividualMessage extends Message {

	private AID receiver;
	
	public IndividualMessage(MessageType messageType, Object content, AID receiver) {
		super(messageType, content);
		this.receiver = receiver;
	}
	
	public AID getReceiver() {
		return receiver;
	}
}

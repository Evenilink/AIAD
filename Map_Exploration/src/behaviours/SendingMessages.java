package behaviours;

import java.util.List;

import agents.Explorer;
import communication.GroupMessage;
import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.MessageType;

public class SendingMessages extends CyclicBehaviour {

	private Explorer agent;
	private GroupMessage message;
	
	public SendingMessages(Explorer agent, List<AID> receivers) {
		this.agent = agent;
		message = new GroupMessage(MessageType.MATRIX, agent.getMatrix(), receivers);			
	}

	@Override
	public void action() {
		// System.out.println(agent.getLocalName() + " sent a message of type '" + message.getMessageType() + "' to " + message.getReceivers().size() + " agents.");
		agent.sendMessage(message);
		message.setContent(agent.getMatrix());
	}
	
	public GroupMessage getMessage() {
		return message;
	}
}

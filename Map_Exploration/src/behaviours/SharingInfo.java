package behaviours;

import agents.Explorer;
import communication.GroupMessage;
import communication.Message;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.MessageType;

public class SharingInfo extends CyclicBehaviour {

	private Explorer agent;
	private GroupMessage groupMessage;
	
	public SharingInfo(Explorer agent, DFAgentDescription[] receivers) {
		this.agent = agent;
		groupMessage = new GroupMessage(MessageType.MATRIX, agent.getMatrix(), receivers);			
	}

	@Override
	public void action() {
		agent.sendMessage(groupMessage);
		groupMessage.setContent(agent.getMatrix());
	}
}

package behaviours;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import agents.Explorer;
import communication.Message;
import jade.core.AID;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.MessageType;

public class SharingInfo extends CyclicBehaviour {

	private Explorer agent;
	private Message msgSend;
	
	public SharingInfo(Explorer explorer, DFAgentDescription[] receivers) {
		this.agent = explorer;
		try {
			Message msgSend = new Message(MessageType.MATRIX, agent.getMatrix(), receivers);
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}

	@Override
	public void action() {
		msgSend.sendMessageToMany();
	}
}

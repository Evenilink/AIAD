package behaviours;

import jade.lang.acl.UnreadableException;
import sajas.core.behaviours.CyclicBehaviour;
import utils.Utils.MessageType;
import agents.Explorer;
import communication.Message;

public class Messaging extends CyclicBehaviour{

	private Explorer agent;
	private Message msgReceive;
	
	public Messaging(Explorer agent) {
		this.agent = agent;
		msgReceive = new Message();
	}

	@Override
	public void action() {
		try {
			Object msgObj = msgReceive.receiveMessage();
			if(msgObj instanceof Message) {
				Message msg = (Message) msgObj;
				MessageType msgType = msg.getMessageType();
				switch(msgType) {
					case MATRIX:
						(Matrix) otherMatrix = msg.getObject();
						mergeMatrix(otherMatrix);
						break;
					case HELP:
						break;
					case default:
						break;
				}
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Merges the matrix of the receiving agent with the matrix received
	 * from other agents inside the communication radius.
	 * @param receivedMatrix
	 */
	private void mergeMatrix(int[][] receivedMatrix) {
		for (int row = 0; row < receivedMatrix.length; row++) {
			for (int column = 0; column < receivedMatrix[row].length; column++) {
				if (receivedMatrix[row][column] != 0 && agent.getMatrixValue(column, row) == 0)
					agent.setMatrixValue(column, row, receivedMatrix[row][column]);
			}
		}
	}
}

package communication;

import java.io.Serializable;

import utils.Utils.MessageType;

public abstract class Message implements Serializable {
	
	private Object content;
	private MessageType messageType;
	
	public Message(MessageType messageType, Object content) {
		this.messageType = messageType;
		this.content = content;
	}
	
	/*******************************/
	/***** Getters and setters *****/
	/*******************************/
	
	public MessageType getMessageType() {
		return messageType;
	}
	
	public Object getContent() {
		return content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}
}

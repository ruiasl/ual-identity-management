package pt.ual.mgi.manager.messaging;

import pt.ual.mgi.detail.messaging.BaseMessage;
import pt.ual.mgi.exception.MgiException;

/**
 * Interface that represents the signatures of the Messaging Manager
 * 
 * @author 20070337
 * @version 1.0
 */
public interface IMessagingManager {

	/**
	 * Send a Message with/without template
	 * 
	 * @param message
	 * @param applyTemplate
	 * @throws MgiException
	 */
	public void sendMessage(BaseMessage message, boolean applyTemplate) throws MgiException;
}
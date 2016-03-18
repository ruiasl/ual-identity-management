/**
 * 
 */
package pt.ual.mgi.detail.messaging;

/**
 * Class that represents a simple message to be sent
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see BaseMessage
 */
public class SimpleMessage extends BaseMessage {
	
	/**
	 * Constructor
	 * 
	 * @param toAddress
	 * @param messageType
	 * @param subject
	 * @param body
	 * @param bodyType
	 * @param messageModule
	 */
	public SimpleMessage(String toAddress, String messageType, 
			String subject, String body, String bodyType, String messageModule) {
		super(toAddress, messageType, subject, body, bodyType, messageModule);
	}
	
	/* (non-Javadoc)
	 * @see pt.dsts.viactt.detail.messaging.BaseMessage#buildParameters()
	 */
	@Override
	public void buildParameters() {}
}
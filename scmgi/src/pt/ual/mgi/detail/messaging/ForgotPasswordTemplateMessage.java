/**
 * 
 */
package pt.ual.mgi.detail.messaging;

import pt.ual.mgi.common.Constants;

/**
 * Class that represents the template message for forgot password process
 * 
 * @author 20070337
 * @version 1.0
 */
public class ForgotPasswordTemplateMessage extends TemplateBaseMessage {

	private String hintAnswer; 
	private static String SUBJECT = "Recuperacao da Password";
	private static String MESSAGE_MODULE = "ForgotPassword";
	
	/**
	 * Constructor
	 * 
	 * @param messageAddress
	 * @param hintAnswer
	 */
	public ForgotPasswordTemplateMessage(String messageAddress, String hintAnswer) {
		super(Constants.MESSAGE_TYPE_EMAIL, SUBJECT, 
				Constants.MESSAGE_BODY_TYPE_HTML, MESSAGE_MODULE);
		this.hintAnswer = hintAnswer;
		this.messageAddress = messageAddress;
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.detail.messaging.BaseMessage#buildParameters()
	 */
	@Override
	public void buildParameters() {
		StringBuilder builder = new StringBuilder("<hintAnswer>");
		builder.append(this.hintAnswer);
		builder.append("</hintAnswer>");
		this.setParameters(builder.toString());
	}
}

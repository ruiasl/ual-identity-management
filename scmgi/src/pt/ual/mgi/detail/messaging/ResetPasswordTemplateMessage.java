/**
 * 
 */
package pt.ual.mgi.detail.messaging;

import pt.ual.mgi.common.Constants;

/**
 * Class that represents the template message for reset password
 * 
 * @author 20070337
 * @version 1.0
 */
public class ResetPasswordTemplateMessage extends TemplateBaseMessage {

	private String password; 
	private static String SUBJECT = "Reset da Password";
	private static String MESSAGE_MODULE = "ResetPassword";
	
	/**
	 * Constructor
	 * 
	 * @param messageAddress
	 * @param password
	 */
	public ResetPasswordTemplateMessage(String messageAddress, String password) {
		super(Constants.MESSAGE_TYPE_EMAIL, SUBJECT, Constants.MESSAGE_BODY_TYPE_HTML, MESSAGE_MODULE);
		this.password = password;
		this.messageAddress = messageAddress;
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.detail.messaging.BaseMessage#buildParameters()
	 */
	@Override
	public void buildParameters() {
		StringBuilder builder = new StringBuilder("<password>");
		builder.append(this.password);
		builder.append("</password>");
		this.setParameters(builder.toString());
	}
}

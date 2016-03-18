package pt.ual.mgi.detail.messaging;

/**
 * Class that represents the Template Base Message
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see BaseMessage
 * @see ITemplateBaseMessage
 */
public abstract class TemplateBaseMessage extends BaseMessage implements ITemplateBaseMessage{

	private String moduleTypeClass = null;

	/**
	 * Constructor
	 * 
	 * @param messageType
	 * @param subject
	 * @param bodyType
	 * @param messageModule
	 */
	public TemplateBaseMessage(String messageType,
							String subject, String bodyType, String messageModule) {
		super(messageType, subject, null, bodyType, messageModule);
	}
	
	/** @return the moduleTypeClass */
	public String getModuleTypeClass() {return moduleTypeClass;}
	
	/**
	 * @param messageAddress the messageAddress to set
	 */
	public void setMessageAddress(String messageAddress) {this.messageAddress = messageAddress;}	
	/**
	 * @param moduleTypeClass the moduleTypeClass to set
	 */
	public void setModuleTypeClass(String moduleTypeClass) {this.moduleTypeClass = moduleTypeClass;}
}
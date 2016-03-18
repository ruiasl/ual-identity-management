/**
 * 
 */
package pt.ual.mgi.detail.messaging;

/**
 * Class that represents the message detail to Log
 * 
 * @author 20070337
 * @version 1.0
 */
public class MessageLogDetail {

	private String state;
	private String address;
	private String messageType;
	private String applicationType;
	private String moduleType;
	private String auditDate;
	
	/**
	 * Base Constructor
	 */
	public MessageLogDetail() {}

	/**
	 * Full Constructor
	 * 
	 * @param state
	 * @param address
	 * @param messageType
	 * @param applicationType
	 * @param moduleType
	 * @param auditDate
	 */
	public MessageLogDetail(String state, String address, String messageType,
			String applicationType, String moduleType, String auditDate) {
		super();
		this.state = state;
		this.address = address;
		this.messageType = messageType;
		this.applicationType = applicationType;
		this.moduleType = moduleType;
		this.auditDate = auditDate;
	}

	/**@return the state*/
	public String getState() {return state;}
	/**@return the address*/
	public String getAddress() {return address;}
	/**@return the messageType*/
	public String getMessageType() {return messageType;}
	/**@return the applicationType*/
	public String getApplicationType() {return applicationType;}
	/**@return the moduleType*/
	public String getModuleType() {return moduleType;}
	/**@return the auditDate*/
	public String getAuditDate() {return auditDate;}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @param applicationType the applicationType to set
	 */
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	/**
	 * @param moduleType the moduleType to set
	 */
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	/**
	 * @param auditDate the auditDate to set
	 */
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
}

/**
 * 
 */
package pt.ual.mgi.detail.messaging;


/**
 * Interface that defines the Template Base Message functionality
 * 
 * @author 20070337
 * @version 1.0
 */
public interface ITemplateBaseMessage {

	/**
	 * Method to get the module type class
	 * 
	 * @return String
	 */
	public String getModuleTypeClass();	
	
	/**
	 * Method to set the message address
	 * 
	 * @param address
	 */
	public void setMessageAddress(String address);
	
	/**
	 * Method to set the message parameters
	 * 
	 * @param parameters
	 */
	public void setParameters(String parameters);
	
	/**
	 * Method to get the message address
	 * 
	 * @return String
	 */
	public String getMessageAddress();
}

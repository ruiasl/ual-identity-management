package pt.ual.mgi.detail.messaging;

/**
 * Class that represents an attach
 * 
 * @author 20070337
 * @version 1.0
 */
public class AttachDetail {

	String attachFileName = null;
	byte[] attachContent = null;
	String attachContentType = null;
	
	/**
	 * Constructor
	 * 
	 * @param attachFileName
	 * @param attachContent
	 */
	public AttachDetail(String attachFileName, byte[] attachContent) {
		this.attachContent = attachContent;
		this.attachFileName = attachFileName;
	}

	/**
	 * Constructor
	 * 
	 * @param attachFileName
	 * @param attachContent
	 * @param attachContentType
	 */
	public AttachDetail(String attachFileName, 
			byte[] attachContent, String attachContentType) {
		this(attachFileName, attachContent);
		this.attachContentType = attachContentType;
	}

	/**@return the attachFileName*/
	public String getAttachFileName() {return attachFileName;}
	/**@return the attachContent*/
	public byte[] getAttachContent() {return attachContent;}
	/**@return the attachContentType*/
	public String getAttachContentType() {return attachContentType;}
}

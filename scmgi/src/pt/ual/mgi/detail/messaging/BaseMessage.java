package pt.ual.mgi.detail.messaging;

import java.util.Arrays;
import java.util.List;

import javax.mail.event.TransportListener;

import pt.ual.mgi.common.Constants;

/**
 * Abstract class that represents a message to sent
 * 
 * @author 20070337
 * @version 1.0
 */
public abstract class BaseMessage {

	protected String messageModule;
	protected String messageType = Constants.MESSAGE_TYPE_EMAIL;
    protected String messageAddress;
    private String[] messageBccAddress;
    private String[] messageCcAddress;
    private String messageFromAddress;
    private String parameters;
	protected String subject;
    private String body;
    protected String bodyType;
	protected TransportListener listener;
	private List<AttachDetail> attaches;
	
	
	/**
	 * Constructor
	 */
	public BaseMessage() {}
	
    /**
     * Constructor of Message object
     * 
     * @param toEmailAddress
     * @param messageType
     * @param subject
     * @param body
     * @param bodyType
     */
    public BaseMessage(String toEmailAddress, String messageType, 
    				String subject, String body, String bodyType, String messageModule) {
        this.messageAddress 	= toEmailAddress;
        this.messageType 		= messageType;
        this.messageModule 		= messageModule;
        this.subject 			= subject;
        this.body 				= body;
        this.bodyType 			= bodyType;
        this.parameters 		= "";
    }
    
    /**
     * Constructor of Message object
     * @param messageType
     * @param subject
     * @param body
     * @param bodyType
     */
    public BaseMessage(String messageType, 
    				String subject, String body, String bodyType, String messageModule) {
        this.messageType 		= messageType;
        this.messageModule 		= messageModule;
        this.subject 			= subject;
        this.body 				= body;
        this.bodyType 			= bodyType;
        this.parameters 		= "";
    }
    
    /**
     * Constructor of Message object
     * 
	 * @param messageModule
	 * @param messageType
	 * @param messageAddress
	 * @param messageBccAddress
	 * @param messageCcAddress
	 * @param messageFromAddress
	 * @param parameters
	 * @param subject
	 * @param body
	 * @param bodyType
	 * @param listener
	 */
	public BaseMessage(String messageModule, String messageType,
				String messageAddress, String[] messageBccAddress,
				String[] messageCcAddress, String messageFromAddress,
			String parameters, String subject, String body, String bodyType,
													TransportListener listener) {
		super();
		this.messageModule = messageModule;
		this.messageType = messageType;
		this.messageAddress = messageAddress;
		this.messageBccAddress = (messageBccAddress != null) ? 
					Arrays.copyOf(messageBccAddress, messageBccAddress.length) : null;
		this.messageCcAddress = (messageCcAddress != null) ? 
				Arrays.copyOf(messageCcAddress, messageCcAddress.length) : null;
		this.messageFromAddress = messageFromAddress;
		this.parameters = parameters;
		this.subject = subject;
		this.body = body;
		this.bodyType = bodyType;
		this.listener = listener;
	}

	/**
     * Method that returns the messageType with emailParameters
     * 
     * @return String
     */
    public String useParameters(){
    	return "<" + messageModule + ">\n" + parameters + "\n" + "</" + messageModule + ">";
    }

    /**
     * Abstract method that should be implemented by the specific message
     */
    public abstract void buildParameters();
    
    /** @return the messageModule */
	public String getMessageModule() {return messageModule;}
	/** @return the messageAddress */
	public String getMessageAddress() {return messageAddress;}	
	/** @return the parameters */
	public String getParameters() {return parameters;}
	/** @return the subject */
	public String getSubject() {return subject;}
	/** @return the body */
	public String getBody() {return body;}
	/** @return the bodyType */
	public String getBodyType() {return bodyType;}
	/** @return the listener */
	public TransportListener getListener() {return listener;}	
	/** @return the messageBccAddress */
	public String[] getMessageBccAddress() {
		return (this.messageBccAddress != null) ? 
			Arrays.copyOf(this.messageBccAddress, this.messageBccAddress.length) : null;
	}
	/** @return the messageCcAddress */
	public String[] getMessageCcAddress() {
		return (this.messageCcAddress != null) ? 
			Arrays.copyOf(this.messageCcAddress, this.messageCcAddress.length) : null;
	}
	/** @return the messageFromAddress */
	public String getMessageFromAddress() {return messageFromAddress;}
	/** @return the messageType */
	public String getMessageType() {return messageType;}	
	/** @return the attaches */
	public List<AttachDetail> getAttaches() {
		return this.attaches;
	}
	/**@param parameters the parameters to set*/
	public void setParameters(String parameters) {this.parameters = parameters;}
	/**@param body the body to set*/
	public void setBody(String body) {this.body = body;}
	/**@param listener the listener to set*/
	public void setListener(TransportListener listener) {this.listener = listener;}
	/**@param messageBccAddress the messageBccAddress to set*/
	public void setMessageBccAddress(String[] messageBccAddress) {
		this.messageBccAddress = (messageBccAddress != null) ? 
			Arrays.copyOf(messageBccAddress, messageBccAddress.length) : null;
	}
	/**@param messageCcAddress the messageCcAddress to set*/
	public void setMessageCcAddress(String[] messageCcAddress) {
		this.messageCcAddress = (messageCcAddress != null) ? 
				Arrays.copyOf(messageCcAddress, messageCcAddress.length) : null;
	}
	/**@param messageFromAddress the messageFromAddress to set*/
	public void setMessageFromAddress(String messageFromAddress) {
		this.messageFromAddress = messageFromAddress;
	}
	/**@param attaches the attaches to set*/
	public void setAttaches(List<AttachDetail> attaches) {
		this.attaches = attaches;
	}
}
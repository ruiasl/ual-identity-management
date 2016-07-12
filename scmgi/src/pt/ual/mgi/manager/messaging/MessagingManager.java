package pt.ual.mgi.manager.messaging;

import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.ual.mgi.common.Constants;
import pt.ual.mgi.detail.messaging.AttachDetail;
import pt.ual.mgi.detail.messaging.BaseMessage;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.manager.messaging.listener.MessagingTransportListener;

/**
 * Class that manages the operations of the functionality messaging.
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see IMessagingManager
 * @see BaseMessagingManager
 */
@Service
public class MessagingManager extends BaseMessagingManager implements IMessagingManager {

	private Logger log = LoggerFactory.getLogger(MessagingManager.class);
	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.messaging.IMessagingManager#
	 * sendMessage(pt.ual.mgi.detail.messaging.BaseMessage, boolean)
	 */
	@Override
	public void sendMessage(BaseMessage message, boolean applyTemplate)throws MgiException {
		log.debug("Entering MessagingManager sendMessage");
		
		log.debug("Checking received message.");
		if(message == null){
			log.error("Message will not be sented: Message was not passed.");
			return;
		}
		
		if(message.getMessageAddress() == null || "".equals(message.getMessageAddress())) {
			log.debug("Message will not be sented: Message has no address.");
			return;
		}
		
		log.debug("Check if needs to apply template.");
		if(applyTemplate) 
			this.templateTransformation((BaseMessage)message, false);
		
		this.send(message);
		log.debug("Exiting MessagingManager sendMessage");
	}	
	
	/**
	 * Method that send the message passed
	 * 
	 * @param baseMessage
	 * @throws MgiException
	 */
	private void send(BaseMessage baseMessage) throws MgiException{
		log.debug("Entering send()");
		Transport transport = null;
		try {			
			log.debug("Create the mail session.");
			Session session = this.createMailSession();
			
			log.debug("Initialize the Transport object.");
			transport = session.getTransport(Constants.SMTP_PROTOCOL);
			transport.addTransportListener(new MessagingTransportListener());
			
			transport.connect();
			
			log.debug("Generate mail message.");
			MimeMessage mime = this.generateMailMessage(session, baseMessage);
			
			log.debug("Send the message.");
			transport.sendMessage(mime, mime.getAllRecipients());
		} catch (MessagingException e) {
			log.debug("Message on error...");
			throw new MgiException("Error sending email", e);
		}
		log.debug("Entering send()");
	}
	
	/**
	 * Create the mail session 
	 * 
	 * @return Session
	 */
	private Session createMailSession() {
//		//Get system properties
//	    Properties props = System.getProperties();
//	    // Setup mail server
//	    String mailHost = Constants.SMTP_HOST;
//	    props.put(Constants.PROP_SMTP, mailHost);
//		Session session = Session.getDefaultInstance(props);
		
		
//		Properties props = new Properties();
//		props.put("mail.smtp.starttls.enable", true); // added this line
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.user", username);
//		props.put("mail.smtp.password", password);
//		props.put("mail.smtp.port", "587");
//		props.put("mail.smtp.auth", true);
//		props.put("mail.debug", "true");
		
		final String password = this.emailProperties.getProperty("mail.smtp.password");
		final String username = this.emailProperties.getProperty("mail.smtp.user");
		
		Session session = Session.getInstance(this.emailProperties,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		return session;
	}

	/**
	 * Generate a MailMessage From a BaseMessage
	 * 
	 * @param session
	 * @param messageToSend
	 * @return MimeMessage
	 * @throws MessagingException
	 */
	private MimeMessage generateMailMessage(Session session, BaseMessage messageToSend) throws MessagingException{
		log.debug("Entering MessagingManager.generateMailMessage");
		
		log.debug("Create MimeMessage");
		MimeMessage message = new MimeMessage(session);
	    
		log.debug("CheckFrom Address");
		if(messageToSend.getMessageFromAddress() == null || 
				"".equals(messageToSend.getMessageFromAddress()))
			message.setFrom(new InternetAddress(Constants.EMAIL_FROM));
		else
			message.setFrom(new InternetAddress(messageToSend.getMessageFromAddress()));
		
	    if(messageToSend.getMessageAddress() == null || "".equals(messageToSend.getMessageAddress()))
	    	throw new AddressException("Mail must have a least one address in recipiente TO.");
	    
	    String address = messageToSend.getMessageAddress();
	    
	    log.debug("Assigning to addresses for the mail");
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
	    
	    log.debug("Assigning BCC to addresses for the mail");
	    if(messageToSend.getMessageBccAddress() != null && 
	    			messageToSend.getMessageBccAddress().length >0){
	    	for (String bccAddress : messageToSend.getMessageBccAddress()) {
	    		message.addRecipient(Message.RecipientType.BCC, 
		    			new InternetAddress(bccAddress));
			}
	    	
	    }
	    
	    if(messageToSend.getMessageCcAddress() != null &&
	    		messageToSend.getMessageCcAddress().length > 0){
	    	for (String ccAddress : messageToSend.getMessageCcAddress()) {
	    		message.addRecipient(Message.RecipientType.CC, 
		    						new InternetAddress(ccAddress));
			}
	    }
	    
	    message.setSubject(messageToSend.getSubject());
	    message.setSentDate(new Date());

	    log.debug("Building the text body");
	    Multipart multipart = new MimeMultipart();
	    BodyPart messageBodyPart = new MimeBodyPart();
	    if(messageToSend.getBodyType().equals(Constants.MESSAGE_BODY_TYPE_HTML))
	    	messageBodyPart.setContent(messageToSend.getBody(), "text/html");
	    else 
	    	messageBodyPart.setText(messageToSend.getBody());
	    
	    multipart.addBodyPart(messageBodyPart);
	    
	    this.generateAttachsBodyPart(messageToSend, multipart);
	    
	    message.setContent(multipart);
	    
		return message;
	}

	/**
	 * Generate body part from Attaches in BaseMessage
	 * 
	 * @param message
	 * @param multipart
	 * @throws MessagingException
	 */
	private void generateAttachsBodyPart(BaseMessage message, 
							Multipart multipart) throws MessagingException {
		log.debug("Entering MessagingBuilder.generateAttachsBodyPart()");
		
		if(message != null && message.getAttaches() != null && message.getAttaches().size() > 0){
			for (AttachDetail attach : message.getAttaches()) {
				
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setFileName(attach.getAttachFileName());
				DataSource ds = null;
				if(attach.getAttachContentType() != null
						&& !"".equals(attach.getAttachContentType()))
					ds = new ByteArrayDataSource(
							attach.getAttachContent(), attach.getAttachContentType());
				else
					ds = new ByteArrayDataSource(
						attach.getAttachContent(), "text/html");
				messageBodyPart.setDataHandler(new DataHandler(ds));
				
				multipart.addBodyPart(messageBodyPart);
			}
		}
	}
}
package pt.ual.mgi.manager.messaging.listener;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ual.mgi.common.Constants;
import pt.ual.mgi.detail.messaging.MessageLogDetail;

/**
 * Listener for logging the messaging sent
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see TransportListener
 */
public class MessagingTransportListener implements TransportListener  {

	private static Logger log = LoggerFactory.getLogger(MessagingTransportListener.class);
	
	/*
	 * (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
	 */
	@Override
	public void messageDelivered(TransportEvent transportevent) {
		MessageLogDetail logDetail = new MessageLogDetail(); 
		logDetail.setAddress(transportevent.getValidSentAddresses()[0].toString());
		logDetail.setState(Constants.MESSAGE_DELIVERED);
		this.writeMessagingLog(logDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
	 */
	@Override
	public void messageNotDelivered(TransportEvent transportevent) {
		MessageLogDetail logDetail = new MessageLogDetail();
		logDetail.setAddress(transportevent.getValidSentAddresses()[0].toString());
		logDetail.setState(Constants.MESSAGE_NOT_DELIVERED);
		this.writeMessagingLog(logDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.mail.event.TransportListener#messagePartiallyDelivered(
	 * javax.mail.event.TransportEvent)
	 */
	@Override
	public void messagePartiallyDelivered(TransportEvent transportevent) {
		MessageLogDetail logDetail = new MessageLogDetail();
		logDetail.setAddress(transportevent.getValidSentAddresses()[0].toString());
		logDetail.setState(Constants.MESSAGE_PARTIALLY_DELIVERED);
		this.writeMessagingLog(logDetail);
	}

	/**
	 * Method to complete the messaging log
	 * This method should be overrided if the application type, module and 
	 * message type isn´t the specified
	 * 
	 * @param log_
	 * @return MessageLogDetail
	 */
	protected MessageLogDetail completeMessagingLog(MessageLogDetail log_) {
		
		System.currentTimeMillis();
		log_.setAuditDate("");
		log_.setMessageType(Constants.MESSAGE_TYPE_EMAIL);
		log_.setApplicationType(Constants.APPLICATION_MESSAGE_TYPE_MGI);
		log_.setModuleType(this.getModuleType());
		return log_;
	}
	
	/**
	 * Method that write´s the information into the Log 
	 * @param logDetail_
	 */
	private void writeMessagingLog(MessageLogDetail logDetail_) {
		logDetail_ = this.completeMessagingLog(logDetail_);
		StringBuilder builder = new StringBuilder();
		builder.append("-------------------------").append("\n");
		builder.append("Sent To: ").append(logDetail_.getAddress());
		builder.append("Sent At: ").append(logDetail_);
		builder.append("Sent State: ").append(logDetail_.getState());
		builder.append("Sent MessageType: ").append(logDetail_.getMessageType());
		builder.append("Sent Module: ").append(logDetail_.getModuleType());
		builder.append("Sent App: ").append(logDetail_.getApplicationType());
		builder.append("-------------------------");
		log.info(builder.toString());
	}
	
	/**
	 * Get the message ModuleType
	 * @return moduleType
	 */
	protected String getModuleType(){return "";}
}
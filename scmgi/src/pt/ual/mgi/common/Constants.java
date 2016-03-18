package pt.ual.mgi.common;

/**
 * Class that holds all the constants used at the application
 * 
 * @author 20070337
 * @version 1.0
 */
public class Constants {

	public static final String MESSAGE_TYPE_EMAIL = "EMAIL";
	public static final String UTF8_ENCODING = "UTF-8";
	public static final String ISO_ENCODING = "ISO-8859-1";
	
	public static final String SMTP_PROTOCOL = "smtp";
	public static final String SMTPS_PROTOCOL = "smtps";
	public static final String SMTP_HOST = "1.1.1.1";
	public static final String PROP_SMTP = "mail.smtp.host";
	
	public static final String EMAIL_FROM = "info@ual.pt";
	
	public static final String MESSAGE_BODY_TYPE_HTML = "HTML";
	
	public static final String MESSAGE_BODY_TYPE_TEXT = "TEXT";
	
	public static final String MESSAGE_CREATED = "P0";
	public static final String MESSAGE_DELIVERED = "X0";
	public static final String MESSAGE_NOT_DELIVERED = "A0";
	public static final String MESSAGE_PARTIALLY_DELIVERED = "P1";
	
	public static final String APPLICATION_MESSAGE_TYPE_MGI = "SCMGI";
}
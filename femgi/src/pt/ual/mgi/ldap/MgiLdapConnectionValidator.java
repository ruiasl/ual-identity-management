package pt.ual.mgi.ldap;

import java.net.Socket;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;

/**
 * Class that has the responsibility to validate LDAP connections
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see DefaultDirContextValidator
 */
public class MgiLdapConnectionValidator extends DefaultDirContextValidator {

	private Logger log = LoggerFactory.getLogger(MgiLdapConnectionValidator.class);
	
	/**
	 * Base Constructor
	 */
	public MgiLdapConnectionValidator() {super();}

	/**
	 * Constructor
	 * @param searchScope
	 */
	public MgiLdapConnectionValidator(int searchScope) {super(searchScope);}

	/**
	 * Constructor 
	 * @param ldapProperties
	 */
	public MgiLdapConnectionValidator(Properties ldapProperties) {
		super();
		String managerBase = ldapProperties.getProperty("ldap.manager.base");
		
		log.debug("Set base as {}", managerBase);
		this.setBase(managerBase);
	}
	
	/**
	 * Method that verifies if a LDAP URL is operational
	 * 
	 * @param urlChosen_
	 * @return boolean
	 */
	public boolean isURLOperational(String urlChosen_) {
		int urlChosenLength = urlChosen_.length();
		if(urlChosen_.endsWith("/")); urlChosenLength -= 1;
		
		String ldapPort = urlChosen_.substring(
					urlChosen_.lastIndexOf(":") + 1, urlChosenLength);
		String ldapIP = urlChosen_.substring(
					urlChosen_.indexOf("//") + 2, urlChosen_.lastIndexOf(":"));
		try {
	        log.debug("Checking if port {} is open by trying to connect as a client on IP {}.", ldapPort, ldapIP);
	        Socket sock = new Socket(ldapIP, Integer.parseInt(ldapPort));          
	        sock.close();
	    } catch (Exception e) {         
	        if (e.getMessage().contains("refused"))
	        	return false;
	    }
		return true;
	}
	
}

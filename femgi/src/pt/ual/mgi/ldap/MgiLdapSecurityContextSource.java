package pt.ual.mgi.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import pt.ual.mgi.exception.MgiException;

/**
 * Class that sets the environment of the LDAP Server
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see DefaultSpringSecurityContextSource
 */
public class MgiLdapSecurityContextSource extends DefaultSpringSecurityContextSource {

	private Logger log = LoggerFactory.getLogger(MgiLdapSecurityContextSource.class);
	
	private boolean validateLdapConnection = true;
	
	private MgiLdapConnectionValidator connectionValidator = null;
	
	/**
	 * Constructor
	 * 
	 * @param providerURL
	 */
	public MgiLdapSecurityContextSource(String providerURL) {super(providerURL);}

	/**
	 * Constructor
	 * 
	 * @param urls
	 * @param baseDn
	 */
	public MgiLdapSecurityContextSource(List<String> urls, String baseDn) {super(urls, baseDn);}
	
	/**
	 * Constructor
	 * 
	 * @param shareDao_
	 * @throws ViacttBusinessServicesException 
	 */
	public MgiLdapSecurityContextSource(Properties ldapProperties) throws MgiException {
    	this(ldapProperties.getProperty("ldap.provider.url"));
    	
    	String managerDn = ldapProperties.getProperty("ldap.manager.dn");
    	String managerPwd = ldapProperties.getProperty("ldap.manager.pwd");
    	if(managerDn == null || "".equals(managerDn) || 
    			managerPwd == null || "".equals(managerPwd))
    		throw new MgiException(
    			"The setting of LDAP manager authentication failed. Verify user and credencials.");
    		
    	super.setUserDn(managerDn);
    	super.setPassword(managerPwd);
    }
	
	
	/* (non-Javadoc)
	 * @see org.springframework.ldap.core.support.AbstractContextSource#createContext(
	 * java.util.Hashtable)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected DirContext createContext(Hashtable environment) {
		Object objLdapUrls = environment.get("java.naming.provider.url");
		if(objLdapUrls == null)
			throw new RuntimeException("Ldap URL must be set.");
		
		String ldapUrl = (String)objLdapUrls;
		
		List<String> ldapURLs = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(ldapUrl);
		while(token.hasMoreTokens())  {
			String url = token.nextToken();
			ldapURLs.add(url);            
        }
		
		String urlChosen = this.chooseLDAPUrl(ldapURLs, this.validateLdapConnection);
		if(urlChosen == null)
			throw LdapUtils.convertLdapException(
					new NamingException("None of the LDAP Url's passed is available."));
		
		environment.put("java.naming.provider.url", urlChosen);
		log.info("Connect to this LDAP URL {}", urlChosen);
		
		DirContext ctx = null;
		try {
			ctx = this.getDirContextInstance(environment);
			return ctx;
		} catch (NamingException e) {
			throw LdapUtils.convertLdapException(e);
		}
	}
	
	/**
	 * Method that randomly selects an URL for LDAP connection
	 * 
	 * @param ldapURLs_
	 * @param validateConn_
	 * @return String
	 */
	private String chooseLDAPUrl(List<String> ldapURLs_, boolean validateConn_) {
		int ldadUrlsSize = ldapURLs_.size();
		if(ldadUrlsSize <=0) return null;

		Random random = new Random();
		log.debug("Exists {} ldapUrls", ldadUrlsSize);
		int urlIndexChosen = random.nextInt(ldapURLs_.size());
		String urlChosen = ldapURLs_.get(urlIndexChosen);
		
		if(validateConn_) {
			if (connectionValidator == null) {
				connectionValidator = new MgiLdapConnectionValidator();
			}

			if(!connectionValidator.isURLOperational(urlChosen)) {
				ldapURLs_.remove(urlChosen);
				urlChosen = this.chooseLDAPUrl(ldapURLs_, validateConn_);
			}
		}
		return urlChosen;
	}
}

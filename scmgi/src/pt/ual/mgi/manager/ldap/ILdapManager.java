/**
 * 
 */
package pt.ual.mgi.manager.ldap;

import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;

import pt.ual.mgi.detail.useraccount.UserAccountDetail;

/**
 * Interface that represents the signatures of the Ldap Manager
 * 
 * @author 19960201
 * @version 1.0
 */
public interface ILdapManager {

	/**
	 * Retrieves the user account details from the LDAP server
	 * 
	 * @param userAccountId
	 * @returns userAccountDetail
	 * @throws LdapException, CursorException
	 */
	public UserAccountDetail getUserAccountEntry (String userAccountId) throws Exception;
	
	/**
	 * Retrieves the user account details from the LDAP server
	 * 
	 * @param connection
	 * @param baseDn
	 * @param searchAttribute
	 * @param searchCriteria
	 * @returns Entry
	 * @throws Exception
	 */
	public Entry getEntry (LdapConnection connection, String BaseDn, String searchAttribute, String searchCriteria) throws Exception;

	/**
	 * Creates the user account on the LDAP server
	 * 
	 * @param userAccountDetail
	 * @returns userAccountDetail
	 * @throws Exception
	 */
	public UserAccountDetail createEntry (UserAccountDetail userAccountDetail) throws Exception;
	
	/**
	 * Updates the user account on the LDAP server
	 * 
	 * @param userAccountDetail
	 * @returns userAccountDetail
	 * @throws Exception
	 */
	public UserAccountDetail updateEntry (UserAccountDetail userAccountDetail) throws Exception;
	
	/**
	 * Deletes the user account on the LDAP server
	 * 
	 * @param userAccountId
	 * @throws Exception
	 */
	public void deleteEntry (String userAccountId) throws Exception;
	
	/**
	 * moves the user account on the LDAP server to a different ou
	 * 
	 * @param userAccountDetail
	 * @param destOu
	 * @returns userAccountDetail
	 * @throws Exception
	 */
	public UserAccountDetail moveEntry (UserAccountDetail userAccountDetail, String destOu) throws Exception;
}

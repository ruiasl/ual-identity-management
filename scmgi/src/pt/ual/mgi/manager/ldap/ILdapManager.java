/**
 * 
 */
package pt.ual.mgi.manager.ldap;

import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionPool;

import pt.ual.mgi.detail.useraccount.UserAccount;

/**
 * Interface that represents the signatures of the Ldap Manager
 * 
 * @author 19960201
 * @version 1.0
 */
public interface ILdapManager {
	
	/**
	 * Converts an Entry (Ldap Entry) object to an userAccount object
	 * 
	 * @param userAccount
	 * @returns Entry
	 */

	public UserAccount convertEntryToAccount (Entry entry);

	/**
	 * Creates a new connection pool to the Ldap server
	 * 
	 * @param host
	 * @param port
	 * @param name
	 * @param password
	 * @returns LdapConnectionPool
	 * @throws Exception
	 */
	public LdapConnectionPool createConnectionPool(String host, int port, String name, String password) throws Exception;
	
	/**
	 * gets a connection to the Ldap server from the connection pool
	 * 
	 * @param pool
	 * @returns LdapConnection
	 * @throws Exception
	 */
	public LdapConnection getConnectionFromPool() throws Exception;
	
	/**
	 * Retrieves the user account details from the LDAP server
	 * 
	 * @param userAccount
	 * @returns Entry
	 * @throws LdapException, CursorException
	 */
	public Entry getEntry (UserAccount userAccount) throws Exception;
	
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
	 * @param userAccount
	 * @returns userAccount
	 * @throws Exception
	 */
	public UserAccount createEntry (UserAccount userAccount) throws Exception;
	
	/**
	 * Updates the user account on the LDAP server
	 * 
	 * @param userAccount
	 * @returns userAccount
	 * @throws Exception
	 */
	public UserAccount updateEntry (UserAccount userAccount) throws Exception;
	
	/**
	 * Deletes the user account on the LDAP server
	 * 
	 * @param userAccount
	 * @throws Exception
	 */
	public void deleteEntry (UserAccount userAccount) throws Exception;
	
	/**
	 * moves the user account on the LDAP server to a different ou
	 * 
	 * @param userAccount
	 * @param destOu
	 * @returns userAccount
	 * @throws Exception
	 */
	public UserAccount moveEntry (UserAccount userAccount, String destOu) throws Exception;
}

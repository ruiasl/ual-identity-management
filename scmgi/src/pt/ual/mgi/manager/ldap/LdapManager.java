/**
 * 
 */
package pt.ual.mgi.manager.ldap;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.AddRequest;
import org.apache.directory.api.ldap.model.message.AddRequestImpl;
import org.apache.directory.api.ldap.model.message.DeleteRequest;
import org.apache.directory.api.ldap.model.message.DeleteRequestImpl;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchRequestImpl;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.PoolableLdapConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pt.ual.mgi.detail.useraccount.UserAccount;

/**
 * @author 19960201
 * 
 *
 */
public class LdapManager implements ILdapManager {

	@Autowired
	private Properties ldapProperties;
	private LdapConnectionPool pool;
	
	@Override
	/**
	 * Creates a new connection pool and returns a connection to the Ldap server
	 * 
	 * @param host
	 * @param port
	 * @param name
	 * @param password
	 * @returns LdapConnection
	 * @throws Exception
	 */
	public LdapConnectionPool createConnectionPool(String host, int port, String name, String password) throws Exception
	{
		//Pool of connections
		LdapConnectionConfig config = new LdapConnectionConfig();
		config.setLdapHost( host );
		config.setLdapPort( port );
		config.setName( name );
		config.setCredentials( password );
		
		PoolableLdapConnectionFactory factory = new PoolableLdapConnectionFactory( config );
		@SuppressWarnings("unchecked")
		LdapConnectionPool pool = new LdapConnectionPool( factory );
		pool.setTestOnBorrow( true );
		return pool;
	}
	


	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public Entry getEntry(UserAccount userAccount) throws Exception {
		
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;
		
		LdapConnection connection = getConnectionFromPool();
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId(); 
		Entry entry = getEntry(connection, baseDn, searchAttribute, searchCriteria);
		return entry;
	}
	
	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getEntry(org.apache.directory.ldap.client.api.LdapConnection, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Entry getEntry(LdapConnection connection, String baseDn,
			String searchAttribute, String searchCriteria)
			throws LdapException, CursorException {
		
		Entry entry = new DefaultEntry();
		
		// set up search
		SearchRequest searchRequest = new SearchRequestImpl();
		searchRequest.setBase(new Dn(baseDn));
		searchRequest.setFilter("(" + searchAttribute + "=" + searchCriteria + ")");
		searchRequest.setScope(SearchScope.ONELEVEL);
		
		// execute
		SearchCursor searchCursor = connection.search(searchRequest);
		searchCursor.next();
		Response searchResponse = searchCursor.get();
		if (searchResponse instanceof SearchResultEntry) {
		    entry = ((SearchResultEntry)searchResponse).getEntry();
		}
		searchCursor.close();
		return entry;
	}


	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#createEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public UserAccount createEntry(UserAccount userAccount)
			throws Exception {
		
		final String organization = this.ldapProperties.getProperty("ldap.ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;

		
		LdapConnection connection = getConnectionFromPool();
		
		DefaultEntry newEntry = new DefaultEntry( 
				userName + userAccount.getUserName() 
	            	+ ",ou=" + organizationUnit
	            	+ ",o=" + organization,
	            "ObjectClass: top",
	            "ObjectClass: person",
	            "ObjectClass: organizationalPerson",
	            "ObjectClass: inetOrgPerson",
	            userName, userAccount.getUserName(),                  
	            userSurname, userAccount.getUserName(),
	            userId, userAccount.getUserId(),
	            userPassword, userAccount.getUserPassword()
	            ); 
		
		AddRequest addRequest = new AddRequestImpl();
		addRequest.setEntry( newEntry );
		connection.add( addRequest );
		
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId(); 
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#deleteEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public void deleteEntry(UserAccount userAccount)
			throws Exception {
		LdapConnection connection = getConnectionFromPool();
		Entry entry = getEntry(userAccount);
		DeleteRequest deleteRequest = new DeleteRequestImpl();
		deleteRequest.setName(entry.getDn());
		connection.delete(deleteRequest);
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getConnectionFromPool(org.apache.directory.ldap.client.api.LdapConnectionPool)
	 */
	@Override
	public LdapConnection getConnectionFromPool()
			throws Exception {
		
		
		
		try {
			if(pool==null){
				
				final String host = this.ldapProperties.getProperty("ldap.host.address");
				final int port = Integer.parseInt(this.ldapProperties.getProperty("ldap.host.port"));
				final String adminDn = this.ldapProperties.getProperty("ldap.admin.dn");
				final String adminPassword = this.ldapProperties.getProperty("ldap.admin.password");
				
				this.pool = createConnectionPool(host, port, adminDn, adminPassword);	
			}
			
			return pool.getConnection();
			
		} catch (LdapException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#convertEntryToAccount(org.apache.directory.api.ldap.model.entry.Entry)
	 */
	@Override
	public UserAccount convertEntryToAccount(Entry entry) {
		
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		
		UserAccount userAccount = new UserAccount(entry.get(userName).toString(),
												  entry.get(userId).toString(),
												  entry.get(userPassword).toString(),
												  "N/A",
												  "N/A",
												  "N/A",
												  "N/A"
													);
		return userAccount;
	}

	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#updateEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public UserAccount updateEntry(UserAccount userAccount) throws Exception {
		
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		//final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		//final String userSecretQuestion = this.ldapProperties.getProperty("ldap.entry.attr.secretQuestion");
		//final String userSecretAnswer = this.ldapProperties.getProperty("ldap.entry.attr.secretAnswer");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;
		

		LdapConnection connection = getConnectionFromPool();

		ArrayList<Modification> modifications = new ArrayList<>();
		// Retrieve the Entry
		Entry existingEntry = getEntry(userAccount);
		// Compare Entry and UserAccount, field by field
		// TODO será que há uma forma mais "automática" de fazer isto?
		if(!existingEntry.get(userName).toString().equals(userAccount.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userName, userAccount.getUserName()));
		}
		
		if(!existingEntry.get(userSurname).toString().equals(userAccount.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userSurname, userAccount.getUserName()));
		}
		
		if(!existingEntry.get(userId).toString().equals(userAccount.getUserId())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userId, userAccount.getUserId()));
		}
		
		if(!existingEntry.get(userPassword).toString().equals(userAccount.getUserPassword())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userPassword, userAccount.getUserPassword()));
		}
		
		// FALTAM AQUI OS RESTANTES ATRIBUTOS !!!
		
		// Update Entry
		for(Modification modification : modifications)
		{
			connection.modify(existingEntry.getDn(), modification);	
		}
		
		// return the updated entry as userAccount
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId();
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}



	/* (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#moveEntry(pt.ual.mgi.detail.useraccount.UserAccount, java.lang.String)
	 */
	@Override
	public UserAccount moveEntry(UserAccount userAccount, String destOuName)
			throws Exception {

		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		
		LdapConnection connection = getConnectionFromPool();
		
		Dn entryDn = getEntry(userAccount).getDn();
		String baseDn = "o=" + organization;
		String searchAttribute = "ou";
		String searchCriteria = destOuName;
		Dn destOuDn = getEntry(connection, baseDn, searchAttribute, searchCriteria).getDn();
		
		connection.move(entryDn, destOuDn);
		
		searchAttribute = userId;
		searchCriteria = userAccount.getUserId();
		baseDn = "ou=" + destOuName + ",o=" + organization;
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

}

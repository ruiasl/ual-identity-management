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
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pt.ual.mgi.detail.useraccount.UserAccount;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.manager.messaging.MessagingManager;

/**
 * @author 19960201
 * 
 *
 */
public class LdapManager implements ILdapManager {

	@Autowired
	private Properties ldapProperties;
	
	private LdapConnectionPool pool;
	
	private Logger log = LoggerFactory.getLogger(MessagingManager.class);
	
	@Override
	/**
	 * Creates a new connection pool and returns it
	 * 
	 * @param host
	 * @param port
	 * @param name
	 * @param password
	 * @returns LdapConnectionPool
	 */
	public LdapConnectionPool createConnectionPool(String host, int port, String name, String password)
	{
		log.debug("Entering LdapManager createConnectionPool");
		//Pool of connections
		LdapConnectionConfig config = new LdapConnectionConfig();
		config.setLdapHost( host );
		config.setLdapPort( port );
		config.setName( name );
		config.setCredentials( password );
		
		log.debug("Creating pool");
		PoolableLdapConnectionFactory factory = new PoolableLdapConnectionFactory( config );
		@SuppressWarnings("unchecked")
		LdapConnectionPool pool = new LdapConnectionPool( factory );
		pool.setTestOnBorrow( true );
		log.debug("Exiting LdapManager createConnectionPool");
		return pool;
	}
	


	/**
	 * Retrieves an entry, based on a userAccount. Wrapper for getEntry(connection, baseDn, searchAttribute, searchCriteria)
	 * 
	 * @param userAccount
	 * @throws MgiException
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public Entry getEntry(UserAccount userAccount) throws MgiException {
		
		log.debug("Entering LdapManager getEntry(userAccount)");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;
		
		log.debug("Getting connection from pool");
		LdapConnection connection;
		try {
			connection = getConnectionFromPool();
		} catch (Exception e) {
			log.debug("LDAP error: failed to get a connection from the connection pool");
			throw new MgiException("LDAP error: failed to get a connection from the connection pool", e);
		}
		log.debug("Retrieving entry from LDAP");
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId(); 
		Entry entry = getEntry(connection, baseDn, searchAttribute, searchCriteria);
		log.debug("Exiting LdapManager getEntry(userAccount)");
		return entry;
	}
	
	/**
	 * Retrieves an entry, based on the search criteria
	 * 
	 * @param connection
	 * @param baseDn
	 * @param searchAttribute
	 * @param searchCriteria
	 * @throws MgiException
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getEntry(org.apache.directory.ldap.client.api.LdapConnection, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Entry getEntry(LdapConnection connection, String baseDn,
			String searchAttribute, String searchCriteria) throws MgiException {
		
		log.debug("Entering LdapManager getEntry(complete parameters)");
		Entry entry = new DefaultEntry();
		
		// set up search
		log.debug("set up search");
		SearchRequest searchRequest = new SearchRequestImpl();
		try {
			searchRequest.setBase(new Dn(baseDn));
		} catch (LdapInvalidDnException e) {
			log.debug("LDAP error: Invalid Dn");
			throw new MgiException("LDAP error: Invalid Dn", e);
		}
		try {
			searchRequest.setFilter("(" + searchAttribute + "=" + searchCriteria + ")");
		} catch (LdapException e) {
			log.debug("LDAP error: Invalid search filter");
			throw new MgiException("LDAP error: Invalid search filter", e);
		}
		searchRequest.setScope(SearchScope.ONELEVEL);
		
		// execute
		log.debug("execute search");
		SearchCursor searchCursor;
		try {
			searchCursor = connection.search(searchRequest);
		} catch (LdapException e) {
			log.debug("LDAP error: search failed");
			throw new MgiException("LDAP error: search failed", e);
		}
		try {
			searchCursor.next();
		} catch (LdapException e) {
			log.debug("LDAP error: search failed");
			throw new MgiException("LDAP error: search failed", e);
		} catch (CursorException e) {
			log.debug("LDAP error: unexpected error on cursor.next");
			throw new MgiException("LDAP error: unexpected error on cursor.next", e);
		}
		Response searchResponse;
		try {
			searchResponse = searchCursor.get();
		} catch (CursorException e) {
			log.debug("LDAP error: unexpected error on cursor.get");
			throw new MgiException("LDAP error: unexpected error on cursor.get", e);
		}
		if (searchResponse instanceof SearchResultEntry) {
		    entry = ((SearchResultEntry)searchResponse).getEntry();
		}
		searchCursor.close();
		log.debug("Exiting LdapManager getEntry(complete parameters)");
		return entry;
	}


	/**
	 * Creates a new entry, based on a userAccount
	 * 
	 * @param userAccount
	 * @throws MgiException
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#createEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public UserAccount createEntry(UserAccount userAccount) throws MgiException
			{
		
		log.debug("Entering LdapManager createEntry");
		final String organization = this.ldapProperties.getProperty("ldap.ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String displayName = this.ldapProperties.getProperty("ldap.entry.attr.displayName");
		final String personalEmail = this.ldapProperties.getProperty("ldap.entry.attr.personalEmail");
		final String secretQuestion = this.ldapProperties.getProperty("ldap.entry.attr.secretQuestion");
		final String secretAnswer = this.ldapProperties.getProperty("ldap.entry.attr.secretAnswer");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;

		log.debug("Getting connection from pool");
		LdapConnection connection = getConnectionFromPool();
		
		log.debug("Creating entry");
		DefaultEntry newEntry;
		try {
			newEntry = new DefaultEntry( 
					userName + userAccount.getUserName() 
			        	+ ",ou=" + organizationUnit
			        	+ ",o=" + organization,
			        "ObjectClass: top",
			        "ObjectClass: person",
			        "ObjectClass: organizationalPerson",
			        "ObjectClass: inetOrgPerson",
			        "ObjectClass: ualPerson",
			        "ObjectClass: radiusprofile",
			        userName, userAccount.getUserName(),                  
			        userSurname, userAccount.getUserName(),
			        userId, userAccount.getUserId(),
			        userPassword, userAccount.getUserPassword(),
			        userEmail, userAccount.getUserEmail(),
			        displayName, userAccount.getDisplayName(),
			        personalEmail, userAccount.getPersonalEmail(),
			        secretQuestion, userAccount.getSecretQuestion(),
			        secretAnswer, userAccount.getSecretAnswer()
			        );
		} catch (LdapException e) {
			log.debug("LDAP error: unexpected error creating default entry");
			throw new MgiException("LDAP error: unexpected error creating default entry", e);
		} 
		
		log.debug("Adding entry to LDAP");
		AddRequest addRequest = new AddRequestImpl();
		addRequest.setEntry( newEntry );
		try {
			connection.add( addRequest );
		} catch (LdapException e) {
			log.debug("LDAP error: unexpected error adding entry to LDAP");
			throw new MgiException("LDAP error: unexpected error adding entry to LDAP", e);
		}
		
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId();
		log.debug("Exiting LdapManager createEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/**
	 * Deletes an entry based on a userAccount
	 * 
	 * @param userAccount
	 * @throws MgiException
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#deleteEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public void deleteEntry(UserAccount userAccount) throws MgiException
		 {
		log.debug("Entering LdapManager deleteEntry");
		LdapConnection connection = getConnectionFromPool();
		Entry entry = getEntry(userAccount);
		log.debug("Deleting entry from LDAP");
		DeleteRequest deleteRequest = new DeleteRequestImpl();
		deleteRequest.setName(entry.getDn());
		try {
			connection.delete(deleteRequest);
		} catch (LdapException e) {
			log.debug("LDAP error: unexpected error deleting entry from LDAP");
			throw new MgiException("LDAP error: unexpected error deleting entry from LDAP", e);
		}
		log.debug("Exiting LdapManager deleteEntry");
	}

	/** 
	 * Retrieves a connection from the connection pool
	 * 
	 * @return LdapConnection
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getConnectionFromPool(org.apache.directory.ldap.client.api.LdapConnectionPool)
	 */
	@Override
	public LdapConnection getConnectionFromPool()
			 {
		log.debug("Entering LdapManager getConnectionFromPool");
		try {
			if(pool==null){
				final String host = this.ldapProperties.getProperty("ldap.host.address");
				final int port = Integer.parseInt(this.ldapProperties.getProperty("ldap.host.port"));
				final String adminDn = this.ldapProperties.getProperty("ldap.admin.dn");
				final String adminPassword = this.ldapProperties.getProperty("ldap.admin.password");
				
				this.pool = createConnectionPool(host, port, adminDn, adminPassword);	
			}
			log.debug("Exiting (sucessfuly) LdapManager getConnectionFromPool");
			return pool.getConnection();
			
		} catch (LdapException e) {
			log.debug("Exiting (unsucessfuly) LdapManager getConnectionFromPool");
			return null;
		}
	}

	/** 
	 * Converts an Entry to a UserAccount
	 * 
	 * @param entry
	 * @return UserAccount
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#convertEntryToAccount(org.apache.directory.api.ldap.model.entry.Entry)
	 */
	@Override
	public UserAccount convertEntryToAccount(Entry entry) {
		
		log.debug("Entering LdapManager convertEntryToAccount");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String displayName = this.ldapProperties.getProperty("ldap.entry.attr.displayName");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String personalEmail = this.ldapProperties.getProperty("ldap.entry.attr.personalEmail");
		final String secretQuestion = this.ldapProperties.getProperty("ldap.entry.attr.secretQuestion");
		final String secretAnswer = this.ldapProperties.getProperty("ldap.entry.attr.secretAnswer");
		
		log.debug("Creating userAccount with entry data");
		UserAccount userAccount = new UserAccount(entry.get(userName).toString(),
												  entry.get(userSurname).toString(),
												  entry.get(displayName).toString(),												  
												  entry.get(userId).toString(),
												  entry.get(userPassword).toString(),
												  entry.get(userEmail).toString(),
												  entry.get(personalEmail).toString(),
												  entry.get(secretQuestion).toString(),
												  entry.get(secretAnswer).toString()
													);
		log.debug("Exiting LdapManager convertEntryToAccount");
		return userAccount;
	}

	/** 
	 * Updates an entry, based on a UserAccount
	 * 
	 * @param userAccount
	 * @return UserAccount
	 * @throws MgiException
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#updateEntry(pt.ual.mgi.detail.useraccount.UserAccount)
	 */
	@Override
	public UserAccount updateEntry(UserAccount userAccount) throws MgiException {
		
		log.debug("Entering LdapManager updateEntry");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String displayName = this.ldapProperties.getProperty("ldap.entry.attr.displayName");
		final String personalEmail = this.ldapProperties.getProperty("ldap.entry.attr.personalEmail");
		final String secretQuestion = this.ldapProperties.getProperty("ldap.entry.attr.secretQuestion");
		final String secretAnswer = this.ldapProperties.getProperty("ldap.entry.attr.secretAnswer");
		final String baseDn = "ou=" + organizationUnit + ",o=" + organization;
		
		log.debug("Getting connection from pool");
		LdapConnection connection = getConnectionFromPool();

		log.debug("Preparing update");
		ArrayList<Modification> modifications = new ArrayList<>();
		// Retrieve the Entry
		Entry existingEntry = getEntry(userAccount);
		// Compare Entry and UserAccount, field by field
		// TODO será que há uma forma mais "automática" de fazer isto?
		
		// userName
		if(!existingEntry.get(userName).toString().equals(userAccount.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userName, userAccount.getUserName()));
		}
		
		// userSurname
		if(!existingEntry.get(userSurname).toString().equals(userAccount.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userSurname, userAccount.getUserName()));
		}
		
		// userPassword
		if(!existingEntry.get(userPassword).toString().equals(userAccount.getUserPassword())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userPassword, userAccount.getUserPassword()));
		}
		
		// userEmail
		if(!existingEntry.get(userEmail).toString().equals(userAccount.getUserEmail())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userEmail, userAccount.getUserEmail()));
		}
		
		// personalEmail
		if(!existingEntry.get(personalEmail).toString().equals(userAccount.getPersonalEmail())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, personalEmail, userAccount.getPersonalEmail()));
		}
				
		// displayName
		if(!existingEntry.get(displayName).toString().equals(userAccount.getDisplayName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, displayName, userAccount.getDisplayName()));
		}
				
		// secretQuestion
		if(!existingEntry.get(secretQuestion).toString().equals(userAccount.getSecretQuestion())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, secretQuestion, userAccount.getSecretQuestion()));
		}
				
		// secretAnswer
		if(!existingEntry.get(secretAnswer).toString().equals(userAccount.getSecretAnswer())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, secretAnswer, userAccount.getSecretAnswer()));
		}
		
		log.debug("Executing update");
		// Update Entry
		for(Modification modification : modifications)
		{
			try {
				connection.modify(existingEntry.getDn(), modification);
			} catch (LdapException e) {
				log.debug("LDAP error: unexpected error modifing entry on the LDAP");
				throw new MgiException("LDAP error: unexpected error modifing entry on the LDAP", e);
			}	
		}
		
		// return the updated entry as userAccount
		String searchAttribute = userId;
		String searchCriteria = userAccount.getUserId();
		log.debug("Exiting LdapManager updateEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/**
	 * Moves an entry from an organizational unit to another in the LDAP server
	 * 
	 * @param userAccount
	 * @param destOuName
	 * @return UserAccount
	 * @throws MgiException
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#moveEntry(pt.ual.mgi.detail.useraccount.UserAccount, java.lang.String)
	 */
	@Override
	public UserAccount moveEntry(UserAccount userAccount, String destOuName) throws MgiException
			 {

		log.debug("Entering LdapManager moveEntry");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		
		log.debug("Getting connection from pool");
		LdapConnection connection = getConnectionFromPool();
		
		log.debug("Setting up move");
		Dn entryDn = getEntry(userAccount).getDn();
		String baseDn = "o=" + organization;
		String searchAttribute = "ou";
		String searchCriteria = destOuName;
		Dn destOuDn = getEntry(connection, baseDn, searchAttribute, searchCriteria).getDn();
		
		log.debug("Executing move");
		try {
			connection.move(entryDn, destOuDn);
		} catch (LdapException e) {
			log.debug("LDAP error: unexpected error moving entry on the LDAP");
			throw new MgiException("LDAP error: unexpected error moving entry on the LDAP", e);
		}
		
		searchAttribute = userId;
		searchCriteria = userAccount.getUserId();
		baseDn = "ou=" + destOuName + ",o=" + organization;
		log.debug("Exiting LdapManager moveEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

}

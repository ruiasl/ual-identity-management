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
import org.springframework.stereotype.Service;

import pt.ual.mgi.detail.useraccount.UserAccountDetail;
import pt.ual.mgi.exception.MgiException;

/**
 * Class that is responsible for Ldap Management
 * 
 * @author 19960201
 * @version 1.0
 * 
 * @see ILdapManager
 */
@Service
public class LdapManager implements ILdapManager {

	@Autowired
	private Properties ldapProperties;
	
	private LdapConnectionPool pool;
	
	private Logger log = LoggerFactory.getLogger(LdapManager.class);
	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getUserAccountEntry(java.lang.String)
	 */
	@Override
	public UserAccountDetail getUserAccountEntry(String userAccountId) throws MgiException {
		log.debug("Entering LdapManager getEntry(userAccountId)");
		Entry entry = this.getEntry(userAccountId);
		
		if(entry == null)
			throw new MgiException("Entry not found...");
		
		return this.convertEntryToAccount(entry);
	}
	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#
	 * getEntry(org.apache.directory.ldap.client.api.LdapConnection, 
	 * java.lang.String, java.lang.String, java.lang.String)
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
		searchRequest.setScope(SearchScope.SUBTREE);
		
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


	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#
	 * createEntry(pt.ual.mgi.detail.useraccount.UserAccountDetail)
	 */
	@Override
	public UserAccountDetail createEntry(UserAccountDetail userAccountDetail) throws MgiException {
		log.debug("Entering LdapManager createEntry");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String phone = this.ldapProperties.getProperty("ldap.entry.attr.phone");
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
					userId + "=" + userAccountDetail.getUserId() 
			        	+ ",ou=" + organizationUnit
			        	+ ",o=" + organization,
			        "ObjectClass: top",
			        "ObjectClass: person",
			        "ObjectClass: organizationalPerson",
			        "ObjectClass: inetOrgPerson",
			        "ObjectClass: ualPerson",
			        "ObjectClass: radiusprofile",
			        userName, userAccountDetail.getUserName(),                  
			        userSurname, userAccountDetail.getUserSurname(),
			        userId, userAccountDetail.getUserId(),
			        userPassword, userAccountDetail.getUserPassword(),
			        userEmail, userAccountDetail.getUserEmail(),
			        displayName, userAccountDetail.getDisplayName(),
			        personalEmail, userAccountDetail.getPersonalEmail(),
			        phone, userAccountDetail.getPhone(),
			        secretQuestion, userAccountDetail.getSecretQuestion(),
			        secretAnswer, userAccountDetail.getSecretAnswer()
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
		String searchCriteria = userAccountDetail.getUserId();
		log.debug("Exiting LdapManager createEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#deleteEntry(java.lang.String)
	 */
	@Override
	public void deleteEntry(String userAccountId) throws MgiException
		 {
		log.debug("Entering LdapManager deleteEntry");
		LdapConnection connection = getConnectionFromPool();
		Entry entry = this.getEntry(userAccountId);
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

	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#updateEntry(pt.ual.mgi.detail.useraccount.UserAccountDetail)
	 */
	@Override
	public UserAccountDetail updateEntry(UserAccountDetail userAccountDetail) throws MgiException {
		
		log.debug("Entering LdapManager updateEntry");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String phone = this.ldapProperties.getProperty("ldap.entry.attr.phone");
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
		Entry existingEntry = this.getEntry(userAccountDetail.getUserId());
		// Compare Entry and UserAccount, field by field
		
		// userName
		if(userAccountDetail.getUserName() != null && 
				!existingEntry.get(userName).get().getString().equals(userAccountDetail.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userName, userAccountDetail.getUserName()));
		}
		
		// userSurname
		if(userAccountDetail.getUserName() != null && !existingEntry.get(userSurname).get().getString().equals(userAccountDetail.getUserName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userSurname, userAccountDetail.getUserName()));
		}
		
		// userPassword
		if(userAccountDetail.getUserPassword() != null && !existingEntry.get(userPassword).get().getString().equals(userAccountDetail.getUserPassword())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userPassword, userAccountDetail.getUserPassword()));
		}
		
		// userEmail
		if(userAccountDetail.getUserEmail() != null && !existingEntry.get(userEmail).get().getString().equals(userAccountDetail.getUserEmail())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, userEmail, userAccountDetail.getUserEmail()));
		}
		
		// personalEmail
		if(userAccountDetail.getPersonalEmail() != null && !existingEntry.get(personalEmail).get().getString().equals(userAccountDetail.getPersonalEmail())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, personalEmail, userAccountDetail.getPersonalEmail()));
		}
		
		// phone
		if(userAccountDetail.getPhone() != null && !existingEntry.get(phone).get().getString().equals(userAccountDetail.getPhone())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, phone, userAccountDetail.getPhone()));
		}
		
		// displayName
		if(userAccountDetail.getDisplayName() != null && !existingEntry.get(displayName).get().getString().equals(userAccountDetail.getDisplayName())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, displayName, userAccountDetail.getDisplayName()));
		}
				
		// secretQuestion
		if(userAccountDetail.getSecretQuestion() != null && !existingEntry.get(secretQuestion).get().getString().equals(userAccountDetail.getSecretQuestion())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, secretQuestion, userAccountDetail.getSecretQuestion()));
		}
				
		// secretAnswer
		if(userAccountDetail.getSecretAnswer() != null && !existingEntry.get(secretAnswer).get().getString().equals(userAccountDetail.getSecretAnswer())) {
			modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, secretAnswer, userAccountDetail.getSecretAnswer()));
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
		String searchCriteria = userAccountDetail.getUserId();
		log.debug("Exiting LdapManager updateEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#
	 * moveEntry(pt.ual.mgi.detail.useraccount.UserAccountDetail, java.lang.String)
	 */
	@Override
	public UserAccountDetail moveEntry(UserAccountDetail userAccountDetail, String destOuName) throws MgiException
			 {

		log.debug("Entering LdapManager moveEntry");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		
		log.debug("Getting connection from pool");
		LdapConnection connection = getConnectionFromPool();
		
		log.debug("Setting up move");
		Dn entryDn = this.getEntry(userAccountDetail.getUserId()).getDn();
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
		searchCriteria = userAccountDetail.getUserId();
		baseDn = "ou=" + destOuName + ",o=" + organization;
		log.debug("Exiting LdapManager moveEntry");
		return convertEntryToAccount(getEntry(connection, baseDn, searchAttribute, searchCriteria));
	}

	/** 
	 * Retrieves a connection from the connection pool
	 * 
	 * @return LdapConnection
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#getConnectionFromPool(org.apache.directory.ldap.client.api.LdapConnectionPool)
	 */
	private LdapConnection getConnectionFromPool()
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
	 * Creates a new connection pool and returns it
	 * 
	 * @param host
	 * @param port
	 * @param name
	 * @param password
	 * @returns LdapConnectionPool
	 */
	private LdapConnectionPool createConnectionPool(String host, int port, String name, String password){
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
	 * Converts an Entry to a userAccountDetail
	 * 
	 * @param entry
	 * @return userAccountDetail
	 * 
	 * @see pt.ual.mgi.manager.ldap.ILdapManager#convertEntryToAccount(org.apache.directory.api.ldap.model.entry.Entry)
	 */
	private UserAccountDetail convertEntryToAccount(Entry entry) {
		log.debug("Entering LdapManager convertEntryToAccount");
		
		if(entry == null)
			return null;
		
		final String userName = this.ldapProperties.getProperty("ldap.entry.attr.name");
		final String userSurname = this.ldapProperties.getProperty("ldap.entry.attr.surname");
		final String displayName = this.ldapProperties.getProperty("ldap.entry.attr.displayName");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String userPassword = this.ldapProperties.getProperty("ldap.entry.attr.password");
		final String userEmail = this.ldapProperties.getProperty("ldap.entry.attr.email");
		final String personalEmail = this.ldapProperties.getProperty("ldap.entry.attr.personalEmail");
		final String phone = this.ldapProperties.getProperty("ldap.entry.attr.phone");
		final String secretQuestion = this.ldapProperties.getProperty("ldap.entry.attr.secretQuestion");
		final String secretAnswer = this.ldapProperties.getProperty("ldap.entry.attr.secretAnswer");
		
		log.debug("Creating userAccount with entry data");
		UserAccountDetail userAccountDetail = new UserAccountDetail(
				entry.get(userName) == null ? null : entry.get(userName).get().getString(),
					entry.get(userSurname) == null ? null : entry.get(userSurname).get().getString(),
						entry.get(displayName) == null ? null : entry.get(displayName).get().getString(),												  
							entry.get(userId) == null ? null : entry.get(userId).get().getString(),
								entry.get(userPassword) == null ? null : entry.get(userPassword).get().getString(),
									entry.get(userEmail) == null ? null : entry.get(userEmail).get().getString(),
										entry.get(personalEmail) == null ? null : entry.get(personalEmail).get().getString(),
											entry.get(secretQuestion) == null ? null :entry.get(secretQuestion).get().getString(),
												entry.get(secretAnswer) == null ? null :entry.get(secretAnswer).get().getString(),
													entry.get(phone) == null ? null :entry.get(phone).get().getString());
		log.debug("Exiting LdapManager convertEntryToAccount");
		return userAccountDetail;
	}

	/**
	 * Method that obtains an entry by userAccountId
	 * 
	 * @param userAccountId
	 * @return Entry
	 * @throws MgiException
	 */
	private Entry getEntry(String userAccountId) throws MgiException {
		log.debug("Entering LdapManager getEntry(userAccountId)");
		final String userId = this.ldapProperties.getProperty("ldap.entry.attr.id");
		final String organization = this.ldapProperties.getProperty("ldap.structure.top.organization");
		final String organizationUnit = this.ldapProperties.getProperty("ldap.structure.users.main.ou");
		//final String baseDn = "ou=" + organizationUnit + ",o=" + organization;
		final String baseDn = "o=" + organization;
		
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
		String searchCriteria = userAccountId; 
		Entry entry = getEntry(connection, baseDn, searchAttribute, searchCriteria);
		log.debug("Exiting LdapManager getEntry(userAccountId)");
		return entry;
	}	
}

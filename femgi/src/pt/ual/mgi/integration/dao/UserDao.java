package pt.ual.mgi.integration.dao;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.integration.detail.user.UserAccount;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * Class that executes the operations on User
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see IUserDaos
 */
@Repository
public class UserDao implements IUserDao {

	private Logger log = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	private Properties restClientProperties;
	
	private Client client;
	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#createAccount(pt.ual.mgi.detail.UserAccount)
	 */
	@Override
	public UserAccount createAccount(UserAccount userAccount) {
		
		
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#getAccount(java.lang.String)
	 */
	@Override
	public UserAccount getAccount(String id) throws MgiException, BusinessMgiException {
		log.debug("Entering UserDat.getAccount...");
		
		UserAccount userAccount = null;
		
		try{
		
			StringBuilder accountsResourceUrlBuilder = new StringBuilder(
					this.restClientProperties.getProperty("scmgi.rest.identities.base.url"));
			accountsResourceUrlBuilder.append(
					this.restClientProperties.getProperty("scmgi.rest.identities.accounts.resource"));
			
			WebTarget webTarget = 
					this.getScmgiClient().target(accountsResourceUrlBuilder.toString()).path(id);
			
			Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get();
			 
			if(response == null){
				log.error("Could not obtain response from identities service...");
				throw new MgiException("Could not obtain response from identities service...");
			}
			
			if(!HttpStatus.OK.equals(response.getStatus())){
				if(HttpStatus.NOT_FOUND.equals(response.getStatus())){
					log.error("User with id {} not found.", id);
					throw new UsernameNotFoundException("User with id {} not found." + id);
				}
				log.error("Error getting user from identities service...");
				throw new MgiException("Error getting user from identities service...");
			}
			
			userAccount = response.readEntity(UserAccount.class);
			
		}catch(Exception e){
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		}
		log.debug("Exiting UserDat.getAccount...");
		return userAccount;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#updateAccount(pt.ual.mgi.detail.UserAccount)
	 */
	@Override
	public UserAccount updateAccount(UserAccount userAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#resetAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#forgotAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean forgotAccountPassword(String id, String hintAnswer,
			String email) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Method that creates the client
	 * @return Client
	 */
	private Client getScmgiClient(){	
		log.debug("Method that obtains the rest client...");
		if(this.client == null){
			try{
				client = ClientBuilder.newClient();
				client.register(new JacksonJsonProvider());
			}catch(Exception e){
				log.error("Error creating client: {}", e.getMessage());
				client = null;
			}
		}
		return client;
	}
	
}

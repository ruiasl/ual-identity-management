package pt.ual.mgi.integration.dao;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.exception.UserNotFoundException;
import pt.ual.mgi.integration.client.ApiClient;
import pt.ual.mgi.integration.client.ApiException;
import pt.ual.mgi.integration.client.api.IdentitiesApi;
import pt.ual.mgi.integration.client.model.UserAccount;

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
	
	private IdentitiesApi identitiesApi;
	
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
			log.debug("Getting the user account by id: {}", id);
			userAccount = this.getScmgiClient().getAccount(id);
			
		} catch(ApiException e){
			if(e.getCode() == HttpStatus.NOT_FOUND.value()){
				log.error("User with id {} not found.", id);
				throw new UserNotFoundException("User with id {} not found." + id);
			}
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		} catch(Exception e){
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
	public UserAccount updateAccount(UserAccount userAccount) throws MgiException {
		log.debug("Entering UserDao.updateAccount...");
		
		try{
			log.debug("Update the the user account by id: {}", userAccount.getId());
			userAccount = this.getScmgiClient().updateAccount(userAccount.getId(), userAccount);
			
		} catch(ApiException e){
			if(e.getCode() == HttpStatus.NOT_FOUND.value()){
				log.error("User with id {} not found.", userAccount.getId());
				throw new UserNotFoundException("User with id {} not found." + userAccount.getId());
			}
			log.error("Error updating user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		} catch(Exception e){
			log.error("Error updating user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		}
		log.debug("Exiting UserDao.updateAccount...");
		return userAccount;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String id) throws MgiException {
		log.debug("Entering UserDao.deleteAccount...");
		
		try{
			log.debug("Delete the the user account by id: {}", id);
			this.getScmgiClient().deleteAccount(id);
			
		} catch(ApiException e){
			if(e.getCode() == HttpStatus.NOT_FOUND.value()){
				log.error("User with id {} not found.", id);
				throw new UserNotFoundException("User with id {} not found." + id);
			}
			log.error("Error deleting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		} catch(Exception e){
			log.error("Error deleting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		}
		log.debug("Exiting UserDao.deleteAccount...");	
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#resetAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd) throws MgiException {
		log.debug("Entering UserDao.resetAccountPassword...");
		
		try{			
			this.getScmgiClient().resetAccountPassword(id, oldPwd, newPwd);			
			return true;
		} catch(ApiException e){
			if(e.getCode() == HttpStatus.NOT_FOUND.value()){
				log.error("User with id {} not found.", id);
				throw new UserNotFoundException("User with id {} not found." + id);
			}
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		} catch(Exception e){
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.integration.dao.IUserDao#forgotAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean forgotAccountPassword(String id, String hintAnswer,String email) throws MgiException {
		log.debug("Entering UserDao.forgotAccountPassword...");
		
		try{			
			this.getScmgiClient().forgotAccountPassword(id, hintAnswer, email);			
			return true;
		} catch(ApiException e){
			if(e.getCode() == HttpStatus.NOT_FOUND.value()){
				log.error("User with id {} not found.", id);
				throw new UserNotFoundException("User with id {} not found." + id);
			}
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		} catch(Exception e){
			log.error("Error getting user: " + e.getMessage());
			throw new MgiException(e.getMessage());
		}
	}
	
	/**
	 * Method that creates the client
	 * @return IdentitiesApi
	 */
	private IdentitiesApi getScmgiClient(){
		if(this.identitiesApi==null){
			
			ApiClient apiClient = new ApiClient().setBasePath(
					this.restClientProperties.getProperty("scmgi.rest.identities.base.url"));
			this.identitiesApi = new IdentitiesApi(apiClient);
		}
		return this.identitiesApi;
	}
}

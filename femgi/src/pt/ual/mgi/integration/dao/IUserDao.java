package pt.ual.mgi.integration.dao;

import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.integration.client.model.UserAccount;

/**
 * Class that represents the signature for UserDao operations 
 * 
 * @author 20070337
 * @version 1.0
 */
public interface IUserDao {

	/**
	 * Method that creates an account
	 * 
	 * @param userAccount
	 * @return UserAccount
	 */
	public UserAccount createAccount(UserAccount userAccount);
	
	/**
	 * Method that gets user by Id
	 * 
	 * @param id
	 * @return UserAccount
	 * @throws MgiException 
	 * @throws BusinessMgiException 
	 */
	public UserAccount getAccount(String id) throws MgiException, BusinessMgiException;
	
	/**
	 * Method that updates an account
	 * 
	 * @param userAccount
	 * @return UserAccount
	 * @throws MgiException 
	 */
	public UserAccount updateAccount(UserAccount userAccount) throws MgiException;
	
	/**
	 * Method that deletes an UserAccount
	 * @param id
	 * @throws MgiException 
	 */
	public void deleteAccount(String id) throws MgiException;
	
	/**
	 * Method that resets an user Password
	 * 
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 * @return boolean
	 * @throws MgiException 
	 */
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd) throws MgiException;
	
	/**
	 * Method that sends a password to user
	 * 
	 * @param id
	 * @param hintAnswer
	 * @param email
	 * @return boolean
	 * @throws MgiException 
	 */
	public boolean forgotAccountPassword(String id, String hintAnswer, String email) throws MgiException;
}

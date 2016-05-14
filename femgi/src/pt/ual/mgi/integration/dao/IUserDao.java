package pt.ual.mgi.integration.dao;

import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.integration.detail.user.UserAccount;

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
	 */
	public UserAccount updateAccount(UserAccount userAccount);
	
	/**
	 * Method that deletes an UserAccount
	 * @param id
	 */
	public void deleteAccount(String id);
	
	/**
	 * Method that resets an user Password
	 * 
	 * @param id
	 * @param oldPwd
	 * @param newPwd
	 * @return boolean
	 */
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd);
	
	/**
	 * Method that sends a password to user
	 * 
	 * @param id
	 * @param hintAnswer
	 * @param email
	 * @return boolean
	 */
	public boolean forgotAccountPassword(String id, String hintAnswer, String email);
}

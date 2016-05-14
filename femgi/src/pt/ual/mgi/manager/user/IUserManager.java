package pt.ual.mgi.manager.user;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;

/**
 * Class that represent the signature of user operations
 * 
 * @author 20070337
 * @version 1.0
 */
public interface IUserManager {

	/**
	 * Method that creates an account
	 * 
	 * @param userAccountDetail
	 * @return UserAccountDetail
	 */
	public UserAccountDetail createAccount(UserAccountDetail userAccountDetail);
	
	/**
	 * Method that gets user by Id
	 * 
	 * @param id
	 * @return UserAccountDetail
	 * @throws MgiException 
	 * @throws BusinessMgiException 
	 */
	public UserAccountDetail getAccount(String id) throws MgiException, BusinessMgiException;
	
	/**
	 * Method that updates an account
	 * 
	 * @param userAccountDetail
	 * @return UserAccountDetail
	 */
	public UserAccountDetail updateAccount(UserAccountDetail userAccountDetail);
	
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

/**
 * 
 */
package pt.ual.mgi.detail.useraccount;

/**
 * Class that represents a user account
 * 
 * @author 19960201
 * @version 1.0
 *
 */
public class UserAccount {
	protected String userName;
	protected String userId;
	protected String userPassword;
	protected String userEmail;
	protected String userPhoneNbr;
	protected String userSecretQuestion;
	protected String userSecretAnswer;
	
	/**
	 * Constructor
	 * 
	 * @param userName
	 * @param userId
	 * @param userPassword
	 * @param userEmail
	 * @param userPhoneNbr
	 * @param userSecretQuestion
	 * @param userSecretAnswer
	 */
	public UserAccount(String userName, String userId, String userPassword,
			String userEmail, String userPhoneNbr, String userSecretQuestion,
			String userSecretAnswer) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhoneNbr = userPhoneNbr;
		this.userSecretQuestion = userSecretQuestion;
		this.userSecretAnswer = userSecretAnswer;
	}

	/**@return the userName*/
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**@return the userId*/
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**@return the userPassword*/
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**@return the userEmail*/
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**@return the userPhoneNbr*/
	public String getUserPhoneNbr() {
		return userPhoneNbr;
	}

	/**
	 * @param userPhoneNbr the userPhoneNbr to set
	 */
	public void setUserPhoneNbr(String userPhoneNbr) {
		this.userPhoneNbr = userPhoneNbr;
	}

	/**@return the userSecretQuestion*/
	public String getUserSecretQuestion() {
		return userSecretQuestion;
	}

	/**
	 * @param userSecretQuestion the userSecretQuestion to set
	 */
	public void setUserSecretQuestion(String userSecretQuestion) {
		this.userSecretQuestion = userSecretQuestion;
	}

	/**@return the userSecretAnswer*/
	public String getUserSecretAnswer() {
		return userSecretAnswer;
	}

	/**
	 * @param userSecretAnswer the userSecretAnswer to set
	 */
	public void setUserSecretAnswer(String userSecretAnswer) {
		this.userSecretAnswer = userSecretAnswer;
	}

}

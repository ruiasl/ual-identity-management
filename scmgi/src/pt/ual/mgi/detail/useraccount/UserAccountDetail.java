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
public class UserAccountDetail {
	protected String userName;
	protected String userSurname;
	protected String displayName;
	protected String userId;
	protected String userPassword;
	protected String userEmail;
	protected String phone;
	protected String personalEmail;
	protected String secretQuestion;
	protected String secretAnswer;
	
	/**
	 * Base Constructor
	 */
	public UserAccountDetail(){super();};
	
	/**
	 * Constructor
	 * 
	 * @param userName
	 * @param userSurname
	 * @param displayName
	 * @param userId
	 * @param userPassword
	 * @param userEmail
	 * @param personalEmail
	 * @param secretQuestion
	 * @param secretAnswer
	 */
	public UserAccountDetail(String userName, String userSurname, String displayName, 
			String userId, String userPassword, String userEmail, String personalEmail,
			String secretQuestion, String secretAnswer, String phone) {
		super();
		this.userName = userName;
		this.userSurname = userSurname;
		this.displayName = displayName;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.personalEmail = personalEmail;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.phone = phone;
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

	/**@return the userSurname*/
	public String getUserSurname() {
		return userSurname;
	}

	/**
	 * @param userSurname the userSurname to set
	 */
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	/**@return the displayName*/
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**@return the personalEmail*/
	public String getPersonalEmail() {
		return personalEmail;
	}

	/**
	 * @param personalEmail the personalEmail to set
	 */
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	/**@return the secretQuestion*/
	public String getSecretQuestion() {
		return secretQuestion;
	}

	/**
	 * @param secretQuestion the secretQuestion to set
	 */
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	/**@return the secretAnswer*/
	public String getSecretAnswer() {
		return secretAnswer;
	}

	/**
	 * @param secretAnswer the secretAnswer to set
	 */
	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}
	/**@return the phone*/
	public String getPhone() {return phone;}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}

/**
 * 
 */
package pt.ual.mgi.detail;

import java.io.Serializable;


/**
 * Class that represents the user Account Detail
 * 
 * @author 20070337
 * @version 1.0	
 */
public class UserAccountDetail implements Serializable {

	private static final long serialVersionUID = 8146336192478978841L;
	
	private String id;
	private String username;
	private String name;
	private String number;
	private String email;
	private String phone;
	private String password;
	private String hintQuestion;
	private String hintAnswer;
	
	/**Base Constructor*/
	public UserAccountDetail() {super();}

	/**@return the id*/
	public String getId() {return id;}
	/**@return the username*/
	public String getUsername() {return username;}
	/**@return the number*/
	public String getNumber() {return number;}
	/**@return the email*/
	public String getEmail() {return email;}
	/**@return the phone*/
	public String getPhone() {return phone;}
	/**@return the password*/
	public String getPassword() {return password;}
	/**@return the hintQuestion*/
	public String getHintQuestion() {return hintQuestion;}
	/**@return the hintAnswer*/
	public String getHintAnswer() {return hintAnswer;}
	/**@return the name*/
	public String getName() {return name;}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param hintQuestion the hintQuestion to set
	 */
	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}
	/**
	 * @param hintAnswer the hintAnswer to set
	 */
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}

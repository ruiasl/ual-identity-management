/**
 * 
 */
package pt.ual.mgi.service.rest.resource;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Class that represents the user resource
 * 
 * @author 20070337
 * @version 1.0	
 */
@XmlRootElement
public class UserAccount implements Serializable {

	private static final long serialVersionUID = -6208232749424287554L;

	private String username;
	private String number;
	private String email;
	private String phone;
	private String password;
	private String hintQuestion;
	private String hintAnswer;
	
	/**Base Constructor*/
	public UserAccount() {super();}

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
}

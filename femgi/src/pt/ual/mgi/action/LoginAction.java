package pt.ual.mgi.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the login action for mgi frontend
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 8774068336054480666L;

	private Logger log = LoggerFactory.getLogger(LoginAction.class);
	
	private String exp;
	private String loginError;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
    	log.debug("Entering LoginAction...");
    	
		if(this.exp != null && this.exp.equals("true")) {
			log.debug("Session expired.");
			this.addActionError(this.getText("login.perform.alert.error"));
		}
    	
		if(this.loginError != null && this.loginError.equals("true")) {
			log.debug("Login Error.");
			this.addActionError(this.getText("login.invalid.error"));
		}
		
    	log.debug("Exiting LoginAction...");
        return SUCCESS;
    }

	/**@return the exp*/
	public String getExp() {return exp;}
	/**@return the loginError*/
	public String getLoginError() {return loginError;}
	/**@param exp the exp to set*/
	public void setExp(String exp) {this.exp = exp;}
	/**@param loginError the loginError to set*/
	public void setLoginError(String loginError) {this.loginError = loginError;}

}

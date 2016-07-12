package pt.ual.mgi.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.exception.UserNotFoundException;
import pt.ual.mgi.manager.user.IUserManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the manage password actions for mgi frontend
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
public class ManagePasswordAction extends BaseAction {

	private static final long serialVersionUID = 8774068336054480666L;

	private Logger log = LoggerFactory.getLogger(ManagePasswordAction.class);
	
	@Resource
	private IUserManager userManager;
	
	private UserAccountDetail userAccountDetail;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
    	log.debug("Entering ManagePasswordAction...");
    	
    	if(userAccountDetail.getUsername() == null || userAccountDetail.getUsername().isEmpty()){
    		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.username")}));
    	}
    	
    	try{
	    	log.debug("Check if user exists");
	    	this.userAccountDetail = this.userManager.getAccount(userAccountDetail.getUsername());
	    	
	    	if(this.userAccountDetail == null)
	    		this.addActionError(this.getText("form.general.error"));
	    	else {
		    	this.userAccountDetail.setEmail(null);
		    	this.userAccountDetail.setHintAnswer(null);
	    	}
    	} catch (UserNotFoundException e){
    		log.error("Error getting user not found: {}", e.getMessage());
    		this.addActionError(this.getText("user.not.found.error"));
    	} catch (BusinessMgiException e){
    		log.error("Error getting user: {}", e.getMessage());
    		this.addActionError(this.getText("form.general.error"));
    	} catch(Exception e){
    		log.error("Error recovering password: {}", e.getMessage());
    		this.addActionError(this.getText("form.general.error"));
    	}
    	
    	if(this.hasActionErrors())
    		return ERROR;
    	
        return SUCCESS;
    }
    
    /**
     * Method thar represents the action for reset the password
     * @return String
     */
    public String resetPassword(){
    	log.debug("Entering ManagePasswordAction.resetPassword...");
    	
    	if(userAccountDetail == null)
    		this.addActionError(this.getText("form.general.error"));
    	
    	if(userAccountDetail.getUsername() == null ||
    			userAccountDetail.getUsername().isEmpty())
    		this.addActionError(
    				this.getText("field.general.error", 
    						new String[]{this.getText("user.profile.username")}));
    	
    	if(userAccountDetail.getEmail() == null || 
    			userAccountDetail.getEmail().isEmpty())
    		this.addActionError(
    				this.getText("field.general.error", 
    						new String[]{this.getText("user.profile.email")}));
    	
    	if(userAccountDetail.getHintAnswer() == null ||
    				userAccountDetail.getEmail().isEmpty())
    		this.addActionError(
    				this.getText("field.general.error", 
    						new String[]{this.getText("user.profile.hintA")}));
    	
    	try{
    		this.userManager.forgotAccountPassword(this.userAccountDetail.getUsername(), 
    				this.userAccountDetail.getHintAnswer(), this.userAccountDetail.getEmail());
    	} catch(MgiException e){
    		log.error("Error recovering password: {}", e.getMessage());
    		this.addActionError(this.getText("form.general.error"));
    	} catch(Exception e){
    		log.error("Error recovering password: {}", e.getMessage());
    		this.addActionError(this.getText("form.general.error"));
    	}
    	
    	if(this.hasActionErrors())
    		return ERROR;
    	
    	this.addActionMessage("Foi enviado um email com a sua password para o seu endereço de confirmação");
    	return SUCCESS;
    }

	/**@return the userAccountDetail*/
	public UserAccountDetail getUserAccountDetail() {return userAccountDetail;}
	/**
	 * @param userAccountDetail the userAccountDetail to set
	 */
	public void setUserAccountDetail(UserAccountDetail userAccountDetail) {
		this.userAccountDetail = userAccountDetail;
	}
}

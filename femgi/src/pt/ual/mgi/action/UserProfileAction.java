package pt.ual.mgi.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.ldap.MgiUserDetails;
import pt.ual.mgi.manager.user.IUserManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the action for user profile
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
@Controller
public class UserProfileAction extends BaseAction {

	private static final long serialVersionUID = 8774068336054480666L;

	private static Logger log = LoggerFactory.getLogger(UserProfileAction.class);
	
	@Resource
	private IUserManager userManager;
	
	private UserAccountDetail userAccountDetail;	
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
    	log.debug("Entering UserProfileAction.execute...");
    	
    	try{
	    	log.debug("Getting useraccount Detail...");
	    	Object principalTemp = BaseAction.getPrincipal();
			if(principalTemp == null) {
				this.addActionError("Not Logged In");
				return ERROR;
			}

			MgiUserDetails principal = (MgiUserDetails)BaseAction.getPrincipal();
	    	
	    	this.userAccountDetail = this.userManager.getAccount(principal.getUserId());
    	} catch(MgiException e){
    		this.addActionError(e.getMessage());
    	} catch(Exception e){
    		this.addActionError(e.getMessage());
    	}
    	
    	log.debug("Exiting UserProfileAction.execute...");
    	if(this.hasActionErrors())
    		return ERROR;
    	
    	return SUCCESS;
    }

    /**
     * Method that performs profile update
     * 
     * @return String
     * @throws Exception
     */
    public String showSecProfile() throws Exception {
    	log.debug("Entering UserProfileAction.showSecProfile...");
    	
    	try{
	    	log.debug("Getting useraccount Detail...");
	    	Object principalTemp = BaseAction.getPrincipal();
			if(principalTemp == null) {
				this.addActionError("Not Logged In");
				return ERROR;
			}
    	} catch(Exception e){
    		this.addActionError(e.getMessage());
    	}
    	
    	MgiUserDetails principal = (MgiUserDetails)BaseAction.getPrincipal();
    	
    	this.userAccountDetail = new UserAccountDetail();
    	this.userAccountDetail.setId(principal.getUserId());
    	
    	log.debug("Exiting UserProfileAction.showSecProfile...");
    	if(this.hasActionErrors())
    		return ERROR;
    	
        return SUCCESS;
    }
    
    /**
     * Method that performs profile update
     * 
     * @return String
     * @throws Exception
     */
    public String update() throws Exception {
        log.debug("Entering UserProfileAction.update...");
    	
        Object principalTemp = BaseAction.getPrincipal();
		if(principalTemp == null) {
			this.addActionError("Not Logged In");
			return ERROR;
		}
        
		MgiUserDetails principal = (MgiUserDetails)BaseAction.getPrincipal();
		
        log.debug("Validate input parameters...");
        if(this.userAccountDetail == null)
        	this.addActionError(this.getText("user.profile.update.error"));
        else{
        	if(this.userAccountDetail.getName() == null || "".equals(this.userAccountDetail.getName()))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.name")}));
        	if(this.userAccountDetail.getEmail() == null || "".equals(this.userAccountDetail.getEmail()))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.email")}));
        	if(this.userAccountDetail.getPhone() == null || "".equals(this.userAccountDetail.getPhone()))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.phone")}));
        	if(!this.getPrincipalUsername().equals(this.userAccountDetail.getId()))
        		this.addActionError(this.getText("user.profile.update.error"));
        }
        
        if(this.hasActionErrors())
        	return ERROR;
        try{   
        	log.debug("Update the account detail...");
        	this.userAccountDetail.setId(principal.getUserId());
        	this.userAccountDetail = 
        			this.userManager.updateAccount(this.userAccountDetail);
        } catch(MgiException e){
        	log.error("Error updating user: {}", e.getMessage());
        	this.addActionError(this.getText("user.profile.update.error"));
        } catch(Exception e){
        	log.error("Error updating user: {}", e.getMessage());
        	this.addActionError(this.getText("user.profile.update.error"));
        }
        
        if(this.hasActionErrors())
        	return ERROR;
        
        this.addActionMessage(this.getText("user.profile.update.success"));
        
        log.debug("Exiting UserProfileAction.update...");
        return SUCCESS;
    }
    
    /**
     * Method that performs security profile update
     * 
     * @return String
     * @throws Exception
     */
    public String updateSecProfile() throws Exception {
        log.debug("Entering UserProfileAction.updateSecProfile...");
    	
        Object principalTemp = BaseAction.getPrincipal();
		if(principalTemp == null) {
			this.addActionError("Not Logged In");
			return ERROR;
		}
        
		MgiUserDetails principal = (MgiUserDetails)BaseAction.getPrincipal();
        
        log.debug("Validate input parameters...");
        if(this.userAccountDetail == null)
        	this.addActionError(this.getText("user.profile.update.error"));
        else{
        	if(this.oldPassword == null || "".equals(this.oldPassword))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.old.password")}));
        	if(this.newPassword == null || "".equals(this.newPassword))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.new.password")}));
        	if(this.confirmPassword == null || "".equals(this.confirmPassword))
        		this.addActionError(this.getText("field.general.error", new String[]{this.getText("user.profile.confirm.password")}));
        }
        
        if(this.hasActionErrors())
        	return ERROR;
        try{   
        	log.debug("Update the account detail...");
        	this.userAccountDetail.setId(principal.getUserId());
        	this.userManager.resetAccountPassword(
        			this.getPrincipalUsername(), this.oldPassword, this.newPassword);
        } catch(MgiException e){
        	log.error("Error updating user: {}", e.getMessage());
        	this.addActionError(this.getText("user.profile.update.error"));
        } catch(Exception e){
        	log.error("Error updating user: {}", e.getMessage());
        	this.addActionError(this.getText("user.profile.update.error"));
        }
        
        if(this.hasActionErrors())
        	return ERROR;
        
        this.addActionMessage(this.getText("user.profile.update.success"));
        
        log.debug("Exiting UserProfileAction.update...");
        return SUCCESS;
    }
    
	/**@return the userAccountDetail*/
	public UserAccountDetail getUserAccountDetail() {return userAccountDetail;}
	/**@return the oldPassword*/
	public String getOldPassword() {return oldPassword;}
	/**@return the newPassword*/
	public String getNewPassword() {return newPassword;}
	/**@return the confirmPassword*/
	public String getConfirmPassword() {return confirmPassword;}
	
	/**
	 * @param userAccountDetail the userAccountDetail to set
	 */
	public void setUserAccountDetail(UserAccountDetail userAccountDetail) {
		this.userAccountDetail = userAccountDetail;
	}
	/**@param oldPassword the oldPassword to set*/
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**@param newPassword the newPassword to set*/
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**@param confirmPassword the confirmPassword to set*/
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}

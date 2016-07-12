package pt.ual.mgi.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.manager.user.IUserManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the logout action for mgi frontend
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = 4831570629321197162L;

	private Logger log = LoggerFactory.getLogger(LogoutAction.class);
	
	@Resource
	private IUserManager userManager;
	
	private UserAccountDetail userAccountDetail;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
    	log.debug("Entering LogoutAction...");
    	
    	this.addActionMessage(this.getText("logout.success"));
    		
    	log.debug("Exiting LogoutAction...");
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

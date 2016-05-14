package pt.ual.mgi.action;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.integration.dao.IUserDao;
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
public class UserProfileAction extends ActionSupport {

	private static final long serialVersionUID = 8774068336054480666L;

	@Resource
	private IUserManager userManager;
	
	private UserAccountDetail userAccountDetail;	
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
    	this.userAccountDetail = this.userManager.getAccount("ola");
    	return SUCCESS;
    }

    /**
     * Method that performs profile update
     * 
     * @return String
     * @throws Exception
     */
    public String update() throws Exception {
        
        return SUCCESS;
    }
    
    /**
     * Method that performs profile update
     * 
     * @return String
     * @throws Exception
     */
    public String forgotPwd() throws Exception {
        this.userAccountDetail = new UserAccountDetail();
        this.userAccountDetail.setEmail("ruiaslopes@gmail.com");
        this.userAccountDetail.setHintQuestion("Qual o teu cão?");
        return SUCCESS;
    }
    
    /**
     * Method that performs profile update
     * 
     * @return String
     * @throws Exception
     */
    public String recoverPwd() throws Exception {
        this.addActionMessage("password enviada para o seu email!");
    	
        return SUCCESS;
    }
    
	/**
	 * @return the userAccountDetail
	 */
	public UserAccountDetail getUserAccountDetail() {
		return userAccountDetail;
	}

	/**
	 * @param userAccountDetail the userAccountDetail to set
	 */
	public void setUserAccountDetail(UserAccountDetail userAccountDetail) {
		this.userAccountDetail = userAccountDetail;
	}

//	/**
//	 * @param userDao the userDao to set
//	 */
//	@Resource
//	public void setUserDao(IUserDao userDao) {
//		this.userDao = userDao;
//	}
}

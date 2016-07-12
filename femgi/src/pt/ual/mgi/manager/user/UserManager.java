package pt.ual.mgi.manager.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.ual.mgi.converter.user.UserAccountDetailToUserAccountConverter;
import pt.ual.mgi.converter.user.UserAccountToDetailConverter;
import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.exception.BusinessMgiException;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.integration.dao.IUserDao;
import pt.ual.mgi.integration.client.model.UserAccount;

/**
 * Class that represents the manager that executes operations on use
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see IUserManager
 */
@Service
public class UserManager implements IUserManager {

	private Logger log = LoggerFactory.getLogger(UserManager.class);
	
	@Resource
	private IUserDao userDao;

	@Resource
	private UserAccountToDetailConverter userAccountToDetailConverter;
	
	@Resource
	private UserAccountDetailToUserAccountConverter userAccountDetailToUserAccountConverter;
	
	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * createAccount(pt.ual.mgi.detail.UserAccountDetail)
	 */
	@Override
	public UserAccountDetail createAccount(UserAccountDetail userAccountDetail) {
		log.debug("Entering UserManager.createAccount...");
		
		log.debug("Validate input paramaters...");
		if(userAccountDetail == null)
			throw new IllegalArgumentException("Parameter userAccountDetail is mandatory");
		
		log.debug("Convert detail for userAccount...");
		UserAccount userAccount = 
				this.userAccountDetailToUserAccountConverter.convert(userAccountDetail);
		
		log.debug("Create the userAccount...");
		userAccount = this.userDao.createAccount(userAccount);
		
		log.debug("Convert userAccount for detail...");
		userAccountDetail = 
				this.userAccountToDetailConverter.convert(userAccount);
		
		log.debug("Exiting UserManager.createAccount...");
		return userAccountDetail;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * getAccount(java.lang.String)
	 */
	@Override
	public UserAccountDetail getAccount(String id) 
			throws MgiException, BusinessMgiException, IllegalArgumentException {
		log.debug("Entering UserManager.getAccount...");
		
		if(id == null || "".equals(id) )
			throw new IllegalArgumentException("Parameter id is mandatory");
		
		log.debug("Getting the userAccount...");
		UserAccount userAccount = this.userDao.getAccount(id);
		
		UserAccountDetail userAccountDetail = 
				this.userAccountToDetailConverter.convert(userAccount);
		
		log.debug("Exiting UserManager.getAccount...");
		return userAccountDetail;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * updateAccount(pt.ual.mgi.detail.UserAccountDetail)
	 */
	@Override
	public UserAccountDetail updateAccount(UserAccountDetail userAccountDetail) throws MgiException {
		log.debug("Entering UserManager.updateAccount...");
		
		log.debug("Validate input paramaters...");
		if(userAccountDetail == null)
			throw new IllegalArgumentException("Parameter userAccountDetail is mandatory");
		
		log.debug("Convert detail for userAccount...");
		UserAccount userAccount = 
				this.userAccountDetailToUserAccountConverter.convert(userAccountDetail);
		
		log.debug("Update the userAccount...");
		userAccount = this.userDao.updateAccount(userAccount);
		
		log.debug("Convert userAccount for detail...");
		userAccountDetail = 
				this.userAccountToDetailConverter.convert(userAccount);
		
		log.debug("Exiting UserManager.updateAccount...");
		return userAccountDetail;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String id) throws MgiException {
		log.debug("Entering UserManager.deleteAccount...");
		
		if(id == null || "".equals(id) )
			throw new IllegalArgumentException("Parameter id is mandatory");
		
		log.debug("Delete the userAccount...");
		this.userDao.deleteAccount(id);
		
		log.debug("Exiting UserManager.deleteAccount...");
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * resetAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd) throws MgiException {
		log.debug("Entering UserManager.resetAccountPassword...");
		if(id == null || "".equals(id) || 
				oldPwd == null || "".equals(oldPwd) || 
						newPwd == null || "".equals(newPwd))
			throw new IllegalArgumentException("Parameter username, oldPwd and newPwd are mandatory");
		
		log.debug("Exiting UserManager.resetAccountPassword...");
		return this.userDao.resetAccountPassword(id, oldPwd, newPwd);
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * forgotAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean forgotAccountPassword(String id, String hintAnswer, String email) throws MgiException {
		log.debug("Entering UserManager.forgotAccountPassword...");
		if(id == null || "".equals(id) || 
				hintAnswer == null || "".equals(hintAnswer) || 
						email == null || "".equals(email))
			throw new IllegalArgumentException("Parameter username, email and email are mandatory");
		
		log.debug("Exiting UserManager.forgotAccountPassword...");
		return this.userDao.forgotAccountPassword(id, hintAnswer, email);
	}
}

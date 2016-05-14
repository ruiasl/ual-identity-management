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
import pt.ual.mgi.integration.detail.user.UserAccount;

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
		// TODO Auto-generated method stub
		return null;
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
	public UserAccountDetail updateAccount(UserAccountDetail userAccountDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * deleteAccount(java.lang.String)
	 */
	@Override
	public void deleteAccount(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * resetAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean resetAccountPassword(String id, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see pt.ual.mgi.manager.user.IUserManager#
	 * forgotAccountPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean forgotAccountPassword(String id, String hintAnswer,
			String email) {
		// TODO Auto-generated method stub
		return false;
	}
}

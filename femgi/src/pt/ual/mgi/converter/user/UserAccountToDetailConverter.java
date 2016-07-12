package pt.ual.mgi.converter.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.integration.client.model.UserAccount;

/**
 * Class that converts an useraccount in an useraccountdetail
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see Converter
 */
@Component
public class UserAccountToDetailConverter implements Converter<UserAccount, UserAccountDetail> {

	private Logger log = LoggerFactory.getLogger(UserAccountToDetailConverter.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public UserAccountDetail convert(UserAccount userAccount) {
		log.debug("Entering UserAccountToDetailConverter.convert...");
		
		if(userAccount == null)
			return null;
		
		UserAccountDetail userAccountDetail = new UserAccountDetail();
		
		userAccountDetail.setEmail(userAccount.getEmail());
		userAccountDetail.setName(userAccount.getName());
		userAccountDetail.setHintAnswer(userAccount.getHintAnswer());
		userAccountDetail.setHintQuestion(userAccount.getHintQuestion());
		userAccountDetail.setId(userAccount.getId());
		userAccountDetail.setNumber(userAccount.getNumber());
		userAccountDetail.setPassword(userAccount.getPassword());
		userAccountDetail.setPhone(userAccount.getPhone());
		userAccountDetail.setUsername(userAccount.getUsername());
		
		log.debug("Entering UserAccountToDetailConverter.convert...");
		return userAccountDetail;
	}
}

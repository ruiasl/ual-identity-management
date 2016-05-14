package pt.ual.mgi.converter.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pt.ual.mgi.detail.UserAccountDetail;
import pt.ual.mgi.integration.detail.user.UserAccount;

/**
 * Class that converts an useraccountdetail to useraccount
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see Converter
 */
@Component
public class UserAccountDetailToUserAccountConverter implements Converter<UserAccountDetail, UserAccount> {

	private Logger log = LoggerFactory.getLogger(UserAccountDetailToUserAccountConverter.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public UserAccount convert(UserAccountDetail userAccountDetail) {
		log.debug("Entering UserAccountDetailToUserAccountConverter.convert...");
		
		if(userAccountDetail == null)
			return null;
		
		UserAccount userAccount = new UserAccount();
		
		userAccount.setEmail(userAccountDetail.getEmail());
		userAccount.setName(userAccountDetail.getName());
		userAccount.setHintAnswer(userAccountDetail.getHintAnswer());
		userAccount.setHintQuestion(userAccountDetail.getHintQuestion());
		userAccount.setId(userAccountDetail.getId());
		userAccount.setNumber(userAccountDetail.getNumber());
		userAccount.setPassword(userAccountDetail.getPassword());
		userAccount.setPhone(userAccountDetail.getPhone());
		userAccount.setUsername(userAccountDetail.getUsername());
		
		log.debug("Entering UserAccountDetailToUserAccountConverters.convert...");
		return userAccount;
	}
}

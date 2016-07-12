package pt.ual.mgi.converter.user;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pt.ual.mgi.detail.useraccount.UserAccountDetail;
import pt.ual.mgi.service.rest.resource.UserAccount;

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
		
		userAccountDetail.setPersonalEmail(userAccount.getEmail());
		userAccountDetail.setUserEmail(userAccount.getUsername());
		userAccountDetail.setPhone(userAccount.getPhone());
		userAccountDetail.setUserName(userAccount.getName());
		userAccountDetail.setDisplayName(this.getNameFromName(userAccount.getName()));
		userAccountDetail.setUserSurname(this.getSurnameFromName(userAccount.getName()));
		userAccountDetail.setSecretAnswer(userAccount.getHintAnswer());
		userAccountDetail.setSecretQuestion(userAccount.getHintQuestion());
		userAccountDetail.setUserId(userAccount.getId());
		userAccountDetail.setUserPassword(userAccount.getPassword());
		
		log.debug("Entering UserAccountToDetailConverter.convert...");
		return userAccountDetail;
	}
	
	/**
	 * Getting surname From name
	 * @param name
	 * @return String
	 */
	private String getSurnameFromName(String name){
		log.debug("Entering UserAccountToDetailConverter.getSurnameFromName...");
		if(name == null)
			return "";
		
		StringTokenizer tokenizer = new StringTokenizer(name, " ");
		String surname = null;
		
		while(tokenizer.hasMoreElements()){
			surname = tokenizer.nextToken();
		}
		return surname;
	}
	
	/**
	 * Get first name from name
	 * @param name
	 * @return String
	 */
	private String getNameFromName(String name){
		log.debug("Entering UserAccountToDetailConverter.getNameFromName...");
		if(name == null)
			return "";
		
		StringTokenizer tokenizer = new StringTokenizer(name, " ");
		return tokenizer.nextToken();
	}
}

package pt.ual.mgi.ldap;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

/**
 * Class that represents the MGI user details mapper
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see LdapUserDetailsMapper
 */
public class MgiUserDetailsMapper extends LdapUserDetailsMapper {

	private Logger log = LoggerFactory.getLogger(MgiUserDetailsMapper.class);
	
    protected String[] roleAttributes = null;

    /*
     * (non-Javadoc)
     * @see org.springframework.security.ldap.userdetails.LdapUserDetailsMapper#
     * mapUserFromContext(org.springframework.ldap.core.DirContextOperations, 
     * java.lang.String, java.util.Collection)
     */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx_,
			String username, Collection<? extends GrantedAuthority> authorities) {
		
		String dn = ctx_.getNameInNamespace();

        log.debug("Mapping user details from context with DN: {}", dn);
		MgiUserDetails.MgiEssence mgiEssence = new MgiUserDetails.MgiEssence();
		mgiEssence.setDn(dn);
		
        Object passwordValue = ctx_.getObjectAttribute("userpassword");

        if (passwordValue != null) {
        	mgiEssence.setPassword(this.mapPassword(passwordValue));
        }
        
        mgiEssence.setUsername(username);        
        mgiEssence.setUserId(username);
        
        Object displayname = ctx_.getObjectAttribute("displayname");
        if (displayname != null) {
        	mgiEssence.setUserName(this.mapPassword(displayname));
        }
        
        // Map the roles
        for (int i = 0; (roleAttributes != null) && (i < roleAttributes.length); i++) {
            String[] rolesForAttribute = ctx_.getStringAttributes(roleAttributes[i]);

            if (rolesForAttribute == null) {
                log.debug("Couldn't read role attribute '{}' for user {}", roleAttributes[i], dn);
                continue;
            }
            for (String roleAttr : rolesForAttribute) {
                GrantedAuthority authority = createAuthority(roleAttr);
                if (authority != null) {
                	mgiEssence.addAuthority(authority);
                }
            }
        }

        // Add the supplied authorities
        for (GrantedAuthority authority : authorities) {
        	mgiEssence.addAuthority(authority);
        }
        
        //Allways add this site ROLE - LDAP does not have role
        GrantedAuthority authority = createAuthority("SITE_USER");
        if (authority != null) {
        	mgiEssence.addAuthority(authority);
        }

        MgiUserDetails details = (MgiUserDetails)mgiEssence.createUserDetails();
        
        return details;
	}
}

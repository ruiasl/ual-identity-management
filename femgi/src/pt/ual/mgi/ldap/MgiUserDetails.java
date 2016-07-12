package pt.ual.mgi.ldap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.util.Assert;

/**
 * Class that contains the principal attributes for the MGI
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see LdapUserDetails
 */
public class MgiUserDetails implements LdapUserDetails {

	private static final long serialVersionUID = -6724133823050522610L;
	
	private String userId;
    private String dn;
    private String password;
    private String username;
    private String userName;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
    private Attributes attributes = new BasicAttributes();
    protected List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;    
    
	/** Constructor */
	public MgiUserDetails() { super(); }
	
	/** @return the userId*/
	public String getUserId() {return userId;}	
	/** @return the attributes*/
	public Attributes getAttributes() {return attributes;}
	/** @return the dn*/
	public String getDn() {return dn;}
	/** @return the password*/
	public String getPassword() {return password;}
	/** @return the username*/
	public String getUsername() {return username;}
	public String getUserName() {return userName;}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {return this.accountNonExpired;}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {return this.accountNonLocked;}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {return this.credentialsNonExpired;}
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {return this.enabled;}

	/** @return the authorities*/
	public List<GrantedAuthority> getAuthorities() {return authorities;}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {this.userId = userId;}
		
	/**
	 * Method that adds a GrantedAuthority to the principal
	 * 
	 * @param auth_
	 */
    public void addAuthority(GrantedAuthority auth_) {
        if(!this.hasAuthority(auth_)) {
        	List<GrantedAuthority> lstAuthoritiesTemp = this.authorities;
        	List<GrantedAuthority> lstAuthorities = new ArrayList<GrantedAuthority>(lstAuthoritiesTemp);
        	lstAuthorities.add(auth_);
            this.authorities = lstAuthorities;
        }
    }
	
    /**
     * Method that verifies if a specific GrantedAuthority already exists in the principal
     * 
     * @param grantedAuthority_
     * @return boolean
     */
    private boolean hasAuthority(GrantedAuthority grantedAuthority_) {
        Iterator<GrantedAuthority> authorities = this.authorities.iterator();

        while(authorities.hasNext()) {
            GrantedAuthority authority = authorities.next();
            if(authority.equals(grantedAuthority_)) {
                return true;
            }
        }
        return false;
    }
    
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.userdetails.ldap.LdapUserDetailsImpl#toString()
	 */
	@Override
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Username: ").append(this.getUsername()).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

        if (this.getAuthorities() != null) {
            sb.append("Granted Authorities: ");
            int idx = 0;
            for(GrantedAuthority authority : this.getAuthorities()) {
            	if (idx > 0) 
                    sb.append(", ");
                
            	sb.append(authority.toString());
            	idx++;
            }
        } else {
            sb.append("Not granted any authorities");
        }
        return sb.toString();        
    }

	/**
	 * Inner class to access the user detail core properties
	 * 
	 * @author 20070337
	 * @version 1.0
	 */
	public static class MgiEssence {
		protected MgiUserDetails instance = (MgiUserDetails)createTarget();
		protected List<GrantedAuthority> mutableAuthorities = new ArrayList<GrantedAuthority>();
		
		/**
		 * Constructor
		 */
		public MgiEssence() { }
		
		/**
		 * Method that created a User details object specific
		 * 
		 * @return LdapUserDetails
		 */
		protected LdapUserDetails createTarget() {
			return new MgiUserDetails();
		}

		/**
		 * Method that created a User details object specific
		 * 
		 * @return LdapUserDetails
		 */
		public LdapUserDetails createUserDetails() {
            Assert.notNull(instance, "Essence can only be used to create a single instance");
            Assert.notNull(instance.username, "username must not be null");
            Assert.notNull(instance.getDn(), "Distinguished name must not be null");
			
            instance.authorities = getGrantedAuthorities();

            LdapUserDetails newInstance = instance;

            instance = null;

			return newInstance;
		}
		
		/** Adds the authority to the list, unless it is already there, in which case it is ignored 
		 * @param a GrantedAuthority */
        public void addAuthority(GrantedAuthority a) {
            if(!hasAuthority(a)) {
                mutableAuthorities.add(a);
            }
        }

        /**
         * Method that verifies if a specific GrantedAuthority already exists in the principal
         * 
         * @param a
         * @return boolean
         */
        private boolean hasAuthority(GrantedAuthority a) {
            Iterator<GrantedAuthority> authorities = mutableAuthorities.iterator();

            while(authorities.hasNext()) {
                GrantedAuthority authority = authorities.next();
                if(authority.equals(a)) {
                    return true;
                }
            }
            return false;
        }

        /** 
         * @return GrantedAuthority[]
         */
        public List<GrantedAuthority> getGrantedAuthorities() {
            return mutableAuthorities;
        }
        /** 
         * @param attributes
         */
        public void setAttributes(Attributes attributes) {
            instance.attributes = attributes;
        }
        /** 
         * @param authorities
         */
        public void setAuthorities(GrantedAuthority[] authorities) {
            mutableAuthorities = new ArrayList<GrantedAuthority>(Arrays.asList(authorities));
        }
        /** 
         * @param dn
         */
        public void setDn(String dn) {instance.dn = dn;}
        /** 
         * @param dn
         */
        public void setDn(Name dn) {instance.dn = dn.toString();}
        /** 
         * @param password
         */
        public void setPassword(String password) {instance.password = password;}
        /** 
         * @param username
         */
        public void setUsername(String username) {instance.username = username;}
        /** 
         * @param accountNonExpired
         */
        public void setAccountNonExpired(boolean accountNonExpired) {
            instance.accountNonExpired = accountNonExpired;
        }
        /** 
         * @param accountNonLocked
         */
        public void setAccountNonLocked(boolean accountNonLocked) {
            instance.accountNonLocked = accountNonLocked;
        }
        /** 
         * @param credentialsNonExpired
         */
        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
            instance.credentialsNonExpired = credentialsNonExpired;
        }
        /** 
         * @param enabled
         */
        public void setEnabled(boolean enabled) {instance.enabled = enabled;}
        /** 
         * @param userId
         */
        public void setUserId(String userId) {instance.userId = userId;}
        /** 
         * @param userName
         */
        public void setUserName(String userName) {instance.userName = userName;}
	}
}

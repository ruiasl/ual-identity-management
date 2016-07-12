package pt.ual.mgi.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class that is responsible for Auth process filter
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see UsernamePasswordAuthenticationFilter
 */
public class MgiAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private Logger log = LoggerFactory.getLogger(MgiAuthenticationProcessingFilter.class);

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#
	 * attemptAuthentication(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
						HttpServletResponse response) throws AuthenticationException{
		log.debug("Entering MgiAuthenticationProcessingFilter.attemptAuthentication()");
		log.debug("Do the authentication procedures.");
		return super.attemptAuthentication(request, response);
	}	
}

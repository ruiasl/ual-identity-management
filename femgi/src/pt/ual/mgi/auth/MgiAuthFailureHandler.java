package pt.ual.mgi.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class that is responsible for auth failure procedures 
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see SimpleUrlAuthenticationFailureHandler
 */
public class MgiAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger log = LoggerFactory.getLogger(MgiAuthFailureHandler.class);
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#
	 * onAuthenticationFailure(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse, 
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
											throws IOException, ServletException {
		log.debug("Entering MgiAuthFailureHandler.onAuthenticationFailure()");

		String username = request.getParameter(
				UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY );
		
		if(exception instanceof LockedException)
			log.error("Conta do username {} Bloqueada...", username);
		else if(exception instanceof AccountExpiredException)
			log.error("Conta do username {} Expirada...", username);
		else if(exception instanceof CredentialsExpiredException)
			log.error("Credenciais do username {} Expiradas...", username);
		else if(exception instanceof UsernameNotFoundException)
			log.error("Utilizador {} não existe...", username);
		
		log.debug("Exiting MgiAuthFailureHandler.onAuthenticationFailure()");
		super.onAuthenticationFailure(request, response, exception);
	}
}

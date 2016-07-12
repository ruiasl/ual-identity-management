package pt.ual.mgi.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * Class that is responsible auth success procedures
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see SimpleUrlAuthenticationSuccessHandler
 */
public class MgiAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger(MgiAuthSuccessHandler.class);	

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#
	 * onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse, 
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
										throws IOException, ServletException {
		log.debug("Entering MgiAuthSuccessHandler.onAuthenticationSuccess()");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

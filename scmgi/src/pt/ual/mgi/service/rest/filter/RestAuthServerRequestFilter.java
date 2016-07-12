/**
 * 
 */
package pt.ual.mgi.service.rest.filter;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import pt.ual.mgi.annotation.Authentication;

/**
 * Class that is responsible for rest auth container filter
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see ContainerRequestFilter
 */
@Provider
@Authentication
public class RestAuthServerRequestFilter extends SpringBeanAutowiringSupport implements ContainerRequestFilter {

	private Logger log;
	private static final String AUTHORIZATION_PREFIX_VALUE = "RestAuth ";
	
	@Autowired
	private Properties restServerProperties;
	
	/**
	 * Constructor 
	 */
	public RestAuthServerRequestFilter() {
		this.log = LoggerFactory.getLogger(RestAuthServerRequestFilter.class);
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#
	 * filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		log.debug("Entering RestAuthServerRequestFilter.filter...");
		ContainerRequest request = (ContainerRequest) containerRequestContext;
		String authentication = request.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (authentication == null || authentication.isEmpty())
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		
		if (!(authentication.startsWith(AUTHORIZATION_PREFIX_VALUE)))
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		
		StringBuilder authBuilder = new StringBuilder(AUTHORIZATION_PREFIX_VALUE);
		authBuilder.append(this.restServerProperties.get("scmgi.rest.server.auth.key"));
		
		if(!authentication.equals(authBuilder.toString()))
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		
		log.debug("Exiting RestAuthServerRequestFilter.filter...");
	}
}

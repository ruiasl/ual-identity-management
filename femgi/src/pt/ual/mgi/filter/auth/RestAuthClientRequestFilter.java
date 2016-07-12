package pt.ual.mgi.filter.auth;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Class that represents the rest client auth filter
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ClientRequestFilter
 */
@Component
public class RestAuthClientRequestFilter extends SpringBeanAutowiringSupport implements ClientRequestFilter {

	private Logger log;
	
	private static final String AUTHORIZATION_PREFIX_VALUE = "RestAuth ";
	
	@Autowired
	private Properties restClientProperties;
	
	/**
	 * Constructor
	 */
	public RestAuthClientRequestFilter() {
		this.log = LoggerFactory.getLogger(RestAuthClientRequestFilter.class);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.client.ClientRequestFilter#
	 * filter(javax.ws.rs.client.ClientRequestContext)
	 */
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		this.log.debug("Entering RestAuthClientRequestFilter.filter...");
		StringBuilder authBuilder = new StringBuilder(AUTHORIZATION_PREFIX_VALUE);
		authBuilder.append(this.restClientProperties.get("scmgi.rest.client.auth.key"));
		requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION,authBuilder.toString());
		this.log.debug("Exiting RestAuthClientRequestFilter.filter...");
	}

}

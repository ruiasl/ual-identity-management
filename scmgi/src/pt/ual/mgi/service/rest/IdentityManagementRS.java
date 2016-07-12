package pt.ual.mgi.service.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import pt.ual.mgi.annotation.Authentication;
import pt.ual.mgi.converter.user.UserAccountDetailToUserAccountConverter;
import pt.ual.mgi.converter.user.UserAccountToDetailConverter;
import pt.ual.mgi.detail.messaging.ForgotPasswordTemplateMessage;
import pt.ual.mgi.detail.messaging.ResetPasswordTemplateMessage;
import pt.ual.mgi.exception.MgiException;
import pt.ual.mgi.manager.ldap.ILdapManager;
import pt.ual.mgi.manager.messaging.IMessagingManager;
import pt.ual.mgi.service.rest.resource.UserAccount;

/**
 * Class that represents the service responsible for Identity Management
 * 
 * @author 20070337
 * @version 1.0
 */
@Api(value = "/identities")
@Produces(MediaType.APPLICATION_JSON)
@Path("/identities")
@Authentication
public class IdentityManagementRS extends SpringBeanAutowiringSupport{

	private static Logger log = LoggerFactory.getLogger(IdentityManagementRS.class);
	
	@Autowired
	private IMessagingManager messagingManager;
	
	@Autowired
	private ILdapManager ldapManager;
	
	@Autowired
	private UserAccountDetailToUserAccountConverter userAccountDetailToUserAccountConverter;
	
	@Autowired
	private UserAccountToDetailConverter userAccountToDetailConverter;
	
	@Autowired
	private Properties emailProperties;
	
	/**
	 * Method that create an Account
	 * 
	 * @param id
	 * @return Response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts")
	@ApiOperation(value = "Cria Conta", notes = "Criar uma conta de utilizador")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Conta Criada", response = UserAccount.class)})
	public Response createAccount(@ApiParam("userAccount") UserAccount userAccount) {
		log.debug("Entering IdentityManagementRS.createAccount...");
		
		log.debug("Validate inputs...");
		if(!this.isValidUserAccount(userAccount))
			return Response.status(Status.BAD_REQUEST).build();
		
		log.debug("Create an Account...");
		try {
			userAccount =  
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.createEntry(this.userAccountToDetailConverter.convert(userAccount)));
		
			log.debug("Exiting IdentityManagementRS.createAccount...");
			return Response.ok(userAccount).build();
		} catch (Exception e) {
			log.error("Error creating account...");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that get an Account for a received ID 
	 * 
	 * @param id
	 * @return Response
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts/{id}")
	@ApiOperation(value = "Obtem Conta", notes = "Obter uma conta de utilizador atraves do seu identificador de utilizador.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "UserAccount", response = UserAccount.class),
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response getAccount(@ApiParam("id") @PathParam("id") String id) {
		log.debug("Entering IdentityManagementRS.getAccount...");
		
		log.debug("Validate Input parameters...");
		try {
			UserAccount userAccount = 
					this.userAccountDetailToUserAccountConverter.convert(
									this.ldapManager.getUserAccountEntry(id));
			
			if(userAccount == null)
				return Response.status(Status.NOT_FOUND).build();
			
			log.debug("Exiting IdentityManagementRS.getAccount...");
			return Response.ok(userAccount).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/**
	 * Method that delete an Account for a received ID
	 * 
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("/accounts/{id}")
	@ApiOperation(value = "Remove Conta", notes = "REmover uma conta de utilizador atraves do seu identificador de utilizador.")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Conta Removida"),
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response deleteAccount(@ApiParam("id") @PathParam("id") String id) {
		log.debug("Entering IdentityManagementRS.deleteAccount...");
		
		try {
			log.debug("Validate if user exists...");
			if(this.ldapManager.getUserAccountEntry(id) == null)
				return Response.status(Status.NOT_FOUND).build();
			
			log.debug("Delete entry...");
			this.ldapManager.deleteEntry(id);
			
			log.debug("Exiting IdentityManagementRS.deleteAccount...");
			return Response.noContent().build();
		} catch (Exception e) {
			log.error("Error deleting account");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that delete an Account for a received ID
	 * 
	 * @param id
	 * @return Response
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts/{id}")
	@ApiOperation(value = "Actualiza UserAccount", notes = "Atualiza uma conta de utilizador.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "UserAccount actualizada", response = UserAccount.class), 
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response updateAccount(@ApiParam("id") @PathParam("id") String id, @ApiParam("userAccount") UserAccount userAccount) {
		log.debug("Entering IdentityManagementRS.updateAccount...");
		
		log.debug("Validate inputs...");
		if(userAccount == null || !userAccount.getId().equals(id)) 
			return Response.status(Status.BAD_REQUEST).build();
		
		try {
			log.debug("Validate if user exists...");
			if(this.ldapManager.getUserAccountEntry(id) == null)
				return Response.status(Status.NOT_FOUND).build();
			
			userAccount =  
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.updateEntry(this.userAccountToDetailConverter.convert(userAccount)));
			
			log.debug("Exiting IdentityManagementRS.updateAccount...");
			return Response.ok(userAccount).build();
		} catch (Exception e) {
			log.error("Error deleting account");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that resets an account password
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * 
	 * @return Response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts/{id}/resetpassword")
	@ApiOperation(value = "Reset da Password", notes = "Efetuar Reset da Password.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Reset da Password efetuada"), 
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response resetAccountPassword(@ApiParam("id") @PathParam("id") String id, 
						@QueryParam("oldPassword") String oldPassword, 
							@QueryParam("newPassword") String newPassword) {
		log.debug("Entering IdentityManagementRS.resetAccountPassword...");

		try {
			if(oldPassword == null || "".equals(oldPassword) 
					|| newPassword == null || "".equals(newPassword))
				return Response.status(Status.BAD_REQUEST).build();
			
			UserAccount userAccount = 
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.getUserAccountEntry(id));

			if(userAccount == null)
				return Response.status(Status.NOT_FOUND).build();

			userAccount.setPassword(newPassword);
			
			userAccount =  
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.updateEntry(this.userAccountToDetailConverter.convert(userAccount)));
			
			ResetPasswordTemplateMessage message = 
					new ResetPasswordTemplateMessage(userAccount.getEmail(), newPassword);
			message.setMessageFromAddress(this.emailProperties.getProperty("emailProperties"));

			this.messagingManager.sendMessage(message, true);
			log.debug("Exiting IdentityManagementRS.resetAccountPassword...");
			return Response.ok().build();
		} catch (MgiException e) {
			log.error("Error reseting the password");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			log.error("Error reseting the password");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that sends an email with password forgotten
	 * 
	 * @param id
	 * @param hintAnswer
	 * @param email
	 * 
	 * @return Response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts/{id}/forgotpassword")
	@ApiOperation(value = "Recuperacao da Password", notes = "Efetuar Recuperacao da Password.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperacao da Password efetuada"), 
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response forgotAccountPassword(@ApiParam("id") @PathParam("id") String id, 
			@QueryParam("hintAnswer") String hintAnswer, @QueryParam("email") String email) {
		log.debug("Entering IdentityManagementRS.forgotAccountPassword...");
		
		try {
			log.debug("Validate inputs...");
			if(hintAnswer == null || "".equals(hintAnswer) || email == null || "".equals(email))
				return Response.status(Status.BAD_REQUEST).build();
			
			UserAccount userAccount = 
					this.userAccountDetailToUserAccountConverter.convert(
									this.ldapManager.getUserAccountEntry(id));
			
			if(userAccount == null)
				return Response.status(Status.NOT_FOUND).build();
		
			log.debug("Validate forgot password data...");
			if(!hintAnswer.equals(userAccount.getHintAnswer()) || !email.equals(userAccount.getEmail()))
				return Response.status(Status.BAD_REQUEST).build();
			
			log.debug("Generate a password...");
			String password = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
			userAccount.setPassword(password);
			
			userAccount =  
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.updateEntry(this.userAccountToDetailConverter.convert(userAccount)));
			
			ForgotPasswordTemplateMessage message = 
					new ForgotPasswordTemplateMessage(userAccount.getEmail(), password);
			message.setMessageFromAddress(this.emailProperties.getProperty("emailProperties"));
			
			this.messagingManager.sendMessage(message, true);
			
			log.debug("Exiting IdentityManagementRS.forgotAccountPassword...");
			return Response.ok().build();
		} catch (MgiException e) {
			log.error("Error recovering the password");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			log.error("Error recovering the password");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that moves and UserAccount ot another ou
	 * 
	 * @param id
	 * @param destination
	 * 
	 * @return Response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/accounts/{id}/move")
	@ApiOperation(value = "Move utilizador", notes = "Move o utilizador na estrutura Ldap.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Reset da Password efetuada"), 
							@ApiResponse(code = 404, message = "UserAccount nao encontrada") })
	public Response move(@ApiParam("id") @PathParam("id") String id, 
						@QueryParam("destination") String destination) {
		log.debug("Entering IdentityManagementRS.move...");

		try {
			if(destination == null || "".equals(destination))
				return Response.status(Status.BAD_REQUEST).build();
			
			UserAccount userAccount = 
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.getUserAccountEntry(id));

			if(userAccount == null)
				return Response.status(Status.NOT_FOUND).build();

			userAccount =  
					this.userAccountDetailToUserAccountConverter.convert(
							this.ldapManager.moveEntry(this.userAccountToDetailConverter.convert(userAccount), destination));
			
			log.debug("Exiting IdentityManagementRS.move...");
			return Response.ok().build();
		} catch (MgiException e) {
			log.error("Error movinguser Account");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			log.error("Error movinguser Account");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Method that validates input
	 * @param userAccount
	 * @return boolean
	 */
	private boolean isValidUserAccount(UserAccount userAccount){
		log.debug("Entering IdentityManagementRS.isValidUserAccount...");
		
		if(userAccount == null ||
				userAccount.getUsername() == null || userAccount.getUsername().isEmpty() ||
						userAccount.getId() == null || userAccount.getId().isEmpty() ||
								userAccount.getName() == null || userAccount.getName().isEmpty() ||
										userAccount.getPassword() == null || userAccount.getPassword().isEmpty() ||
												userAccount.getEmail() == null || userAccount.getEmail().isEmpty())
			return false;
		
		log.debug("Exiting IdentityManagementRS.isValidUserAccount...");
		return true;
	}
}

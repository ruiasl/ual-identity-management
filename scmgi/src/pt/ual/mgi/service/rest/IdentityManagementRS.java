package pt.ual.mgi.service.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import pt.ual.mgi.detail.messaging.ForgotPasswordTemplateMessage;
import pt.ual.mgi.detail.messaging.ResetPasswordTemplateMessage;
import pt.ual.mgi.exception.MgiException;
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
public class IdentityManagementRS extends SpringBeanAutowiringSupport{

	private static Logger log = LoggerFactory.getLogger(IdentityManagementRS.class);
	
	@Autowired
	private IMessagingManager messagingManager; 
	
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
		
		log.debug("Create an Account...");
		
		log.debug("Exiting IdentityManagementRS.createAccount...");
		return Response.ok().build();
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
	@ApiOperation(value = "Obtém Conta", notes = "Obter uma conta de utilizador através do seu identificador de utilizador.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "UserAccount", response = UserAccount.class),
							@ApiResponse(code = 404, message = "UserAccount não encontrada") })
	public Response getAccount(@ApiParam("id") @PathParam("id") String id) {
		log.debug("Entering IdentityManagementRS.getAccount...");
		
		UserAccount account = new UserAccount();
		account.setId("123456");
		account.setNumber("20070337");
		account.setUsername("20070337@students.ual.pt");
		account.setEmail("ruiaslopes@gmail.com");
		account.setPhone("914303502");
		account.setName("Rui Lopes");
		
		log.debug("Exiting IdentityManagementRS.getAccount...");
		return Response.ok(account).build();
	}
	
	/**
	 * Method that delete an Account for a received ID
	 * 
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("/accounts/{id}")
	@ApiOperation(value = "Remove Conta", notes = "REmover uma conta de utilizador através do seu identificador de utilizador.")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Conta Removida"),
							@ApiResponse(code = 404, message = "UserAccount não encontrada") })
	public Response deleteAccount(@ApiParam("id") @PathParam("id") String id) {
		log.debug("Entering IdentityManagementRS.deleteAccount...");
		
		log.debug("Exiting IdentityManagementRS.deleteAccount...");
		return Response.noContent().build();
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
							@ApiResponse(code = 404, message = "UserAccount não encontrada") })
	public Response updateAccount(@ApiParam("id") @PathParam("id") String id) {
		log.debug("Entering IdentityManagementRS.updateAccount...");
		
		log.debug("Exiting IdentityManagementRS.updateAccount...");
		return Response.ok().build();
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
	@ApiOperation(value = "Reset da Password", notes = "Efetuar Reset À Password.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Reset da Password efetuada"), 
							@ApiResponse(code = 404, message = "UserAccount não encontrada") })
	public Response resetAccountPassword(@ApiParam("id") @PathParam("id") String id, 
						@QueryParam("oldPassword") String oldPassword, 
							@QueryParam("newPassword") String newPassword) {
		log.debug("Entering IdentityManagementRS.resetAccountPassword...");
		
		ResetPasswordTemplateMessage message = 
				new ResetPasswordTemplateMessage("ruiaslopes@gmail.com","newpassword");
		message.setMessageFromAddress("ruiaslopes@gmail.com");
		try {
			this.messagingManager.sendMessage(message, true);
		} catch (MgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Exiting IdentityManagementRS.resetAccountPassword...");
		return Response.ok().build();
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
	@ApiOperation(value = "Recuperação da Password", notes = "Efetuar Recuperação À Password.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Recuperação da Password efetuada"), 
							@ApiResponse(code = 404, message = "UserAccount não encontrada") })
	public Response forgotAccountPassword(@ApiParam("id") @PathParam("id") String id, 
			@QueryParam("hintAnswer") String hintAnswer, @QueryParam("email") String email) {
		log.debug("Entering IdentityManagementRS.forgotAccountPassword...");
		
		ForgotPasswordTemplateMessage message = 
				new ForgotPasswordTemplateMessage("ruiaslopes@gmail.com", "Encarnado");
		message.setMessageFromAddress("ruiaslopes@gmail.com");
		try {
			this.messagingManager.sendMessage(message, true);
		} catch (MgiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("Exiting IdentityManagementRS.forgotAccountPassword...");
		return Response.ok().build();
	}
}

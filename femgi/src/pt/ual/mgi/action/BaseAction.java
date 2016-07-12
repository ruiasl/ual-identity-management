package pt.ual.mgi.action;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pt.ual.mgi.ldap.MgiUserDetails;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the base action for mgi frontend
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = -5114587853034749717L;

	/**
	 * Return to the actions the default main menu title.
	 * 
	 * @return String
	 */
	public String getActionName() {
		String actionName = ActionContext.getContext().getName();
		if (actionName != null && !"".equals(actionName)) {
			actionName = actionName.substring(actionName.indexOf("-") + 1);
		}
		return actionName;
	}
	
	/**
	 * Gets the principal logged
	 * 
	 * @return Object
	 */
	public static Object getPrincipal(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
			if (!auth.getPrincipal().equals("roleAnonymous"))
				return auth.getPrincipal();
		
		return null;
	}
	
	/**
	 * Getting the Principal Username
	 * @return String
	 */
	public String getPrincipalUsername(){
		if(BaseAction.getPrincipal()!=null)
			return ((MgiUserDetails)BaseAction.getPrincipal()).getUsername();
		return null;
	}
	/**
	 * Getting the Principal User Number 
	 * @return String
	 */
	public String getPrincipalUserName(){
		if(BaseAction.getPrincipal()!=null)
			return ((MgiUserDetails)BaseAction.getPrincipal()).getUserName();
		return null;
	}
}
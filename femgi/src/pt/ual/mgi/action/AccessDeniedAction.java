package pt.ual.mgi.action;

/**
 * Class responsible for access denied action flow
 * 
 * @author 20070337
 * @version 1.0
 *
 * @see BaseAction
 */
public class AccessDeniedAction extends BaseAction{

	private static final long serialVersionUID = 5300551897932518364L;
	private String accessDenied;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		this.accessDenied = "true";
		return SUCCESS;
	}

	/** @return the accessDenied*/
	public String getAccessDenied() {return accessDenied;}

	/**
	 * @param accessDenied the accessDenied to set
	 */
	public void setAccessDenied(String accessDenied) {this.accessDenied = accessDenied;}
}

package pt.ual.mgi.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class that represents the home action for mgi frontend
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see ActionSupport
 */
public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = 8774068336054480666L;

	public static final String MESSAGE = "welcome.home.message";

	private String message;
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
        this.setMessage(this.getText(MESSAGE));
        return SUCCESS;
    }

	/**@return the message*/
	public String getMessage() {return message;}
	/**@param message the message to set*/
	public void setMessage(String message) {this.message = message;}
}

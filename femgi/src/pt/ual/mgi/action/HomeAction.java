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
public class HomeAction extends BaseAction {

	private static final long serialVersionUID = 8774068336054480666L;

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
    public String execute() throws Exception {
     
        return SUCCESS;
    }
}

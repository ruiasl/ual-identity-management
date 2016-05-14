/**
 * 
 */
package pt.ual.mgi.exception;

/**
 * The Bussiness application exception
 * 
 * @author 20070337
 * @version 1.0
 */
public class UserNotFoundException extends BusinessMgiException {

	private static final long serialVersionUID = -4975350382245449398L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}


	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

/**
 * 
 */
package pt.ual.mgi.exception;

/**
 * The General application exception
 * 
 * @author 20070337
 * @version 1.0
 */
public class MgiException extends Exception {

	private static final long serialVersionUID = -4975350382245449398L;


	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public MgiException(String message) {
		super(message);
	}


	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public MgiException(String message, Throwable cause) {
		super(message, cause);
	}

}

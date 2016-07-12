/**
 * 
 */
package pt.ual.mgi.exception;

/**
 * The Bussiness application exception
 * 
 * @author 20070337
 * @version 1.0
 * 
 * @see MgiException
 */
public class BusinessMgiException extends MgiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3932724900641783748L;


	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public BusinessMgiException(String message) {
		super(message);
	}


	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public BusinessMgiException(String message, Throwable cause) {
		super(message, cause);
	}

}

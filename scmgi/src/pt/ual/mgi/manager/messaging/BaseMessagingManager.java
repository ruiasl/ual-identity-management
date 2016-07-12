/**
 * 
 */
package pt.ual.mgi.manager.messaging;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ual.mgi.common.Constants;
import pt.ual.mgi.detail.messaging.BaseMessage;
import pt.ual.mgi.exception.MgiException;

/**
 * Base class for the messaging managers
 * 
 * @author 20070337
 * @version 1.0
 */
@Service
public abstract class BaseMessagingManager {
	
	private Logger log = LoggerFactory.getLogger(BaseMessagingManager.class);
		
	@Autowired
	protected Properties emailProperties;
	
	/**
	 * Method that applies a template to a message
	 * 
	 * @param message
	 * @param parametersBuild
	 * @throws MgiException
	 */
	protected void templateTransformation(BaseMessage message, Boolean parametersBuild) 
											throws MgiException{
		log.debug("Entering BaseMessagingManager.templateTransformation");
		
		if(message == null)
			throw new MgiException("No message passed to transform");
		
		if(!parametersBuild) message.buildParameters();
		
		InputStream xsltInputStream = null;
		ByteArrayInputStream bais = null;
		try {
			log.debug("Prepare the Template xslt String ");
			xsltInputStream = this.getXsltDataFromFile();
			
			if(xsltInputStream == null)
				throw new MgiException(
							"No tempate passed to do transformation");

			message.setParameters(message.useParameters());
			
			StringReader inputBuffer = new StringReader(message.getParameters());
			
			log.debug("Update the stream where the transformation will happen: xmlContent");
			bais = new ByteArrayInputStream(message.getParameters().getBytes(Constants.UTF8_ENCODING));
			log.debug("Create transformation sources.");
			Source xsltSource =     new StreamSource(xsltInputStream);
			StreamResult result = new StreamResult(new StringWriter());

			log.debug("Create an instance of TransformerFactory.");
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xsltSource);
			trans.setOutputProperty(OutputKeys.ENCODING, Constants.ISO_ENCODING);
			trans.transform(new StreamSource(inputBuffer), result);

			log.debug("Remove xml markup if exists.");
			int li= -1;
			int hi= -1;
			String beginXmlDeclaration = "<?xml";
			String endXmlDeclaration = "?>";
			String transformResult = result.getWriter().toString();
			
			li = transformResult.indexOf(beginXmlDeclaration);
			if (li > -1) {
				hi = transformResult.indexOf(endXmlDeclaration);
				StringBuffer strBufTemp = new StringBuffer(transformResult.substring(0, li));
				strBufTemp.append((transformResult.substring(
							hi + endXmlDeclaration.length(), transformResult.length())));
				transformResult = strBufTemp.toString();
			}
			
			message.setBody(transformResult);
			
		} catch(TransformerException | UnsupportedEncodingException e) {
			log.error("Error ocurred in transformation.");
			throw new MgiException(e.getMessage(),e);
		} finally {
			try {
				if(bais != null) {
					bais.close();
					bais = null;
				}
			} catch (IOException e) {
				log.error("Could not close Stream.");
			}
		}
	}	
	
	/**
	 * Method that get Template File from Disk
	 * 
	 * @return InputStream
	 * @throws MgiException 
	 */
	private InputStream getXsltDataFromFile() throws MgiException {
		log.debug("Entering getXsltDataFromFile");
		InputStream input = null;
		String name = this.emailProperties.getProperty("mail.template.file.location"); 
		
		File file = new File(name);
		if(!file.exists())
			throw new MgiException(
					"XSLTemplate: Unable to read the XSL template file:" + name);

		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new MgiException(e.getMessage(), e);
		}
		log.debug("Exiting getXsltDataFromFile");
		return input;
	}
}
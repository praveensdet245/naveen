package genericLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * This utility is used to get property values from Configuration.properties file in project
 * @author praveen.k
 * @version 1.0
 */

import org.apache.log4j.Logger;
public class PropertiesUtil {
	/**
	 * This utility is used to get data from properties file where the path <br>
	 * is <strong>System.getProperty("user.dir")+"\\Configuration.properties"</strong>
	 */
	private static Logger log = Logger.getLogger(PropertiesUtil.class.getName());
	private static Properties properties = new Properties();
	private static String propFileName = System.getProperty("user.dir")+"\\Configuration.properties";
	final static File file = new File(propFileName);
	static {
		
		log.info("Properties file path : "+propFileName);
		try {
			InputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
			
		} catch (IOException e) {
			log.error("Unable to find file...",e);
		}
	}
    /**
     * properties file is key value based file to get the key value <br>
     * pass that key as argument and it will return value,make sure <br>
     * in properties file key should be unique but value can be duplicate<br>
     * @param strPropKey  <strong>key in properties file</strong>
     * @return <strong>value based on key</strong>
     */
	public static String getPropValues(String strPropKey) {
		log.info("Key name from Properties File : "+strPropKey);
		String value = properties.getProperty(strPropKey);
		
		return value;
	}
	
	public static void setPropValues(String strPropKey,String value,String commentsForValue) throws IOException{
		properties.setProperty(strPropKey, value);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		properties.store(fileOutputStream, commentsForValue);
		fileOutputStream.close();
	}

}
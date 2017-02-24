package genericLibrary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class MonitorMail {
	private static Logger log = Logger.getLogger(MonitorMail.class.getName());
	private static String USER_NAME = "tom.salary@gmail.com";  // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = "tomsalary"; // GMail password
    private static Properties props = System.getProperties();

		
	/**
	 * This methods used in Listeners and use of this is on test script error it will
	 * send mail to the mail id which have configured in configuration.properties
	 * with the key "Failed_Test_Screenshot_To_Report"
	 * @param fullyQualifiedScreenshotName
	 * @param methodName
	 */
	public static void onErrorSendScreenshot(String fullyQualifiedScreenshotName,String methodName){
		    
		    File file = new File(fullyQualifiedScreenshotName);
		    log.info("Screenshot exist : "+file.exists()+"..... { "+fullyQualifiedScreenshotName+" } ");
		    String host = "smtp.gmail.com";
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.ssl.trust", host);
		    props.put("mail.smtp.user", USER_NAME);
		    props.put("mail.smtp.password", PASSWORD);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
	       
		    String username = PropertiesUtil.getPropValues("Failed_Test_Screenshot_To_Report");
		    
		    String subject = "Failed Test Screenshot.."+methodName+MonitorMail.date();
	        
	        String[] to = username.split(",");
		    Session session = Session.getDefaultInstance(props);
		    MimeMessage message = new MimeMessage(session);
		    Multipart multipart = new MimeMultipart();
		    try {


		        message.setFrom(new InternetAddress(USER_NAME));
		        InternetAddress[] toAddress = new InternetAddress[to.length];

		        // To get the array of addresses
		        for( int i = 0; i < to.length; i++ ) {
		            toAddress[i] = new InternetAddress(to[i]);
		        }

		        for( int i = 0; i < toAddress.length; i++) {
		            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		        }

		        message.setSubject(subject);
		        String bodyText = "Please find the below attahment which got failed while automation script execution...";
		        
		        message.setText("Automation Test Suite Report");
		        MimeBodyPart bodypart = new MimeBodyPart();
		        MimeBodyPart attachpart = new MimeBodyPart();
		        bodypart.setContent(bodyText, "text/html");
		        attachpart.setContent("Test Script Failed Report by Screenshot", "image/png");
		        String attachfile1 = fullyQualifiedScreenshotName;
		        System.out.println("FIle 1 : "+attachfile1);
		        
		        DataSource source = new FileDataSource(attachfile1);
		        attachpart.setDataHandler(new DataHandler(source));
		        attachpart.setFileName(new File(attachfile1).getName());

		        multipart.addBodyPart(attachpart);
		        message.setContent(multipart);
		        Transport transport = session.getTransport("smtp");


		        transport.connect(host, USER_NAME, PASSWORD);
		        transport.sendMessage(message, message.getAllRecipients());
		        transport.close();
		        log.info("Test Script Failed Report Mail sent successfully to ( "+username+" )");

		    }
		    catch (AddressException ae) {
		    	log.error("Unable To Send Email to ( "+username+" )",ae);
		    }
		    catch (MessagingException me) {
		    	log.error("Unable To Send Email to ( "+username+" )",me);
		        
		    }
		    
		    
	}
	private static String date(){
		java.util.Date date= new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	    return ft.format(date);
	}
}

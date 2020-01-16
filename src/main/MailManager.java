package main;

import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailManager {
	private String mailAddress = "ipchange@mynet.com";
	private String mailPassword = "s*******";
	private String smtpHost = "pop.mynet.com";
	private String smtpPort = "587";
	private String mailHeader = "Whats my new IP?";
	private String mailFrom = "august.bug@jungle.com";
	private ArrayList<String> subscribers = new ArrayList<>();
	
	public void init(String mailAddress, String mailPass, String smtpHost, String smtpPort, String mailHeader, String mailFrom) {
		this.mailAddress = mailAddress;
		this.mailPassword = mailPass;
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.mailHeader = mailHeader;
		this.mailFrom = mailFrom;
	}
	
	public void addSubscibers(ArrayList<String> nSubs) {
		for(String s : nSubs) {
			subscribers.add(s);
		}
	}
	
	public void sendMail2(String ip) {
		final String username = mailAddress;
        final String password = mailPassword;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        
        String to = "";
        for(String s : subscribers) {
        	to += s + ",";
        }
        to = to.substring(0, to.length()-1);
        System.out.println("to : " + to);

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
            message.setSubject(mailHeader);
            message.setText("new ip : " + ip);

            Transport.send(message);
            System.out.println("mail sent.");
        } 

        catch (MessagingException e) 
        {
            System.out.println("Username or Password are incorrect!");
        }
	}
}

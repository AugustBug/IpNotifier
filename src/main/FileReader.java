package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FileReader {

	public void readSettingsFile(ApplicationMain parent) {
		
		try {
			ClassLoader classLoader = getClass().getClassLoader();
//			File inputFile = new File(classLoader.getResource("settings.xml").getFile());
			File inputFile = new File("settings.xml");
			
			InputStream is = new FileInputStream(inputFile);
			Reader reader = new InputStreamReader(is, "utf-8");
			InputSource source = new InputSource(reader);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(source);
			doc.getDocumentElement().normalize();
			NodeList sList = doc.getElementsByTagName("Subscriber");
			for (int temp = 0; temp < sList.getLength(); temp++) {
				Node nNode = sList.item(temp);
				String subs = nNode.getTextContent();
				System.out.println("sub : " + subs);
				parent.addSubscriber(subs);
			}
			
			NodeList fList = doc.getElementsByTagName("From");
			if(fList.getLength() > 0) {
				System.out.println(fList.item(0).getTextContent());
				parent.setMailFrom(fList.item(0).getTextContent());
			}
			
			NodeList iList = doc.getElementsByTagName("IntervalSec");
			if(iList.getLength() > 0) {
				System.out.println(iList.item(0).getTextContent());
				try {
					int sec = Integer.parseInt(iList.item(0).getTextContent());
					System.out.println(sec);
					parent.setCheckIntervalSec(sec);
				} catch (Exception e) {
					System.out.println("check interval can not be converted!");
				}
			}

			NodeList shList = doc.getElementsByTagName("SmtpHost");
			if(shList.getLength() > 0) {
				System.out.println(shList.item(0).getTextContent());
				parent.setSmtpHost(shList.item(0).getTextContent());
			}
			
			NodeList spList = doc.getElementsByTagName("SmtpPort");
			if(spList.getLength() > 0) {
				System.out.println(spList.item(0).getTextContent());
				parent.setSmtpPort(spList.item(0).getTextContent());
			}
			
			NodeList maList = doc.getElementsByTagName("MailAddress");
			if(maList.getLength() > 0) {
				System.out.println(maList.item(0).getTextContent());
				parent.setMailAddress(maList.item(0).getTextContent());
			}
			
			NodeList mpList = doc.getElementsByTagName("MailPassword");
			if(mpList.getLength() > 0) {
				System.out.println(mpList.item(0).getTextContent());
				parent.setMailPassword(mpList.item(0).getTextContent());
			}
			
			NodeList mhList = doc.getElementsByTagName("MailHeader");
			if(mhList.getLength() > 0) {
				System.out.println(mhList.item(0).getTextContent());
				parent.setMailHeader(mhList.item(0).getTextContent());
			}
			
		} catch (Exception e) {
			System.out.println("Settings file read error! ");
			e.printStackTrace();
		}
	}
}

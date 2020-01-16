package main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Ip Change Notifier
// Ahmert
// 12.11.2017

public class ApplicationMain {
	
	// helper classes
	private MailManager mailManager;
	private NetworkManager netManager;
	private FileReader fReader;
	
	private Timer timer;
	private TimerTask task;
	private int checkInterval = 3000;
	private boolean terminated = false;
	
	// mail info settings
	private String smtpHost;
	private String smtpPort;
	private String mailFrom;
	private String mailAddress;
	private String mailPassword;
	private String mailHeader;
	private int checkIntervalSec;
	private ArrayList<String> subscribers;
	
	private static String lastIp = "";
	
	public ApplicationMain() {
		subscribers = new ArrayList<>(); 
	}
	
	public void init() {
		netManager = new NetworkManager();
		mailManager = new MailManager();
		fReader = new FileReader();
		
		fReader.readSettingsFile(this);
		
		mailManager.init(mailAddress, mailPassword, smtpHost, smtpPort, mailHeader, mailFrom);
		mailManager.addSubscibers(subscribers);
		
		// periodically checks the global ip address of the pc
		// sends mail if any ip change occurs
		timer = new Timer();
		task = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("...");
				String nIp = netManager.findIp2();
				if(nIp.length() > 2) {
					if(!lastIp.equals(nIp)) {
						lastIp = nIp;
						mailManager.sendMail2(nIp);
						System.out.println("ip : $" + nIp + "$");
					} else {
						System.out.println("...");
					}
				} else {
					System.out.println("..");
				}
			}
		};
		
		timer.schedule(task, checkInterval, checkInterval);
	}
	
	public void loop() {
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public static void main(String[] args) {
		System.out.println("started");
		ApplicationMain app = new ApplicationMain();
		app.init();
		while(true) {
			if(app.isTerminated()) {
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("finished");
	}

	// SETTERS
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public void setCheckIntervalSec(int checkIntervalSec) {
		this.checkIntervalSec = checkIntervalSec;
		this.checkInterval = this.checkIntervalSec * 1000;
	}
	
	public void addSubscriber(String subs) {
		this.subscribers.add(subs);
	}
	
	public void setMailHeader(String header) {
		this.mailHeader = header;
	}
}

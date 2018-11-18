package au.com.mason.expensemanager.robot;

import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.domain.Notification;
import au.com.mason.expensemanager.domain.RefData;
import au.com.mason.expensemanager.processor.Processor;
import au.com.mason.expensemanager.processor.ProcessorFactory;
import au.com.mason.expensemanager.service.EncryptionService;
import au.com.mason.expensemanager.service.NotificationService;
import au.com.mason.expensemanager.service.RefDataService;

@Component
public class EmailTrawler {
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private RefDataService refDataService;
	
	@Autowired
	private ProcessorFactory processorFactory;
	
	@Autowired
	protected NotificationService notificationService;
	
	@Value("${required.info}")
	private String requiredKey;
	
	@Value("${req.account}")
	private String reqAccount;

	public void check() {
		try {
			String host = "pop.gmail.com";// change accordingly
			String user = encryptionService.decrypt(reqAccount);
			String password = encryptionService.decrypt(requiredKey);

			// create properties field
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");

			Session emailSession = Session.getDefaultInstance(properties);
			Store store = emailSession.getStore();
			store.connect(host, user, password);

			Folder emailFolder = store.getFolder("INBOX");
			// use READ_ONLY if you don't wish the messages
			// to be marked as read after retrieving its content
			emailFolder.open(Folder.READ_WRITE);

			Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			System.out.println("messages.length---" + messages.length);
			
			List<RefData> refDatas = refDataService.getAllWithEmailKey(); 

			for (Message message : messages) {
				for (RefData refData : refDatas) {
					if (message.getSubject().startsWith(refData.getEmailKey())) {
						Processor processor = processorFactory.getProcessor(refData.getEmailProcessor());
						processor.execute(message, refData);
					}
					else {
						Notification notification = new Notification();
						notification.setMessage("Unhandled Email with title - " + message.getSubject());
						notificationService.create(notification);
					}
					//mark as read
					message.getContent();
					MimeMessage source = (MimeMessage) message;
					MimeMessage copy = new MimeMessage(source);
					System.out.println("Subject: " + message.getSubject());
				}
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message[] fetchMessages(String host, String user, String password, boolean read) throws Exception {
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");

		Session emailSession = Session.getDefaultInstance(properties);
		Store store = emailSession.getStore();
		store.connect(host, user, password);

		Folder emailFolder = store.getFolder("INBOX");
		// use READ_ONLY if you don't wish the messages
		// to be marked as read after retrieving its content
		emailFolder.open(Folder.READ_WRITE);

		// search for all "unseen" messages
		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, read);
		return emailFolder.search(unseenFlagTerm);
	}

}

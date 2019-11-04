package com.gsbsoft.evenmore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.List;
import java.util.Properties;

public class MailSender {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Properties mailProp;
	private Session session;
	
	private void init() {
		mailProp = new Properties();
		mailProp.put("mail.transport.protocol",		ConfigConstants.MAIL_PROTOCOL);
		mailProp.put("mail.smtp.host",				ConfigConstants.MAIL_HOST);
		mailProp.put("mail.smtp.port",				ConfigConstants.MAIL_PORT);
		mailProp.put("mail.smtp.auth",				ConfigConstants.MAIL_SMTP_AUTH);
		mailProp.put("mail.smtp.starttls.enable", 	ConfigConstants.MAIL_SMTP_START_TLS_ENABLE);
		mailProp.put("mail.smtp.starttls.required",	ConfigConstants.MAIL_SMTP_AUTH);
		session = Session.getDefaultInstance(mailProp, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ConfigConstants.MAIL_USERNAME, ConfigConstants.MAIL_PASSWORD);
			}
		});
	}
	
	public void send(InternetAddress from, InternetAddress to, String subject, String contents) {
		send(null, from, new InternetAddress[]{to}, subject, contents, null);
	}

	public void send(Session session, InternetAddress from, InternetAddress to, String subject, String contents) {
		send(session, from, new InternetAddress[]{to}, subject, contents, null);
	}

	public void send(InternetAddress from, InternetAddress to, String subject, String contents, List<MailAttachModel> fileList) {
		send(null, from, new InternetAddress[]{to}, subject, contents, fileList);
	}

	public void send(Session session, InternetAddress from, InternetAddress to, String subject, String contents, List<MailAttachModel> fileList) {
		send(session, from, new InternetAddress[]{to}, subject, contents, fileList);
	}
	
	public void send(Session session, InternetAddress from, InternetAddress[] toList, String subject, String contents, List<MailAttachModel> fileList) {
		init();
		if (session == null) session = this.session;
		
		try {
			Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
			mimeMessage.setFrom(from);
			mimeMessage.setRecipients(Message.RecipientType.TO, toList);
			mimeMessage.setSubject(MimeUtility.encodeText(subject, ConfigConstants.MAIL_ENCODING, "B"));
			mimeMessage.setContent(contents, "text/html; charset="+ConfigConstants.MAIL_ENCODING);

			
			//File
			if (fileList != null && fileList.size() != 0) {
				MimeBodyPart fileBodyPart;
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				Multipart multipart = new MimeMultipart();
				for(MailAttachModel attach : fileList) {
					fileBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attach.getSaveFileNm());
					fileBodyPart.setDataHandler(new DataHandler(source));
					fileBodyPart.setFileName(attach.getOrgiFileNm());
					multipart.addBodyPart(fileBodyPart);
				}
				messageBodyPart.setContent(contents, "text/html; charset="+ConfigConstants.MAIL_ENCODING);
				multipart.addBodyPart(messageBodyPart);
				mimeMessage.setContent(multipart);

			}
			
			Transport.send(mimeMessage);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	public static void main(String[] args)  {

		InternetAddress ia;
		try {
			ia = new InternetAddress("taehyeong.kim@yap.net", "관리자", ConfigConstants.MAIL_ENCODING);

//			List<MailAttachModel> fileList = new ArrayList<>();
//			MailAttachModel file = new MailAttachModel();
//			file.setOrgiFileNm("setup.xlsx");
//			file.setSaveFileNm("/Users/petaek/Downloads/20190524105126ini.xlsx");
//			fileList.add(file);

			MailSender sender = new MailSender();
			//sender.send(ia, ia, "test1", "<html><body>file</body></html>", fileList);
			sender.send(ia, ia, "file and tex","<p>hello world</p>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

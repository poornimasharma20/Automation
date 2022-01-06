package com.zanui.lib.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.zanui.config.ZanuiConstants;
import com.zanui.lib.main.Constants;

/**
 * Send email using the extent report as an attachment
 * 
 * @author Pooja Bagga
 *
 */
public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmailWithanAttachment(String toEmail, String subject, String body) {
		try {
			final Session session = createSession();
			final MimeMessage message = new MimeMessage(session);

			XlsReader excel = new XlsReader(ZanuiConstants.ConfigPropertyFilePath);
			String EmailFrom = excel.getCellData("EmailSettings", 2, "SENT_FROM");
			message.setFrom(new InternetAddress(EmailFrom, "NoReply-JD"));
			message.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
			message.setSubject(subject);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			String bodymessage = "<i>Greetings!</i><br><br>";
			bodymessage += "<b>Wish you a nice day!</b><br><br>";
			bodymessage += "<font color=red>Please find the attached automation test report.</font><br><br>";
			bodymessage += "<b>Regards,</b><br>";
			bodymessage += "<b>Testing Team</b><br>";

			// 3) create MimeBodyPart object and set your message content
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(bodymessage, "text/html");

			// 4) create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			final String filename = Constants.ResultPath + File.separator + "Automation Report.html";// change
																										// accordingly
			final DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(source.getName());

			// 5) create Multipart object and add MimeBodyPart objects to this object
			final Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			// 6) set the multipart object to the message object
			message.setContent(multipart, "text/html");

			// 7) send message
			Transport.send(message);

			System.out.println("message sent....");

		} catch (final MessagingException | UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Create email session {@link Session}
	 */
	private static Session createSession() {
		System.out.println("Simple Email Start");
		Properties props = System.getProperties();
		XlsReader excel = new XlsReader(ZanuiConstants.ConfigPropertyFilePath);
		String SmtpServer = excel.getCellData("EmailSettings", 2, "SMTP_SERVER");
		props.put("mail.smtp.host", SmtpServer);
		return Session.getInstance(props, null);
	}
}
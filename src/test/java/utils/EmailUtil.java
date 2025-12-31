package utils;

//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.mail.search.SubjectTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.SubjectTerm;

public class EmailUtil {
    private String email;
    private String password;
    private String imapHost;
    private String emailSubject;

    public EmailUtil(String email, String password, String imapHost, String emailSubject) {
        this.email = email;
        this.password = password;
        this.imapHost = imapHost;
        this.emailSubject = emailSubject;
    }

    public String fetchOTP(int maxRetries, long retryIntervalMs) throws Exception {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return fetchOTPFromEmail();
            } catch (Exception e) {
                if (i == maxRetries - 1) throw e;
                Thread.sleep(retryIntervalMs);
            }
        }
        throw new Exception("Failed to fetch OTP after " + maxRetries + " attempts");
    }

    private String fetchOTPFromEmail() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", imapHost);
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        
        try {
            store.connect(imapHost, email, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new SubjectTerm(emailSubject), inbox.getMessages());
            if (messages.length == 0) {
                throw new Exception("No OTP email found with subject: " + emailSubject);
            }

            Message latestMessage = messages[messages.length - 1];
            String content = extractEmailContent(latestMessage);
            return extractOTPFromContent(content);
        } finally {
            if (store.isConnected()) {
                store.close();
            }
        }
    }

    private String extractEmailContent(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            StringBuilder content = new StringBuilder();
            
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    content.append(bodyPart.getContent().toString());
                }
            }
            
            return content.toString();
        }

        return message.getContent().toString();
    }

    private String extractOTPFromContent(String content) {
        Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new RuntimeException("Failed to extract OTP from email content");
    }
}
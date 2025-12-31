package ParentPages;


import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signuppage {

    private static final String EMAIL = "padmavathi555divyakolu@gmail.com";
    private static final String EMAIL_PASSWORD = "qizk fsjc dvam ajha"; // App Password

    public static String fetchOTPFromGmail() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");

        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", EMAIL, EMAIL_PASSWORD);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            String subject = message.getSubject();
            if (subject != null && subject.contains("OTP")) {
                String content = message.getContent().toString();
                Matcher matcher = Pattern.compile("\\b\\d{6}\\b").matcher(content);
                if (matcher.find()) {
                    String otp = matcher.group();
                    message.setFlag(Flags.Flag.SEEN, true);
                    return otp;
                }
            }
        }

        throw new Exception("OTP not found in unread emails.");
    }
}

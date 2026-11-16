package vn.iotstar.util;

import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream in = MailUtil.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (in != null) CONFIG.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MailUtil() {}

    private static String cfg(String envName, String key) {
        String v = System.getenv(envName);
        return (v != null && !v.isBlank()) ? v : CONFIG.getProperty(key, "");
    }

    public static void send(String to, String subject, String body) throws Exception {
        final String user = cfg("MAIL_USER", "mail.username");
        final String pass = cfg("MAIL_PASSWORD", "mail.password");

        // Chưa cấu hình mail -> chế độ test: in nội dung ra console
        if (user.isBlank() || user.equals("your_email@gmail.com")) {
            System.out.println("=== [MAIL TEST MODE] To: " + to + " | " + subject + "\n" + body + "\n===");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", CONFIG.getProperty("mail.host", "smtp.gmail.com"));
        props.put("mail.smtp.port", CONFIG.getProperty("mail.port", "587"));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user, "JPA Shopping", "UTF-8"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject, "UTF-8");
        msg.setText(body, "UTF-8");
        Transport.send(msg);
    }
}

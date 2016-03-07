package notification.mail;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventNotification;
import domain.notificationcenter.api.NotificationServiceConfiguration;
import domain.notificationcenter.spi.NotificationService;
import notification.mail.configuration.MailConfigurationParser;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

public class MailNotification implements NotificationService {
    private static String EMAIL_SETTING = "email";

    private final Session session;

    @Override
    public String getServiceId() {
        return "mail";
    }

    public MailNotification(final String username, final String password) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        this.session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    @Override
    public void sendNotification(NotificationServiceConfiguration serviceConfiguration, FileEventNotification.Type type, String userId, List<File> files, Map<String, Permission> sharedUsersIdWithPermission) {
        MailConfigurationParser configurationParser = new MailConfigurationParser(serviceConfiguration);
        String emailToSend = configurationParser.getMail();

        if (type == FileEventNotification.Type.SHARE_WITH) {
            sendMailNotification(emailToSend, userId + " share file: " + displayFiles(files) + " with " + sharedUsersIdWithPermission.toString());
        } else {
            sendMailNotification(emailToSend, userId + " " + type.name() + " file(s): " + displayFiles(files));
        }
    }


    private void sendMailNotification(String emailToSend, String messageEvent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hexagonal@awsome.is"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailToSend));
            message.setSubject("File manager sendNotification");
            message.setText(messageEvent);
            Transport.send(message);
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String displayFiles(List<File> files) {
        return join(files.stream().map(this::displayFiles).collect(Collectors.toList()), ", ");
    }

    private String displayFiles(File file) {
        return file.getName();
    }

    private static String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if(var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }


}
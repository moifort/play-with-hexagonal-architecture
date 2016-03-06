package handler.mail;

import domain.filemanager.api.entity.File;
import domain.filemanager.api.entity.Permission;
import domain.filemanager.spi.FileEventHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

public class MailHandler implements FileEventHandler {

    private final Session session;

    public MailHandler() {
        final String username = "thibaut@alantaya.com";
        final String password = "Doremido91!";

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
    public void addFileEvent(String userId, File file) {
        sendMailNotification(userId + " add file: " + displayFiles(file));
    }

    @Override
    public void getFileEvent(String userId, List<File> files) {
        sendMailNotification(userId + " get file(s): " + displayFiles(files));
    }

    @Override
    public void getSharedFileEvent(String userId, List<File> files) {
        sendMailNotification(userId + " get shared file(s): " + displayFiles(files));
    }

    @Override
    public void deleteFileEvent(String userId, File file) {
        sendMailNotification(userId + " delete file: " + displayFiles(file));
    }

    @Override
    public void shareFileEvent(String userId, File file, Map<String, Permission> sharedUsersIdWithPermission) {
        sendMailNotification(userId + " share file: " + displayFiles(file) + " with " + sharedUsersIdWithPermission.toString());
    }

    private void sendMailNotification(String messageEvent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("thibaut@alantaya.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("thibaut.mottet@gmail.com"));
            message.setSubject("File manager event");
            message.setText(messageEvent);
            Transport.send(message);
        } catch (MessagingException e) {
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
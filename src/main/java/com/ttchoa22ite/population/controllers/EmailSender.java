package com.ttchoa22ite.population.controllers;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

    private final String username;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;

    public EmailSender(String username, String password, String smtpHost, String smtpPort) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail(String recipient, String subject, String body) throws MessagingException {
        // Tạo đối tượng Properties để cấu hình các thông số kết nối SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Tạo đối tượng Authenticator để xác thực tài khoản người dùng
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Tạo đối tượng Session để thiết lập kết nối SMTP
        Session session = Session.getInstance(properties, authenticator);

        // Tạo đối tượng Message để tạo email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        // Tạo đối tượng MimeBodyPart để đại diện cho nội dung email
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Tạo đối tượng Multipart để chứa nội dung email và tệp đính kèm (nếu có)
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Thiết lập nội dung của email bằng Multipart
        message.setContent(multipart);

        // Gửi email
        Transport.send(message);
    }

    public void sendEmailWithAttachment(String recipient, String subject, String body, File attachmentFile) throws MessagingException {
        // Tạo đối tượng Properties để cấu hình các thông số kết nối SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Tạo đối tượng Authenticator để xác thực tài khoản người dùng
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Tạo đối tượng Session để thiết lập kết nối SMTP
        Session session = Session.getInstance(properties, authenticator);

        // Tạo đối tượng Message để tạo email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        // Tạo đối tượng MimeBodyPart để đại diện cho nội dung email
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Tạo đối tượng Multipart để chứa nội dung email và tệp đính kèm (nếu có)
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Nếu có tệp đính kèm, thêm chúng vào Multipart
        if (attachmentFile != null) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource dataSource = new FileDataSource(attachmentFile);
            attachmentPart.setDataHandler(new DataHandler(dataSource));
            attachmentPart.setFileName(attachmentFile.getName());
            multipart.addBodyPart(attachmentPart);
        }

        // Thiết lập nội dung của email bằng Multipart
        message.setContent(multipart);

        // Gửi email
        Transport.send(message);
    }
}
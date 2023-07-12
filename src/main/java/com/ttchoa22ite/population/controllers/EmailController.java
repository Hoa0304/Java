package com.ttchoa22ite.population.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

//import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailController {
    @FXML
    private TextField toField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField attachmentField;
    @FXML
    private TextArea attachmentArea;

    @FXML
    private Button attachButton;

    @FXML
    private Button sendButton;


    @FXML
    private File attachment;

    @FXML
    void initialize() {

        sendButton.setOnAction(event -> {
            String to = toField.getText();
            String subject = subjectField.getText();
            String body = attachmentArea.getText();

            if (to.isEmpty() || subject.isEmpty()) {
//                Toolkit.getDefaultToolkit().beep();
                return;
            }
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("hoattc.22ite@vku.udn.vn", "0789469867Hoa@");
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("hoattc.22ite@vku.udn.vn"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);

                if (attachment != null) {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();

                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName(attachment.getName());

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    multipart.addBodyPart(attachmentBodyPart);

                    message.setContent(multipart);
                } else {
                    // Nếu không có tệp đính kèm, sử dụng phần thân email hiện có
                    message.setContent(body, "text/plain");
                }

                // Gửi email
                Transport.send(message);

                // Đóng cửa sổ
                ((Stage) sendButton.getScene().getWindow()).close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private void attachFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn tập tin");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tất cả các tập tin", "*.*"),
                new FileChooser.ExtensionFilter("Tập tin văn bản", "*.txt", "*.doc", "*.docx"),
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tệp nén", "*.zip", "*.rar")
        );
        Stage stage = (Stage) attachButton.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("Đã chọn tập tin: " + file.getAbsolutePath());
            sendFile(file);
        }
    }

    @FXML
    void getHome(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("home.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void sendFile(File file) {
        // Gửi tập tin đến người nhận
        // Thêm mã xử lý ở đây
    }
}
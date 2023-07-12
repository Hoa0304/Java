package com.ttchoa22ite.population.controllers;

import com.ttchoa22ite.population.models.ChatModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatController {
    @FXML
    private AnchorPane onl;
    @FXML
    private TextFlow emojiList;
    @FXML
    private VBox chatBox;
    @FXML
    private TextArea chatArea;
    @FXML
    private HBox inputBox;
    @FXML
    private TextField messageField;
    @FXML
    private Button emojiButton;
    @FXML
    private Button sendButton;

    private ChatModel chatModel;

    private boolean isServer;

    public void initialize() {
        isServer = false;
        initializeChat();
    }

    public void emojiAction() {
        // TODO: Implement emoji action
//        emojiList.setVisible(!emojiList.isVisible());
    }

    @FXML
    private void handleEmojiClick(ActionEvent event) {
        Text emoji = (Text) event.getSource();
        String selectedEmoji = emoji.getText();
        System.out.println("Emoji đã chọn: " + selectedEmoji);
    }

    public void sendMessage() {
        String message = messageField.getText();
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);clientSocket.close();
            chatArea.appendText("You: " + message + "\n");
            messageField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    public void shutdown() {
        chatModel.shutdown();
    }

    public void getHome(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(((getClass().getResource("home.fxml"))));
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

    public void setServerAddressToLocalhost() {
        chatModel.setServerAddress("localhost",1234);
    }

    public void startServer() {
        isServer = true;
        initializeChat();
    }

    public void connectToServer() {
        isServer = false;
        setServerAddressToLocalhost();
        initializeChat();
    }

    private void initializeChat() {
        if (chatModel != null) {
            chatModel.shutdown();
        }
        chatModel = new ChatModel();
        if (isServer) {
            chatModel.startServer();
        } else {
            chatModel.connectToServer();
        }
        chatModel.setOnMessageReceivedListener(this::appendMessage);
        sendButton.setOnAction(event -> sendMessage());
    }

    public void shutdownChat() {
        chatModel.shutdown();
    }
}
package com.ttchoa22ite.population.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController {

    @FXML
    private Button logoutBtn;

    @FXML
    private Button manageButton;

    @FXML
    private Button permanan;
    @FXML
    private Button emailButton;


    @FXML
    void getResident(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(((getClass().getResource("resident.fxml"))));
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(parent);
            //set transparent
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ResidentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void getEmail(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(((getClass().getResource("email.fxml"))));
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(parent);
            //set transparent
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ResidentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void getchat(MouseEvent event) {

        try {
            Parent parent = FXMLLoader.load(((getClass().getResource("chat.fxml"))));
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(parent);
            //set transparent
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}



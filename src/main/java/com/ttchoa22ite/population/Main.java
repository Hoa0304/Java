package com.ttchoa22ite.population;

import com.ttchoa22ite.population.controllers.ChatServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application implements Runnable {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.<Parent>load(getClass().getResource("loginAdmin.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();


}




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {

    }
}



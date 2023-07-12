package com.ttchoa22ite.population.controllers;


import com.ttchoa22ite.population.utils.ConnectionDB;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

public class LoginAdmin implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSignin;
    @FXML
    private Label lblErrors;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = ConnectionDB.DAO();
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Lỗi máy chủ: Kiểm tra lại");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Máy chủ đã hoạt động: Tốt để sử dụng");
        }
    }

    public LoginAdmin() {
        con = ConnectionDB.DAO();
    }

    @FXML
    void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignin) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            if (username.isEmpty() || password.isEmpty()) {
                setLblError(Color.TOMATO, "Thông tin đăng nhập trống");
            } else {
                try {
                    String sql = "SELECT * FROM login WHERE username = ?";
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        System.out.println(hashedPassword);
                        if  (BCrypt.checkpw(password,hashedPassword)) {
                            setLblError(Color.GREEN, "Đăng nhập thành công..Đang chuyển hướng..");
                            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
                            Stage primaryStage = new Stage();
                            primaryStage.initStyle(StageStyle.TRANSPARENT);
                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        } else {
                            setLblError(Color.TOMATO, "Nhập đúng Tên đăng nhập / Mật Khẩu");
                        }
                    } else {
                        setLblError(Color.TOMATO, "Nhập đúng Tên đăng nhập / Mật Khẩu");
                    }
                } catch (SQLException ex) {
                    setLblError(Color.TOMATO, "Lỗi truy vấn cơ sở dữ liệu");
                    ex.printStackTrace();
                } catch (Exception e) {
                    setLblError(Color.TOMATO, "Lỗi không xác định");
                    e.printStackTrace();
                } finally {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        }
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}

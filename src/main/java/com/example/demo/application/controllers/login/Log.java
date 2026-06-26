package com.example.demo.application.controllers.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Log {
    @FXML
    private Label loginError;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    public void enter() throws java.io.IOException{
        // verificar se o usuário e a senha estão corretos e abre a página de dashboard.
        String username = txtUser.getText();
        String password = txtPassword.getText();

        if (username.equals("admin") && password.equals("1234")) {
            // login funcionou
            // carrega o FXML do dashboard
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/example/demo/dashboard/dashboard-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            txtUser.getScene().getWindow().hide(); // esconde a janela de login
            stage.show();
        }else{
            // login falhou
            loginError.setVisible(true);
        }
    }
}

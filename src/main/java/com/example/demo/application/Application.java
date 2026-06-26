package com.example.demo.application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        // inicia a aplicação
    }

    @Override
    public void start(Stage stage) throws Exception {
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/example/demo/login/log-view.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

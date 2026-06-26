package com.example.demo.application;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        // inicia a aplicação
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Garante que a tabela e os dados iniciais existam antes de iniciar a UI
        StockyDao dao = DaoFactory.createStockyDao();
        dao.createStocky();

        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/com/example/demo/login/log-view.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

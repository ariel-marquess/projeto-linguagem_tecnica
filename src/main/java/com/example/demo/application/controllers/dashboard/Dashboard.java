package com.example.demo.application.controllers.dashboard;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class Dashboard {

    private StockyDao stockyDao;

    @FXML
    public ListView<Stocky> Lista;    // Lista de visualização dos produtos


    @FXML
    public void initialize() {
        // quando a tela for carregada, carrega os produtos do estoque na lista de visualização
        stockyDao = DaoFactory.createStockyDao();
        List<Stocky> produtos = stockyDao.allProdutos();
        Lista.getItems().setAll(produtos);
        Lista.setCellFactory(param -> new javafx.scene.control.ListCell<Stocky>() {

            @Override
            protected void updateItem(Stocky item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    // formata a string para alinhar como uma tabela
                    String formattedText = String.format("%-8s %-30s %10d",
                            item.getId(),
                            item.getNome(),
                            item.getQuantidade());
                    setText(formattedText);

                    // Define uma fonte monoespaçada para garantir o alinhamento
                    setStyle("-fx-font-family: 'monospace';");
                }
            }
        });
    }

    public void cadastrar() {
        // Deve abrir a tela de cadastro de produtos
    }

    public void adicionar() {
        // Deve abrir a tela de adicionar produtos
    }

    public void remover() {
        // Deve abrir a tela de remover produtos
    }

    public void verificar() {
        // Deve abrir a tela de verificar produtos
        // Essa está referenciada pela classe Check na pasta check
    }

    public void sair() {
        // Deve sair do dashboard e retornas para a tela de login
    }
}

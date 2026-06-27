package com.example.demo.application.controllers.dashboard.tools.check;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class Check {
    @FXML
    public TextField campoPesquisar;
    @FXML
    public GridPane gridPane;

    private StockyDao stockyDao;

    private void atualizarGrid() {
        List<Stocky> lista = stockyDao.allProdutos();
        desenhar(lista);
    }

    @FXML
    public void initialize() {
        stockyDao = DaoFactory.createStockyDao();

        // Define o espaçamento vertical e torna as linhas da grade visíveis
        gridPane.setVgap(10);
        gridPane.setGridLinesVisible(true);

        atualizarGrid();
    }

    public void pesquisar() {
        // Filtra por substring no nome e redesenha
        String q = campoPesquisar.getText();
        List<Stocky> base = stockyDao.allProdutos();
        if (q == null || q.trim().isEmpty()) {
            desenhar(base);
            return;
        }
        String termo = q.toLowerCase();
        List<Stocky> filtrado = new ArrayList<>();
        for (Stocky p : base) {
            if (p.getNome().toLowerCase().contains(termo)) {
                filtrado.add(p);
            }
        }
        desenhar(filtrado);
    }

    public void sair() {
        // fecha a janela atual e retorna ao Dashboard
        campoPesquisar.getScene().getWindow().hide();
    }

    // faz as linhas no GridPane
    private void desenhar(List<Stocky> itens) {
        gridPane.getChildren().clear();

        int row = 0;
        for (Stocky p : itens) {
            Label lCodigo = new Label(String.format("%04d", p.getId()));
            Label lNome   = new Label(p.getNome());
            Label lQtd    = new Label(String.valueOf(p.getQuantidade()));
            Label lValor  = new Label(String.format("R$ %.2f", p.getValor()));
            double total  = p.getQuantidade() * p.getValor();
            Label lTotal  = new Label(String.format("R$ %.2f", total));

            // Agrupa os botões em um HBox com espaçamento
            Button btnEditar = new Button("Editar");
            Button btnRemover = new Button("Remover");
            btnEditar.setOnAction(event -> abrirJanelaEdicao(p));
            btnRemover.setOnAction(event -> abrirJanelaRemocao(p));
            HBox hboxBotoes = new HBox(10); // 10 pixels de espaçamento
            hboxBotoes.setAlignment(Pos.CENTER);
            hboxBotoes.getChildren().addAll(btnEditar, btnRemover);

            // Alinhamento das labels
            lCodigo.setAlignment(Pos.CENTER);
            lNome.setAlignment(Pos.CENTER);
            lQtd.setAlignment(Pos.CENTER);
            lValor.setAlignment(Pos.CENTER);
            lTotal.setAlignment(Pos.CENTER);

            gridPane.add(lCodigo, 0, row);
            gridPane.add(lNome,   1, row);
            gridPane.add(lQtd,    2, row);
            gridPane.add(lValor,  3, row);
            gridPane.add(lTotal,  4, row);
            gridPane.add(hboxBotoes, 5, row); // Adiciona o HBox à grade

            row++;
        }
    }

    private void abrirJanelaEdicao(Stocky produto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/dashboard/tools/check/editItem-view.fxml"));
            Parent root = loader.load();

            EditItem controller = loader.getController();
            controller.setProduto(produto, stockyDao);

            Stage stage = new Stage();
            stage.setTitle("Editar Produto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            stage.showAndWait();

            atualizarGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirJanelaRemocao(Stocky produto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/dashboard/tools/check/removeItem-view.fxml"));
            Parent root = loader.load();

            RemoveItem controller = loader.getController();
            controller.setProduto(produto, stockyDao);

            Stage stage = new Stage();
            stage.setTitle("Remover Produto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gridPane.getScene().getWindow());
            stage.showAndWait();

            atualizarGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCell(Label label, double prefWidth) {
        label.setPrefWidth(prefWidth);
        label.setAlignment(Pos.CENTER);
    }
}

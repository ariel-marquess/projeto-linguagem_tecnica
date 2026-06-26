package com.example.demo.application.controllers.dashboard;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Dashboard {

    private StockyDao stockyDao;

    @FXML
    public ListView<Stocky> Lista;    // Lista de visualização dos produtos

    private void atualizarLista(){
        // busca os produtos do banco e os adiciona à lista
        List<Stocky> produtos = stockyDao.allProdutos();
        Lista.getItems().setAll(produtos);
    }

    @FXML
    public void initialize() {
        // instancia o DAO para buscar os dados
        stockyDao = DaoFactory.createStockyDao();

        // configuração pra criar um layout personalizado para cada linha
        Lista.setCellFactory(param -> new javafx.scene.control.ListCell<Stocky>() {
            private final HBox hbox = new HBox();
            private final Label codigo = new Label();
            private final Label produto = new Label();
            private final Label quantidade = new Label();

            {
                // definine a aparência das colunas.

                // Define larguras fixas para cada coluna para garantir o alinhamento.
                codigo.setPrefWidth(100);
                produto.setPrefWidth(250);
                quantidade.setPrefWidth(100);

                // Define o alinhamento do texto dentro de cada coluna como centralizado.
                codigo.setAlignment(javafx.geometry.Pos.CENTER);
                produto.setAlignment(javafx.geometry.Pos.CENTER);
                quantidade.setAlignment(javafx.geometry.Pos.CENTER);

                // Adiciona as colunas ao nosso layout de linha.
                hbox.getChildren().addAll(codigo, produto, quantidade);
            }

            @Override
            protected void updateItem(Stocky item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null); // Não mostra nada para células vazias.
                } else {
                    // Para células com dados, define o texto de cada "coluna".
                    codigo.setText(String.valueOf(item.getId()));
                    produto.setText(item.getNome());
                    quantidade.setText(String.valueOf(item.getQuantidade()));

                    // Define nosso layout de linha (o HBox) como o conteúdo gráfico da célula.
                    setGraphic(hbox);
                }
            }
        });
        atualizarLista();

    }

    public void cadastrar() throws IOException {
        // abre a tela de cadastro de produtos

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/dashboard/tools/register-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        // showAndWait bloqueia ate fechar a janela de cadastro
        stage.showAndWait();
        // atualiza a lista apos fechar o cadastro para refletir os novos produtos
        atualizarLista();
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

    public void sair() throws IOException {
        // sai do dashboard e retorna para a tela de login escondendo o dashboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/login/log-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Lista.getScene().getWindow().hide();

    }
}

package com.example.demo.application.controllers.dashboard.tools;

import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Register {
    @FXML
    public TextField nome;

    @FXML
    public TextField quantidade;

    @FXML
    public TextField valor;

    @FXML
    public TextField codigo;

    private StockyDao stockyDao;

    @FXML
    public VBox erroContainer;

    @FXML
    public Label erroMessage;

    // Quando ocorrer um erro, torne visível o erroContainer e passe a mensagem de erro para o erroMessage.
    // Por padrão o erroContainer é invisível.
    // Crie uma função que torne o erroContainer invisível; coloque essa função no início dos outros métodos para que, sempre que uma ação seja feita pelo usuário, a mensagem de erro desapareça.

    private void hideError(){
        erroContainer.setVisible(false);
        erroMessage.setVisible(false);
    }

    private void showError(String mensagem){
        erroContainer.setVisible(true);
        erroMessage.setText(mensagem);
        erroMessage.setVisible(true);
    }

    @FXML
    public void initialize() {
        // Ao inicializar a página, passa para o campo de código o endereço que terá o produto a ser cadastrado.
        // pega do banco de dados o id do último produto cadastrado e adiciona 1 ao valor.
        // O valor no TextField tem o formato "0000", uma string, mas o valor que será retornado pelo banco será um número inteiro.
        stockyDao = com.example.demo.dao.DaoFactory.createStockyDao();
        int lastId = stockyDao.lastId();
        codigo.setText(String.format("%04d", lastId + 1));
    }

    private boolean salvarProduto() {
        hideError();

        String Nome = nome.getText().trim();
        String Quantidade = quantidade.getText().trim();
        String Valor = valor.getText().trim().replace(",", ".");

        try {
            if (Nome.isEmpty() || Quantidade.isEmpty() || Valor.isEmpty()) {
                throw new Exception("Todos os campos devem ser preenchidos.");
            }

            int FQuantidade = Integer.parseInt(Quantidade);
            double FValor = Double.parseDouble(Valor);

            if (FQuantidade < 0) {
                throw new Exception("Quantidade não pode ser negativa.");
            }
            if (FValor < 0) {
                throw new Exception("Valor não pode ser negativo.");
            }

            com.example.demo.models.Stocky novoProduto = new com.example.demo.models.Stocky(0, Nome, FQuantidade, FValor);
            stockyDao.insert(novoProduto);
            return true;

        } catch (NumberFormatException e) {
            showError("Os campos de quantidade e valor devem conter apenas números.");
            return false;

        } catch (Exception e) {
            showError(e.getMessage());
            return false;
        }
    }



    public void salvarEContinuar() {
        // salva o produto e atualiza a pagína atual.
        if(salvarProduto()) {
            initialize();
            nome.clear();
            quantidade.clear();
            valor.clear();
        }
    }

    public void salvarESair() throws Exception{
        // salva o produto e volta para a tela de dashboard.
        if (salvarProduto()) {
            nome.getScene().getWindow().hide();
        }
    }

    public void sair() {
        // volta para a tela de dashboard
        nome.getScene().getWindow().hide();
    }
}

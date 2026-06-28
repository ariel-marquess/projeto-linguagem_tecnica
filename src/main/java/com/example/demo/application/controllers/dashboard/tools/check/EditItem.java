package com.example.demo.application.controllers.dashboard.tools.check;

import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditItem {
    @FXML
    public TextField nome;
    @FXML
    public TextField valor;
    @FXML
    public TextField codigo;
    @FXML
    public VBox erroContainer;
    @FXML
    public Label erroMessage;

    private StockyDao stockyDao;
    private Stocky produtoAtual;

    private void hideError() {
        if (erroContainer != null) {
            erroContainer.setVisible(false);
        }
    }

    private void showError(String mensagem) {
        if (erroContainer != null && erroMessage != null) {
            erroMessage.setText(mensagem);
            erroContainer.setVisible(true);
        }
    }

    @FXML
    public void initialize() {
        hideError();
        codigo.setDisable(true); // O código não pode ser editado
    }

    // Metodo pra receber o produto
    public void setProduto(Stocky produto, StockyDao dao) {
        this.produtoAtual = produto;
        this.stockyDao = dao;

        // Preenche os campos com os dados do produto
        codigo.setText(String.format("%04d", produtoAtual.getId()));
        nome.setText(produtoAtual.getNome());
        valor.setText(String.format("%.2f", produtoAtual.getValor()).replace(".", ","));
    }

    public void salvarESair() {
        hideError();
        try {
            String nomeStr = nome.getText();
            String valorStr = valor.getText().replace(",", ".");

            if (nomeStr == null || nomeStr.trim().isEmpty() || valorStr.isEmpty()) {
                throw new Exception("Todos os campos devem ser preenchidos.");
            }

            double novoValor = Double.parseDouble(valorStr);

            // Atualiza o objeto em memória
            produtoAtual.setNome(nomeStr);
            produtoAtual.setValor(novoValor);

            //  alteração no banco de dados
            stockyDao.update(produtoAtual);

            // Fecha a janela
            sair();

        } catch (NumberFormatException e) {
            showError("O campo 'Valor' deve conter um número válido.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void sair() {
        nome.getScene().getWindow().hide();
    }


}
package com.example.demo.application.controllers.dashboard.tools.check;

import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RemoveItem {
    @FXML
    public Label nomeProduto;

    private StockyDao stockyDao;
    private Stocky produtoParaRemover;

    public void setProduto(Stocky produto, StockyDao dao) {
        this.produtoParaRemover = produto;
        this.stockyDao = dao;
        nomeProduto.setText(produto.getNome());
    }

    public void confirmar() {
        if (produtoParaRemover != null && stockyDao != null) {
            stockyDao.deleteById(produtoParaRemover.getId());
        }
        cancelar(); // fecha a janela após a remoção
    }

    public void cancelar() {
        nomeProduto.getScene().getWindow().hide();
    }
}
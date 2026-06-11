package com.example.demo.application.controllers.dashboard.tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class Add {
    public MenuButton menu;   // Lista suspensa de produtos
    public TextField quantidade;
    public TextField valor;
    public TextField codigo;

    @FXML
    public void initialize() {
        // Deve carregar todos os nomes dos produtos na lista suspensa.
    }

    public void salvarEContinuar() {
        // Deve salvar o produto.
        // Limpe a caixa de texto da quantidade e coloque os valores padrão "R$ 0,00", para o valor, e "0000", para o código.
    }

    public void salvarESair() {
        // Deve salvar o produto e voltar para a tela de dashboard.
    }

    public void sair() {
        // Deve voltar para a tela de dashboard.
    }
}

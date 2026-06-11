package com.example.demo.application.controllers.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Dashboard {
    public ListView Lista;    // Lista de visualização dos produtos

    @FXML
    public void initialize() {
        // Quando a tela for carregada, deve carregar os produtos do estoque na lista de visualização (veja como está feito no design do Figma para uma melhor disposição do conteúdo).
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

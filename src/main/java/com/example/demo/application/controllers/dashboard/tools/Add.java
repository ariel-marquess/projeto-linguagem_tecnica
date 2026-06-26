package com.example.demo.application.controllers.dashboard.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Add {
    public MenuButton menu;   // Lista suspensa de produtos
    public TextField quantidade;
    public TextField valor;
    public TextField codigo;

    public Label erroMessage;
    public VBox erroContainer;

    private void hideError(){
        erroContainer.setVisible(false);
        erroMessage.setVisible(false);
    }

    private void showError(String mensagem){
        erroContainer.setVisible(true);
        erroMessage.setText(mensagem);
        erroMessage.setVisible(true);
    }

    // Dopois que o usuário selecionar um produto no menu, pegue o nome produto pesquise seu valor e seu id no banco de dados e passe essas informações para o campo "valor" e o campo "codigo".
    // Utilize os métodos "getId" e "getValor" da classe StockyDaoJDBC (mas a implementação deste método não é feita diretamente por essa classe).

    @FXML
    public void initialize() {
        // Deve carregar todos os nomes dos produtos na lista suspensa.
        // Por padrão os valores de "valor" e "codigo" devem ser "R$ 0,00" e "0000", respectivamente.
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

package com.example.demo.application.controllers.dashboard.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import com.example.demo.dao.DaoFactory;
import com.example.demo.dao.StockyDao;
import com.example.demo.models.Stocky;



public class Remove {
    public MenuButton menu;  // Lista suspensa de produtos
    public TextField quantidade;
    public TextField valor;
    public TextField codigo;

    private StockyDao stockyDao;
    private Integer selectedId = null;

    public Label erroMessage;
    public VBox erroContainer;


    private boolean salvarMovimento() {
        hideError();

        if (selectedId == null) {
            showError("Selecione um produto.");
            return false;
        }

        String qtd = quantidade.getText().trim();
        try {
            if (qtd.isEmpty()) throw new Exception("Informe a quantidade.");
            int q = Integer.parseInt(qtd);
            if (q <= 0) throw new Exception("Quantidade deve ser maior que zero.");

            stockyDao.removeQuantidade(selectedId, q);
            return true;

        } catch (NumberFormatException nfe) {
            showError("Quantidade deve conter apenas números inteiros.");
            return false;

        } catch (Exception ex) {
            showError(ex.getMessage());
            return false;
        }
    }

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
        stockyDao = DaoFactory.createStockyDao();

        menu.getItems().clear();
        for (Stocky p : stockyDao.allProdutos()) {
            String rotulo = String.format("%04d", p.getId()) + " - " + p.getNome();
            javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(rotulo);
            item.setOnAction(e -> {
                hideError();
                selectedId = p.getId();
                menu.setText(p.getNome());
                codigo.setText(String.format("%04d", p.getId()));
                valor.setText(String.format("R$ %.2f", p.getValor()));
            });
            menu.getItems().add(item);
        }

        menu.setText("Selecionar produto");
        codigo.setText("0000");
        valor.setText("R$ 0,00");
    }

    public void salvarEContinuar() {
        if (salvarMovimento()) {
            quantidade.clear();
            selectedId = null;
            menu.setText("Selecionar produto");
            codigo.setText("0000");
            valor.setText("R$ 0,00");
        }
    }

    public void salvarESair() {
        if (salvarMovimento()) {
            menu.getScene().getWindow().hide();
        }    }

    public void sair() {
        menu.getScene().getWindow().hide();
    }
}

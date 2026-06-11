package com.example.demo.impl;

import com.example.demo.dao.StockyDao;

import java.sql.Connection;

public class StockyDaoJDBC implements StockyDao {
    private final Connection conn;

    public StockyDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    // Aqui devem ser estabelecidos os métodos de comunicação com o banco de dados. Métodos como listar os itens do bando, pesquisar um item específico, adicionar um novo item, etc.
    // Utilize a variável "conn" como a conexão com o banco.
}

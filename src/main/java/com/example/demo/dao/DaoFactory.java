package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.impl.StockyDaoJDBC;
import com.example.demo.exceptions.DbException;

public class DaoFactory {
    // Ponto unico para criar a DAO. Assim os controllers nao precisam conhecer StockyDaoJDBC nem DB.
    public static StockyDao createStockyDao() throws DbException {
        StockyDao dao = new StockyDaoJDBC(DB.getConnection());

        // Ao criar a DAO, ja garantimos que a tabela existe e possui dados iniciais.
        return dao;
    }
}

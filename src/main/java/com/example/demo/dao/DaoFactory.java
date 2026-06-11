package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.impl.StockyDaoJDBC;
import com.example.demo.exceptions.DbException;

public class DaoFactory {
    public static StockyDao createStockyDao() throws DbException {    // Cria a instância que contém os métodos de comunicação com o banco de dados
        return new StockyDaoJDBC(DB.getConnection());
    }
}

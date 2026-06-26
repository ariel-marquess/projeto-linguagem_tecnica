package com.example.demo.impl;

import com.example.demo.dao.StockyDao;
import com.example.demo.db.DB;
import com.example.demo.exceptions.DbException;
import com.example.demo.models.Stocky;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Implementacao JDBC da DAO. Esta classe concentra todos os comandos SQL do estoque.
public class StockyDaoJDBC implements StockyDao {
    private final Connection conn;
    private static final String TABLE = "stocky";

    public StockyDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public int lastId(){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            // COALESCE evita retornar null quando a tabela ainda estiver vazia.
            st = conn.prepareStatement("SELECT COALESCE(MAX(id), 0) AS last_id FROM " + TABLE);
            rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt("last_id");
            }

            return 0;
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public int getId(String produto){
        // Reaproveita a busca completa para nao duplicar SELECT por nome.
        Stocky stocky = getProduto(produto);
        return stocky == null ? 0 : readInt(stocky, "id");
    }

    @Override
    public double getValor(String produto){
        // Retorna 0 quando o produto nao existe, facilitando a exibicao padrao na tela.
        Stocky stocky = getProduto(produto);
        return stocky == null ? 0.0 : readDouble(stocky, "valor");
    }

    @Override
    public Stocky getProduto(String produto){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            // PreparedStatement com ? protege contra SQL injection e trata strings corretamente.
            st = conn.prepareStatement("SELECT id, nome, quantidade, valor FROM " + TABLE + " WHERE nome = ?");
            st.setString(1, produto);
            rs = st.executeQuery();

            if(rs.next()){
                return instantiateStocky(rs);
            }

            return null;
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Stocky getProdutoById(int id){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            // Busca por id, util para editar, excluir e validar movimentacoes de estoque.
            st = conn.prepareStatement("SELECT id, nome, quantidade, valor FROM " + TABLE + " WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()){
                return instantiateStocky(rs);
            }

            return null;
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Stocky> allProdutos(){
        PreparedStatement st = null;
        ResultSet rs = null;

        try{
            // Lista ordenada por nome para facilitar a leitura nas telas.
            st = conn.prepareStatement("SELECT id, nome, quantidade, valor FROM " + TABLE + " ORDER BY nome");
            rs = st.executeQuery();

            List<Stocky> produtos = new ArrayList<>();
            while(rs.next()){
                produtos.add(instantiateStocky(rs));
            }

            return produtos;
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public void insert(Stocky produto){
        PreparedStatement st = null;

        try{
            // RETURN_GENERATED_KEYS permite recuperar o id auto_increment criado pelo MySQL.
            st = conn.prepareStatement(
                    "INSERT INTO " + TABLE + " (nome, quantidade, valor) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            // Os valores sao lidos do objeto Stocky por getters ou campos com estes nomes.
            st.setString(1, readString(produto, "nome"));
            st.setInt(2, readInt(produto, "quantidade"));
            st.setDouble(3, readDouble(produto, "valor"));

            int rows = st.executeUpdate();
            if(rows == 0){
                throw new DbException("Nenhuma linha foi inserida.");
            }

            ResultSet keys = st.getGeneratedKeys();
            if(keys.next()){
                // Atualiza o objeto em memoria com o id gerado pelo banco.
                writeValue(produto, "id", keys.getInt(1));
            }
            DB.closeResultSet(keys);
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Stocky produto){
        PreparedStatement st = null;

        try {
            // Atualiza todas as informacoes editaveis do produto usando o id como identificador.
            st = conn.prepareStatement(
                    "UPDATE " + TABLE + " SET nome = ?, quantidade = ?, valor = ? WHERE id = ?"
            );

            st.setString(1, readString(produto, "nome"));
            st.setInt(2, readInt(produto, "quantidade"));
            st.setDouble(3, readDouble(produto, "valor"));
            st.setInt(4, readInt(produto, "id"));

            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(int id){
        PreparedStatement st = null;

        try{
            // Exclusao principal: o id e a forma mais segura de identificar um produto.
            st = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteByName(String produto){
        PreparedStatement st = null;

        try {
            // Atalho para telas que tenham apenas o nome selecionado.
            st = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE nome = ?");
            st.setString(1, produto);
            st.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void addQuantidade(int id, int quantidade){
        // Quantidades negativas ou zero nao representam entrada real de estoque.
        if(quantidade <= 0){
            throw new DbException("A quantidade adicionada deve ser maior que zero.");
        }

        updateQuantidade(id, quantidade);
    }

    @Override
    public void removeQuantidade(int id, int quantidade){
        // A remocao tambem precisa ser positiva; o sinal negativo e aplicado internamente.
        if(quantidade <= 0){
            throw new DbException("A quantidade removida deve ser maior que zero.");
        }

        // Antes de remover, verificamos se o produto existe e se ha estoque suficiente.
        Stocky produto = getProdutoById(id);
        if(produto == null){
            throw new DbException("Produto nao encontrado.");
        }

        int quantidadeAtual = readInt(produto, "quantidade");
        if(quantidadeAtual < quantidade){
            throw new DbException("Quantidade insuficiente em estoque.");
        }

        updateQuantidade(id, -quantidade);
    }

    @Override
    public void createStocky(){
        Statement st = null;
        PreparedStatement check = null;
        PreparedStatement seed = null;
        ResultSet rs = null;

        try {
            // Cria a tabela somente se ela ainda nao existir no banco configurado.
            st = conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "nome VARCHAR(100) NOT NULL, "
                    + "quantidade INT NOT NULL DEFAULT 0, "
                    + "valor DECIMAL(10,2) NOT NULL DEFAULT 0.00, "
                    + "PRIMARY KEY (id), "
                    + "UNIQUE KEY uk_stocky_nome (nome)"
                    + ")");

            // Verifica se a tabela esta vazia para evitar avancos no AUTO_INCREMENT a cada inicializacao.
            check = conn.prepareStatement("SELECT COUNT(*) FROM " + TABLE);
            rs = check.executeQuery();
            int total = 0;
            if (rs.next()) {
                total = rs.getInt(1);
            }
            DB.closeResultSet(rs);
            DB.closeStatement(check);

            if (total == 0) {
                // Insere os dados iniciais apenas uma vez (sem IGNORE), quando a tabela esta vazia.
                seed = conn.prepareStatement(
                        "INSERT INTO " + TABLE + " (nome, quantidade, valor) VALUES "
                                + "('Arroz', 20, 5.99), "
                                + "('Feijao', 15, 7.50), "
                                + "('Macarrao', 30, 3.25)"
                );
                seed.executeUpdate();
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(seed);
            // rs e check ja foram fechados acima em caso de sucesso; garanta fechamento tambem em fluxos alternativos.
            DB.closeResultSet(rs);
            DB.closeStatement(check);
            DB.closeStatement(st);
        }
    }

    private void updateQuantidade(int id, int delta){
        PreparedStatement st = null;

        try {
            // delta positivo adiciona estoque; delta negativo remove estoque.
            st = conn.prepareStatement("UPDATE " + TABLE + " SET quantidade = quantidade + ? WHERE id = ?");
            st.setInt(1, delta);
            st.setInt(2, id);

            int rows = st.executeUpdate();
            if(rows == 0){
                throw new DbException("Produto nao encontrado.");
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    private Stocky instantiateStocky(ResultSet rs) throws SQLException{
        try {
            // O Stocky deve ter construtor vazio; depois preenchemos os campos vindos do SELECT.
            Stocky stocky = Stocky.class.getDeclaredConstructor().newInstance();
            writeValue(stocky, "id", rs.getInt("id"));
            writeValue(stocky, "nome", rs.getString("nome"));
            writeValue(stocky, "quantidade", rs.getInt("quantidade"));
            writeValue(stocky, "valor", rs.getDouble("valor"));
            return stocky;
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e){
            throw new DbException("Nao foi possivel criar o objeto Stocky: " + e.getMessage());
        }
    }

    private String readString(Stocky stocky, String property){
        Object value = readValue(stocky, property);
        return value == null ? null : value.toString();
    }

    private int readInt(Stocky stocky, String property){
        Object value = readValue(stocky, property);
        if(value instanceof Number){
            return ((Number) value).intValue();
        }
        return Integer.parseInt(value.toString());
    }

    private double readDouble(Stocky stocky, String property){
        Object value = readValue(stocky, property);
        if(value instanceof Number){
            return ((Number) value).doubleValue();
        }
        return Double.parseDouble(value.toString().replace(",", "."));
    }

    private Object readValue(Stocky stocky, String property){
        String methodName = "get" + capitalize(property);

        try{
            // Primeiro tenta usar getter publico, por exemplo getNome().
            Method method = Stocky.class.getMethod(methodName);
            return method.invoke(stocky);
        } catch(NoSuchMethodException e){
            // Se nao houver getter, tenta acessar diretamente um campo chamado nome/id/etc.
            return readField(stocky, property);
        } catch(IllegalAccessException | InvocationTargetException e){
            throw new DbException("Nao foi possivel ler " + property + " de Stocky: " + e.getMessage());
        }
    }

    private Object readField(Stocky stocky, String property){
        try{
            Field field = Stocky.class.getDeclaredField(property);
            field.setAccessible(true);
            return field.get(stocky);
        } catch(NoSuchFieldException | IllegalAccessException e){
            throw new DbException("Stocky deve ter getter ou campo chamado '" + property + "'.");
        }
    }

    private void writeValue(Stocky stocky, String property, Object value){
        String methodName = "set" + capitalize(property);

        // Primeiro tenta usar setter publico, por exemplo setNome(String nome).
        for(Method method : Stocky.class.getMethods()){
            if(method.getName().equals(methodName) && method.getParameterCount() == 1){
                try {
                    method.invoke(stocky, value);
                    return;
                } catch(IllegalAccessException | InvocationTargetException e){
                    throw new DbException("Nao foi possivel escrever " + property + " em Stocky: " + e.getMessage());
                }
            }
        }

        // Se nao houver setter, tenta preencher diretamente o campo.
        writeField(stocky, property, value);
    }

    private void writeField(Stocky stocky, String property, Object value){
        try{
            Field field = Stocky.class.getDeclaredField(property);
            field.setAccessible(true);
            field.set(stocky, value);
        } catch(NoSuchFieldException | IllegalAccessException e){
            throw new DbException("Stocky deve ter setter ou campo chamado '" + property + "'.");
        }
    }

    private String capitalize(String value){
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}

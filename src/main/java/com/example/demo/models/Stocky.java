package com.example.demo.models;

public class Stocky {

    private int id;
    private String nome;
    private int quantidade;
    private double valor;

    public Stocky() {
        // Construtor vazio pra o DAO
    }

    // construtor
    public Stocky (int id, String nome, int quantidade, double valorUnitario) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valorUnitario;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    // um metodo que exibe os dados
    public void exibirDados() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor Unitário: R$ " + valor);
    }



    public static void main(String[] args) {
        Stocky produto = new Stocky (1, "Teclado", 10, 99.90);

        //usando getters
        System.out.println(produto.getNome());

        //usando setters
        produto.setQuantidade(20);

        produto.exibirDados();
    }
}
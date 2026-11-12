package modelo;

// ABSTRAÇÃO: O restaurante tem vários funcionários, mas não existe um "Funcionário" genérico.
public abstract class Funcionario {
    private int id;
    private String nome;

    public Funcionario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
}
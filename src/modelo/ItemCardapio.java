package modelo;

// ABSTRAÇÃO: Não podemos criar um "ItemCardapio" genérico, apenas Prato ou Bebida.
public abstract class ItemCardapio {
    private String nome;
    private double preco;
    private String diaDisponivel;

    public ItemCardapio(String nome, double preco, String diaDisponivel) {
        this.nome = nome;
        this.preco = preco;
        this.diaDisponivel = diaDisponivel;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getDiaDisponivel() { return diaDisponivel; }
}
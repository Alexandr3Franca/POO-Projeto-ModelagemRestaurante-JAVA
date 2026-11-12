package modelo;

// HERANÇA: Prato é um tipo de ItemCardapio
public class Prato extends ItemCardapio {
    private String pontoCarne;

    public Prato(String nome, double preco, String diaDisponivel, String pontoCarne) {
        super(nome, preco, diaDisponivel); // Chama o construtor da classe abstrata
        this.pontoCarne = pontoCarne;
    }

    public String getPontoCarne() { return pontoCarne; }
}
package modelo;

// HERANÇA: Bebida é um tipo de ItemCardapio
public class Bebida extends ItemCardapio {
    private boolean comGelo;

    public Bebida(String nome, double preco, String diaDisponivel, boolean comGelo) {
        super(nome, preco, diaDisponivel);
        this.comGelo = comGelo;
    }

    public boolean isComGelo() { return comGelo; }
}
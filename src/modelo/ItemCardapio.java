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

    public boolean verificarDisponibilidade(String diaAtual) {
        if (diaAtual == null || this.diaDisponivel == null) {
            return false;
        }
        return this.diaDisponivel.equalsIgnoreCase("Todos os dias")
                || this.diaDisponivel.equalsIgnoreCase(diaAtual);
    }

    public String getNome() { return nome; }
    public void setNome (String nome){
        this.nome =nome;
    }

    public double getPreco() { return preco; }
    public void setPreco(double preco){
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    public String getDiaDisponivel() { return diaDisponivel; }
    public void setDiaDisponivel(String diaDisponivel){
        this.diaDisponivel = diaDisponivel;
    }
}
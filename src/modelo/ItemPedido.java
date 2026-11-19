package modelo;

public class ItemPedido {
    private ItemCardapio item;
    private boolean devolvido;
    private String motivoDevolucao;

    public ItemPedido(ItemCardapio item) {
        this.item = item;
        this.devolvido = false;
        this.motivoDevolucao = "";
    }

    public void registrarDevolucao(String motivo) {
        this.devolvido = true;
        this.motivoDevolucao = motivo;
    }

    public ItemCardapio getItem() {
        return item;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public String getMotivoDevolucao() {
        return motivoDevolucao;
    }
}
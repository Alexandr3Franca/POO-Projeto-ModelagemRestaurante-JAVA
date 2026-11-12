package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    // POLIMORFISMO: O atendente pode ser um Garcom ou uma Telefonista
    private Funcionario atendente; 
    // AGREGAÇÃO: O pedido tem uma lista de itens do cardápio
    private List<ItemCardapio> itens; 
    private boolean devolvido;

    public Pedido(int id, Cliente cliente, Funcionario atendente) {
        this.id = id;
        this.cliente = cliente;
        this.atendente = atendente;
        this.itens = new ArrayList<>();
        this.devolvido = false;
    }

    public void adicionarItem(ItemCardapio item) {
        this.itens.add(item);
    }

    public void registrarDevolucao() {
        this.devolvido = true;
    }

    public void exibirResumo() {
        System.out.println("\n--- PEDIDO #" + id + " ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Atendido por: " + atendente.getNome());
        System.out.println("Itens:");
        for (ItemCardapio item : itens) {
            System.out.println(" - " + item.getNome() + " | R$ " + item.getPreco());
        }
        System.out.println("Status de Devolução: " + (devolvido ? "Sim" : "Não"));
    }

    // Método auxiliar para a Nota calcular o valor
    public double calcularTotal() {
        double total = 0;
        for (ItemCardapio item : itens) {
            total += item.getPreco();
        }
        return total;
    }
}
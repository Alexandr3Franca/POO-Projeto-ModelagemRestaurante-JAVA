package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Mesa mesa;
    // POLIMORFISMO: O atendente pode ser um Garcom ou uma Telefonista
    private Funcionario atendente; 
    // AGREGAÇÃO: O pedido tem uma lista de itens do cardápio
    private List<ItemPedido> itens; 
    private Endereco enderecoEntrega;
    private LocalDateTime horaPedido;
    private LocalDateTime horaEntrega;
    private LocalDateTime dataHoraAgendada;

    public Pedido(int id, Cliente cliente, Funcionario atendente) {
        this.id = id;
        this.cliente = cliente;
        this.atendente = atendente;
        this.itens = new ArrayList<>();
        this.horaPedido = LocalDateTime.now();
    }

    public void adicionarItem(ItemCardapio item) {
        this.itens.add(new ItemPedido(item));
    }

    public void registrarEntrega(LocalDateTime horaEntrega){
        this.horaEntrega = horaEntrega;
    }
    public long calcularTempoAtendimento() {
            if (horaPedido == null || horaEntrega == null) {
                return 0;
            }
            return Duration.between(horaPedido, horaEntrega).toMinutes();
        }

    // Método auxiliar para a Nota calcular o valor
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido itemPedido : itens) {
            if (!itemPedido.isDevolvido()) {
                total += itemPedido.getItem().getPreco();
            }
        }
        return total;
    }


    public void exibirResumo() {
        System.out.println("\n--- PEDIDO #" + id + " ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Atendido por: " + atendente.getNome());
        if (mesa != null) {
            System.out.println("Mesa: " + mesa.getNumero());
        }
        if (enderecoEntrega != null) {
            System.out.println("Entrega em: " + enderecoEntrega.getEnderecoCompleto());
        }
        if (dataHoraAgendada != null) {
            System.out.println("Agendado para: " + dataHoraAgendada);
        }
        System.out.println("Itens:");
        for (ItemPedido ip : itens) {
            String status = ip.isDevolvido() ? " [DEVOLVIDO: " + ip.getMotivoDevolucao() + "]" : "";
            System.out.println(" - " + ip.getItem().getNome() + " | R$ " + ip.getItem().getPreco() + status);
        }
        System.out.println("Total (itens não devolvidos): R$ " + calcularTotal());
        if (horaEntrega != null) {
            System.out.println("Tempo de atendimento: " + calcularTempoAtendimento() + " min");
        }
    }

    
    public int getId() {
    return id;
    }
    public void setId (int id){
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Funcionario getAtendente() {
        return atendente;
    }
    
    public LocalDateTime getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(LocalDateTime horaPedido) {
        this.horaPedido = horaPedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Mesa getMesa() {
        return mesa;
    }
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }
    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
}
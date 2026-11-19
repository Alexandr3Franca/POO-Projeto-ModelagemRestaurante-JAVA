package modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Nota {
    private Cliente cliente;
    private List<Pedido> pedidos;
    private double valorTotal;
    private double tempoMedioAtendimento;
    private long tempoPermanencia;

    public Nota(Cliente cliente, List<Pedido> pedidos) {
        this.cliente = cliente;
        this.pedidos = (pedidos != null) ? new ArrayList<>(pedidos) : new ArrayList<>();
        this.valorTotal = calcularTotal();
        this.tempoMedioAtendimento = calcularTempoMedioAtendimento();
        this.tempoPermanencia = calcularTempoPermanencia();
    }

    public double calcularTotal() {
        double total = 0;
        for (Pedido p : pedidos) {
            total += p.calcularTotal();
        }
        this.valorTotal = total;
        return this.valorTotal;
    }

    public double calcularTempoMedioAtendimento() {
        if (pedidos.isEmpty()) {
            this.tempoMedioAtendimento = 0;
            return 0;
        }
        long somaTempos = 0;
        for (Pedido p : pedidos) {
            somaTempos += p.calcularTempoAtendimento();
        }
        this.tempoMedioAtendimento = (double) somaTempos / pedidos.size();
        return this.tempoMedioAtendimento;
    }

    public long calcularTempoPermanencia() {
        for (Pedido p : pedidos) {
            if (p.getMesa() != null && p.getMesa().getHoraChegada() != null) {
                this.tempoPermanencia = Duration.between(p.getMesa().getHoraChegada(), LocalDateTime.now()).toMinutes();
                return this.tempoPermanencia;
            }
        }
        return this.tempoPermanencia;
    }

    // Polimorfismo na FormaPagamento + Regra do Gerente acionando o Manobrista
    public void emitirNota(FormaPagamento pagamento, Gerente gerente, Manobrista manobrista) {
        calcularTotal();
        calcularTempoMedioAtendimento();
        calcularTempoPermanencia();

        System.out.println("\n=== EMITINDO NOTA ===");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Valor Total: R$ " + String.format("%.2f", valorTotal));
        System.out.println("Tempo Médio de Atendimento: " + String.format("%.1f", tempoMedioAtendimento) + " min");
        System.out.println("Tempo de Permanência: " + tempoPermanencia + " min");

        boolean pago = pagamento.processarPagamento(valorTotal);

        if (pago) {
            System.out.println("Nota paga com sucesso!");
            if (gerente != null && manobrista != null) {
                gerente.ordenarBuscarAutomovel(manobrista, cliente);
            }
            for (Pedido p : pedidos) {
                if (p.getMesa() != null && p.getMesa().isOcupada()) {
                    p.getMesa().liberar();
                }
            }
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getTempoMedioAtendimento() {
        return tempoMedioAtendimento;
    }

    public long getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(long tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }
}
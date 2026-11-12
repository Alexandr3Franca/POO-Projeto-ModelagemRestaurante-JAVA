package modelo;

import java.util.List;

public class Nota {
    private double valorTotal;
    private int tempoAtendimento;
    private int tempoPermanencia;

    public Nota(int tempoAtendimento, int tempoPermanencia) {
        this.tempoAtendimento = tempoAtendimento;
        this.tempoPermanencia = tempoPermanencia;
    }

    public void calcularTotal(List<Pedido> pedidos) {
        this.valorTotal = 0;
        for (Pedido p : pedidos) {
            this.valorTotal += p.calcularTotal();
        }
    }

    // Aqui usamos a Interface! A Nota não se importa se é Cartão, Cheque ou Dinheiro.
    public void emitirNota(FormaPagamento pagamento) {
        System.out.println("\n=== EMITINDO NOTA ===");
        System.out.println("Valor Total: R$ " + valorTotal);
        System.out.println("Tempo de Atendimento: " + tempoAtendimento + " min");
        System.out.println("Tempo de Permanência: " + tempoPermanencia + " min");
        
        // O polimorfismo acontece aqui: ele chama o método da classe específica (Cartão/Dinheiro)
        boolean pago = pagamento.processarPagamento(valorTotal);
        
        if (pago) {
            System.out.println("Nota paga com sucesso!");
        }
    }
}
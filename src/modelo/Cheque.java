package modelo;

public class Cheque implements FormaPagamento {
    @Override
    public boolean processarPagamento(double valor) {
        System.out.println("Pagamento de R$ " + valor + " recebido via cheque.");
        return true;
    }
}
package modelo;

public class Dinheiro implements FormaPagamento {
    @Override
    public boolean processarPagamento(double valor) {
        System.out.println("Pagamento de R$ " + valor + " recebido em Dinheiro.");
        return true;
    }
}
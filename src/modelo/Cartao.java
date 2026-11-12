package modelo;

public class Cartao implements FormaPagamento {
    @Override
    public boolean processarPagamento(double valor) {
        System.out.println("Pagamento de R$ " + valor + " aprovado no Cartão.");
        return true;
    }
}
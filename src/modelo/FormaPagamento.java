package modelo;

// INTERFACE: É um "contrato". A Nota não sabe COMO o pagamento é feito, só sabe que qualquer 
// forma de pagamento DEVE ter o método processarPagamento.
public interface FormaPagamento {
    boolean processarPagamento(double valor);
}
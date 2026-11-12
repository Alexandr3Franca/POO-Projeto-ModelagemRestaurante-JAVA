package principal;

import modelo.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 1. Criando o ambiente
        Cliente cliente = new Cliente(1, "João Silva");
        Garcom garcom = new Garcom(10, "Carlos", false);
        
        // 2. Criando os itens do cardápio
        Prato file = new Prato("Filé Mal Passado", 50.0, "Segunda", "Mal Passado");
        Bebida suco = new Bebida("Suco de Laranja", 10.0, "Segunda", true);

        // 3. Criando o Pedido (Módulo da Entrega 1)
        Pedido pedido = new Pedido(100, cliente, garcom);
        pedido.adicionarItem(file);
        pedido.adicionarItem(suco);

        // 4. Simulando a regra de devolução
        // (Premissa atual: o cliente devolveu o pedido inteiro)
        // pedido.registrarDevolucao(); // Descomente para testar a devolução

        // 5. Mostrando o Resumo do Pedido
        pedido.exibirResumo();

        // 6. Emitindo a Nota
        Nota nota = new Nota(15, 45); // 15 min atendimento, 45 min permanência
        nota.calcularTotal(Arrays.asList(pedido)); // Passando uma lista com o pedido
        
        // Aqui testamos o Polimorfismo da Interface (trocando o Cartao por Dinheiro, por exemplo)
        FormaPagamento pagamentoCartao = new Cartao(); 
        nota.emitirNota(pagamentoCartao);
    }
}
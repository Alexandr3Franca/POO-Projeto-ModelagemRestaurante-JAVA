package principal;

import modelo.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static List<Cliente> clientes = new ArrayList<>();
    static List<Pedido> pedidos = new ArrayList<>();
    static List<ItemCardapio> cardapio = new ArrayList<>();
    static List<Funcionario> funcionarios = new ArrayList<>();
    
    static Gerente gerente;
    static Manobrista manobrista;
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        popularDadosIniciais();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Fazer Novo Pedido");
            System.out.println("2. Gerenciar Pedido Ativo (Add Item, Devolver, Pagar)");
            System.out.println("3. Emitir Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: fazerPedido(); break;
                case 2: gerenciarPedidoAtivo(); break;
                case 3: emitirRelatorios(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private static void fazerPedido() {
        System.out.print("Nome do Cliente: ");
        String nomeCliente = scanner.nextLine();
        Cliente c = new Cliente(clientes.size() + 1, nomeCliente);
        clientes.add(c);
        
        Garcom garcom = (Garcom) funcionarios.get(0);
        Pedido p = new Pedido(pedidos.size() + 1, c, garcom);
        
        Mesa mesa = new Mesa(pedidos.size() + 1); 
        mesa.ocupar(LocalDateTime.now());
        p.setMesa(mesa); 
        
        pedidos.add(p);
        System.out.println("Pedido #" + p.getId() + " criado para " + c.getNome() + " na Mesa " + mesa.getNumero() + "!");
    }

    private static void gerenciarPedidoAtivo() {
        // Filtra apenas pedidos não pagos
        List<Pedido> ativos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isPago()) ativos.add(p);
        }

        if (ativos.isEmpty()) {
            System.out.println("Nenhum pedido em aberto.");
            return;
        }

        System.out.println("\n--- PEDIDOS ATIVOS ---");
        for (int i = 0; i < ativos.size(); i++) {
            System.out.println((i + 1) + ". Pedido #" + ativos.get(i).getId() + " - " + ativos.get(i).getCliente().getNome());
        }
        System.out.print("Escolha o pedido (0 para voltar): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha <= 0 || escolha > ativos.size()) return;

        Pedido pedido = ativos.get(escolha - 1);

        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- GERENCIANDO PEDIDO #" + pedido.getId() + " (" + pedido.getCliente().getNome() + ") ---");
            System.out.println("1. Adicionar Item");
            System.out.println("2. Registrar Devolução");
            System.out.println("3. Emitir Nota e Pagar");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            subOpcao = scanner.nextInt();
            scanner.nextLine();

            switch (subOpcao) {
                case 1: adicionarItem(pedido); break;
                case 2: registrarDevolucao(pedido); break;
                case 3: emitirNota(pedido); subOpcao = 0; break; // Volta ao menu principal após pagar
            }
        }
    }

    private static void adicionarItem(Pedido pedido) {
        System.out.println("\n--- CARDÁPIO ---");
        for (int i = 0; i < cardapio.size(); i++) {
            System.out.println((i + 1) + ". " + cardapio.get(i).getNome() + " - R$ " + cardapio.get(i).getPreco());
        }
        System.out.print("Escolha: ");
        int esc = scanner.nextInt();
        if (esc > 0 && esc <= cardapio.size()) {
            pedido.adicionarItem(cardapio.get(esc - 1));
            System.out.println("Item adicionado!");
        }
    }

    private static void registrarDevolucao(Pedido pedido) {
        List<ItemPedido> itens = pedido.getItens();
        if (itens.isEmpty()) {
            System.out.println("O pedido não tem itens.");
            return;
        }
        System.out.println("\n--- DEVOLUÇÃO ---");
        for (int i = 0; i < itens.size(); i++) {
            String status = itens.get(i).isDevolvido() ? " [JÁ DEVOLVIDO]" : "";
            System.out.println((i + 1) + ". " + itens.get(i).getItem().getNome() + status);
        }
        System.out.print("Escolha o item: ");
        int esc = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        
        if (esc > 0 && esc <= itens.size()) {
            System.out.print("Motivo da devolução: ");
            String motivo = scanner.nextLine();
            itens.get(esc - 1).registrarDevolucao(motivo);
            System.out.println("Devolvido!");
        }
    }

    private static void emitirNota(Pedido pedido) {
        pedido.registrarEntrega(LocalDateTime.now().plusMinutes(15)); // Simula a entrega
        
        System.out.println("\n--- PAGAMENTO ---");
        System.out.println("1. Cartão | 2. Dinheiro");
        System.out.print("Opção: ");
        int esc = scanner.nextInt();
        FormaPagamento pagamento = (esc == 1) ? new Cartao() : new Dinheiro();
        
        List<Pedido> lista = new ArrayList<>();
        lista.add(pedido);
        
        Nota nota = new Nota(pedido.getCliente(), lista);
        nota.emitirNota(pagamento, gerente, manobrista);
        
        pedido.setPago(true); // Marca como pago, some da lista de ativos
    }

    // --- RELATÓRIOS AQUI! ---
    private static void emitirRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado no sistema.");
            return;
        }

        // RELATÓRIO A: Tempo médio de atendimento por garçom
        System.out.println("\n(a) Tempo Médio de Atendimento por Garçom:");
        double soma = 0;
        int count = 0;
        for (Pedido p : pedidos) {
            if (p.getAtendente() != null && !p.isPago() == false) { // Considera todos os pedidos (pagos e não pagos)
                soma += p.calcularTempoAtendimento();
                count++;
            }
        }
        if (count > 0) {
            System.out.printf("   Média Geral: %.1f minutos\n", (soma / count));
        } else {
            System.out.println("   Nenhum dado de atendimento registrado.");
        }

        // RELATÓRIO B: Pratos mais devolvidos
        System.out.println("\n(b) Itens mais devolvidos:");
        // Usamos um array para contar os itens
        List<String> nomesItens = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();

        for (Pedido p : pedidos) {
            for (ItemPedido ip : p.getItens()) {
                if (ip.isDevolvido()) {
                    String nome = ip.getItem().getNome();
                    int index = nomesItens.indexOf(nome);
                    if (index >= 0) {
                        contagem.set(index, contagem.get(index) + 1);
                    } else {
                        nomesItens.add(nome);
                        contagem.add(1);
                    }
                }
            }
        }
        
        if (nomesItens.isEmpty()) {
            System.out.println("   Nenhum item devolvido.");
        } else {
            for (int i = 0; i < nomesItens.size(); i++) {
                System.out.println("   " + nomesItens.get(i) + ": " + contagem.get(i) + " devolução(ões)");
            }
        }
    }

    private static void popularDadosIniciais() {
        cardapio.add(new Prato("Filé Mal Passado", 50.0, "Segunda", "Mal Passado"));
        cardapio.add(new Bebida("Suco de Laranja", 10.0, "Segunda", true));
        funcionarios.add(new Garcom(10, "Carlos Silva", false));
        gerente = new Gerente(2, "Roberto (Gerente)");
        manobrista = new Manobrista(3, "José (Manobrista)");
    }
}
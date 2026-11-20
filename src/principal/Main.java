package principal;

import modelo.*;
import java.time.LocalDate;
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
    static Entregador entregador;
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        popularDadosIniciais();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Fazer Novo Pedido (Mesa ou Entrega)");
            System.out.println("2. Gerenciar Pedido Ativo");
            System.out.println("3. Emitir Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1: fazerPedido(); break;
                case 2: gerenciarPedidoAtivo(); break;
                case 3: emitirRelatorios(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    // ============================================================
    // 1. FAZER PEDIDO (Mesa ou Entrega)
    // ============================================================
    private static void fazerPedido() {
        System.out.print("Nome do Cliente: ");
        String nomeCliente = scanner.nextLine();
        Cliente c = new Cliente(clientes.size() + 1, nomeCliente);
        clientes.add(c);
        
        Garcom garcom = (Garcom) funcionarios.get(0);
        Pedido p = new Pedido(pedidos.size() + 1, c, garcom);
        
        System.out.println("\nTipo de pedido:");
        System.out.println("1. Mesa");
        System.out.println("2. Entrega a domicílio");
        System.out.print("Opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        if (tipo == 1) {
            Mesa mesa = new Mesa(pedidos.size() + 1);
            mesa.ocupar(LocalDateTime.now());
            p.setMesa(mesa);
            System.out.println("Pedido #" + p.getId() + " criado para " + c.getNome() + " na Mesa " + mesa.getNumero() + "!");
        } else {
            // --- AQUI FOI AJUSTADO O NÚMERO COMO INT ---
            System.out.print("Rua: ");
            String rua = scanner.nextLine();
            System.out.print("Número: ");
            int numero = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            System.out.print("Bairro: ");
            String bairro = scanner.nextLine();
            
            Endereco endereco = new Endereco(rua, numero, bairro);
            p.setEnderecoEntrega(endereco);
            
            System.out.println("Pedido #" + p.getId() + " criado para ENTREGA em: " + endereco.getEnderecoCompleto());
        }
        
        pedidos.add(p);
    }

    // ============================================================
    // 2. GERENCIAR PEDIDO ATIVO
    // ============================================================
    private static void gerenciarPedidoAtivo() {
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
            Pedido p = ativos.get(i);
            String tipo = p.getMesa() != null ? "Mesa" : "Entrega";
            System.out.println((i + 1) + ". Pedido #" + p.getId() + " - " + p.getCliente().getNome() + " (" + tipo + ")");
        }
        System.out.print("Escolha o pedido (0 para voltar): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha <= 0 || escolha > ativos.size()) return;
        Pedido pedido = ativos.get(escolha - 1);

        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- PEDIDO #" + pedido.getId() + " (" + pedido.getCliente().getNome() + ") ---");
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
                case 3: emitirNota(pedido); subOpcao = 0; break;
            }
        }
    }

    // ============================================================
    // 3. ADICIONAR ITEM — VALIDAÇÃO DO DIA DA SEMANA
    // ============================================================
   private static void adicionarItem(Pedido pedido) {
    String hoje = getDiaDaSemanaHoje();
    System.out.println("\n--- CARDÁPIO (Hoje é " + hoje + ") ---");
    
    for (int i = 0; i < cardapio.size(); i++) {
        ItemCardapio item = cardapio.get(i);
        
        // REGRA: Se for "Todos", está sempre disponível
        boolean disponivel = item.getDiaDisponivel().equalsIgnoreCase("Todos")
                          || item.getDiaDisponivel().equalsIgnoreCase(hoje);
        
        String status = disponivel ? "DISPONÍVEL" : "INDISPONÍVEL hoje";
        System.out.println((i + 1) + ". " + item.getNome() + " - R$ " + item.getPreco() + " [" + status + "]");
    }
    
    System.out.print("Escolha: ");
    int esc = scanner.nextInt();
    scanner.nextLine();
    
    if (esc > 0 && esc <= cardapio.size()) {
        ItemCardapio itemEscolhido = cardapio.get(esc - 1);
        
        // VALIDAÇÃO: só bloqueia se NÃO for "Todos" E o dia não bater
        boolean disponivel = itemEscolhido.getDiaDisponivel().equalsIgnoreCase("Todos")
                          || itemEscolhido.getDiaDisponivel().equalsIgnoreCase(hoje);
        
        if (!disponivel) {
            System.out.println("❌ ERRO: '" + itemEscolhido.getNome() 
                + "' só está disponível " + itemEscolhido.getDiaDisponivel() + ".");
            return;
        }
        
        pedido.adicionarItem(itemEscolhido);
        System.out.println("✅ Item adicionado!");
    }
}                 

    private static String getDiaDaSemanaHoje() {
        switch (LocalDate.now().getDayOfWeek()) {
            case MONDAY: return "Segunda";
            case TUESDAY: return "Terça";
            case WEDNESDAY: return "Quarta";
            case THURSDAY: return "Quinta";
            case FRIDAY: return "Sexta";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return "";
        }
    }

    // ============================================================
    // 4. DEVOLUÇÃO
    // ============================================================
    private static void registrarDevolucao(Pedido pedido) {
        List<ItemPedido> itens = pedido.getItens();
        if (itens.isEmpty()) { System.out.println("O pedido não tem itens."); return; }
        
        System.out.println("\n--- DEVOLUÇÃO ---");
        for (int i = 0; i < itens.size(); i++) {
            String status = itens.get(i).isDevolvido() ? " [JÁ DEVOLVIDO]" : "";
            System.out.println((i + 1) + ". " + itens.get(i).getItem().getNome() + status);
        }
        System.out.print("Escolha o item: ");
        int esc = scanner.nextInt();
        scanner.nextLine();
        
        if (esc > 0 && esc <= itens.size()) {
            System.out.print("Motivo: ");
            String motivo = scanner.nextLine();
            itens.get(esc - 1).registrarDevolucao(motivo);
            System.out.println("Devolução registrada!");
        }
    }

    // ============================================================
    // 5. EMITIR NOTA — AQUI ENTRA A LÓGICA DO ENTREGADOR
    // ============================================================
    private static void emitirNota(Pedido pedido) {
        pedido.registrarEntrega(LocalDateTime.now().plusMinutes(15));
        
        System.out.println("\n--- PAGAMENTO ---");
        System.out.println("1. Cartão | 2. Dinheiro");
        System.out.print("Opção: ");
        int esc = scanner.nextInt();
        FormaPagamento pagamento = (esc == 1) ? new Cartao() : new Dinheiro();
        
        List<Pedido> lista = new ArrayList<>();
        lista.add(pedido);
        Nota nota = new Nota(pedido.getCliente(), lista);
        nota.emitirNota(pagamento, gerente, manobrista);
        
        pedido.setPago(true);

        // REGRA: Se for entrega, o Entregador recebe a rota!
        if (pedido.getEnderecoEntrega() != null) {
            List<Pedido> entregas = new ArrayList<>();
            entregas.add(pedido);
            System.out.println("\n[SISTEMA]: Acionando entregador...");
            entregador.realizarEntrega(entregas);
        }
    }

    // ============================================================
    // 6. RELATÓRIOS
    // ============================================================
    private static void emitirRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        if (pedidos.isEmpty()) { System.out.println("Nenhum pedido registrado."); return; }

        // (a) Tempo médio por garçom
        System.out.println("\n(a) Tempo Médio de Atendimento por Garçom:");
        double soma = 0; int count = 0;
        for (Pedido p : pedidos) {
            if (p.getAtendente() != null) { soma += p.calcularTempoAtendimento(); count++; }
        }
        if (count > 0) System.out.printf("   Média: %.1f min\n", (soma / count));
        else System.out.println("   Sem dados.");

        // (b) Itens mais devolvidos
        System.out.println("\n(b) Itens mais devolvidos:");
        List<String> nomes = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();
        for (Pedido p : pedidos) {
            for (ItemPedido ip : p.getItens()) {
                if (ip.isDevolvido()) {
                    String nome = ip.getItem().getNome();
                    int idx = nomes.indexOf(nome);
                    if (idx >= 0) contagem.set(idx, contagem.get(idx) + 1);
                    else { nomes.add(nome); contagem.add(1); }
                }
            }
        }
        if (nomes.isEmpty()) System.out.println("   Nenhum item devolvido.");
        else for (int i = 0; i < nomes.size(); i++) System.out.println("   " + nomes.get(i) + ": " + contagem.get(i) + "x");
    }

    // ============================================================
    // 7. DADOS INICIAIS
    // ============================================================
    private static void popularDadosIniciais() {
        // Cardápio de exemplo
        cardapio.add(new Prato("Filé Mal Passado", 50.0, "Todos", "Mal Passado"));
        cardapio.add(new Bebida("Suco de Laranja", 10.0, "Todos", true));
        cardapio.add(new Prato("Feijoada", 40.0, "Sabádo",   "Bem Passado")); // Só no sabádo!
        cardapio.add(new Prato("Risoto", 35.0, "Quarta", "Ao Ponto")); // Só na quarta!
        
        funcionarios.add(new Garcom(10, "Carlos Silva", false));
        gerente = new Gerente(2, "Roberto (Gerente)");
        manobrista = new Manobrista(3, "José (Manobrista)");
        
        // --- AQUI FOI AJUSTADO O CONSTRUTOR DO ENTREGADOR ---
        entregador = new Entregador(4, "Pedro (Entregador)", true);
    }
}
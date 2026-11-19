package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Entregador extends Funcionario {
    private String rotaAtual;
    private boolean disponivel;

    public Entregador(int id, String nome, boolean disponivel){
        super(id, nome);
        this.rotaAtual = " ";
        this.disponivel = true;
    }

    public  String getRotaAtual(){
        return rotaAtual;
    }
    public void setRotaAtual(String rotaAtual){
        this.rotaAtual = rotaAtual;
    }

    public boolean isDisponivel(){
        return disponivel;
    }
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }
    

    public void realizarEntrega(List<Pedido> pedidos) {
        if (!disponivel) {
            System.out.println("Entregador " + getNome() + " não está disponível no momento.");
            return;
        }

        this.disponivel = false;
        List<Pedido> rota = new ArrayList<>(pedidos);
        rota.sort(Comparator.comparing(p -> p.getEnderecoEntrega().getBairro()));

        StringBuilder sb = new StringBuilder();
        System.out.println("\n--- Rota do Entregador: " + getNome() + " ---");
        int parada = 1;
        for (Pedido p : rota) {
            String linha = parada++ + ". Pedido #" + p.getId() + " - " + p.getEnderecoEntrega().getEnderecoCompleto();
            System.out.println(linha);
            sb.append(linha).append(" | ");
        }

        this.rotaAtual = sb.toString();
        System.out.println("Entregas concluídas!\n");
        this.disponivel = true;
    }


    
}

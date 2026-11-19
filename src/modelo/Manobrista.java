package modelo;

public class Manobrista extends Funcionario{
    public Manobrista(int id, String nome ){
        super(id, nome);
    }

    public void buscarAutomovel(Cliente cliente){
        System.out.println("[Manobrista " + getNome() + "] Carro do cliente " + cliente.getNome() + "disponibilizado na portaria.");
    }
}

package modelo;

public class Gerente extends Funcionario{
    public Gerente(int id,  String nome) {
        super(id,nome);
    }

    public void emitirOrdem(Funcionario funcionario, String ordem){
        System.out.println("[Gerente " + getNome() + "-> " + funcionario.getNome() + "] Ordem: " + ordem);
    }

    public void ordenarBuscarAutomovel(Manobrista manobrista, Cliente cliente) {
        emitirOrdem(manobrista, "Trazer o automóvel do cliente " + cliente.getNome() + " para a portaria.");
        manobrista.buscarAutomovel(cliente);
    }
    
}

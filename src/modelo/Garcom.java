package modelo;

public class Garcom extends Funcionario {
    private boolean exclusivoBebida;

    public Garcom(int id, String nome, boolean exclusivoBebida) {
        super(id, nome);
        this.exclusivoBebida = exclusivoBebida;
    }

    public boolean isExclusivoBebida() { return exclusivoBebida; }
}
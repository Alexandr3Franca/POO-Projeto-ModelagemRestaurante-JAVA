package modelo;

public class Cliente {
    // Encapsulamento: atributos privados
    private int id;
    private String nome;
    private Endereco endereco;

    public Cliente(int id, String nome) {
        this(id, nome, null);
    }

    public Cliente(int id, String nome, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id){
        this.id = id;
    }

    public String getNome() { return nome; }
    public void setNome (String nome){
        this.nome = nome;
    }

    public Endereco getEndereco(){
        return endereco;
    }
    public void setEndereco(Endereco endereco){
        this.endereco = endereco;
    }


}

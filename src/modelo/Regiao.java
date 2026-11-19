package modelo;

import java.util.ArrayList;
import java.util.List;

public class Regiao {
    private String nome;
    private List<Mesa> mesas;
    private List<Garcom> garcons;

    public Regiao(String nome) {
        this.nome = nome;
        this.mesas = new ArrayList<>();
        this.garcons = new ArrayList<>();
    }

    public void adicionarMesa(Mesa mesa) {
        this.mesas.add(mesa);
    }

    public void adicionarGarcom(Garcom garcom) {
        this.garcons.add(garcom);
    }

    public boolean garcomAtendeMesa(Garcom garcom, Mesa mesa) {
        return this.garcons.contains(garcom) && this.mesas.contains(mesa);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }
    public void setMesas(List<Mesa> mesas) {
        if (mesas != null) {
            this.mesas = new ArrayList<>(mesas);
        }
    }

    public List<Garcom> getGarcons() {
        return garcons;
    }
    public void setGarcons(List<Garcom> garcons) {
        if (garcons != null) {
            this.garcons = new ArrayList<>(garcons);
        }
    }
}
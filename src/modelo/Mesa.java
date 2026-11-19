package modelo;

import java.time.LocalDateTime;

public class Mesa {
    private int numero;
    private boolean ocupada;
    private LocalDateTime horaChegada;

    public Mesa(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }


    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public LocalDateTime getHoraChegada() {
        return horaChegada;
    }
    
    public void ocupar(LocalDateTime chegada) {
        this.ocupada = true;
        this.horaChegada = chegada;
    }

    public void liberar() {
        this.ocupada = false;
        this.horaChegada = null;
    }
}
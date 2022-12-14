package view;

import java.util.Collection;

import modelo.Premio;

public class MaquinaView {
    private int nroMaquina;
    private int nroCasillas;
    private float recaudacion;
    private float recaudacionMin;
    private float costoJugada;
    private float creditoDisponible;
    private String[] frutas;
    private Collection<Premio> premiosDisponibles;

    public int getNroMaquina() {
        return nroMaquina;
    }

    public int getNroCasillas() {
        return nroCasillas;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    public float getRecaudacionMin() {
        return recaudacionMin;
    }

    public float getCostoJugada() {
        return costoJugada;
    }

    public float getCreditoDisponible() {
        return creditoDisponible;
    }

    public Collection<Premio> getPremiosDisponibles() {
        return premiosDisponibles;
    }

    public String[] getFrutas() {
        return frutas;
    }

    public MaquinaView(int nroMaquina, int nroCasillas, float recaudacion, float recaudacionMin, float costoJugada,
            float creditoDisponible, String[] frutas, Collection<Premio> premios) {
        this.nroMaquina = nroMaquina;
        this.nroCasillas = nroCasillas;
        this.recaudacion = recaudacion;
        this.recaudacionMin = recaudacionMin;
        this.costoJugada = costoJugada;
        this.creditoDisponible = creditoDisponible;
        this.frutas = frutas;
        this.premiosDisponibles = premios;
    }

    public MaquinaView() {
    }
}

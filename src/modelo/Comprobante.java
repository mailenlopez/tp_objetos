package modelo;

import java.util.Date;

public class Comprobante {

    private int nroMaquina;
    private float creditoDisponible;
    private Date fechaEmision;

    public int getNroMaquina() {
        return nroMaquina;
    }

    public float getCreditoDisponible() {
        return creditoDisponible;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public Comprobante(int nroMaquina, float creditoDisponible, Date fechaEmision) {
        this.nroMaquina = nroMaquina;
        this.creditoDisponible = creditoDisponible;
        this.fechaEmision = fechaEmision;
    }
}
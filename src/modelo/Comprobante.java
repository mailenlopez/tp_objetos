package modelo;

import java.util.Date;

public class Comprobante {

    private int nroMaquina;
    private int nroTicket;
    private float creditoDisponible;
    private Date fechaEmision;

    public Comprobante(int nroMaquina, int nroTicket, float creditoDisponible, Date fechaEmision) {
        this.nroMaquina = nroMaquina;
        this.nroTicket = nroTicket;
        this.creditoDisponible = creditoDisponible;
        this.fechaEmision = fechaEmision;
    }
}
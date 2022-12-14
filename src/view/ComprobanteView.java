package view;

import java.util.Date;

public class ComprobanteView {

    private int nroMaquina;
    private float creditoDisponible;
    private Date fechaEmision;

    public ComprobanteView() {
    }

    public ComprobanteView(int nroMaquina, float creditoDisp, Date fechaEmision) {
        this.nroMaquina = nroMaquina;
        this.creditoDisponible = creditoDisp;
        this.fechaEmision = fechaEmision;
    }

    public int getNroMaquina() {
        return nroMaquina;
    }

    public float getCreditoDisponible() {
        return creditoDisponible;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }
}

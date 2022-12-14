package view;

public class TicketView {
    private int nroTicket;
    private float importe;

    public float getImporte() {
        return importe;
    }

    public int getNroTicket() {
        return nroTicket;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public TicketView(int nroTicket, float importe) {

        this.nroTicket = nroTicket;
        this.importe = importe;
    }

    public TicketView() {
    }
}

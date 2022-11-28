package modelo;

public class Ticket {
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

	public Ticket(int nroTicket, float importe) {

		this.nroTicket = nroTicket;
		this.importe = importe;
	}

	public boolean SoyEseTicket(int nroTicket) {
		return (nroTicket == this.nroTicket);
	}
}

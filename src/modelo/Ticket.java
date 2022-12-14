package modelo;

import view.TicketView;

public class Ticket {
	private int nroTicket;
	private float importe;
	private boolean activo;

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

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
		this.activo = true;
	}

	public boolean SoyEseTicket(int nroTicket) {
		return (nroTicket == this.nroTicket);
	}

	public TicketView toView() {
		return new TicketView(nroTicket, importe);
	}
}

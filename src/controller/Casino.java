package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import modelo.Jugada;
import modelo.Maquina;
import modelo.Premio;
import modelo.Ticket;
import view.ComprobanteView;
import view.JugadaView;
import view.MaquinaView;
import view.PremioView;
import view.TicketView;

public class Casino {
	private Collection<Maquina> maquinas;
	private Collection<Ticket> tickets;
	private static Casino singleton;

	public static synchronized Casino getInstance() {
		if (singleton == null)
			singleton = new Casino();
		return singleton;
	}

	public Collection<MaquinaView> getMaquinas() {
		Collection<MaquinaView> maquinasViews = new ArrayList<MaquinaView>();
		maquinas.forEach(m -> maquinasViews.add(m.toView()));

		return maquinasViews;
	}

	public Collection<TicketView> getTickets() {
		Collection<TicketView> ticketsViews = new ArrayList<TicketView>();
		tickets.forEach(t -> ticketsViews.add(t.toView()));

		return ticketsViews;
	}

	public Casino() {
		super();
		maquinas = new ArrayList<Maquina>();
		tickets = new ArrayList<Ticket>();
	}

	public MaquinaView CrearMaquina(int nroCasillas, float recaudacion, float recaudacionMin, float costoJugada) {
		Maquina maquina = new Maquina(maquinas.size() + 1, nroCasillas, recaudacion, recaudacionMin, costoJugada);
		maquinas.add(maquina);

		return maquina.toView();
	}

	public MaquinaView ModificarMaquina(int nroMaquina, int nroCasillas, float recaudacion, float recaudacionMin,
			float costoJugada) {
		// Maquina maquina = BuscarMaquina(nroMaquina);
		Maquina maquina = maquinas.stream()
				.filter(s -> s.getNroMaquina() == nroMaquina)
				.findFirst().orElse(null);

		if (maquina != null) {
			maquina.ModificarMaquina(nroCasillas, recaudacion, recaudacionMin, costoJugada);
		}

		return maquina.toView();
	}

	public void AltaPremio(int nroMaquina, String[] combinacion, float dinero) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		if (maquina != null) {
			maquina.CargarPremio(combinacion, dinero);
		} else {
			String mensaje = "El premio no ha podido ser dado de alta ya que el número de máquina ingresado no corresponde a una máquina activa existente.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}
	}

	public void BajaPremio(int nroMaquina, int premioId) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		if (maquina != null) {
			maquina.DarBajaPremio(premioId);
		} else {
			String mensaje = "El premio no ha podido ser dado de baja ya que el número de máquina ingresado no corresponde a una máquina activa existente.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}
	}

	public PremioView BuscarPremioPorCombinacion(int nroMaquina, String[] combinacion) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		if (maquina != null) {
			Premio premio = maquina.BuscarPremioPorCombinacion(combinacion);
			if (premio != null) {
				return premio.toView();
			}
		}

		return null;
	}

	private Maquina BuscarMaquina(int nroMaquina) {
		for (Maquina m : maquinas) {
			if (m.SoyEsaMaquina(nroMaquina)) {
				return m;
			}
		}

		return null;
	}

	public MaquinaView ObtenerMaquina(int nroMaquina) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		if (maquina != null) {
			return maquina.toView();
		}

		return null;
	}

	public void CargarCreditoMaquina(int nroMaquina, int nroTicket) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		String mensaje = "";

		if (maquina != null) {
			Ticket ticket = BuscarTicket(nroTicket);
			if (ticket != null && ticket.isActivo()) {
				maquina.CargarCreditoDisponible(ticket.getImporte());
				ticket.setActivo(false);
				mensaje = "El ticket se ha cargado correctamente. Nuevo crédito disponible: $"
						+ maquina.getCreditoDisponible();
			} else if (ticket == null) {
				mensaje = "El ticket ingresado no existe.";

			} else if (!ticket.isActivo()) {
				mensaje = "El ticket no se pudo cargar porque ya ha sido utilizado.";
			}
		} else {
			mensaje = "La maquina ingresada no existe.";
		}

		System.out.print(mensaje);
		JOptionPane.showMessageDialog(null, mensaje);
	}

	private Ticket BuscarTicket(int nroTicket) {
		for (Ticket t : tickets) {
			if (t.SoyEseTicket(nroTicket)) {
				return t;
			}
		}

		return null;
	}

	public TicketView GenerarTicket(float dinero) {
		Ticket ticket = new Ticket(tickets.size() + 1, dinero);
		tickets.add(ticket);

		return ticket.toView();
	}

	public ComprobanteView EmitirComprobante(int nroMaquina) {
		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina != null) {
			return maquina.EmitirComprobante().toView();
		}

		String mensaje = "El número de máquina ingresado no corresponde a ningúna máquina activa existente.";
		System.out.print(mensaje);
		JOptionPane.showMessageDialog(null, mensaje);

		return null;
	}

	public JugadaView Jugar(int nroMaquina) {

		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina == null) {
			return null;
		}

		Jugada jugada = maquina.GenerarJugada();
		JugadaView jugadaView = jugada.toView();
		float premio = maquina.CalcularPremio();

		if (premio > 0) {
			jugadaView.setPremio(premio);
		}

		return jugadaView;
	}

}

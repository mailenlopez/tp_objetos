package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import modelo.Comprobante;
import modelo.Jugada;
import modelo.Maquina;
import modelo.Premio;
import modelo.Ticket;

public class Casino {
	private Collection<Maquina> maquinas;
	private Collection<Ticket> tickets;
	private static Casino singleton;

	public static synchronized Casino getInstance() {
		if (singleton == null)
			singleton = new Casino();
		return singleton;
	}

	public Collection<Maquina> getMaquinas() {
		return maquinas;
	}

	public Collection<Ticket> getTickets() {
		return tickets;
	}

	public Casino() {
		super();
		maquinas = new ArrayList<Maquina>();
		tickets = new ArrayList<Ticket>();
	}

	public Maquina CrearMaquina(int nroCasillas, float recaudacion, float recaudacionMin, float costoJugada) {
		Maquina maquina = new Maquina(maquinas.size() + 1, nroCasillas, recaudacion, recaudacionMin, costoJugada);
		maquinas.add(maquina);

		return maquina;
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

	public Maquina BuscarMaquina(int nroMaquina) {
		for (Maquina m : maquinas) {
			if (m.SoyEsaMaquina(nroMaquina)) {
				return m;
			}
		}

		return null;
	}

	public void CargarCreditoMaquina(int nroMaquina, int nroTicket) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		String mensaje;

		if (maquina != null) {
			Ticket ticket = BuscarTicket(nroTicket);
			if (ticket != null) {
				maquina.CargarCreditoDisponible(ticket.getImporte());
				mensaje = "El ticket se ha cargado correctamente. Nuevo crédito disponible: $"
						+ maquina.getCreditoDisponible();
				System.out.print(mensaje);
				JOptionPane.showMessageDialog(null, mensaje);
			} else {
				mensaje = "El ticket ingresado no existe.";
				System.out.print(mensaje);
				JOptionPane.showMessageDialog(null, mensaje);
			}
		} else {
			mensaje = "La maquina ingresada no existe.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}
	}

	private Ticket BuscarTicket(int nroTicket) {
		for (Ticket t : tickets) {
			if (t.SoyEseTicket(nroTicket)) {
				return t;
			}
		}

		return null;
	}

	public Ticket GenerarTicket(float dinero) {
		Ticket ticket = new Ticket(tickets.size() + 1, dinero);
		tickets.add(ticket);

		return ticket;
	}

	public Comprobante EmitirComprobante(int nroMaquina) {
		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina != null) {
			return maquina.EmitirComprobante();
		}

		String mensaje = "El número de máquina ingresado no corresponde a ningúna máquina activa existente.";
		System.out.print(mensaje);
		JOptionPane.showMessageDialog(null, mensaje);

		return null;
	}

	public Jugada Jugar(int nroMaquina) {

		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina == null) {
			return null;
		}

		Jugada jugada = maquina.GenerarJugada();
		// float premio = maquina.CalcularPremio();

		// System.out.print("Jugada obtenida: " +
		// Arrays.toString(jugada.getCombinacion()));

		// if (premio > 0) {
		// System.out.print("Premio obtenido!: Ha ganado $" + premio);
		// }

		return jugada;
	}

	public float EsPremiado(int nroMaquina, Jugada jugada) {
		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina != null) {
			Premio premio = maquina.BuscarPremioPorCombinacion(jugada.getCombinacion());
			if (premio != null) {
				JOptionPane.showMessageDialog(null, "Premio obtenido: $" + premio.getDinero());
				return premio.getDinero();
			}
		}

		JOptionPane.showMessageDialog(null, "Sin premio.");
		return 0;
	}
}

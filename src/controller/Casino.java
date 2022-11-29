package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import modelo.Comprobante;
import modelo.Jugada;
import modelo.Maquina;
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
			System.out.print(
					"El premio no ha podido ser dado de alta ya que el número de máquina ingresado no corresponde a una máquina activa existente.");
		}
	}

	public void BajaPremio(int nroMaquina, String[] combinacion) {
		Maquina maquina = BuscarMaquina(nroMaquina);
		if (maquina != null) {
			maquina.DarBajaPremio(combinacion);
		} else {
			System.out.print(
					"El premio no ha podido ser dado de baja ya que el número de máquina ingresado no corresponde a una máquina activa existente.");
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

		if (maquina != null) {
			Ticket ticket = BuscarTicket(nroTicket);
			if (ticket != null) {
				maquina.CargarCreditoDisponible(ticket.getImporte());
			} else {
				System.out.print("El ticket ingresado no existe.");
			}
		} else {
			System.out.print("La maquina ingresada no existe.");
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

		System.out.print("El número de máquina ingresado no corresponde a ningúna máquina activa existente.");
		return null;
	}

	public Jugada Jugar(int nroMaquina) {
		Maquina maquina = BuscarMaquina(nroMaquina);

		if (maquina != null) {
			maquina.GenerarJugada();
			float premio = maquina.CalcularPremio();
			Jugada jugada = maquina.getUltimaJugada();

			System.out.print("Jugada obtenida: " + Arrays.toString(jugada.getCombinacion()));

			if (premio > 0) {
				System.out.print("Premio obtenido!: Ha ganado $" + premio);
			}

			return jugada;
		}

		return null;
	}
}

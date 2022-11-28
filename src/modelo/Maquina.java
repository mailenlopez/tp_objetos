package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SuppressWarnings("unused")

public class Maquina {
	private int nroMaquina;
	private int nroCasillas;
	private float recaudacion;
	private float recaudacionMin;
	private float costoJugada;
	private float creditoDisponible;
	private String[] frutas;
	private Collection<Premio> premiosDisponibles;
	private Jugada jugada;

	// Getters
	public int getNroMaquina() {
		return nroMaquina;
	}

	public int getNroCasillas() {
		return nroCasillas;
	}

	public Collection<Premio> getPremiosDisponibles() {
		return premiosDisponibles;
	}

	public Maquina(int nroMaquina, int nroCasillas, float recaudacion, float recaudacionMin, float costoJugada) {
		this.nroMaquina = nroMaquina;
		this.nroCasillas = nroCasillas;
		this.recaudacion = recaudacion;
		this.recaudacionMin = recaudacionMin;
		this.costoJugada = costoJugada;
		this.creditoDisponible = 0;
		this.frutas = new String[] { "Manzana", "Guinda", "Banana", "Frutilla", "Uva", "Sandia" };
		this.premiosDisponibles = new ArrayList<Premio>();
		this.jugada = new Jugada();
	}

	public boolean SoyEsaMaquina(int nroMaquina) {
		return (nroMaquina == this.nroMaquina);
	}

	private boolean TengoEseNroCasillas(int nroCasillas) {
		return (nroCasillas == this.nroCasillas);
	}

	public float CalcularPremio() {
		for (Premio p : premiosDisponibles) {
			if (p.SoyEstaCombinacion(jugada.getCombinacion())) {
				float dineroPremio = p.getDinero();
				if (dineroPremio >= recaudacion) {
					ReducirRecaudacion(dineroPremio);
					CargarCreditoDisponible(dineroPremio);

					return dineroPremio;
				} else {
					System.out.print("La máquina no tiene recaudación suficiente para pagar el dinero de este premio.");
					break;
				}
			}
		}
		return 0;
	}

	private void ReducirRecaudacion(float dineroPremio) {
		recaudacion -= dineroPremio;
	}

	public Jugada getUltimaJugada() {
		return jugada;
	}

	public void DarBajaPremio(String[] frutas) {
		boolean existePremio = false;

		for (Premio p : premiosDisponibles) {
			if (p.SoyEstaCombinacion(frutas)) {
				existePremio = true;
				premiosDisponibles.remove(p);
				break;
			}
		}

		if (!existePremio) {
			System.out.print("El premio no ha sido encontrado en la máquina ingresada.");
		}
	}

	public void CargarPremio(String[] combinacionFrutas, float dineroPremio) {
		if (combinacionFrutas.length == nroCasillas) {
			Premio nuevoPremio = new Premio(combinacionFrutas, dineroPremio);
			premiosDisponibles.add(nuevoPremio);
		} else {
			System.out.print(
					"El número de frutas de las combinaciones premiadas debe coincidir con el número de casillas de la máquina.");
		}
	}

	public void CargarCreditoDisponible(float credito) {
		this.creditoDisponible += credito;
	}

	public void GenerarJugada() {
		if (recaudacion <= recaudacionMin) {
			System.out.print(
					"La máquina ha alcanzado su recaudación mínima. Existe la posibilidad de no poder pagar los próximos premios.");
		}

		if (creditoDisponible >= costoJugada) {
			recaudacion += costoJugada;
			creditoDisponible -= costoJugada;
			jugada.GenerarCombinacion(nroCasillas, frutas);
		}
	}

	private void LimpiarSesionMaquina() {
		creditoDisponible = 0;
		jugada = new Jugada();
	}

	public Comprobante EmitirComprobante() {
		Comprobante comprobante = new Comprobante(nroMaquina, nroCasillas, creditoDisponible, new Date());
		LimpiarSesionMaquina();

		return comprobante;
	}
}
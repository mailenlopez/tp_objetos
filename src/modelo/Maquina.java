package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

@SuppressWarnings("unused")

public class Maquina {
	private int nroMaquina;

	public void setNroMaquina(int nroMaquina) {
		this.nroMaquina = nroMaquina;
	}

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

	public float getRecaudacion() {
		return recaudacion;
	}

	public float getRecaudacionMin() {
		return recaudacionMin;
	}

	public float getCostoJugada() {
		return costoJugada;
	}

	public float getCreditoDisponible() {
		return creditoDisponible;
	}

	public Collection<Premio> getPremiosDisponibles() {
		return premiosDisponibles;
	}

	public String[] getFrutas() {
		return frutas;
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
				if (recaudacion >= dineroPremio) {
					ReducirRecaudacion(dineroPremio);
					CargarCreditoDisponible(dineroPremio);

					return dineroPremio;
				} else {
					String mensaje = "La máquina no tiene recaudación suficiente para pagar el dinero de este premio.";
					System.out.print(mensaje);
					JOptionPane.showMessageDialog(null, mensaje);
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

	public void DarBajaPremio(int nroPremio) {
		boolean existePremio = false;

		Premio premio = BuscarPremioPorId(nroPremio);
		if (premio != null) {
			premiosDisponibles.remove(premio);
		} else {
			String mensaje = "El premio no ha sido encontrado en la máquina ingresada.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}
	}

	public Premio CargarPremio(String[] combinacionFrutas, float dineroPremio) {
		if (combinacionFrutas.length == nroCasillas) {
			Premio nuevoPremio = new Premio(premiosDisponibles.size() + 1, combinacionFrutas, dineroPremio);
			premiosDisponibles.add(nuevoPremio);
			return nuevoPremio;
		} else {
			String mensaje = "El número de frutas de las combinaciones premiadas debe coincidir con el número de casillas de la máquina.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
			return null;
		}
	}

	public void CargarCreditoDisponible(float credito) {
		this.creditoDisponible += credito;
	}

	public Jugada GenerarJugada() {
		String mensaje;

		if (recaudacion <= recaudacionMin) {
			mensaje = "La máquina ha alcanzado su recaudación mínima. Existe la posibilidad de no poder pagar los próximos premios.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}

		if (creditoDisponible >= costoJugada) {
			recaudacion += costoJugada;
			creditoDisponible -= costoJugada;
			jugada.GenerarCombinacion(nroCasillas, frutas);
		} else {
			mensaje = "El crédito disponible no alcanza para realizar una jugada.";
			System.out.print(mensaje);
			JOptionPane.showMessageDialog(null, mensaje);
		}

		return jugada;
	}

	private void LimpiarSesionMaquina() {
		creditoDisponible = 0;
		jugada = new Jugada();
	}

	public Comprobante EmitirComprobante() {
		Comprobante comprobante = new Comprobante(nroMaquina, creditoDisponible, new Date());
		LimpiarSesionMaquina();

		return comprobante;
	}

	public Premio BuscarPremioPorCombinacion(String[] frutas) {
		for (Premio p : premiosDisponibles) {
			if (p.SoyEstaCombinacion(frutas)) {
				return p;
			}
		}

		return null;
	}

	public Premio BuscarPremioPorId(int id) {
		for (Premio p : premiosDisponibles) {
			if (p.SoyEstePremio(id)) {
				return p;
			}
		}

		return null;
	}

	public void ModificarMaquina(int nroCasillas, float recaudacion, float recaudacionMin, float costoJugada) {
		this.nroCasillas = nroCasillas;
		this.recaudacion = recaudacion;
		this.recaudacionMin = recaudacionMin;
		this.costoJugada = costoJugada;
	}
}
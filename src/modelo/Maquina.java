package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import view.MaquinaView;

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

	public void setNroMaquina(int nroMaquina) {
		this.nroMaquina = nroMaquina;
	}

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

	public Jugada getUltimaJugada() {
		return jugada;
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

	public MaquinaView toView() {
		return new MaquinaView(nroMaquina, nroCasillas, recaudacion, recaudacionMin, costoJugada, creditoDisponible,
				frutas, premiosDisponibles);
	}

	public boolean SoyEsaMaquina(int nroMaquina) {
		return (nroMaquina == this.nroMaquina);
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

	public Premio CargarPremio(String[] combinacion, float dineroPremio) {
		if (combinacion.length == nroCasillas) {
			Premio nuevoPremio = new Premio(premiosDisponibles.size() + 1, combinacion, dineroPremio);
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

	public Premio BuscarPremioPorId(int nroPremio) {
		for (Premio p : premiosDisponibles) {
			if (p.SoyEstePremio(nroPremio)) {
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
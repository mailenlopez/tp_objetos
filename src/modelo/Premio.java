package modelo;

import java.util.Arrays;

public class Premio {
	private int id;
	private String[] combinacionGanadora;
	private float dinero;

	public int getId() {
		return id;
	}

	public String[] getCombinacionGanadora() {
		return combinacionGanadora;
	}

	public float getDinero() {
		return dinero;
	}

	public Premio(int id, String[] combinacionGanadora, float dinero) {
		this.id = id;
		this.combinacionGanadora = combinacionGanadora;
		this.dinero = dinero;
	}

	public boolean SoyEstaCombinacion(String[] combinacion) {
		return Arrays.equals(combinacion, combinacionGanadora);
	}

	public boolean SoyEstePremio(int premioId) {
		return (this.id == premioId);
	}
}

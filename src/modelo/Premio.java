package modelo;

import java.util.Arrays;

public class Premio {

	private String[] combinacionGanadora;
	private float dinero;

	public String[] getCombinacionGanadora() {
		return combinacionGanadora;
	}

	public float getDinero() {
		return dinero;
	}

	public Premio(String[] combinacionGanadora, float dinero) {
		this.combinacionGanadora = combinacionGanadora;
		this.dinero = dinero;
	}

	public boolean SoyEstaCombinacion(String[] combinacion) {
		return Arrays.equals(combinacion, combinacionGanadora);
	}
}

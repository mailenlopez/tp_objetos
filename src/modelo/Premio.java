package modelo;

import java.util.Arrays;

import view.PremioView;

public class Premio {
	private int nroPremio;
	private String[] combinacionGanadora;
	private float dinero;

	public int getId() {
		return nroPremio;
	}

	public String[] getCombinacionGanadora() {
		return combinacionGanadora;
	}

	public float getDinero() {
		return dinero;
	}

	public Premio(int nroPremio, String[] combinacionGanadora, float dinero) {
		this.nroPremio = nroPremio;
		this.combinacionGanadora = combinacionGanadora;
		this.dinero = dinero;
	}

	public boolean SoyEstaCombinacion(String[] combinacion) {
		return Arrays.equals(combinacion, combinacionGanadora);
	}

	public boolean SoyEstePremio(int nroPremio) {
		return (this.nroPremio == nroPremio);
	}

	public PremioView toView() {
		return new PremioView(nroPremio, combinacionGanadora, dinero);
	}
}

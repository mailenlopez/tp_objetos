package modelo;

import view.JugadaView;

public class Jugada {

	private String[] combinacion;

	public String[] getCombinacion() {
		return combinacion;
	}

	public void GenerarCombinacion(int nroCasillas, String[] frutas) {
		combinacion = new String[nroCasillas];

		for (int i = 0; i < nroCasillas; i++) {
			combinacion[i] = frutas[(int) (Math.random() * nroCasillas)];
		}
	}

	public JugadaView toView() {
		return new JugadaView(combinacion);
	}
}

package view;

public class PremioView {
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

	public PremioView(int id, String[] combinacionGanadora, float dinero) {
		this.id = id;
		this.combinacionGanadora = combinacionGanadora;
		this.dinero = dinero;
	}

	public PremioView() {
	}
}

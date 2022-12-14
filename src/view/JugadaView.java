package view;

public class JugadaView {

    private String[] combinacion;
    private float premio;

    public JugadaView() {
    }

    public JugadaView(String[] combinacion) {
        this.combinacion = combinacion;
    }

    public String[] getCombinacion() {
        return combinacion;
    }

    public float getPremio() {
        return premio;
    }

    public void setPremio(float premio) {
        this.premio = premio;
    }
}

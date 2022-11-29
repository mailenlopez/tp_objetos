package modelo;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;

    private int nroMaquina;
    private int nroCasillas;
    private float recaudacion;
    private float recaudacionMin;
    private float costoJugada;
    private float creditoDisponible;

    public int getNroMaquina() {
        return nroMaquina;
    }

    public void setNroMaquina(int nroMaquina) {
        this.nroMaquina = nroMaquina;
    }

    public int getNroCasillas() {
        return nroCasillas;
    }

    public void setNroCasillas(int nroCasillas) {
        this.nroCasillas = nroCasillas;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(float recaudacion) {
        this.recaudacion = recaudacion;
    }

    public float getRecaudacionMin() {
        return recaudacionMin;
    }

    public void setRecaudacionMin(float recaudacionMin) {
        this.recaudacionMin = recaudacionMin;
    }

    public float getCostoJugada() {
        return costoJugada;
    }

    public void setCostoJugada(float costoJugada) {
        this.costoJugada = costoJugada;
    }

    public float getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(float creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}

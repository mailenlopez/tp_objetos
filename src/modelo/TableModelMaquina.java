package modelo;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

public class TableModelMaquina extends AbstractTableModel {

    private static final String[] columnNames = { "Id", "Casillas", "Recaudación", "Recaudación Min", "Costo" };
    private LinkedList<Maquina> list;

    public TableModelMaquina() {
        list = new LinkedList<Maquina>();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getNroMaquina();
            case 1:
                return list.get(rowIndex).getNroCasillas();
            case 2:
                return list.get(rowIndex).getRecaudacion();
            case 3:
                return list.get(rowIndex).getRecaudacionMin();
            case 4:
                return list.get(rowIndex).getCostoJugada();
            default:
                return null;
        }
    }

    public void addElement(Maquina e) {
        // Adds the element in the last position in the list
        list.add(e);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }
}

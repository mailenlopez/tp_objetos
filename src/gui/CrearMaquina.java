package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.PlainDocument;

import controller.Casino;
import filter.IntFilter;
import modelo.Maquina;

import java.awt.event.WindowEvent;
import java.util.Collection;
import java.awt.event.WindowAdapter;

public class CrearMaquina extends JDialog implements ActionListener {
    private Maquina maquina;
    private VentanaPrincipal ventanaPrincipal;
    private JLabel lblNumeroCasillas, lblRecaudacion, lblRecaudacionMin, lblPrecioJugada;
    private JTextField txtNumeroCasillas, txtRecaudacion, txtRecaudacionMin, txtPrecioJugada;
    private JPanel jpMainPanel;

    public CrearMaquina(VentanaPrincipal _ventanaPrincipal) {
        super(_ventanaPrincipal, true);
        ventanaPrincipal = _ventanaPrincipal;

        IniciarlizarVentana();
        InicializarComponentes();
    }

    public CrearMaquina(VentanaPrincipal _ventanaPrincipal, Maquina _maquina) {
        super(_ventanaPrincipal, true);
        ventanaPrincipal = _ventanaPrincipal;
        maquina = _maquina;

        IniciarlizarVentana();
        InicializarComponentes();
    }

    private void IniciarlizarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void InicializarComponentes() {

        lblNumeroCasillas = new JLabel("Casillas: ");
        txtNumeroCasillas = new JTextField();
        PlainDocument docCasillas = (PlainDocument) txtNumeroCasillas.getDocument();
        docCasillas.setDocumentFilter(new IntFilter());

        lblRecaudacion = new JLabel("Recaudación total: ");
        txtRecaudacion = new JTextField();
        PlainDocument docRecaudacion = (PlainDocument) txtRecaudacion.getDocument();
        docRecaudacion.setDocumentFilter(new IntFilter());

        lblRecaudacionMin = new JLabel("Recaudación min: ");
        txtRecaudacionMin = new JTextField();
        PlainDocument docRecaudacionMin = (PlainDocument) txtRecaudacionMin.getDocument();
        docRecaudacionMin.setDocumentFilter(new IntFilter());

        lblPrecioJugada = new JLabel("Costo jugada: ");
        txtPrecioJugada = new JTextField();
        PlainDocument docPrecioJugada = (PlainDocument) txtPrecioJugada.getDocument();
        docPrecioJugada.setDocumentFilter(new IntFilter());

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                GuardarMaquina();
            }

        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);

        JPanel jpForm = new JPanel();
        jpForm.setLayout(new GridLayout(5, 2, 20, 20));

        jpForm.add(lblNumeroCasillas);
        jpForm.add(txtNumeroCasillas);
        jpForm.add(lblRecaudacion);
        jpForm.add(txtRecaudacion);
        jpForm.add(lblRecaudacionMin);
        jpForm.add(txtRecaudacionMin);
        jpForm.add(lblPrecioJugada);
        jpForm.add(txtPrecioJugada);
        jpForm.add(btnAceptar);
        jpForm.add(btnCancelar);

        jpMainPanel = new JPanel();
        jpMainPanel.setLayout(new BorderLayout());
        jpMainPanel.add(jpForm, BorderLayout.NORTH);
        jpMainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        add(jpMainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    private void GuardarMaquina() {

        Maquina maquina = ventanaPrincipal.getCasino().CrearMaquina(
                Integer.valueOf(txtNumeroCasillas.getText()),
                Float.valueOf(txtRecaudacion.getText()),
                Float.valueOf(txtRecaudacionMin.getText()),
                Float.valueOf(txtPrecioJugada.getText()));

        if (maquina != null) {
            JOptionPane.showMessageDialog(jpMainPanel, "La maquina ha sido guardada.");
            LimpiarInputs();
        } else {
            JOptionPane.showMessageDialog(jpMainPanel, "La maquina no pudo ser almacenada.");
        }

    }

    private void LimpiarInputs() {
        txtNumeroCasillas.setText("");
        txtRecaudacion.setText("");
        txtRecaudacionMin.setText("");
        txtPrecioJugada.setText("");
    }
}

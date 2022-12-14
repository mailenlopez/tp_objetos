package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import controller.Casino;
import filter.IntFilter;
import view.MaquinaView;

public class VentanaCrearMaquina extends JDialog implements ActionListener {
    private MaquinaView maquina;
    private VentanaPrincipal ventanaPrincipal;
    private Casino casino;
    private JLabel lblNumeroCasillas, lblRecaudacion, lblRecaudacionMin, lblPrecioJugada;
    private JTextField txtNumeroCasillas, txtRecaudacion, txtRecaudacionMin, txtPrecioJugada;
    private JPanel jpMainPanel;

    public VentanaCrearMaquina(VentanaPrincipal _ventanaPrincipal) {
        super(_ventanaPrincipal, true);
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
        IniciarlizarVentana();
        InicializarComponentes();
    }

    public VentanaCrearMaquina(VentanaPrincipal _ventanaPrincipal, MaquinaView _maquina) {
        super(_ventanaPrincipal, true);
        maquina = _maquina;
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();

        IniciarlizarVentana();
        InicializarComponentes();
        InicializarMaquina(maquina);
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
        btnAceptar.setName("btnAceptar");
        btnAceptar.addActionListener(this);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setName("btnCancelar");
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

    private void InicializarMaquina(MaquinaView maquina) {
        txtNumeroCasillas.setText(String.valueOf(maquina.getNroCasillas()));
        txtRecaudacion.setText(String.valueOf((int) maquina.getRecaudacion()));
        txtRecaudacionMin.setText(String.valueOf((int) maquina.getRecaudacionMin()));
        txtPrecioJugada.setText(String.valueOf((int) maquina.getCostoJugada()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        switch (btnClicked.getName()) {
            case "btnAceptar":
                GuardarMaquina();
                break;
            case "btnCancelar":
                Volver();
                break;
        }
    }

    private void GuardarMaquina() {
        Integer nroCasillas = Integer.valueOf(txtNumeroCasillas.getText());
        Float recaudacion = Float.valueOf(txtRecaudacion.getText());
        Float recaudacionMin = Float.valueOf(txtRecaudacionMin.getText());
        Float precioJugada = Float.valueOf(txtPrecioJugada.getText());

        if (maquina != null) {
            casino.ModificarMaquina(maquina.getNroMaquina(),
                    nroCasillas,
                    recaudacion,
                    recaudacionMin,
                    precioJugada);
        } else {
            maquina = casino.CrearMaquina(
                    nroCasillas,
                    recaudacion,
                    recaudacionMin,
                    precioJugada);
        }

        if (maquina != null) {
            JOptionPane.showMessageDialog(jpMainPanel, "La maquina ha sido guardada.");
            LimpiarInputs();
        } else {
            JOptionPane.showMessageDialog(jpMainPanel, "La maquina no pudo ser almacenada.");
        }

        Volver();
    }

    private void Volver() {
        dispose();
        VentanaMaquinas ventanaMaquinas = new VentanaMaquinas(ventanaPrincipal);
        ventanaMaquinas.setVisible(true);
    }

    private void LimpiarInputs() {
        txtNumeroCasillas.setText("");
        txtRecaudacion.setText("");
        txtRecaudacionMin.setText("");
        txtPrecioJugada.setText("");
    }
}

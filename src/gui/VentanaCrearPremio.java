package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import filter.IntFilter;
import modelo.Maquina;
import modelo.Premio;

public class VentanaCrearPremio extends JDialog implements ActionListener {
    private Maquina maquina;
    private JLabel lblDinero;
    private JTextField txtDinero;
    private JPanel jpMainPanel;
    private JComboBox[] comboPremios;
    private Container contenedor;
    private VentanaMaquinas ventanaMaquinas;

    public VentanaCrearPremio(VentanaPremios _ventanaPremios, VentanaMaquinas _ventanaMaquinas, Maquina _maquina) {
        super(_ventanaPremios, true);
        maquina = _maquina;
        ventanaMaquinas = _ventanaMaquinas;
        InicializarVentana();
        InicializarComponentes();
    }

    private void InicializarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void InicializarComponentes() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        int casillas = maquina.getNroCasillas();
        comboPremios = new JComboBox[casillas];

        for (int i = 0; i < casillas; i++) {
            comboPremios[i] = new JComboBox<String>(maquina.getFrutas());
            comboPremios[i].setBounds(10, i * 25 + 10, 100, 25);
            contenedor.add(comboPremios[i]);
        }

        lblDinero = new JLabel("Dinero: ");
        lblDinero.setBounds(200, 20, 100, 25);
        txtDinero = new JTextField();
        txtDinero.setBounds(200, 50, 100, 25);
        PlainDocument docDinero = (PlainDocument) txtDinero.getDocument();
        docDinero.setDocumentFilter(new IntFilter());

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setName("btnAceptar");
        btnAceptar.setBounds(30, 250, 100, 25);
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarPremio();
            }

        });

        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.setName("btnCancelar");
        btnCancelar.setBounds(80, 250, 100, 25);
        btnCancelar.addActionListener(this);

        contenedor.add(lblDinero);
        contenedor.add(txtDinero);
        contenedor.add(btnAceptar);
        contenedor.add(btnCancelar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        switch (btnClicked.getName()) {
            case "btnAceptar":
                GuardarPremio();
                break;
            case "btnCancelar":
                Volver();
                break;
        }
    }

    private void GuardarPremio() {
        int nroCasillas = maquina.getNroCasillas();
        String[] combinacion = new String[nroCasillas];
        for (int i = 0; i < nroCasillas; i++) {
            combinacion[i] = comboPremios[i].getItemAt(comboPremios[i].getSelectedIndex()).toString();
        }

        Premio premioExistente = maquina.BuscarPremioPorCombinacion(combinacion);

        if (premioExistente != null) {
            JOptionPane.showMessageDialog(contenedor,
                    "La combinación elegida ya cuenta con un premio en esta máquina.");
        } else {
            maquina.CargarPremio(combinacion, Integer.valueOf(txtDinero.getText()));
            Volver();
        }
    }

    private void Volver() {
        // dispose();
        // VentanaPremios ventanaPremios = new VentanaPremios(ventanaMaquinas, maquina);
        // ventanaPremios.setVisible(true);
    }

    /*
     * private void GuardarPremio() {
     * 
     * Premio premio = maquina.CargarPremio(
     * new String[] { txtFruta1.getText(), txtFruta2.getText(), txtFruta3.getText()
     * },
     * Float.valueOf(txtDinero.getText()));
     * 
     * if (premio != null) {
     * JOptionPane.showMessageDialog(jpMainPanel, "El premio ha sido creado.");
     * LimpiarInputs();
     * } else {
     * JOptionPane.showMessageDialog(jpMainPanel,
     * "El premio no ha podido ser creado.");
     * }
     * 
     * }
     */

}

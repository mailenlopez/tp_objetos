package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import controller.Casino;
import filter.IntFilter;
import view.MaquinaView;
import view.PremioView;

public class VentanaCrearPremio extends JDialog implements ActionListener {
    private MaquinaView maquina;
    private JLabel lblDinero;
    private JTextField txtDinero;
    private JComboBox[] comboPremios;
    private Container contenedor;
    private VentanaMaquinas ventanaMaquinas;
    private VentanaPrincipal ventanaPrincipal;
    private Casino casino;

    public VentanaCrearPremio(VentanaPremios _ventanaPremios, VentanaPrincipal _ventanaPrincipal,
            VentanaMaquinas _ventanaMaquinas, MaquinaView _maquina) {
        super(_ventanaPremios, true);
        maquina = _maquina;
        ventanaMaquinas = _ventanaMaquinas;
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
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
        btnAceptar.addActionListener(this);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setName("btnCancelar");
        btnCancelar.setBounds(150, 250, 100, 25);
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

        PremioView premioExistente = casino.BuscarPremioPorCombinacion(maquina.getNroMaquina(), combinacion);

        if (premioExistente != null) {
            JOptionPane.showMessageDialog(contenedor,
                    "La combinación elegida ya cuenta con un premio en esta máquina.");
        } else {
            casino.AltaPremio(maquina.getNroMaquina(), combinacion, Integer.valueOf(txtDinero.getText()));
            Volver();
        }
    }

    private void Volver() {
        dispose();
        VentanaPremios ventanaPremios = new VentanaPremios(ventanaMaquinas, ventanaPrincipal, maquina);
        ventanaPremios.setVisible(true);
    }

}

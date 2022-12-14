package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Casino;
import view.ComprobanteView;
import view.MaquinaView;

public class VentanaComprobante extends JDialog implements ActionListener {
    private MaquinaView maquina;
    private ComprobanteView comprobante;
    private JLabel lblHeader, lblMaquinaConfig, lblCredito, lblFecha;
    private JPanel jpMainPanel;
    private VentanaPrincipal ventanaPrincipal;
    private Casino casino;

    public VentanaComprobante(VentanaJugada _ventanaJugada, VentanaPrincipal _ventanaPrincipal, MaquinaView _maquina) {
        super(_ventanaJugada, true);
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
        maquina = _maquina;

        InicializarVentana();
        EmitirComprobante();
        MostrarComponentes();
    }

    private void InicializarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(350, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void MostrarComponentes() {
        lblHeader = new JLabel("-- Comprobante de pago --");
        lblMaquinaConfig = new JLabel("Maquina " + maquina.getNroMaquina());

        lblCredito = new JLabel("Credito a cobrar: $" + comprobante.getCreditoDisponible());
        lblFecha = new JLabel("Fecha de emisión: " + comprobante.getFechaEmision());

        JPanel jpForm = new JPanel();
        jpForm.setLayout(new GridLayout(5, 2, 20, 20));

        jpForm.add(lblHeader);
        jpForm.add(lblMaquinaConfig);
        jpForm.add(lblCredito);
        jpForm.add(lblFecha);

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

    private void EmitirComprobante() {
        comprobante = casino.EmitirComprobante(maquina.getNroMaquina());
        Volver();
    }

    private void Volver() {
        dispose();
        maquina = casino.ObtenerMaquina(maquina.getNroMaquina());
        VentanaJugada ventanaJugada = new VentanaJugada(ventanaPrincipal, maquina);
        ventanaJugada.setVisible(true);
    }
}

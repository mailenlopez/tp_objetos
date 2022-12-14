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
import view.TicketView;

public class VentanaCrearTicket extends JDialog {
    private Casino casino;
    private JLabel lblNumeroTicket, lblImporte;
    private JTextField txtImporte;
    private JPanel jpMainPanel;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaCrearTicket(VentanaPrincipal _ventanaPrincipal, VentanaTickets _ventanaTickets) {
        super(_ventanaTickets, true);
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
        lblNumeroTicket = new JLabel("Nuevo ticket - Número: " + (casino.getTickets().size() + 1));

        lblImporte = new JLabel("Importe: ");
        txtImporte = new JTextField();
        PlainDocument docImporte = (PlainDocument) txtImporte.getDocument();
        docImporte.setDocumentFilter(new IntFilter());

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setName("btnAceptar");
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarTicket();
            }

        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setName("btnCancelar");
        btnCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Volver();
            }

        });

        JPanel jpForm = new JPanel();
        jpForm.setLayout(new GridLayout(5, 2, 20, 20));

        jpForm.add(lblNumeroTicket);
        jpForm.add(lblImporte);
        jpForm.add(txtImporte);
        jpForm.add(btnAceptar);
        jpForm.add(btnCancelar);

        jpMainPanel = new JPanel();
        jpMainPanel.setLayout(new BorderLayout());
        jpMainPanel.add(jpForm, BorderLayout.NORTH);
        jpMainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        add(jpMainPanel);
    }

    private void GuardarTicket() {

        TicketView ticket = casino.GenerarTicket(Float.valueOf(txtImporte.getText()));

        if (ticket != null) {
            JOptionPane.showMessageDialog(jpMainPanel, "El ticket ha sido creado.");
            LimpiarInputs();
            Volver();
        } else {
            JOptionPane.showMessageDialog(jpMainPanel, "El ticket no pudo ser creado.");
        }

    }

    private void LimpiarInputs() {
        txtImporte.setText("");
    }

    private void Volver() {
        dispose();
        VentanaTickets ventanaTickets = new VentanaTickets(ventanaPrincipal);
        ventanaTickets.setVisible(true);
    }
}

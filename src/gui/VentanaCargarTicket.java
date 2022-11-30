package gui;

import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import controller.Casino;
import filter.IntFilter;
import modelo.Maquina;

public class VentanaCargarTicket extends JDialog implements ActionListener {
    private Maquina maquina;
    private JLabel lblMaquinaConfig, lblNroTicket;
    private JTextField txtNroTicket;
    private JPanel jpMainPanel;
    private VentanaPrincipal ventanaPrincipal;
    private Casino casino;

    public VentanaCargarTicket(VentanaJugada _ventanaJugada, VentanaPrincipal _ventanaPrincipal, Maquina _maquina) {
        super(_ventanaJugada, true);
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
        maquina = _maquina;

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
        lblMaquinaConfig = new JLabel("Maquina " + maquina.getNroMaquina());

        lblNroTicket = new JLabel("Numero de ticket: ");
        txtNroTicket = new JTextField();
        PlainDocument docNroTicket = (PlainDocument) txtNroTicket.getDocument();
        docNroTicket.setDocumentFilter(new IntFilter());

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setName("btnAceptar");
        btnAceptar.addActionListener(this);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setName("btnCancelar");
        btnCancelar.addActionListener(this);

        JPanel jpForm = new JPanel();
        jpForm.setLayout(new GridLayout(5, 2, 20, 20));

        jpForm.add(lblMaquinaConfig);
        jpForm.add(lblNroTicket);
        jpForm.add(txtNroTicket);
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
        JButton btnClicked = (JButton) e.getSource();

        switch (btnClicked.getName()) {
            case "btnAceptar":
                CargarTicket();
                Volver();
                break;
            case "btnCancelar":
                Volver();
                break;
        }
    }

    private void CargarTicket() {
        casino.CargarCreditoMaquina(
                maquina.getNroMaquina(),
                Integer.parseInt(txtNroTicket.getText()));
        LimpiarInputs();
    }

    private void Volver() {
        dispose();
        VentanaJugada ventanaJugada = new VentanaJugada(ventanaPrincipal, maquina);
        ventanaJugada.setVisible(true);
    }

    private void LimpiarInputs() {
        txtNroTicket.setText("");
    }
}

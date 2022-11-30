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
    private JTextField txtFruta1, txtFruta2, txtFruta3, txtDinero;
    private JPanel jpMainPanel;
    private JComboBox[] comboPremios;
    private Container contenedor;

    public VentanaCrearPremio(VentanaPremios _ventanaPremios, Maquina _maquina) {
        super(_ventanaPremios, true);
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
        txtDinero = new JTextField();
        PlainDocument docDinero = (PlainDocument) txtDinero.getDocument();
        docDinero.setDocumentFilter(new IntFilter());

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                GuardarPremio();
            }

        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);

        JPanel jpForm = new JPanel();
        jpForm.setLayout(new GridLayout(5, 2, 20, 20));

        jpForm.add(lblDinero);
        jpForm.add(txtDinero);
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

    private void GuardarPremio() {

        Premio premio = maquina.CargarPremio(
                new String[] { txtFruta1.getText(), txtFruta2.getText(), txtFruta3.getText() },
                Float.valueOf(txtDinero.getText()));

        if (premio != null) {
            JOptionPane.showMessageDialog(jpMainPanel, "El premio ha sido creado.");
            LimpiarInputs();
        } else {
            JOptionPane.showMessageDialog(jpMainPanel, "El premio no ha podido ser creado.");
        }

    }

    private void LimpiarInputs() {
        txtFruta1.setText("");
        txtFruta2.setText("");
        txtFruta3.setText("");
        txtDinero.setText("");
    }
}

package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.Casino;
import modelo.Maquina;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JDialog;

public class SeleccionaMaquina extends JDialog {
    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;
    private Casino casino;
    private JComboBox comboMaquinas;

    public SeleccionaMaquina(VentanaPrincipal _ventanaPrincipal, Casino _casino) {
        super(_ventanaPrincipal, true);
        ventanaPrincipal = _ventanaPrincipal;
        casino = _casino;

        InicializarVentana();
        InicializarComponentes();
    }

    public void InicializarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(200, 130);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void InicializarComponentes() {

        contenedor = getContentPane();
        contenedor.setLayout(null);

        JButton btnAceptar = new JButton();
        btnAceptar.setText("Aceptar");
        btnAceptar.setBackground(new Color(59, 89, 182));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAceptar.setBounds(10, 60, 150, 25);
        btnAceptar.setName("btnAceptar");
        btnAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setDefaultCloseOperation(HIDE_ON_CLOSE);
                setVisible(false);

                Maquina maquina = casino.BuscarMaquina(Integer
                        .valueOf(comboMaquinas.getItemAt(comboMaquinas.getSelectedIndex()).toString().substring(8)));
                VentanaJugada ventana = new VentanaJugada(ventanaPrincipal, maquina);
                ventana.setVisible(true);
                setVisible(false);
            }

        });

        Collection<Maquina> maquina = casino.getMaquinas();
        String[] maquinas = new String[maquina.size()];

        comboMaquinas = new JComboBox<String>(maquinas);

        for (int i = 0; i < maquinas.length; i++) {
            maquinas[i] = "Maquina " + (i + 1);
        }

        comboMaquinas = new JComboBox<String>(maquinas);
        comboMaquinas.setBounds(10, 20, 150, 25);
        contenedor.add(comboMaquinas);
        contenedor.add(btnAceptar);
    }
}

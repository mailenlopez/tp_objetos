package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.Casino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private Container contenedor;
    private Casino casino;

    /**
     * @return Casino instance
     */
    public Casino getCasino() {
        return casino;
    }

    public VentanaPrincipal(Casino _casino) {
        casino = _casino;
        IniciarlizarVentanaPrincipal();
        InicializarComponentes();
    }

    public void IniciarlizarVentanaPrincipal() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void InicializarComponentes() {

        contenedor = getContentPane();
        contenedor.setLayout(null);

        JButton btnMaquinas = new JButton();
        btnMaquinas.setText("Maquinas");
        btnMaquinas.setBackground(new Color(59, 89, 182));
        btnMaquinas.setForeground(Color.WHITE);
        btnMaquinas.setFocusPainted(false);
        btnMaquinas.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnMaquinas.setBounds(515, 40, 170, 25);
        btnMaquinas.setName("btnMaquinas");
        btnMaquinas.addActionListener(this);

        JButton btnTickets = new JButton();
        btnTickets.setText("Tickets");
        btnTickets.setBackground(new Color(59, 89, 182));
        btnTickets.setForeground(Color.WHITE);
        btnTickets.setFocusPainted(false);
        btnTickets.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTickets.setBounds(515, 85, 170, 25);
        btnTickets.setName("btnTickets");
        btnTickets.addActionListener(this);

        JButton btnJugada = new JButton();
        btnJugada.setText("Iniciar Jugada");
        btnJugada.setBounds(500, 150, 200, 100);
        btnJugada.setBackground(Color.BLACK);
        btnJugada.setName("btnJugada");
        btnJugada.setBorderPainted(false);
        btnJugada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Image img = new ImageIcon(getClass().getResource("jugar.png")).getImage();
        Image newimg = img.getScaledInstance(200, 100, java.awt.Image.SCALE_SMOOTH);
        btnJugada.setIcon(new ImageIcon(newimg));
        btnJugada.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

        contenedor.add(btnMaquinas);
        contenedor.add(btnTickets);
        contenedor.add(btnJugada);
        contenedor.add(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton obj = (JButton) e.getSource();

        switch (obj.getName()) {
            case "btnMaquinas":
                MostrarVentanaMaquinas();
                break;
            case "btnTickets":
                MostrarVentanaTickets();
                break;
            case "btnJugada":
                SeleccionarMaquina();
                break;
        }
    }

    public void MostrarVentanaMaquinas() {
        VentanaMaquinas ventanaMaquinas = new VentanaMaquinas(this);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaMaquinas.setVisible(true);
        this.setVisible(false);
    }

    public void MostrarVentanaTickets() {
        VentanaTickets ventanaTickets = new VentanaTickets(this);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaTickets.setVisible(true);
        this.setVisible(false);
    }

    private void SeleccionarMaquina() {
        if (casino.getMaquinas().size() > 0) {
            SeleccionaMaquina seleccionaMaquina = new SeleccionaMaquina(this, casino);
            seleccionaMaquina.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Debe tener al menos una máquina creada para jugar.");
        }

    }
}
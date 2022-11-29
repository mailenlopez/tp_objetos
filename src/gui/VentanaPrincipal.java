package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Driver;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private Container contenedor;

    public VentanaPrincipal() {
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
        btnMaquinas.setBounds(515, 20, 170, 25);
        btnMaquinas.setName("btnMaquinas");
        btnMaquinas.addActionListener(this);

        JButton btnTickets = new JButton();
        btnTickets.setText("Tickets");
        btnTickets.setBackground(new Color(59, 89, 182));
        btnTickets.setForeground(Color.WHITE);
        btnTickets.setFocusPainted(false);
        btnTickets.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTickets.setBounds(515, 65, 170, 25);
        btnMaquinas.setName("btnTickets");
        btnTickets.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

        contenedor.add(btnMaquinas);
        contenedor.add(btnTickets);
        contenedor.add(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String obj = e.getActionCommand();
        switch (obj) {
            case "Maquinas":
                MostrarVentanaMaquinas();
                break;
            case "Tickets":
                System.out.print("Entró a tickets");
                break;
        }
    }

    public void MostrarVentanaMaquinas() {
        VentanaMaquinas ventanaMaquinas = new VentanaMaquinas(this);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaMaquinas.setVisible(true);
        this.setVisible(false);
    }
}
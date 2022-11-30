package gui;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import controller.Casino;
import modelo.Ticket;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.event.WindowAdapter;

public class VentanaTickets extends JFrame implements ActionListener {

    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;
    private Casino casino;

    public VentanaTickets(VentanaPrincipal _ventanaPrincipal) {
        super();
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
        InicializarVentana();
        InicializarComponentes();
    }

    public void InicializarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                ventanaPrincipal.setVisible(true);
                ventanaPrincipal.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }

    private void InicializarComponentes() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        JButton btnCrearTicket = new JButton();
        btnCrearTicket.setText("Crear Ticket");
        btnCrearTicket.setBackground(new Color(59, 89, 182));
        btnCrearTicket.setForeground(Color.WHITE);
        btnCrearTicket.setFocusPainted(false);
        btnCrearTicket.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCrearTicket.setBounds(515, 30, 170, 25);
        btnCrearTicket.setName("btnCrearTicket");
        btnCrearTicket.addActionListener(this);

        JButton btnVolver = new JButton();
        btnVolver.setText("Volver");
        btnVolver.setBackground(new Color(59, 89, 182));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnVolver.setBounds(515, 450, 170, 25);
        btnVolver.setName("btnVolver");
        btnVolver.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

        contenedor.add(btnVolver);
        Grid(contenedor);
        contenedor.add(btnCrearTicket);
        contenedor.add(contentPane);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton obj = (JButton) e.getSource();

        switch (obj.getName()) {
            case "btnCrearTicket":
                MostrarCrearTicket();
                break;
            case "btnVolver":
                Volver();
                break;
        }
    }

    private void Grid(Container container) {

        String[] columnNames = { "Numero Ticket",
                "Importe" };
        Collection<Ticket> tickets = casino.getTickets();
        ArrayList<Ticket> arrayTickets = new ArrayList<>(tickets);

        Object[][] data = new Object[tickets.size()][columnNames.length];

        for (int i = 0; i < arrayTickets.size(); i++) {
            Ticket ticket = arrayTickets.get(i);

            data[i][0] = ticket.getNroTicket();
            data[i][1] = "$" + String.valueOf(ticket.getImporte());
        }

        JTable table = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);

        table.setRowSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        scrollPane.setBounds(185, 105, 500, 200);
        container.add(scrollPane);
    }

    private void MostrarCrearTicket() {
        dispose();
        VentanaCrearTicket crearTicket = new VentanaCrearTicket(ventanaPrincipal);
        crearTicket.setVisible(true);
    }

    private void Volver() {
        dispose();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(casino);
        ventanaPrincipal.setVisible(true);
    }
}

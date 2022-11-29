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
import javax.swing.table.JTableHeader;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class VentanaMaquinas extends JFrame implements ActionListener {

    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;

    public VentanaMaquinas(VentanaPrincipal _ventanaPrincipal) {
        super();
        ventanaPrincipal = _ventanaPrincipal;
        IniciarlizarVentana();
        InicializarComponentes();
    }

    public void IniciarlizarVentana() {
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

        JButton btnMaquinas = new JButton();
        btnMaquinas.setText("Crear Maquina");
        btnMaquinas.setBackground(new Color(59, 89, 182));
        btnMaquinas.setForeground(Color.WHITE);
        btnMaquinas.setFocusPainted(false);
        btnMaquinas.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnMaquinas.setBounds(515, 20, 170, 25);
        btnMaquinas.setName("btnMaquinas");
        btnMaquinas.addActionListener(this);

        JButton btnTickets = new JButton();
        btnTickets.setText("Maquinas Disponibles");
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

        Grid(contenedor);
        contenedor.add(btnMaquinas);
        contenedor.add(btnTickets);
        contenedor.add(contentPane);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String obj = e.getActionCommand();
        switch (obj) {
            case "Maquinas":
                System.out.print("Entró a maquinas");
                break;
            case "Tickets":
                System.out.print("Entró a tickets");
                break;
        }
    }

    private void Grid(Container container) {

        String[] columnNames = { "Id",
                "Casillas",
                "Recaudación",
                "Recaudación Min",
                "Costo" };

        Object[][] data = {
                { 1, "3", "$1,200", "$500", "$10" },
                { 2, "3", "$1,900", "$500", "$5" },
                { 3, "3", "$1,000", "$500", "$20" },
                { 4, "3", "$1,250", "$500", "$7" },
                { 5, "3", "$900", "$400", "$3" }
        };

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
}

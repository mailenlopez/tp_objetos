package gui;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.*;

import modelo.Maquina;

import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
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
            case "Crear Maquina":
                MostrarCrearMaquina(0);
                break;
            case "btnTickets":
                System.out.print("Entró a tickets");
                break;
        }
    }

    private void Grid(Container container) {

        String[] columnNames = { "Id",
                "Casillas",
                "Recaudación",
                "Recaudación Min",
                "Costo"
        };

        Collection<Maquina> maquinas = ventanaPrincipal.getCasino().getMaquinas();
        ArrayList<Maquina> arrayMaquinas = new ArrayList<>(maquinas);

        Object[][] data = new Object[maquinas.size()][columnNames.length];

        for (int i = 0; i < arrayMaquinas.size(); i++) {
            Maquina maquina = arrayMaquinas.get(i);

            data[i][0] = maquina.getNroMaquina();
            data[i][1] = maquina.getNroCasillas();
            data[i][2] = "$" + String.valueOf(maquina.getRecaudacion());
            data[i][3] = "$" + String.valueOf(maquina.getRecaudacionMin());
            data[i][4] = "$" + String.valueOf(maquina.getCostoJugada());
        }

        JTable table = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Object objeto = table.getValueAt(table.getSelectedRow(), 0);
                    // TODO: Agregar la busqueda de la maquina por su Id
                    System.out.println(objeto);
                    MostrarCrearMaquina((int) objeto);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        scrollPane.setBounds(185, 105, 500, 200);
        container.add(scrollPane);
    }

    private void MostrarCrearMaquina(int numeroMaquina) {

        Maquina maquina = null;
        CrearMaquina crearMaquina;

        if (numeroMaquina > 0) {
            maquina = ventanaPrincipal.getCasino().BuscarMaquina(numeroMaquina);
        }

        if (maquina != null) {
            crearMaquina = new CrearMaquina(ventanaPrincipal, maquina);
        } else {
            crearMaquina = new CrearMaquina(ventanaPrincipal);
        }

        crearMaquina.setVisible(true);
    }
}

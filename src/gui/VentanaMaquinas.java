package gui;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import controller.Casino;
import modelo.TableModelMaquina;
import view.MaquinaView;

import java.util.ArrayList;

public class VentanaMaquinas extends JFrame implements ActionListener {

    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;
    private MaquinaView maquina;
    private int maquinaSeleccionada;
    private Casino casino;

    public VentanaMaquinas(VentanaPrincipal _ventanaPrincipal) {
        super();
        ventanaPrincipal = _ventanaPrincipal;
        casino = _ventanaPrincipal.getCasino();
        maquinaSeleccionada = 0;
        InicializarVentana();
        InicializarComponentes();
    }

    public void InicializarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage());

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

        JButton btnCrearMaquina = new JButton();
        btnCrearMaquina.setText("Crear Maquina");
        btnCrearMaquina.setBackground(new Color(59, 89, 182));
        btnCrearMaquina.setForeground(Color.WHITE);
        btnCrearMaquina.setFocusPainted(false);
        btnCrearMaquina.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCrearMaquina.setBounds(515, 20, 170, 25);
        btnCrearMaquina.setName("btnCrearMaquina");
        btnCrearMaquina.addActionListener(this);

        JButton btnPremios = new JButton();
        btnPremios.setText("Premios");
        btnPremios.setEnabled(false);
        btnPremios.setBackground(new Color(59, 89, 182));
        btnPremios.setForeground(Color.WHITE);
        btnPremios.setFocusPainted(false);
        btnPremios.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPremios.setBounds(515, 65, 170, 25);
        btnPremios.setName("btnPremios");
        btnPremios.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

        JLabel mensajeLbl = new JLabel();
        mensajeLbl.setText("Selecciona una máquina para accionar. \n Doble click para editar parámetros.");
        mensajeLbl.setBounds(200, 160, 430, 30);
        mensajeLbl.setBackground(Color.BLACK);
        mensajeLbl.setForeground(Color.WHITE);
        mensajeLbl.setOpaque(true);

        JButton btnVolver = new JButton();
        btnVolver.setText("Volver");
        btnVolver.setBackground(new Color(59, 89, 182));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnVolver.setBounds(515, 450, 170, 25);
        btnVolver.setName("btnVolver");
        btnVolver.addActionListener(this);

        contenedor.add(btnVolver);
        Grilla(contenedor);
        contenedor.add(btnCrearMaquina);
        contenedor.add(mensajeLbl);
        contenedor.add(btnPremios);
        contenedor.add(contentPane);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton obj = (JButton) e.getSource();

        switch (obj.getName()) {
            case "btnCrearMaquina":
                MostrarCrearMaquina(0);
                break;
            case "btnPremios":
                MostrarPremios();
                break;
            case "btnVolver":
                Volver();
                break;
        }
    }

    private void Grilla(Container container) {

        String[] columnNames = { "Id",
                "Casillas",
                "Recaudación",
                "Recaudación Min",
                "Costo"
        };

        ArrayList<MaquinaView> arrayMaquinas = new ArrayList<>(casino.getMaquinas());

        TableModelMaquina tableModel = new TableModelMaquina();
        tableModel.addElement(maquina);
        Object[][] data = new Object[arrayMaquinas.size()][columnNames.length];

        for (int i = 0; i < arrayMaquinas.size(); i++) {
            maquina = arrayMaquinas.get(i);

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
                if (me.getClickCount() == 1) {
                    maquinaSeleccionada = (int) table.getValueAt(table.getSelectedRow(), 0);
                    maquina = casino.ObtenerMaquina(maquinaSeleccionada);

                    Component configComp = EncontrarComponentePorNombre("btnPremios");
                    configComp.setEnabled(true);
                }

                if (me.getClickCount() == 2) {
                    Object objeto = table.getValueAt(table.getSelectedRow(), 0);
                    System.out.println(objeto);
                    MostrarCrearMaquina((int) objeto);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        scrollPane.setBounds(185, 200, 500, 200);
        container.add(scrollPane);
    }

    private void MostrarCrearMaquina(int numeroMaquina) {
        dispose();
        MaquinaView maquina = null;
        VentanaCrearMaquina crearMaquina;

        if (numeroMaquina > 0) {
            maquina = casino.ObtenerMaquina(numeroMaquina);
        }

        if (maquina != null) {
            crearMaquina = new VentanaCrearMaquina(ventanaPrincipal, maquina);
        } else {
            crearMaquina = new VentanaCrearMaquina(ventanaPrincipal);
        }

        crearMaquina.setVisible(true);
    }

    private Component EncontrarComponentePorNombre(String nombreComponente) {
        for (Component componente : contenedor.getComponents()) {
            if (nombreComponente.equals(componente.getName())) {
                return componente;
            }
        }

        return null;
    }

    private void MostrarPremios() {
        dispose();
        VentanaPremios ventanaMaquinas = new VentanaPremios(this, ventanaPrincipal, maquina);
        ventanaMaquinas.setVisible(true);
    }

    private void Volver() {
        dispose();
        ventanaPrincipal = new VentanaPrincipal(casino);
        ventanaPrincipal.setVisible(true);
    }

}

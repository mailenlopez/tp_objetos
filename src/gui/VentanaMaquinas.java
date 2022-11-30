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
import modelo.Maquina;
import modelo.TableModelMaquina;

import java.util.Collection;
import java.util.ArrayList;

public class VentanaMaquinas extends JFrame implements ActionListener {

    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;
    private Maquina maquina;
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

        JButton btnComprobante = new JButton();
        btnComprobante.setText("Emitir Comprobante");
        btnComprobante.setEnabled(false);
        btnComprobante.setBackground(new Color(59, 89, 182));
        btnComprobante.setForeground(Color.WHITE);
        btnComprobante.setFocusPainted(false);
        btnComprobante.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnComprobante.setBounds(515, 110, 170, 25);
        btnComprobante.setName("btnComprobante");
        btnComprobante.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

        JLabel mensajeLbl = new JLabel();
        mensajeLbl.setText("Selecciona una máquina para accionar. \n Doble click para editar parámetros.");
        mensajeLbl.setBounds(185, 145, 500, 200);

        Grilla(contenedor);
        contenedor.add(btnCrearMaquina);
        contenedor.add(mensajeLbl);
        contenedor.add(btnPremios);
        contenedor.add(btnComprobante);
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
            case "btnComprobante":
                MostrarComprobante();
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

        Collection<Maquina> maquinas = casino.getMaquinas();
        ArrayList<Maquina> arrayMaquinas = new ArrayList<>(maquinas);

        TableModelMaquina tableModel = new TableModelMaquina();
        tableModel.addElement(maquina);
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
                if (me.getClickCount() == 1) {
                    maquinaSeleccionada = (int) table.getValueAt(table.getSelectedRow(), 0);
                    maquina = casino.BuscarMaquina(maquinaSeleccionada);

                    Component configComp = EncontrarComponentePorNombre("btnPremios");
                    configComp.setEnabled(true);

                    Component comprobanteComp = EncontrarComponentePorNombre("btnComprobante");
                    comprobanteComp.setEnabled(true);
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

        scrollPane.setBounds(185, 170, 500, 200);
        container.add(scrollPane);
    }

    private void MostrarCrearMaquina(int numeroMaquina) {

        Maquina maquina = null;
        VentanaCrearMaquina crearMaquina;

        if (numeroMaquina > 0) {
            maquina = casino.BuscarMaquina(numeroMaquina);
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

    private void MostrarComprobante() {
        VentanaComprobante ventanaComprobante = new VentanaComprobante(this, casino, maquina);
        ventanaComprobante.setVisible(true);
    }

    private void MostrarPremios() {
        VentanaPremios ventanaMaquinas = new VentanaPremios(this, maquina);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        ventanaMaquinas.setVisible(true);
        this.setVisible(false);
    }
}

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

import modelo.Maquina;
import modelo.Premio;

import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.WindowAdapter;

public class VentanaPremios extends JFrame implements ActionListener {

    private VentanaMaquinas ventanaMaquinas;
    private VentanaPrincipal ventanaPrincipal;
    private Container contenedor;
    private int premioSeleccionado;
    private Maquina maquina;

    public VentanaPremios(VentanaMaquinas _ventanaMaquinas, VentanaPrincipal _ventanaPrincipal, Maquina _maquina) {
        super();
        ventanaMaquinas = _ventanaMaquinas;
        ventanaPrincipal = _ventanaPrincipal;
        maquina = _maquina;
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
                ventanaMaquinas.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });
    }

    private void InicializarComponentes() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        JButton btnCrearPremio = new JButton();
        btnCrearPremio.setText("Crear Premio");
        btnCrearPremio.setBackground(new Color(59, 89, 182));
        btnCrearPremio.setForeground(Color.WHITE);
        btnCrearPremio.setFocusPainted(false);
        btnCrearPremio.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCrearPremio.setBounds(515, 20, 170, 25);
        btnCrearPremio.setName("btnCrearPremio");
        btnCrearPremio.addActionListener(this);

        JButton btnBorrarPremio = new JButton();
        btnBorrarPremio.setText("Borrar Premio");
        btnBorrarPremio.setEnabled(false);
        btnBorrarPremio.setBackground(new Color(59, 89, 182));
        btnBorrarPremio.setForeground(Color.WHITE);
        btnBorrarPremio.setFocusPainted(false);
        btnBorrarPremio.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBorrarPremio.setBounds(515, 65, 170, 25);
        btnBorrarPremio.setName("btnBorrarPremio");
        btnBorrarPremio.addActionListener(this);

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
        contenedor.add(btnCrearPremio);
        contenedor.add(btnBorrarPremio);
        contenedor.add(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton obj = (JButton) e.getSource();

        switch (obj.getName()) {
            case "btnCrearPremio":
                MostrarCrearPremio();
                break;
            case "btnBorrarPremio":
                BorrarPremio(premioSeleccionado);
                Recargar();
                break;
            case "btnVolver":
                Volver();
                break;
        }
    }

    private void Grid(Container container) {
        String[] columnNames = {
                "Id",
                "Combinación ganadora",
                "Premio"
        };

        Collection<Premio> premios = maquina.getPremiosDisponibles();
        ArrayList<Premio> arrayPremios = new ArrayList<>(premios);

        Object[][] data = new Object[premios.size()][columnNames.length];

        for (int i = 0; i < arrayPremios.size(); i++) {
            Premio premio = arrayPremios.get(i);

            data[i][0] = premio.getId();
            data[i][1] = Arrays.toString(premio.getCombinacionGanadora());
            data[i][2] = "$" + String.valueOf(premio.getDinero());
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
                    premioSeleccionado = (int) table.getValueAt(table.getSelectedRow(), 0);
                    Component componente = EncontrarComponentePorNombre("btnBorrarPremio");
                    componente.setEnabled(true);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        scrollPane.setBounds(185, 105, 500, 200);
        container.add(scrollPane);
    }

    private void MostrarCrearPremio() {
        dispose();
        VentanaCrearPremio crearPremio = new VentanaCrearPremio(this, ventanaPrincipal, ventanaMaquinas, maquina);
        crearPremio.setVisible(true);
    }

    private void BorrarPremio(int nroPremio) {
        maquina.DarBajaPremio(nroPremio);
    }

    private Component EncontrarComponentePorNombre(String componentName) {
        for (Component component : contenedor.getComponents()) {
            if (componentName.equals(component.getName())) {
                return component;
            }
        }

        return null;
    }

    private void Volver() {
        dispose();
        VentanaMaquinas ventanaMaquinas = new VentanaMaquinas(ventanaPrincipal);
        ventanaMaquinas.setVisible(true);
    }

    private void Recargar() {
        dispose();
        VentanaPremios ventanaPremios = new VentanaPremios(ventanaMaquinas, ventanaPrincipal, maquina);
        ventanaPremios.setVisible(true);
    }
}

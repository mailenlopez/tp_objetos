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
import java.awt.event.WindowAdapter;

public class VentanaPremios extends JFrame implements ActionListener {

    private VentanaMaquinas ventanaMaquinas;
    private Container contenedor;
    private int premioSeleccionado;
    private Maquina maquina;

    public VentanaPremios(VentanaMaquinas _ventanaMaquinas, Maquina _maquina) {
        super();
        ventanaMaquinas = _ventanaMaquinas;
        maquina = _maquina; // TODO: Si maquina no existe, tirar error
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
                ventanaMaquinas.setVisible(true);
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

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        contentPane.setIcon(new ImageIcon(getClass().getResource("background.jpg")));
        contentPane.setSize(800, 600);

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
                break;
        }
    }

    private void Grid(Container container) {

        String[] columnNames = { "Combinación ganadora",
                "Premio"
        };

        Collection<Premio> premios = maquina.getPremiosDisponibles();
        ArrayList<Premio> arrayPremios = new ArrayList<>(premios);

        Object[][] data = new Object[premios.size()][columnNames.length];

        for (int i = 0; i < arrayPremios.size(); i++) {
            Premio premio = arrayPremios.get(i);

            data[i][0] = premio.getCombinacionGanadora();
            data[i][1] = "$" + String.valueOf(premio.getDinero());
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
                    Component componente = EncontrarComponentePorNombre("Borrar Premio");
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
        VentanaCrearPremio crearPremio = new VentanaCrearPremio(this, maquina);
        crearPremio.setVisible(true);
    }

    private void BorrarPremio(int numeroPremio) {
        maquina.DarBajaPremio(numeroPremio);
    }

    private Component EncontrarComponentePorNombre(String componentName) {
        for (Component component : contenedor.getComponents()) {
            if (componentName.equals(component.getName())) {
                return component;
            }
        }

        return null;
    }
}

package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import controller.Casino;
import modelo.Jugada;
import modelo.Maquina;

public class VentanaJugada extends JFrame implements ActionListener {
    private Maquina maquina;
    private Casino casino;
    private JLabel lblMaquinaConfig, lblCreditoDisp;
    private Container contenedor;
    private VentanaPrincipal ventanaPrincipal;
    private JLabel[] lblFrutas;
    private String[] frutas;

    public VentanaJugada(VentanaPrincipal _ventanaPrincipal, Casino _casino, Maquina _maquina) {
        super();
        casino = _casino;
        maquina = _maquina;
        ventanaPrincipal = _ventanaPrincipal;
        frutas = maquina.getFrutas();
        IniciarlizarVentana();
        InicializarComponentes();
    }

    private void IniciarlizarVentana() {
        setResizable(false);
        setTitle("Casino Corona");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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

        int casillas = maquina.getNroCasillas();

        lblFrutas = new JLabel[casillas];
        for (int i = 0; i < casillas; i++) {
            lblFrutas[i] = new JLabel();
            lblFrutas[i] = new JLabel(new ImageIcon(getClass().getResource("7.png")));
            lblFrutas[i].setBounds((i * 110) + 219, 240, 85, 85);
            contenedor.add(lblFrutas[i]);
        }

        lblMaquinaConfig = new JLabel("Maquina " + maquina.getNroMaquina());
        lblMaquinaConfig.setBounds(10, 5, 100, 20);

        lblCreditoDisp = new JLabel("Crédito disponible: $" + maquina.getCreditoDisponible());
        lblCreditoDisp.setBounds(10, 520, 200, 25);

        JButton btnCargarTicket = new JButton();
        btnCargarTicket.setText("Cargar Ticket");
        btnCargarTicket.setBackground(new Color(59, 89, 182));
        btnCargarTicket.setForeground(Color.WHITE);
        btnCargarTicket.setFocusPainted(false);
        btnCargarTicket.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCargarTicket.setBounds(615, 25, 150, 25);
        btnCargarTicket.setName("btnCargarTicket");
        btnCargarTicket.addActionListener(this);

        JLabel contentPane = new JLabel();
        contentPane.setBounds(0, 0, 800, 600);
        Image img = new ImageIcon(getClass().getResource("background.jpg")).getImage();
        Image newImg = img.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
        contentPane.setIcon(new ImageIcon(newImg));
        contentPane.setSize(800, 600);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getClass().getResource("maquina.png")));
        label.setBounds(80, 115, 610, 333);

        JButton btnJugar = new JButton("Jugar");
        btnJugar.setBounds(650, 260, 100, 25);
        btnJugar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (maquina.getCreditoDisponible() >= maquina.getCostoJugada()) {
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            btnJugar.setEnabled(false);
                            boolean jugada = Jugar();

                            if (jugada) {
                                JOptionPane.showMessageDialog(contenedor, "¡Felicidades ha ganado!");
                            }

                            return null;
                        }

                        @Override
                        protected void done() {
                            try {
                                get();
                            } catch (Exception ignore) {
                            } finally {
                                btnJugar.setEnabled(true);
                            }
                        }

                    }.execute();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay crédito disponible para la jugada.");
                }
            }
        });

        contenedor.add(contentPane);
        contenedor.add(label);
        contenedor.add(lblMaquinaConfig);
        contenedor.add(lblCreditoDisp);
        contenedor.add(btnJugar);
        contenedor.add(btnCargarTicket);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton obj = (JButton) e.getSource();

        switch (obj.getName()) {
            case "btnCargarTicket":
                MostrarCargarTicket();
                break;
        }

    }

    private void MostrarCargarTicket() {
        VentanaCargarTicket ventanaMaquinas = new VentanaCargarTicket(this, casino, maquina);
        ventanaMaquinas.setVisible(true);
    }

    private void cambiarFruta(int posicion, String fruta) {
        this.lblFrutas[posicion].setIcon(new ImageIcon(getClass().getResource(fruta + ".png")));
    }

    private boolean Jugar() throws Exception {

        int casillas = maquina.getNroCasillas();
        int nroMaquina = maquina.getNroMaquina();
        Maquina maquina = casino.BuscarMaquina(nroMaquina);
        Jugada jugada = maquina.GenerarJugada();

        // boolean resultado = c.ultimaJugada(idMaquina);
        String[] combinacion = jugada.getCombinacion();
        int contador = 0;
        long pausa, finish;
        for (int i = 0; i < casillas; i++) {
            finish = System.currentTimeMillis() + 500;
            while (System.currentTimeMillis() < finish) {
                contador = (contador == 5) ? 0 : contador + 1;
                for (int j = i; j < casillas; j++) {
                    cambiarFruta(j, frutas[contador]);
                }
                pausa = System.currentTimeMillis() + 50;
                while (System.currentTimeMillis() < pausa)
                    ;
            }
            cambiarFruta(i, combinacion[i]);
        }

        lblCreditoDisp.setText("Credito :" + maquina.getCreditoDisponible());

        return false;
    }
}

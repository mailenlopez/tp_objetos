package test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import controller.Casino;
import gui.VentanaPrincipal;
import modelo.Premio;
import view.MaquinaView;

public class Application {

    private static String[] listadoFrutas = { "Banana", "Frutilla", "Guinda", "Manzana", "Sandia", "Uva" };

    public static void main(String[] args) {
        Casino casino = Casino.getInstance();
        VentanaPrincipal ventana = new VentanaPrincipal(casino);
        ventana.setVisible(true);

        /*
         * Scanner scanner = new Scanner(System.in);
         * Casino casino = Casino.getInstance();
         * 
         * int nroMaquina = CrearMaquina(casino, scanner);
         * MockedPremio(casino);
         * BajaPremio(casino, scanner);
         * CrearPremios(casino, scanner);
         * Ticket ticket = casino.GenerarTicket(200);
         * casino.CargarCreditoMaquina(nroMaquina, ticket.getNroTicket());
         * casino.Jugar(nroMaquina);
         * 
         * scanner.close();
         */
    }

    private static int CrearMaquina(Casino casino, Scanner scanner) {
        System.out.print("--- Creando nueva máquina --- \n");
        System.out.print("Número de casillas: 3 \n");

        System.out.print("Ingrese la recaudación inicial: ");
        float recaudacion = scanner.nextFloat();

        System.out.print("Ingrese la recaudación mínima necesaria para poder otorgar premios: ");
        float recaudacionMin = scanner.nextFloat();

        System.out.print("Ingrese el precio de la jugada: ");
        float precioJugada = scanner.nextFloat();

        final MaquinaView maquina = casino.CrearMaquina(3, recaudacion, recaudacionMin, precioJugada);
        return maquina.getNroMaquina();
    }

    private static void CrearPremios(Casino casino, Scanner scanner) {
        boolean agregarPremio = true;

        while (agregarPremio) {
            System.out.print("--- Creando premio --- \n");
            System.out.print("Ingrese el número de máquina. Pulse 0 para salir: \n");
            int nroMaquina = scanner.nextInt();
            scanner.nextLine();

            if (nroMaquina == 0) {
                return;
            }

            MaquinaView maquina = SeleccionarMaquina(casino, nroMaquina);

            if (maquina == null) {
                System.out.print("El número de máquina ingresado no corresponde a una máquina existente. \n");
                continue;
            }

            Collection<Premio> premios = maquina.getPremiosDisponibles();
            int nroCasillas = maquina.getNroCasillas();
            String[] combinacion = new String[nroCasillas];

            System.out.print(
                    "Frutas disponibles en las máquinas: 0-Banana - 1-Frutilla - 2-Guinda - 3-Manzana - 4-Sandía - 5-Uva \n");
            for (int i = 0; i < nroCasillas; i++) {
                System.out.print(String.format("Ingrese la fruta para la casilla %d: ", i + 1));
                int seleccion = scanner.nextInt();
                if (seleccion < listadoFrutas.length) {
                    combinacion[i] = listadoFrutas[seleccion];
                    System.out.print("La fruta seleccionada es: " + listadoFrutas[seleccion] + "\n");
                } else {
                    System.out.print("Esta opción no está disponible. \n");
                    i--;
                }
            }

            Premio premioExistente = BuscarPremio(premios, combinacion);

            if (premioExistente != null) {
                System.out.print("La combinación que desea agregar ya existe en la lista de premios. \n");
                continue;
            }

            System.out.print("Ingrese el dinero del premio: \n");
            float dinero = scanner.nextFloat();

            casino.AltaPremio(nroMaquina, combinacion, dinero);
            System.out.print("¿Desea continuar agregando premios? - 1: Si - 2: No \n");
            int continuarAgregandoPremio = scanner.nextInt();

            if (continuarAgregandoPremio != 1 && continuarAgregandoPremio != 2) {
                System.out.print("Esa opción no está disponible. \n");
            } else if (continuarAgregandoPremio == 2) {
                agregarPremio = false;
            }
        }
    }

    private static MaquinaView SeleccionarMaquina(Casino casino, int nroMaquina) {
        Collection<MaquinaView> maquinas = casino.getMaquinas();

        MaquinaView maquina = maquinas.stream()
                .filter(s -> s.getNroMaquina() == nroMaquina)
                .findFirst().orElse(null);

        return maquina;
    }

    private static Premio BuscarPremio(Collection<Premio> premios, String[] combinacion) {
        Premio premio = premios.stream()
                .filter(p -> p.SoyEstaCombinacion(combinacion))
                .findFirst().orElse(null);

        return premio;
    }

    private static void MockedPremio(Casino casino) {
        String[] combinacion = { "Frutilla", "Frutilla", "Frutilla" };
        int dinero = 200;

        casino.AltaPremio(1, combinacion, dinero);
    }

    private static void BajaPremio(Casino casino, Scanner scanner) {
        MaquinaView maquina = SeleccionarMaquina(casino, 1);
        Collection<Premio> premios = maquina.getPremiosDisponibles();
        if (premios.size() > 0) {
            Premio premioAEliminar = premios.stream()
                    .findFirst()
                    .orElse(null);

            casino.BajaPremio(maquina.getNroMaquina(), premioAEliminar.getId());

            System.out.print("La siguiente combinación ha sido dada de baja de la lista de premios: \n"
                    + Arrays.toString(premioAEliminar.getCombinacionGanadora()) + "\n");
        } else {
            System.out.print("No hay ningún premio disponible para dar de baja. \n");
        }
    }
}

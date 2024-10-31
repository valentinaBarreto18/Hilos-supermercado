package org.example.Principal;

import org.example.hilo.Caja;
import org.example.persistencia.BaseDeDatos;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Supermercado {
    private static List<Thread> hilosCajas = new ArrayList<>();
    private static List<Caja> cajas = new ArrayList<>();
    private static BaseDeDatos baseDeDatos = new BaseDeDatos();
    private static boolean simulacionIniciada = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----- Supermercado -----");
            System.out.println("1. Iniciar simulación");
            System.out.println("2. Ver estado de las cajas");
            System.out.println("3. Consultar registro de ventas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    if (!simulacionIniciada) {
                        iniciarSimulacion();
                        simulacionIniciada = true;
                    } else {
                        System.out.println("La simulación ya está en marcha.");
                    }
                    break;
                case "2":
                    verEstadoCajas();
                    break;
                case "3":
                    consultarRegistroVentas();
                    break;
                case "4":
                    detenerSimulacion();
                    baseDeDatos.cerrarConexion();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void iniciarSimulacion() {
        for (int i = 1; i <= 3; i++) { // Supongamos que tenemos 3 cajas
            Caja caja = new Caja(i, baseDeDatos);
            cajas.add(caja);
            Thread hiloCaja = new Thread(caja);
            hilosCajas.add(hiloCaja);
            hiloCaja.start();
        }
        System.out.println("Simulación iniciada.");
    }

    private static void verEstadoCajas() {
        for (Caja caja : cajas) {
            System.out.println("Caja " + caja.getIdCaja() + " ha atendido a " + caja.getClientesAtendidos() + " clientes. Total procesado: $" + String.format("%.2f", caja.getTotalVentas()));
        }
    }

    private static void consultarRegistroVentas() {
        baseDeDatos.consultarTransacciones();
    }

    private static void detenerSimulacion() {
        for (Thread hilo : hilosCajas) {
            hilo.interrupt(); // Interrumpir cada hilo de caja
        }
        System.out.println("Simulación detenida.");
    }
}

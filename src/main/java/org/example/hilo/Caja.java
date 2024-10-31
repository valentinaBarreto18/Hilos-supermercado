package org.example.hilo;

import org.example.modelo.Cliente;
import org.example.persistencia.BaseDeDatos;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class Caja  implements Runnable {
    private int idCaja;
    private AtomicInteger clientesAtendidos;
    private double totalVentas;
    private BaseDeDatos baseDeDatos;

    public Caja(int idCaja, BaseDeDatos baseDeDatos) {
        this.idCaja = idCaja;
        this.clientesAtendidos = new AtomicInteger(0);
        this.totalVentas = 0.0;
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            Cliente cliente = new Cliente(); // Asegúrate de que el constructor sea correcto.
            atenderCliente(cliente);
            try {
                Thread.sleep(rand.nextInt(3000) + 1000); // Tiempo de procesamiento aleatorio entre 1 y 4 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Volver a establecer el estado de interrupción
                break; // Salir del bucle
            }
        }
    }

    private void atenderCliente(Cliente cliente) {
        double totalCliente = cliente.calcularTotal();
        synchronized (this) { // Sincronizar solo al actualizar las variables compartidas
            totalVentas += totalCliente;
            clientesAtendidos.incrementAndGet();
        }
        baseDeDatos.guardarTransaccion(idCaja, totalCliente);
        System.out.println("Caja " + idCaja + " atendió a un cliente. Total: $" + String.format("%.2f", totalCliente));
    }

    public int getClientesAtendidos() {
        return clientesAtendidos.get();
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public int getIdCaja() {
        return idCaja;
    }
}

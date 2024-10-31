package org.example.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Cliente {
    private List<TipoArticulo> cesta;

    public Cliente() {
        cesta = new ArrayList<>();
        Random rand = new Random();
        int numeroArticulos = rand.nextInt(5) + 1; // Número aleatorio de artículos entre 1 y 5
        TipoArticulo[] articulos = TipoArticulo.values();
        for (int i = 0; i < numeroArticulos; i++) {
            TipoArticulo articuloAleatorio = articulos[rand.nextInt(articulos.length)];
            cesta.add(articuloAleatorio);
        }
    }

    public List<TipoArticulo> getCesta() {
        return new ArrayList<>(cesta);
    }

    public double calcularTotal() {
        return cesta.stream().mapToDouble(TipoArticulo::getPrecio).sum();
    }
}

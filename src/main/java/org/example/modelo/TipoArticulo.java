package org.example.modelo;


public enum TipoArticulo {
    FRUTAS(1.0),
    VERDURAS(0.8),
    BEBIDAS(1.5),
    LACTEOS(1.2),
    CARNES(2.0);

    private double precio;

    private TipoArticulo(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}

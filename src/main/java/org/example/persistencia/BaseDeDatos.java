package org.example.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.InputStream;
import java.util.Properties;

public class BaseDeDatos {
    private Connection conexion;

    public BaseDeDatos() {
        Properties propiedades = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo config.properties");
                return;
            }
            propiedades.load(input);
            String url = propiedades.getProperty("db.url");
            String usuario = propiedades.getProperty("db.usuario");
            String contraseña = propiedades.getProperty("db.contraseña");
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarTransaccion(int idCaja, double monto) {
        String sql = "INSERT INTO transacciones (id_caja, monto) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCaja);
            stmt.setDouble(2, monto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultarTransacciones() {
        String sql = "SELECT id_caja, COUNT(*) AS clientes_atendidos, SUM(monto) AS total_ventas FROM transacciones GROUP BY id_caja";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idCaja = rs.getInt("id_caja");
                int clientesAtendidos = rs.getInt("clientes_atendidos");
                double totalVentas = rs.getDouble("total_ventas");
                System.out.println("Caja " + idCaja + ": " + clientesAtendidos + " clientes, Total ventas: $" + String.format("%.2f", totalVentas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

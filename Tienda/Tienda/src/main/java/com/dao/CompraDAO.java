package com.dao;

import config.ConexionBD;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Compra;


public class CompraDAO {
    private Connection conexion;
    private ConexionBD conexionBD;

    public CompraDAO() {
        conexionBD = new ConexionBD();
        conexion = conexionBD.getConexion();
    }

    public void crearCompra(Compra compra) {
        String sql =  "INSERT INTO compras (fecha) VALUES (?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, compra.getFecha());
            statement.executeUpdate();
            System.out.println("Compra insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }
    
    public List<Compra> leerCompras() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras";
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fecha = resultSet.getString("fecha");
                Compra compra = new Compra(id, fecha);
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos: " + e.getMessage());
        }
        return compras;
    }

    public void actualizarCompra(Compra compra) {
        String sql = "UPDATE compras SET fecha = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, compra.getFecha());
            statement.setInt(2, compra.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos: " + e.getMessage());
        }
    }

    public void eliminarCompra(int id) {
        String sql = "DELETE FROM compras WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar los datos: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        conexionBD.closeConexion(conexion);
    }
}

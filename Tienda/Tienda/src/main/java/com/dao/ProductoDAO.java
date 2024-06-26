package com.dao;

import config.ConexionBD;
import model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ProductoDAO {
    private Connection conexion;
    private ConexionBD conexionBD;

    public ProductoDAO() { 
        conexionBD = new ConexionBD();
        conexion = conexionBD.getConexion();
    }

    public void crearProducto(Producto producto){
        String sql = "INSERT INTO productos (nombre, precio, categoriaId) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCategoriaId());
            statement.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error al guardar los datos: " + e.getMessage());
        } 
    }

    public List<Producto> leerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                int categoriaId = resultSet.getInt("categoriaId");
                Producto producto = new Producto(id, nombre, precio, categoriaId);
                productos.add(producto);
            }
        } catch (SQLException e) {
                System.out.println("Error al consultar los datos: " + e.getMessage());
        }
        return productos;
    }

    public void actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, categoriaId = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getCategoriaId());
            statement.setInt(4, producto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
                System.out.println("Error al actualizar los datos: " + e.getMessage());
        }
    }

    public void eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
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

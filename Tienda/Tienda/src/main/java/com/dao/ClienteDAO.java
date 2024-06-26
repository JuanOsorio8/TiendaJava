package com.dao;

import config.ConexionBD;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class ClienteDAO {

    private Connection conexion;
    private ConexionBD conexionBD;

    public ClienteDAO() {
        conexionBD = new ConexionBD();
        conexion = conexionBD.getConexion();
    }

    public void crearCliente(Cliente cliente) {
        if (!existeCliente(cliente)) {
        String sql = "INSERT INTO cliente (nombres, telefono) VALUES (?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys(); // Recuperar el ID generado
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                System.out.println("Cliente insertado con ID: " + id);
            }
            System.out.println("Cliente insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
        } else {
            System.out.println("el valor  ya existe");
        }
    }

    public List<Cliente> leerCliente() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Statement statement = conexion.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombres");
                String telefono = resultSet.getString("telefono");
                Cliente cliente = new Cliente(nombre);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos: " + e.getMessage());
        }
        return clientes;
    }

    public void actualizarClinete(Cliente cliente) {
        String sql = "UPDATE cliente SET nombres = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());
            statement.setInt(3, cliente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos: " + e.getMessage());
        }
    }

    public void eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar los datos: " + e.getMessage());
        }
    }

    public boolean existeCliente(Cliente cliente) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE nombres = ? and telefono = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getTelefono());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna verdadero si ya existe
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del cliente: " + e.getMessage());
        }
        return false; // Si no hay errores, pero no se encontró el cliente
    }

    public int verificarExisteCliente() {

        String sql = "SELECT COUNT(?) * FROM cliente  P WHERE P.ID";
        try (Statement statement = conexion.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombres");
                String telefono = resultSet.getString("telefono");
                Cliente cliente = new Cliente(nombre);

            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos: " + e.getMessage());
        }
        return 4;
    }

    public void cerrarConexion() {
        conexionBD.closeConexion(conexion);
    }
}

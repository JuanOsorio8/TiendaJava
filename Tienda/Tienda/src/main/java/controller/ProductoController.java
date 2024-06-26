package controller;

import com.dao.ProductoDAO;
import model.Producto;
import views.ProductoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {
    private ProductoView productoView;
    private ProductoDAO productoDAO;

    public ProductoController(ProductoView productoView, ProductoDAO productoDAO) {
        this.productoView = productoView;
        this.productoDAO = productoDAO;

        // Configurar listeners para los botones
        productoView.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });

        productoView.btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        productoView.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        productoView.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        productoView.btnVolver1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Puedes implementar lógica para volver atrás
                JOptionPane.showMessageDialog(productoView, "Implementa la lógica para volver atrás.");
            }
        });
    }

    private void crearProducto() {
        String nombre = productoView.txtNombre.getText();
        int precio = Integer.parseInt(productoView.txtPrecio.getText());
        int categoriaId = Integer.parseInt(productoView.txtIdCategoria.getText());

        Producto producto = new Producto(nombre, precio, categoriaId);
        productoDAO.crearProducto(producto);

        JOptionPane.showMessageDialog(productoView, "Producto creado exitosamente.");
    }

    private void mostrarProductos() {
        List<Producto> productos = productoDAO.leerProductos();
        DefaultTableModel model = (DefaultTableModel) productoView.tablaProducto.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de agregar datos

        for (Producto producto : productos) {
            Object[] row = {producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getCategoriaId()};
            model.addRow(row);
        }
    }

    private void actualizarProducto() {
        int id = Integer.parseInt(productoView.txtIdProducto.getText());
        String nombre = productoView.txtNombre.getText();
        double precio = Double.parseDouble(productoView.txtPrecio.getText());
        int categoriaId = Integer.parseInt(productoView.txtIdCategoria.getText());

        Producto producto = new Producto(id, nombre, precio, categoriaId);
        productoDAO.actualizarProducto(producto);

        JOptionPane.showMessageDialog(productoView, "Producto actualizado exitosamente.");
    }

    private void eliminarProducto() {
        int id = Integer.parseInt(productoView.txtIdProducto.getText());
        productoDAO.eliminarProducto(id);

        JOptionPane.showMessageDialog(productoView, "Producto eliminado exitosamente.");
    }
}
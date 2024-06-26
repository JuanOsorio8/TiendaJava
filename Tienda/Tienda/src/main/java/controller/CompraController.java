package controller;

import com.dao.CategoriaDAO;
import com.dao.CompraDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Compra;
import views.CompraView;
import views.CategoriaView;
import views.ClienteView;
import views.ProductoView;

public class CompraController {

    private final CompraView view;
    private final CompraDAO dao;

    public CompraController(CompraView view, CompraDAO dao) {
        this.view = view;
        this.dao = dao;

        // Listener para el botón Crear
        this.view.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCompra();
            }
        });

        // Listener para el botón Mostrar
        this.view.btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCompras();
            }
        });

        // Listener para el botón Actualizar
        this.view.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCompra();
            }
        });

        // Listener para el botón Eliminar
        this.view.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCompra();
            }
        });

        // Listener para el botón Cliente
        this.view.btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirClienteView();
            }
        });

        // Listener para el botón Categoría
        this.view.btnCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(view);
                frame.dispose(); // Cierra el JFrame actual

                // Abrir la nueva vista UsuarioView
                JFrame libroFrame = new JFrame("Gestión de Usuarios");
                CategoriaView view = new CategoriaView();
                CategoriaDAO dao = new CategoriaDAO();
                CategoriaController prestamo = new CategoriaController(view, dao);
                libroFrame.getContentPane().add(view);

                // Establecer el tamaño del nuevo marco, 
                // hacerlo visible y centrarlo en la pantalla
                libroFrame.pack();
                libroFrame.setVisible(true);
                libroFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ajusta esto según tu lógica de cierre
                libroFrame.setLocationRelativeTo(null);
            }
        });

        // Listener para el botón Producto
        this.view.btnProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProductoView();
            }
        });
    }

    private void cargarCompras() {
        List<Compra> compras = dao.leerCompras();
        DefaultTableModel model = (DefaultTableModel) view.tablaCompra.getModel();
        model.setRowCount(0);
        for (Compra compra : compras) {
            model.addRow(new Object[]{compra.getId(), compra.getFecha()});
        }
    }

    private void crearCompra() {
        try {
            Compra compra = new Compra();
            compra.setFecha(view.txtFecha.getText());
            dao.crearCompra(compra);
            cargarCompras();
            JOptionPane.showMessageDialog(null, "Compra creada correctamente");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo de Fecha debe ser una cadena válida");
        }
    }

    private void actualizarCompra() {
        try {
            int filaSeleccionada = view.tablaCompra.getSelectedRow();
            if (filaSeleccionada != -1) {
                Compra compra = new Compra();
                compra.setId(Integer.parseInt(view.txtIdCompra.getText()));
                compra.setFecha(view.txtFecha.getText());
                dao.actualizarCompra(compra);
                cargarCompras();
                JOptionPane.showMessageDialog(null, "Compra actualizada correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una compra de la tabla para actualizar");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El campo de Id debe ser un número entero");
        }
    }

    private void eliminarCompra() {
        int filaSeleccionada = view.tablaCompra.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idCompra = (int) view.tablaCompra.getValueAt(filaSeleccionada, 0);
            dao.eliminarCompra(idCompra);
            cargarCompras();
            JOptionPane.showMessageDialog(null, "Compra eliminada correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una compra de la tabla para eliminar");
        }
    }

    private void limpiarCampos() {
        view.txtIdCompra.setText("");
        view.txtFecha.setText("");
    }

    private void abrirClienteView() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClienteView clienteView = new ClienteView();
                // Inicializar DAO de Cliente si es necesario
                // ClienteDAO clienteDAO = new ClienteDAO();
                // ClienteController clienteController = new ClienteController(clienteView, clienteDAO);

                JFrame frame = new JFrame("Gestor de Clientes");
                frame.setContentPane(clienteView);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }



    private void abrirProductoView() {
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProductoView productoView = new ProductoView();
                // Inicializar DAO de Producto si es necesario
                // ProductoDAO productoDAO = new ProductoDAO();
                // ProductoController productoController = new ProductoController(productoView, productoDAO);

                JFrame frame = new JFrame("Gestor de Productos");
                frame.setContentPane(productoView);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}

package controller;

import com.dao.CompraDAO;
import com.dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import views.CompraView;
import views.ClienteView;

public class ClienteController {

    private ClienteView view;
    private ClienteDAO dao;
    

    public ClienteController(ClienteView view, ClienteDAO dao) {
        this.view = view;
        this.dao = dao;
        this.view.btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuario();
            }
        });
        this.view.btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios();
            }
        });
        this.view.btnActualizar.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios();
                if (view.txtIdCliente.getText().isEmpty()
                        || view.txtNombre.getText().isEmpty()
                        || view.txtTelefono.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Por favor, seleccione un usuario de la tabla para actualizar.");
                } else {
                    actualizarUsuario();
                }

            }
        }
        );

        this.view.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios();
                if (view.txtIdCliente.getText().isEmpty()
                        || view.txtNombre.getText().isEmpty()
                        || view.txtTelefono.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Por favor, seleccione un usuario de la tabla para eliminar.");
                } else {
                    int response = JOptionPane.showConfirmDialog(
                            view,
                            "¿Está seguro de que desea eliminar este usuario?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        eliminarUsuario();
                    }
                }
            }
        }
        );

        this.view.tablaCliente.getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e
                    ) {
                        if (!e.getValueIsAdjusting() && view.tablaCliente.getSelectedRow() != -1) {
                            mostrarDatosSeleccionados();
                        }
                    }
                }
                        
                );
        
        this.view.btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
                if (currentFrame != null) {
                    currentFrame.dispose();
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        CompraView view = new CompraView();
                        CompraDAO dao = new CompraDAO();
                        
                        CompraController controller = new CompraController(view, dao);

                        JFrame frame = new JFrame("Gestor de Usuarios");
                        frame.setContentPane(view);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);

                    }
                });
            }
        });
    }

    private void crearUsuario() {
        String nombre = view.txtNombre.getText();
        String telefono = view.txtTelefono.getText();

        Cliente cliente = new Cliente(nombre, telefono);
        dao.crearCliente(cliente);

        JOptionPane.showMessageDialog(view, "Usuario creado exitosamente.");
        limpiarCampos();
    }

    private void mostrarUsuarios() {
        List<Cliente> listaCliente = dao.leerCliente();
        DefaultTableModel model = (DefaultTableModel) view.tablaCliente.getModel();
        model.setRowCount(0);

        for (Cliente cliente : listaCliente) {
            model.addRow(new Object[]{cliente.getId(), cliente.getNombre(), cliente.getTelefono()});
        }
    }

    private void actualizarUsuario() {
        int id = Integer.parseInt(view.txtIdCliente.getText());
        String nombre = view.txtNombre.getText();
        String telefono = view.txtTelefono.getText();

        Cliente cliente = new Cliente(nombre, telefono);
        dao.actualizarClinete(cliente);

        JOptionPane.showMessageDialog(view, "Usuario actualizado exitosamente.");
        limpiarCampos();
        mostrarUsuarios(); // Actualizar la tabla después de actualizar el usuario
    }

    private void eliminarUsuario() {
        int id = Integer.parseInt(view.txtIdCliente.getText());
        dao.eliminarCliente(id);

        JOptionPane.showMessageDialog(view, "Usuario eliminado exitosamente.");
        limpiarCampos();
        mostrarUsuarios(); // Actualizar la tabla después de eliminar el usuario
    }

    private void limpiarCampos() {
        view.txtIdCliente.setText("");
        view.txtNombre.setText("");
        view.txtTelefono.setText("");
    }

    private void mostrarDatosSeleccionados() {
        int row = view.tablaCliente.getSelectedRow();
        view.txtIdCliente.setText(view.tablaCliente.getValueAt(row, 0).toString());
        view.txtNombre.setText(view.tablaCliente.getValueAt(row, 1).toString());
        view.txtTelefono.setText(view.tablaCliente.getValueAt(row, 3).toString());
    }
}
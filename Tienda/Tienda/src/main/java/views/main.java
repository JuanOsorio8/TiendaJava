package views;

import com.dao.CompraDAO;
import controller.CategoriaController;
import controller.ClienteController;
import controller.CompraController;
import controller.ProductoController;

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CompraView view = new CompraView();
                CompraDAO dao = new CompraDAO();
                CompraController controller = new CompraController(view, dao);

                JFrame frame = new JFrame("Gestor de Compras");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(view);
                frame.pack();
                frame.setLocationRelativeTo(null); // Centrar ventana en la pantalla
                frame.setVisible(true);
            }
        });
    }
}

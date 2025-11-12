package co.edu.uniquindio.poo.templatemetthod;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Launcher {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JuegoInterfaz.iniciarJuego();
                } catch (Exception e) {
                    String mensaje = "Error al iniciar el juego";
                    System.err.println(mensaje);
                    e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(
                        null,
                        mensaje,
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}

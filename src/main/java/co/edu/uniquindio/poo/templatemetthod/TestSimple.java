package co.edu.uniquindio.poo.templatemetthod;

import javax.swing.JOptionPane;

/**
 * Clase de prueba simple para verificar que Swing funciona
 */
public class TestSimple {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba simple...");
        try {
            JOptionPane.showMessageDialog(
                null,
                "Prueba: Si ves este mensaje, Swing funciona correctamente.",
                "Prueba",
                JOptionPane.INFORMATION_MESSAGE
            );
            System.out.println("Prueba exitosa!");
        } catch (Exception e) {
            System.err.println("Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


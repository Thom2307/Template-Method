package co.edu.uniquindio.poo.templatemetthod;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class JuegoInterfaz {

    public static void iniciarJuego() {
        try {
            // Mostrar mensaje de bienvenida
            JOptionPane.showMessageDialog(
                null,
                "¡Bienvenido al Juego de Aventura!\n\nSelecciona un personaje para comenzar.",
                "Juego de Aventura",
                JOptionPane.INFORMATION_MESSAGE
            );

            // Seleccionar personaje
            Juego personaje = seleccionarPersonaje();

            if (personaje == null) {
                JOptionPane.showMessageDialog(
                    null,
                    "No se seleccionó ningún personaje. El juego ha terminado.",
                    "Juego Cancelado",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Ejecutar el juego
            List<String> acciones = personaje.jugar();

            // Mostrar las acciones en un diálogo
            mostrarAcciones(personaje.getNombre(), acciones);
        } catch (Exception e) {
            System.err.println("Error en iniciarJuego: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static Juego seleccionarPersonaje() {
        try {
            // Crear los personajes disponibles
            Caballero caballero = new Caballero();
            Arquero arquero = new Arquero();
            Mago mago = new Mago();
            Juego[] personajes = {caballero, arquero, mago};
            String[] nombres = new String[personajes.length];
            ImageIcon[] iconos = new ImageIcon[personajes.length];

            // Preparar nombres e iconos
            for (int i = 0; i < personajes.length; i++) {
                nombres[i] = personajes[i].getNombre();
                iconos[i] = cargarImagen(personajes[i].getRutaImagen());
            }

            // Crear panel con las opciones
            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JButton[] botones = new JButton[personajes.length];
            final Juego[] seleccionado = new Juego[1];
            final JDialog[] dialogRef = new JDialog[1];

            for (int i = 0; i < personajes.length; i++) {
                final int index = i;
                final Juego personaje = personajes[i];

                botones[i] = new JButton(nombres[i]);
                botones[i].setFont(new Font("Arial", Font.BOLD, 16));
                botones[i].setPreferredSize(new Dimension(200, 50));

                if (iconos[i] != null) {
                    Image img = iconos[i].getImage();
                    Image imgRedimensionada = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    botones[i].setIcon(new ImageIcon(imgRedimensionada));
                }

                botones[i].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        seleccionado[0] = personaje;
                        if (dialogRef[0] != null) {
                            dialogRef[0].dispose();
                        }
                    }
                });

                panel.add(botones[i]);
            }

            // Crear el diálogo
            JDialog dialog = new JDialog((Frame) null, "Selecciona tu Personaje", true);
            dialogRef[0] = dialog;
            dialog.setLayout(new BorderLayout());
            dialog.add(new JLabel("Elige tu personaje:", JLabel.CENTER), BorderLayout.NORTH);
            dialog.add(panel, BorderLayout.CENTER);

            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    seleccionado[0] = null;
                    dialog.dispose();
                }
            });
            dialog.add(btnCancelar, BorderLayout.SOUTH);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            return seleccionado[0];
        } catch (Exception e) {
            System.err.println("Error en seleccionarPersonaje: " + e.getMessage());
            e.printStackTrace();
            // Fallback: usar JOptionPane simple
            return seleccionarPersonajeSimple();
        }
    }

    private static Juego seleccionarPersonajeSimple() {
        String[] opciones = {"Caballero", "Arquero", "Mago", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Selecciona tu personaje:",
            "Selección de Personaje",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion == 0) {
            return new Caballero();
        } else if (seleccion == 1) {
            return new Arquero();
        } else if (seleccion == 2) {
            return new Mago();
        } else {
            return null;
        }
    }

    private static JPanel crearPanelPersonaje(Juego personaje, Runnable onSeleccionar) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);

        // Cargar imagen
        ImageIcon icono = cargarImagen(personaje.getRutaImagen());
        JLabel labelImagen = new JLabel();

        if (icono != null) {
            // Redimensionar imagen si es necesario
            Image imagen = icono.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(180, 250, Image.SCALE_SMOOTH);
            labelImagen.setIcon(new ImageIcon(imagenRedimensionada));
        } else {
            // Si no hay imagen, mostrar texto
            labelImagen.setText("<html><center>Imagen no<br>disponible</center></html>");
            labelImagen.setHorizontalAlignment(JLabel.CENTER);
            labelImagen.setVerticalAlignment(JLabel.CENTER);
            labelImagen.setPreferredSize(new Dimension(180, 250));
            labelImagen.setOpaque(true);
            labelImagen.setBackground(Color.LIGHT_GRAY);
            labelImagen.setForeground(Color.DARK_GRAY);
        }

        labelImagen.setHorizontalAlignment(JLabel.CENTER);
        labelImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón de selección
        JButton btnSeleccionar = new JButton(personaje.getNombre());
        btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 16));
        btnSeleccionar.setPreferredSize(new Dimension(150, 40));
        btnSeleccionar.setBackground(new Color(70, 130, 180));
        btnSeleccionar.setForeground(Color.WHITE);
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setBorderPainted(false);
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                onSeleccionar.run();
            }
        });

        // Efecto hover
        btnSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSeleccionar.setBackground(new Color(100, 150, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSeleccionar.setBackground(new Color(70, 130, 180));
            }
        });

        panel.add(labelImagen, BorderLayout.CENTER);
        panel.add(btnSeleccionar, BorderLayout.SOUTH);

        return panel;
    }

    private static ImageIcon cargarImagen(String ruta) {
        try {
            URL url = JuegoInterfaz.class.getResource(ruta);
            if (url != null) {
                return new ImageIcon(url);
            } else {
                // Si no se encuentra la imagen, crear una imagen de placeholder
                System.out.println("No se encontró la imagen: " + ruta);
                return crearImagenPlaceholder();
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + ruta);
            e.printStackTrace();
            return crearImagenPlaceholder();
        }
    }

    private static ImageIcon crearImagenPlaceholder() {
        // Crear una imagen de placeholder simple
        BufferedImage img = new BufferedImage(180, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // Fondo degradado
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(220, 220, 220),
            0, 250, new Color(180, 180, 180)
        );
        g.setPaint(gradient);
        g.fillRect(0, 0, 180, 250);

        // Borde
        g.setColor(new Color(150, 150, 150));
        g.setStroke(new BasicStroke(2));
        g.drawRect(1, 1, 177, 247);

        // Texto
        g.setColor(new Color(100, 100, 100));
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        String texto = "Imagen no disponible";
        FontMetrics fm = g.getFontMetrics();
        int x = (180 - fm.stringWidth(texto)) / 2;
        int y = 250 / 2;
        g.drawString(texto, x, y);

        g.dispose();
        return new ImageIcon(img);
    }

    private static void mostrarAcciones(String nombrePersonaje, List<String> acciones) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("<html><body style='width: 400px;'>");
        mensaje.append("<h2 style='text-align: center; color: #4682B4;'>Aventura del ");
        mensaje.append(nombrePersonaje);
        mensaje.append("</h2>");
        mensaje.append("<ul style='font-size: 14px;'>");

        for (String accion : acciones) {
            mensaje.append("<li style='margin-bottom: 8px;'>");
            mensaje.append(accion);
            mensaje.append("</li>");
        }

        mensaje.append("</ul></body></html>");

        StringBuilder titulo = new StringBuilder();
        titulo.append("Aventura Completada - ");
        titulo.append(nombrePersonaje);

        JOptionPane.showMessageDialog(
            null,
            mensaje.toString(),
            titulo.toString(),
            JOptionPane.INFORMATION_MESSAGE
        );

        // Preguntar si quiere jugar de nuevo
        int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Deseas jugar de nuevo?",
            "Jugar de Nuevo",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            iniciarJuego();
        } else {
            JOptionPane.showMessageDialog(
                null,
                "¡Gracias por jugar!",
                "Fin del Juego",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}


package co.edu.uniquindio.poo.templatemetthod;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class JuegoInterfaz {

    public static void iniciarJuego() {
        try {
            // Mostrar mensaje de bienvenida mejorado
            mostrarBienvenida();

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

            // Crear el diálogo principal
            JDialog dialog = new JDialog((Frame) null, "Selecciona tu Personaje", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            
            // Panel principal con fondo degradado
            JPanel panelPrincipal = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(30, 30, 50),
                        0, getHeight(), new Color(60, 60, 90)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Título mejorado
            JLabel titulo = new JLabel("Elige tu Personaje", JLabel.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 28));
            titulo.setForeground(new Color(255, 215, 0));
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            panelPrincipal.add(titulo, BorderLayout.NORTH);

            // Panel para los personajes (horizontal)
            JPanel panelPersonajes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            panelPersonajes.setOpaque(false);

            final Juego[] seleccionado = new Juego[1];

            // Crear panel para cada personaje
            for (Juego personaje : personajes) {
                JPanel panelPersonaje = crearPanelPersonaje(personaje, () -> {
                    seleccionado[0] = personaje;
                    dialog.dispose();
                });
                panelPersonajes.add(panelPersonaje);
            }

            panelPrincipal.add(panelPersonajes, BorderLayout.CENTER);

            // Panel de botones
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBotones.setOpaque(false);
            panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

            JButton btnCancelar = crearBotonEstilizado("Cancelar", new Color(180, 50, 50), new Color(200, 70, 70));
            btnCancelar.addActionListener(e -> {
                seleccionado[0] = null;
                dialog.dispose();
            });
            panelBotones.add(btnCancelar);

            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

            dialog.add(panelPrincipal);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setResizable(false);
            dialog.setVisible(true);

            return seleccionado[0];
        } catch (Exception e) {
            System.err.println("Error en seleccionarPersonaje: " + e.getMessage());
            e.printStackTrace();
            // Fallback: usar JOptionPane simple
            return seleccionarPersonajeSimple();
        }
    }

    private static JPanel crearPanelPersonaje(Juego personaje, Runnable onSeleccionar) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Panel interno con fondo
        JPanel panelInterno = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(40, 40, 60),
                    0, getHeight(), new Color(70, 70, 100)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        panelInterno.setOpaque(false);

        // Cargar y redimensionar imagen manteniendo proporción
        ImageIcon icono = cargarImagen(personaje.getRutaImagen());
        JLabel labelImagen = new JLabel();
        labelImagen.setHorizontalAlignment(JLabel.CENTER);
        labelImagen.setVerticalAlignment(JLabel.CENTER);

        if (icono != null) {
            ImageIcon imagenRedimensionada = redimensionarImagenManteniendoProporcion(icono, 200, 280);
            labelImagen.setIcon(imagenRedimensionada);
        } else {
            labelImagen.setText("<html><center>Imagen no<br>disponible</center></html>");
            labelImagen.setForeground(Color.WHITE);
            labelImagen.setPreferredSize(new Dimension(200, 280));
        }

        labelImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInterno.add(labelImagen, BorderLayout.CENTER);

        // Botón de selección estilizado
        JButton btnSeleccionar = crearBotonEstilizado(
            personaje.getNombre(),
            new Color(70, 130, 180),
            new Color(100, 150, 200)
        );
        btnSeleccionar.setPreferredSize(new Dimension(180, 45));
        btnSeleccionar.addActionListener(e -> onSeleccionar.run());

        panelInterno.add(btnSeleccionar, BorderLayout.SOUTH);
        panel.add(panelInterno);

        return panel;
    }

    private static JButton crearBotonEstilizado(String texto, Color colorBase, Color colorHover) {
        final boolean[] hover = {false};
        
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Color colorActual = hover[0] ? colorHover : colorBase;
                GradientPaint gradient = new GradientPaint(
                    0, 0, colorActual.brighter(),
                    0, getHeight(), colorActual.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Borde
                g2d.setColor(colorActual.darker());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);

                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 10, 10);

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hover[0] = true;
                boton.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hover[0] = false;
                boton.repaint();
            }
        });

        return boton;
    }

    private static ImageIcon redimensionarImagenManteniendoProporcion(ImageIcon icono, int maxAncho, int maxAlto) {
        Image imagen = icono.getImage();
        int anchoOriginal = icono.getIconWidth();
        int altoOriginal = icono.getIconHeight();

        // Calcular dimensiones manteniendo proporción
        double ratioAncho = (double) maxAncho / anchoOriginal;
        double ratioAlto = (double) maxAlto / altoOriginal;
        double ratio = Math.min(ratioAncho, ratioAlto);

        int nuevoAncho = (int) (anchoOriginal * ratio);
        int nuevoAlto = (int) (altoOriginal * ratio);

        // Redimensionar con alta calidad
        Image imagenRedimensionada = imagen.getScaledInstance(
            nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH
        );

        return new ImageIcon(imagenRedimensionada);
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

    private static void mostrarBienvenida() {
        JDialog dialog = new JDialog((Frame) null, "Bienvenido", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(30, 30, 50),
                    0, getHeight(), new Color(60, 60, 90)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("¡Bienvenido al Juego de Aventura!", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel mensaje = new JLabel("<html><center>Selecciona un personaje para comenzar<br>tu épica aventura</center></html>", JLabel.CENTER);
        mensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        mensaje.setForeground(Color.WHITE);

        JButton btnContinuar = crearBotonEstilizado("Continuar", new Color(70, 130, 180), new Color(100, 150, 200));
        btnContinuar.setPreferredSize(new Dimension(150, 40));
        btnContinuar.addActionListener(e -> dialog.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelBoton.add(btnContinuar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(mensaje, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private static void mostrarAcciones(String nombrePersonaje, List<String> acciones) {
        JDialog dialog = new JDialog((Frame) null, "Aventura Completada", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(30, 30, 50),
                    0, getHeight(), new Color(60, 60, 90)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        panel.setOpaque(false);

        // Título
        JLabel titulo = new JLabel("Aventura del " + nombrePersonaje, JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Panel de acciones con scroll
        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setOpaque(false);
        panelAcciones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        for (String accion : acciones) {
            JLabel labelAccion = new JLabel("<html><div style='width: 400px; padding: 5px;'>" + 
                "• " + accion + "</div></html>");
            labelAccion.setFont(new Font("Arial", Font.PLAIN, 14));
            labelAccion.setForeground(Color.WHITE);
            labelAccion.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            panelAcciones.add(labelAccion);
        }

        JScrollPane scrollPane = new JScrollPane(panelAcciones);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(450, 200));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton btnJugarNuevo = crearBotonEstilizado("Jugar de Nuevo", new Color(70, 130, 180), new Color(100, 150, 200));
        btnJugarNuevo.setPreferredSize(new Dimension(150, 40));
        btnJugarNuevo.addActionListener(e -> {
            dialog.dispose();
            iniciarJuego();
        });

        JButton btnSalir = crearBotonEstilizado("Salir", new Color(180, 50, 50), new Color(200, 70, 70));
        btnSalir.setPreferredSize(new Dimension(150, 40));
        btnSalir.addActionListener(e -> {
            dialog.dispose();
            JOptionPane.showMessageDialog(
                null,
                "<html><center><b>¡Gracias por jugar!</b></center></html>",
                "Fin del Juego",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        panelBotones.add(btnJugarNuevo);
        panelBotones.add(btnSalir);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}


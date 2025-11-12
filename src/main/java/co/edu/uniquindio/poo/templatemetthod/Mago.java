package co.edu.uniquindio.poo.templatemetthod;

public class Mago extends Juego {
    
    @Override
    protected void matarDragon() {
        agregarAccion("El mago destruye al dragón con un poderoso hechizo.");
    }

    @Override
    public String getNombre() {
        return "Mago";
    }

    @Override
    public String getRutaImagen() {
        return "/co/edu/uniquindio/poo/templatemetthod/imagenes/mago.jpg";
    }
}

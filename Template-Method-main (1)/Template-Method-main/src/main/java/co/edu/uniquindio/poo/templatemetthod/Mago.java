package co.edu.uniquindio.poo.templatemetthod;

public class Mago extends Juego {
    
    @Override
    protected void matarDragon() {
        agregarAccion("El mago destruye al dragón con un poderoso hechizo.");
    }

    @Override
    protected void salvarPrincesa() {
        agregarAccion("El mago salva a la princesa usando su magia para teletransportarla a un lugar seguro.");
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

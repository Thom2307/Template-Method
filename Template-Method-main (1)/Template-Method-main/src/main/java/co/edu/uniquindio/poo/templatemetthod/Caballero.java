package co.edu.uniquindio.poo.templatemetthod;

public class Caballero extends Juego {
    @Override
    protected void matarDragon() {
        agregarAccion("El caballero mata al dragón con su espada.");
    }

    @Override
    protected void salvarPrincesa() {
        agregarAccion("El caballero salva a la princesa y se casa con ella.");
    }

    @Override
    public String getNombre() {
        return "Caballero";
    }

    @Override
    public String getRutaImagen() {
        return "/co/edu/uniquindio/poo/templatemetthod/imagenes/caballero.jpg";
    }
}

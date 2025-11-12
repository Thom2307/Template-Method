package co.edu.uniquindio.poo.templatemetthod;

public class Arquero extends Juego{

    @Override
    protected void matarDragon() {
        agregarAccion("El arquero disparó su arco y acabó con el dragón con un tiro certero en la cabeza");
    }

    @Override
    protected void salvarPrincesa() {
        agregarAccion("El arquero salva a la princesa para cobrar la recompensa por su rescate");
    }

    @Override
    public String getNombre() {
        return "Arquero";
    }

    @Override
    public String getRutaImagen() {
        return "/co/edu/uniquindio/poo/templatemetthod/imagenes/arquero.jpg";
    }
}

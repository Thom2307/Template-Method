package co.edu.uniquindio.poo.templatemetthod;

public class Arquero extends Juego{

    @Override
    protected void matarDragon() {
        System.out.println("El arquero disparo su arco y acabo con el dragon con un tiro certero en la cabeza");
    }

    @Override
    protected void salvarPrincesa() {
        System.out.println("El arquero salva a la princesa para cobrar la recompensa por su rescate");
    }
}

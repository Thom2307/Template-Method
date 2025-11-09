package co.edu.uniquindio.poo.templatemetthod;

class Caballero extends Juego {
    @Override
    protected void matarDragon() {
        System.out.println("El caballero mata al dragón con su espada.");
    }

    @Override
    protected void salvarPrincesa() {
        System.out.println("El caballero salva a la princesa y se casa con ella.");
    }
}

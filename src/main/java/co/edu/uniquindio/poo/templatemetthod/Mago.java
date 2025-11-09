package co.edu.uniquindio.poo.templatemetthod;

class Mago extends Juego {
    @Override
    protected void matarDragon() {
        System.out.println("El mago destruye al dragón con un poderoso hechizo.");
    }

    // No sobrescribe salvarPrincesa(), porque no le importa
}

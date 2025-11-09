package co.edu.uniquindio.poo.templatemetthod;

// Clase abstracta que define el "template"
abstract class Juego {

    // Método template (define el algoritmo general)
    public final void jugar() {
        entrarCueva();       // Paso fijo
        matarDragon();       // Paso abstracto (cada subclase lo define)
        salvarPrincesa();    // Hook (opcional)
    }

    // Paso fijo
    private void entrarCueva() {
        System.out.println("El héroe entra en la cueva.");
    }

    // Paso abstracto (cada personaje lo hace distinto)
    protected abstract void matarDragon();

    // Hook (opcional — por defecto no hace nada)
    protected void salvarPrincesa() {
        // vacío: las subclases pueden sobrescribirlo si quieren
    }
}

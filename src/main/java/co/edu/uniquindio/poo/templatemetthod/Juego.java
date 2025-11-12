package co.edu.uniquindio.poo.templatemetthod;

import java.util.ArrayList;
import java.util.List;

public abstract class Juego {

    private List<String> acciones = new ArrayList<>();

    public final List<String> jugar() {
        acciones.clear();
        entrarCueva();
        matarDragon();
        salvarPrincesa();
        return new ArrayList<>(acciones);
    }

    private void entrarCueva() {
        String mensaje = "El héroe entra en la cueva.";
        agregarAccion(mensaje);
    }

    protected abstract void matarDragon();

    protected void salvarPrincesa() {
    }

    protected void agregarAccion(String accion) {
        acciones.add(accion);
        System.out.println(accion);
    }

    public abstract String getNombre();

    public abstract String getRutaImagen();
}

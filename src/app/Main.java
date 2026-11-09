package app;

import Modelo.GestorInventario;
import Vista.VentanaInventario;
import Controlador.ControladorInventario;

public class Main {

    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println("Modo: " + args[0]);
        }

        GestorInventario gestor = new GestorInventario();
        VentanaInventario vista = new VentanaInventario();

        new ControladorInventario(gestor, vista);

        vista.setVisible(true);
    }
}

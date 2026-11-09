package Modelo;

import java.util.ArrayList;
import java.io.*;

public class GestorInventario {

    private ArrayList<Producto> productos;

    public GestorInventario() {
        productos = new ArrayList<>();
        cargarTXT();
    }

    public boolean registrarProducto(int codigo, String nombre,
                                     double precio, int stock) {

        if (codigo <= 0 || nombre == null || nombre.trim().isEmpty()
                || !Double.isFinite(precio) || precio <= 0 || stock < 0) {
            return false;
        }

        if (buscarProducto(codigo) != null) {
            return false;
        }

        Producto producto = new Producto(codigo, nombre, precio, stock);
        productos.add(producto);
        guardarTXT();
        return true;
    }

    public ArrayList<Producto> consultarProductos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarProducto(int codigo) {

        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }

        return null;
    }

    public int contarProductos() {
        return UtilidadesGenericas.contarElementos(productos);
    }

    public void guardarTXT() {

        try (PrintWriter archivo = new PrintWriter(
                new FileWriter("productos.txt"))) {

            for (Producto producto : productos) {
                archivo.println(
                        producto.getCodigo() + ";" +
                        producto.getNombre() + ";" +
                        producto.getPrecio() + ";" +
                        producto.getStock()
                );
            }

        } catch (IOException e) {
            System.out.println("Error al guardar productos.");
        }
    }

    public void cargarTXT() {

        File archivo = new File("productos.txt");

        if (!archivo.exists()) {
            return;
        }

        productos.clear();

        try (BufferedReader lector = new BufferedReader(
                new FileReader(archivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length == 4) {
                    try {
                        int codigo = Integer.parseInt(datos[0]);
                        String nombre = datos[1];
                        double precio = Double.parseDouble(datos[2]);
                        int stock = Integer.parseInt(datos[3]);

                        if (codigo > 0 && !nombre.trim().isEmpty()
                                && Double.isFinite(precio)
                                && precio > 0 && stock >= 0
                                && buscarProducto(codigo) == null) {

                            productos.add(new Producto(
                                    codigo, nombre, precio, stock));
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Producto no valido en TXT.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar productos.");
        }
    }
}

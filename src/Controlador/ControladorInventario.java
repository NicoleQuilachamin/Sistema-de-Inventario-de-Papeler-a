package Controlador;

import Modelo.Producto;
import Modelo.GestorInventario;
import Vista.VentanaInventario;

import javax.swing.JOptionPane;

public class ControladorInventario {

    private GestorInventario gestor;
    private VentanaInventario vista;

    public ControladorInventario(GestorInventario gestor,
                                 VentanaInventario vista) {

        this.gestor = gestor;
        this.vista = vista;

        vista.btnRegistrar.addActionListener(e -> registrarProducto());
        vista.btnConsultar.addActionListener(e -> consultarProductos());
        vista.btnVerificar.addActionListener(e -> verificarStock());

        consultarProductos();
    }

    private void registrarProducto() {

        String codigoTexto = vista.txtCodigo.getText().trim();
        String nombre = vista.txtNombre.getText().trim();
        String precioTexto = vista.txtPrecio.getText().trim();
        String stockTexto = vista.txtStock.getText().trim();

        if (codigoTexto.isEmpty() || nombre.isEmpty()
                || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            mostrarMensaje("Complete todos los campos.");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoTexto);
            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            if (codigo <= 0) {
                mostrarMensaje("El codigo debe ser mayor a cero.");
                return;
            }

            if (!Double.isFinite(precio) || precio <= 0) {
                mostrarMensaje("El precio debe ser mayor a cero.");
                return;
            }

            if (stock < 0) {
                mostrarMensaje("El stock no puede ser negativo.");
                return;
            }

            if (gestor.buscarProducto(codigo) != null) {
                mostrarMensaje("Ya existe un producto con ese codigo.");
                return;
            }

            if (gestor.registrarProducto(codigo, nombre, precio, stock)) {

                mostrarMensaje("Producto registrado correctamente.");

                vista.txtCodigo.setText("");
                vista.txtNombre.setText("");
                vista.txtPrecio.setText("");
                vista.txtStock.setText("");

                consultarProductos();
            } else {
                mostrarMensaje("No se pudo registrar el producto.");
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("Ingrese datos numericos validos.");
        }
    }

    private void consultarProductos() {

        vista.modeloTabla.setRowCount(0);

        for (Producto producto : gestor.consultarProductos()) {

            vista.modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    String.format("%.2f", producto.getPrecio()),
                    producto.getStock()
            });
        }

        System.out.println("Productos registrados: "
                + gestor.contarProductos());
    }

    private void verificarStock() {

        String texto = vista.txtCodigo.getText().trim();

        if (texto.isEmpty()) {
            mostrarMensaje("Ingrese el codigo del producto.");
            return;
        }

        try {
            int codigo = Integer.parseInt(texto);

            Producto producto = gestor.buscarProducto(codigo);

            if (producto == null) {
                mostrarMensaje("Producto no encontrado.");
                vista.lblResultado.setText("Stock disponible: -- unidades");
            } else {
                vista.lblResultado.setText(
                        "Stock disponible: " + producto.getStock()
                        + " unidades");

                mostrarMensaje("Producto: " + producto.getNombre()
                        + "\nStock: " + producto.getStock());
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("Ingrese un codigo numerico valido.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
                vista,
                mensaje,
                "Mensaje",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}

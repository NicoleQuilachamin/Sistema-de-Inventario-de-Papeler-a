package Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaInventario extends JFrame {

    public JTextField txtCodigo;
    public JTextField txtNombre;
    public JTextField txtPrecio;
    public JTextField txtStock;

    public JButton btnRegistrar;
    public JButton btnConsultar;
    public JButton btnVerificar;

    public JTable tablaProductos;
    public DefaultTableModel modeloTabla;

    public JLabel lblResultado;

    public VentanaInventario() {

        setTitle("Gestion de Inventario de Papeleria");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Color rosa = new Color(255, 225, 235);
        getContentPane().setBackground(rosa);

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelDatos.setBackground(rosa);

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        panelDatos.add(new JLabel("Codigo del producto:"));
        panelDatos.add(txtCodigo);

        panelDatos.add(new JLabel("Nombre:"));
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("Precio ($):"));
        panelDatos.add(txtPrecio);

        panelDatos.add(new JLabel("Stock:"));
        panelDatos.add(txtStock);

        btnRegistrar = new JButton("Registrar producto");
        btnConsultar = new JButton("Consultar productos");
        btnVerificar = new JButton("Verificar stock");

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(rosa);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnVerificar);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Codigo", "Nombre", "Precio", "Stock"}, 0
        ) {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);

        lblResultado = new JLabel("Stock disponible: -- unidades");

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(rosa);
        panelInferior.add(lblResultado);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(rosa);

        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
}

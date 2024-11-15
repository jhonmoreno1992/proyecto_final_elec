/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.producto;
import vista.frmProducto;

/**
 *
 * @author usuario
 */
public class productoController implements ActionListener {

    public final producto modeloProducto;
    public final frmProducto vistaProducto;
    Object[][] datosProducto;
    int fila = -1;

    public productoController(producto modeloProducto, frmProducto vistaProducto) {
        this.modeloProducto = modeloProducto;
        this.vistaProducto = vistaProducto;
        this.vistaProducto.btnguardar.addActionListener(this);
        this.vistaProducto.btnlimpiar.addActionListener(this);
        this.vistaProducto.btnactualizar.addActionListener(this);

    }

    public byte[] convertirImagenABytes(String ruta) {
        try {
            BufferedImage bImage = ImageIO.read(new File(ruta));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            // Obtener la extensión del archivo (jpg o png)
            String extension = ruta.substring(ruta.lastIndexOf(".") + 1).toLowerCase();

            // Escribir la imagen en el formato correspondiente (jpg o png)
            if ("png".equals(extension) || "jpg".equals(extension)) {
                ImageIO.write(bImage, extension, bos);
            } else {
                System.out.println("Formato de imagen no soportado");
                return null;
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTable() {
        Object[] columna = {"ID", "NOMBREPRODUCTO", "DESCRIPCION", "PRECIO", "OBSERVACIONES", "STOCK", "IMAGEN", "CATEGORIA_ID_CATEGORIA"};
        datosProducto = modeloProducto.getProducto();

        DefaultTableModel datos = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                // Definir la columna de imagen para mostrar ImageIcon
                return column == 6 ? ImageIcon.class : Object.class;
            }
        };
        datos.setColumnIdentifiers(columna);

        for (Object[] fila : datosProducto) {
            if (fila[6] instanceof byte[]) { // Si la imagen es byte[]
                byte[] imagenBytes = (byte[]) fila[6];
                ImageIcon imagenIcon = new ImageIcon(imagenBytes);
                // Escalar la imagen a un tamaño adecuado
                Image img = imagenIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                fila[6] = new ImageIcon(img); // Asignar ImageIcon escalado
            } else {
                fila[6] = null; // Si no hay imagen, muestra celda vacía o ícono por defecto
            }
            datos.addRow(fila);
        }

        vistaProducto.tbproducto.setModel(datos);
        vistaProducto.tbproducto.setRowHeight(100); // Ajustar la altura de la fila para la imagen

        // Llamar al método para centrar el contenido de la tabla, excluyendo la columna de imagen
        centrarContenidoTabla(vistaProducto.tbproducto, 6);
    }

// Método para centrar el contenido de todas las celdas en la tabla, excepto la columna de imagen
    public void centrarContenidoTabla(JTable tabla, int columnaImagen) {
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = tabla.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i != columnaImagen) { // Saltar la columna de imagen
                columnModel.getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    public void limpiar() {
        vistaProducto.txtproducto.setText(null);
        vistaProducto.txtdescripcion.setText(null);
        vistaProducto.txtprecio.setText(null);
        vistaProducto.txtobservaciones.setText(null);
        vistaProducto.txtstock.setText(null);
        vistaProducto.lblimagen.setIcon(null);
        vistaProducto.lblimagen.setText(null);
        vistaProducto.txtcategoria.setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaProducto.btnguardar) {
            long precio, stock;
            String nombreproducto = vistaProducto.txtproducto.getText();
            String descripcion = vistaProducto.txtdescripcion.getText();
            String observaciones = vistaProducto.txtobservaciones.getText();
            String rutaImagen = vistaProducto.lblimagen.getText(); // Ruta de la imagen
            String categoria_id_categoria = vistaProducto.txtcategoria.getText();

            if (vistaProducto.txtprecio.getText().isEmpty() || nombreproducto.isEmpty()
                    || descripcion.isEmpty() || observaciones.isEmpty() || rutaImagen.isEmpty()
                    || categoria_id_categoria.isEmpty() || vistaProducto.txtstock.getText().isEmpty()) {

                JOptionPane.showMessageDialog(vistaProducto, "Faltan datos");
            } else {
                precio = Long.parseLong(vistaProducto.txtprecio.getText());
                stock = Long.parseLong(vistaProducto.txtstock.getText());
                byte[] imagen = convertirImagenABytes(rutaImagen); // Convertir imagen a byte[]

                modeloProducto.AgregarProducto(nombreproducto, descripcion, precio, observaciones, stock, imagen, categoria_id_categoria);
                JOptionPane.showMessageDialog(vistaProducto, "Registro Guardado");
                this.updateTable(); // Actualizar tabla para mostrar el nuevo registro
                this.limpiar();
            }
        }
        if (e.getSource() == vistaProducto.btnactualizar) {
            fila = vistaProducto.tbproducto.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione una fila para Actualizar");
                return;
            }

            // Obtener datos del formulario
            int id = Integer.parseInt(vistaProducto.tbproducto.getValueAt(fila, 0).toString());
            String nombreproducto = vistaProducto.txtproducto.getText();
            String descripcion = vistaProducto.txtdescripcion.getText();
            long precio = Long.parseLong(vistaProducto.txtprecio.getText());
            String observaciones = vistaProducto.txtobservaciones.getText();
            long stock = Long.parseLong(vistaProducto.txtstock.getText());
            String categoria_id_categoria = vistaProducto.txtcategoria.getText();

            // Convertir imagen a byte[]
            byte[] imagen = null;
            if (vistaProducto.lblimagen.getIcon() != null) {
                ImageIcon icon = (ImageIcon) vistaProducto.lblimagen.getIcon();
                BufferedImage bufferedImage = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB // Imagen con fondo blanco
                );

                // Crear gráficos y pintar un fondo blanco antes de dibujar el icono
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.setColor(Color.WHITE); // Establecer color de fondo blanco
                g2d.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight()); // Rellenar con fondo blanco
                icon.paintIcon(null, g2d, 0, 0); // Pintar el icono encima del fondo blanco
                g2d.dispose();

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "jpg", baos); // Guardar como JPG para evitar transparencia
                    imagen = baos.toByteArray();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            // Actualizar el producto en la base de datos con la imagen en formato byte[]
            modeloProducto.ActualizarProducto(id, nombreproducto, descripcion, precio, observaciones, stock, imagen, categoria_id_categoria);
            JOptionPane.showMessageDialog(vistaProducto, "Se ha modificado su registro");
            this.updateTable();
            this.limpiar();
        }

        if (e.getSource() == vistaProducto.btnlimpiar) {
            this.limpiar();
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

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
import javax.swing.table.DefaultTableModel;
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
            // Cambia "jpg" por el formato específico de la imagen
            ImageIO.write(bImage, "jpg", bos);
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
    }
    
     public void limpiar() {
        vistaProducto.txtproducto.setText(null);
        vistaProducto.txtdescripcion.setText(null);
        vistaProducto.txtprecio.setText(null);
        vistaProducto.txtobservaciones.setText(null);
        vistaProducto.txtstock.setText(null);
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
            }
        }
        if (e.getSource() == vistaProducto.btnactualizar) {
            fila = vistaProducto.tbproducto.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione una fila para Actualizar");
                return;
            }
            int id = Integer.parseInt(vistaProducto.tbproducto.getValueAt(fila, 0).toString());
            String nombreproducto = vistaProducto.txtproducto.getText();
            String descripcion = vistaProducto.txtdescripcion.getText();
            long precio = Long.parseLong(vistaProducto.txtprecio.getText());
            String observaciones = vistaProducto.txtobservaciones.getText();
            long stock = Long.parseLong(vistaProducto.txtstock.getText());
            String imagen = vistaProducto.lblimagen.getText();
            String categoria_id_categoria = vistaProducto.txtproducto.getText();
            
            
            modeloProducto.ActualizarProducto(id, nombreproducto, descripcion, precio, observaciones, stock, imagen, categoria_id_categoria);
            JOptionPane.showMessageDialog(vistaProducto, "Se ha modificado su registro");
            this.updateTable();
            this.limpiar();
        }
        
        if(e.getSource() == vistaProducto.btnlimpiar){
            this.limpiar();
        }
        
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.registro;
import vista.frmRegistro;

/**
 *
 * @author usuario
 */
public class registroController implements ActionListener {

    public final registro modeloRegistro;
    public final frmRegistro vistaRegistro;
    Object[][] datosRegistro;
    int fila = -1;

    public registroController(registro modeloRegistro, frmRegistro vistaRegistro) {
        this.modeloRegistro = modeloRegistro;
        this.vistaRegistro = vistaRegistro;
        this.vistaRegistro.btnguardar.addActionListener(this);
        this.vistaRegistro.btnlimpiar.addActionListener(this);
        this.vistaRegistro.btnactualizar.addActionListener(this);

    }

    public void updateTable() {
        Object[] columna = {"ID", "NOMBRE", "APELLIDO", "CEDULA", "DIRECCION", "TELEFONO", "CORREO", "USUARIO", "PASSWORD", "CONFIRMPASSWORD"};
        datosRegistro = modeloRegistro.getRegistro();
        DefaultTableModel datos = new DefaultTableModel(datosRegistro, columna);
        vistaRegistro.tbregistro.setModel(datos);

    }

    public void limpiar() {
        vistaRegistro.txtnombre.setText(null);
        vistaRegistro.txtapellido.setText(null);
        vistaRegistro.txtcedula.setText(null);
        vistaRegistro.txtdireccion.setText(null);
        vistaRegistro.txttelefono.setText(null);
        vistaRegistro.txtcorreo.setText(null);
        vistaRegistro.txtusuario.setText(null);
        vistaRegistro.txtpassword.setText(null);
        vistaRegistro.txtconfirmpassword.setText(null);
    }

    public void actionPerformed(ActionEvent e) {
        // Métodos guardar, modificar y limpiar
        if (e.getSource() == vistaRegistro.btnguardar) {
            long cedula = 0, telefono = 0;
            String nombre = vistaRegistro.txtnombre.getText();
            String apellido = vistaRegistro.txtapellido.getText();
            String direccion = vistaRegistro.txtdireccion.getText();
            String correo = vistaRegistro.txtcorreo.getText();
            String usuario = vistaRegistro.txtusuario.getText();
            String password = vistaRegistro.txtpassword.getText();
            String confirmpassword = vistaRegistro.txtconfirmpassword.getText();

            // Verificar si hay campos vacíos
            if (vistaRegistro.txtcedula.getText().isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                    || direccion.isEmpty() || correo.isEmpty() || usuario.isEmpty()
                    || password.isEmpty() || confirmpassword.isEmpty()
                    || vistaRegistro.txttelefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan datos");
                return; // Salir del método si hay campos vacíos
            }

            // Verificar que las contraseñas coincidan
            if (!password.equals(confirmpassword)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                return; // Salir del método si las contraseñas no coinciden
            }

            // Convertir los campos numéricos y guardar el registro
            cedula = Long.parseLong(vistaRegistro.txtcedula.getText());
            telefono = Long.parseLong(vistaRegistro.txttelefono.getText());
            modeloRegistro.AgregarRegistro(nombre, apellido, cedula, direccion, telefono, correo, usuario, password, confirmpassword);
            JOptionPane.showMessageDialog(null, "Registro guardado satisfactoriamente");

            // Actualizar la tabla y limpiar los campos del formulario
            this.updateTable();
            this.limpiar();
        }

        if (e.getSource() == vistaRegistro.btnactualizar) {
            // Obtener fila seleccionada
            fila = vistaRegistro.tbregistro.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione una fila para actualizar");
                return;
            }

            int id = Integer.parseInt(vistaRegistro.tbregistro.getValueAt(fila, 0).toString());
            String nombre = vistaRegistro.txtnombre.getText();
            String apellido = vistaRegistro.txtapellido.getText();
            long cedula = Long.parseLong(vistaRegistro.txtcedula.getText());
            String direccion = vistaRegistro.txtdireccion.getText();
            long telefono = Long.parseLong(vistaRegistro.txttelefono.getText());
            String correo = vistaRegistro.txtcorreo.getText();
            String usuario = vistaRegistro.txtusuario.getText();
            String password = vistaRegistro.txtpassword.getText();
            String confirmpassword = vistaRegistro.txtconfirmpassword.getText();

            // Verificar que las contraseñas coincidan al actualizar
            if (!password.equals(confirmpassword)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                return; // Salir del método si las contraseñas no coinciden
            }

            // Actualizar el registro si las contraseñas coinciden
            boolean actualizado = modeloRegistro.ActualizarRegistro(id, nombre, apellido, cedula, direccion, telefono, correo, usuario, password, confirmpassword);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Se ha modificado su registro");
                this.updateTable();
                this.limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el registro.");
            }
        }
    
    }
    
}

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

    }

    public void updateTable() {
        Object[] columna = {"ID", "NOMBRE", "APELLIDO", "CEDULA", "DIRECCION", "TELEFONO", "CORREO", "PASSWORD", "CONFIRMPASSWORD"};
        datosRegistro = modeloRegistro.getRegistro();
        DefaultTableModel datos = new DefaultTableModel(datosRegistro, columna);
        vistaRegistro.tbregistro.setModel(datos);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //metodos guardar, modificar y limpiar
        if (e.getSource() == vistaRegistro.btnguardar) {
            long cedula, telefono = 0;
            String nombre = vistaRegistro.txtnombre.getText();
            String apellido = vistaRegistro.txtapellido.getText();
            String direccion = vistaRegistro.txtdireccion.getText();
            String correo = vistaRegistro.txtcorreo.getText();
            String password = vistaRegistro.txtpassword.getText();
            String confirmpassword = vistaRegistro.txtconfirmpassword.getText();
            
            if (vistaRegistro.txtcedula.getText().isEmpty()
                    || nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() 
                    || correo.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()
                    || vistaRegistro.txttelefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Falta datos");
            } else {
                cedula = Long.parseLong(vistaRegistro.txtcedula.getText());
                telefono = Long.parseLong(vistaRegistro.txttelefono.getText());
                modeloRegistro.AgregarRegistro(nombre, apellido, cedula, direccion, telefono, correo, password, confirmpassword);
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                this.updateTable();
            }

        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.usuario;
import vista.frmLogin;
import vista.frmProducto;


public class usuarioController {
    
    public final usuario modeloUsuario;
    public final frmLogin vistaLogin;
    
    public usuarioController(usuario modeloUsuario, frmLogin vistaLogin){
        this.modeloUsuario = modeloUsuario;
        this.vistaLogin = vistaLogin;
    }
    
    public void iniciarSesion(){
        String username = vistaLogin.txtusuario.getText();
        String password = vistaLogin.txtpassword.getText();
        if(modeloUsuario.validarUsuario(username, password)){
            JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso");
            frmProducto producto = new frmProducto();
            producto.setVisible(true);
            producto.setLocationRelativeTo(null);
            this.vistaLogin.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(null, "Los Datos estan Incorrectos");
        }
    }
    
}
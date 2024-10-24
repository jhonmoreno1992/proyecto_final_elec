/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.usuario;
import vista.frmRegistro;
import vista.frmLogin;

/**
 *
 * @author usuario
 */
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
        String typeuser = vistaLogin.cbmtipousuario.getActionCommand();
                
        if(modeloUsuario.validarUsuario(username, password)){
            JOptionPane.showMessageDialog(null, "Inicio de sesion exitoso");
            frmRegistro registro = new frmRegistro();
            registro.setVisible(true);
            registro.setLocationRelativeTo(null);
            this.vistaLogin.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(null, "Los Datos estan Incorrectos");
        }
    }
    
}

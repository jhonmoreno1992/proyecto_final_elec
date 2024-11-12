/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;

public class registro {

    conexion con;

    public registro() {
        con = new conexion();
    }

    public Object[][] getRegistro() {
        int registros = 0;
        try {
            String sql = "SELECT count(1) AS total FROM registro";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();

        } catch (Exception e) {
            System.err.println(e);
        }

        Object[][] datos = new Object[registros][10];
        try {
            String sql = "SELECT * FROM registro ORDER BY idregistro";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                int idregistro = res.getInt("idregistro");
                String nombre = res.getString("nombre");
                String apellido = res.getString("apellido");
                long cedula = res.getLong("cedula");
                String direccion = res.getString("direccion");
                long telefono = res.getLong("telefono");
                String correo = res.getString("correo");
                String usuario = res.getString("usuario");
                String password = res.getString("password");
                String confirmpassword = res.getString("confirmpassword");
                datos[i][0] = idregistro;
                datos[i][1] = nombre;
                datos[i][2] = apellido;
                datos[i][3] = cedula;
                datos[i][4] = direccion;
                datos[i][5] = telefono;
                datos[i][6] = correo;
                datos[i][7] = usuario;
                datos[i][8] = password;
                datos[i][9] = confirmpassword;
                i++;
            }
            res.close();

        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }

    public void AgregarRegistro(String nombre, String apellido, long cedula, String direccion, long telefono,
            String correo, String usuario, String password, String confirmpassword) {
        String sql = "INSERT INTO registro(nombre, apellido, cedula, direccion, telefono, correo, usuario, password, confirmpassword) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setLong(3, cedula);
            ps.setString(4, direccion);
            ps.setLong(5, telefono);
            ps.setString(6, correo);
            ps.setString(7, usuario);
            ps.setString(8, password);
            ps.setString(9, confirmpassword);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean ActualizarRegistro(int id, String nombre, String apellido, long cedula, String direccion,
            long telefono, String correo, String usuario, String password, String confirmpassword) {
        String sql = "UPDATE registro SET nombre=?, apellido=?, cedula=?, direccion=?, telefono=?, correo=?, usuario=?, password=?, confirmpassword=? WHERE idregistro=?";
        try {
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setLong(3, cedula);
            ps.setString(4, direccion);
            ps.setLong(5, telefono);
            ps.setString(6, correo);
            ps.setString(7, usuario);
            ps.setString(8, password);
            ps.setString(9, confirmpassword);
            ps.setInt(10, id);

            int filasActualizadas = ps.executeUpdate(); // Cambia ps.execute() a ps.executeUpdate()
            ps.close();

            return filasActualizadas > 0; // Retorna true si se actualizó al menos una fila

        } catch (Exception e) {
            System.err.println("Error al actualizar el registro: " + e.getMessage());
            return false; // Retorna false en caso de error
        }
    }
    
    
}

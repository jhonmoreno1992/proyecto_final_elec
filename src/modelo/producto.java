/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.*;

public class producto {

    conexion con;

    public producto() {
        con = new conexion();
    }

    public Object[][] getProducto() {
        int registros = 0;
        try {
            String sql = "SELECT count(1) AS total FROM producto";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();

        } catch (Exception e) {
            System.err.println(e);
        }

        Object[][] datos = new Object[registros][8];
        try {
            String sql = "SELECT * FROM producto ORDER BY idproducto";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            int i = 0;
            while (res.next()) {
                int idproducto = res.getInt("idproducto");
                String nombreproducto = res.getString("nombreproducto");
                String descripcion = res.getString("descripcion");
                long precio = res.getLong("precio");
                String observaciones = res.getString("observaciones");
                long stock = res.getLong("stock");
                byte[] imagen = res.getBytes("imagen");
                String categoria_id_categoria = res.getString("categoria_id_categoria");
                datos[i][0] = idproducto;
                datos[i][1] = nombreproducto;
                datos[i][2] = descripcion;
                datos[i][3] = precio;
                datos[i][4] = observaciones;
                datos[i][5] = stock;
                datos[i][6] = imagen;
                datos[i][7] = categoria_id_categoria;
                i++;
            }
            res.close();

        } catch (Exception e) {
            System.err.println(e);
        }
        return datos;
    }
    

    public void AgregarProducto(String nombreproducto, String descripcion, long precio, String observaciones, long stock, byte[] imagen, String categoria_id_categoria) {
        String sql = "INSERT INTO producto(nombreproducto, descripcion, precio, observaciones, stock, imagen, categoria_id_categoria) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, nombreproducto);
            ps.setString(2, descripcion);
            ps.setLong(3, precio);
            ps.setString(4, observaciones);
            ps.setLong(5, stock);
            ps.setBytes(6, imagen);
            ps.setString(7, categoria_id_categoria);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void ActualizarProducto(int id, String nombreproducto, String descripcion, long precio, String observaciones, long stock, byte[] imagen, String categoria_id_categoria){
        String sql = "UPDATE producto SET nombreproducto=?, descripcion=?, precio=?, observaciones=?, stock=?, imagen=?, categoria_id_categoria=? WHERE idproducto=?";
        try{
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, nombreproducto);
            ps.setString(2, descripcion);
            ps.setLong(3, precio);
            ps.setString(4, observaciones);
            ps.setLong(5, stock);
            ps.setBytes(6, imagen);
            ps.setString(7, categoria_id_categoria);
            ps.setInt(8, id);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.err.println(e);    
        
        }
    }
}

package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class consultas {
    public void guardarUsuario(String contrasenia, String email, boolean es_admin, String nombre_alumno) {
    ConexionDB db = new ConexionDB();
    String sql = "INSERT INTO usuario(contrasenia, email, es_admin, nombre_alumno) VALUES (?, ?, ?, ?)";
    Connection conexion = db.conectar();
    PreparedStatement ps = null;

    try {
        ps = conexion.prepareStatement(sql);
        ps.setString(1, contrasenia);
        ps.setString(2, email);
        ps.setBoolean(3, es_admin);
        ps.setString(4, nombre_alumno);

        int rowCount = ps.executeUpdate();

        if (rowCount > 0) {
            JOptionPane.showMessageDialog(null, "Guardado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el usuario");
        }
    } catch (SQLException e) {
        System.err.println("Error al guardar el usuario: " + e.getMessage());
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}


    public int accesoUsuario(String email, String pass) {
        ConexionDB db = new ConexionDB();
        int idUsuario = -1; // Valor predeterminado para indicar que no se encontró el usuario

        try {
            Connection cn = db.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT id, email, contrasenia FROM usuario WHERE email = ?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String emailCorrecto = rs.getString("email");
                String passCorrecto = rs.getString("contrasenia");

                if (email.equals(emailCorrecto) && pass.equals(passCorrecto)) {
                    idUsuario = rs.getInt("id");
                }
            }

            rs.close();
            pst.close();
            cn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder: " + e.getMessage());
        }

        return idUsuario;
    }

}


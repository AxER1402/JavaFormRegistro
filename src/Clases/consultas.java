package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class consultas {
    public void guardarUsuario(String usuario, String password) {
        ConexionDB db = new ConexionDB();
        String sql = "INSERT INTO usuarios(nombre, clave) VALUES (?, ?)";
        Connection conexion = db.conectar();
        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);

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

    public int accesoUsuario(String user, String pass) {
        ConexionDB db = new ConexionDB();
        int idUsuario = -1; // Valor predeterminado para indicar que no se encontró el usuario

        try {
            Connection cn = db.conectar();
            PreparedStatement pst = cn.prepareStatement("SELECT idUsuario, nombre, clave FROM usuarios WHERE nombre = ?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String usuarioCorrecto = rs.getString("nombre");
                String passCorrecto = rs.getString("clave");

                if (user.equals(usuarioCorrecto) && pass.equals(passCorrecto)) {
                    idUsuario = rs.getInt("idUsuario");
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


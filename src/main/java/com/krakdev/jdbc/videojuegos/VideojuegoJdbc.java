package com.krakdev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.krakdev.jdbc.Conexion;
import com.krakdev.videojuegos.entidades.Videojuego;

@Repository
public class VideojuegoJdbc {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoJdbc.class);

    public Videojuego insertar(Videojuego v) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.conectar();
            String sql = "INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getCodigo());
            ps.setString(2, v.getNombre());
            ps.setString(3, v.getPlataforma());
            ps.setDouble(4, v.getPrecio());
            ps.setBoolean(5, v.isDisponible());
            ps.setString(6, v.getGenero());
            ps.executeUpdate();
            logger.info("Videojuego insertado: {}", v.getCodigo());
            return v;
        } catch (SQLException e) {
            logger.error("Error al insertar videojuego", e);
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    public List<Videojuego> listar() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Videojuego> lista = new ArrayList<>();
        try {
            con = Conexion.conectar();
            String sql = "SELECT codigo, nombre, plataforma, precio, disponible, genero FROM videojuegos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Videojuego v = new Videojuego(
                    rs.getString("codigo"), rs.getString("nombre"), rs.getString("plataforma"),
                    rs.getDouble("precio"), rs.getBoolean("disponible"), rs.getString("genero")
                );
                lista.add(v);
            }
            logger.info("Videojuegos listados correctamente. Total: {}", lista.size());
        } catch (SQLException e) {
            logger.error("Error al listar videojuegos", e);
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return lista;
    }

    public Videojuego buscarPorCodigo(String codigo) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Videojuego v = null;
        try {
            con = Conexion.conectar();
            String sql = "SELECT codigo, nombre, plataforma, precio, disponible, genero FROM videojuegos WHERE codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                v = new Videojuego(
                    rs.getString("codigo"), rs.getString("nombre"), rs.getString("plataforma"),
                    rs.getDouble("precio"), rs.getBoolean("disponible"), rs.getString("genero")
                );
                logger.info("Videojuego encontrado: {}", codigo);
            }
        } catch (SQLException e) {
            logger.error("Error al buscar videojuego por código: {}", codigo, e);
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return v;
    }

    public Videojuego actualizar(Videojuego v) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.conectar();
            String sql = "UPDATE videojuegos SET nombre = ?, plataforma = ?, precio = ?, disponible = ?, genero = ? WHERE codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getPlataforma());
            ps.setDouble(3, v.getPrecio());
            ps.setBoolean(4, v.isDisponible());
            ps.setString(5, v.getGenero());
            ps.setString(6, v.getCodigo());
            ps.executeUpdate();
            logger.info("Videojuego actualizado: {}", v.getCodigo());
            return v;
        } catch (SQLException e) {
            logger.error("Error al actualizar", e);
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    public boolean eliminar(String codigo) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.conectar();
            String sql = "DELETE FROM videojuegos WHERE codigo = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            int filasAfectadas = ps.executeUpdate();
            logger.info("Videojuego eliminado: {}", codigo);
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar", e);
            throw e;
        } finally {
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }
}
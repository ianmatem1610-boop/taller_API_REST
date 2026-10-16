package com.krakdev.jdbc.videojuegos.services;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krakdev.jdbc.videojuegos.VideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@Service
public class ServicioVideojuegoJdbc {
    @Autowired
    private VideojuegoJdbc videojuegoJdbc;

    public Videojuego crear(Videojuego v) throws SQLException { return videojuegoJdbc.insertar(v); }
    public List<Videojuego> listar() throws SQLException { return videojuegoJdbc.listar(); }
    public Videojuego buscarPorCodigo(String codigo) throws SQLException { return videojuegoJdbc.buscarPorCodigo(codigo); }
    public Videojuego actualizar(Videojuego v) throws SQLException { return videojuegoJdbc.actualizar(v); }
    public boolean eliminar(String codigo) throws SQLException { return videojuegoJdbc.eliminar(codigo); }
}
package com.krakdev.jdbc.videojuegos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.jdbc.videojuegos.services.ServicioVideojuegoJdbc;
import com.krakdev.videojuegos.entidades.Videojuego;

@RestController
@RequestMapping("/jdbc/videojuegos")
public class VideojuegoJdbcController {

    @Autowired
    private ServicioVideojuegoJdbc servicio;

    @PostMapping
    public ResponseEntity<Object> insertar(@RequestBody Videojuego v) {
        try {
            Videojuego creado = servicio.crear(v);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error al insertar en la BD: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> listar() {
        try {
            List<Videojuego> lista = servicio.listar();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error al listar de la BD: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Object> buscarPorCodigo(@PathVariable String codigo) {
        try {
            Videojuego v = servicio.buscarPorCodigo(codigo);
            if (v != null) {
                return new ResponseEntity<>(v, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Videojuego no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error al buscar en la BD: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Object> actualizar(@PathVariable String codigo, @RequestBody Videojuego v) {
        try {
            v.setCodigo(codigo);
            Videojuego actualizado = servicio.actualizar(v);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error al actualizar en la BD: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Object> eliminar(@PathVariable String codigo) {
        try {
            boolean eliminado = servicio.eliminar(codigo);
            if (eliminado) {
                return new ResponseEntity<>("Videojuego eliminado con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Videojuego no encontrado para eliminar", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>("Error al eliminar de la BD: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
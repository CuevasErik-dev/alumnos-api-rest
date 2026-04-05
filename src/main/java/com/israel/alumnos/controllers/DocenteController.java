package com.israel.alumnos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import com.israel.alumnos.model.Docente;
import com.israel.alumnos.services.DocentesServices;

@RestController
@RequestMapping("/docente")
@CrossOrigin(origins = "*") // permite la entrada de cualquier direccion
public class DocenteController {

    @Autowired
    private DocentesServices docentesServices;

    // metodo get para traer todos los docentes de la base de Datos
    @GetMapping("/traer-docentes")
    public List<Docente> TraerDocentes() {
        return docentesServices.obtenerTodos();
    }

    // Metodo para traer un docente por numero de cont
    @GetMapping("/traer-docente/{id}")
    public ResponseEntity<Docente> TraerUnDocente(@PathVariable Long id) {
        return docentesServices.obtenerPorId(id)
                .map(docente -> ResponseEntity.ok(docente))
                .orElse(ResponseEntity.notFound().build());
    }

    // Metodo para Insertar un docente a la base de datos
    @PostMapping("/insertar-docente")
    public Docente insertarDocente(@RequestBody Docente docente) {
        return docentesServices.guardarDocente(docente);
    }

    // metodo para editar un docente
    @PutMapping("/editar-docentes/{id}")
    public ResponseEntity<Docente> actualizarDocente(@PathVariable long id, @RequestBody Docente docente) {
        Optional<Docente> actualizado = docentesServices.actualizarDocente(id, docente);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // metodo para eliminar un docente
    @DeleteMapping("/eliminar-docente/{id}")
    public ResponseEntity<Void> eliminarDocente(@PathVariable long id) {
        docentesServices.eliminarDocente(id);
        return ResponseEntity.ok().build();
    }
}
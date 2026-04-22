package com.israel.alumnos.controllers;

import java.util.List;
import java.util.Optional;

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

import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.services.AlumnoServices;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "http://localhost:5173") 
public class AlumnoController {

    @Autowired
    private AlumnoServices alumnoServices;

    // metodo get para traer todos los alumnos de la base de Datos
    @GetMapping("/traer-alumnos")
    public List<Alumno> TraerAlumnos() {
        return alumnoServices.obtenerTodos();
    }

    // Metodo para traer un alumno por numero de control
    @GetMapping("/traer-alumno/{id}")
    public ResponseEntity<Alumno> TraerUnAlumno(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoServices.obtenerPorId(id);
        return alumno.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Metodo para Insertar un alumno a la base de datos
    @PostMapping("/insertar-alumno")
    public Alumno insertarAlumno(@RequestBody Alumno alumno) {
        return alumnoServices.guardarAlumno(alumno);
    }

    // metodo para editar un alumno
    @PutMapping("/editar-alumnos/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable long id, @RequestBody Alumno alumno) {
        Optional<Alumno> actualizado = alumnoServices.actualizarAlumno(id, alumno);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // metodo para eliminar un alumno
    @DeleteMapping("/eliminar-alumnos/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable long id) {
        alumnoServices.eliminarAlumno(id);
        return ResponseEntity.ok().build();
    }
}

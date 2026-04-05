package com.israel.alumnos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.repository.AlumnoRepository;

@Service
public class AlumnoServices {
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    public Alumno guardarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> actualizarAlumno(Long id, Alumno alumnoDetalles) {
        return alumnoRepository.findById(id).map(alumnoExistente -> {
            alumnoExistente.setApellido(alumnoDetalles.getApellido());
            alumnoExistente.setGmail(alumnoDetalles.getGmail());
            alumnoExistente.setNumeroControl(alumnoDetalles.getNumeroControl());
            alumnoExistente.setTelefono(alumnoDetalles.getTelefono());
            alumnoExistente.setCarrera(alumnoDetalles.getCarrera());
            alumnoExistente.setImagenurl(alumnoDetalles.getImagenurl());
            return alumnoRepository.save(alumnoExistente);
        });
    }

    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
}

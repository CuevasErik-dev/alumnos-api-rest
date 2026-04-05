package com.israel.alumnos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.israel.alumnos.model.Docente;
import com.israel.alumnos.repository.DocenteRepository;

@Service
public class DocentesServices {
    @Autowired
    private DocenteRepository docenteRepository;


    public List<Docente> obtenerTodos() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> obtenerPorId(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente guardarDocente(Docente docente) {
        return docenteRepository.save(docente);
    }

    public Optional<Docente> actualizarDocente(Long id, Docente docenteDetalles) {
        return docenteRepository.findById(id).map(docenteExistente -> {
            docenteExistente.setNombre(docenteDetalles.getNombre());
            docenteExistente.setApellido(docenteDetalles.getApellido());
            docenteExistente.setCorreo(docenteDetalles.getCorreo());
            docenteExistente.setCarrera(docenteDetalles.getCarrera());
            return docenteRepository.save(docenteExistente);
        });
    }

    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }
}

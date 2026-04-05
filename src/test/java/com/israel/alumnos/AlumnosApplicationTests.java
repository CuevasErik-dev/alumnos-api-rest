package com.israel.alumnos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.israel.alumnos.controllers.AlumnoController;
import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.services.AlumnoServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(AlumnoController.class)
class AlumnosApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    // private AlumnoRepository alumnoRepository;
    private AlumnoServices alumnoServices;

    @Test
    public void debeInsertarUnAlumno() throws Exception {

        Alumno alumno = new Alumno();
        alumno.setNombre("Israel");
        alumno.setNumeroControl("22620202");

        when(alumnoServices.guardarAlumno(any(Alumno.class)))
                .thenReturn(alumno);

        mockMvc.perform(post("/alumnos/insertar-alumno")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alumno)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Israel"));
    }

    @Test
    public void debeTraerTodosLosAlumnos() throws Exception {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        alumno1.setNombre("Israel");
        alumno1.setCarrera("Sistemas");

        Alumno alumno2 = new Alumno();
        alumno2.setId(2L);
        alumno2.setNombre("Erik");
        alumno2.setCarrera("Sistemas");

        when(alumnoServices.obtenerTodos()).thenReturn(java.util.Arrays.asList(alumno1, alumno2));

        mockMvc.perform(get("/alumnos/traer-alumnos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Israel")));

    }

    @Test
    public void debeEditarUnAlumno() throws Exception{
    Long idParaEditar = 1L;

    Alumno alumnoModificado = new Alumno();
    alumnoModificado.setId(idParaEditar);
    alumnoModificado.setNombre("Israel Cuevas");
    alumnoModificado.setCarrera("Ingeniería");

    when(alumnoServices.actualizarAlumno(eq(idParaEditar), any(Alumno.class)))
            .thenReturn(Optional.of(alumnoModificado));

    mockMvc.perform(put("/alumnos/editar-alumnos/{id}", idParaEditar)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(alumnoModificado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Israel Cuevas"))
            .andExpect(jsonPath("$.carrera").value("Ingeniería"));

    verify(alumnoServices, times(1))
            .actualizarAlumno(eq(idParaEditar), any(Alumno.class));
}

    @Test
    public void debeEliminarUnAlumno() throws Exception {
        Long idParaEliminar = 1L;

        mockMvc.perform(delete("/alumnos/eliminar-alumnos/{id}", idParaEliminar)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(alumnoServices, times(1)).eliminarAlumno(idParaEliminar);
    }
}

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
import com.israel.alumnos.controllers.DocenteController;
import com.israel.alumnos.model.Alumno;
import com.israel.alumnos.model.Docente;
import com.israel.alumnos.services.DocentesServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(DocenteController.class)
class DocenteControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private DocentesServices docenteServices;

        @Test
        public void debeInsertarUnDocente() throws Exception {

                Docente docente = new Docente();
                docente.setNombre("Israel");
                docente.setApellido("Cuevas");

                when(docenteServices.guardarDocente(any(Docente.class)))
                                .thenReturn(docente);

                mockMvc.perform(post("/docente/insertar-docente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(docente)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Israel"));
        }

        @Test
        public void debeTraerTodosLosADocente() throws Exception {
                Docente docente1 = new Docente();
                docente1.setId(1L);
                docente1.setNombre("Israel");
                docente1.setCarrera("Sistemas");

                Docente docente2 = new Docente();
                docente2.setId(2L);
                docente2.setNombre("Erik");
                docente2.setCarrera("Sistemas");

                when(docenteServices.obtenerTodos()).thenReturn(java.util.Arrays.asList(docente1, docente2));

                mockMvc.perform(get("/docente/traer-docentes")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].nombre", is("Israel")));

        }

        @Test
        public void debeEditarUnDocente() throws Exception {
                Long idParaEditar = 1L;

                Docente docenteModificado = new Docente();
                docenteModificado.setId(idParaEditar);
                docenteModificado.setNombre("Israel Cuevas");
                docenteModificado.setCarrera("Ingeniería");

                when(docenteServices.actualizarDocente(eq(idParaEditar), any(Docente.class)))
                                .thenReturn(Optional.of(docenteModificado));

                mockMvc.perform(put("/docente/editar-docentes/{id}", idParaEditar)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(docenteModificado)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nombre").value("Israel Cuevas"))
                                .andExpect(jsonPath("$.carrera").value("Ingeniería"));

                verify(docenteServices, times(1))
                                .actualizarDocente(eq(idParaEditar), any(Docente.class));
        }

        @Test
    public void debeEliminarUnDocente() throws Exception {
        Long idParaEliminar = 1L;

        mockMvc.perform(delete("/docente/eliminar-docente/{id}", idParaEliminar)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(docenteServices, times(1)).eliminarDocente(idParaEliminar);
    }

}
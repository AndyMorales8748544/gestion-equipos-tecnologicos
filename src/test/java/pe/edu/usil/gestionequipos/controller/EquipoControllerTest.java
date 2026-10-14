package pe.edu.usil.gestionequipos.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;
import pe.edu.usil.gestionequipos.service.EquipoService;

import org.springframework.validation.BindingResult;
import pe.edu.usil.gestionequipos.dto.EquipoDTO;

import org.springframework.dao.DataIntegrityViolationException;

class EquipoControllerTest {

    private EquipoService equipoService;
    private EquipoController equipoController;

    @BeforeEach
    void configurar() {
        equipoService = mock(EquipoService.class);
        equipoController = new EquipoController(equipoService);
    }

    @Test
    void mantenimientoEquipos_debeMostrarListaDeEquipos() {

        // Preparar los datos simulados
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Laptop Lenovo");

        List<EquipoEntity> equipos = List.of(equipo);

        when(equipoService.listarEquipos()).thenReturn(equipos);

        // Ejecutar el método del controlador
        ModelAndView resultado = equipoController.mantenimientoEquipos();

        // Verificar los resultados
        assertEquals("equipos/index", resultado.getViewName());
        assertEquals(equipos, resultado.getModel().get("listaEquipos"));

        verify(equipoService).listarEquipos();
    }

    @Test
    void nuevoEquipo_debeMostrarFormularioVacio() {

        // Ejecutar el método del controlador
        ModelAndView resultado = equipoController.nuevoEquipo();

        // Verificar que se muestre el formulario
        assertEquals("equipos/formulario", resultado.getViewName());

        // Verificar que el formulario tenga un objeto EquipoDTO
        assertNotNull(resultado.getModel().get("equipo"));

        assertInstanceOf(
            pe.edu.usil.gestionequipos.dto.EquipoDTO.class,
            resultado.getModel().get("equipo")
        );
    }

    @Test
    void guardarEquipo_conErroresDeValidacion_debeVolverAlFormulario() {

        // Preparar los datos
        EquipoDTO equipoDTO = new EquipoDTO();
        BindingResult resultado = mock(BindingResult.class);

        // Simular que el formulario contiene errores
        when(resultado.hasErrors()).thenReturn(true);

        // Ejecutar el método del controlador
        String vista = equipoController.guardarEquipo(equipoDTO, resultado);

        // Verificar que regrese al formulario
        assertEquals("equipos/formulario", vista);

        // Verificar que no se intente guardar el equipo
        verify(equipoService, never()).guardarEquipo(any());
    }

    @Test
    void guardarEquipo_conNumeroSerieDuplicado_debeMostrarError() {

        // Preparar los datos
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setNumeroSerie("SERIE-001");

        BindingResult resultado = mock(BindingResult.class);

        // Simular que el formulario no tiene errores iniciales
        when(resultado.hasErrors()).thenReturn(false);

        // Simular que el número de serie ya existe
        when(equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-001", null))
                .thenReturn(true);

        // Ejecutar el método del controlador
        String vista = equipoController.guardarEquipo(equipoDTO, resultado);

        // Verificar que regrese al formulario
        assertEquals("equipos/formulario", vista);

        // Verificar que se registre el error de número de serie
        verify(resultado).rejectValue(
                eq("numeroSerie"),
                eq("error.numeroSerie"),
                anyString()
        );

        // Verificar que no se guarde el equipo
        verify(equipoService, never()).guardarEquipo(any());
    }

    @Test
    void guardarEquipo_conDatosValidos_debeGuardarYRedirigir() {

        // Preparar los datos del equipo
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setNombre("Laptop Lenovo");
        equipoDTO.setTipo("Laptop");
        equipoDTO.setMarca("Lenovo");
        equipoDTO.setNumeroSerie("SERIE-002");
        equipoDTO.setFechaRegistro(java.time.LocalDate.of(2026, 10, 13));
        equipoDTO.setEstado("Activo");

        BindingResult resultado = mock(BindingResult.class);

        // Simular que no existen errores de validación
        when(resultado.hasErrors()).thenReturn(false);

        // Simular que el número de serie está disponible
        when(equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-002", null))
                .thenReturn(false);

        // Ejecutar el método del controlador
        String vista = equipoController.guardarEquipo(equipoDTO, resultado);

        // Verificar que redirija al listado
        assertEquals("redirect:/mantenimientoEquipos", vista);

        // Verificar que se guarde el equipo con los datos correctos
        verify(equipoService).guardarEquipo(argThat(equipo ->
                equipo.getNombre().equals("Laptop Lenovo")
                && equipo.getTipo().equals("Laptop")
                && equipo.getMarca().equals("Lenovo")
                && equipo.getNumeroSerie().equals("SERIE-002")
                && equipo.getEstado().equals("Activo")
        ));
    }

    @Test
    void editarEquipo_cuandoExiste_debeMostrarFormularioConDatos() {

        // Preparar un equipo existente
        EquipoEntity equipo = new EquipoEntity();
        equipo.setIdEquipo(1);
        equipo.setNombre("Laptop Lenovo");
        equipo.setTipo("Laptop");
        equipo.setMarca("Lenovo");
        equipo.setNumeroSerie("SERIE-001");
        equipo.setFechaRegistro(java.time.LocalDate.of(2026, 10, 13));
        equipo.setEstado("Activo");

        // Simular que el servicio encuentra el equipo
        when(equipoService.obtenerEquipo(1)).thenReturn(equipo);

        // Ejecutar el método del controlador
        ModelAndView resultado = equipoController.editarEquipo(1);

        // Verificar que muestre el formulario
        assertEquals("equipos/formulario", resultado.getViewName());

        // Recuperar los datos enviados al formulario
        EquipoDTO equipoDTO = (EquipoDTO) resultado.getModel().get("equipo");

        // Verificar que los datos sean correctos
        assertNotNull(equipoDTO);
        assertEquals(1, equipoDTO.getIdEquipo());
        assertEquals("Laptop Lenovo", equipoDTO.getNombre());
        assertEquals("Laptop", equipoDTO.getTipo());
        assertEquals("Lenovo", equipoDTO.getMarca());
        assertEquals("SERIE-001", equipoDTO.getNumeroSerie());
        assertEquals(java.time.LocalDate.of(2026, 10, 13), equipoDTO.getFechaRegistro());
        assertEquals("Activo", equipoDTO.getEstado());

        verify(equipoService).obtenerEquipo(1);
    }

    @Test
    void editarEquipo_cuandoNoExiste_debeRedirigirAlListado() {

        // Simular que el equipo no existe
        when(equipoService.obtenerEquipo(999)).thenReturn(null);

        // Ejecutar el método del controlador
        ModelAndView resultado = equipoController.editarEquipo(999);

        // Verificar que redirija al listado
        assertEquals(
            "redirect:/mantenimientoEquipos",
            resultado.getViewName()
        );

        // Verificar que se haya consultado el equipo
        verify(equipoService).obtenerEquipo(999);

        // Verificar que no se envíen datos al formulario
        assertFalse(resultado.getModel().containsKey("equipo"));
    }

    @Test
    void eliminarEquipo_debeEliminarYRedirigirAlListado() {

        // Preparar el ID del equipo
        Integer idEquipo = 1;

        // Ejecutar el método del controlador
        String vista = equipoController.eliminarEquipo(idEquipo);

        // Verificar que se solicite eliminar el equipo
        verify(equipoService).eliminarEquipo(idEquipo);

        // Verificar que redirija al listado
        assertEquals(
            "redirect:/mantenimientoEquipos",
            vista
        );
    }

    @Test
    void buscarEquipos_debeMostrarResultadosPorNombre() {

        // Preparar un equipo de prueba
        EquipoEntity equipo = new EquipoEntity();
        equipo.setIdEquipo(1);
        equipo.setNombre("Laptop Lenovo");

        List<EquipoEntity> equipos = List.of(equipo);

        // Simular la búsqueda realizada por el servicio
        when(equipoService.buscarEquiposPorNombre("Laptop"))
                .thenReturn(equipos);

        // Ejecutar el método del controlador
        ModelAndView resultado = equipoController.buscarEquipos("Laptop");

        // Verificar que muestre la vista del listado
        assertEquals("equipos/index", resultado.getViewName());

        // Verificar que los resultados sean correctos
        assertEquals(
                equipos,
                resultado.getModel().get("listaEquipos")
        );

        // Verificar que el servicio reciba el nombre buscado
        verify(equipoService).buscarEquiposPorNombre("Laptop");
    }

    @Test
    void guardarEquipo_conErrorDeIntegridad_debeVolverAlFormulario() {

        // Preparar los datos del equipo
        EquipoDTO equipoDTO = new EquipoDTO();
        equipoDTO.setNombre("Laptop Lenovo");
        equipoDTO.setNumeroSerie("SERIE-003");

        BindingResult resultado = mock(BindingResult.class);

        // Simular que no hay errores iniciales
        when(resultado.hasErrors()).thenReturn(false);

        // Simular que el número de serie no está duplicado
        when(equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-003", null))
                .thenReturn(false);

        // Simular un error de integridad de la base de datos
        doThrow(new DataIntegrityViolationException("Error de integridad"))
                .when(equipoService)
                .guardarEquipo(any(EquipoEntity.class));

        // Ejecutar el método del controlador
        String vista = equipoController.guardarEquipo(equipoDTO, resultado);

        // Verificar que regrese al formulario
        assertEquals("equipos/formulario", vista);

        // Verificar que registre el error
        verify(resultado).reject(
                eq("error.integridad"),
                anyString()
        );
    }

    



}
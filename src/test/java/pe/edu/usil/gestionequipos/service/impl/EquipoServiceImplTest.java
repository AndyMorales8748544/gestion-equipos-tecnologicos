package pe.edu.usil.gestionequipos.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.usil.gestionequipos.repository.EquipoRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Test
    void debeDetectarNumeroSerieDuplicado() {

        // Arrange: preparar los datos
        EquipoEntity equipoExistente = new EquipoEntity();
        equipoExistente.setIdEquipo(1);

        when(equipoRepository.findByNumeroSerie("SERIE-001"))
                .thenReturn(Optional.of(equipoExistente));

        // Act: ejecutar el método que queremos probar
        boolean resultado = equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-001", 2
        );

        // Assert: comprobar el resultado
        assertTrue(resultado);
    }

    @Test
    void debePermitirNumeroSerieDelMismoEquipo() {

        // Arrange: preparar los datos
        EquipoEntity equipoExistente = new EquipoEntity();
        equipoExistente.setIdEquipo(1);

        when(equipoRepository.findByNumeroSerie("SERIE-001"))
                .thenReturn(Optional.of(equipoExistente));

        // Act: verificar el mismo número de serie y el mismo ID
        boolean resultado = equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-001", 1
        );

        // Assert: no debe considerarse duplicado
        assertFalse(resultado);
    }

    @Test
    void debePermitirNumeroSerieNoRegistrado() {

        // Arrange: el número de serie no existe
        when(equipoRepository.findByNumeroSerie("SERIE-999"))
                .thenReturn(Optional.empty());

        // Act: comprobar si existe en otro equipo
        boolean resultado = equipoService.existeNumeroSerieEnOtroEquipo(
                "SERIE-999", 3
        );

        // Assert: no debe existir duplicado
        assertFalse(resultado);
    }

    @Test
    void debeListarTodosLosEquipos() {

        // Arrange: preparar dos equipos de prueba
        EquipoEntity equipo1 = new EquipoEntity();
        equipo1.setIdEquipo(1);
        equipo1.setNombre("Laptop Lenovo");

        EquipoEntity equipo2 = new EquipoEntity();
        equipo2.setIdEquipo(2);
        equipo2.setNombre("Monitor Samsung");

        List<EquipoEntity> equiposSimulados = List.of(equipo1, equipo2);

        when(equipoRepository.findAll())
                .thenReturn(equiposSimulados);

        // Act: ejecutar el método
        List<EquipoEntity> resultado = equipoService.listarEquipos();

        // Assert: verificar los resultados
        assertEquals(2, resultado.size());
        assertEquals("Laptop Lenovo", resultado.get(0).getNombre());
        assertEquals("Monitor Samsung", resultado.get(1).getNombre());

        verify(equipoRepository).findAll();
    }

    @Test
    void debeObtenerEquipoPorId() {

        // Arrange: preparar un equipo existente
        EquipoEntity equipoExistente = new EquipoEntity();
        equipoExistente.setIdEquipo(1);
        equipoExistente.setNombre("Laptop Lenovo");

        when(equipoRepository.findById(1))
                .thenReturn(Optional.of(equipoExistente));

        // Act: buscar el equipo por su ID
        EquipoEntity resultado = equipoService.obtenerEquipo(1);

        // Assert: comprobar el resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEquipo());
        assertEquals("Laptop Lenovo", resultado.getNombre());

        verify(equipoRepository).findById(1);
    }

    @Test
    void debeDevolverNullCuandoEquipoNoExiste() {

        // Arrange: simular que el equipo no existe
        when(equipoRepository.findById(99))
                .thenReturn(Optional.empty());

        // Act: buscar el equipo con ID 99
        EquipoEntity resultado = equipoService.obtenerEquipo(99);

        // Assert: comprobar que no se encontró
        assertNull(resultado);

        verify(equipoRepository).findById(99);
    }

    @Test
    void debeBuscarEquiposPorNombre() {

        // Arrange: preparar un equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setIdEquipo(1);
        equipo.setNombre("Laptop Lenovo");

        when(equipoRepository.findByNombreContainingIgnoreCase("laptop"))
                .thenReturn(List.of(equipo));

        // Act: buscar equipos por nombre
        List<EquipoEntity> resultado =
                equipoService.buscarEquiposPorNombre("laptop");

        // Assert: comprobar los resultados
        assertEquals(1, resultado.size());
        assertEquals("Laptop Lenovo", resultado.get(0).getNombre());

        verify(equipoRepository)
                .findByNombreContainingIgnoreCase("laptop");
    }

    @Test
    void debeGuardarEquipoCorrectamente() {

        // Arrange: preparar un equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Laptop HP");
        equipo.setTipo("Laptop");
        equipo.setMarca("HP");
        equipo.setNumeroSerie("HP-001");

        // Simular que el repositorio guarda el equipo
        when(equipoRepository.save(equipo))
                .thenReturn(equipo);

        // Act: ejecutar el método
        EquipoEntity resultado = equipoService.guardarEquipo(equipo);

        // Assert: comprobar el resultado
        assertNotNull(resultado);
        assertEquals("Laptop HP", resultado.getNombre());
        assertEquals("HP-001", resultado.getNumeroSerie());

        verify(equipoRepository).save(equipo);
    }

    @Test
    void debeActualizarEquipoCorrectamente() {

        // Arrange: preparar un equipo con datos actualizados
        EquipoEntity equipo = new EquipoEntity();
        equipo.setIdEquipo(1);
        equipo.setNombre("Laptop Lenovo Actualizada");
        equipo.setMarca("Lenovo");
        equipo.setNumeroSerie("LEN-001");

        // Simular la actualización en el repositorio
        when(equipoRepository.save(equipo))
                .thenReturn(equipo);

        // Act: ejecutar el método
        EquipoEntity resultado = equipoService.actualizarEquipo(equipo);

        // Assert: comprobar los datos actualizados
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEquipo());
        assertEquals("Laptop Lenovo Actualizada", resultado.getNombre());
        assertEquals("LEN-001", resultado.getNumeroSerie());

        verify(equipoRepository).save(equipo);
    }

    @Test
    void debeEliminarEquipoCorrectamente() {

        // Arrange: preparar el ID del equipo
        Integer idEquipo = 1;

        // Act: ejecutar el método de eliminación
        equipoService.eliminarEquipo(idEquipo);

        // Assert: verificar que se llamó al repositorio
        verify(equipoRepository).deleteById(idEquipo);
    }
}

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
}

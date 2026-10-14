package pe.edu.usil.gestionequipos.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EquipoRepositoryTest {

    @Autowired
    private EquipoRepository equipoRepository;

    @Test
    void guardarEquipo_debeRegistrarEquipoEnBaseDeDatos() {

        // Preparar los datos del equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Laptop Lenovo");
        equipo.setTipo("Laptop");
        equipo.setMarca("Lenovo");
        equipo.setNumeroSerie("TEST-SERIE-001");
        equipo.setFechaRegistro(LocalDate.now());
        equipo.setEstado("Activo");

        // Guardar el equipo en MySQL
        EquipoEntity equipoGuardado = equipoRepository.saveAndFlush(equipo);

        // Verificar que se generó un ID
        assertNotNull(equipoGuardado.getIdEquipo());

        // Consultar el equipo desde la base de datos
        EquipoEntity equipoEncontrado = equipoRepository
                .findById(equipoGuardado.getIdEquipo())
                .orElseThrow();

        // Verificar los datos registrados
        assertEquals("Laptop Lenovo", equipoEncontrado.getNombre());
        assertEquals("Lenovo", equipoEncontrado.getMarca());
        assertEquals("TEST-SERIE-001", equipoEncontrado.getNumeroSerie());
    }

    @Test
    void buscarEquiposPorNombre_debeIgnorarMayusculasYMinusculas() {

        // Preparar un equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Laptop Lenovo");
        equipo.setTipo("Laptop");
        equipo.setMarca("Lenovo");
        equipo.setNumeroSerie("TEST-SERIE-002");
        equipo.setFechaRegistro(LocalDate.now());
        equipo.setEstado("Activo");

        // Guardar el equipo en la base de datos de pruebas
        equipoRepository.saveAndFlush(equipo);

        // Buscar utilizando letras minúsculas
        var resultados = equipoRepository
                .findByNombreContainingIgnoreCase("lenovo");

        // Comprobar que encontramos el equipo registrado
        assertTrue(resultados.stream()
                .anyMatch(e -> "TEST-SERIE-002".equals(e.getNumeroSerie())));
    }

    @Test
    void buscarEquipoPorNumeroSerie_debeEncontrarEquipo() {

        // Preparar un equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Monitor Samsung");
        equipo.setTipo("Monitor");
        equipo.setMarca("Samsung");
        equipo.setNumeroSerie("TEST-SERIE-003");
        equipo.setFechaRegistro(LocalDate.now());
        equipo.setEstado("Activo");

        // Guardar el equipo en MySQL
        equipoRepository.saveAndFlush(equipo);

        // Buscar por número de serie
        var resultado = equipoRepository
                .findByNumeroSerie("TEST-SERIE-003");

        // Verificar que existe
        assertTrue(resultado.isPresent());

        // Verificar los datos recuperados
        assertEquals("Monitor Samsung", resultado.get().getNombre());
        assertEquals("Samsung", resultado.get().getMarca());
    }

    @Test
    void actualizarEquipo_debeModificarDatosEnBaseDeDatos() {

        // Crear un equipo de prueba
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Laptop HP");
        equipo.setTipo("Laptop");
        equipo.setMarca("HP");
        equipo.setNumeroSerie("TEST-SERIE-004");
        equipo.setFechaRegistro(LocalDate.now());
        equipo.setEstado("Activo");

        // Guardar el equipo
        EquipoEntity equipoGuardado = equipoRepository.saveAndFlush(equipo);

        // Actualizar sus datos
        equipoGuardado.setNombre("Laptop HP Actualizada");
        equipoGuardado.setEstado("Mantenimiento");

        equipoRepository.saveAndFlush(equipoGuardado);

        // Consultar el equipo actualizado
        EquipoEntity equipoActualizado = equipoRepository
                .findById(equipoGuardado.getIdEquipo())
                .orElseThrow();

        // Verificar los cambios
        assertEquals("Laptop HP Actualizada", equipoActualizado.getNombre());
        assertEquals("Mantenimiento", equipoActualizado.getEstado());
    }

    @Test
    void eliminarEquipo_debeEliminarRegistroDeBaseDeDatos() {

        // Crear un equipo de prueba
        EquipoEntity equipo = new EquipoEntity();
        equipo.setNombre("Teclado Logitech");
        equipo.setTipo("Periférico");
        equipo.setMarca("Logitech");
        equipo.setNumeroSerie("TEST-SERIE-005");
        equipo.setFechaRegistro(LocalDate.now());
        equipo.setEstado("Activo");

        // Guardar el equipo
        EquipoEntity equipoGuardado = equipoRepository.saveAndFlush(equipo);

        Integer idEquipo = equipoGuardado.getIdEquipo();

        // Verificar que existe
        assertTrue(equipoRepository.existsById(idEquipo));

        // Eliminar el equipo
        equipoRepository.deleteById(idEquipo);
        equipoRepository.flush();

        // Verificar que ya no existe
        assertFalse(equipoRepository.existsById(idEquipo));
    }

    

}

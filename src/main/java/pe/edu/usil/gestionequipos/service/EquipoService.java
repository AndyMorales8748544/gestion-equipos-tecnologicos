package pe.edu.usil.gestionequipos.service;

import java.util.List;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

public interface EquipoService {
    
    public List<EquipoEntity> listarEquipos(); // Mostrar todos
    public List<EquipoEntity> buscarEquiposPorNombre(String nombre); // Buscar por nombre
    public EquipoEntity obtenerEquipo(Integer idEquipo); // Obtener uno por su ID
    public EquipoEntity guardarEquipo(EquipoEntity equipo); // Registrar
    public EquipoEntity actualizarEquipo(EquipoEntity equipo); // Actualizar
    public void eliminarEquipo(Integer idEquipo); // Eliminar
}

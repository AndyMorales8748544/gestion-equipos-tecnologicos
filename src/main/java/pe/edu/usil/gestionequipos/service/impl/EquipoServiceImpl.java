package pe.edu.usil.gestionequipos.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

import lombok.RequiredArgsConstructor;
import pe.edu.usil.gestionequipos.repository.EquipoRepository;
import pe.edu.usil.gestionequipos.service.EquipoService;

@Service
@RequiredArgsConstructor
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;

    @Override
    public List<EquipoEntity> listarEquipos() {
        return equipoRepository.findAll(); //obtiene todos los equipos
    }

    @Override
    public List<EquipoEntity> buscarEquiposPorNombre(String nombre) {
        return equipoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public EquipoEntity obtenerEquipo(Integer idEquipo) {
        return equipoRepository.findById(idEquipo).orElse(null); //busca un equipo por su ID
    }

    @Override
    public EquipoEntity guardarEquipo(EquipoEntity equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public EquipoEntity actualizarEquipo(EquipoEntity equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public void eliminarEquipo(Integer idEquipo) {
        equipoRepository.deleteById(idEquipo);
    }

}

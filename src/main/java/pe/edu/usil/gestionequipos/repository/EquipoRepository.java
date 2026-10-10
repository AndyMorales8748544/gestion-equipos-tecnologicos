package pe.edu.usil.gestionequipos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

public interface EquipoRepository extends JpaRepository<EquipoEntity, Integer> {

    List<EquipoEntity> findByNombreContainingIgnoreCase(String nombre);

    Optional<EquipoEntity> findByNumeroSerie(String numeroSerie);
}

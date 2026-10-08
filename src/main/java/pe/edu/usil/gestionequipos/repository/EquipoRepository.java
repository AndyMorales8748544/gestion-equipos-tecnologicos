package pe.edu.usil.gestionequipos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntity, Integer> {

    List<EquipoEntity> findByNombreContainingIgnoreCase(String nombre);
}

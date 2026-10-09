package pe.edu.usil.gestionequipos.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipoDTO {

    private Integer idEquipo;

    private String nombre;

    private String tipo;

    private String marca;

    private String numeroSerie;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaRegistro;

    private String estado;
}

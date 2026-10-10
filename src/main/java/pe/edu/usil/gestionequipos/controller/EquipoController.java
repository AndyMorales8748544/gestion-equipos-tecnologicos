package pe.edu.usil.gestionequipos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;
import pe.edu.usil.gestionequipos.service.EquipoService;

import pe.edu.usil.gestionequipos.dto.EquipoDTO;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;


@Controller
public class EquipoController {
    
    private static final String VISTA_FORMULARIO = "equipos/formulario";
    
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping("/mantenimientoEquipos")
    public ModelAndView mantenimientoEquipos() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("equipos/index");
        mv.addObject("listaEquipos", equipoService.listarEquipos());

        return mv;
    }

    @GetMapping("/nuevoEquipo")
    public ModelAndView nuevoEquipo() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName(VISTA_FORMULARIO);
        mv.addObject("equipo", new EquipoDTO());

        return mv;
    }

    @PostMapping("/guardarEquipo")
    public String guardarEquipo(@Valid @ModelAttribute("equipo") EquipoDTO equipoDTO, BindingResult resultado) {

        if (resultado.hasErrors()){
            return VISTA_FORMULARIO;
        }

        if (equipoService.existeNumeroSerieEnOtroEquipo(
            equipoDTO.getNumeroSerie(),
            equipoDTO.getIdEquipo())) {

            resultado.rejectValue(
                    "numeroSerie",
                    "error.numeroSerie",
                    "El número de serie ya está registrado en otro equipo"
            );

            return VISTA_FORMULARIO;
        }

        EquipoEntity equipo = new EquipoEntity();

        equipo.setIdEquipo(equipoDTO.getIdEquipo());
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setTipo(equipoDTO.getTipo());
        equipo.setMarca(equipoDTO.getMarca());
        equipo.setNumeroSerie(equipoDTO.getNumeroSerie());
        equipo.setFechaRegistro(equipoDTO.getFechaRegistro());
        equipo.setEstado(equipoDTO.getEstado());

        equipoService.guardarEquipo(equipo);

        return "redirect:/mantenimientoEquipos";
    }

    @GetMapping("/editarEquipo/{id}")
    public ModelAndView editarEquipo(@PathVariable("id") Integer idEquipo) {
        ModelAndView mv = new ModelAndView();

        EquipoEntity equipo = equipoService.obtenerEquipo(idEquipo);

        EquipoDTO equipoDTO = new EquipoDTO();

        equipoDTO.setIdEquipo(equipo.getIdEquipo());
        equipoDTO.setNombre(equipo.getNombre());
        equipoDTO.setTipo(equipo.getTipo());
        equipoDTO.setMarca(equipo.getMarca());
        equipoDTO.setNumeroSerie(equipo.getNumeroSerie());
        equipoDTO.setFechaRegistro(equipo.getFechaRegistro());
        equipoDTO.setEstado(equipo.getEstado());

        mv.setViewName(VISTA_FORMULARIO);
        mv.addObject("equipo", equipo);

        return mv;
    }

    @PostMapping("/eliminarEquipo/{id}")
    public String eliminarEquipo(@PathVariable("id") Integer idEquipo) {

        equipoService.eliminarEquipo(idEquipo);

        return "redirect:/mantenimientoEquipos";
    }

    @GetMapping("/buscarEquipos")
    public ModelAndView buscarEquipos(@RequestParam("nombre") String nombre) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("equipos/index");
        mv.addObject("listaEquipos", equipoService.buscarEquiposPorNombre(nombre));

        return mv;
    }


    
}

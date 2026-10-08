package pe.edu.usil.gestionequipos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.edu.usil.gestionequipos.entity.EquipoEntity;
import pe.edu.usil.gestionequipos.service.EquipoService;


@Controller
public class EquipoController {
    
    @Autowired
    private EquipoService equipoService;

    @RequestMapping("/mantenimientoEquipos")
    public ModelAndView mantenimientoEquipos() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("equipos/index");
        mv.addObject("listaEquipos", equipoService.listarEquipos());

        return mv;
    }

    @RequestMapping("/nuevoEquipo")
    public ModelAndView nuevoEquipo() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("equipos/formulario");
        mv.addObject("equipo", new EquipoEntity());

        return mv;
    }

    @PostMapping("/guardarEquipo")
    public String guardarEquipo(@ModelAttribute("equipo") EquipoEntity equipo) {

        equipoService.guardarEquipo(equipo);

        return "redirect:/mantenimientoEquipos";
    }

    @GetMapping("/editarEquipo/{id}")
    public ModelAndView editarEquipo(@PathVariable("id") Integer idEquipo) {
        ModelAndView mv = new ModelAndView();

        EquipoEntity equipo = equipoService.obtenerEquipo(idEquipo);

        mv.setViewName("equipos/formulario");
        mv.addObject("equipo", equipo);

        return mv;
    }

    @GetMapping("/eliminarEquipo/{id}")
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

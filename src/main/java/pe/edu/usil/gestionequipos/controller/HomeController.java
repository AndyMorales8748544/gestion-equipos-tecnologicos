package pe.edu.usil.gestionequipos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/") //indica que cuando entramos a http://localhost:8080/
    public String index() {
        return "index";
    }
}

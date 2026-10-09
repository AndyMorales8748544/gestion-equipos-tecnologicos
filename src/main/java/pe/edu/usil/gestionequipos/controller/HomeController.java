package pe.edu.usil.gestionequipos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Página principal: http://localhost:8080/
    public String index() {
        return "index";
    }
}

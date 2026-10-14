package pe.edu.usil.gestionequipos.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    void index_debeRetornarVistaPrincipal() {

        // Arrange: crear el controlador
        HomeController controller = new HomeController();

        // Act: ejecutar el metodo
        String vista = controller.index();

        // Assert: verificar el resultado
        assertEquals("index", vista);
    }
}

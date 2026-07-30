package es.caib.sistramit.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller sin autenticación para verificar que aplicación está viva.
 */
@Controller
public class AliveController {

    @RequestMapping(value = "/alive")
    public void alive(HttpServletResponse response) throws IOException {
        // Retornamos directamente un 200 OK con respuesta con texto ALIVE
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");
        response.getWriter().write("ALIVE");
    }

}

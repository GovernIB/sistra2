package es.caib.sistrages.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.XTestJson;

@RestController
@RequestMapping("/ejemploRest")
public class RestControllerExample {

    @Autowired
    private RestApiInternaService restApiService;

    @RequestMapping("/versionTramite")
    public RVersionTramite versionTramite() {
        // restApiService.test("hola");
        return XTestJson.crearVersionTramite();
    }

}

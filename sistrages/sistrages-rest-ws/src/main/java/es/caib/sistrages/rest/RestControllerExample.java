package es.caib.sistrages.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistrages.core.api.service.RestApiService;
import es.caib.sistrages.rest.api.VersionTramite;
import es.caib.sistrages.rest.api.util.XTestJson;

@RestController
@RequestMapping("/ejemploRest")
public class RestControllerExample {

    @Autowired
    private RestApiService restApiService;

    @RequestMapping("/versionTramite")
    public VersionTramite versionTramite() {
        // restApiService.test("hola");
        return XTestJson.crearVersionTramite();
    }

}

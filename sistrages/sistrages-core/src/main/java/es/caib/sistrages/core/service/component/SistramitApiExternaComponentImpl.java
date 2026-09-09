package es.caib.sistrages.core.service.component;

import es.caib.sistrages.core.api.model.FuncionarioHabilitadoInfo;
import es.caib.sistrages.core.api.model.PersonaInfo;
import es.caib.sistrages.core.api.model.TramiteFH;
import es.caib.sistramit.rest.api.externa.v1.RFuncionarioHabilitadoInfo;
import es.caib.sistramit.rest.api.externa.v1.RInfoTicketAccesoFH;
import es.caib.sistramit.rest.api.externa.v1.RPersonaInfo;
import es.caib.sistramit.rest.api.externa.v1.RTramiteFH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

/**
 * Implementación del acceso a componente SISTRAMIT.
 *
 * @author Indra
 *
 */
@Component("sistramitApiExternaComponent")
public class SistramitApiExternaComponentImpl implements SistramitApiExternaComponent {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConfiguracionComponent configuracionComponent;

    @Override
    public String obtenerTicketAccesoFH(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo, PersonaInfo interesado,
            PersonaInfo representante, TramiteFH tramiteFH) {
        String resultado = "";
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        final RInfoTicketAccesoFH param = new RInfoTicketAccesoFH();
        param.setFuncionarioHabilitado(convierteFuncionarioHabilitadoInfo(funcionarioHabilitadoInfo));
        param.setInteresado(conviertePersonaInfo(interesado));
        if(representante != null) {
            param.setRepresentante(conviertePersonaInfo(representante));
        }
        param.setTramiteFH(convierteTramiteFH(tramiteFH));

        final HttpEntity<RInfoTicketAccesoFH> request = new HttpEntity<>(param, headers);
        ResponseEntity<String> response = null;
        final String url = getUrl();

        try {
            response = restTemplate.postForEntity(url + "/ticketAccesoFH", request, String.class);
        } catch (RestClientException | IllegalArgumentException e) {
            log.warn("Error obteniendo ticket de acceso FH desde Sistramit. URL: " + url, e);
            return resultado;
        }

        if (response != null && response.getStatusCodeValue() == 200) {
            resultado = response.getBody();
        }

        if (resultado == null || resultado.trim().isEmpty()) {
            log.warn("Sistramit no ha devuelto un ticket de acceso FH válido. URL: " + url);
        }

        return resultado;
    }

    private RFuncionarioHabilitadoInfo convierteFuncionarioHabilitadoInfo(FuncionarioHabilitadoInfo funcionarioHabilitadoInfo) {
        RFuncionarioHabilitadoInfo rFuncionarioHabilitadoInfo = new RFuncionarioHabilitadoInfo();

        rFuncionarioHabilitadoInfo.setApellido1(funcionarioHabilitadoInfo.getApellido1());
        rFuncionarioHabilitadoInfo.setApellido2(funcionarioHabilitadoInfo.getApellido2());
        rFuncionarioHabilitadoInfo.setDir3(funcionarioHabilitadoInfo.getDir3());
        rFuncionarioHabilitadoInfo.setNif(funcionarioHabilitadoInfo.getNif());
        rFuncionarioHabilitadoInfo.setNombre(funcionarioHabilitadoInfo.getNombre());
        rFuncionarioHabilitadoInfo.setUsername(funcionarioHabilitadoInfo.getUsername());

        return rFuncionarioHabilitadoInfo;
    }

    private RPersonaInfo conviertePersonaInfo(PersonaInfo personaInfo) {
        RPersonaInfo rPersonaInfo = new RPersonaInfo();

        rPersonaInfo.setApellido1(personaInfo.getApellido1());
        rPersonaInfo.setApellido2(personaInfo.getApellido2());
        rPersonaInfo.setNif(personaInfo.getNif());
        rPersonaInfo.setNombre(personaInfo.getNombre());

        return rPersonaInfo;
    }

    private RTramiteFH convierteTramiteFH(TramiteFH tramiteFH) {
        RTramiteFH rTramiteFH = new RTramiteFH();

        rTramiteFH.setIdTramiteCatalogo(tramiteFH.getIdTramiteCatalogo());
        rTramiteFH.setIdioma(tramiteFH.getIdioma());
        rTramiteFH.setParametros(tramiteFH.getParametros());
        rTramiteFH.setServicioCatalogo(tramiteFH.isServicioCatalogo());
        rTramiteFH.setTramite(tramiteFH.getTramite());
        rTramiteFH.setVersion(tramiteFH.getVersion());

        return rTramiteFH;
    }

    private String getPassword() {
        return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_APIEXTERNA_PWD);
    }

    private String getUser() {
        return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_APIEXTERNA_USER);
    }

    private String getUrl() {
        return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_APIEXTERNA_URL);
    }
}

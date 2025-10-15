package es.caib.sistrahelp.core.ejb;

import es.caib.sistrahelp.core.api.model.DatosResumen;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.MensajeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MensajeEmailServiceBean implements MensajeEmailService {


    @Autowired
    private MensajeEmailService mensajeEmailService;

    @Override
    @PermitAll
    public String mensajeResumenDiario(String idioma, String nombre, String logo, byte[] imageBytes,
                                       DatosResumen datosResumen, TypeRoleAcceso rolUsuario) {
        return mensajeEmailService.mensajeResumenDiario(idioma, nombre, logo, imageBytes, datosResumen, rolUsuario);
    }

}

package es.caib.sistramit.core.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.SesionFormularioInfo;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.service.GestorFormulariosInternoService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GestorFormulariosInternoServiceBean
        implements GestorFormulariosInternoService {

    // @Autowired
    // private GestorFormulariosInternoService gestorFormulariosInternoService;

    @Override
    public String cargarSesion(String ticket) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaginaFormulario cargarPaginaActual(String idSesionFormulario) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoEvaluarCambioCampo evaluarCambioCampoPagina(
            String idSesionFormulario, String idCampo,
            List<ValorCampo> valoresPagina) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultadoGuardarPagina guardarPagina(String idSesionFormulario,
            List<ValorCampo> valoresPagina, String accionPersonalizada) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void cancelarFormulario(String idSesionFormulario) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ValorCampo> deserializarValoresCampos(String idSesionFormulario,
            Map<String, String> valores) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void purgar() {
        // TODO Auto-generated method stub

    }

    @Override
    public SesionFormularioInfo obtenerInformacionFormulario(
            String idSesionFormulario) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaginaFormulario cargarPaginaAnterior(String idSesionFormulario) {
        // TODO Auto-generated method stub
        return null;
    }

}

package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
/**
 * Servicios Api Rest.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

    public String test(String echo);
    public List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro);
	public List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, String filtro);
	public List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad);
	public Entidad loadEntidad(final Long idEntidad);
	public String getReferenciaFichero(Long id);
	public Tramite loadTramite(Long idTramite);
	public Dominio loadDominio(Long id);
	public String getValorConfiguracionGlobal(String propiedad);
	public DisenyoFormulario getFormularioInterno(Long pId);
	public FormateadorFormulario getFormateadorFormulario(Long idFmt);
	public String getPaginaFormularioHTMLAsistente(Long pIdPage, String pLang);
	public List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(Long idPlantillaFormulario);

}

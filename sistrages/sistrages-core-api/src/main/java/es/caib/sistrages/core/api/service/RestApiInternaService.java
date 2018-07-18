package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
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
	public TramiteVersion loadTramiteVersion(String idTramite, int version);
	public List<TramitePaso> getTramitePasos(Long idTramiteVersion);
	public DisenyoFormulario getDisenyoFormularioById(Long idForm);
	public List<AvisoEntidad> getAvisosEntidad(String pIdEntidad);
	public ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> listaParams);
	

}

package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Servicios Api Rest.
 *
 * @author Indra
 *
 */
public interface RestApiInternaService {

	String test(String echo);

	List<ConfiguracionGlobal> listConfiguracionGlobal(final String filtro);

	List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, String filtro);

	List<FormularioSoporte> listOpcionesFormularioSoporte(final Long idEntidad);

	Entidad loadEntidad(final Long idEntidad);

	Entidad loadEntidad(String idEntidad);

	String getReferenciaFichero(Long id);

	Tramite loadTramite(Long idTramite);

	Dominio loadDominio(String idDominio);

	Dominio loadDominio(Long idDominio);

	String getValorConfiguracionGlobal(String propiedad);

	DisenyoFormulario getFormularioInterno(Long pId);

	FormateadorFormulario getFormateadorFormulario(Long idFmt);

	String getPaginaFormularioHTMLAsistente(Long pIdPage, String pLang);

	List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(Long idPlantillaFormulario);

	TramiteVersion loadTramiteVersion(String idTramite, int version);

	List<TramitePaso> getTramitePasos(Long idTramiteVersion);

	DisenyoFormulario getDisenyoFormularioById(Long idForm);

	List<AvisoEntidad> getAvisosEntidad(String pIdEntidad);

	ValoresDominio realizarConsultaFuenteDatos(String idDominio, List<ValorParametroDominio> listaParams);

	Area loadArea(Long idArea);

	public List<Rol> obtenerPermisosHelpdesk();

	ValoresDominio realizarConsultaListaFija(String idDominio);

	List<String> getIdentificadoresDominiosByTV(final Long idTramiteVersion);

	FormateadorFormulario getFormateadorPorDefecto(String codigoDir3);

	List<PlantillaFormateador> getPlantillasFormateador(final Long idFormateador);

	List<String> listIdAreasByEntidad(Long pIdEntidad);

	List<Entidad> listEntidad();

	String getIdentificadorByCodigoVersion(Long codigoTramiteVersion);

	List<IncidenciaValoracion> getValoraciones(Long codigo);

	List<GestorExternoFormularios> listGestorExternoFormularios(Long pIdEntidad);

	List<PlantillaEntidad> getPlantillasEntidad(Long codigo);
}

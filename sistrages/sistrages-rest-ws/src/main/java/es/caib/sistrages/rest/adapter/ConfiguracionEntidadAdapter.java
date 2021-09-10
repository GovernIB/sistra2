package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.IncidenciaValoracion;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RIncidenciaValoracion;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
import es.caib.sistrages.rest.api.interna.RPlantillaEntidad;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistrages.rest.api.interna.RPlantillaIdioma;
import es.caib.sistrages.rest.utils.AdapterUtils;

/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */
@Component
public class ConfiguracionEntidadAdapter {

	/** Servicio rest. */
	@Autowired
	private RestApiInternaService restApiService;

	/**
	 * Conversion a modelo rest.
	 *
	 * @param entidad
	 * @param formSoporte
	 * @param gestoresExternosFormularios
	 */
	public RConfiguracionEntidad convertir(final Entidad entidad, final List<FormularioSoporte> formSoporte,
			final List<PlantillaFormateador> plantillas, final List<IncidenciaValoracion> valoraciones, final List<PlantillaEntidad> plantillasEntidad) {

		final RConfiguracionEntidad rConfiguracionEntidad = new RConfiguracionEntidad();
		rConfiguracionEntidad.setTimestamp(System.currentTimeMillis() + "");
		rConfiguracionEntidad.setIdentificador(entidad.getCodigoDIR3() != null ? entidad.getCodigoDIR3() : null);
		rConfiguracionEntidad.setLogo(restApiService.getReferenciaFichero(
				entidad.getLogoAsistente() != null ? entidad.getLogoAsistente().getCodigo() : null));
		rConfiguracionEntidad.setLogoGestor(restApiService
				.getReferenciaFichero(entidad.getLogoGestor() != null ? entidad.getLogoGestor().getCodigo() : null));
		rConfiguracionEntidad.setCss(
				restApiService.getReferenciaFichero(entidad.getCss() != null ? entidad.getCss().getCodigo() : null));
		rConfiguracionEntidad.setContactoHTML(AdapterUtils.generarLiteral(entidad.getPie()));
		rConfiguracionEntidad.setUrlCarpeta(AdapterUtils.generarLiteral(entidad.getUrlCarpetaCiudadana()));
		rConfiguracionEntidad.setUrlSede(AdapterUtils.generarLiteral(entidad.getUrlSede()));
		rConfiguracionEntidad.setEmail(entidad.getEmail());
		rConfiguracionEntidad.setAyudaEmail(entidad.isEmailHabilitado());
		if (entidad.isTelefonoHabilitado()) {
			rConfiguracionEntidad.setAyudaTelefono(entidad.getTelefono());
		}
		if (entidad.isUrlSoporteHabilitado()) {
			rConfiguracionEntidad.setAyudaUrl(entidad.getUrlSoporte());
		}
		if (entidad.isFormularioIncidenciasHabilitado()) {
			rConfiguracionEntidad.setAyudaFormulario(generaFormularios(formSoporte));
		}
		rConfiguracionEntidad.setPlugins(
				AdapterUtils.crearPlugins(restApiService.listPlugin(TypeAmbito.ENTIDAD, entidad.getCodigo(), null)));
		rConfiguracionEntidad.setDescripcion(AdapterUtils.generarLiteral(entidad.getNombre()));
		rConfiguracionEntidad.setDiasPreregistro(entidad.getDiasPreregistro());
		rConfiguracionEntidad.setInfoLopdHTML(AdapterUtils.generarLiteral(entidad.getLopd()));

		rConfiguracionEntidad.setMapaWeb(AdapterUtils.generarLiteral(entidad.getMapaWeb()));
		rConfiguracionEntidad.setAvisoLegal(AdapterUtils.generarLiteral(entidad.getAvisoLegal()));
		rConfiguracionEntidad.setRss(AdapterUtils.generarLiteral(entidad.getRss()));
		rConfiguracionEntidad.setUrlFacebook(entidad.getUrlFacebook());
		rConfiguracionEntidad.setUrlInstagram(entidad.getUrlInstagram());
		rConfiguracionEntidad.setUrlTwitter(entidad.getUrlTwitter());
		rConfiguracionEntidad.setUrlYoutube(entidad.getUrlYoutube());
		rConfiguracionEntidad.setDiasTramitesPresenciales(entidad.getDiasTramitesPresenciales());
		rConfiguracionEntidad.setPermiteSubsanarAnexar(entidad.isPermiteSubsanarAnexar());
		rConfiguracionEntidad.setPermiteSubsanarPagar(entidad.isPermiteSubsanarPagar());
		rConfiguracionEntidad.setPermiteSubsanarRegistrar(entidad.isPermiteSubsanarRegistrar());
		rConfiguracionEntidad
				.setInstruccionesPresencial(AdapterUtils.generarLiteral(entidad.getInstruccionesPresencial()));
		rConfiguracionEntidad
				.setInstruccionesSubsanacion(AdapterUtils.generarLiteral(entidad.getInstruccionesSubsanacion()));
		rConfiguracionEntidad.setOficinaRegistroCentralizado(entidad.getOficinaRegistroCentralizado());
		rConfiguracionEntidad.setRegistroCentralizado(entidad.isRegistroCentralizado());

		if (plantillas != null && !plantillas.isEmpty()) {
			final List<RPlantillaIdioma> plantillasDefecto = new ArrayList<>();
			for (final PlantillaFormateador plantilla : plantillas) {
				final RPlantillaIdioma rplantilla = new RPlantillaIdioma();
				rplantilla.setIdioma(plantilla.getIdioma());

				final RPlantillaFormulario plantillaFormulario = new RPlantillaFormulario();
				plantillaFormulario.setClaseFormateador(plantilla.getFormateador().getClassname());
				plantillaFormulario.setDefecto(true);
				if (plantilla.getFichero() != null) {
					plantillaFormulario.setFicheroPlantilla(
							restApiService.getReferenciaFichero(plantilla.getFichero().getCodigo()));
				}
				plantillaFormulario.setIdentificador(plantilla.getFormateador().getIdentificador());
				rplantilla.setPlantilla(plantillaFormulario);

				plantillasDefecto.add(rplantilla);
			}
			rConfiguracionEntidad.setPlantillasDefecto(plantillasDefecto);
		}
		rConfiguracionEntidad.setValorarTramite(entidad.isValorarTramite());
		if (valoraciones != null) {
			final List<RIncidenciaValoracion> rvaloraciones = new ArrayList<>();
			for (final IncidenciaValoracion valoracion : valoraciones) {
				final RIncidenciaValoracion rvaloracion = new RIncidenciaValoracion();
				rvaloracion.setDescripcion(AdapterUtils.generarLiteral(valoracion.getDescripcion()));
				rvaloracion.setIdentificador(valoracion.getIdentificador());
				rvaloraciones.add(rvaloracion);
			}
			rConfiguracionEntidad.setIncidenciasValoracion(rvaloraciones);
		}
		rConfiguracionEntidad.setRegistroOcultarDescargaDocumentos(entidad.isRegistroOcultarDescargaDocumentos());

		List<RPlantillaEntidad> rplantillasEntidad = null;
		if (plantillasEntidad != null) {
			rplantillasEntidad = new ArrayList<>();
			for(PlantillaEntidad plantilla : plantillasEntidad) {
				RPlantillaEntidad rplantilla = new RPlantillaEntidad();
				rplantilla.setIdioma(plantilla.getIdioma());
				rplantilla.setTipo(plantilla.getTipo().toString());
				if (plantilla.getFichero() != null) {
					rplantilla.setPath(
							restApiService.getReferenciaFichero(plantilla.getFichero().getCodigo()));
				}
				rplantillasEntidad.add(rplantilla);
			}
		}
		rConfiguracionEntidad.setPlantillas(rplantillasEntidad);
		return rConfiguracionEntidad;
	}

	/**
	 * Genera los formularios
	 *
	 * @param formularioIncidencias
	 * @return
	 */
	private List<ROpcionFormularioSoporte> generaFormularios(final List<FormularioSoporte> formularioIncidencias) {
		ROpcionFormularioSoporte opc;
		List<ROpcionFormularioSoporte> opciones = null;
		if (formularioIncidencias != null) {
			opciones = new ArrayList<>();
			for (final FormularioSoporte fs : formularioIncidencias) {
				opc = new ROpcionFormularioSoporte();
				opc.setTipo(AdapterUtils.generarLiteral(fs.getTipoIncidencia()));
				opc.setDescripcion(AdapterUtils.generarLiteral(fs.getDescripcion()));
				opc.setDestinatario(fs.getTipoDestinatario() != null ? fs.getTipoDestinatario().toString() : null);
				opc.setListaEmails(fs.getListaEmails());
				opc.setCodigo(fs.getCodigo());
				opciones.add(opc);
			}
		}
		return opciones;
	}

}
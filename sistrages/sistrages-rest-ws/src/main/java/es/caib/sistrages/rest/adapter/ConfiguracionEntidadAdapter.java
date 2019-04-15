package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.ROpcionFormularioSoporte;
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
	 */
	public RConfiguracionEntidad convertir(final Entidad entidad, final List<FormularioSoporte> formSoporte,
			final List<PlantillaFormateador> plantillas) {

		final RConfiguracionEntidad rConfiguracionEntidad = new RConfiguracionEntidad();
		rConfiguracionEntidad.setTimestamp(System.currentTimeMillis() + "");
		rConfiguracionEntidad.setIdentificador(entidad.getCodigoDIR3() != null ? entidad.getCodigoDIR3() : null);
		rConfiguracionEntidad.setLogo(restApiService.getReferenciaFichero(
				entidad.getLogoAsistente() != null ? entidad.getLogoAsistente().getCodigo() : null));
		rConfiguracionEntidad.setLogoGestor(restApiService.getReferenciaFichero(
				entidad.getLogoGestor() != null ? entidad.getLogoGestor().getCodigo() : null));
		rConfiguracionEntidad.setCss(restApiService
				.getReferenciaFichero(entidad.getCss() != null ? entidad.getCss().getCodigo() : null));
		rConfiguracionEntidad.setEmail(entidad.getEmail());
		rConfiguracionEntidad.setContactoHTML(AdapterUtils.generarLiteral(entidad.getPie()));
		rConfiguracionEntidad.setUrlCarpeta(AdapterUtils.generarLiteral(entidad.getUrlCarpetaCiudadana()));
		rConfiguracionEntidad.setAyudaTelefono(entidad.getTelefono());
		rConfiguracionEntidad.setAyudaUrl(entidad.getUrlSoporte());
		rConfiguracionEntidad.setPlugins(
				AdapterUtils.crearPlugins(restApiService.listPlugin(TypeAmbito.ENTIDAD, entidad.getCodigo(), null)));
		rConfiguracionEntidad.setAyudaFormulario(generaFormularios(formSoporte));
		rConfiguracionEntidad.setAyudaEmail(entidad.isEmailHabilitado());
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
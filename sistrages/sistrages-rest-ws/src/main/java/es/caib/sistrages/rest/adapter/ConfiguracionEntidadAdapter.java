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
	 * @param idEntidad
	 * @param formSoporte
	 */
	public RConfiguracionEntidad convertir(final Entidad idEntidad, final List<FormularioSoporte> formSoporte,
			final List<PlantillaFormateador> plantillas) {

		final RConfiguracionEntidad rConfiguracionEntidad = new RConfiguracionEntidad();
		rConfiguracionEntidad.setTimestamp(System.currentTimeMillis() + "");
		rConfiguracionEntidad.setIdentificador(idEntidad.getCodigoDIR3() != null ? idEntidad.getCodigoDIR3() : null);
		rConfiguracionEntidad.setLogo(restApiService.getReferenciaFichero(
				idEntidad.getLogoAsistente() != null ? idEntidad.getLogoAsistente().getCodigo() : null));
		rConfiguracionEntidad.setLogoGestor(restApiService.getReferenciaFichero(
				idEntidad.getLogoGestor() != null ? idEntidad.getLogoGestor().getCodigo() : null));
		rConfiguracionEntidad.setCss(restApiService
				.getReferenciaFichero(idEntidad.getCss() != null ? idEntidad.getCss().getCodigo() : null));
		rConfiguracionEntidad.setEmail(idEntidad.getEmail());
		rConfiguracionEntidad.setContactoHTML(AdapterUtils.generarLiteral(idEntidad.getPie()));
		rConfiguracionEntidad.setUrlCarpeta(AdapterUtils.generarLiteral(idEntidad.getUrlCarpetaCiudadana()));
		rConfiguracionEntidad.setAyudaTelefono(idEntidad.getTelefono());
		rConfiguracionEntidad.setAyudaUrl(idEntidad.getUrlSoporte());
		rConfiguracionEntidad.setPlugins(
				AdapterUtils.crearPlugins(restApiService.listPlugin(TypeAmbito.ENTIDAD, idEntidad.getCodigo(), null)));
		rConfiguracionEntidad.setAyudaFormulario(generaFormularios(formSoporte));
		rConfiguracionEntidad.setAyudaEmail(idEntidad.isEmailHabilitado());
		rConfiguracionEntidad.setDescripcion(AdapterUtils.generarLiteral(idEntidad.getNombre()));
		rConfiguracionEntidad.setDiasPreregistro(idEntidad.getDiasPreregistro());
		rConfiguracionEntidad.setInfoLopdHTML(AdapterUtils.generarLiteral(idEntidad.getLopd()));

		rConfiguracionEntidad.setMapaWeb(AdapterUtils.generarLiteral(idEntidad.getMapaWeb()));
		rConfiguracionEntidad.setAvisoLegal(AdapterUtils.generarLiteral(idEntidad.getAvisoLegal()));
		rConfiguracionEntidad.setRss(AdapterUtils.generarLiteral(idEntidad.getRss()));
		rConfiguracionEntidad.setUrlFacebook(idEntidad.getUrlFacebook());
		rConfiguracionEntidad.setUrlInstagram(idEntidad.getUrlInstagram());
		rConfiguracionEntidad.setUrlTwitter(idEntidad.getUrlTwitter());
		rConfiguracionEntidad.setUrlYoutube(idEntidad.getUrlYoutube());

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
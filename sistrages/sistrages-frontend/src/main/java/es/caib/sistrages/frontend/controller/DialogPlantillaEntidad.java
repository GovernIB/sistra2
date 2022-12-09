package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.PlantillaEntidad;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeFichero;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogPlantillaEntidad extends DialogControllerBase {

	/** Servicio. **/
	@Inject
	EntidadService entidadService;

	/** Servicio. **/
	@Inject
	ConfiguracionGlobalService configuracionGlobalService;

	/** Cod plantilla. **/
	private Long codEntidad;

	/** Lista plantilla formateador. **/
	private List<PlantillaEntidad> listaPlantillaEntidad;

	/** Lista de idiomas. **/
	private List<String> idiomas;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {

		final String iIdiomas = configuracionGlobalService.getConfiguracionGlobal("sistramit.idiomas").getValor();
		setIdiomas(UtilTraducciones.getIdiomas(iIdiomas));

		cargarDatos();

	}

	private void cargarDatos() {
		listaPlantillaEntidad = entidadService.getListaPlantillasEmailFin(codEntidad);

		if (listaPlantillaEntidad.isEmpty()) {
			for (final String idioma : idiomas) {
				listaPlantillaEntidad.add(new PlantillaEntidad(idioma, TypeFichero.FIN_REGISTRO));
			}
		} else {
			final List<String> idiomasPlantilla = new ArrayList<>();
			for (final PlantillaEntidad plantilla : listaPlantillaEntidad) {
				idiomasPlantilla.add(plantilla.getIdioma());
			}
			for (final String idioma : idiomas) {
				if (!idiomasPlantilla.contains(idioma)) {
					listaPlantillaEntidad.add(new PlantillaEntidad(idioma, TypeFichero.FIN_REGISTRO));
				}
			}
		}

		if (!listaPlantillaEntidad.isEmpty()) {
			Collections.sort(listaPlantillaEntidad, (o1, o2) -> o1.getIdioma().compareTo(o2.getIdioma()));
		}
	}

	public boolean requerido(final String pidioma) {
		return idiomas.contains(pidioma);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		// result.setResult(selec);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void descargaFichero(final Fichero fichero) {
		if (fichero != null && fichero.getCodigo() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getCodigo());
		}
	}

	public void gestionFichero(final PlantillaEntidad pPlantilla) {
		// Muestra dialogo
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_PLANTILLA_ENTIDAD, pPlantilla);

		final Map<String, String> params = new HashMap<>();
		// params.put(TypeParametroVentana.ID.toString(), String.valueOf(codEntidad));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.PLANTILLA_ENTIDAD.toString());
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);
	}

	public String getCampoIdioma(final PlantillaEntidad pPlantilla) {
		return pPlantilla.getFichero() != null ? pPlantilla.getFichero().getNombre() : "";
	}

	/**
	 * Retorno dialogo fichero.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoFichero(final SelectEvent event) {
		cargarDatos();
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the listaPlantillaFormateador
	 */
	public final List<PlantillaEntidad> getListaPlantillaEntidad() {
		return listaPlantillaEntidad;
	}

	/**
	 * @param listaPlantillaFormateador the listaPlantillaFormateador to set
	 */
	public final void setListaPlantillaEntidad(final List<PlantillaEntidad> listaPlantillaFormateador) {
		this.listaPlantillaEntidad = listaPlantillaFormateador;
	}

	/**
	 * @return the idiomas
	 */
	public final List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public final void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

}

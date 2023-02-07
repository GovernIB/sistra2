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
import es.caib.sistrages.core.api.model.PlantillaFormateador;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
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
public class DialogPlantillaFormateador extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private FormateadorFormularioService fmtService;

	/** Servicio. **/
	@Inject
	ConfiguracionGlobalService configuracionGlobalService;

	/** Cod plantilla. **/
	private Long codPlantilla;

	/** Lista plantilla formateador. **/
	private List<PlantillaFormateador> listaPlantillaFormateador;

	/** Lista de idiomas. **/
	private List<String> idiomas;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	public void init() {

		final String iIdiomas = configuracionGlobalService.getConfiguracionGlobal("sistramit.idiomas").getValor();
		setIdiomas(UtilTraducciones.getIdiomas(iIdiomas));

		cargarDatos();

	}

	private void cargarDatos() {
		listaPlantillaFormateador = fmtService.getListaPlantillasFormateador(codPlantilla);

		if (listaPlantillaFormateador.isEmpty()) {
			for (final String idioma : idiomas) {
				listaPlantillaFormateador.add(new PlantillaFormateador(idioma));
			}
		} else {
			final List<String> idiomasPlantilla = new ArrayList<>();
			for (final PlantillaFormateador plantilla : listaPlantillaFormateador) {
				idiomasPlantilla.add(plantilla.getIdioma());
			}
			for (final String idioma : idiomas) {
				if (!idiomasPlantilla.contains(idioma)) {
					listaPlantillaFormateador.add(new PlantillaFormateador(idioma));
				}
			}
		}

		if (!listaPlantillaFormateador.isEmpty()) {
			Collections.sort(listaPlantillaFormateador, (o1, o2) -> o1.getIdioma().compareTo(o2.getIdioma()));
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

	public void gestionFichero(final PlantillaFormateador pPlantilla) {
		// Muestra dialogo
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_PLANTILLA_FORMATEADOR, pPlantilla);

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(codPlantilla));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.PLANTILLA_FORMATEADOR.toString());
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);
	}

	public String getCampoIdioma(final PlantillaFormateador pPlantilla) {
		return pPlantilla.getFichero() != null ? pPlantilla.getFichero().getNombre() : "";
	}

	/**
	 * Retorno dialogo fichero.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoFichero(final SelectEvent event) {
		cargarDatos();
	}

	/**
	 * @return the codPlantilla
	 */
	public final Long getCodPlantilla() {
		return codPlantilla;
	}

	/**
	 * @param codPlantilla
	 *            the codPlantilla to set
	 */
	public final void setCodPlantilla(final Long codPlantilla) {
		this.codPlantilla = codPlantilla;
	}

	/**
	 * @return the listaPlantillaFormateador
	 */
	public final List<PlantillaFormateador> getListaPlantillaFormateador() {
		return listaPlantillaFormateador;
	}

	/**
	 * @param listaPlantillaFormateador
	 *            the listaPlantillaFormateador to set
	 */
	public final void setListaPlantillaFormateador(final List<PlantillaFormateador> listaPlantillaFormateador) {
		this.listaPlantillaFormateador = listaPlantillaFormateador;
	}

	/**
	 * @return the idiomas
	 */
	public final List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas
	 *            the idiomas to set
	 */
	public final void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}

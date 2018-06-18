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
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeCampoFichero;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogPlantillaIdiomaFormulario extends DialogControllerBase {

	@Inject
	FormularioInternoService formIntService;

	private Long codPlantilla;

	private List<PlantillaIdiomaFormulario> listaPlantillaIdioma;

	private List<String> idiomas;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			setIdiomas((List<String>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO));
		}

		cargarDatos();

	}

	private void cargarDatos() {
		listaPlantillaIdioma = formIntService.getListaPlantillaIdiomaFormulario(codPlantilla);

		if (listaPlantillaIdioma.isEmpty()) {
			for (final String idioma : idiomas) {
				listaPlantillaIdioma.add(new PlantillaIdiomaFormulario(idioma));
			}
		} else {
			final List<String> idiomasPlantilla = new ArrayList<>();
			for (final PlantillaIdiomaFormulario plantilla : listaPlantillaIdioma) {
				idiomasPlantilla.add(plantilla.getIdioma());
			}
			for (final String idioma : idiomas) {
				if (!idiomasPlantilla.contains(idioma)) {
					listaPlantillaIdioma.add(new PlantillaIdiomaFormulario(idioma));
				}
			}
		}

		if (!listaPlantillaIdioma.isEmpty()) {
			Collections.sort(listaPlantillaIdioma, (o1, o2) -> o1.getIdioma().compareTo(o2.getIdioma()));
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
		if (fichero != null && fichero.getId() != null) {
			UtilJSF.redirectJsfPage(Constantes.DESCARGA_FICHEROS_URL + "?id=" + fichero.getId());
		}
	}

	public void gestionFichero(final PlantillaIdiomaFormulario pPlantilla) {
		// Muestra dialogo
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
		mochilaDatos.put(Constantes.CLAVE_MOCHILA_PLTIDIOMAFORM, pPlantilla);

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(codPlantilla));
		params.put(TypeParametroVentana.CAMPO_FICHERO.toString(), TypeCampoFichero.PLANTILLA_IDIOMA_FORM.toString());
		UtilJSF.openDialog(DialogFichero.class, TypeModoAcceso.EDICION, params, true, 750, 350);
	}

	public String getCampoIdioma(final PlantillaIdiomaFormulario pPlantilla) {
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

	public List<PlantillaIdiomaFormulario> getListaPlantillaIdioma() {
		return listaPlantillaIdioma;
	}

	public void setListaPlantillaIdioma(final List<PlantillaIdiomaFormulario> listaPlantillaIdioma) {
		this.listaPlantillaIdioma = listaPlantillaIdioma;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public Long getCodPlantilla() {
		return codPlantilla;
	}

	public void setCodPlantilla(final Long codigo) {
		this.codPlantilla = codigo;
	}

}

package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogTraduccion extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	// @Inject
	// private EntidadService entidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private List<Traduccion> data;

	/**
	 * Idiomas obligatorios.
	 */
	private List<String> idiomasObligatorios;

	/**
	 * Inicialización.
	 */
	public void init() {

		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new ArrayList<>();
		} else {
			data = new ArrayList<>();
			data.add(new Traduccion("es", "Traduccion espanyol"));
			data.add(new Traduccion("ca", "Traduccion catalán"));
			data.add(new Traduccion("en", "Traduccion inglés"));
		}

		if (idiomasObligatorios == null) {
			// Si no tiene los idiomas obligatorios, damos por hecho
			// que todos los idiomas que hay de datos son obligatorios.
			idiomasObligatorios = new ArrayList<>();
			for (final Traduccion dato : data) {
				idiomasObligatorios.add(dato.getIdioma());
			}
		} else {
			for (final String idiomaObligatorio : idiomasObligatorios) {

				boolean noEncontrado = true;
				for (final Traduccion dato : data) {
					if (idiomaObligatorio.equals(dato.getIdioma())) {
						noEncontrado = false;
					}
				}

				if (noEncontrado) {
					data.add(new Traduccion(idiomaObligatorio, ""));
				}
			}
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
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

	/**
	 * ¿Es obligatorio el idioma? Es para marcarlo como obligatorio o no.
	 *
	 * @param idioma
	 */
	public boolean isObligatorio(final String idioma) {
		boolean retorno;
		if (idioma == null) {
			retorno = false;
		} else if (idiomasObligatorios.contains(idioma)) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public List<Traduccion> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(final List<Traduccion> data) {
		this.data = data;
	}

	/**
	 * @return the idiomasObligatorios
	 */
	public List<String> getIdiomasObligatorios() {
		return idiomasObligatorios;
	}

	/**
	 * @param idiomasObligatorios
	 *            the idiomasObligatorios to set
	 */
	public void setIdiomasObligatorios(final List<String> idiomasObligatorios) {
		this.idiomasObligatorios = idiomasObligatorios;
	}
}

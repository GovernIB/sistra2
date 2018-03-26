package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Formulario;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;

/**
 * Opción del arbol versión del trámite.
 *
 * @author Indra
 *
 */
public class OpcionArbol {

	/** Texto. **/
	private String name;

	/** Url. **/
	private String url;

	/** Trámite paso. **/
	private TramitePaso tramitePaso;

	/** Formulario. **/
	private Formulario formulario;

	/** Documento. **/
	private Documento documento;

	/** Tasa. **/
	private Tasa tasa;

	/**
	 * Constructor.
	 *
	 * @param name
	 */
	public OpcionArbol(final String name) {
		super();
		this.name = name;
	}

	/**
	 * Constructor con url.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final String url) {
		super();
		this.name = nombre;
		this.url = url;
	}

	/**
	 * Constructor con tramite paso.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final String url, final TramitePaso tramitePaso) {
		super();
		this.name = nombre;
		this.url = url;
		this.tramitePaso = tramitePaso;
	}

	/**
	 * Constructor con formulario.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final String url, final Formulario formulario,
			final TramitePaso tramitePaso) {
		super();
		this.name = nombre;
		this.url = url;
		this.formulario = formulario;
		this.tramitePaso = tramitePaso;
	}

	/**
	 * Constructor con documento.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final String url, final Documento documento,
			final TramitePaso tramitePaso) {
		super();
		this.name = nombre;
		this.url = url;
		this.documento = documento;
		this.tramitePaso = tramitePaso;
	}

	/**
	 * Constructor con tasa.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final String url, final Tasa tasa, final TramitePaso tramitePaso) {
		super();
		this.name = nombre;
		this.url = url;
		this.tasa = tasa;
		this.tramitePaso = tramitePaso;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the tramitePaso
	 */
	public TramitePaso getTramitePaso() {
		return tramitePaso;
	}

	/**
	 * @param tramitePaso
	 *            the tramitePaso to set
	 */
	public void setTramitePaso(final TramitePaso tramitePaso) {
		this.tramitePaso = tramitePaso;
	}

	/**
	 * @return the formulario
	 */
	public Formulario getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario
	 *            the formulario to set
	 */
	public void setFormulario(final Formulario formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the documento
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(final Documento documento) {
		this.documento = documento;
	}

	/**
	 * @return the tasa
	 */
	public Tasa getTasa() {
		return tasa;
	}

	/**
	 * @param tasa
	 *            the tasa to set
	 */
	public void setTasa(final Tasa tasa) {
		this.tasa = tasa;
	}

	@Override
	public String toString() {
		return name;
	}

}

package es.caib.sistrages.frontend.model;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

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
	private FormularioTramite formulario;

	/** Documento. **/
	private Documento documento;

	/** Tasa. **/
	private Tasa tasa;

	/** Tipo. **/
	private TypeObjetoFormulario tipo;

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
	 * Constructor con url.
	 *
	 * @param nombre
	 * @param url
	 */
	public OpcionArbol(final String nombre, final TypeObjetoFormulario tipo) {
		super();
		this.name = nombre;
		this.setTipo(tipo);
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
	public OpcionArbol(final String nombre, final String url, final FormularioTramite formulario,
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
	 * Tiene icono, de momento, si tiene tipo, es que tiene icono.
	 */
	public boolean tieneIcono() {
		boolean tiene;
		if (this.tipo == null) {
			tiene = false;
		} else {
			tiene = true;
		}
		return tiene;
	}

	/**
	 * Devuelve la ruta del icono.
	 *
	 * @return
	 */
	public String srcIcono() {
		String src = "";
		if (tipo != null) {
			switch (tipo) {
			case CAMPO_TEXTO:
				src = "fa fa-pencil-square-o";
				break;
			case SELECTOR:
				src = "fa fa-caret-square-o-down";
				break;
			case CHECKBOX:
				src = "fa fa-check-square";
				break;
			case CAMPO_OCULTO:
				src = "fa fa-eye-slash";
				break;
			case CAPTCHA:
			case ETIQUETA:
			case IMAGEN:
			case LINEA:
			case LISTA_ELEMENTOS:
			case PAGINA:
			case SECCION:
			default:
				break;
			}
		}
		return src;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
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
	 * @param url the url to set
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
	 * @param tramitePaso the tramitePaso to set
	 */
	public void setTramitePaso(final TramitePaso tramitePaso) {
		this.tramitePaso = tramitePaso;
	}

	/**
	 * @return the formulario
	 */
	public FormularioTramite getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(final FormularioTramite formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the documento
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
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
	 * @param tasa the tasa to set
	 */
	public void setTasa(final Tasa tasa) {
		this.tasa = tasa;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the tipo
	 */
	public TypeObjetoFormulario getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(final TypeObjetoFormulario tipo) {
		this.tipo = tipo;
	}

}

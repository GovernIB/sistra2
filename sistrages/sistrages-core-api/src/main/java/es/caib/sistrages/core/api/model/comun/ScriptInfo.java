package es.caib.sistrages.core.api.model.comun;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.model.types.TypeScript;
import es.caib.sistrages.core.api.model.types.TypeScriptFlujo;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;

/**
 * La clase Script Info de apoyo para descargar.
 */

public class ScriptInfo extends ModelApi {

	public enum TYPE_COMPONENTE {
		PAGINA, FORMULARI, TEXT, TEXT_OCULTO, SELECTOR, CHECKBOX
	}

	/** Serial version UID. **/
	private static final long serialVersionUID = 1L;

	/** Codigo script. */
	private Long codigoScript;

	/** Tipo Script. **/
	private TypeScript tipoScript;

	/** Paso. **/
	private TypePaso paso;

	/** Documento. **/
	private Documento documento;

	/** Tasa. **/
	private Tasa tasa;

	/** Formulario. **/
	private FormularioTramite formulario;

	/** Componente. **/
	private TYPE_COMPONENTE componente;

	/** Componente identificador. **/
	private String idComponente;

	/**
	 * Genera el resumen del elemento que va en la cabecera.
	 *
	 * @return
	 */
	public String getResumenTXT() {
		final StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(" ******************** ");
		if (paso == null) {
			sBuilder.append(" Versió ");
		} else {
			sBuilder.append(" Pas:" + paso);
		}

		if (documento != null) {
			sBuilder.append(" Doc:" + documento.getIdentificador());
		} else if (tasa != null) {
			sBuilder.append(" Tasa:" + tasa.getIdentificador());
		} else if (formulario != null) {
			sBuilder.append(" Form:" + formulario.getIdentificador());
		}

		if (componente != null) {
			sBuilder.append(" Componente(" + componente + "):" + idComponente);
		}
		sBuilder.append(" Script:" + tipoScript);
		sBuilder.append(" ******************** ");
		return sBuilder.toString();
	}

	/** Revisa si también pertenece a un elemento. **/
	public String getElemento() {
		String elemento;
		if (documento != null) {
			elemento = "Document: " + documento.getIdentificador();
		} else if (tasa != null) {
			elemento = "Tasa:" + tasa.getIdentificador();
		} else if (formulario != null) {
			elemento = "Formulari:" + formulario.getIdentificador();
		} else {
			elemento = " ";
		}
		return elemento;
	}

	/** Constructor. **/
	public ScriptInfo(final Long codigoScript, final TypePaso tipoPaso, final TypeScriptFlujo tipoScript) {
		this.codigoScript = codigoScript;
		this.paso = tipoPaso;
		this.tipoScript = tipoScript;
	}

	/** Constructor. **/
	public ScriptInfo(final Long codigoScript, final TypePaso tipoPaso, final TypeScriptFlujo tipoScript,
			final Documento documento) {
		this.codigoScript = codigoScript;
		this.paso = tipoPaso;
		this.tipoScript = tipoScript;
		this.documento = documento;
	}

	/** Constructor. **/
	public ScriptInfo(final Long codigoScript, final TypePaso tipoPaso, final TypeScriptFlujo tipoScript,
			final Tasa tasa) {
		this.codigoScript = codigoScript;
		this.paso = tipoPaso;
		this.tipoScript = tipoScript;
		this.setTasa(tasa);
	}

	/** Constructor. **/
	public ScriptInfo(final Long codigoScript, final TypePaso tipoPaso, final TypeScriptFlujo tipoScript,
			final FormularioTramite formulario) {
		this.codigoScript = codigoScript;
		this.paso = tipoPaso;
		this.tipoScript = tipoScript;
		this.formulario = formulario;
	}

	/** Constructor cuando es un componente dentro de un formulario. **/
	public ScriptInfo(final Long codigoScript, final TypeScriptFormulario script, final FormularioTramite formulario,
			final TYPE_COMPONENTE tipoComponente, final String identificador) {
		this.codigoScript = codigoScript;
		this.paso = TypePaso.RELLENAR; // Siempre son del paso de rellenar
		this.tipoScript = script;
		this.formulario = formulario;
		this.componente = tipoComponente;
		this.idComponente = identificador;
	}

	/**
	 * Obtiene el tipo que toca.
	 *
	 * @param tipo
	 * @return
	 */
	public static TYPE_COMPONENTE getEnum(final String tipo) {
		TYPE_COMPONENTE typeComponent;
		switch (tipo) {
		case "SE":
			typeComponent = TYPE_COMPONENTE.SELECTOR;
			break;
		case "CT":
			typeComponent = TYPE_COMPONENTE.TEXT;
			break;
		case "CK":
			typeComponent = TYPE_COMPONENTE.CHECKBOX;
			break;
		case "PG":
			typeComponent = TYPE_COMPONENTE.PAGINA;
			break;
		case "OC":
			typeComponent = TYPE_COMPONENTE.TEXT_OCULTO;
			break;
		default:
			typeComponent = null;
			break;
		}
		return typeComponent;
	}

	/**
	 * @return the codigoScript
	 */
	public final Long getCodigoScript() {
		return codigoScript;
	}

	/**
	 * @param codigoScript the codigoScript to set
	 */
	public final void setCodigoScript(final Long codigoScript) {
		this.codigoScript = codigoScript;
	}

	/**
	 * @return the componente
	 */
	public final TYPE_COMPONENTE getComponente() {
		return componente;
	}

	/**
	 * @param componente the componente to set
	 */
	public final void setComponente(final TYPE_COMPONENTE componente) {
		this.componente = componente;
	}

	/**
	 * @return the paso
	 */
	public final TypePaso getPaso() {
		return paso;
	}

	/**
	 * @param paso the paso to set
	 */
	public final void setPaso(final TypePaso paso) {
		this.paso = paso;
	}

	/**
	 * @return the documento
	 */
	public final Documento getDocumento() {
		return documento;
	}

	/**
	 * @param documento the documento to set
	 */
	public final void setDocumento(final Documento documento) {
		this.documento = documento;
	}

	/**
	 * @param tipoScript the tipoScript to set
	 */
	public final void setTipoScript(final TypeScript tipoScript) {
		this.tipoScript = tipoScript;
	}

	/**
	 * @return the tipoScript
	 */
	public final TypeScript getTipoScript() {
		return tipoScript;
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

	/**
	 * @return the formulario
	 */
	public final FormularioTramite getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public final void setFormulario(final FormularioTramite formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the idComponente
	 */
	public String getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(final String idComponente) {
		this.idComponente = idComponente;
	}

}

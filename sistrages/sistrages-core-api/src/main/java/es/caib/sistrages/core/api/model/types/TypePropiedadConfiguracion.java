package es.caib.sistrages.core.api.model.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

	// TODO V0 REVISAR NOMBRES PROPIEDADES
	/** Entorno. */
	ENTORNO("entorno"),
	/** Versión. */
	VERSION("sistra2.version"),
	/** Versión.patch */
	VERSION_PATCH("sistra2.version.patch"),
	/** Url asistente. */
	SISTRAMIT_URL("sistramit.url"),
	/** Prefijo plugin. */
	PLUGINS_PREFIJO("plugins.prefix"),
	/** Tinymce botón code activo. **/
	TINYMCE_CODE("tinymce.code"),
	/** Sistrages ayuda externa. */
	SISTRAGES_AYUDA_PATH("ayuda.sistrages.path"),
	/** Sistrages habilitar modo entrega. */
	SISTRAGES_MODOENTREGA_HABILITAR("sistrages.modoEntrega.habilitar"),
	SISTRAGES_CONVERTIR_PDF("sistrages.mostrarConvertirPDF"),
	/** Sistramit rest password. **/
	SISTRAMIT_REST_PWD("sistramit.rest.pwd"),
	/** Sistramit rest usuario. **/
	SISTRAMIT_REST_USER("sistramit.rest.user"),
	/** Sistramit rest url. **/
	SISTRAMIT_REST_URL("sistramit.rest.url"),
	/** Sistrahelp view url. **/
	SISTRAHELP_VIEW_URL("sistrahelp.view.url"),
	/** Sistramit rest url. **/
	ANEXO_TAMANYO_MAX_INDIVIDUAL("sistramit.anexos.tamanyoMaximoIndividual"),
	/** Anexo extensiones permitidas **/
	ANEXOS_EXTENSIONES_PERMITIDAS("sistramit.anexos.extensionesPermitidas"),
	/** Sistramit rest password. **/
	SISTRAMIT_REST_APIEXTERNA_PWD("sistramit.rest.externa.pwd"),
	/** Sistramit rest usuario. **/
	SISTRAMIT_REST_APIEXTERNA_USER("sistramit.rest.externa.user"),
	/** Sistramit rest url. **/
	SISTRAMIT_REST_APIEXTERNA_URL("sistramit.rest.externa.url"),
	/** Motor de scripting. */
	SCRIPT_ENGINE("scriptEngine"),
	/** Depurar los datos mostrando los logs de xhtml y java **/
	DEBUG_MOSTRAR("debug.activo"),
	/** Desactivar el codeMirror **/
	CODEMIRROR_DESACTIVAR("codemirror.desactivar");
	;

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueStr
	 *                     Valor como string.
	 */
	private TypePropiedadConfiguracion(final String valueStr) {
		stringValue = valueStr;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *                 string
	 * @return TypeSiNo
	 */
	public static TypePropiedadConfiguracion fromString(final String text) {
		TypePropiedadConfiguracion respuesta = null;
		if (text != null) {
			for (final TypePropiedadConfiguracion b : TypePropiedadConfiguracion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}

package es.caib.sistrahelp.core.api.model.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

	// TODO REVISAR NOMBRES PROPIEDADES
	PATH_PROPERTIES("es.caib.sistrahelp.properties.path"),
	/** Entorno. */
	ENTORNO("entorno"),
	/** Filtro inicial. */
	FILTRO_INICIAL("filtroInicial"),
	/** Hora desde. */
	HORA_DESDE("horaDesde"),
	/** Minutos refresco. */
	MINUTOS_REFRESCO("minutosRefresco"),
	/** Permite personalizar los umbrales de aviso en CM ( true / false ) */
	PERMITIR_PERSONALIZAR_UMBRALES_CM("permitirPersonalizarUmbralesCM"),
	/** Porcentajes de los umbrales que separan los valores de aviso "Normal", "Atención" y "Revisar" en el cuadro de mando */
	/** 0% >= "Normal" < [umbralNormalAtencion]% >= "Atencion" < [umbralAtencionRevisar]% < "Revisar" >= 100% */
	UMBRAL_NORMAL_ATENCION("umbralNormalAtencion"),
	UMBRAL_ATENCION_REVISAR("umbralAtencionRevisar"),
	/** STG Url. */
	SISTRAGES_URL("sistrages.rest.url"),
	/** STG Url. */
	SISTRAGES_VIEW_URL("sistrages.view.url"),
	/** STG Url. */
	SISTRAHELP_VIEW_URL("sistrahelp.view.url"),
	/** STG Url. */
	SISTRAGES_USR("sistrages.rest.user"),
	/** Prefijo plugin. */
	PLUGINS_PREFIJO("plugins.prefix"),
	/** STG Url. */
	SISTRAGES_PWD("sistrages.rest.pwd"),
	SISTRAGES_AYUDA_PATH("ayuda.sistrahelp.path"),
	/**
	 * Tiempo (dias) tras su fecha de purgado tras el cual seran definitivamente
	 * borrados los purgados.
	 **/
	PURGA_PURGADOS("sistramit.purga.purgados"),

	PATH_FICHEROS_EXTERNOS("ficherosExternos.path"),

	SISTRAMIT_URL("sistramit.rest.url"),
	/** STG Url. */
	SISTRAMIT_USR("sistramit.rest.user"),
	/** STG Url. */
	SISTRAMIT_PWD("sistramit.rest.pwd"),
	/** PIB Url. */
	PAYMENTIB_VIEW_URL("paymentib.view.url"),
	SISTRAGES_MODOENTREGA_HABILITAR("sistrages.modoEntrega.habilitar"),
	/** Motor de scripting. */
	SCRIPT_ENGINE("scriptEngine"),
	ALERT_MSG_TEMPLATE_ES("template.evento.es"),
	ALERT_MSG_TEMPLATE_CA("template.evento.ca");


	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueStr Valor como string.
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
	 * @param text string
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

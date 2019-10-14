package es.caib.sistrages.core.api.model.types;

/**
 * Indica tipo documental.
 *
 * @author Indra
 *
 */
public enum TypeTipoDocumental {

	/** TD01_RESOLUCION. */
	TD01_RESOLUCION("TD01"),
	/** TD02 ACUERDO. */
	TD02_ACUERDO("TD02"),
	/** TD03 CONTRATO. */
	TD03_CONTRATO("TD03"),
	/** TD04 CONVENIO. */
	TD04_CONVENIO("TD04"),
	/** TD05 DECLARACIÓN. */
	TD05_DECLARACION("TD05"),
	/** TD06 COMUNICACIÓN. */
	TD06_COMUNICACION("TD06"),
	/** TD07 NOTIFICACIÓN. */
	TD07_NOTIFICACION("TD07"),
	/** TD08 PUBLICACIÓN. */
	TD08_PUBLICACION("TD08"),
	/** TD09 ACUSE DE RECIBO. */
	TD09_ACUSE_DE_RECIBO("TD09"),
	/** TD10 ACTA. */
	TD10_ACTA("TD10"),
	/** TD11 CERTIFICADO. */
	TD11_CERTIFICADO("TD11"),
	/** TD12 DILIGENCIA. */
	TD12_DILIGENCIA("TD12"),
	/** TD13 INFORME. */
	TD13_INFORME("TD13"),
	/** TD14 SOLICITUD. */
	TD14_SOLICITUD("TD14"),
	/** TD15 DENUNCIA. */
	TD15_DENUNCIA("TD15"),
	/** TD16 ALEGACIÓN. */
	TD16_ALEGACION("TD16"),
	/** TD17 RECURSOS. */
	TD17_RECURSOS("TD17"),
	/** TD18 COMUNICACIÓN CIUDADANO. */
	TD18_COMUNICACION_CIUDADANO("TD18"),
	/** TD19 FACTURA. */
	TD19_FACTURA("TD19"),
	/** TD20 OTROS INCAUTADOS. */
	TD20_OTROS_INCAUTADOS("TD20"),
	/** TD51 LEY. */
	TD51_LEY("TD51"),
	/** TD52 MOCIÓN. */
	TD52_MOCION("TD52"),
	/** TD53 INSTRUCCIÓN. */
	TD53_INSTRUCCION("TD53"),
	/** TD54 CONVOCATORIA. */
	TD54_CONVOCATORIA("TD54"),
	/** TD55 ORDEN DEL DÍA. */
	TD55_ORDEN_DEL_DÍA("TD55"),
	/** TD56 INFORME DE PONENCIA. */
	TD56_INFORME_DE_PONENCIA("TD56"),
	/** TD57 DICTAMEN DE COMISIÓN. */
	TD57_DICTAMEN_DE_COMISION("TD57"),
	/** TD58 INICIATIVA LEGISLATIVA. */
	TD58_INICIATIVA_LEGISLATIVA("TD58"),
	/** TD59 PREGUNTA. */
	TD59_PREGUNTA("TD59"),
	/** TD60 INTERPELACIÓN. */
	TD60_INTERPELACION("TD60"),
	/** TD61 RESPUESTA. */
	TD61_RESPUESTA("TD61"),
	/** TD62 PROPOSICIÓN NO DE LEY. */
	TD62_PROPOSICION_NO_DE_LEY("TD62"),
	/** TD63 ENMIENDA. */
	TD63_ENMIENDA("TD63"),
	/** TD64 PROPUESTA DE RESOLUCIÓN. */
	TD64_PROPUESTA_DE_RESOLUCION("TD64"),
	/** TD65 COMPARECENCIA. */
	TD65_COMPARECENCIA("TD65"),
	/** TD66 SOLICITUD DE INFORMACIÓN. */
	TD66_SOLICITUD_DE_INFORMACION("TD66"),
	/** TD67 ESCRITO. */
	TD67_ESCRITO("TD67"),
	/** TD68 INICIATIVA LEGISLATIVA. */
	TD68_INICIATIVA_LEGISLATIVA("TD68"),
	/** TD69 PETICIÓN. */
	TD69_PETICION("TD69"),
	/** TD99 OTROS. */
	TD99_OTROS("TD99");

	/**
	 * Valor como string.
	 */
	private final String stringValueTypeTipoDocumental;

	/**
	 * Constructor.
	 *
	 * @param value Valor como string.
	 */
	private TypeTipoDocumental(final String value) {
		stringValueTypeTipoDocumental = value;
	}

	@Override
	public String toString() {
		return stringValueTypeTipoDocumental;
	}

	/**
	 * Método para From string de la clase TypeTipoDocumental.
	 *
	 * @param text Parámetro text
	 * @return el type estado paso
	 */
	public static TypeTipoDocumental fromString(final String text) {
		TypeTipoDocumental respuesta = null;
		if (text != null) {
			for (final TypeTipoDocumental b : TypeTipoDocumental.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
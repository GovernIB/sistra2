package es.caib.sistramit.core.service.component.formulario.interno.formateadores;

import java.util.List;

import es.caib.sistrages.rest.api.interna.RFormularioInterno;

/**
 * Formateador PDF para formularios.
 *
 * @author Indra
 *
 */
public interface FormateadorPdfFormulario {

	/**
	 * Formatea a PDF.
	 *
	 * @param xml
	 *                                  XML datos formulario
	 * @param paginasRellenadas
	 *                                  Páginas rellenadas
	 * @param defFormInterno
	 *                                  Definición formulario interno
	 * @param plantilla
	 *                                  plantilla a usar
	 * @param idioma
	 *                                  idioma
	 * @param tituloProcedimiento
	 *                                  titulo del procedimiento
	 * @param tituloTramite
	 *                                  titulo del trámite
	 * @param siaProcedimiento
	 *                                  sia del procedimiento
	 * @param codigoDir3Responsable
	 *                                  código dir3 responsable
	 * @return PDF generado
	 */
	byte[] formatear(byte[] xml, List<String> paginasRellenadas, byte[] plantilla, String idioma,
			RFormularioInterno defFormInterno, String tituloProcedimiento, String tituloTramite,
			String siaProcedimiento, String codigoDir3Responsable);

}

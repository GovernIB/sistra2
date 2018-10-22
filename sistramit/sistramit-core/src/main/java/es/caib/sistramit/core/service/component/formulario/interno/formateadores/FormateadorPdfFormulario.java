package es.caib.sistramit.core.service.component.formulario.interno.formateadores;

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
     *            XML datos formulario
     * @param defFormInterno
     *            Definición formulario interno
     * @param plantilla
     *            plantilla a usar
     * @param idioma
     *            idioma
     * @return PDF generado
     */
    byte[] formatear(byte[] xml, byte[] plantilla, String idioma,
            RFormularioInterno defFormInterno);

}

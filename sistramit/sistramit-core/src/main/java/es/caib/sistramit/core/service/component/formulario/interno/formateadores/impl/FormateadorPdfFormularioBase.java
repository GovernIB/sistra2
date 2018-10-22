package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.util.HashMap;

import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;

/**
 * Formateador PDF para formularios.
 *
 * @author Indra
 *
 */
public class FormateadorPdfFormularioBase implements FormateadorPdfFormulario {

	/**
	 * XPATH al numero de registro/envio/preregistro/preenvio asociado al documento
	 * a partir de los usos
	 */
	public static final String XPATH_REGISTRONUMERO = "REGISTRO.NUMERO";
	/**
	 * XPATH a la fecha de registro/envio/preregistro/preenvio asociado al documento
	 * a partir de los usos
	 */
	public static final String XPATH_FECHAREGISTRO = "REGISTRO.FECHA";
	/**
	 * XPATH al digito de control
	 */
	public static final String XPATH_DIGITOCONTROL = "REGISTRO.DC";

	public static final String CODIGO_LISTAS = "[CODIGO]";
	public static final String INDICE_LISTAS = "indice";

	@Override
	public byte[] formatear(final byte[] ixml, final byte[] plantilla, final String idioma,
			final RFormularioInterno defFormInterno) {

		final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);

		// Creamos Map de valores
		final HashMap datos = new HashMap();

		/*
		 * // Cargamos datos desde xml for (final Iterator<ValorCampo> it =
		 * xml.getValores().iterator(); it.hasNext();) {
		 * 
		 * final Object dat = it.next();
		 * 
		 * // Comprobamos si es un multivaluado String value = ""; String index = "";
		 * String refCampo = ""; boolean primer = true, indexed = false; if (dat
		 * instanceof List) { for (final Iterator it2 = ((List) dat).iterator();
		 * it2.hasNext();) { final Nodo nodo = (Nodo) it2.next();
		 * 
		 * if (!primer) { value += ", "; index += ", "; } else { refCampo =
		 * referenciaCampo(nodo.getXpath()); primer = false; }
		 * 
		 * value += nodo.getValor();
		 * 
		 * // En caso de ser lista de valores buscamos atributo de codigo if
		 * (nodo.getAtributos() != null && nodo.getAtributos().size() > 0) { indexed =
		 * true; for (final Iterator it3 = nodo.getAtributos().iterator();
		 * it3.hasNext();) { final Par atributo = (Par) it3.next(); if
		 * (atributo.getNombre().equals(INDICE_LISTAS)) { index += atributo.getValor();
		 * } } } }
		 * 
		 * datos.put(refCampo, value); if (indexed) datos.put(refCampo + CODIGO_LISTAS,
		 * index);
		 * 
		 * } else { // Valor unico final Nodo nodo = (Nodo) dat;
		 * datos.put(referenciaCampo(nodo.getXpath()), nodo.getValor());
		 * 
		 * // En caso de ser lista de valores buscamos atributo de codigo if
		 * (nodo.getAtributos() != null && nodo.getAtributos().size() > 0) { for (final
		 * Iterator it2 = nodo.getAtributos().iterator(); it2.hasNext();) { final Par
		 * atributo = (Par) it2.next(); if (atributo.getNombre().equals(INDICE_LISTAS))
		 * { datos.put(referenciaCampo(nodo.getXpath()) + CODIGO_LISTAS,
		 * atributo.getValor()); } } } }
		 * 
		 * }
		 * 
		 * // Cargamos datos iniciales (extendido por formateadores) for (final Iterator
		 * it = datosIniciales.keySet().iterator(); it.hasNext();) { final String key =
		 * (String) it.next(); datos.put(key, datosIniciales.get(key)); }
		 * 
		 * // Obtenemos usos del documento para ver si tiene usos asociados a //
		 * registro/envio/preregistro UsoRDS uso = UtilRDS.obtenerNumeroEntrada(usos);
		 * 
		 * // Si no hay entrada intentamos obtener numero salida if (uso == null) { uso
		 * = UtilRDS.obtenerNumeroSalida(usos); }
		 * 
		 * if (uso != null) { datos.put(XPATH_REGISTRONUMERO, uso.getReferencia()); if
		 * (uso.getTipoUso().startsWith("PRE")) { datos.put(XPATH_DIGITOCONTROL,
		 * StringUtil.calculaDC(uso.getReferencia())); } if (uso.getFechaSello() !=
		 * null) { datos.put(XPATH_FECHAREGISTRO,
		 * StringUtil.fechaACadena(uso.getFechaSello(), "dd/MM/yyyy HH:mm")); } }
		 */

		return null;
	}

}

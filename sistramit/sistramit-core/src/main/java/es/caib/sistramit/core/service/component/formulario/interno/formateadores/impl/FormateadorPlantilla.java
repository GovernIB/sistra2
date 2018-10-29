package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistra2.commons.pdf.PdfUtil;
import es.caib.sistra2.commons.pdf.TipoVisualizacionListado;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.api.exception.FormateadorException;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.service.component.formulario.UtilsFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;

/**
 * Formateador PDF para formularios.
 *
 * @author Indra
 *
 */
public class FormateadorPlantilla implements FormateadorPdfFormulario {

	/** Como es la visualización de listados. **/
	private TipoVisualizacionListado listadoVisualizacion;

	@Override
	public byte[] formatear(final byte[] ixml, final byte[] plantilla, final String idioma,
			final RFormularioInterno defFormInterno) {

		inicializarValores();
		try {

			final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);
			final PdfUtil pdf = new PdfUtil(plantilla);
			final Map<String, String> datos = new HashMap<>();
			if (xml != null && xml.getValores() != null) {
				for (final ValorCampo valor : xml.getValores()) {
					if (valor instanceof ValorCampoSimple) {
						datos.put(valor.getId(), ((ValorCampoSimple) valor).getValor());
					} else if (valor instanceof ValorCampoIndexado) {
						datos.put(valor.getId(), getValor((ValorCampoIndexado) valor));
					} else if (valor instanceof ValorCampoListaIndexados) {
						datos.put(valor.getId(), getValor((ValorCampoListaIndexados) valor));
					}
				}
			}
			pdf.establecerSoloImpresion();
			pdf.ponerValor(datos);
			return pdf.guardarEnMemoria(true);

		} catch (final Exception e) {
			throw new FormateadorException("Error convirtiendo el documento a bytes", e);
		}

		// final Path path = Paths.get("P:\\prueba.pdf");
		// Files.write(path, retorno);

	}

	/**
	 * Inicializa los valores
	 *
	 * @param plantilla
	 */
	private void inicializarValores() {

		listadoVisualizacion = TipoVisualizacionListado.SUFIJO_PARENTESIS;
	}

	/**
	 * Objeto propiedad para ValorCampoIndexado
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private String getValor(final ValorCampoIndexado valor) {
		if (listadoVisualizacion == TipoVisualizacionListado.SOLO_CODIGO) {
			return valor.getValor().getValor();
		} else if (listadoVisualizacion == TipoVisualizacionListado.SOLO_DESCRIPCION) {
			return valor.getValor().getDescripcion();
		} else {
			return getValorCampoIndexado(valor.getValor());
		}
	}

	/**
	 * Objeto propiedad para ValorCampoListaIndexados.
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private String getValor(final ValorCampoListaIndexados valor) {
		final ValorCampoListaIndexados valorLista = valor;
		final StringBuilder valorListaSimple = new StringBuilder("");
		final String separador = ",";
		for (final ValorIndexado valorElemento : valorLista.getValor()) {
			valorListaSimple.append(getValorCampoIndexado(valorElemento));
			valorListaSimple.append(separador);
		}

		String linea = valorListaSimple.toString().trim();
		if (linea != null && linea.endsWith(",")) {
			linea = linea.substring(0, linea.length() - 1);
		}
		return linea;
	}

	/**
	 * Valor de campo indexado.
	 *
	 * @param valor
	 * @return
	 */
	private String getValorCampoIndexado(final ValorIndexado valorElemento) {
		final StringBuilder valorListaSimple = new StringBuilder("");
		switch (listadoVisualizacion) {
		case PREFIJO_GUION:
		case PREFIJO_PARENTESIS:
			valorListaSimple.append(getCodigoVisualizado(valorElemento.getValor()));
			valorListaSimple.append(valorElemento.getDescripcion());
			break;

		case SUFIJO_GUION:
		case SUFIJO_PARENTESIS:
			valorListaSimple.append(valorElemento.getDescripcion());
			valorListaSimple.append(getCodigoVisualizado(valorElemento.getValor()));
			break;

		case SOLO_CODIGO:
		case SOLO_CODIGO_COMAS:
			valorListaSimple.append(valorElemento.getValor());
			break;
		case SOLO_DESCRIPCION_COMAS:
		case SOLO_DESCRIPCION:
			valorListaSimple.append(valorElemento.getDescripcion());
			break;
		}
		return valorListaSimple.toString();
	}

	private String getCodigoVisualizado(final String codigo) {
		final StringBuilder texto = new StringBuilder();
		if (listadoVisualizacion == TipoVisualizacionListado.PREFIJO_PARENTESIS
				|| listadoVisualizacion == TipoVisualizacionListado.SUFIJO_PARENTESIS) {
			texto.append(" (");
		} else if (listadoVisualizacion == TipoVisualizacionListado.SUFIJO_GUION) {
			texto.append(" - ");
		}

		texto.append(codigo);
		if (listadoVisualizacion == TipoVisualizacionListado.PREFIJO_PARENTESIS
				|| listadoVisualizacion == TipoVisualizacionListado.SUFIJO_PARENTESIS) {
			texto.append(") ");
		} else if (listadoVisualizacion == TipoVisualizacionListado.PREFIJO_GUION) {
			texto.append(" - ");
		}

		return texto.toString();

	}

}

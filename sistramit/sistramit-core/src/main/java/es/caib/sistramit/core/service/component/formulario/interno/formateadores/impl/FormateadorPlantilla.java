package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.util.HashMap;
import java.util.Map;

import es.caib.sistra2.commons.pdf.PdfUtil;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.api.exception.FormateadorException;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.types.TipoVisualizacionValorIndexado;
import es.caib.sistramit.core.service.util.UtilsFormulario;

/**
 * Formateador PDF para formularios.
 *
 * @author Indra
 *
 */
public class FormateadorPlantilla implements FormateadorPdfFormulario {

	/** Como es la visualización de listados. **/
	private TipoVisualizacionValorIndexado listadoVisualizacion = TipoVisualizacionValorIndexado.DESCRIPCION;

	@Override
	public byte[] formatear(final byte[] ixml, final byte[] plantilla, final String idioma,
			final RFormularioInterno defFormInterno, final String tituloProcedimiento) {

		try {

			final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);
			final PdfUtil pdf = new PdfUtil(plantilla);
			final Map<String, String> datos = new HashMap<>();
			if (xml != null && xml.getValores() != null) {
				for (final ValorCampo valor : xml.getValores()) {
					if (valor instanceof ValorCampoSimple) {
						datos.put(valor.getId(), ((ValorCampoSimple) valor).getValor());
					} else if (valor instanceof ValorCampoIndexado && ((ValorCampoIndexado) valor).getValor() != null) {

						datos.put(valor.getId(), ((ValorCampoIndexado) valor).getValor().getDescripcion());
						datos.put(valor.getId() + ".DESCRIPCION",
								((ValorCampoIndexado) valor).getValor().getDescripcion());
						datos.put(valor.getId() + ".CODIGO", ((ValorCampoIndexado) valor).getValor().getValor());

					} else if (valor instanceof ValorCampoListaIndexados) {
						datos.put(valor.getId(), getDescripcion((ValorCampoListaIndexados) valor));
						datos.put(valor.getId() + ".ELEMENTOS",
								String.valueOf(((ValorCampoListaIndexados) valor).getValor().size()));
						for (int i = 0; i < ((ValorCampoListaIndexados) valor).getValor().size(); i++) {
							datos.put(valor.getId() + "[" + (i + 1) + "].DESCRIPCION",
									((ValorCampoListaIndexados) valor).getValor().get(i).getDescripcion());
							datos.put(valor.getId() + "[" + (i + 1) + "].CODIGO",
									((ValorCampoListaIndexados) valor).getValor().get(i).getValor());
						}
					}
				}
			}
			pdf.ponerValor(datos);
			return pdf.guardarEnMemoria(true);

		} catch (final Exception e) {
			throw new FormateadorException("Error convirtiendo el documento a bytes", e);
		}

	}

	/**
	 * Obtiene la descripcion
	 *
	 * @param valor
	 * @return
	 */
	private String getDescripcion(final ValorCampoListaIndexados valor) {
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
		case DESCRIPCION_CODIGO_CON_GUION:
		case CODIGO_DESCRIPCION_CON_PARENTESIS:
			valorListaSimple.append(getCodigoVisualizado(valorElemento.getValor()));
			valorListaSimple.append(valorElemento.getDescripcion());
			break;

		case CODIGO_DESCRIPCION_CON_GUION:
		case DESCRIPCION_CODIGO_CON_PARENTESIS:
			valorListaSimple.append(valorElemento.getDescripcion());
			valorListaSimple.append(getCodigoVisualizado(valorElemento.getValor()));
			break;

		case DESCRIPCION:
			valorListaSimple.append(valorElemento.getDescripcion());
			break;
		}
		return valorListaSimple.toString();
	}

	private String getCodigoVisualizado(final String codigo) {
		final StringBuilder texto = new StringBuilder();
		if (listadoVisualizacion == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_PARENTESIS
				|| listadoVisualizacion == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS) {
			texto.append(" (");
		} else if (listadoVisualizacion == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_GUION) {
			texto.append(" - ");
		}

		texto.append(codigo);
		if (listadoVisualizacion == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_PARENTESIS
				|| listadoVisualizacion == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS) {
			texto.append(") ");
		} else if (listadoVisualizacion == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_GUION) {
			texto.append(" - ");
		}

		return texto.toString();

	}

	/**
	 * @return the listadoVisualizacion
	 */
	public final TipoVisualizacionValorIndexado getListadoVisualizacion() {
		return listadoVisualizacion;
	}

	/**
	 * @param listadoVisualizacion
	 *            the listadoVisualizacion to set
	 */
	public final void setListadoVisualizacion(final TipoVisualizacionValorIndexado listadoVisualizacion) {
		this.listadoVisualizacion = listadoVisualizacion;
	}

}

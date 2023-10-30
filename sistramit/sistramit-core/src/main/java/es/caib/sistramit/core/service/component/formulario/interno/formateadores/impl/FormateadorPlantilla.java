package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.util.*;

import es.caib.sistramit.core.api.model.formulario.*;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.pdf.PdfUtil;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistramit.core.api.exception.FormateadorException;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
import es.caib.sistramit.core.service.component.formulario.interno.utils.UtilsFormularioInterno;
import es.caib.sistramit.core.service.model.formulario.XmlFormulario;
import es.caib.sistramit.core.service.model.formulario.types.TipoVisualizacionValorIndexado;
import es.caib.sistramit.core.service.util.UtilsFormulario;
import es.caib.sistramit.core.service.util.UtilsSTG;

/**
 * Formateador PDF para formularios.
 *
 * @author Indra
 *
 */
public class FormateadorPlantilla implements FormateadorPdfFormulario {

	/** Formato presentación fechas. */
	private static String FORMATO_FECHAS_PDF = "dd/MM/yyyy";

	/** Como es la visualización de listados. **/
	private TipoVisualizacionValorIndexado listadoVisualizacion = TipoVisualizacionValorIndexado.DESCRIPCION;

	@Override
	public byte[] formatear(final byte[] ixml, final List<String> paginasRellenadas, final byte[] plantilla,
			final String idioma, final RFormularioInterno defFormInterno, final String tituloProcedimiento,
			final String tituloTramite, final String siaProcedimiento, final String codigoDir3Responsable) {

		try {

			final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);
			final PdfUtil pdf = new PdfUtil(plantilla);
			//final Map<String, String> datos = new HashMap<>();
			Map<String, String> datos = new HashMap<>();
			List<RComponente> componentes = null;
			if (defFormInterno != null && defFormInterno.getPaginas() != null) {
				componentes = UtilsFormularioInterno.devuelveListaCampos(defFormInterno);
			}
			if (xml != null && xml.getValores() != null) {
				for (final ValorCampo valor : xml.getValores()) {
					setDatos("", datos, valor, componentes);
				}
			}

			datos.put("[DEBUG]", getTodosLosDatos(datos));

			pdf.ponerValor(datos);
			return pdf.guardarEnMemoria(true);

		} catch (final Exception e) {
			throw new FormateadorException("Error convertint el document a bytes", e);
		}
	}

	private String getTodosLosDatos(Map<String, String> datos) {
		StringBuilder sb = new StringBuilder();
		Map<String, String> treeMap = new TreeMap<String, String>(datos);
		//usamos treemap para que aparezca ordenado por key
		for (Map.Entry<String, String> dato : treeMap.entrySet()) {
			sb.append(dato.getKey());
			sb.append("=");
			sb.append(dato.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}


	private void setDatos(String prefijo, Map<String, String> datos, ValorCampo valor, List<RComponente> componentes ){

		if (valor instanceof ValorCampoSimple) {
			if (isComponenteTipoFecha(componentes, valor.getId())) {
				datos.put(prefijo+valor.getId(), getConversionFecha(((ValorCampoSimple) valor).getValor()));
			} else {
				datos.put(prefijo+valor.getId(), ((ValorCampoSimple) valor).getValor());
			}
		} else if (valor instanceof ValorCampoIndexado && ((ValorCampoIndexado) valor).getValor() != null) {

			datos.put(prefijo+valor.getId(), ((ValorCampoIndexado) valor).getValor().getDescripcion());
			datos.put(prefijo+valor.getId() + ".DESCRIPCION",
					((ValorCampoIndexado) valor).getValor().getDescripcion());
			datos.put(prefijo+valor.getId() + ".CODIGO", ((ValorCampoIndexado) valor).getValor().getValor());

		} else if (valor instanceof ValorCampoListaIndexados) {
			datos.put(prefijo+valor.getId(), getDescripcion((ValorCampoListaIndexados) valor));
			datos.put(prefijo+valor.getId() + ".ELEMENTOS",
					String.valueOf(((ValorCampoListaIndexados) valor).getValor().size()));
			for (int i = 0; i < ((ValorCampoListaIndexados) valor).getValor().size(); i++) {
				datos.put(prefijo+valor.getId() + "[" + (i + 1) + "].DESCRIPCION",
						((ValorCampoListaIndexados) valor).getValor().get(i).getDescripcion());
				datos.put(prefijo+valor.getId() + "[" + (i + 1) + "].CODIGO",
						((ValorCampoListaIndexados) valor).getValor().get(i).getValor());
			}
		} else if (valor instanceof ValorCampoListaElementos) {
			datos.put(prefijo + valor.getId() + ".ELEMENTOS",
					String.valueOf(((ValorCampoListaElementos) valor).getValor().size()));
			for (int i = 0; i < ((ValorCampoListaElementos) valor).getValor().size(); i++) {
				for (final ValorCampo valorElemeto : ((ValorCampoListaElementos) valor).getValor().get(i).getElemento()) {
					//llamamos de manera recursiva a setDatos.
					setDatos(prefijo + valor.getId() + "[" + (i + 1) + "].", datos, valorElemeto, componentes);
				}
			}
		}
	}


	/**
	 * Convierte la fecha en formato YYYY-MM-DD a DD-MM-YYYY
	 *
	 * @param fecha
	 *                  Fecha en formato YYYY-MM-DD
	 * @return Fecha en formato DD-MM-YYYY
	 * @throws Exception
	 */
	private String getConversionFecha(final String fecha) {
		String valor = "";
		try {
			if (StringUtils.isNotBlank(fecha)) {
				if (ValidacionesTipo.getInstance().esFecha(fecha, "yyyy-MM-dd")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "yyyy-MM-dd");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, FORMATO_FECHAS_PDF);
				} else if (ValidacionesTipo.getInstance().esFecha(fecha, "yyyy/MM/dd")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "yyyy/MM/dd");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, FORMATO_FECHAS_PDF);
				} else if (ValidacionesTipo.getInstance().esFecha(fecha, "dd/MM/yyyy")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "dd/MM/yyyy");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, FORMATO_FECHAS_PDF);
				} else if (ValidacionesTipo.getInstance().esFecha(fecha, "dd-MM-yyyy")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "dd-MM-yyyy");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, FORMATO_FECHAS_PDF);
				} else {
					throw new FormateadorException("Data no vàlida");
				}
			}
		} catch (final ValidacionTipoException e) {
			throw new FormateadorException("Data no vàlida");
		}
		return valor;
	}

	/**
	 * Comprueba si un componente es de tipo fecha y por tanto su valor es de tipo
	 * fecha en formato String
	 *
	 * @param componente
	 * @return
	 */
	private boolean isComponenteTipoFecha(final List<RComponente> componentes, final String identificador) {
		boolean retorno = false;

		if (componentes != null) {
			for (final RComponente componente : componentes) {
				if (identificador.equals(componente.getIdentificador())) {
					retorno = UtilsSTG.traduceTipoCampo(componente.getTipo()) == TypeCampo.TEXTO && UtilsSTG
							.traduceTipoTexto(((RComponenteTextbox) componente).getTipoTexto()) == TypeTexto.FECHA;
					break;
				}
			}
		}
		return retorno;

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
	 *                                 the listadoVisualizacion to set
	 */
	public final void setListadoVisualizacion(final TipoVisualizacionValorIndexado listadoVisualizacion) {
		this.listadoVisualizacion = listadoVisualizacion;
	}

}

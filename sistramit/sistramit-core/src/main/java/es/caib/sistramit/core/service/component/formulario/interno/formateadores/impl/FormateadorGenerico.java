package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.pdfcaib.GeneradorPdf;
import es.caib.sistra2.commons.pdfcaib.model.Cabecera;
import es.caib.sistra2.commons.pdfcaib.model.CampoTexto;
import es.caib.sistra2.commons.pdfcaib.model.FormularioPdf;
import es.caib.sistra2.commons.pdfcaib.model.Linea;
import es.caib.sistra2.commons.pdfcaib.model.PersonalizacionTexto;
import es.caib.sistra2.commons.pdfcaib.model.Seccion;
import es.caib.sistra2.commons.pdfcaib.model.Texto;
import es.caib.sistra2.commons.pdfcaib.types.TypeFuente;
import es.caib.sistra2.commons.utils.ValidacionTipoException;
import es.caib.sistra2.commons.utils.ValidacionesTipo;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteAviso;
import es.caib.sistrages.rest.api.interna.RComponenteSeccion;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistramit.core.api.exception.FormateadorException;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoListaIndexados;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.types.TypeCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeTexto;
import es.caib.sistramit.core.service.component.formulario.interno.formateadores.FormateadorPdfFormulario;
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
public class FormateadorGenerico implements FormateadorPdfFormulario {

	/** Formato presentación fechas. */
	private static String FORMATO_FECHAS_PDF = "dd/MM/yyyy";

	/** Propiedad plantilla que indica url logo. **/
	public static final String PROP_LOGO_URL = "logo.url";

	/** Propiedad plantilla que indica visualización campos indexados. **/
	public static final String PROP_VISUALIZACION_VALOR_INDEXADO = "campoIndexado.visualizacion";

	/** Propiedad plantilla que indica si se muestran mensajes de aviso. **/
	public static final String PROP_MOSTRAR_AVISOS = "aviso.mostrar";

	/** Propiedad plantilla que indica si se muestra título procedimiento. */
	public static final String PROP_MOSTRAR_TITULO_PROCEDIMIENTO = "titulo.procedimiento";

	/** Visualización campos indexados. **/
	private TipoVisualizacionValorIndexado visualizacionValorIndexado;

	/** Indica si hay que mostrar los mensajes de aviso. **/
	private Boolean mostrarAviso;

	/** Url de la imagen. **/
	private String urlImagen;

	/**
	 * Si está activo, se mostrará procedimiento - titulo , sino solo el titulo.
	 **/
	private Boolean mostrarTituloConProcedimiento;

	@Override
	public byte[] formatear(final byte[] ixml, final byte[] plantilla, final String idioma,
			final RFormularioInterno defFormInterno, final String tituloProcedimiento) {

		final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);

		inicializarValores(plantilla);

		// Creamos formulario
		final FormularioPdf formularioPdf = new FormularioPdf();

		// Cabecera
		final Cabecera cabecera = new Cabecera();
		if (mostrarTituloConProcedimiento) {
			cabecera.setTitulo(tituloProcedimiento);
			cabecera.setSubtitulo(defFormInterno.getTitulo());
		} else {
			cabecera.setTitulo(tituloProcedimiento);
		}
		if (StringUtils.isNotBlank(urlImagen)) {
			try {
				byte[] arrayBytes = null;
				if (urlImagen.startsWith("http")) {
					arrayBytes = IOUtils.toByteArray(new URL(urlImagen));
				} else {
					arrayBytes = IOUtils.toByteArray(new FileInputStream(urlImagen));
				}
				cabecera.setLogoByte(arrayBytes);
			} catch (final IOException e) {
				throw new FormateadorException("Error accediendo a url logo: " + urlImagen, e);
			}
		}
		formularioPdf.setCabecera(cabecera);

		// Recorremos las paginas
		final List<Linea> lineas = new ArrayList<Linea>();
		formularioPdf.setLineas(lineas);

		for (final RPaginaFormulario pagina : defFormInterno.getPaginas()) {

			for (final RLineaComponentes rLinea : pagina.getLineas()) {

				for (final RComponente componente : rLinea.getComponentes()) {

					// Una linea para cada campo
					final Linea linea = new Linea();
					lineas.add(linea);

					// Añadir segun componente
					if (componente instanceof RComponenteSeccion) {
						// - Componente seccion
						final RComponenteSeccion componenteSeccion = (RComponenteSeccion) componente;
						linea.getObjetosLinea().add(
								new Seccion(/* componenteSeccion.getLetra() */"", componenteSeccion.getEtiqueta()));
					} else if (componente instanceof RComponenteAviso) {
						// - Componente aviso (si está activo la variable)
						if (mostrarAviso != null && mostrarAviso) {
							// Creamos seccion vacia para separar
							linea.getObjetosLinea().add(new Seccion("", ""));
							// Creamos texto aviso
							final RComponenteAviso componenteAviso = (RComponenteAviso) componente;
							final PersonalizacionTexto personalizacicionTexto = new PersonalizacionTexto(false, true,
									TypeFuente.NOTOSANS, 6);
							final Texto texto = new Texto(personalizacicionTexto, componenteAviso.getEtiqueta(), 6);
							linea.getObjetosLinea().add(texto);
						}
					} else {
						// - Campos
						if (xml.getValores() != null && componente.getIdentificador() != null) {
							anyadirDato(componente, linea, xml);
						}
					}

				}
			}

		}

		// Generar PDF
		try {
			final GeneradorPdf generadorPdf = new GeneradorPdf();
			final byte[] pdf = generadorPdf.generarPdf(formularioPdf);
			return pdf;
		} catch (final Exception e) {
			throw new FormateadorException("Error convirtiendo el documento a bytes", e);
		}

	}

	/**
	 * Inicializa los valores
	 *
	 * @param plantilla
	 */
	private void inicializarValores(final byte[] plantilla) {

		urlImagen = null;
		visualizacionValorIndexado = TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS;
		mostrarAviso = false;
		mostrarTituloConProcedimiento = false;
		if (plantilla != null) {
			try (ByteArrayInputStream bis = new ByteArrayInputStream(plantilla)) {

				final Properties propiedades = new Properties();
				propiedades.load(bis);

				urlImagen = propiedades.getProperty(PROP_LOGO_URL);

				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_VISUALIZACION_VALOR_INDEXADO))) {
					visualizacionValorIndexado = TipoVisualizacionValorIndexado
							.valueOf(propiedades.getProperty(PROP_VISUALIZACION_VALOR_INDEXADO));
				}

				mostrarAviso = Boolean.valueOf(propiedades.getProperty(PROP_MOSTRAR_AVISOS));

				mostrarTituloConProcedimiento = Boolean
						.valueOf(propiedades.getProperty(PROP_MOSTRAR_TITULO_PROCEDIMIENTO));

			} catch (final Exception e) {
				throw new FormateadorException("Error obteniendo propiedades formateador", e);
			}
		}
	}

	/**
	 * Comprueba si un componente es de tipo fecha y por tanto su valor es de tipo
	 * fecha en formato String
	 *
	 * @param componente
	 * @return
	 */
	private boolean isComponenteTipoFecha(final RComponente componente) {
		return UtilsSTG.traduceTipoCampo(componente.getTipo()) == TypeCampo.TEXTO
				&& UtilsSTG.traduceTipoTexto(((RComponenteTextbox) componente).getTipoTexto()) == TypeTexto.FECHA;
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
				final String[] formatosFecha = { "dd/MM/yyyy", "dd-MM-yyyy", "yyyy-MM-dd", "yyyy/MM/dd" };
				for (final String ff : formatosFecha) {
					if (ValidacionesTipo.getInstance().esFecha(fecha, ff)) {
						final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, ff);
						valor = ValidacionesTipo.getInstance().formateaFecha(date, FORMATO_FECHAS_PDF);
						break;
					}
				}
				if (StringUtils.isBlank(valor)) {
					throw new FormateadorException("Fecha no valida");
				}
			}
		} catch (final ValidacionTipoException e) {
			throw new FormateadorException("Fecha no valida");
		}
		return valor;
	}

	/**
	 * Busca un valor y lo añade.
	 *
	 * @param componente
	 * @param linea
	 * @param xml
	 * @throws Exception
	 */
	private void anyadirDato(final RComponente componente, final Linea linea, final XmlFormulario xml) {

		boolean encontrado = false;
		for (final ValorCampo valor : xml.getValores()) {
			if (valor.getId() != null && componente.getIdentificador().equals(valor.getId())) {
				encontrado = true;
				if (UtilsSTG.traduceTipoCampo(componente.getTipo()) != TypeCampo.OCULTO) {
					if (valor instanceof ValorCampoSimple) {
						String valorCampoSimple = ((ValorCampoSimple) valor).getValor();
						if (isComponenteTipoFecha(componente)) {
							valorCampoSimple = getConversionFecha(valorCampoSimple);
						}
						linea.getObjetosLinea().add(new CampoTexto(6, isMultilinea(valorCampoSimple),
								componente.getEtiqueta(), valorCampoSimple));
					} else if (valor instanceof ValorCampoIndexado) {
						linea.getObjetosLinea().add(new CampoTexto(6, false, componente.getEtiqueta(),
								getValorCampoIndexado((ValorCampoIndexado) valor)));
					} else if (valor instanceof ValorCampoListaIndexados) {
						final String valorStr = getValorCampoListaIndexados((ValorCampoListaIndexados) valor);
						linea.getObjetosLinea()
								.add(new CampoTexto(6, isMultilinea(valorStr), componente.getEtiqueta(), valorStr));
					}
				}
			}
		}
		if (!encontrado) {
			linea.getObjetosLinea().add(new CampoTexto(6, false, componente.getEtiqueta(), ""));
		}

	}

	/**
	 * Indica si texto es multilínea.
	 *
	 * @param texto
	 *                  texto
	 * @return boolean
	 */
	private boolean isMultilinea(final String texto) {
		return (texto != null && texto.indexOf("\n") != -1);
	}

	/**
	 * Objeto valor para ValorCampoIndexado
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private String getValorCampoIndexado(final ValorCampoIndexado valor) {
		String valorCampoIndexado = "";
		if (valor != null && valor.getValor() != null) {
			if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.DESCRIPCION) {
				valorCampoIndexado = valor.getValor().getDescripcion();
			} else {
				valorCampoIndexado = getValorCampoIndexado(valor.getValor());
			}
		}
		return valorCampoIndexado;
	}

	/**
	 * Objeto propiedad para ValorCampoListaIndexados.
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private String getValorCampoListaIndexados(final ValorCampoListaIndexados valor) {
		final ValorCampoListaIndexados valorLista = valor;
		final StringBuilder valorListaSimple = new StringBuilder("");
		final String separador = "\n";
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
		switch (visualizacionValorIndexado) {
		case CODIGO_DESCRIPCION_CON_GUION:
		case CODIGO_DESCRIPCION_CON_PARENTESIS:
			valorListaSimple.append(getCodigoVisualizado(valorElemento.getValor()));
			valorListaSimple.append(valorElemento.getDescripcion());
			break;

		case DESCRIPCION_CODIGO_CON_GUION:
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
		if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_PARENTESIS
				|| visualizacionValorIndexado == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS) {
			texto.append(" (");
		} else if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_GUION) {
			texto.append(" - ");
		}

		texto.append(codigo);
		if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_PARENTESIS
				|| visualizacionValorIndexado == TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS) {
			texto.append(") ");
		} else if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.CODIGO_DESCRIPCION_CON_GUION) {
			texto.append(" - ");
		}

		return texto.toString();

	}

}

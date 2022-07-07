package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import es.caib.sistra2.commons.pdf.ImageStamp;
import es.caib.sistra2.commons.pdf.ObjectStamp;
import es.caib.sistra2.commons.pdf.UtilPDF;
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

	/** Propiedad plantilla que indica url logo. **/
	public static final String PROP_LOGO_DINAMICO_URL = "logo.dinamico.url";

	/** Propiedad plantilla que indica escalado alto logo en %. **/
	public static final String PROP_LOGO_ALTO = "logo.escalado.alto";

	/** Propiedad plantilla que indica escalado ancho logo en %. **/
	public static final String PROP_LOGO_ANCHO = "logo.escalado.ancho";

	/** Propiedad plantilla que indica visualización campos indexados. **/
	public static final String PROP_VISUALIZACION_VALOR_INDEXADO = "campoIndexado.visualizacion";

	/** Propiedad plantilla que indica si se muestran mensajes de aviso. **/
	public static final String PROP_MOSTRAR_AVISOS = "aviso.mostrar";

	/** Propiedad plantilla que indica si se muestra título procedimiento. */
	public static final String PROP_MOSTRAR_TITULO_PROCEDIMIENTO = "titulo.procedimiento";

	/** Propiedad plantilla que indica si se muestra título en mayusculas. */
	public static final String PROP_MOSTRAR_TITULO_MAYUSCULAS = "titulo.mayusculas";

	/** Propiedad plantilla que indica si se muestra subtítulo en mayusculas. */
	public static final String PROP_MOSTRAR_SUBTITULO_MAYUSCULAS = "subtitulo.mayusculas";

	/** Propiedad plantilla que indica marca de agua. */
	public static final String PROP_MARCA_AGUA_URL = "marcaAgua.url";

	/** Propiedad plantilla que indica escala X. */
	public static final String PROP_MARCA_AGUA_SCALE_X = "marcaAgua.scale.percentX";

	/** Propiedad plantilla que indica escala Y. */
	public static final String PROP_MARCA_AGUA_SCALE_Y = "marcaAgua.scale.percentY";

	/** Propiedad plantilla que indica posicion X. */
	public static final String PROP_MARCA_AGUA_POSITION_X = "marcaAgua.position.X";

	/** Propiedad plantilla que indica posicion y. */
	public static final String PROP_MARCA_AGUA_POSITION_Y = "marcaAgua.position.Y";

	/** Visualización campos indexados. **/
	private TipoVisualizacionValorIndexado visualizacionValorIndexado;

	/** Indica si hay que mostrar los mensajes de aviso. **/
	private Boolean mostrarAviso;

	/** Url de la imagen. **/
	private String urlImagen;

	/** Url de la imagen dinamico. **/
	private String urlImagenDinamico;

	/** Escalado alto imagen. */
	private Integer escaladoAltoImagen;

	/** Escalado ancho imagen. */
	private Integer escaladoAnchoImagen;

	/** Si se muestra procedimiento - titulo, sino solo el titulo. **/
	private Boolean mostrarTituloConProcedimiento;

	/** Si se muestra titulo en mayusculas. **/
	private Boolean mostrarTituloMayusculas;

	/** Si se muestra subtitulo en mayusculas. **/
	private Boolean mostrarSubtituloMayusculas;

	/** Marca agua: url. */
	private String marcaAguaUrl;

	/** Marca agua: escala X. */
	private Integer marcaAguaEscalaX;

	/** Marca agua: escala Y. */
	private Integer marcaAguaEscalaY;

	/** Marca agua: posicion X. */
	private Integer marcaAguaPosicionX;

	/** Marca agua: posicion Y. */
	private Integer marcaAguaPosicionY;

	@Override
	public byte[] formatear(final byte[] ixml, final byte[] plantilla, final String idioma,
			final RFormularioInterno defFormInterno, final String tituloProcedimiento, final String siaProcedimiento,
			final String codigoDir3Responsable) {

		final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);

		inicializarValores(plantilla);

		// Creamos formulario
		final FormularioPdf formularioPdf = new FormularioPdf();

		// Cabecera
		final Cabecera cabecera = new Cabecera();
		formularioPdf.setCabecera(cabecera);

		// - Titulo
		if (mostrarTituloConProcedimiento) {
			cabecera.setTitulo(tituloProcedimiento);
			cabecera.setSubtitulo(defFormInterno.getTitulo());
		} else {
			cabecera.setTitulo(tituloProcedimiento);
		}
		// TODO PENDIENTE VERSALITAS
		if (mostrarTituloMayusculas) {
			cabecera.setTitulo(cabecera.getTitulo().toUpperCase());
		}
		if (mostrarSubtituloMayusculas && StringUtils.isNotBlank(cabecera.getSubtitulo())) {
			cabecera.setSubtitulo(cabecera.getSubtitulo().toUpperCase());
		}

		// - Codigo SIA
		cabecera.setCodigoSia(siaProcedimiento);

		// - Logo
		byte[] arrayBytes = null;
		if (StringUtils.isNotBlank(urlImagenDinamico)) {
			// Reemplazamos DIR3
			final String url = urlImagenDinamico.replace("${DIR3_RESPONSABLE}", codigoDir3Responsable);
			arrayBytes = getImagen(url);
		}
		if (arrayBytes == null && StringUtils.isNotBlank(urlImagen)) {
			arrayBytes = getImagen(urlImagen);
		}
		if (arrayBytes != null) {
			cabecera.setLogoByte(arrayBytes);
			if (escaladoAltoImagen != null) {
				cabecera.setAltoLogo(escaladoAltoImagen);
			}
			if (escaladoAnchoImagen != null) {
				cabecera.setAnchoLogo(escaladoAnchoImagen);
			}
		}

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
							// Creamos texto aviso
							final RComponenteAviso componenteAviso = (RComponenteAviso) componente;
							final PersonalizacionTexto personalizacicionTexto = new PersonalizacionTexto(false, true,
									TypeFuente.NOTOSANS, 10);
							// Texto aviso puede contener html, por lo q limpiamos
							final String textoAviso = Jsoup.parse(componenteAviso.getEtiqueta()).text();
							final Texto texto = new Texto(personalizacicionTexto, textoAviso, 6);
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
			// Genera PDF
			final GeneradorPdf generadorPdf = new GeneradorPdf();
			byte[] pdf = generadorPdf.generarPdf(formularioPdf);
			// Establece marca de agua
			pdf = stampMarcaAgua(pdf);
			return pdf;
		} catch (final Exception e) {
			throw new FormateadorException("Error convertint el document a bytes", e);
		}

	}

	/**
	 * Obtiene url imagen
	 *
	 * @param url url imagen
	 * @return bytes imagen (nulo si no puede recuperarla)
	 */
	private byte[] getImagen(final String url) {
		byte[] arrayBytes = null;
		if (StringUtils.isNotBlank(url)) {
			try {
				if (url.startsWith("http")) {
					arrayBytes = IOUtils.toByteArray(new URL(url));
				} else {
					arrayBytes = IOUtils.toByteArray(new FileInputStream(url));
				}
			} catch (final IOException e) {
				arrayBytes = null;
			}
		}
		return arrayBytes;
	}

	/**
	 * Stamp marca de agua.
	 *
	 * @param pdf PDF
	 * @return pdf con marca de agua
	 */
	private byte[] stampMarcaAgua(final byte[] pdf) {
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		byte[] pdfStamp = pdf;
		try {
			if (StringUtils.isNotBlank(marcaAguaUrl)) {
				byte[] marcarAguaBytes;
				if (marcaAguaUrl.startsWith("http")) {
					marcarAguaBytes = IOUtils.toByteArray(new URL(marcaAguaUrl));
				} else {
					marcarAguaBytes = IOUtils.toByteArray(new FileInputStream(marcaAguaUrl));
				}
				final ImageStamp imgStamp = new ImageStamp();
				imgStamp.setImagen(marcarAguaBytes);
				imgStamp.setOverContent(false);
				imgStamp.setScalePerCent(marcaAguaEscalaX != null || marcaAguaEscalaY != null);
				if (marcaAguaEscalaX != null) {
					imgStamp.setXScale((float) marcaAguaEscalaX);
				}
				if (marcaAguaEscalaY != null) {
					imgStamp.setYScale((float) marcaAguaEscalaY);
				}
				if (marcaAguaPosicionX != null) {
					imgStamp.setX(marcaAguaPosicionX);
				}
				if (marcaAguaPosicionY != null) {
					imgStamp.setY(marcaAguaPosicionY);
				}
				final ObjectStamp[] objects = new ObjectStamp[1];
				objects[0] = imgStamp;
				bis = new ByteArrayInputStream(pdf);
				bos = new ByteArrayOutputStream(pdf.length + marcarAguaBytes.length);
				UtilPDF.stamp(bos, bis, objects);
				pdfStamp = bos.toByteArray();
			}
			return pdfStamp;
		} catch (final Exception ex) {
			throw new FormateadorException("Excepció al estampar marca d'aigua: " + ex.getMessage(), ex);
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * Inicializa los valores
	 *
	 * @param plantilla
	 */
	private void inicializarValores(final byte[] plantilla) {

		urlImagen = null;
		escaladoAltoImagen = null;
		escaladoAnchoImagen = null;
		visualizacionValorIndexado = TipoVisualizacionValorIndexado.DESCRIPCION_CODIGO_CON_PARENTESIS;
		mostrarAviso = false;
		mostrarTituloConProcedimiento = false;
		mostrarTituloMayusculas = false;
		mostrarSubtituloMayusculas = false;
		marcaAguaUrl = null;
		marcaAguaEscalaX = null;
		marcaAguaEscalaY = null;
		marcaAguaPosicionX = null;
		marcaAguaPosicionY = null;

		if (plantilla != null) {
			try (ByteArrayInputStream bis = new ByteArrayInputStream(plantilla)) {

				final Properties propiedades = new Properties();
				propiedades.load(bis);

				urlImagen = propiedades.getProperty(PROP_LOGO_URL);
				urlImagenDinamico = propiedades.getProperty(PROP_LOGO_DINAMICO_URL);

				escaladoAltoImagen = (propiedades.getProperty(PROP_LOGO_ALTO) != null
						? Integer.parseInt(propiedades.getProperty(PROP_LOGO_ALTO))
						: null);
				escaladoAnchoImagen = (propiedades.getProperty(PROP_LOGO_ANCHO) != null
						? Integer.parseInt(propiedades.getProperty(PROP_LOGO_ANCHO))
						: null);

				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_VISUALIZACION_VALOR_INDEXADO))) {
					visualizacionValorIndexado = TipoVisualizacionValorIndexado
							.valueOf(propiedades.getProperty(PROP_VISUALIZACION_VALOR_INDEXADO));
				}

				mostrarAviso = Boolean.valueOf(propiedades.getProperty(PROP_MOSTRAR_AVISOS));

				mostrarTituloConProcedimiento = Boolean
						.valueOf(propiedades.getProperty(PROP_MOSTRAR_TITULO_PROCEDIMIENTO));

				mostrarTituloMayusculas = Boolean.valueOf(propiedades.getProperty(PROP_MOSTRAR_TITULO_MAYUSCULAS));

				mostrarSubtituloMayusculas = Boolean
						.valueOf(propiedades.getProperty(PROP_MOSTRAR_SUBTITULO_MAYUSCULAS));

				marcaAguaUrl = propiedades.getProperty(PROP_MARCA_AGUA_URL);
				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_MARCA_AGUA_SCALE_X))) {
					marcaAguaEscalaX = Integer.valueOf(propiedades.getProperty(PROP_MARCA_AGUA_SCALE_X));
				}
				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_MARCA_AGUA_SCALE_Y))) {
					marcaAguaEscalaY = Integer.valueOf(propiedades.getProperty(PROP_MARCA_AGUA_SCALE_Y));
				}
				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_MARCA_AGUA_POSITION_X))) {
					marcaAguaPosicionX = Integer.valueOf(propiedades.getProperty(PROP_MARCA_AGUA_POSITION_X));
				}
				if (StringUtils.isNotBlank(propiedades.getProperty(PROP_MARCA_AGUA_POSITION_Y))) {
					marcaAguaPosicionY = Integer.valueOf(propiedades.getProperty(PROP_MARCA_AGUA_POSITION_Y));
				}

			} catch (final Exception e) {
				throw new FormateadorException("Error obtenint propietat formatetjador", e);
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
	private boolean isComponenteTipoMultilinea(final RComponente componente) {
		return UtilsSTG.traduceTipoCampo(componente.getTipo()) == TypeCampo.TEXTO
				&& UtilsSTG.traduceTipoTexto(((RComponenteTextbox) componente).getTipoTexto()) == TypeTexto.NORMAL
				&& ((RComponenteTextbox) componente).getTextoNormal().getLineas() > 1;
	}

	/**
	 * Comprueba si un componente es de tipo texto
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
	 * @param fecha Fecha en formato YYYY-MM-DD
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
					throw new FormateadorException("Data no vàlida");
				}
			}
		} catch (final ValidacionTipoException e) {
			throw new FormateadorException("Data no vàlida");
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
				final TypeCampo tipoCampo = UtilsSTG.traduceTipoCampo(componente.getTipo());
				if (tipoCampo != TypeCampo.OCULTO && tipoCampo != TypeCampo.CAPTCHA) {
					if (valor instanceof ValorCampoSimple) {
						String valorCampoSimple = ((ValorCampoSimple) valor).getValor();
						// Conversión fecha
						if (isComponenteTipoFecha(componente)) {
							valorCampoSimple = getConversionFecha(valorCampoSimple);
						}
						// Si es multilinea nos aseguramos que exista un salto de línea
						if (isComponenteTipoMultilinea(componente) && valorCampoSimple != null
								&& valorCampoSimple.indexOf("\n") == -1) {
							valorCampoSimple = valorCampoSimple + "\r\n";
						}
						// Establece valor en pdf
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
	 * @param texto texto
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

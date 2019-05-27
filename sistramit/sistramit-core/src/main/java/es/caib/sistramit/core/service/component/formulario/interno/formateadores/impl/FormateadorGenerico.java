package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistra2.commons.pdf.PDFDocument;
import es.caib.sistra2.commons.pdf.Parrafo;
import es.caib.sistra2.commons.pdf.Propiedad;
import es.caib.sistra2.commons.pdf.Seccion;
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

		// Creamos Map de valores
		final PDFDocument documento;
		if (mostrarTituloConProcedimiento) {
			documento = new PDFDocument(tituloProcedimiento + " - " + defFormInterno.getTitulo());
		} else {
			documento = new PDFDocument(defFormInterno.getTitulo());
		}
		if (StringUtils.isNotBlank(urlImagen)) {
			documento.setImagen(urlImagen);
		}

		// Recorremos las paginas
		for (final RPaginaFormulario pagina : defFormInterno.getPaginas()) {
			boolean creadaSeccion = false;
			Seccion seccion = null;
			for (final RLineaComponentes linea : pagina.getLineas()) {
				for (final RComponente componente : linea.getComponentes()) {

					// Si no se ha creado una sección y el componente no es de
					// tipo sección
					if (!(componente instanceof RComponenteSeccion) && !creadaSeccion) {
						seccion = crearSeccionVacia();
						creadaSeccion = true;
					}

					// Añadir segun componente
					if (componente instanceof RComponenteSeccion) {

						// Si ya hay seccion, se cierra y se añade
						if (seccion != null) {
							documento.addSeccion(seccion);
						}
						final RComponenteSeccion componenteSeccion = (RComponenteSeccion) componente;
						seccion = new Seccion(componenteSeccion.getLetra(), componenteSeccion.getEtiqueta());

						creadaSeccion = true;

					} else if (componente instanceof RComponenteAviso) {

						// Sólo se muestra el aviso si está activo la variable.
						if (mostrarAviso != null && mostrarAviso) {
							// Si no se ha creado una sección
							if (seccion == null) {
								seccion = crearSeccionVacia();
								creadaSeccion = true;
							}

							final RComponenteAviso componenteAviso = (RComponenteAviso) componente;
							final Parrafo campo = new Parrafo(componenteAviso.getEtiqueta());
							seccion.addCampo(campo);
						}

					} else {
						if (seccion != null && xml.getValores() != null && componente.getIdentificador() != null) {
							anyadirDato(componente, seccion, xml);
						}
					}

				}
			}
			documento.addSeccion(seccion);
		}

		try {

			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			documento.generate(output);
			return output.toByteArray();

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
	 * Crea seccion sin cabecera.
	 *
	 * @return
	 */
	private Seccion crearSeccionVacia() {
		return new Seccion(false);
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
	 *            Fecha en formato YYYY-MM-DD
	 * @return Fecha en formato DD-MM-YYYY
	 * @throws Exception
	 */
	private String getConversionFecha(final String fecha) {
		String valor = "";
		try {
			if (StringUtils.isNotBlank(fecha)) {
				if (ValidacionesTipo.getInstance().esFecha(fecha, "yyyy-MM-dd")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "yyyy-MM-dd");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, "dd-MM-yyyy");
				} else if (ValidacionesTipo.getInstance().esFecha(fecha, "yyyy/MM/dd")) {
					final Date date = ValidacionesTipo.getInstance().parseFecha(fecha, "yyyy/MM/dd");
					valor = ValidacionesTipo.getInstance().formateaFecha(date, "dd/MM/yyyy");
				} else {
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
	 * @param seccion
	 * @param xml
	 * @throws Exception
	 */
	private void anyadirDato(final RComponente componente, final Seccion seccion, final XmlFormulario xml) {

		boolean encontrado = false;
		for (final ValorCampo valor : xml.getValores()) {
			if (valor.getId() != null && componente.getIdentificador().equals(valor.getId())) {
				encontrado = true;
				if (valor instanceof ValorCampoSimple) {
					String valorCampoSimple = ((ValorCampoSimple) valor).getValor();
					if (isComponenteTipoFecha(componente)) {
						valorCampoSimple = getConversionFecha(valorCampoSimple);
					}
					seccion.addCampo(createPropiedad(componente.getEtiqueta(), valorCampoSimple));
				} else if (valor instanceof ValorCampoIndexado) {
					seccion.addCampo(getPropiedad(componente.getEtiqueta(), (ValorCampoIndexado) valor));
				} else if (valor instanceof ValorCampoListaIndexados) {
					seccion.addCampo(getPropiedad(componente.getEtiqueta(), (ValorCampoListaIndexados) valor));
				}
			}
		}

		if (!encontrado) {
			final Propiedad campo = new Propiedad(componente.getEtiqueta(), "");
			seccion.addCampo(campo);
		}

	}

	/**
	 * Objeto propiedad para ValorCampoIndexado
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private Propiedad getPropiedad(final String etiqueta, final ValorCampoIndexado valor) {

		String descripcion = "";
		String valorCampoIndexado = "";

		if (valor != null && valor.getValor() != null) {
			descripcion = valor.getValor().getDescripcion();
			valorCampoIndexado = getValorCampoIndexado(valor.getValor());
		}

		if (visualizacionValorIndexado == TipoVisualizacionValorIndexado.DESCRIPCION) {
			return new Propiedad(etiqueta, descripcion);
		} else {
			return new Propiedad(etiqueta, valorCampoIndexado);
		}
	}

	/**
	 * Objeto propiedad para ValorCampoListaIndexados.
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private Propiedad getPropiedad(final String etiqueta, final ValorCampoListaIndexados valor) {
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
		return new Propiedad(etiqueta, linea);
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

	/**
	 * Objeto propiedad para ValorCampoSimple
	 *
	 * @param etiqueta
	 * @param valor
	 * @return
	 */
	private Propiedad createPropiedad(final String etiqueta, final String valor) {
		return new Propiedad(etiqueta, valor);
	}

}

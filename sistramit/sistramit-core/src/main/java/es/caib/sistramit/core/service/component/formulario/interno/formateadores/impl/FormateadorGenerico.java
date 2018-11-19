package es.caib.sistramit.core.service.component.formulario.interno.formateadores.impl;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;

import es.caib.sistra2.commons.pdf.PDFDocument;
import es.caib.sistra2.commons.pdf.Parrafo;
import es.caib.sistra2.commons.pdf.Propiedad;
import es.caib.sistra2.commons.pdf.Seccion;
import es.caib.sistra2.commons.pdf.TipoVisualizacionListado;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteAviso;
import es.caib.sistrages.rest.api.interna.RComponenteSeccion;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
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
public class FormateadorGenerico implements FormateadorPdfFormulario {

    /**
     * Propiedad que se pasa por plantilla, indica la url para poner el pdf.
     **/
    public static final String IMAGEN_URL = "imagen.url";
    /**
     * Propiedad que se pasa por plantilla, indica como mostrar la visualización
     * de listado de valores.
     **/
    public static final String LISTADO_VISUALIZACION = "listado.visualizacion";
    /**
     * Propiedad que se pasa por plantilla, indica si los mensajes de aviso se
     * deben mostrar tambien el pdf.
     **/
    public static final String AVISO_MOSTRAR = "aviso.mostrar";

    /** Como es la visualización de listados. **/
    private TipoVisualizacionListado listadoVisualizacion;
    /** Indica si hay que mostrar los mensajes de aviso. **/
    private Boolean mostrarAviso;
    /** Url de la imagen. **/
    private String urlImagen;

    @Override
    public byte[] formatear(final byte[] ixml, final byte[] plantilla,
            final String idioma, final RFormularioInterno defFormInterno) {

        final XmlFormulario xml = UtilsFormulario.xmlToValores(ixml);

        inicializarValores(plantilla);

        // Creamos Map de valores
        final PDFDocument documento = new PDFDocument(
                defFormInterno.getTitulo());
        if (urlImagen != null) {
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
                    if (!(componente instanceof RComponenteSeccion)
                            && !creadaSeccion) {
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
                        seccion = new Seccion(componenteSeccion.getLetra(),
                                componenteSeccion.getEtiqueta());

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
                            final Parrafo campo = new Parrafo(
                                    componenteAviso.getEtiqueta());
                            seccion.addCampo(campo);
                        }

                    } else {
                        if (seccion != null && xml.getValores() != null
                                && componente.getIdentificador() != null) {
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
            throw new FormateadorException(
                    "Error convirtiendo el documento a bytes", e);
        }

    }

    /**
     * Inicializa los valores
     *
     * @param plantilla
     */
    private void inicializarValores(final byte[] plantilla) {

        urlImagen = null;
        listadoVisualizacion = TipoVisualizacionListado.SUFIJO_PARENTESIS;
        mostrarAviso = false;
        if (plantilla != null) {
            try {

                final Properties prop = SerializationUtils
                        .deserialize(plantilla);
                if (prop != null && prop.containsKey(IMAGEN_URL)) {
                    urlImagen = prop.getProperty(IMAGEN_URL);
                }

                if (prop != null && prop.containsKey(LISTADO_VISUALIZACION)) {
                    listadoVisualizacion = (TipoVisualizacionListado) prop
                            .get(LISTADO_VISUALIZACION);
                }

                if (prop != null && prop.containsKey(AVISO_MOSTRAR)) {
                    mostrarAviso = Boolean
                            .valueOf(prop.get(AVISO_MOSTRAR).toString());
                }

            } catch (final Exception e) {
                throw new FormateadorException(
                        "Error obteniendo propiedades formateador", e);
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
     * Busca un valor y lo añade.
     *
     * @param componente
     * @param seccion
     * @param xml
     */
    private void anyadirDato(final RComponente componente,
            final Seccion seccion, final XmlFormulario xml) {

        boolean encontrado = false;
        for (final ValorCampo valor : xml.getValores()) {
            if (valor.getId() != null
                    && componente.getIdentificador().equals(valor.getId())) {
                encontrado = true;
                if (valor instanceof ValorCampoSimple) {
                    seccion.addCampo(getPropiedad(componente.getEtiqueta(),
                            (ValorCampoSimple) valor));
                } else if (valor instanceof ValorCampoIndexado) {
                    seccion.addCampo(getPropiedad(componente.getEtiqueta(),
                            (ValorCampoIndexado) valor));
                } else if (valor instanceof ValorCampoListaIndexados) {
                    seccion.addCampo(getPropiedad(componente.getEtiqueta(),
                            (ValorCampoListaIndexados) valor));
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
    private Propiedad getPropiedad(final String etiqueta,
            final ValorCampoIndexado valor) {
        if (listadoVisualizacion == TipoVisualizacionListado.SOLO_CODIGO) {
            return new Propiedad(etiqueta, valor.getValor().getValor());
        } else if (listadoVisualizacion == TipoVisualizacionListado.SOLO_DESCRIPCION) {
            return new Propiedad(etiqueta, valor.getValor().getDescripcion());
        } else {
            return new Propiedad(etiqueta,
                    getValorCampoIndexado(valor.getValor()));
        }
    }

    /**
     * Objeto propiedad para ValorCampoListaIndexados.
     *
     * @param etiqueta
     * @param valor
     * @return
     */
    private Propiedad getPropiedad(final String etiqueta,
            final ValorCampoListaIndexados valor) {
        final ValorCampoListaIndexados valorLista = valor;
        final StringBuilder valorListaSimple = new StringBuilder("");
        String separador;
        if (listadoVisualizacion == TipoVisualizacionListado.SOLO_DESCRIPCION_COMAS
                || listadoVisualizacion == TipoVisualizacionListado.SOLO_CODIGO_COMAS) {
            separador = ", ";
        } else {
            separador = "\n";
        }
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
        switch (listadoVisualizacion) {
        case PREFIJO_GUION:
        case PREFIJO_PARENTESIS:
            valorListaSimple
                    .append(getCodigoVisualizado(valorElemento.getValor()));
            valorListaSimple.append(valorElemento.getDescripcion());
            break;

        case SUFIJO_GUION:
        case SUFIJO_PARENTESIS:
            valorListaSimple.append(valorElemento.getDescripcion());
            valorListaSimple
                    .append(getCodigoVisualizado(valorElemento.getValor()));
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

    /**
     * Objeto propiedad para ValorCampoSimple
     *
     * @param etiqueta
     * @param valor
     * @return
     */
    private Propiedad getPropiedad(final String etiqueta,
            final ValorCampoSimple valor) {
        return new Propiedad(etiqueta, valor.getValor());
    }

}

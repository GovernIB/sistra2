package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * RespuestaBase. Estructura de respuesta que contiene la información comun a
 * todas las respuestas.
 *
 * @author indra
 *
 */
@XmlRootElement
public class RLink {

    public RLink(final String entidad, final String codigo, final String url,
            final String descripcion, final boolean hateoas) {
        super();
        this.rel = entidad;
        this.codigo = codigo;
        this.descripcion = descripcion;
        if (hateoas) {
            this.href = url;
        } else {
            this.href = null;
        }
    }

    public RLink(final String entidad, final String codigo, final String url,
            final boolean hateoas) {
        this(entidad, codigo, url, null, hateoas);
    }

    public RLink() {
        super();
    }

    /** Status a retornar. **/
    private String rel;

    /** Mensaje de error. **/
    private String codigo;

    /** Mensaje de error. **/
    private String href;

    /** Mensaje de error. **/
    private String descripcion;

    /**
     * @return the rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * @param rel
     *            the rel to set
     */
    public void setRel(final String rel) {
        this.rel = rel;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href
     *            the href to set
     */
    public void setHref(final String href) {
        this.href = href;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion
     *            the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}

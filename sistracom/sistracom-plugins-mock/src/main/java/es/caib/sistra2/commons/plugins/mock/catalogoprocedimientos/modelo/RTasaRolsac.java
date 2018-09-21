package es.caib.sistra2.commons.plugins.mock.catalogoprocedimientos.modelo;

import java.io.IOException;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Fitxes.
 *
 * @author indra
 *
 */
@XmlRootElement
public class RTasaRolsac {

    /** codigo **/
    private long codigo;

    private java.lang.String codificacio;

    private java.lang.String descripcio;

    private java.lang.String formaPagament;

    private java.lang.Long orden;

    public RTasaRolsac(final RTasaRolsac elem, final String urlBase,
            final String idioma, final boolean hateoasEnabled) {
        super();
    }

    public RTasaRolsac() {
        super();
    }

    public void generaLinks(final String urlBase) {
    }

    public static RTasaRolsac valueOf(final String json) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final TypeReference<RTasaRolsac> typeRef = new TypeReference<RTasaRolsac>() {
        };
        RTasaRolsac obj;
        try {
            obj = (RTasaRolsac) objectMapper.readValue(json, typeRef);
        } catch (final IOException e) {
            // TODO PENDIENTE
            throw new RuntimeException(e);
        }
        return obj;
    }

    public String toJson() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
            return objectMapper.writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            // TODO PENDIENTE
            throw new RuntimeException(e);
        }
    }

    protected void addSetersInvalidos() {
        // TODO Auto-generated method stub

    }

    public void setId(final Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(final long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codificacio
     */
    public java.lang.String getCodificacio() {
        return codificacio;
    }

    /**
     * @param codificacio
     *            the codificacio to set
     */
    public void setCodificacio(final java.lang.String codificacio) {
        this.codificacio = codificacio;
    }

    /**
     * @return the descripcio
     */
    public java.lang.String getDescripcio() {
        return descripcio;
    }

    /**
     * @param descripcio
     *            the descripcio to set
     */
    public void setDescripcio(final java.lang.String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * @return the formaPagament
     */
    public java.lang.String getFormaPagament() {
        return formaPagament;
    }

    /**
     * @param formaPagament
     *            the formaPagament to set
     */
    public void setFormaPagament(final java.lang.String formaPagament) {
        this.formaPagament = formaPagament;
    }

    /**
     * @return the orden
     */
    public java.lang.Long getOrden() {
        return orden;
    }

    /**
     * @param orden
     *            the orden to set
     */
    public void setOrden(final java.lang.Long orden) {
        this.orden = orden;
    }

}

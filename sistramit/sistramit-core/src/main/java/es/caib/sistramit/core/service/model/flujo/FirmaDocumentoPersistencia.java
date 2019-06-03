package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.flujo.types.TypeFirmaDigital;

/**
 * Firma de un documento.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FirmaDocumentoPersistencia implements Serializable {

    /**
     * Nif firmante.
     */
    private String nif;
    /**
     * Nombre firmante.
     */
    private String nombre;

    /**
     * Referencia fichero firmado.
     */
    private ReferenciaFichero fichero;

    /**
     * Referencia fichero que contiene la firma.
     */
    private ReferenciaFichero firma;

    /**
     * Tipo de firma digital.
     */
    private TypeFirmaDigital tipoFirma;

    /** Atributo fecha. */
    private Date fecha;

    /**
     * Método de acceso a nif.
     *
     * @return nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Método para establecer nif.
     *
     * @param nif
     *            nif a establecer
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Método de acceso a nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer nombre.
     *
     * @param nombre
     *            nombre a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método de acceso a fichero.
     *
     * @return fichero
     */
    public ReferenciaFichero getFichero() {
        return fichero;
    }

    /**
     * Método para establecer fichero.
     *
     * @param fichero
     *            fichero a establecer
     */
    public void setFichero(ReferenciaFichero fichero) {
        this.fichero = fichero;
    }

    /**
     * Método de acceso a firma.
     *
     * @return firma
     */
    public ReferenciaFichero getFirma() {
        return firma;
    }

    /**
     * Método para establecer firma.
     *
     * @param firma
     *            firma a establecer
     */
    public void setFirma(ReferenciaFichero firma) {
        this.firma = firma;
    }

    /**
     * Método de acceso a tipoFirma.
     *
     * @return tipoFirma
     */
    public TypeFirmaDigital getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Método para establecer tipoFirma.
     *
     * @param tipoFirma
     *            tipoFirma a establecer
     */
    public void setTipoFirma(TypeFirmaDigital tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    /**
     * Método de acceso a fecha.
     *
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método para establecer fecha.
     *
     * @param fecha
     *            fecha a establecer
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Crea instancia (para bucles).
     *
     * @return FirmaDocumentoPersistencia
     */
    public static FirmaDocumentoPersistencia createFirmaDocumentoPersistencia() {
        final FirmaDocumentoPersistencia f = new FirmaDocumentoPersistencia();
        return f;
    }

}

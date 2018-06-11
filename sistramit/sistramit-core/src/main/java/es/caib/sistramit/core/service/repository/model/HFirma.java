package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapeo tabla STT_FIRDPT.
 */

@Entity
@Table(name = "STT_FIRDPT")
@SuppressWarnings("serial")
public class HFirma implements IModelApi {

    /** Atributo codigo. */
    @Id
    @SequenceGenerator(name = "STT_FIRDPT_SEQ", sequenceName = "STT_FIRDPT_SEQ", allocationSize = ConstantesNumero.N1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_FIRDPT_SEQ")
    @Column(name = "FDP_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
    private Long codigo;

    /** Documento persistente. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "FDP_CODDPT")
    private HDocumento documentoPersistente;

    /** Atributo fichero. */
    @Column(name = "FDP_CODFIC")
    private Long fichero;

    /** Atributo nif. */
    @Column(name = "FDP_FTENIF")
    private String nif;

    /** Atributo nombre. */
    @Column(name = "FDP_FTENOM")
    private String nombre;

    /** Atributo fecha firma. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FDP_FECFIR")
    private Date fecha;

    /** Atributo tipo firma. */
    @Column(name = "FDP_TIPFIR")
    private String tipoFirma;

    /** Atributo firma: cod fichero. */
    @Column(name = "FDP_FIRMA")
    private Long firma;

    /** Atributo firma: clave fichero. */
    @Column(name = "FDP_FIRMAC")
    private String firmaClave;

    /**
     * Obtiene el atributo codigo de HFirma.
     *
     * @return el atributo codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Asigna el atributo codigo de HFirma.
     *
     * @param pCodigo
     *            el nuevo valor para codigo
     */
    public void setCodigo(final Long pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Obtiene el atributo nif de HFirma.
     *
     * @return el atributo nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Asigna el atributo nif de HFirma.
     *
     * @param pNif
     *            el nuevo valor para nif
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * Obtiene el atributo nombre de HFirma.
     *
     * @return el atributo nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el atributo nombre de HFirma.
     *
     * @param pNombre
     *            el nuevo valor para nombre
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Obtiene el atributo firma de HFirma.
     *
     * @return el atributo firma
     */
    public Long getFirma() {
        return firma;
    }

    /**
     * Asigna el atributo firma de HFirma.
     *
     * @param pFirma
     *            el nuevo valor para firma
     */
    public void setFirma(final Long pFirma) {
        firma = pFirma;
    }

    /**
     * Método para obtener el campo fecha.
     *
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método para settear el campo fecha.
     *
     * @param pFecha
     *            el campo fecha a settear
     */
    public void setFecha(final Date pFecha) {
        fecha = pFecha;
    }

    /**
     * Método para obtener el campo firmaClave.
     *
     * @return the firmaClave
     */
    public String getFirmaClave() {
        return firmaClave;
    }

    /**
     * Método para settear el campo firmaClave.
     *
     * @param pFirmaClave
     *            el campo firmaClave a settear
     */
    public void setFirmaClave(final String pFirmaClave) {
        firmaClave = pFirmaClave;
    }

    /**
     * Método para obtener el campo fichero.
     *
     * @return the fichero
     */
    public Long getFichero() {
        return fichero;
    }

    /**
     * Método para settear el campo fichero.
     *
     * @param pFichero
     *            el campo fichero a settear
     */
    public void setFichero(final Long pFichero) {
        fichero = pFichero;
    }

    /**
     * Método para Crea new h firma de la clase HFirma.
     *
     * @return el h firma
     */
    public static HFirma createNewHFirma() {
        return new HFirma();
    }

    /**
     * Método de acceso a documentoPersistente.
     *
     * @return documentoPersistente
     */
    public HDocumento getDocumentoPersistente() {
        return documentoPersistente;
    }

    /**
     * Método para establecer documentoPersistente.
     *
     * @param pDocumentoPersistente
     *            documentoPersistente a establecer
     */
    public void setDocumentoPersistente(
            final HDocumento pDocumentoPersistente) {
        documentoPersistente = pDocumentoPersistente;
    }

    /**
     * Método de acceso a tipoFirma.
     *
     * @return tipoFirma
     */
    public String getTipoFirma() {
        return tipoFirma;
    }

    /**
     * Método para establecer tipoFirma.
     *
     * @param pTipoFirma
     *            tipoFirma a establecer
     */
    public void setTipoFirma(final String pTipoFirma) {
        tipoFirma = pTipoFirma;
    }

}

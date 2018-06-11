package es.caib.sistramit.core.service.repository.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;

/**
 * Mapea tabla STT_PASTRP.
 */
@Entity
@Table(name = "STT_PASTRP")
@SuppressWarnings("serial")
public final class HPaso implements IModelApi {

    /** Atributo codigo. */
    @Id
    @SequenceGenerator(name = "STT_PASTRP_SEQ", sequenceName = "STT_PASTRP_SEQ", allocationSize = ConstantesNumero.N1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_PASTRP_SEQ")
    @Column(name = "PTR_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
    private Long codigo;

    /** Atributo tramite persistencia. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PTR_CODTRP")
    private HTramite tramitePersistencia;

    /** Atributo identificador paso. */
    @Column(name = "PTR_IDEPTR", length = ConstantesNumero.N30)
    private String identificadorPaso;

    /** Atributo tipo paso. */
    @Column(name = "PTR_TIPO", length = ConstantesNumero.N2)
    private String tipoPaso;

    /** Atributo estado. */
    @Column(name = "PTR_ESTADO", length = ConstantesNumero.N1)
    private String estado;

    /** Atributo orden. */
    @Column(name = "PTR_ORDEN")
    private Integer orden;

    /** Atributo documentos. */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paso", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC, instanciaTimeStamp ASC")
    private List<HDocumento> documentos = new ArrayList<>(0);

    /**
     * Método para obtener el campo documentos.
     *
     * @return the documentos
     */
    public List<HDocumento> getDocumentos() {
        return documentos;
    }

    /**
     * Método para settear el campo documentos.
     *
     * @param pDocumentos
     *            el campo documentos a settear
     */
    public void setDocumentos(final List<HDocumento> pDocumentos) {
        documentos = pDocumentos;
    }

    /**
     * Añade documento a lista documentos.
     *
     * @param doc
     *            Documento a añadir
     */
    public void addDocumento(final HDocumento doc) {
        doc.setPaso(this);

        // Tenemos en cuenta si es multiinstancia (si no al recuperar los datos
        // de persistencia dentro de la misma sesion, se cachean la lista de
        // docs del paso y no cuadra)
        int index = 0;
        boolean enc = false;
        boolean medio = false;
        for (final HDocumento d : getDocumentos()) {
            if (d.getId().equals(doc.getId())) {
                enc = true;
            }
            if (enc && !d.getId().equals(doc.getId())) {
                medio = true;
                break;
            }
            index++;
        }
        if (medio) {
            getDocumentos().add(index, doc);
        } else {
            getDocumentos().add(doc);
        }
    }

    /**
     * Elimina documento de lista documentos.
     *
     * @param doc
     *            Documento a añadir
     */
    public void removeDocumento(final HDocumento doc) {
        getDocumentos().remove(doc);
    }

    /**
     * Método para obtener el campo codigo.
     *
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Método para settear el campo codigo.
     *
     * @param pCodigo
     *            el campo codigo a settear
     */
    public void setCodigo(final Long pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Método para obtener el campo tramitePersistencia.
     *
     * @return the tramitePersistencia
     */
    public HTramite getTramitePersistencia() {
        return tramitePersistencia;
    }

    /**
     * Método para settear el campo tramitePersistencia.
     *
     * @param pTramitePersistencia
     *            el campo tramitePersistencia a settear
     */
    public void setTramitePersistencia(final HTramite pTramitePersistencia) {
        tramitePersistencia = pTramitePersistencia;
    }

    /**
     * Método para obtener el campo identificadorPaso.
     *
     * @return the identificadorPaso
     */
    public String getIdentificadorPaso() {
        return identificadorPaso;
    }

    /**
     * Método para settear el campo identificadorPaso.
     *
     * @param pIdentificadorPaso
     *            el campo identificadorPaso a settear
     */
    public void setIdentificadorPaso(final String pIdentificadorPaso) {
        identificadorPaso = pIdentificadorPaso;
    }

    /**
     * Método para obtener el campo tipoPaso.
     *
     * @return the tipoPaso
     */
    public String getTipoPaso() {
        return tipoPaso;
    }

    /**
     * Método para settear el campo tipoPaso.
     *
     * @param pTipoPaso
     *            el campo tipoPaso a settear
     */
    public void setTipoPaso(final String pTipoPaso) {
        tipoPaso = pTipoPaso;
    }

    /**
     * Método para obtener el campo estado.
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Método para settear el campo estado.
     *
     * @param pEstado
     *            el campo estado a settear
     */
    public void setEstado(final String pEstado) {
        estado = pEstado;
    }

    /**
     * Método para obtener el campo orden.
     *
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Método para settear el campo orden.
     *
     * @param pOrden
     *            el campo orden a settear
     */
    public void setOrden(final Integer pOrden) {
        orden = pOrden;
    }

}

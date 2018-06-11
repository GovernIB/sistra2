package es.caib.sistramit.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;

/**
 * Mapeo tabla STT_INVALI.
 */

@Entity
@Table(name = "STT_INVALI")
@SuppressWarnings("serial")
public final class HInvalidacion implements IModelApi {

    /** Atributo codigo. */
    @Id
    @SequenceGenerator(name = "STT_INVALI_SEQ", sequenceName = "STT_INVALI_SEQ", allocationSize = ConstantesNumero.N1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_INVALI_SEQ")
    @Column(name = "INV_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N20, scale = 0)
    private Long codigo;

    /** Atributo tipo. */
    @Column(name = "INV_TIPO")
    private String tipo;

    /** Atributo tipo. */
    @Column(name = "INV_IDENTI")
    private String identificador;

    /** Atributo fecha. */
    @Column(name = "INV_FECHA")
    private Date fecha;

    /**
     * Método de acceso a codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Método para establecer codigo.
     *
     * @param pCodigo
     *            codigo a establecer
     */
    public void setCodigo(final Long pCodigo) {
        codigo = pCodigo;
    }

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    public void setTipo(final String pTipo) {
        tipo = pTipo;
    }

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param pIdentificador
     *            identificador a establecer
     */
    public void setIdentificador(final String pIdentificador) {
        identificador = pIdentificador;
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
     * @param pFecha
     *            fecha a establecer
     */
    public void setFecha(final Date pFecha) {
        fecha = pFecha;
    }

    public static HInvalidacion fromModel(Invalidacion m) {
        final HInvalidacion h = new HInvalidacion();
        h.setFecha(new Date());
        h.setIdentificador(m.getIdentificador());
        h.setTipo(m.getTipo().toString());
        return h;
    }

    public Invalidacion toModel() {
        final Invalidacion m = new Invalidacion();
        m.setFecha(this.getFecha());
        m.setTipo(TypeInvalidacion.fromString(this.getTipo()));
        m.setIdentificador(this.getIdentificador());
        return m;

    }

}

package es.caib.sistra2.commons.plugins.catalogoprocedimientos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Datos trámite proveniente del Catálogo de Procedimientos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DefinicionTramiteCP implements Serializable {

    /** Identificador trámite en el catálogo de procedimientos. */
    private String identificador;

    /** Título trámite. */
    private String descripcion;

    /** Procedimiento asociado. */
    private DefinicionProcedimientoCP procedimiento;

    /** Indica si es vigente. */
    private boolean vigente;

    /** Plazo inicio. */
    private Date plazoInicio;

    /** Plazo fin. */
    private Date plazoFin;

    /** Url info. */
    private String urlInformacion;

    /** Código destino del órgano DIR3. */
    private String organoDestinoDir3;

    /** Documentos asociados. */
    List<DefinicionDocumentoTramiteCP> documentos;

    /** Tasas asociadas. */
    List<DefinicionTasaTramiteCP> tasas;

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
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a descripcion.
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     * 
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a procedimiento.
     * 
     * @return procedimiento
     */
    public DefinicionProcedimientoCP getProcedimiento() {
        return procedimiento;
    }

    /**
     * Método para establecer procedimiento.
     * 
     * @param procedimiento
     *            procedimiento a establecer
     */
    public void setProcedimiento(DefinicionProcedimientoCP procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Método de acceso a vigente.
     * 
     * @return vigente
     */
    public boolean isVigente() {
        return vigente;
    }

    /**
     * Método para establecer vigente.
     * 
     * @param vigente
     *            vigente a establecer
     */
    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    /**
     * Método de acceso a plazoInicio.
     * 
     * @return plazoInicio
     */
    public Date getPlazoInicio() {
        return plazoInicio;
    }

    /**
     * Método para establecer plazoInicio.
     * 
     * @param plazoInicio
     *            plazoInicio a establecer
     */
    public void setPlazoInicio(Date plazoInicio) {
        this.plazoInicio = plazoInicio;
    }

    /**
     * Método de acceso a plazoFin.
     * 
     * @return plazoFin
     */
    public Date getPlazoFin() {
        return plazoFin;
    }

    /**
     * Método para establecer plazoFin.
     * 
     * @param plazoFin
     *            plazoFin a establecer
     */
    public void setPlazoFin(Date plazoFin) {
        this.plazoFin = plazoFin;
    }

    /**
     * Método de acceso a urlInformacion.
     * 
     * @return urlInformacion
     */
    public String getUrlInformacion() {
        return urlInformacion;
    }

    /**
     * Método para establecer urlInformacion.
     * 
     * @param urlInformacion
     *            urlInformacion a establecer
     */
    public void setUrlInformacion(String urlInformacion) {
        this.urlInformacion = urlInformacion;
    }

    /**
     * Método de acceso a organoDestinoDir3.
     * 
     * @return organoDestinoDir3
     */
    public String getOrganoDestinoDir3() {
        return organoDestinoDir3;
    }

    /**
     * Método para establecer organoDestinoDir3.
     * 
     * @param organoDestinoDir3
     *            organoDestinoDir3 a establecer
     */
    public void setOrganoDestinoDir3(String organoDestinoDir3) {
        this.organoDestinoDir3 = organoDestinoDir3;
    }

    /**
     * Método de acceso a documentos.
     * 
     * @return documentos
     */
    public List<DefinicionDocumentoTramiteCP> getDocumentos() {
        return documentos;
    }

    /**
     * Método para establecer documentos.
     * 
     * @param documentos
     *            documentos a establecer
     */
    public void setDocumentos(List<DefinicionDocumentoTramiteCP> documentos) {
        this.documentos = documentos;
    }

    /**
     * Método de acceso a tasas.
     * 
     * @return tasas
     */
    public List<DefinicionTasaTramiteCP> getTasas() {
        return tasas;
    }

    /**
     * Método para establecer tasas.
     * 
     * @param tasas
     *            tasas a establecer
     */
    public void setTasas(List<DefinicionTasaTramiteCP> tasas) {
        this.tasas = tasas;
    }

}

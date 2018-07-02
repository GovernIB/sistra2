package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;

/**
 *
 * Datos de un documento de un paso en persistencia.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentoPasoPersistencia implements Serializable {
    /**
     * Id documento.
     */
    private String id;
    /**
     * Instancia documento (1 para todos excepto para los anexos
     * multiinstancia).
     */
    private int instancia;
    /**
     * Tipo documento (formulario, anexo, pago, justificante y variable flujo).
     */
    private TypeDocumentoPersistencia tipo;
    /**
     * Estado documento (vacío, rellenado ok, rellenado ko).
     */
    private TypeEstadoDocumento estado;

    /**
     * Fichero asociado al documento.
     */
    private ReferenciaFichero fichero;

    /**
     * Para formularios indica la vista pdf.
     */
    private ReferenciaFichero formularioPdf;
    /**
     * Nombre fichero asociado al anexo.
     */
    private String anexoNombreFichero;
    /**
     * Para anexos genéricos indica una descripción personalizada.
     */
    private String anexoDescripcionInstancia;
    /**
     * Para pagos indica el justificante de pago en pdf.
     */
    private ReferenciaFichero pagoJustificantePdf;
    /**
     * Para pagos indica el nif del sujeto pasivo.
     */
    private String pagoNifSujetoPasivo;
    /**
     * Para pagos indica el nif del sujeto pasivo.
     */
    private String pagoNumeroAutoliquidacion;
    /**
     * Para pagos incorrectos indica el estado incorrecto.
     */
    private TypeEstadoPagoIncorrecto pagoEstadoIncorrecto;
    /**
     * Para pagos incorrectos indica el codigo de error de pasarela de pagos.
     */
    private String pagoErrorPasarela;
    /**
     * Para pagos incorrectos indica el mensaje de error de pasarela de pagos.
     */
    private String pagoMensajeErrorPasarela;
    /**
     * Para registro, indica es un preregistro.
     */
    private TypeSiNo registroPreregistro;

    /**
     * Para registro, indica numero registro.
     */
    private String registroNumeroRegistro;

    /**
     * Para registro, indica fecha registro.
     */
    private Date registroFechaRegistro;

    /**
     * Método de acceso a id.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para establecer id.
     * 
     * @param id
     *            id a establecer
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Método de acceso a instancia.
     * 
     * @return instancia
     */
    public int getInstancia() {
        return instancia;
    }

    /**
     * Método para establecer instancia.
     * 
     * @param instancia
     *            instancia a establecer
     */
    public void setInstancia(int instancia) {
        this.instancia = instancia;
    }

    /**
     * Método de acceso a tipo.
     * 
     * @return tipo
     */
    public TypeDocumentoPersistencia getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     * 
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(TypeDocumentoPersistencia tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a estado.
     * 
     * @return estado
     */
    public TypeEstadoDocumento getEstado() {
        return estado;
    }

    /**
     * Método para establecer estado.
     * 
     * @param estado
     *            estado a establecer
     */
    public void setEstado(TypeEstadoDocumento estado) {
        this.estado = estado;
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
     * Método de acceso a formularioPdf.
     * 
     * @return formularioPdf
     */
    public ReferenciaFichero getFormularioPdf() {
        return formularioPdf;
    }

    /**
     * Método para establecer formularioPdf.
     * 
     * @param formularioPdf
     *            formularioPdf a establecer
     */
    public void setFormularioPdf(ReferenciaFichero formularioPdf) {
        this.formularioPdf = formularioPdf;
    }

    /**
     * Método de acceso a anexoNombreFichero.
     * 
     * @return anexoNombreFichero
     */
    public String getAnexoNombreFichero() {
        return anexoNombreFichero;
    }

    /**
     * Método para establecer anexoNombreFichero.
     * 
     * @param anexoNombreFichero
     *            anexoNombreFichero a establecer
     */
    public void setAnexoNombreFichero(String anexoNombreFichero) {
        this.anexoNombreFichero = anexoNombreFichero;
    }

    /**
     * Método de acceso a anexoDescripcionInstancia.
     * 
     * @return anexoDescripcionInstancia
     */
    public String getAnexoDescripcionInstancia() {
        return anexoDescripcionInstancia;
    }

    /**
     * Método para establecer anexoDescripcionInstancia.
     * 
     * @param anexoDescripcionInstancia
     *            anexoDescripcionInstancia a establecer
     */
    public void setAnexoDescripcionInstancia(String anexoDescripcionInstancia) {
        this.anexoDescripcionInstancia = anexoDescripcionInstancia;
    }

    /**
     * Método de acceso a pagoJustificantePdf.
     * 
     * @return pagoJustificantePdf
     */
    public ReferenciaFichero getPagoJustificantePdf() {
        return pagoJustificantePdf;
    }

    /**
     * Método para establecer pagoJustificantePdf.
     * 
     * @param pagoJustificantePdf
     *            pagoJustificantePdf a establecer
     */
    public void setPagoJustificantePdf(ReferenciaFichero pagoJustificantePdf) {
        this.pagoJustificantePdf = pagoJustificantePdf;
    }

    /**
     * Método de acceso a pagoNifSujetoPasivo.
     * 
     * @return pagoNifSujetoPasivo
     */
    public String getPagoNifSujetoPasivo() {
        return pagoNifSujetoPasivo;
    }

    /**
     * Método para establecer pagoNifSujetoPasivo.
     * 
     * @param pagoNifSujetoPasivo
     *            pagoNifSujetoPasivo a establecer
     */
    public void setPagoNifSujetoPasivo(String pagoNifSujetoPasivo) {
        this.pagoNifSujetoPasivo = pagoNifSujetoPasivo;
    }

    /**
     * Método de acceso a pagoNumeroAutoliquidacion.
     * 
     * @return pagoNumeroAutoliquidacion
     */
    public String getPagoNumeroAutoliquidacion() {
        return pagoNumeroAutoliquidacion;
    }

    /**
     * Método para establecer pagoNumeroAutoliquidacion.
     * 
     * @param pagoNumeroAutoliquidacion
     *            pagoNumeroAutoliquidacion a establecer
     */
    public void setPagoNumeroAutoliquidacion(String pagoNumeroAutoliquidacion) {
        this.pagoNumeroAutoliquidacion = pagoNumeroAutoliquidacion;
    }

    /**
     * Método de acceso a pagoEstadoIncorrecto.
     * 
     * @return pagoEstadoIncorrecto
     */
    public TypeEstadoPagoIncorrecto getPagoEstadoIncorrecto() {
        return pagoEstadoIncorrecto;
    }

    /**
     * Método para establecer pagoEstadoIncorrecto.
     * 
     * @param pagoEstadoIncorrecto
     *            pagoEstadoIncorrecto a establecer
     */
    public void setPagoEstadoIncorrecto(
            TypeEstadoPagoIncorrecto pagoEstadoIncorrecto) {
        this.pagoEstadoIncorrecto = pagoEstadoIncorrecto;
    }

    /**
     * Método de acceso a pagoErrorPasarela.
     * 
     * @return pagoErrorPasarela
     */
    public String getPagoErrorPasarela() {
        return pagoErrorPasarela;
    }

    /**
     * Método para establecer pagoErrorPasarela.
     * 
     * @param pagoErrorPasarela
     *            pagoErrorPasarela a establecer
     */
    public void setPagoErrorPasarela(String pagoErrorPasarela) {
        this.pagoErrorPasarela = pagoErrorPasarela;
    }

    /**
     * Método de acceso a pagoMensajeErrorPasarela.
     * 
     * @return pagoMensajeErrorPasarela
     */
    public String getPagoMensajeErrorPasarela() {
        return pagoMensajeErrorPasarela;
    }

    /**
     * Método para establecer pagoMensajeErrorPasarela.
     * 
     * @param pagoMensajeErrorPasarela
     *            pagoMensajeErrorPasarela a establecer
     */
    public void setPagoMensajeErrorPasarela(String pagoMensajeErrorPasarela) {
        this.pagoMensajeErrorPasarela = pagoMensajeErrorPasarela;
    }

    /**
     * Método de acceso a registroPreregistro.
     * 
     * @return registroPreregistro
     */
    public TypeSiNo getRegistroPreregistro() {
        return registroPreregistro;
    }

    /**
     * Método para establecer registroPreregistro.
     * 
     * @param registroPreregistro
     *            registroPreregistro a establecer
     */
    public void setRegistroPreregistro(TypeSiNo registroPreregistro) {
        this.registroPreregistro = registroPreregistro;
    }

    /**
     * Método de acceso a registroNumeroRegistro.
     * 
     * @return registroNumeroRegistro
     */
    public String getRegistroNumeroRegistro() {
        return registroNumeroRegistro;
    }

    /**
     * Método para establecer registroNumeroRegistro.
     * 
     * @param registroNumeroRegistro
     *            registroNumeroRegistro a establecer
     */
    public void setRegistroNumeroRegistro(String registroNumeroRegistro) {
        this.registroNumeroRegistro = registroNumeroRegistro;
    }

    /**
     * Método de acceso a registroFechaRegistro.
     * 
     * @return registroFechaRegistro
     */
    public Date getRegistroFechaRegistro() {
        return registroFechaRegistro;
    }

    /**
     * Método para establecer registroFechaRegistro.
     * 
     * @param registroFechaRegistro
     *            registroFechaRegistro a establecer
     */
    public void setRegistroFechaRegistro(Date registroFechaRegistro) {
        this.registroFechaRegistro = registroFechaRegistro;
    }

}

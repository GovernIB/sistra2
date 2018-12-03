package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeAviso;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Resultado de guardar la página: indica si se ha guardado bien, si no ha
 * pasado la validación y se ha generado un mensaje, etc. También indica si se
 * ha llegado al final del formulario en cuyo caso se deberá retornar al flujo
 * de tramitación invocando a guardar formulario del paso correspondiente
 * pasando el ticket de retorno.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoGuardarPagina implements Serializable {

    /**
     * Indica si se ha producido un error en la lógica de algún script (plugin
     * error). Los errores de script no controlados generarán una excepción de
     * servicio. Se debe forzar a cancelar el formulario.
     */
    private TypeSiNo error = TypeSiNo.NO;

    /**
     * Permite establecer un mensaje.
     */
    private String mensaje;

    /**
     * Indica si hay que generar mensaje de aviso. No para ejecución formulario.
     */
    private TypeSiNo aviso = TypeSiNo.NO;

    /**
     * Permite establecer un mensaje de aviso(WARNING / INFO).
     */
    private String mensajeAviso;

    /**
     * Permite establecer tipo mensaje de aviso: WARNING / INFO.
     */
    private TypeAviso tipoMensajeAviso;

    /**
     * Indica si se ha finalizado el formulario. En este caso se debe retornar
     * al flujo de tramitación mediante el ticket.
     */
    private TypeSiNo finalizado = TypeSiNo.NO;

    /**
     * En caso de que se haya finalizado el formulario se indica la url para
     * retornar al flujo de tramitación.
     */
    private String urlAsistente;

    /**
     * Método de acceso a error.
     *
     * @return error
     */
    public TypeSiNo getError() {
        return error;
    }

    /**
     * Método para establecer error.
     *
     * @param pError
     *            error a establecer
     */
    public void setError(final TypeSiNo pError) {
        error = pError;
    }

    /**
     * Método de acceso a mensaje.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Método para establecer mensaje.
     *
     * @param pMensaje
     *            mensaje a establecer
     */
    public void setMensaje(final String pMensaje) {
        mensaje = pMensaje;
    }

    /**
     * Método de acceso a finalizado.
     *
     * @return finalizado
     */
    public TypeSiNo getFinalizado() {
        return finalizado;
    }

    /**
     * Método para establecer finalizado.
     *
     * @param pFinalizado
     *            finalizado a establecer
     */
    public void setFinalizado(final TypeSiNo pFinalizado) {
        finalizado = pFinalizado;
    }

    /**
     * Método de acceso a ticket.
     *
     * @return ticket
     */
    public String getUrlAsistente() {
        return urlAsistente;
    }

    /**
     * Método para establecer ticket.
     *
     * @param pTicket
     *            ticket a establecer
     */
    public void setUrlAsistente(final String pTicket) {
        urlAsistente = pTicket;
    }

    /**
     * Método de acceso a aviso.
     *
     * @return aviso
     */
    public TypeSiNo getAviso() {
        return aviso;
    }

    /**
     * Método para establecer aviso.
     *
     * @param pAviso
     *            aviso a establecer
     */
    public void setAviso(final TypeSiNo pAviso) {
        aviso = pAviso;
    }

    /**
     * Método de acceso a mensajeAviso.
     *
     * @return mensajeAviso
     */
    public String getMensajeAviso() {
        return mensajeAviso;
    }

    /**
     * Método para establecer mensajeAviso.
     *
     * @param pMensajeAviso
     *            mensajeAviso a establecer
     */
    public void setMensajeAviso(final String pMensajeAviso) {
        mensajeAviso = pMensajeAviso;
    }

    /**
     * Método de acceso a tipoMensajeAviso.
     *
     * @return tipoMensajeAviso
     */
    public TypeAviso getTipoMensajeAviso() {
        return tipoMensajeAviso;
    }

    /**
     * Método para establecer tipoMensajeAviso.
     *
     * @param pTipoMensajeAviso
     *            tipoMensajeAviso a establecer
     */
    public void setTipoMensajeAviso(final TypeAviso pTipoMensajeAviso) {
        tipoMensajeAviso = pTipoMensajeAviso;
    }

}

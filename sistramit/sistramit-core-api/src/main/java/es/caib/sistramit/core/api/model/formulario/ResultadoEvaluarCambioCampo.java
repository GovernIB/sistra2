package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeAviso;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 *
 * Resultado de evaluar el cambio de un campo con dependencias en los scripts.
 * Se devuelve la lista de campos modificados, bien por sus valores, su estado
 * (enabled, disabled o readonly) o sus valores posibles (campo selector). Se
 * ejecuta también el script de validación de un campo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoEvaluarCambioCampo implements Serializable {

    /**
     * Indica si se ha producido un error en la lógica de algún script (plugin
     * error, los errores de script no controlados generarán una excepción de
     * servicio). Se deberá forzar a cancelar el formulario.
     */
    private TypeSiNo error = TypeSiNo.NO;

    /**
     * Indica si la validación del campo es correcta.
     */
    private TypeSiNo validacionCorrecta = TypeSiNo.SI;

    /**
     * Permite establecer un mensaje (en caso de error o validacion de campo
     * incorrecta).
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
     * Lista de campos para los que se ha modificado su valor.
     */
    private List<ValorCampo> valores = new ArrayList<>();

    /**
     * Lista de campos para los que se ha modificado su estado.
     */
    private List<ConfiguracionModificadaCampo> configuracion = new ArrayList<>();

    /**
     * Lista de campos de tipo selector para los que se modifican sus valores
     * posibles. Sólo para tipo lista desplegable y múltiple.Para tipo ventana
     * no hace falta para actualizar la página.
     */
    private List<ValoresPosiblesCampo> valoresPosibles = new ArrayList<>();

    /**
     * Método de acceso a valores.
     *
     * @return valores
     */
    public List<ValorCampo> getValores() {
        return valores;
    }

    /**
     * Método para establecer valores.
     *
     * @param pValores
     *            valores a establecer
     */
    public void setValores(final List<ValorCampo> pValores) {
        valores = pValores;
    }

    /**
     * Método de acceso a estados.
     *
     * @return estados
     */
    public List<ConfiguracionModificadaCampo> getConfiguracion() {
        return configuracion;
    }

    /**
     * Método para establecer estados.
     *
     * @param pConfiguracion
     *            el nuevo valor para configuracion
     */
    public void setConfiguracion(
            final List<ConfiguracionModificadaCampo> pConfiguracion) {
        configuracion = pConfiguracion;
    }

    /**
     * Método de acceso a valoresPosibles.
     *
     * @return valoresPosibles
     */
    public List<ValoresPosiblesCampo> getValoresPosibles() {
        return valoresPosibles;
    }

    /**
     * Método para establecer valoresPosibles.
     *
     * @param pValoresPosibles
     *            valoresPosibles a establecer
     */
    public void setValoresPosibles(
            final List<ValoresPosiblesCampo> pValoresPosibles) {
        valoresPosibles = pValoresPosibles;
    }

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
     * Método de acceso a validacionCorrecta.
     *
     * @return validacionCorrecta
     */
    public TypeSiNo getValidacionCorrecta() {
        return validacionCorrecta;
    }

    /**
     * Método para establecer validacionCorrecta.
     *
     * @param pValidacionCorrecta
     *            validacionCorrecta a establecer
     */
    public void setValidacionCorrecta(final TypeSiNo pValidacionCorrecta) {
        validacionCorrecta = pValidacionCorrecta;
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

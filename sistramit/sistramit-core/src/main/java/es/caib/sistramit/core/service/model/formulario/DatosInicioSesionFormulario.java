package es.caib.sistramit.core.service.model.formulario;

import java.io.Serializable;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;

/**
 * Datos de inicio de sesión en un gestor de formularios (propiedades comunes).
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInicioSesionFormulario implements Serializable {
    /**
     * Id sesión de tramitación.
     */
    private String idSesionTramitacion;
    /**
     * Id paso en el que se encuentra el formulario.
     */
    private String idPaso;
    /**
     * Idioma.
     */
    private String idioma;
    /**
     * Nivel autenticacion usuario que rellena el formulario.
     */
    private TypeAutenticacion nivelAutenticacion;
    /**
     * Usuario que rellena el formulario (si esta autenticado).
     */
    private DatosUsuario usuario;
    /**
     * Indica entorno de ejecución. Si el ATT esta configurado para el entorno
     * de desarrollo permite cambiar el entorno de ejecución para ejecutar sobre
     * preproducción. Si el ATT esta configurado para el entorno de producción
     * no se permite cambiar.
     */
    private TypeEntorno entorno;
    /**
     * Id tramite.
     */
    private String idTramite;
    /**
     * Versión trámite.
     */
    private int versionTramite;
    /**
     * Release trámite.
     */
    private int releaseTramite;
    /**
     * Id formulario.
     */
    private String idFormulario;
    /**
     * Xml con los datos actuales del formulario.
     */
    private byte[] xmlDatosActuales;
    /**
     * Parametros de apertura del formulario.
     */
    private ParametrosAperturaFormulario parametros;

    /**
     * Datos procedimiento.
     */
    private DefinicionTramiteCP infoTramiteCP;

    /** Id gestor formularios (para externos). */
    private String idGestorFormulariosExterno;

    /** Id formulario externo (para externos). */
    private String idFormularioExterno;

    /**
     * Método de acceso a idSesionTramitacion.
     *
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para establecer idSesionTramitacion.
     *
     * @param idSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Método de acceso a idPaso.
     *
     * @return idPaso
     */
    public String getIdPaso() {
        return idPaso;
    }

    /**
     * Método para establecer idPaso.
     *
     * @param idPaso
     *            idPaso a establecer
     */
    public void setIdPaso(String idPaso) {
        this.idPaso = idPaso;
    }

    /**
     * Método de acceso a idioma.
     *
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Método para establecer idioma.
     *
     * @param idioma
     *            idioma a establecer
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a nivelAutenticacion.
     *
     * @return nivelAutenticacion
     */
    public TypeAutenticacion getNivelAutenticacion() {
        return nivelAutenticacion;
    }

    /**
     * Método para establecer nivelAutenticacion.
     *
     * @param nivelAutenticacion
     *            nivelAutenticacion a establecer
     */
    public void setNivelAutenticacion(TypeAutenticacion nivelAutenticacion) {
        this.nivelAutenticacion = nivelAutenticacion;
    }

    /**
     * Método de acceso a usuario.
     *
     * @return usuario
     */
    public DatosUsuario getUsuario() {
        return usuario;
    }

    /**
     * Método para establecer usuario.
     *
     * @param usuario
     *            usuario a establecer
     */
    public void setUsuario(DatosUsuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método de acceso a entorno.
     *
     * @return entorno
     */
    public TypeEntorno getEntorno() {
        return entorno;
    }

    /**
     * Método para establecer entorno.
     *
     * @param entorno
     *            entorno a establecer
     */
    public void setEntorno(TypeEntorno entorno) {
        this.entorno = entorno;
    }

    /**
     * Método de acceso a idTramite.
     *
     * @return idTramite
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * Método para establecer idTramite.
     *
     * @param idTramite
     *            idTramite a establecer
     */
    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    /**
     * Método de acceso a versionTramite.
     *
     * @return versionTramite
     */
    public int getVersionTramite() {
        return versionTramite;
    }

    /**
     * Método para establecer versionTramite.
     *
     * @param versionTramite
     *            versionTramite a establecer
     */
    public void setVersionTramite(int versionTramite) {
        this.versionTramite = versionTramite;
    }

    /**
     * Método de acceso a releaseTramite.
     *
     * @return releaseTramite
     */
    public int getReleaseTramite() {
        return releaseTramite;
    }

    /**
     * Método para establecer releaseTramite.
     *
     * @param releaseTramite
     *            releaseTramite a establecer
     */
    public void setReleaseTramite(int releaseTramite) {
        this.releaseTramite = releaseTramite;
    }

    /**
     * Método de acceso a idFormulario.
     *
     * @return idFormulario
     */
    public String getIdFormulario() {
        return idFormulario;
    }

    /**
     * Método para establecer idFormulario.
     *
     * @param idFormulario
     *            idFormulario a establecer
     */
    public void setIdFormulario(String idFormulario) {
        this.idFormulario = idFormulario;
    }

    /**
     * Método de acceso a xmlDatosActuales.
     *
     * @return xmlDatosActuales
     */
    public byte[] getXmlDatosActuales() {
        return xmlDatosActuales;
    }

    /**
     * Método para establecer xmlDatosActuales.
     *
     * @param xmlDatosActuales
     *            xmlDatosActuales a establecer
     */
    public void setXmlDatosActuales(byte[] xmlDatosActuales) {
        this.xmlDatosActuales = xmlDatosActuales;
    }

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public ParametrosAperturaFormulario getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(ParametrosAperturaFormulario parametros) {
        this.parametros = parametros;
    }

    /**
     * Método de acceso a infoTramiteCP.
     *
     * @return infoTramiteCP
     */
    public DefinicionTramiteCP getInfoTramiteCP() {
        return infoTramiteCP;
    }

    /**
     * Método para establecer infoTramiteCP.
     *
     * @param infoTramiteCP
     *            infoTramiteCP a establecer
     */
    public void setInfoTramiteCP(DefinicionTramiteCP infoTramiteCP) {
        this.infoTramiteCP = infoTramiteCP;
    }

    /**
     * Método de acceso a idGestorFormulariosExterno.
     *
     * @return idGestorFormulariosExterno
     */
    public String getIdGestorFormulariosExterno() {
        return idGestorFormulariosExterno;
    }

    /**
     * Método para establecer idGestorFormulariosExterno.
     *
     * @param idGestorFormulariosExterno
     *            idGestorFormulariosExterno a establecer
     */
    public void setIdGestorFormulariosExterno(
            String idGestorFormulariosExterno) {
        this.idGestorFormulariosExterno = idGestorFormulariosExterno;
    }

    /**
     * Método de acceso a idFormularioExterno.
     *
     * @return idFormularioExterno
     */
    public String getIdFormularioExterno() {
        return idFormularioExterno;
    }

    /**
     * Método para establecer idFormularioExterno.
     *
     * @param idFormularioExterno
     *            idFormularioExterno a establecer
     */
    public void setIdFormularioExterno(String idFormularioExterno) {
        this.idFormularioExterno = idFormularioExterno;
    }

}

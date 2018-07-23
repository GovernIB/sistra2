package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;
import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DatosUsuario;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Datos de la instancia del trámite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosTramite implements Serializable {

    /**
     * Indica si el tramite es nuevo o se ha cargado de persistencia.
     */
    private boolean nuevo;

    /**
     * Identificador de sesión de tramitación.
     */
    private String idSesionTramitacion;

    /**
     * Estado del trámite.
     */
    private TypeEstadoTramite estadoTramite;

    /**
     * Id trámite.
     */
    private String idTramite;

    /**
     * Versión trámite.
     */
    private int versionTramite;

    /**
     * Entorno.
     */
    private TypeEntorno entorno;

    /**
     * Tipo de flujo.
     */
    private TypeFlujoTramitacion tipoFlujo;

    /**
     * Datos provenientes de Catalogo de Procedimientos.
     */
    private DefinicionTramiteCP definicionTramiteCP;

    /**
     * Parámetros de inicio.
     */
    private Map<String, String> parametrosInicio;

    /**
     * Nivel autenticación del usuario que ha iniciado el trámite.
     */
    private TypeAutenticacion nivelAutenticacion;

    /**
     * Metodo autenticacion inicio tramite.
     */
    private TypeMetodoAutenticacion metodoAutenticacionInicio;

    /**
     * Datos del usuario que ha iniciado el trámite.
     */
    private DatosUsuario iniciador;

    /**
     * Idioma de tramitación.
     */
    private String idioma;

    /**
     * Titulo del trámite (puede ser personalizado en el script de inicio).
     */
    private String tituloTramite;

    /**
     * Indica si el plazo se ha establecido de forma dinámica a través del
     * script de personalización del trámite. En este caso no se tomarán en
     * cuenta los plazos provinientes del catálogo de procedimientos.
     */
    private boolean plazoDinamico;

    /**
     * Plazo inicio (puede ser personalizado en el script de inicio).
     */
    private Date plazoInicio;

    /**
     * Plazo fin (puede ser personalizado en el script de inicio).
     */
    private Date plazoFin;

    /**
     * Indica último acceso al trámite (inicio o carga).
     */
    private Date ultimoAcceso;

    /**
     * Fecha de inicio.
     */
    private Date fechaInicio;

    /**
     * Fecha de caducidad (en caso de ser persistente con días de persistencia).
     */
    private Date fechaCaducidad;

    /**
     * Datos de los pasos de tramitación.
     */
    private final List<DatosPaso> datosPasos = new ArrayList<>();

    /**
     * Identificador del paso actual. En caso de ser nulo implicará que el
     * trámite se esta iniciando o se esta cargando.
     */
    private String idPasoActual;

    /**
     * Identificador del paso siguiente. Se calculará tras ir a un paso o
     * despues de ejecutar la accion sobre un paso.
     */
    private String idPasoSiguiente;

    /**
     * Accesibilidad de los pasos.
     */
    private final Map<String, Boolean> accesibilidadPasos = new HashMap<>();

    /**
     * Url de inicio del tramite.
     */
    private String urlInicio;

    /**
     * Indica si el trámite es vigente o no.
     */
    private boolean vigente;

    /**
     * Metodos permitidos de login.
     */
    private List<TypeAutenticacion> metodosLogin = new ArrayList<>();

    /**
     * Timestamp flujo.
     */
    private Date timestampFlujo;

    /**
     * Método para obtener el campo ultimoAcceso.
     *
     * @return the ultimoAcceso
     */
    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    /**
     * Método de acceso a idSesionTramitacion.
     *
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para settear el campo ultimoAcceso.
     *
     * @param pUltimoAcceso
     *            el campo ultimoAcceso a settear
     */
    public void setUltimoAcceso(final Date pUltimoAcceso) {
        ultimoAcceso = pUltimoAcceso;
    }

    /**
     * Método para establecer idSesionTramitacion.
     *
     * @param pIdSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
        idSesionTramitacion = pIdSesionTramitacion;
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
     * @param pIdioma
     *            idioma a establecer
     */
    public void setIdioma(final String pIdioma) {
        idioma = pIdioma;
    }

    /**
     * Obtiene los datos de un paso por id del paso.
     *
     * @param id
     *            del paso
     * @return datosPaso Datos del paso
     */
    public DatosPaso getDatosPaso(final String id) {
        DatosPaso dpr = null;
        for (final DatosPaso dp : this.datosPasos) {
            if (dp.getIdPaso().equals(id)) {
                dpr = dp;
                break;
            }
        }
        return dpr;
    }

    /**
     * Obtiene los datos de pasos por tipo de paso.
     *
     * @param tipoPaso
     *            Tipo de paso
     *
     * @return Lista de pasos del tipo indicado
     */
    public List<DatosPaso> getDatosPasos(final TypePaso tipoPaso) {
        final List<DatosPaso> res = new ArrayList<>();
        for (final DatosPaso dp : this.datosPasos) {
            if (dp.getTipo() == tipoPaso) {
                res.add(dp);
            }
        }
        return res;
    }

    /**
     * Obtiene el orden de un paso (empieza en 0).
     *
     * @param idPaso
     *            Id de paso
     *
     * @return Orden del paso. Devuelve -1 si no lo encuentra.
     */
    public int getOrdenPaso(final String idPaso) {
        int res = 0;
        boolean enc = false;
        for (final DatosPaso dp : this.datosPasos) {
            if (dp.getIdPaso().equals(idPaso)) {
                enc = true;
                break;
            }
            res++;
        }
        if (!enc) {
            res = ConstantesNumero.N_1;
        }
        return res;
    }

    /**
     * Obtiene los datos del paso actual.
     *
     * @return datosPaso Datos del paso actual
     */
    public DatosPaso getDatosPasoActual() {
        return getDatosPaso(this.getIdPasoActual());
    }

    /**
     * Obtiene indice del paso actual.
     *
     * @return indice del paso actual (empieza en 0).
     */
    public int getIndiceDatosPasoActual() {
        int i = 0;
        for (final DatosPaso dp : this.datosPasos) {
            if (dp.getIdPaso().equals(this.getIdPasoActual())) {
                break;
            }
            i++;
        }
        return i;
    }

    /**
     * Obtiene los datos del último paso.
     *
     * @return datosPaso Datos del paso actual
     */
    public DatosPaso getDatosPasoUltimo() {
        final DatosPaso dp = this.getDatosPasos()
                .get(this.getDatosPasos().size() - ConstantesNumero.N1);
        return dp;
    }

    /**
     * Elimina los datos de un paso.
     *
     * @param id
     *            Id del paso
     */
    public void removeDatosPaso(final String id) {
        for (final DatosPaso dp : this.datosPasos) {
            if (dp.getIdPaso().equals(id)) {
                this.datosPasos.remove(dp);
                break;
            }
        }
    }

    /**
     * Método de acceso a parametrosInicio.
     *
     * @return parametrosInicio
     */
    public Map<String, String> getParametrosInicio() {
        return parametrosInicio;
    }

    /**
     * Método para establecer parametrosInicio.
     *
     * @param pParametrosInicio
     *            parametrosInicio a establecer
     */
    public void setParametrosInicio(
            final Map<String, String> pParametrosInicio) {
        parametrosInicio = pParametrosInicio;
    }

    /**
     * Método de acceso a usuario.
     *
     * @return usuario
     */
    public DatosUsuario getIniciador() {
        return iniciador;
    }

    /**
     * Método para establecer usuario.
     *
     * @param pUsuario
     *            usuario a establecer
     */
    public void setIniciador(final DatosUsuario pUsuario) {
        iniciador = pUsuario;
    }

    /**
     * Método de acceso a idPasoActual.
     *
     * @return idPasoActual
     */
    public String getIdPasoActual() {
        return idPasoActual;
    }

    /**
     * Método para establecer idPasoActual.
     *
     * @param pIdPasoActual
     *            idPasoActual a establecer
     */
    public void setIdPasoActual(final String pIdPasoActual) {
        idPasoActual = pIdPasoActual;
    }

    /**
     * Método de acceso a tituloTramite.
     *
     * @return tituloTramite
     */
    public String getTituloTramite() {
        return tituloTramite;
    }

    /**
     * Método para establecer tituloTramite.
     *
     * @param pTituloTramite
     *            tituloTramite a establecer
     */
    public void setTituloTramite(final String pTituloTramite) {
        tituloTramite = pTituloTramite;
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
     * @param pPlazoInicio
     *            plazoInicio a establecer
     */
    public void setPlazoInicio(final Date pPlazoInicio) {
        plazoInicio = pPlazoInicio;
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
     * @param pPlazoFin
     *            plazoFin a establecer
     */
    public void setPlazoFin(final Date pPlazoFin) {
        plazoFin = pPlazoFin;
    }

    /**
     * Método de acceso a datosPasos.
     *
     * @return datosPasos
     */
    public List<DatosPaso> getDatosPasos() {
        return datosPasos;
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
     * @param pNivelAutenticacion
     *            nivelAutenticacion a establecer
     */
    public void setNivelAutenticacion(
            final TypeAutenticacion pNivelAutenticacion) {
        nivelAutenticacion = pNivelAutenticacion;
    }

    /**
     * Método de acceso a definición trámite en el Catálogo de Procedimientos.
     *
     * @return definicionTramiteCP definición trámite en el Catálogo de
     *         Procedimientos
     */
    public DefinicionTramiteCP getDefinicionTramiteCP() {
        return definicionTramiteCP;
    }

    /**
     * Método para establecer definición trámite en el Catálogo de
     * Procedimientos.
     *
     * @param pDefinicionTramiteCP
     *            definición trámite en el Catálogo de Procedimientos a
     *            establecer
     */
    public void setDefinicionTramiteCP(
            final DefinicionTramiteCP pDefinicionTramiteCP) {
        definicionTramiteCP = pDefinicionTramiteCP;
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
     * @param pIdTramite
     *            idTramite a establecer
     */
    public void setIdTramite(final String pIdTramite) {
        idTramite = pIdTramite;
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
     * @param pVersionTramite
     *            versionTramite a establecer
     */
    public void setVersionTramite(final int pVersionTramite) {
        versionTramite = pVersionTramite;
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
     * @param pEntorno
     *            entorno a establecer
     */
    public void setEntorno(final TypeEntorno pEntorno) {
        entorno = pEntorno;
    }

    /**
     * Método de acceso a tipoFlujo.
     *
     * @return tipoFlujo
     */
    public TypeFlujoTramitacion getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * Método para establecer tipoFlujo.
     *
     * @param pTipoFlujo
     *            tipoFlujo a establecer
     */
    public void setTipoFlujo(final TypeFlujoTramitacion pTipoFlujo) {
        tipoFlujo = pTipoFlujo;
    }

    /**
     * Método de acceso a plazoDinamico.
     *
     * @return plazoDinamico
     */
    public boolean isPlazoDinamico() {
        return plazoDinamico;
    }

    /**
     * Método para establecer plazoDinamico.
     *
     * @param pPlazoDinamico
     *            plazoDinamico a establecer
     */
    public void setPlazoDinamico(final boolean pPlazoDinamico) {
        plazoDinamico = pPlazoDinamico;
    }

    /**
     * Método de acceso a estadoTramite.
     *
     * @return estadoTramite
     */
    public TypeEstadoTramite getEstadoTramite() {
        return estadoTramite;
    }

    /**
     * Método para establecer estadoTramite.
     *
     * @param pEstadoTramite
     *            estadoTramite a establecer
     */
    public void setEstadoTramite(final TypeEstadoTramite pEstadoTramite) {
        estadoTramite = pEstadoTramite;
    }

    /**
     * Indica si el paso es accesible.
     *
     * @param idPaso
     *            Id paso
     * @param accesible
     *            Accesible
     */
    public void setAccesibilidadPaso(final String idPaso,
            final boolean accesible) {
        this.accesibilidadPasos.put(idPaso, accesible);
    }

    /**
     * Indica si el paso es accesible.
     *
     * @param idPaso
     *            Id paso return accesible
     * @return Indica si el paso es accesible
     */
    public boolean getAccesibilidadPaso(final String idPaso) {
        boolean res = false;
        final Boolean accesible = this.accesibilidadPasos.get(idPaso);
        if (accesible != null) {
            res = accesible.booleanValue();
        }
        return res;
    }

    /**
     * Método de acceso a idPasoSiguiente.
     *
     * @return idPasoSiguiente
     */
    public String getIdPasoSiguiente() {
        return idPasoSiguiente;
    }

    /**
     * Método para establecer idPasoSiguiente.
     *
     * @param pIdPasoSiguiente
     *            idPasoSiguiente a establecer
     */
    public void setIdPasoSiguiente(final String pIdPasoSiguiente) {
        idPasoSiguiente = pIdPasoSiguiente;
    }

    /**
     * Método de acceso a nuevo.
     *
     * @return nuevo
     */
    public boolean isNuevo() {
        return nuevo;
    }

    /**
     * Método para establecer nuevo.
     *
     * @param pNuevo
     *            nuevo a establecer
     */
    public void setNuevo(final boolean pNuevo) {
        nuevo = pNuevo;
    }

    /**
     * Método de acceso a urlInicio.
     *
     * @return urlInicio
     */
    public String getUrlInicio() {
        return urlInicio;
    }

    /**
     * Método para establecer urlInicio.
     *
     * @param pUrlInicio
     *            urlInicio a establecer
     */
    public void setUrlInicio(final String pUrlInicio) {
        urlInicio = pUrlInicio;
    }

    /**
     * Método de acceso a fechaCaducidad.
     *
     * @return fechaCaducidad
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Método para establecer fechaCaducidad.
     *
     * @param pFechaCaducidad
     *            fechaCaducidad a establecer
     */
    public void setFechaCaducidad(final Date pFechaCaducidad) {
        fechaCaducidad = pFechaCaducidad;
    }

    /**
     * Método de acceso a metodosLogin.
     *
     * @return metodosLogin
     */
    public List<TypeAutenticacion> getMetodosLogin() {
        return metodosLogin;
    }

    /**
     * Método para establecer metodosLogin.
     *
     * @param pMetodosLogin
     *            metodosLogin a establecer
     */
    public void setMetodosLogin(final List<TypeAutenticacion> pMetodosLogin) {
        metodosLogin = pMetodosLogin;
    }

    /**
     * @return the vigente
     */
    public boolean isVigente() {
        return vigente;
    }

    /**
     * @param pVigente
     *            the vigente to set
     */
    public void setVigente(final boolean pVigente) {
        vigente = pVigente;
    }

    /**
     * Método de acceso a metodoAutenticacionInicio.
     *
     * @return metodoAutenticacionInicio
     */
    public TypeMetodoAutenticacion getMetodoAutenticacionInicio() {
        return metodoAutenticacionInicio;
    }

    /**
     * Método para establecer metodoAutenticacionInicio.
     *
     * @param pMetodoAutenticacionInicio
     *            metodoAutenticacionInicio a establecer
     */
    public void setMetodoAutenticacionInicio(
            final TypeMetodoAutenticacion pMetodoAutenticacionInicio) {
        metodoAutenticacionInicio = pMetodoAutenticacionInicio;
    }

    /**
     * Método de acceso a timestampFlujo.
     *
     * @return timestampFlujo
     */
    public Date getTimestampFlujo() {
        return timestampFlujo;
    }

    /**
     * Método para establecer timestampFlujo.
     *
     * @param pTimestampFlujo
     *            timestampFlujo a establecer
     */
    public void setTimestampFlujo(final Date pTimestampFlujo) {
        timestampFlujo = pTimestampFlujo;
    }

    /**
     * Método de acceso a fechaInicio.
     * 
     * @return fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Método para establecer fechaInicio.
     * 
     * @param fechaInicio
     *            fechaInicio a establecer
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}

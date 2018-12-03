package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 *
 * Página formulario: datos necesarios para la carga de la página.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PaginaFormulario implements Serializable {

    /**
     * Id formulario al que pertenece la pagina.
     */
    private String idFormulario;

    /**
     * Html pagina.
     */
    private String html;

    /**
     * Configuracion de los campos.
     */
    private List<ConfiguracionCampo> configuracion = new ArrayList<>();

    /**
     * Valores de los campos.
     */
    private List<ValorCampo> valores;

    /**
     * Valores posibles de los campos selectores.
     */
    private List<ValoresPosiblesCampo> valoresPosibles;

    /**
     * Acciones personalizadas del formulario. Se establecerán en la última
     * página del formulario.
     */
    private List<AccionPersonalizada> acciones = new ArrayList<>();

    /**
     * Recursos estáticos del formulario. Se establecen las urls de estos
     * recursos estáticos para establecerse en el html del formulario.
     */
    private RecursosFormulario recursos;

    /**
     * Indica si hay una página anterior.
     */
    private TypeSiNo paginaAnterior = TypeSiNo.NO;

    /**
     * Constructor.
     *
     * @param pIdFormulario
     *            id formulario
     */
    public PaginaFormulario(final String pIdFormulario) {
        this.idFormulario = pIdFormulario;
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
     * @param pIdFormulario
     *            idFormulario a establecer
     */
    public void setIdFormulario(final String pIdFormulario) {
        idFormulario = pIdFormulario;
    }

    /**
     * Método de acceso a html.
     * 
     * @return html
     */
    public String getHtml() {
        return html;
    }

    /**
     * Método para establecer html.
     * 
     * @param html
     *            html a establecer
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * Método de acceso a configuracion.
     * 
     * @return configuracion
     */
    public List<ConfiguracionCampo> getConfiguracion() {
        return configuracion;
    }

    /**
     * Método para establecer configuracion.
     * 
     * @param configuracion
     *            configuracion a establecer
     */
    public void setConfiguracion(List<ConfiguracionCampo> configuracion) {
        this.configuracion = configuracion;
    }

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
     * @param valores
     *            valores a establecer
     */
    public void setValores(List<ValorCampo> valores) {
        this.valores = valores;
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
     * @param valoresPosibles
     *            valoresPosibles a establecer
     */
    public void setValoresPosibles(List<ValoresPosiblesCampo> valoresPosibles) {
        this.valoresPosibles = valoresPosibles;
    }

    /**
     * Método de acceso a acciones.
     * 
     * @return acciones
     */
    public List<AccionPersonalizada> getAcciones() {
        return acciones;
    }

    /**
     * Método para establecer acciones.
     * 
     * @param acciones
     *            acciones a establecer
     */
    public void setAcciones(List<AccionPersonalizada> acciones) {
        this.acciones = acciones;
    }

    /**
     * Método de acceso a recursos.
     * 
     * @return recursos
     */
    public RecursosFormulario getRecursos() {
        return recursos;
    }

    /**
     * Método para establecer recursos.
     * 
     * @param recursos
     *            recursos a establecer
     */
    public void setRecursos(RecursosFormulario recursos) {
        this.recursos = recursos;
    }

    /**
     * Método de acceso a paginaAnterior.
     * 
     * @return paginaAnterior
     */
    public TypeSiNo getPaginaAnterior() {
        return paginaAnterior;
    }

    /**
     * Método para establecer paginaAnterior.
     * 
     * @param paginaAnterior
     *            paginaAnterior a establecer
     */
    public void setPaginaAnterior(TypeSiNo paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

}

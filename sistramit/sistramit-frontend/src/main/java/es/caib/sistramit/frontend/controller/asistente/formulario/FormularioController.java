package es.caib.sistramit.frontend.controller.asistente.formulario;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.comun.types.TypeAviso;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampo;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoSelector;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoCP;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoEmail;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoFecha;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoId;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoOculto;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoPassword;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoTextoTelefono;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.ConfiguracionModificadaCampo;
import es.caib.sistramit.core.api.model.formulario.Imagen;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoExpReg;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoNormal;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoNumero;
import es.caib.sistramit.core.api.model.formulario.OpcionesCampoTextoTelefono;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.RecursosFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.ValorCampoIndexado;
import es.caib.sistramit.core.api.model.formulario.ValorCampoSimple;
import es.caib.sistramit.core.api.model.formulario.ValorIndexado;
import es.caib.sistramit.core.api.model.formulario.ValoresCampoVerificacion;
import es.caib.sistramit.core.api.model.formulario.ValoresPosiblesCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypeSelector;
import es.caib.sistramit.core.api.model.formulario.types.TypeSeparador;
import es.caib.sistramit.core.api.service.GestorFormulariosInternoService;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.model.RespuestaJSON;

/**
 * Interacción con gestor formularios interno.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/fm")
public final class FormularioController extends TramitacionController {

    /** Gestor formulario interno. */
    @Autowired
    private GestorFormulariosInternoService formService;

    /**
     * Carga la sesión iniciada en el gestor de formularios.
     *
     * @param ticket
     *            Ticket de acceso a la sesión del formulario
     * @return Datos página inicial del formulario.
     */
    @RequestMapping("/cargarFormulario.json")
    public ModelAndView cargarFormulario(
            @RequestParam("ticket") final String ticket) {

        // Carga sesion formulario y registrarlo en la sesion
        final String idSesionFormulario = formService.cargarSesion(ticket);
        this.registraSesionFormulario(idSesionFormulario);

        // Carga pagina inicial
        return cargarPaginaActual();
    }

    /**
     * Realiza la carga de la página actual.
     *
     * @return Datos de la página.
     */
    @RequestMapping("/cargarPaginaActual.json")
    public ModelAndView cargarPaginaActual() {

        final PaginaFormulario pf = new PaginaFormulario("F1");

        pf.setHtml("<html><body><img id='img1'/></body>formulario 1</html>");

        final List<ConfiguracionCampo> configuracion = new ArrayList<>();
        final List<ValorCampo> valores = new ArrayList<>();

        // Configuracion campos
        // - Texto normal
        final ConfiguracionCampoTextoNormal cTextNormal = new ConfiguracionCampoTextoNormal();
        cTextNormal.setId("cTextNormal");
        cTextNormal.setAyuda("ayuda en linea");
        final OpcionesCampoTextoNormal opcsTextNormal = new OpcionesCampoTextoNormal();
        opcsTextNormal.setLineas(1);
        opcsTextNormal.setTamanyo(100);
        cTextNormal.setOpciones(opcsTextNormal);
        configuracion.add(cTextNormal);
        valores.add(new ValorCampoSimple("cTextNormal", "Valor 1"));

        final ConfiguracionCampoTextoCP cTextCP = new ConfiguracionCampoTextoCP();
        cTextCP.setId("cTextCP");
        cTextCP.setAyuda("ayuda en linea");
        configuracion.add(cTextCP);
        valores.add(new ValorCampoSimple("cTextCP", "46001"));

        final ConfiguracionCampoTextoEmail cTextEmail = new ConfiguracionCampoTextoEmail();
        cTextEmail.setId("cTextEmail");
        cTextEmail.setAyuda("ayuda en linea");
        configuracion.add(cTextEmail);
        valores.add(new ValorCampoSimple("cTextEmail", "correo@email.es"));

        final ConfiguracionCampoTextoExpReg cTextExpreg = new ConfiguracionCampoTextoExpReg();
        cTextExpreg.setId("cTextExpreg");
        cTextExpreg.setAyuda("ayuda en linea");
        final OpcionesCampoTextoExpReg opcsTextExpreg = new OpcionesCampoTextoExpReg();
        opcsTextExpreg.setRegexp("^expregular$");
        cTextExpreg.setOpciones(opcsTextExpreg);
        configuracion.add(cTextExpreg);
        valores.add(new ValorCampoSimple("cTextExpreg", "xxxx"));

        final ConfiguracionCampoTextoFecha cTextoFecha = new ConfiguracionCampoTextoFecha();
        cTextoFecha.setId("cTextoFecha");
        cTextoFecha.setAyuda("ayuda en linea");
        configuracion.add(cTextoFecha);
        valores.add(new ValorCampoSimple("cTextoFecha", "01/01/2018"));

        final ConfiguracionCampoTextoId cTextoId = new ConfiguracionCampoTextoId();
        cTextoId.setId("cTextoId");
        cTextoId.setAyuda("ayuda en linea");
        configuracion.add(cTextoId);
        valores.add(new ValorCampoSimple("cTextoId", "123456"));

        final ConfiguracionCampoTextoNumero cTextoNumero = new ConfiguracionCampoTextoNumero();
        cTextoNumero.setId("cTextoNumero");
        cTextoNumero.setAyuda("ayuda en linea");
        final OpcionesCampoTextoNumero opcsTextoNumero = new OpcionesCampoTextoNumero();
        opcsTextoNumero.setDecimales(2);
        opcsTextoNumero.setEnteros(5);
        opcsTextoNumero.setSeparador(TypeSeparador.PUNTO_COMA);
        cTextoNumero.setOpciones(opcsTextoNumero);
        configuracion.add(cTextoNumero);
        valores.add(new ValorCampoSimple("cTextoNumero", "1.456,72"));

        final ConfiguracionCampoTextoOculto cTextoOculto = new ConfiguracionCampoTextoOculto();
        cTextoOculto.setId("cTextoOculto");
        configuracion.add(cTextoOculto);
        valores.add(new ValorCampoSimple("cTextoOculto", "oculto"));

        final ConfiguracionCampoTextoPassword cTextoPassword = new ConfiguracionCampoTextoPassword();
        cTextoPassword.setId("cTextoPassword");
        cTextoPassword.setAyuda("ayuda en linea");
        configuracion.add(cTextoPassword);
        valores.add(new ValorCampoSimple("cTextoPassword", "password"));

        final ConfiguracionCampoTextoTelefono cTextoTelefono = new ConfiguracionCampoTextoTelefono();
        cTextoTelefono.setId("cTextoTelefono");
        cTextoTelefono.setAyuda("ayuda en linea");
        final OpcionesCampoTextoTelefono opcsTextoTelefono = new OpcionesCampoTextoTelefono();
        opcsTextoTelefono.setMovil(TypeSiNo.SI);
        cTextoTelefono.setOpciones(opcsTextoTelefono);
        configuracion.add(cTextoTelefono);
        valores.add(new ValorCampoSimple("cTextoTelefono", "961234567"));

        final ConfiguracionCampoSelector cSelector = new ConfiguracionCampoSelector();
        cSelector.setId("cSelector");
        cSelector.setAyuda("ayuda en linea");
        cSelector.setContenido(TypeSelector.LISTA);
        configuracion.add(cSelector);
        valores.add(new ValorCampoIndexado("cSelector", "1", "Opc 1"));

        final ConfiguracionCampoVerificacion cVerificacion = new ConfiguracionCampoVerificacion();
        cVerificacion.setId("cVerificacion");
        cVerificacion.setAyuda("ayuda en linea");
        final ValoresCampoVerificacion valoresCheck = new ValoresCampoVerificacion();
        valoresCheck.setChecked("valorChecked");
        valoresCheck.setNoChecked("valorNoChecked");
        cVerificacion.setValores(valoresCheck);
        configuracion.add(cVerificacion);
        valores.add(new ValorCampoSimple("cVerificacion", "valorChecked"));

        pf.setConfiguracion(configuracion);
        pf.setValores(valores);

        // Valores posibles selectores
        final List<ValoresPosiblesCampo> valoresPosibles = generarValoresPosibles();
        pf.setValoresPosibles(valoresPosibles);

        // Recursos formularios
        final RecursosFormulario recursos = new RecursosFormulario();
        final Imagen img = new Imagen();
        img.setId("img1");
        img.setSrc("http://www.caib.es/govern/imgs/svg/logo.svg");
        recursos.getImagenes().add(img);
        pf.setRecursos(recursos);

        // Respuesta
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(pf);
        return generarJsonView(res);
    }

    private List<ValoresPosiblesCampo> generarValoresPosibles() {
        final List<ValoresPosiblesCampo> valoresPosibles = new ArrayList<>();

        final ValoresPosiblesCampo vpcSU = new ValoresPosiblesCampo();
        vpcSU.setId("cSelector");
        final List<ValorIndexado> valoresSU = new ArrayList<>();
        valoresSU.add(new ValorIndexado("A", "Opc A"));
        valoresSU.add(new ValorIndexado("B", "Opc B"));
        vpcSU.setValores(valoresSU);
        valoresPosibles.add(vpcSU);

        return valoresPosibles;

    }

    /**
     * Realiza la carga de la página anterior.
     *
     * @return Datos de la página.
     */
    @RequestMapping("/cargarPaginaAnterior.json")
    public ModelAndView cargarPaginaAnterior() {
        return null;
    }

    /**
     * Evalua el cambio de una página de un formulario y calcula el valor los
     * campos según los scripts del formulario.
     *
     * @param idCampo
     *            Id campo que se esta modificando
     * @param request
     *            Request para obtener los datos actuales de la página en el
     *            cliente
     * @return Datos de la página resultantes que deben refrescarse en el
     *         cliente
     */
    @RequestMapping("/evaluarCambioCampo.json")
    public ModelAndView evaluarCambioCampoPagina(

            @RequestParam("idCampo") final String idCampo,
            final HttpServletRequest request) {

        final ResultadoEvaluarCambioCampo re = new ResultadoEvaluarCambioCampo();
        re.setValidacionCorrecta(TypeSiNo.SI);
        re.setAviso(TypeSiNo.SI);
        re.setMensajeAviso("Mensaje de aviso");
        re.setTipoMensajeAviso(TypeAviso.INFO);
        final List<ConfiguracionModificadaCampo> confModif = new ArrayList<>();
        ConfiguracionModificadaCampo cm = new ConfiguracionModificadaCampo();
        cm.setId("cTextoTelefono");
        cm.setSoloLectura(TypeSiNo.SI);
        confModif.add(cm);
        cm = new ConfiguracionModificadaCampo();
        cm.setId("cTextoNumero");
        cm.setSoloLectura(TypeSiNo.SI);
        confModif.add(cm);
        re.setConfiguracion(confModif);
        final List<ValorCampo> valores = new ArrayList<>();
        valores.add(new ValorCampoSimple("cTextNormal", "Valor 1"));
        valores.add(new ValorCampoSimple("cTextCP", "46001"));
        re.setValores(valores);
        re.setValoresPosibles(generarValoresPosibles());

        // Devolvemos respuesta
        final RespuestaJSON resp = new RespuestaJSON();
        resp.setDatos(re);

        return generarJsonView(resp);
    }

    /**
     * Guarda los datos de la página.
     *
     * @param request
     *            Datos de la página
     * @return Resultado de guardar la página: indica si se ha guardado bien, si
     *         se ha llegado al fin del formulario, si no ha pasado la
     *         validación y se ha generado un mensaje, etc. En caso de llegar al
     *         fin del formulario se indicará el ticket de acceso al XML y PDF
     *         generados.
     */
    @RequestMapping("/guardarPagina.json")
    public ModelAndView guardarPagina(final HttpServletRequest request) {

        final ResultadoGuardarPagina rgp = new ResultadoGuardarPagina();
        rgp.setFinalizado(TypeSiNo.SI);
        rgp.setAviso(TypeSiNo.SI);
        rgp.setTipoMensajeAviso(TypeAviso.INFO);
        rgp.setMensajeAviso("Mensaje aviso");
        rgp.setUrlAsistente("http://vuelta_asistente");

        // Devolvemos respuesta
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(rgp);
        return generarJsonView(res);
    }

    /**
     * Cancela el rellenado del formulario.
     *
     * @return No retorna ningún valor especial. Si tiene éxito se deberá
     *         recargar el paso actual.
     */
    @RequestMapping("/cancelar.json")
    public ModelAndView cancelar() {
        final RespuestaJSON res = new RespuestaJSON();
        return generarJsonView(res);
    }

}

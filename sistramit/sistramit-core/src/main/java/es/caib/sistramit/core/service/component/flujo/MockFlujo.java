package es.caib.sistramit.core.service.component.flujo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Anexo;
import es.caib.sistramit.core.api.model.flujo.DatosGuardarJustificante;
import es.caib.sistramit.core.api.model.flujo.DescripcionPaso;
import es.caib.sistramit.core.api.model.flujo.DetallePaso;
import es.caib.sistramit.core.api.model.flujo.DetallePasoAnexar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoDebeSaber;
import es.caib.sistramit.core.api.model.flujo.DetallePasoGuardar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoPagar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRegistrar;
import es.caib.sistramit.core.api.model.flujo.DetallePasoRellenar;
import es.caib.sistramit.core.api.model.flujo.DetallePasos;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.DocumentoRegistro;
import es.caib.sistramit.core.api.model.flujo.DocumentosRegistroPorTipo;
import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.model.flujo.Fichero;
import es.caib.sistramit.core.api.model.flujo.Formulario;
import es.caib.sistramit.core.api.model.flujo.Pago;
import es.caib.sistramit.core.api.model.flujo.PasoLista;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.PlantillaAnexo;
import es.caib.sistramit.core.api.model.flujo.SoporteOpcion;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoTramite;
import es.caib.sistramit.core.api.model.flujo.types.TypeFlujoTramitacion;
import es.caib.sistramit.core.api.model.flujo.types.TypeObligatoriedad;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;
import es.caib.sistramit.core.api.model.flujo.types.TypePlantillaAnexo;
import es.caib.sistramit.core.api.model.flujo.types.TypePresentacion;
import es.caib.sistramit.core.api.model.security.UsuarioAutenticadoInfo;

public class MockFlujo {

    public static DetalleTramite generarDetalleTramite(
            String idSesionTramitacion,
            UsuarioAutenticadoInfo usuarioAutenticado, String idPasoActual) {

        final DetalleTramite dt = new DetalleTramite();
        dt.setEntorno(TypeEntorno.DESARROLLO);
        dt.setIdSesionTramitacion(idSesionTramitacion);
        dt.setIdTramite("TRAM1");
        dt.setTitulo("Tramite 1");
        dt.setIdioma("es");
        dt.setPersistente(TypeSiNo.SI);
        dt.setDiasPersistencia(5);
        dt.setUsuario(usuarioAutenticado);
        dt.setTipoFlujo(TypeFlujoTramitacion.NORMALIZADO);
        dt.setFechaDefinicion((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"))
                .format(new Date()));
        dt.setEntidad(generarConfiguracionEntidad());
        dt.setIdPasoActual(idPasoActual);
        return dt;
    }

    public static DetallePasos generarDetallePasos(String idPasoActual) {

        final TypePaso pasoActual = TypePaso.fromString(idPasoActual);
        final TypePaso pasoAnterior = pasoAnterior(pasoActual);
        final TypePaso pasoSiguiente = pasoSiguiente(pasoActual);

        final DetallePasos detallePasos = new DetallePasos();
        detallePasos.setEstado(TypeEstadoTramite.RELLENANDO);
        detallePasos.setAnterior(
                pasoAnterior != null ? pasoAnterior.toString() : null);
        detallePasos.setSiguiente(
                pasoSiguiente != null ? pasoSiguiente.toString() : null);
        detallePasos.setPasos(generarListaPasos(pasoActual));
        detallePasos.setActual(generarDetallePaso(pasoActual));
        return detallePasos;
    }

    public static Entidad generarConfiguracionEntidad() {
        final Entidad e = new Entidad();
        e.setContacto("Contacto <strong>HTML</strong>");
        e.setCss("http://url/css");
        e.setLogo("http://url/logo");
        e.setInfoLOPD("Info <strong>LOPD</strong>");
        e.setNombre("Entidad 1");
        e.setSoporteEmail("soporte@entidad.es");
        final List<SoporteOpcion> soporteOpciones = new ArrayList<>();
        SoporteOpcion opcion;
        opcion = new SoporteOpcion();
        opcion.setCodigo("1");
        opcion.setTitulo("Problemas con firma");
        opcion.setDescripcion("Problemas cuando vas a firmar y no va");
        soporteOpciones.add(opcion);
        opcion = new SoporteOpcion();
        opcion.setCodigo("2");
        opcion.setTitulo("Problemas con registro");
        opcion.setDescripcion("Problemas cuando vas a registrar y no va");
        soporteOpciones.add(opcion);
        e.setSoporteOpciones(soporteOpciones);
        e.setSoporteTelefono("123");
        e.setSoporteUrl("http://url/soporte");
        e.setUrlCarpeta("http://url/carpeta");
        return e;
    }

    private static DetallePaso generarDetallePaso(TypePaso tipoPaso) {
        DetallePaso res = null;
        switch (tipoPaso) {
        case DEBESABER:
            res = generarPasoDS();
            break;
        case RELLENAR:
            res = generarPasoRF();
            break;
        case ANEXAR:
            res = generarPasoAN();
            break;
        case PAGAR:
            res = generarPasoPT();
            break;
        case REGISTRAR:
            res = generarPasoRG();
            break;
        case GUARDAR:
            res = generarPasoGU();
            break;
        }

        return res;
    }

    private static DetallePaso generarPasoGU() {
        final DetallePasoGuardar dp = new DetallePasoGuardar();
        dp.setId(TypePaso.GUARDAR.toString());
        dp.setCompletado(TypeSiNo.SI);
        dp.setSoloLectura(TypeSiNo.SI);

        final DatosGuardarJustificante justificante = new DatosGuardarJustificante();
        justificante.setAsunto("Asunto tal y tal");
        justificante.setNumero("reg1234");
        justificante.setFecha("01/01/2018 12:00:00");
        justificante
                .setSolicitante(new Persona("11111111H", "Jose García García"));
        justificante.setDocumentos(generarDocsRegistro(true));
        dp.setJustificante(justificante);

        dp.setInstruccionesTramitacion(
                "Instrucciones <strong>fin de tramitación</strong>");

        return dp;
    }

    private static DetallePaso generarPasoRG() {
        final DetallePasoRegistrar dp = new DetallePasoRegistrar();
        dp.setId(TypePaso.REGISTRAR.toString());
        dp.setCompletado(TypeSiNo.NO);
        dp.setSoloLectura(TypeSiNo.NO);
        dp.setDocumentos(generarDocsRegistro(false));
        return dp;
    }

    private static List<DocumentosRegistroPorTipo> generarDocsRegistro(
            boolean registrado) {
        final List<DocumentosRegistroPorTipo> documentosRegistro = new ArrayList<>();
        DocumentosRegistroPorTipo drt;
        final List<DocumentoRegistro> listaDocs;
        DocumentoRegistro dr;
        List<Persona> firmantes;

        // Formularios
        drt = new DocumentosRegistroPorTipo();
        drt.setTipo(TypeDocumento.FORMULARIO);
        listaDocs = new ArrayList<>();
        dr = new DocumentoRegistro();
        dr.setId("F1-1");
        dr.setFirmar(TypeSiNo.SI);
        firmantes = new ArrayList<>();
        firmantes.add(new Persona("11111111H", "Jose García García"));
        dr.setFirmantes(firmantes);
        dr.setDescargable(TypeSiNo.SI);
        dr.setTitulo("Formulario 1");
        listaDocs.add(dr);
        drt.setListado(listaDocs);

        // Anexos
        drt = new DocumentosRegistroPorTipo();
        drt.setTipo(TypeDocumento.ANEXO);

        dr = new DocumentoRegistro();
        dr.setId("A1");
        dr.setInstancia(1);
        dr.setFirmar(TypeSiNo.SI);
        firmantes = new ArrayList<>();
        firmantes.add(new Persona("11111111H", "Jose García García"));
        dr.setFirmantes(firmantes);
        dr.setDescargable(TypeSiNo.SI);
        dr.setTitulo("Anexo 1-1");
        listaDocs.add(dr);

        dr = new DocumentoRegistro();
        dr.setId("A1");
        dr.setInstancia(2);
        dr.setFirmar(TypeSiNo.SI);
        firmantes = new ArrayList<>();
        firmantes.add(new Persona("11111111H", "Jose García García"));
        dr.setFirmantes(firmantes);
        dr.setDescargable(TypeSiNo.SI);
        dr.setTitulo("Anexo 1-2");
        listaDocs.add(dr);

        drt.setListado(listaDocs);

        // Pagos
        drt = new DocumentosRegistroPorTipo();
        drt.setTipo(TypeDocumento.PAGO);
        dr = new DocumentoRegistro();
        dr.setId("P1-1");
        dr.setDescargable(TypeSiNo.SI);
        dr.setTitulo("Pago 1");
        listaDocs.add(dr);
        drt.setListado(listaDocs);

        documentosRegistro.add(drt);
        return documentosRegistro;
    }

    private static DetallePaso generarPasoPT() {
        final DetallePasoPagar dp = new DetallePasoPagar();
        dp.setId(TypePaso.PAGAR.toString());
        dp.setCompletado(TypeSiNo.NO);
        dp.setSoloLectura(TypeSiNo.NO);

        final List<Pago> pagos = new ArrayList<>();
        Pago f;
        f = new Pago();
        f.setId("P1");
        f.setObligatorio(TypeObligatoriedad.OBLIGATORIO);
        f.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
        f.setTitulo("Formulario 1");
        f.setPresentacion(TypePresentacion.ELECTRONICA);
        pagos.add(f);

        dp.setPagos(pagos);

        return dp;
    }

    private static DetallePaso generarPasoAN() {
        final DetallePasoAnexar dp = new DetallePasoAnexar();
        dp.setId(TypePaso.ANEXAR.toString());
        dp.setCompletado(TypeSiNo.NO);
        dp.setSoloLectura(TypeSiNo.NO);

        final List<Anexo> anexos = new ArrayList<>();
        Anexo anexo;
        anexo = new Anexo();
        anexo.setId("A1");
        anexo.setMaxInstancias(1);
        anexo.setObligatorio(TypeObligatoriedad.OBLIGATORIO);
        anexo.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
        anexo.setTitulo("Anexo 1");
        anexo.setExtensiones("txt,doc,pdf");
        anexo.setTamMax("1MB");
        anexo.setAyuda("Ayuda html <strong> para rellenar fichero");
        final PlantillaAnexo plantilla = new PlantillaAnexo();
        plantilla.setTipo(TypePlantillaAnexo.INTERNA);
        anexo.setPlantilla(plantilla);
        anexo.setConvertirPDF(TypeSiNo.SI);
        anexo.setPresentacion(TypePresentacion.ELECTRONICA);

        final List<Fichero> ficheros = new ArrayList<>();
        final Fichero f = new Fichero();
        f.setTitulo("Documento X");
        f.setFichero("fichero.pdf");

        ficheros.add(f);

        anexo.setFicheros(ficheros);

        anexos.add(anexo);

        anexo = new Anexo();
        anexo.setId("A2");
        anexo.setMaxInstancias(1);
        anexo.setPresentacion(TypePresentacion.ELECTRONICA);
        anexo.setObligatorio(TypeObligatoriedad.DEPENDIENTE);
        anexo.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
        anexo.setTitulo("Anexo 2");
        final PlantillaAnexo plantillae = new PlantillaAnexo();
        plantillae.setTipo(TypePlantillaAnexo.EXTERNA);
        plantillae.setUrl("http://www.google.es");
        anexo.setPlantilla(plantillae);
        anexos.add(anexo);

        dp.setAnexos(anexos);

        return dp;
    }

    private static DetallePaso generarPasoRF() {
        final DetallePasoRellenar dp = new DetallePasoRellenar();
        dp.setId(TypePaso.RELLENAR.toString());
        dp.setCompletado(TypeSiNo.NO);
        dp.setSoloLectura(TypeSiNo.NO);

        final List<Formulario> formularios = new ArrayList<>();
        Formulario f;
        f = new Formulario();
        f.setId("F1");
        f.setObligatorio(TypeObligatoriedad.OBLIGATORIO);
        f.setRellenado(TypeEstadoDocumento.RELLENADO_CORRECTAMENTE);
        f.setTitulo("Formulario 1");
        formularios.add(f);
        f = new Formulario();
        f.setId("F2");
        f.setObligatorio(TypeObligatoriedad.DEPENDIENTE);
        f.setRellenado(TypeEstadoDocumento.SIN_RELLENAR);
        f.setTitulo("Formulario 2");
        formularios.add(f);

        dp.setFormularios(formularios);

        return dp;
    }

    private static List<PasoLista> generarListaPasos(final TypePaso tipoPaso) {

        final List<PasoLista> listaPasos = new ArrayList<>();

        final TypePaso tiposPasosLista[] = {TypePaso.DEBESABER,
                TypePaso.RELLENAR, TypePaso.ANEXAR, TypePaso.PAGAR,
                TypePaso.REGISTRAR, TypePaso.GUARDAR};

        for (final TypePaso tipoPasoLista : tiposPasosLista) {
            final PasoLista pasoLista = new PasoLista();
            pasoLista.setTipo(tipoPasoLista);
            pasoLista.setId(pasoLista.getTipo().toString());
            pasoLista.setAccesible(TypeSiNo.SI);
            pasoLista.setCompletado(
                    esPasoAnterior(tipoPaso, pasoLista.getTipo()));
            listaPasos.add(pasoLista);
        }

        return listaPasos;
    }

    private static TypeSiNo esPasoAnterior(final TypePaso pasoActual,
            TypePaso tipoPasoLista) {

        TypeSiNo res = TypeSiNo.NO;

        switch (tipoPasoLista) {
        case DEBESABER:
            res = TypeSiNo.NO;
            break;
        case RELLENAR:
            if (pasoActual == TypePaso.DEBESABER) {
                res = TypeSiNo.SI;
            }
            break;
        case ANEXAR:
            if (pasoActual == TypePaso.DEBESABER
                    || pasoActual == TypePaso.RELLENAR) {
                res = TypeSiNo.SI;
            }
            break;
        case PAGAR:
            if (pasoActual == TypePaso.DEBESABER
                    || pasoActual == TypePaso.RELLENAR
                    || pasoActual == TypePaso.ANEXAR) {
                res = TypeSiNo.SI;
            }
            break;
        case REGISTRAR:
            if (pasoActual == TypePaso.DEBESABER
                    || pasoActual == TypePaso.RELLENAR
                    || pasoActual == TypePaso.ANEXAR
                    || pasoActual == TypePaso.PAGAR) {
                res = TypeSiNo.SI;
            }
            break;
        case GUARDAR:
            if (pasoActual == TypePaso.DEBESABER
                    || pasoActual == TypePaso.RELLENAR
                    || pasoActual == TypePaso.ANEXAR
                    || pasoActual == TypePaso.PAGAR
                    || pasoActual == TypePaso.REGISTRAR) {
                res = TypeSiNo.SI;
            }
            break;
        }

        return pasoActual == tipoPasoLista ? TypeSiNo.SI : TypeSiNo.NO;
    }

    private static DetallePasoDebeSaber generarPasoDS() {

        final List<DescripcionPaso> pasos = new ArrayList<>();
        pasos.add(crearDescripcionPaso(TypePaso.DEBESABER));
        pasos.add(crearDescripcionPaso(TypePaso.RELLENAR));
        pasos.add(crearDescripcionPaso(TypePaso.ANEXAR));
        pasos.add(crearDescripcionPaso(TypePaso.PAGAR));
        pasos.add(crearDescripcionPaso(TypePaso.REGISTRAR));
        pasos.add(crearDescripcionPaso(TypePaso.GUARDAR));

        final DetallePasoDebeSaber ds = new DetallePasoDebeSaber();
        ds.setId(TypePaso.DEBESABER.toString());
        ds.setCompletado(TypeSiNo.NO);
        ds.setSoloLectura(TypeSiNo.NO);
        ds.setPasos(pasos);
        ds.setInstrucciones("Instrucciones <strong>inicio</strong> trámite");

        return ds;
    }

    private static DescripcionPaso crearDescripcionPaso(
            final TypePaso tipoPaso) {
        DescripcionPaso descPaso;
        descPaso = new DescripcionPaso();
        descPaso.setId(tipoPaso.toString());
        descPaso.setTipo(tipoPaso);
        return descPaso;
    }

    private static TypePaso pasoAnterior(final TypePaso pasoActual) {

        TypePaso res = null;

        switch (pasoActual) {
        case DEBESABER:
            res = null;
            break;
        case RELLENAR:
            res = TypePaso.DEBESABER;
            break;
        case ANEXAR:
            res = TypePaso.RELLENAR;
            break;
        case PAGAR:
            res = TypePaso.ANEXAR;
            break;
        case REGISTRAR:
            res = TypePaso.PAGAR;
            break;
        case GUARDAR:
            res = null;
            break;
        }

        return res;
    }

    private static TypePaso pasoSiguiente(final TypePaso pasoActual) {

        TypePaso res = null;

        switch (pasoActual) {
        case DEBESABER:
            res = TypePaso.RELLENAR;
            break;
        case RELLENAR:
            res = TypePaso.ANEXAR;
            break;
        case ANEXAR:
            res = TypePaso.PAGAR;
            break;
        case PAGAR:
            res = TypePaso.REGISTRAR;
            break;
        case REGISTRAR:
            res = TypePaso.GUARDAR;
            break;
        case GUARDAR:
            res = null;
            break;
        }

        return res;
    }

}

package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoDebeSaber;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeFormularioObligatoriedad;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RAnexoTramite;
import es.caib.sistrages.rest.api.interna.RAnexoTramiteAyuda;
import es.caib.sistrages.rest.api.interna.RAnexoTramitePresentacionElectronica;
import es.caib.sistrages.rest.api.interna.RComponente;
import es.caib.sistrages.rest.api.interna.RComponenteAviso;
import es.caib.sistrages.rest.api.interna.RComponenteCheckbox;
import es.caib.sistrages.rest.api.interna.RComponenteSeccion;
import es.caib.sistrages.rest.api.interna.RComponenteSelector;
import es.caib.sistrages.rest.api.interna.RComponenteTextbox;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RFormularioExterno;
import es.caib.sistrages.rest.api.interna.RFormularioInterno;
import es.caib.sistrages.rest.api.interna.RFormularioTramite;
import es.caib.sistrages.rest.api.interna.RLineaComponentes;
import es.caib.sistrages.rest.api.interna.RListaDominio;
import es.caib.sistrages.rest.api.interna.RPaginaFormulario;
import es.caib.sistrages.rest.api.interna.RPagoTramite;
import es.caib.sistrages.rest.api.interna.RParametroDominio;
import es.caib.sistrages.rest.api.interna.RPasoTramitacion;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionAnexar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionDebeSaber;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionPagar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRegistrar;
import es.caib.sistrages.rest.api.interna.RPasoTramitacionRellenar;
import es.caib.sistrages.rest.api.interna.RPlantillaFormulario;
import es.caib.sistrages.rest.api.interna.RPropiedadesCampo;
import es.caib.sistrages.rest.api.interna.RPropiedadesTextoExpRegular;
import es.caib.sistrages.rest.api.interna.RPropiedadesTextoIdentificacion;
import es.caib.sistrages.rest.api.interna.RPropiedadesTextoNormal;
import es.caib.sistrages.rest.api.interna.RPropiedadesTextoNumero;
import es.caib.sistrages.rest.api.interna.RValorListaFija;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.interna.RVersionTramiteControlAcceso;
import es.caib.sistrages.rest.api.interna.RVersionTramitePropiedades;
import es.caib.sistrages.rest.utils.AdapterUtils;

/**
 * Adapter para convertir a modelo rest.
 *
 * @author Indra
 *
 */
@Component
public class VersionTramiteAdapter {

    /** Servicio rest. */
    @Autowired
    private RestApiInternaService restApiService;

    /**
     * Convierte a entidad rest RVersionTramite
     *
     * @param idtramite
     * @param tv
     * @param idioma
     * @param idiomaDefecto
     * @return RVersionTramite
     */
    public RVersionTramite convertir(final String idtramite,
            final TramiteVersion tv, final String idioma,
            final String idiomaDefecto) {

        List<String> idiSoportados;
        String idiRes;

        idiSoportados = Arrays.asList(tv.getIdiomasSoportados()
                .split(AdapterUtils.SEPARADOR_IDIOMAS));

        // El idioma a usar sigue este orden:
        // 1) idioma si es un idioma soportado
        // 2) idioma por defecto si lo hay
        // 3) el primero de la lista de idiomas soportados
        idiRes = idiSoportados.get(0);
        if (idiSoportados.contains(idioma)) {
            idiRes = idioma;
        } else {
            if (!StringUtils.isEmpty(idiomaDefecto)
                    && idiSoportados.contains(idiomaDefecto)) {
                idiRes = idiomaDefecto;
            }
        }

        final List<RPasoTramitacion> pasos = new ArrayList<>();
        final List<TramitePaso> ltp = restApiService
                .getTramitePasos(tv.getCodigo());
        if (ltp != null) {
            for (final TramitePaso tp : ltp) {
                final RPasoTramitacion pt = creaPaso(tp, idiRes);
                if (pt != null) {
                    pasos.add(pt);
                }
            }
        }

        final RVersionTramite rVersionTramite = new RVersionTramite();
        rVersionTramite.setTimestamp(System.currentTimeMillis() + "");
        rVersionTramite.setIdentificador(idtramite);
        rVersionTramite.setVersion(tv.getNumeroVersion());
        rVersionTramite.setRelease(tv.getRelease());

        rVersionTramite.setPasos(pasos);
        rVersionTramite
                .setDominios(generarListaDominios(tv.getListaDominios()));
        rVersionTramite.setIdioma(idiRes);
        rVersionTramite.setTipoFlujo(tv.getTipoFlujo() == null ? null
                : tv.getTipoFlujo().toString());
        rVersionTramite.setControlAcceso(generaControlAcceso(tv));
        rVersionTramite
                .setIdEntidad(generaidEntidadfromTramite(tv.getIdTramite()));
        rVersionTramite.setIdArea(generaidAreafromTramite(tv.getIdTramite()));
        rVersionTramite.setPropiedades(generaPropiedades(tv));

        return rVersionTramite;

    }

    /**
     * Genera el id de entidad para un tramite
     *
     * @param idTramite
     * @return id de entidad
     */
    private String generaidEntidadfromTramite(final Long idTramite) {
        String res = null;
        if (idTramite != null) {
            final Tramite t = restApiService.loadTramite(idTramite);
            if (t != null && t.getIdEntidad() != null) {
                final Entidad e = restApiService.loadEntidad(t.getIdEntidad());
                if (e != null) {
                    res = e.getCodigoDIR3();
                }
            }
        }
        return res;
    }

    /**
     * Genera el id de area para un tramite
     *
     * @param idTramite
     * @return id de entidad
     */
    private String generaidAreafromTramite(final Long idTramite) {
        String res = null;
        if (idTramite != null) {
            final Tramite t = restApiService.loadTramite(idTramite);
            if (t != null && t.getIdArea() != null) {
                final Area e = restApiService.loadArea(t.getIdArea());
                if (e != null) {
                    res = e.getIdentificador();
                }
            }
        }
        return res;
    }

    /**
     * Genera Propiedades de un tramite version
     *
     * @param tv
     * @return
     */
    private RVersionTramitePropiedades generaPropiedades(
            final TramiteVersion tv) {

        final RVersionTramitePropiedades res = new RVersionTramitePropiedades();
        res.setAutenticado(tv.isAutenticado());
        if (tv.getPersistenciaDias() != null) {
            res.setDiasPersistencia(tv.getPersistenciaDias());
        }

        if (tv.getIdiomasSoportados() != null) {
            res.setIdiomas(Arrays.asList(tv.getIdiomasSoportados()
                    .split(AdapterUtils.SEPARADOR_IDIOMAS)));
        }

        res.setNivelQAA(tv.getNivelQAA());
        res.setNoAutenticado(tv.isNoAutenticado());
        res.setPersistente(tv.isPersistencia());
        res.setScriptParametrosIniciales(
                AdapterUtils.generaScript(tv.getScriptInicializacionTramite()));
        res.setScriptPersonalizacion(
                AdapterUtils.generaScript(tv.getScriptPersonalizacion()));
        return res;
    }

    /**
     * Genera el control de acceso para un tramite version
     *
     * @param tv
     * @return RVersionTramiteControlAcceso
     */
    private RVersionTramiteControlAcceso generaControlAcceso(
            final TramiteVersion tv) {
        final RVersionTramiteControlAcceso res = new RVersionTramiteControlAcceso();
        res.setActivo(tv.isActiva());
        res.setDebug(tv.isDebug());
        res.setLimitarTramitacion(tv.isLimiteTramitacion());
        if (tv.getIntLimiteTramitacion() != null) {
            res.setLimiteTramitacionInicios(tv.getIntLimiteTramitacion());
        }
        if (tv.getIntLimiteTramitacion() != null) {
            res.setLimiteTramitacionIntervalo(tv.getIntLimiteTramitacion());
        }

        return res;
    }

    /**
     * Genera un paso para un idioma
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */

    private RPasoTramitacion creaPaso(final TramitePaso paso,
            final String idioma) {

        if (paso instanceof TramitePasoRellenar) {
            return crearPasoRellenar((TramitePasoRellenar) paso, idioma);
        } else if (paso instanceof TramitePasoDebeSaber) {
            return crearPasoDebeSaber((TramitePasoDebeSaber) paso, idioma);
        } else if (paso instanceof TramitePasoTasa) {
            return crearPasoTasa((TramitePasoTasa) paso, idioma);
        } else if (paso instanceof TramitePasoAnexar) {
            return crearPasoAnexar((TramitePasoAnexar) paso, idioma);
        } else if (paso instanceof TramitePasoRegistrar) {
            return crearPasoRegistrar((TramitePasoRegistrar) paso, idioma);
        }

        return null;
    }

    /**
     * Crea un paso registrar
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */
    private RPasoTramitacion crearPasoRegistrar(final TramitePasoRegistrar paso,
            final String idioma) {
        final RPasoTramitacionRegistrar resPaso = new RPasoTramitacionRegistrar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setAdmiteRepresentacion(paso.isAdmiteRepresentacion());
        resPaso.setDestino(null);
        resPaso.setInstruccionesPresentacionHtml(
                AdapterUtils.generarLiteralIdioma(
                        paso.getInstruccionesPresentacion(), idioma));
        resPaso.setInstruccionesTramitacionHtml(
                AdapterUtils.generarLiteralIdioma(
                        paso.getInstruccionesFinTramitacion(), idioma));
        resPaso.setScriptDestino(
                AdapterUtils.generaScript(paso.getScriptDestinoRegistro()));
        resPaso.setScriptPresentador(
                AdapterUtils.generaScript(paso.getScriptPresentador()));
        resPaso.setScriptRepresentante(
                AdapterUtils.generaScript(paso.getScriptRepresentante()));
        resPaso.setScriptValidar(
                AdapterUtils.generaScript(paso.getScriptValidarRegistrar()));
        resPaso.setValidaRepresentacion(paso.isValidaRepresentacion());
        return resPaso;
    }

    /**
     * Crea un paso anexar para un idioma
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */
    private RPasoTramitacion crearPasoAnexar(final TramitePasoAnexar paso,
            final String idioma) {
        final RPasoTramitacionAnexar resPaso = new RPasoTramitacionAnexar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setAnexos(generaAnexos(paso.getDocumentos(), idioma));
        resPaso.setScriptAnexosDinamicos(
                AdapterUtils.generaScript(paso.getScriptAnexosDinamicos()));
        return resPaso;
    }

    /**
     * Crear Paso debe saber para un idioma
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */
    private RPasoTramitacion crearPasoDebeSaber(final TramitePasoDebeSaber paso,
            final String idioma) {
        final RPasoTramitacionDebeSaber resPaso = new RPasoTramitacionDebeSaber();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setInstruccionesInicio(AdapterUtils.generarLiteralIdioma(
                paso.getInstruccionesIniciales(), idioma));
        return resPaso;
    }

    /**
     * Crea un paso tasa para un idioma
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */
    private RPasoTramitacion crearPasoTasa(final TramitePasoTasa paso,
            final String idioma) {
        final RPasoTramitacionPagar resPaso = new RPasoTramitacionPagar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setPagos(generarPagos(paso.getTasas(), idioma));
        return resPaso;
    }

    /**
     * crea un paso rellenar para un idioma
     *
     * @param paso
     * @param idioma
     * @return RPasoTramitacion
     */
    private RPasoTramitacion crearPasoRellenar(final TramitePasoRellenar paso,
            final String idioma) {
        final RPasoTramitacionRellenar resPaso = new RPasoTramitacionRellenar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setFormularios(
                generaFormularios(paso.getFormulariosTramite(), idioma));
        return resPaso;

    }

    /**
     * genera una lista de dominios
     *
     * @param listaDominios
     * @return List<RDominio>
     */
    private List<RDominio> generarListaDominios(
            final List<Long> listaDominios) {
        List<RDominio> res = null;
        if (listaDominios != null) {
            res = new ArrayList<RDominio>();
            for (final Long idDom : listaDominios) {
                final Dominio dom = restApiService.loadDominio(idDom);
                if (dom != null) {
                    final RDominio rdom = new RDominio();
                    rdom.setCachear(dom.isCacheable());
                    rdom.setIdentificador(dom.getIdentificador());
                    rdom.setSql(dom.getSql());
                    rdom.setTipo(generaTipo(dom.getTipo()));
                    rdom.setUri(dom.getUrl());
                    res.add(rdom);
                }
            }
        }
        return res;
    }

    /**
     * Genera un tipo
     *
     * @param tipo
     * @return
     */
    private String generaTipo(final TypeDominio tipo) {
        return tipo.toString();
    }

    /**
     * Genera una lista de formularios para un idioma
     *
     * @param formulariosTramite
     * @param idioma
     * @return List<RFormularioTramite>
     */
    private List<RFormularioTramite> generaFormularios(
            final List<FormularioTramite> formulariosTramite,
            final String idioma) {
        List<RFormularioTramite> res = null;

        if (formulariosTramite != null) {
            res = new ArrayList<RFormularioTramite>();
            for (final FormularioTramite f : formulariosTramite) {
                final RFormularioTramite formularioTramite = new RFormularioTramite();
                formularioTramite.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(f.getDescripcion(), idioma));
                formularioTramite.setFirmar(f.isDebeFirmarse());
                formularioTramite
                        .setInterno(f.getIdFormularioInterno() != null);
                formularioTramite.setFormularioExterno(generaFormularioExterno(
                        f.getFormularioGestorExterno()));
                formularioTramite.setFormularioInterno(generaFormularioInterno(
                        f.getIdFormularioInterno(), idioma));
                formularioTramite.setIdentificador(f.getIdentificador());
                formularioTramite.setObligatoriedad(
                        generaObligatoriedad(f.getObligatoriedad()));
                formularioTramite
                        .setPresentarPreregistro(f.isDebePrerregistrarse());
                formularioTramite.setScriptDatosIniciales(
                        AdapterUtils.generaScript(f.getScriptDatosIniciales()));
                formularioTramite.setScriptObligatoriedad(
                        AdapterUtils.generaScript(f.getScriptObligatoriedad()));
                formularioTramite.setScriptFirmantes(
                        AdapterUtils.generaScript(f.getScriptFirma()));
                formularioTramite.setScriptParametrosApertura(
                        AdapterUtils.generaScript(f.getScriptParametros()));
                formularioTramite.setScriptPostguardar(
                        AdapterUtils.generaScript(f.getScriptRetorno()));
                res.add(formularioTramite);
            }
        }
        return res;
    }

    /**
     * Genera el formulario interno
     *
     * @param idFormularioInterno
     * @param idioma
     * @return RFormularioInterno
     */
    private RFormularioInterno generaFormularioInterno(
            final Long idFormularioInterno, final String idioma) {
        RFormularioInterno formInterno = null;
        if (idFormularioInterno != null) {
            final DisenyoFormulario d = restApiService
                    .getDisenyoFormularioById(idFormularioInterno);
            if (d != null) {
                formInterno = new RFormularioInterno();
                formInterno.setMostrarTitulo(d.isMostrarCabecera());
                // TODO: verificar que se obtienen las plantillas y páginas,
                // parece que no llegan en el objeto
                formInterno.setPaginas(
                        generarPaginas(d.getPaginas(), d.getCodigo(), idioma));
                formInterno.setPlantillas(
                        generarPlantillas(d.getPlantillas(), idioma));

                formInterno.setScriptPlantillas(
                        AdapterUtils.generaScript(d.getScriptPlantilla()));
                formInterno.setTitulo(AdapterUtils
                        .generarLiteralIdioma(d.getTextoCabecera(), idioma));

            }
        }
        return formInterno;
    }

    /**
     * Genera la lista de plantillas
     *
     * @param plantillas
     * @param idioma
     * @return Lista de RPlantillaFormulario
     */
    private List<RPlantillaFormulario> generarPlantillas(
            final List<PlantillaFormulario> plantillas, final String idioma) {
        List<RPlantillaFormulario> lpf = null;
        if (plantillas != null) {
            lpf = new ArrayList<RPlantillaFormulario>();
            for (final PlantillaFormulario p : plantillas) {
                final RPlantillaFormulario plantillaFormulario = new RPlantillaFormulario();
                plantillaFormulario.setClaseFormateador(
                        (getStringFormateador(p.getIdFormateadorFormulario())));
                plantillaFormulario.setDefecto(p.isPorDefecto());
                plantillaFormulario.setFicheroPlantilla(
                        generaFicheroPlantilla(p.getCodigo(), idioma));
                plantillaFormulario.setIdentificador(p.getIdentificador());
                lpf.add(plantillaFormulario);
            }
        }
        return lpf;
    }

    /**
     * genera fichero plantilla
     *
     * @param idPlantillaFormulario
     * @param idioma
     * @return String
     */
    private String generaFicheroPlantilla(final Long idPlantillaFormulario,
            final String idioma) {
        final List<PlantillaIdiomaFormulario> lp = restApiService
                .getListaPlantillaIdiomaFormularioById(idPlantillaFormulario);
        if (lp != null) {
            for (final PlantillaIdiomaFormulario p : lp) {
                if (idioma.equals(p.getIdioma()) && p.getFichero() != null) {
                    return restApiService
                            .getReferenciaFichero(p.getFichero().getCodigo());
                }
            }
        }
        return null;
    }

    /**
     * Recupera el string del formateador del formulario
     *
     * @param idFormateadorFormulario
     * @return
     */
    private String getStringFormateador(final Long idFormateadorFormulario) {
        final FormateadorFormulario f = restApiService
                .getFormateadorFormulario(idFormateadorFormulario);
        if (f != null) {
            return f.getClassname();
        }
        return null;
    }

    /**
     * Genera las páginas
     *
     * @param paginas
     * @param idForm
     * @param idioma
     * @return lista de RPaginaFormulario
     */
    private List<RPaginaFormulario> generarPaginas(
            final List<PaginaFormulario> paginas, final Long idForm,
            final String idioma) {
        List<RPaginaFormulario> lpf = null;

        if (paginas != null) {
            lpf = new ArrayList<RPaginaFormulario>();
            for (final PaginaFormulario p : paginas) {
                final RPaginaFormulario res = new RPaginaFormulario();
                res.setHtmlB64(restApiService.getPaginaFormularioHTMLAsistente(
                        p.getCodigo(), idioma));
                res.setLineas(generarLineas(p.getLineas(), idioma));
                res.setPaginaFinal(p.isPaginaFinal());
                res.setScriptValidacion(
                        AdapterUtils.generaScript(p.getScriptValidacion()));
                lpf.add(res);
            }
        }
        return lpf;
    }

    /**
     * Genera las lineas
     *
     * @param lineas
     * @param idioma
     * @return lista de RLineaComponentes
     */
    private List<RLineaComponentes> generarLineas(
            final List<LineaComponentesFormulario> lineas,
            final String idioma) {
        List<RLineaComponentes> llc = null;
        if (lineas != null) {
            llc = new ArrayList<RLineaComponentes>();
            for (final LineaComponentesFormulario l : lineas) {
                final RLineaComponentes res = new RLineaComponentes();
                res.setComponentes(
                        generaComponentes(l.getComponentes(), idioma));
                llc.add(res);
            }
        }
        return llc;
    }

    /**
     * genera la lista de componentes
     *
     * @param componentes
     * @param idioma
     * @return lista de Rcomponente
     */
    private List<RComponente> generaComponentes(
            final List<ComponenteFormulario> componentes, final String idioma) {

        if (componentes != null) {
            final List<RComponente> lc = new ArrayList<RComponente>();
            for (final ComponenteFormulario c : componentes) {
                switch (c.getTipo()) {
                case CAMPO_TEXTO:
                    final ComponenteFormularioCampoTexto ct = (ComponenteFormularioCampoTexto) c;
                    final RComponenteTextbox resTB = generaComponenteTextbox(ct,
                            idioma);
                    lc.add(resTB);
                    break;
                case CHECKBOX:
                    final ComponenteFormularioCampoCheckbox cch = (ComponenteFormularioCampoCheckbox) c;
                    final RComponenteCheckbox resCH = generaComponenteCheckBox(
                            cch, idioma);
                    lc.add(resCH);
                    break;
                case SELECTOR:
                    final ComponenteFormularioCampoSelector cs = (ComponenteFormularioCampoSelector) c;
                    final RComponenteSelector resSL = generaComponenteSelector(
                            cs, idioma);
                    lc.add(resSL);
                    break;
                case SECCION:
                    final ComponenteFormularioSeccion csc = (ComponenteFormularioSeccion) c;
                    final RComponenteSeccion resSC = generaComponenteSeccion(
                            csc, idioma);
                    lc.add(resSC);
                    break;
                case ETIQUETA:
                    final ComponenteFormularioEtiqueta ca = (ComponenteFormularioEtiqueta) c;
                    final RComponenteAviso resET = generaComponenteAviso(ca,
                            idioma);
                    lc.add(resET);
                    break;
                default:
                    return null;
                }
            }
            return lc;
        } else {
            return null;
        }
    }

    /**
     * Genera componentes aviso
     *
     * @param ca
     * @param idioma
     * @return RComponenteAviso
     */
    private RComponenteAviso generaComponenteAviso(
            final ComponenteFormularioEtiqueta ca, final String idioma) {
        final RComponenteAviso resET = new RComponenteAviso();
        resET.setAlineacion(ca.getAlineacionTexto() == null ? null
                : ca.getAlineacionTexto().toString());
        resET.setAyuda(
                AdapterUtils.generarLiteralIdioma(ca.getAyuda(), idioma));
        resET.setColumnas(ca.getNumColumnas());
        resET.setEtiqueta(
                AdapterUtils.generarLiteralIdioma(ca.getTexto(), idioma));
        resET.setIdentificador(ca.getIdComponente());
        resET.setMostrarEtiqueta(!ca.isNoMostrarTexto());
        resET.setTipo(ca.getTipo() == null ? null : ca.getTipo().toString());
        resET.setTipoAviso(ca.getTipoEtiqueta() == null ? null
                : ca.getTipoEtiqueta().toString());
        return resET;
    }

    /**
     * Genera Componente Seccion
     *
     * @param csc
     * @param idioma
     * @return RComponenteSeccion
     */
    private RComponenteSeccion generaComponenteSeccion(
            final ComponenteFormularioSeccion csc, final String idioma) {
        final RComponenteSeccion resSC = new RComponenteSeccion();
        resSC.setAlineacion(csc.getAlineacionTexto() == null ? null
                : csc.getAlineacionTexto().toString());
        resSC.setAyuda(
                AdapterUtils.generarLiteralIdioma(csc.getAyuda(), idioma));
        resSC.setColumnas(csc.getNumColumnas());
        resSC.setEtiqueta(
                AdapterUtils.generarLiteralIdioma(csc.getTexto(), idioma));
        resSC.setIdentificador(csc.getIdComponente());
        resSC.setLetra(csc.getLetra());
        resSC.setMostrarEtiqueta(!csc.isNoMostrarTexto());
        resSC.setTipo(csc.getTipo() == null ? null : csc.getTipo().toString());
        return resSC;
    }

    /**
     * Genera Componente Selector
     *
     * @param cs
     * @param idioma
     * @return RComponenteSelector
     */
    private RComponenteSelector generaComponenteSelector(
            final ComponenteFormularioCampoSelector cs, final String idioma) {
        final RComponenteSelector resSL = new RComponenteSelector();
        resSL.setAlineacion(cs.getAlineacionTexto() == null ? null
                : cs.getAlineacionTexto().toString());
        resSL.setAyuda(
                AdapterUtils.generarLiteralIdioma(cs.getAyuda(), idioma));
        resSL.setColumnas(cs.getNumColumnas());
        resSL.setEtiqueta(
                AdapterUtils.generarLiteralIdioma(cs.getTexto(), idioma));
        resSL.setIdentificador(cs.getIdComponente());
        resSL.setListaDominio(generaListaDominio(cs));
        resSL.setListaFija(
                generaListaFija(cs.getListaValorListaFija(), idioma));
        resSL.setMostrarEtiqueta(!cs.isNoMostrarTexto());
        resSL.setPropiedadesCampo(generarPropiedadesCampo(cs));
        resSL.setScriptListaValores(
                AdapterUtils.generaScript(cs.getScriptValoresPosibles()));
        resSL.setTipo(cs.getTipo() == null ? null : cs.getTipo().toString());
        resSL.setTipoListaValores(cs.getTipoListaValores() == null ? null
                : cs.getTipoListaValores().toString());
        return resSL;
    }

    /**
     * genera Componente CheckBox
     *
     * @param cch
     * @param idioma
     * @return RComponenteCheckbox
     */
    private RComponenteCheckbox generaComponenteCheckBox(
            final ComponenteFormularioCampoCheckbox cch, final String idioma) {
        final RComponenteCheckbox resCH = new RComponenteCheckbox();
        resCH.setAlineacion(cch.getAlineacionTexto() == null ? null
                : cch.getAlineacionTexto().toString());
        resCH.setAyuda(
                AdapterUtils.generarLiteralIdioma(cch.getAyuda(), idioma));
        resCH.setColumnas(cch.getNumColumnas());
        resCH.setEtiqueta(
                AdapterUtils.generarLiteralIdioma(cch.getTexto(), idioma));
        resCH.setIdentificador(cch.getIdComponente());
        resCH.setMostrarEtiqueta(!cch.isNoMostrarTexto());
        resCH.setPropiedadesCampo(generarPropiedadesCampo(cch));
        resCH.setTipo(cch.getTipo() == null ? null : cch.getTipo().toString());
        resCH.setValorChecked(cch.getValorChecked());
        resCH.setValorNoChecked(cch.getValorNoChecked());
        return resCH;
    }

    /**
     * Genera Componente Textbox
     *
     * @param ct
     * @param idioma
     * @return RComponenteTextbox
     */
    private RComponenteTextbox generaComponenteTextbox(
            final ComponenteFormularioCampoTexto ct, final String idioma) {
        final RComponenteTextbox resTB = new RComponenteTextbox();
        resTB.setAlineacion(ct.getAlineacionTexto() == null ? null
                : ct.getAlineacionTexto().toString());
        resTB.setAyuda(
                AdapterUtils.generarLiteralIdioma(ct.getAyuda(), idioma));
        resTB.setColumnas(ct.getNumColumnas());
        resTB.setEtiqueta(
                AdapterUtils.generarLiteralIdioma(ct.getTexto(), idioma));
        resTB.setIdentificador(ct.getIdComponente());
        resTB.setMostrarEtiqueta(!ct.isNoMostrarTexto());
        resTB.setPropiedadesCampo(generaPropiedadesCampo(ct));
        resTB.setTextoExpRegular(
                generaExpresionRegular(ct.getExpresionRegular()));
        resTB.setTextoIdentificacion(generaTextoIdentificacion(ct));
        resTB.setTextoNormal(generaTextoNormal(ct));
        resTB.setTextoNumero(generaTextoNumero(ct));
        resTB.setTipo(ct.getTipo() == null ? null : ct.getTipo().toString());
        resTB.setTipoTexto(ct.getTipoCampoTexto() == null ? null
                : ct.getTipoCampoTexto().toString());
        return resTB;
    }

    /**
     * Genera Lista Fija
     *
     * @param ori
     * @param idioma
     * @return lista de RValorListaFija
     */
    private List<RValorListaFija> generaListaFija(
            final List<ValorListaFija> ori, final String idioma) {
        List<RValorListaFija> rlv = new ArrayList<RValorListaFija>();
        if (ori != null) {
            rlv = new ArrayList<RValorListaFija>();
            for (final ValorListaFija v : ori) {
                final RValorListaFija res = new RValorListaFija();
                res.setCodigo(v.getValor());
                res.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(v.getDescripcion(), idioma));
                res.setPorDefecto(v.isPorDefecto());
                rlv.add(res);
            }
        }
        return rlv;
    }

    /**
     * Genera la lista de dominos
     *
     * @param cs
     * @return RListaDominio
     */
    private RListaDominio generaListaDominio(
            final ComponenteFormularioCampoSelector cs) {
        final RListaDominio l = new RListaDominio();
        l.setCampoCodigo(cs.getCampoDominioCodigo());
        l.setCampoDescripción(cs.getCampoDominioDescripcion());
        l.setDominio(generaDominio(cs.getCodDominio()));
        l.setParametros(
                generaParametrosDominio(cs.getListaParametrosDominio()));
        return l;
    }

    /**
     * generaParametrosDominio
     *
     * @param ori
     * @return lista RParametroDominio
     */
    private List<RParametroDominio> generaParametrosDominio(
            final List<ParametroDominio> ori) {
        List<RParametroDominio> lpd = null;
        if (ori != null) {
            lpd = new ArrayList<RParametroDominio>();
            for (final ParametroDominio p : ori) {
                final RParametroDominio res = new RParametroDominio();
                res.setIdentificador(p.getParametro());
                res.setTipo(
                        p.getTipo() == null ? null : p.getTipo().toString());
                res.setValor(p.getValor());
                lpd.add(res);
            }
        }
        return lpd;
    }

    /**
     * Genera id Dominio
     *
     * @param codDominio
     * @return id del dominio
     */
    private String generaDominio(final Long codDominio) {
        if (codDominio != null) {
            final Dominio d = restApiService.loadDominio(codDominio);
            if (d != null) {
                return d.getIdString();
            }
        }
        return null;
    }

    /**
     * Genera Propiedades del Campo checkbox
     *
     * @param ori
     * @return RPropiedadesCampo
     */
    private RPropiedadesCampo generarPropiedadesCampo(
            final ComponenteFormularioCampoCheckbox ori) {
        final RPropiedadesCampo res = new RPropiedadesCampo();
        res.setNoModificable(ori.isNoModificable());
        res.setObligatorio(ori.isObligatorio());
        res.setScriptAutorrellenable(
                AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
        res.setScriptEstado(
                (AdapterUtils.generaScript(ori.getScriptSoloLectura())));
        res.setScriptValidacion(
                (AdapterUtils.generaScript(ori.getScriptValidacion())));
        res.setSoloLectura(ori.isSoloLectura());
        return res;
    }

    /**
     * genera Propiedades Campo texto
     *
     * @param ori
     * @return RPropiedadesCampo
     */
    private RPropiedadesCampo generaPropiedadesCampo(
            final ComponenteFormularioCampoTexto ori) {
        final RPropiedadesCampo res = new RPropiedadesCampo();
        res.setNoModificable(ori.isNoModificable());
        res.setObligatorio(ori.isObligatorio());
        res.setScriptAutorrellenable(
                AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
        res.setScriptEstado(
                (AdapterUtils.generaScript(ori.getScriptSoloLectura())));
        res.setScriptValidacion(
                (AdapterUtils.generaScript(ori.getScriptValidacion())));
        res.setSoloLectura(ori.isSoloLectura());
        return res;
    }

    /**
     * genera Propiedades Campo selector
     *
     * @param ori
     * @return RPropiedadesCampo
     */
    private RPropiedadesCampo generarPropiedadesCampo(
            final ComponenteFormularioCampoSelector ori) {
        final RPropiedadesCampo res = new RPropiedadesCampo();
        res.setNoModificable(ori.isNoModificable());
        res.setObligatorio(ori.isObligatorio());
        res.setScriptAutorrellenable(
                AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
        res.setScriptEstado(
                (AdapterUtils.generaScript(ori.getScriptSoloLectura())));
        res.setScriptValidacion(
                (AdapterUtils.generaScript(ori.getScriptValidacion())));
        res.setSoloLectura(ori.isSoloLectura());
        return res;
    }

    /**
     * Genera propiedades Texto Numero
     *
     * @param ori
     * @return RPropiedadesTextoNumero
     */
    private RPropiedadesTextoNumero generaTextoNumero(
            final ComponenteFormularioCampoTexto ct) {
        final RPropiedadesTextoNumero res = new RPropiedadesTextoNumero();
        res.setFormatoNumero(ct.getNumeroSeparador() == null ? null
                : ct.getNumeroSeparador().toString());
        res.setNegativos(ct.isNumeroConSigno());
        res.setPrecisionDecimal(ct.getNumeroDigitosDecimales() == null ? 0
                : ct.getNumeroDigitosDecimales());
        res.setPrecisionEntera(ct.getNumeroDigitosEnteros() == null ? 0
                : ct.getNumeroDigitosEnteros());
        res.setRango(ct.isPermiteRango());
        res.setRangoDesde(ct.getNumeroRangoMinimo() == null ? 0
                : ct.getNumeroRangoMinimo());
        res.setRangoHasta(ct.getNumeroRangoMaximo() == null ? 0
                : ct.getNumeroRangoMaximo());
        return res;
    }

    /**
     * Genera propiedades Texto Normal
     *
     * @param ct
     * @return RPropiedadesTextoNormal
     */
    private RPropiedadesTextoNormal generaTextoNormal(
            final ComponenteFormularioCampoTexto ct) {
        if (ct != null) {
            final RPropiedadesTextoNormal res = new RPropiedadesTextoNormal();
            res.setMultilinea(ct.isNormalMultilinea());
            res.setTamanyoMax(
                    ct.getNormalTamanyo() == null ? 0 : ct.getNormalTamanyo());
            return res;
        }
        return null;

    }

    /**
     * Genera Texto Identificacion
     *
     * @param componenteOrigen
     * @return RPropiedadesTextoIdentificacion
     */
    private RPropiedadesTextoIdentificacion generaTextoIdentificacion(
            final ComponenteFormularioCampoTexto componenteOrigen) {
        RPropiedadesTextoIdentificacion res = null;
        if (componenteOrigen != null) {
            res = new RPropiedadesTextoIdentificacion();
            res.setCif(componenteOrigen.isIdentCif());
            res.setNie(componenteOrigen.isIdentNie());
            res.setNif(componenteOrigen.isIdentNif());
            res.setNss(componenteOrigen.isIdentNss());
        }
        return res;

    }

    /**
     * Genera propiedades Expresión Regular
     *
     * @param exp
     * @return RPropiedadesTextoExpRegular
     */
    private RPropiedadesTextoExpRegular generaExpresionRegular(
            final String exp) {
        if (StringUtils.isEmpty(exp)) {
            return null;
        }
        final RPropiedadesTextoExpRegular res = new RPropiedadesTextoExpRegular();
        res.setExpresionRegular(exp);
        return res;
    }

    /**
     * Genera Formulario Externo
     *
     * @param ori
     * @return RFormularioExterno
     */
    private RFormularioExterno generaFormularioExterno(
            final GestorExternoFormularios ori) {
        RFormularioExterno res = null;
        if (ori != null) {
            res = new RFormularioExterno();
            res.setIdentificadorFormulario(ori.getIdentificador());
            res.setIdentificadorGestorFormularios(ori.getIdentificador());// TODO:
                                                                          // revisar
                                                                          // esta
                                                                          // setIdentificadorGestorFormularios,
                                                                          // pendiente
                                                                          // validar
        }
        return res;
    }

    /**
     * Genera Obligatoriedad
     *
     * @param obligatoriedad
     * @return obligatoriedad
     */
    private String generaObligatoriedad(
            final TypeFormularioObligatoriedad obligatoriedad) {
        return obligatoriedad == null ? null : obligatoriedad.toString();
    }

    /**
     * Generar lista Pagos
     *
     * @param tasas
     * @param idioma
     * @return lista RPagoTramite
     */
    private List<RPagoTramite> generarPagos(final List<Tasa> tasas,
            final String idioma) {
        List<RPagoTramite> lres = null;
        if (tasas != null) {
            lres = new ArrayList<RPagoTramite>();
            for (final Tasa t : tasas) {
                final RPagoTramite res = new RPagoTramite();
                res.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(t.getDescripcion(), idioma));
                res.setIdentificador(t.getIdentificador());
                res.setObligatoriedad(t.getObligatoriedad() == null ? null
                        : t.getObligatoriedad().toString());
                res.setScriptDependencia(
                        AdapterUtils.generaScript(t.getScriptObligatoriedad()));
                res.setScriptPago(AdapterUtils.generaScript(t.getScriptPago()));
                res.setTipo(t.getTipo().toString());
                res.setSimularPago(t.isSimulado());
                lres.add(res);
            }
        }
        return lres;
    }

    /**
     * Generar lista anexos
     *
     * @param documentos
     * @param idioma
     * @return lista RAnexoTramite
     */
    private List<RAnexoTramite> generaAnexos(final List<Documento> documentos,
            final String idioma) {
        List<RAnexoTramite> lres = null;
        if (documentos != null) {
            lres = new ArrayList<RAnexoTramite>();
            for (final Documento d : documentos) {
                final RAnexoTramite res = new RAnexoTramite();
                res.setAyuda(generaAyudaFichero(d.getAyudaTexto(),
                        d.getAyudaFichero(), d.getAyudaURL(), idioma));
                res.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(d.getDescripcion(), idioma));
                res.setIdentificador(d.getIdentificador());
                res.setObligatoriedad(d.getObligatoriedad() == null ? null
                        : d.getObligatoriedad().toString());
                res.setPresentacion(d.getTipoPresentacion() == null ? null
                        : d.getTipoPresentacion().toString());
                res.setScriptDependencia(
                        AdapterUtils.generaScript(d.getScriptObligatoriedad()));

                final RAnexoTramitePresentacionElectronica resPE = new RAnexoTramitePresentacionElectronica();
                resPE.setAnexarFirmado(d.isDebeAnexarFirmado());
                resPE.setConvertirPDF(d.isDebeConvertirPDF());
                if (StringUtils.isNotBlank(d.getExtensiones())
                        && !"*".equals(d.getExtensiones())) {
                    resPE.setExtensiones(Arrays.asList(d.getExtensiones()
                            .split(AdapterUtils.SEPARADOR_EXTENSIONES)));
                }
                resPE.setFirmar(d.isDebeFirmarDigitalmente());
                resPE.setInstancias(d.getNumeroInstancia());
                resPE.setScriptFirmantes(AdapterUtils
                        .generaScript(d.getScriptFirmarDigitalmente()));
                resPE.setScriptValidacion(
                        AdapterUtils.generaScript(d.getScriptValidacion()));
                resPE.setTamanyoMax(d.getTamanyoMaximo());
                resPE.setTamanyoUnidad(d.getTipoTamanyo() == null ? null
                        : d.getTipoTamanyo().toString());

                res.setPresentacionElectronica(resPE);
                lres.add(res);
            }
        }
        return lres;
    }

    /**
     * Genera Ayuda Fichero
     *
     * @param texto
     * @param fichero
     * @param url
     * @param idioma
     * @return RAnexoTramiteAyuda
     */
    private RAnexoTramiteAyuda generaAyudaFichero(final Literal texto,
            final Fichero fichero, final String url, final String idioma) {
        final RAnexoTramiteAyuda res = new RAnexoTramiteAyuda();
        res.setFichero(restApiService.getReferenciaFichero(
                fichero == null ? null : fichero.getCodigo()));
        res.setMensajeHtml(AdapterUtils.generarLiteralIdioma(texto, idioma));
        res.setUrl(url);
        return res;
    }

}

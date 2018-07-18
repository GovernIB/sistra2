package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
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

public class RVersionTramiteAdapter {
    private RVersionTramite rVersionTramite;
    private final RestApiInternaService restApiService;

    public RVersionTramiteAdapter(TramiteVersion tv, String idioma,
            String idiomaDefecto, RestApiInternaService restApiService) {
        this.restApiService = restApiService;
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

        rVersionTramite = new RVersionTramite();
        rVersionTramite.setTimestamp(System.currentTimeMillis() + "");
        rVersionTramite.setIdentificador(tv.getIdTramite() + "");
        rVersionTramite.setVersion(tv.getNumeroVersion());
        rVersionTramite.setPasos(pasos);
        rVersionTramite
                .setDominios(generarListaDominios(tv.getListaDominios()));
        rVersionTramite.setIdioma(idiRes);
        rVersionTramite.setTipoFlujo(tv.getTipoFlujo() == null ? null
                : tv.getTipoFlujo().toString());
        rVersionTramite.setControlAcceso(generaControlAcceso(tv)); // ver
                                                                   // pantalla
        rVersionTramite
                .setIdEntidad(generaidEntidadfromTramite(tv.getIdTramite())); // buscar
                                                                              // hacia
                                                                              // arriba
        rVersionTramite.setPropiedades(generaPropiedades(tv)); // ver pantalla

    }

    private String generaidEntidadfromTramite(Long idTramite) {
        if (idTramite != null) {
            final Tramite t = restApiService.loadTramite(idTramite);
            if (t != null && t.getIdEntidad() != null) {
                return t.getIdEntidad() + "";
            }
        }

        return null;
    }

    private RVersionTramitePropiedades generaPropiedades(TramiteVersion tv) {
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

    private RVersionTramiteControlAcceso generaControlAcceso(
            TramiteVersion tv) {
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

    private RPasoTramitacion creaPaso(TramitePaso paso, String idioma) {

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

    private RPasoTramitacion crearPasoRegistrar(TramitePasoRegistrar paso,
            String idioma) {
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

    private RPasoTramitacion crearPasoAnexar(TramitePasoAnexar paso,
            String idioma) {
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

    private RPasoTramitacion crearPasoDebeSaber(TramitePasoDebeSaber paso,
            String idioma) {
        final RPasoTramitacionDebeSaber resPaso = new RPasoTramitacionDebeSaber();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setInstruccionesInicio(AdapterUtils.generarLiteralIdioma(
                paso.getInstruccionesIniciales(), idioma));
        return resPaso;
    }

    private RPasoTramitacion crearPasoTasa(TramitePasoTasa paso,
            String idioma) {
        final RPasoTramitacionPagar resPaso = new RPasoTramitacionPagar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setPagos(generarPagos(paso.getTasas(), idioma));
        return resPaso;
    }

    private RPasoTramitacion crearPasoRellenar(TramitePasoRellenar paso,
            String idioma) {
        final RPasoTramitacionRellenar resPaso = new RPasoTramitacionRellenar();
        resPaso.setIdentificador(paso.getIdPasoTramitacion());
        resPaso.setTipo(
                paso.getTipo() != null ? paso.getTipo().toString() : null);
        resPaso.setPasoFinal(paso.isPasoFinal());
        resPaso.setFormularios(
                generaFormularios(paso.getFormulariosTramite(), idioma));
        return resPaso;

    }

    private List<RDominio> generarListaDominios(List<Long> listaDominios) {
        final List<RDominio> res = new ArrayList<RDominio>();
        if (listaDominios != null) {
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

    private String generaTipo(TypeDominio tipo) {
        return tipo.toString();
    }

    private List<RFormularioTramite> generaFormularios(
            List<FormularioTramite> formulariosTramite, String idioma) {
        final List<RFormularioTramite> res = new ArrayList<RFormularioTramite>();

        if (formulariosTramite != null) {
            for (final FormularioTramite f : formulariosTramite) {
                final RFormularioTramite formularioTramite = new RFormularioTramite();
                formularioTramite.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(f.getDescripcion(), idioma));
                formularioTramite.setFirmar(f.isDebeFirmarse());
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

    private RFormularioInterno generaFormularioInterno(Long idFormularioInterno,
            String idioma) {
        if (idFormularioInterno != null) {
            // DisenyoFormulario d =
            // restApiService.getFormularioInterno(idFormularioInterno);
            final DisenyoFormulario d = restApiService
                    .getDisenyoFormularioById(idFormularioInterno);
            if (d != null) {
                final RFormularioInterno formInterno = new RFormularioInterno();
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
                return formInterno;
            }
        }
        return null;
    }

    private List<RPlantillaFormulario> generarPlantillas(
            List<PlantillaFormulario> plantillas, String idioma) {
        final List<RPlantillaFormulario> lpf = new ArrayList<RPlantillaFormulario>();
        if (plantillas != null) {
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

    private String generaFicheroPlantilla(Long idPlantillaFormulario,
            String idioma) {
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

    private String getStringFormateador(Long idFormateadorFormulario) {
        final FormateadorFormulario f = restApiService
                .getFormateadorFormulario(idFormateadorFormulario);
        if (f != null) {
            return f.getClassname();
        }
        return null;
    }

    private List<RPaginaFormulario> generarPaginas(
            List<PaginaFormulario> paginas, Long idForm, String idioma) {
        final List<RPaginaFormulario> lpf = new ArrayList<RPaginaFormulario>();
        if (paginas != null) {
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

    private List<RLineaComponentes> generarLineas(
            List<LineaComponentesFormulario> lineas, String idioma) {
        final List<RLineaComponentes> llc = new ArrayList<RLineaComponentes>();
        if (lineas != null) {
            for (final LineaComponentesFormulario l : lineas) {
                final RLineaComponentes res = new RLineaComponentes();
                res.setComponentes(
                        generaComponentes(l.getComponentes(), idioma));
                llc.add(res);
            }
        }
        return llc;
    }

    private List<RComponente> generaComponentes(
            List<ComponenteFormulario> componentes, String idioma) {
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

    private RComponenteAviso generaComponenteAviso(
            ComponenteFormularioEtiqueta ca, String idioma) {
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

    private RComponenteSeccion generaComponenteSeccion(
            ComponenteFormularioSeccion csc, String idioma) {
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

    private RComponenteSelector generaComponenteSelector(
            ComponenteFormularioCampoSelector cs, String idioma) {
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

    private RComponenteCheckbox generaComponenteCheckBox(
            ComponenteFormularioCampoCheckbox cch, String idioma) {
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

    private RComponenteTextbox generaComponenteTextbox(
            ComponenteFormularioCampoTexto ct, String idioma) {
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

    private List<RValorListaFija> generaListaFija(List<ValorListaFija> ori,
            String idioma) {
        if (ori != null) {
            final List<RValorListaFija> rlv = new ArrayList<RValorListaFija>();
            for (final ValorListaFija v : ori) {
                final RValorListaFija res = new RValorListaFija();
                res.setCodigo(v.getValor());
                res.setDescripcion(AdapterUtils
                        .generarLiteralIdioma(v.getDescripcion(), idioma));
                res.setPorDefecto(v.isPorDefecto());
                rlv.add(res);
            }
            return rlv;
        }
        return null;
    }

    private RListaDominio generaListaDominio(
            ComponenteFormularioCampoSelector cs) {
        final RListaDominio l = new RListaDominio();
        l.setCampoCodigo(cs.getCampoDominioCodigo());
        l.setCampoDescripción(cs.getCampoDominioDescripcion());
        l.setDominio(generaDominio(cs.getCodDominio()));
        l.setParametros(
                generaParametrosDominio(cs.getListaParametrosDominio()));
        return l;
    }

    private List<RParametroDominio> generaParametrosDominio(
            List<ParametroDominio> ori) {
        if (ori != null) {
            final List<RParametroDominio> lpd = new ArrayList<RParametroDominio>();
            for (final ParametroDominio p : ori) {
                final RParametroDominio res = new RParametroDominio();
                res.setIdentificador(p.getParametro());
                res.setTipo(
                        p.getTipo() == null ? null : p.getTipo().toString());
                res.setValor(p.getValor());
                lpd.add(res);
            }
            return lpd;
        }
        return null;
    }

    private String generaDominio(Long codDominio) {
        if (codDominio != null) {
            final Dominio d = restApiService.loadDominio(codDominio);
            if (d != null) {
                return d.getIdString();
            }
        }
        return null;
    }

    private RPropiedadesCampo generarPropiedadesCampo(
            ComponenteFormularioCampoCheckbox ori) {
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

    private RPropiedadesCampo generaPropiedadesCampo(
            ComponenteFormularioCampoTexto ori) {
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

    private RPropiedadesCampo generarPropiedadesCampo(
            ComponenteFormularioCampoSelector ori) {
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

    private RPropiedadesTextoNumero generaTextoNumero(
            ComponenteFormularioCampoTexto ct) {
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

    private RPropiedadesTextoNormal generaTextoNormal(
            ComponenteFormularioCampoTexto ct) {
        if (ct != null) {
            final RPropiedadesTextoNormal res = new RPropiedadesTextoNormal();
            res.setMultilinea(ct.isNormalMultilinea());
            res.setTamanyoMax(
                    ct.getNormalTamanyo() == null ? 0 : ct.getNormalTamanyo());
            return res;
        }
        return null;

    }

    private RPropiedadesTextoIdentificacion generaTextoIdentificacion(
            ComponenteFormularioCampoTexto componenteOrigen) {
        if (componenteOrigen != null) {
            final RPropiedadesTextoIdentificacion res = new RPropiedadesTextoIdentificacion();
            res.setCif(componenteOrigen.isIdentCif());
            res.setNie(componenteOrigen.isIdentNie());
            res.setNif(componenteOrigen.isIdentNif());
            res.setNss(componenteOrigen.isIdentNss());
            return res;
        }
        return null;

    }

    private RPropiedadesTextoExpRegular generaExpresionRegular(String exp) {
        if (StringUtils.isEmpty(exp)) {
            return null;
        }
        final RPropiedadesTextoExpRegular res = new RPropiedadesTextoExpRegular();
        res.setExpresionRegular(exp);
        return res;
    }

    private RFormularioExterno generaFormularioExterno(
            GestorExternoFormularios ori) {
        if (ori != null) {
            final RFormularioExterno res = new RFormularioExterno();
            res.setIdentificadorFormulario(ori.getIdentificador());
            res.setIdentificadorGestorFormularios(ori.getIdentificador());// TODO:
                                                                          // revisar
                                                                          // esta
                                                                          // setIdentificadorGestorFormularios,
                                                                          // pendiente
                                                                          // validar
            return res;
        }
        return null;
    }

    private String generaObligatoriedad(
            TypeFormularioObligatoriedad obligatoriedad) {
        return obligatoriedad == null ? null : obligatoriedad.toString();
    }

    private List<RPagoTramite> generarPagos(List<Tasa> tasas, String idioma) {
        final List<RPagoTramite> lres = new ArrayList<RPagoTramite>();
        if (tasas != null) {
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
                res.setSimularPago(t.isSimulado());
                lres.add(res);
            }
        }
        return lres;
    }

    private List<RAnexoTramite> generaAnexos(List<Documento> documentos,
            String idioma) {
        final List<RAnexoTramite> lres = new ArrayList<RAnexoTramite>();
        if (documentos != null) {
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
                resPE.setExtensiones(Arrays.asList(d.getExtensiones()
                        .split(AdapterUtils.SEPARADOR_EXTENSIONES)));
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

    private RAnexoTramiteAyuda generaAyudaFichero(Literal texto,
            Fichero fichero, String url, String idioma) {
        final RAnexoTramiteAyuda res = new RAnexoTramiteAyuda();
        res.setFichero(restApiService.getReferenciaFichero(
                fichero == null ? null : fichero.getCodigo()));
        res.setMensajeHtml(AdapterUtils.generarLiteralIdioma(texto, idioma));
        res.setUrl(url);
        return res;
    }

    public RVersionTramite getrVersionTramite() {
        return rVersionTramite;
    }

    public void setrVersionTramite(RVersionTramite rVersionTramite) {
        this.rVersionTramite = rVersionTramite;
    }

}

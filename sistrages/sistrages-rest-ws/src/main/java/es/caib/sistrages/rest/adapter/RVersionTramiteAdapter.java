package es.caib.sistrages.rest.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
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
private RestApiInternaService restApiService;
	public RVersionTramiteAdapter(TramiteVersion tv, String idioma, String idiomaDefecto, RestApiInternaService restApiService) {
		this.restApiService = restApiService;

		List<String> idiSoportados = Arrays.asList(tv.getIdiomasSoportados().split(AdapterUtils.SEPARADOR_IDIOMAS));
		// el idioma a usar sigue este orden:
		// idioma si es un idioma soportado
		// idioma por defecto si lo hay
		// el primero de la lista de idiomas soportados

		String idiRes = idiSoportados.get(0);
		if (idiSoportados.contains(idioma)) {
			idiRes = idioma;
		} else {
			if (!StringUtils.isEmpty(idiomaDefecto)) {
				idiRes = idiomaDefecto;
			}
		}

		final List<RPasoTramitacion> pasos = new ArrayList<>();
		List<TramitePaso> ltp = tv.getListaPasos();
		for (TramitePaso tp : ltp) {
			RPasoTramitacion pt = creaPaso(tp, idiRes);
			if (pt != null) {
				pasos.add(pt);
			}
		}

		rVersionTramite = new RVersionTramite();
		rVersionTramite.setIdentificador(tv.getIdTramite() + "");
		rVersionTramite.setVersion(tv.getNumeroVersion());
		rVersionTramite.setPasos(pasos);
		rVersionTramite.setDominios(generarListaDominios(tv.getListaDominios()));
		rVersionTramite.setIdioma(idiRes);
		rVersionTramite.setTipoFlujo(tv.getTipoFlujo()==null?null:tv.getTipoFlujo().toString());
		rVersionTramite.setControlAcceso(generaControlAcceso(tv)); // ver pantalla
		rVersionTramite.setIdEntidad(generaidEntidadfromTramite(tv.getIdTramite())); // buscar hacia arriba
		rVersionTramite.setPropiedades(generaPropiedades(tv)); //ver pantalla

	}

	private String generaidEntidadfromTramite(Long idTramite) {

	Tramite t = restApiService.loadTramite(idTramite);
	if(t!=null && t.getIdEntidad()!=null ) {
		return t.getIdEntidad()+"";
	}
		return null;
	}

	private RVersionTramitePropiedades generaPropiedades(TramiteVersion tv) {
		RVersionTramitePropiedades res = new RVersionTramitePropiedades();
		res.setAutenticado(tv.isAutenticado());
		res.setDiasPersistencia(tv.getPersistenciaDias());
		res.setIdiomas(Arrays.asList(tv.getIdiomasSoportados().split(AdapterUtils.SEPARADOR_IDIOMAS)));
		res.setNivelQAA(tv.getNivelQAA());
		res.setNoAutenticado(tv.isNoAutenticado());
		res.setPersistente(tv.isPersistencia());
		res.setScriptParametrosIniciales(AdapterUtils.generaScript(tv.getScriptInicializacionTramite()));
		res.setScriptPersonalizacion(AdapterUtils.generaScript(tv.getScriptPersonalizacion()));
		return res;
	}

	private RVersionTramiteControlAcceso generaControlAcceso(TramiteVersion tv) {
		RVersionTramiteControlAcceso res = new RVersionTramiteControlAcceso();
		res.setActivo(tv.isActiva());
		res.setDebug(tv.isDebug());
		res.setLimitarTramitacion(tv.isLimiteTramitacion());
		res.setLimiteTramitacionInicios(tv.getIntLimiteTramitacion());
		res.setLimiteTramitacionIntervalo(tv.getIntLimiteTramitacion());
		return null;
	}



	private RPasoTramitacion creaPaso(TramitePaso paso, String idioma) {

		if (paso instanceof TramitePasoRellenar) {
			return crearPasoRellenar((TramitePasoRellenar) paso, idioma);
		} else if (paso instanceof TramitePasoDebeSaber) {
			return crearPasoDebeSaber((TramitePasoDebeSaber) paso, idioma);
		} else if (paso instanceof TramitePasoTasa) {
			return crearPasoTasa((TramitePasoTasa) paso, idioma);
		} else if (paso instanceof TramitePasoAnexar) {
			return crearPasoAnexar((TramitePasoAnexar) paso,idioma);
		} else if (paso instanceof TramitePasoRegistrar) {
			return crearPasoRegistrar((TramitePasoRegistrar) paso, idioma);
		}

		return null;
	}

	private RPasoTramitacion crearPasoRegistrar(TramitePasoRegistrar paso, String idioma) {
		RPasoTramitacionRegistrar resPaso = new RPasoTramitacionRegistrar();
		resPaso.setIdentificador(paso.getIdPasoTramitacion());
		resPaso.setTipo(paso.getTipo() != null ? paso.getTipo().toString() : null);
		resPaso.setPasoFinal(paso.isPasoFinal());
		resPaso.setAdmiteRepresentacion(paso.isAdmiteRepresentacion());
		resPaso.setDestino(null);
		resPaso.setInstruccionesPresentacionHtml(
				AdapterUtils.generarLiteralIdioma(paso.getInstruccionesPresentacion(), idioma));
		resPaso.setInstruccionesTramitacionHtml(
				AdapterUtils.generarLiteralIdioma(paso.getInstruccionesFinTramitacion(), idioma));
		resPaso.setScriptDestino(AdapterUtils.generaScript(paso.getScriptDestinoRegistro()));
		resPaso.setScriptPresentador(AdapterUtils.generaScript(paso.getScriptPresentador()));
		resPaso.setScriptRepresentante(AdapterUtils.generaScript(paso.getScriptRepresentante()));
		resPaso.setScriptValidar(AdapterUtils.generaScript(paso.getScriptValidarRegistrar()));
		resPaso.setValidaRepresentacion(paso.isValidaRepresentacion());
		return resPaso;
	}

	private RPasoTramitacion crearPasoAnexar(TramitePasoAnexar paso, String idioma) {
		RPasoTramitacionAnexar resPaso = new RPasoTramitacionAnexar();
		resPaso.setIdentificador(paso.getIdPasoTramitacion());
		resPaso.setTipo(paso.getTipo() != null ? paso.getTipo().toString() : null);
		resPaso.setPasoFinal(paso.isPasoFinal());
		resPaso.setAnexos(generaAnexos(paso.getDocumentos(),idioma));
		resPaso.setScriptAnexosDinamicos(AdapterUtils.generaScript(paso.getScriptAnexosDinamicos()));
		return resPaso;
	}

	private RPasoTramitacion crearPasoDebeSaber(TramitePasoDebeSaber paso, String idioma) {
		RPasoTramitacionDebeSaber resPaso = new RPasoTramitacionDebeSaber();
		resPaso.setIdentificador(paso.getIdPasoTramitacion());
		resPaso.setTipo(paso.getTipo() != null ? paso.getTipo().toString() : null);
		resPaso.setPasoFinal(paso.isPasoFinal());
		resPaso.setInstruccionesInicio(AdapterUtils.generarLiteralIdioma(paso.getInstruccionesIniciales(), idioma));
		return resPaso;
	}

	private RPasoTramitacion crearPasoTasa(TramitePasoTasa paso,String idioma) {
		RPasoTramitacionPagar resPaso = new RPasoTramitacionPagar();
		resPaso.setIdentificador(paso.getIdPasoTramitacion());
		resPaso.setTipo(paso.getTipo() != null ? paso.getTipo().toString() : null);
		resPaso.setPasoFinal(paso.isPasoFinal());
		resPaso.setPagos(generarPagos(paso.getTasas(),idioma));
		return resPaso;
	}

	private RPasoTramitacion crearPasoRellenar(TramitePasoRellenar paso,String idioma) {
		RPasoTramitacionRellenar resPaso = new RPasoTramitacionRellenar();
		resPaso.setIdentificador(paso.getIdPasoTramitacion());
		resPaso.setTipo(paso.getTipo() != null ? paso.getTipo().toString() : null);
		resPaso.setPasoFinal(paso.isPasoFinal());
		resPaso.setFormularios(generaFormularios(paso.getFormulariosTramite(), idioma));
		return resPaso;

	}

	private List<RDominio> generarListaDominios(List<Long> listaDominios) {
		List<RDominio> res = new ArrayList<RDominio>();
		for(Long idDom : listaDominios) {
			Dominio dom = restApiService.loadDominio(idDom);
			if(dom!=null) {
				RDominio rdom = new RDominio();
				rdom.setCachear(dom.isCacheable());
				rdom.setIdentificador(dom.getIdentificador());
				rdom.setSql(dom.getSql());
				rdom.setTipo(generaTipo(dom.getTipo()));
				rdom.setUri(dom.getUrl());
				res.add(rdom);
			}
		}
		return res;
	}

	private String generaTipo(TypeDominio tipo) {
		return tipo.toString();
	}

	private List<RFormularioTramite> generaFormularios(List<FormularioTramite> formulariosTramite,String idioma) {
		List<RFormularioTramite> res = new ArrayList<RFormularioTramite>();
		for (FormularioTramite f : formulariosTramite) {
			RFormularioTramite formularioTramite = new RFormularioTramite();
			formularioTramite.setDescripcion(AdapterUtils.generarLiteralIdioma(f.getDescripcion(), idioma));
			formularioTramite.setFirmar(f.isDebeFirmarse());
			formularioTramite.setFormularioExterno(generaFormularioExterno(f.getFormularioGestorExterno()));
			formularioTramite.setFormularioInterno(generaFormularioInterno(f.getIdFormularioInterno(),idioma));
			formularioTramite.setIdentificador(f.getIdentificador());
			formularioTramite.setObligatoriedad(generaObligatoriedad(f.getObligatoriedad()));
			formularioTramite.setPresentarPreregistro(f.isDebePrerregistrarse());
			formularioTramite.setScriptDatosIniciales(AdapterUtils.generaScript(f.getScriptDatosIniciales()));
			formularioTramite.setScriptObligatoriedad(AdapterUtils.generaScript( f.getScriptObligatoriedad()));
			formularioTramite.setScriptFirmantes(AdapterUtils.generaScript(f.getScriptFirma()));
			formularioTramite.setScriptParametrosApertura(AdapterUtils.generaScript(f.getScriptParametros()));
			formularioTramite.setScriptPostguardar(AdapterUtils.generaScript(f.getScriptRetorno()));
			res.add(formularioTramite);
		}
		return res;
	}


	private RFormularioInterno generaFormularioInterno(Long idFormularioInterno,String idioma) {
		if(idFormularioInterno!=null) {
			DisenyoFormulario d = restApiService.getFormularioInterno(idFormularioInterno);
			if(d!=null) {
				RFormularioInterno formInterno = new RFormularioInterno();
				formInterno.setMostrarTitulo(d.isMostrarCabecera());
				formInterno.setPaginas(generarPaginas(d.getPaginas(),d.getCodigo(),idioma));
				formInterno.setPlantillas(generarPlantillas(d.getPlantillas(),idioma));
				formInterno.setScriptPlantillas(AdapterUtils.generaScript(d.getScriptPlantilla()));
				formInterno.setTitulo(AdapterUtils.generarLiteralIdioma(d.getTextoCabecera(), idioma));
				return formInterno;
			}
		}
		return null;
	}

	private List<RPlantillaFormulario> generarPlantillas(List<PlantillaFormulario> plantillas, String idioma) {
		List<RPlantillaFormulario> lpf = new ArrayList<RPlantillaFormulario>();
		for(PlantillaFormulario p : plantillas) {
			RPlantillaFormulario plantillaFormulario = new RPlantillaFormulario();
			plantillaFormulario.setClaseFormateador((getStringFormateador(p.getIdFormateadorFormulario())));
			plantillaFormulario.setDefecto(p.isPorDefecto());
			plantillaFormulario.setFicheroPlantilla(generaFicheroPlantilla(p.getCodigo(),idioma));
			plantillaFormulario.setIdentificador(p.getIdentificador());
			lpf.add(plantillaFormulario);
		}

		return lpf;
	}

	private String generaFicheroPlantilla(Long idPlantillaFormulario, String idioma) {
		List<PlantillaIdiomaFormulario> lp = restApiService.getListaPlantillaIdiomaFormularioById(idPlantillaFormulario);
		if(lp!=null) {
			for (PlantillaIdiomaFormulario p : lp) {
				if(idioma.equals(p.getIdioma()) && p.getFichero()!=null ) {
					return restApiService.getReferenciaFichero(p.getFichero().getCodigo());
				}
			}
		}
		return null;
	}

	private String getStringFormateador(Long idFormateadorFormulario) {
		FormateadorFormulario f = restApiService.getFormateadorFormulario(idFormateadorFormulario);
		if(f!=null) {
			return f.getClassname();
		}
		return null;
	}

	private List<RPaginaFormulario> generarPaginas(List<PaginaFormulario> paginas, Long idForm, String idioma) {
		List<RPaginaFormulario> lpf = new ArrayList<RPaginaFormulario>();
		for(PaginaFormulario p : paginas) {
			RPaginaFormulario res = new RPaginaFormulario();
			res.setHtmlB64(restApiService.getPaginaFormularioHTMLAsistente(p.getCodigo(), idioma));
			res.setLineas(generarLineas(p.getLineas(), idioma));
			res.setPaginaFinal(p.isPaginaFinal());
			res.setScriptValidacion(AdapterUtils.generaScript(p.getScriptValidacion()));
			lpf.add(res);
		}
		return lpf;
	}

	private List<RLineaComponentes> generarLineas(List<LineaComponentesFormulario> lineas, String idioma) {
		List<RLineaComponentes> llc = new ArrayList<RLineaComponentes>();
		for (LineaComponentesFormulario l : lineas) {
			RLineaComponentes res = new RLineaComponentes();
			res.setComponentes(generaComponentes(l.getComponentes(),idioma));
			llc.add(res);
		}
		return llc;
	}

	private List<RComponente> generaComponentes(List<ComponenteFormulario> componentes, String idioma) {
		// TODO Auto-generated method stub
		List<RComponente> lc = new ArrayList<RComponente>();

		for (ComponenteFormulario c : componentes) {

			switch (c.getTipo()) {

			case CAMPO_TEXTO:
				RComponenteTextbox resTB = new RComponenteTextbox();
				ComponenteFormularioCampoTexto ct = (ComponenteFormularioCampoTexto)c;
				
				resTB.setAlineacion(ct.getAlineacionTexto()==null?null:ct.getAlineacionTexto().toString());
				resTB.setAyuda(AdapterUtils.generarLiteralIdioma(ct.getAyuda(), idioma));
				resTB.setColumnas(ct.getNumColumnas());
				resTB.setEtiqueta(AdapterUtils.generarLiteralIdioma(ct.getTexto(),idioma));				
				resTB.setIdentificador(ct.getIdComponente());
				resTB.setMostrarEtiqueta(!ct.isNoMostrarTexto());				
				resTB.setPropiedadesCampo(generaPropiedadesCampo(ct));
				resTB.setTextoExpRegular(generaExpresionRegular(ct.getExpresionRegular()));
				resTB.setTextoIdentificacion(generaTextoIdentificacion(ct));
				resTB.setTextoNormal(generaTextoNormal(ct));
				resTB.setTextoNumero(generaTextoNumero(ct));
				resTB.setTipo(ct.getTipo()==null?null:ct.getTipo().toString());
				resTB.setTipoTexto(ct.getTipoCampoTexto()==null?null:ct.getTipoCampoTexto().toString());	
				lc.add(resTB);
				break;
			case CHECKBOX:
				RComponenteCheckbox resCH= new RComponenteCheckbox();
				ComponenteFormularioCampoCheckbox cch = (ComponenteFormularioCampoCheckbox)c;
				resCH.setAlineacion(cch.getAlineacionTexto()==null?null:cch.getAlineacionTexto().toString());
				resCH.setAyuda(AdapterUtils.generarLiteralIdioma(cch.getAyuda(), idioma));
				resCH.setColumnas(cch.getNumColumnas());
				resCH.setEtiqueta(AdapterUtils.generarLiteralIdioma(cch.getTexto(),idioma));				
				resCH.setIdentificador(cch.getIdComponente());
				resCH.setMostrarEtiqueta(!cch.isNoMostrarTexto());
				resCH.setPropiedadesCampo(generarPropiedadesCampo(cch));
				resCH.setTipo(cch.getTipo()==null?null:cch.getTipo().toString());
				resCH.setValorChecked(cch.getValorChecked());
				resCH.setValorNoChecked(cch.getValorNoChecked());
				lc.add(resCH);
				break;
			case SELECTOR:
				RComponenteSelector resSL = new RComponenteSelector();
				ComponenteFormularioCampoSelector cs = (ComponenteFormularioCampoSelector)c;
				resSL.setAlineacion(cs.getAlineacionTexto()==null?null:cs.getAlineacionTexto().toString());
				resSL.setAyuda(AdapterUtils.generarLiteralIdioma(cs.getAyuda(),idioma));
				resSL.setColumnas(cs.getNumColumnas());
				resSL.setEtiqueta(AdapterUtils.generarLiteralIdioma(cs.getTexto(),idioma));
				resSL.setIdentificador(cs.getIdComponente());
				resSL.setListaDominio(generaListaDominio(cs));
				resSL.setListaFija(generaListaFija(cs.getListaValorListaFija()));
				resSL.setMostrarEtiqueta(!cs.isNoMostrarTexto());
				resSL.setPropiedadesCampo(generarPropiedadesCampo(cs));
				resSL.setScriptListaValores(AdapterUtils.generaScript(cs.getScriptValoresPosibles()));
				resSL.setTipo(cs.getTipo()==null?null:cs.getTipo().toString());
				resSL.setTipoListaValores(cs.getTipoListaValores()==null?null:cs.getTipoListaValores().toString());
				lc.add(resSL);
				break;
			case SECCION:
				RComponenteSeccion resSC = new RComponenteSeccion();
				//TODO: terminar
				break;
			case ETIQUETA:
				RComponenteAviso resET =new RComponenteAviso();
				//TODO: terminar
				break;
			default:
				return null;
			}
			
			

		}

		return null;
	}

	
	private List<RValorListaFija> generaListaFija(List<ValorListaFija> listaValorListaFija) {
		// TODO Auto-generated method stub
		return null;
	}

	private RListaDominio generaListaDominio(ComponenteFormularioCampoSelector cs) {
		// TODO Auto-generated method stub
		RListaDominio l = new RListaDominio();
		l.setCampoCodigo(cs.getCampoDominioCodigo());
		l.setCampoDescripción(cs.getCampoDominioDescripcion());
		l.setDominio(generaDominio(cs.getCodDominio()));
		l.setParametros(generaParametrosDominio(cs.getListaParametrosDominio()));
		
		
		
		return null;
	}

	private List<RParametroDominio> generaParametrosDominio(List<ParametroDominio> listaParametrosDominio) {
		// TODO Auto-generated method stub
		return null;
	}

	private String generaDominio(Long codDominio) {
		// TODO Auto-generated method stub
		return null;
	}

	private RPropiedadesCampo generarPropiedadesCampo(ComponenteFormularioCampoCheckbox ori) {
		RPropiedadesCampo res = new RPropiedadesCampo();
		res.setNoModificable(ori.isNoModificable());
		res.setObligatorio(ori.isObligatorio());
		res.setScriptAutorrellenable(AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
		res.setScriptEstado((AdapterUtils.generaScript(ori.getScriptSoloLectura()))); 
		res.setScriptValidacion((AdapterUtils.generaScript(ori.getScriptValidacion())));
		res.setSoloLectura(ori.isSoloLectura());
		return res;
	}
	
	private RPropiedadesCampo generaPropiedadesCampo(ComponenteFormularioCampoTexto ori) {
		RPropiedadesCampo res = new RPropiedadesCampo();
		res.setNoModificable(ori.isNoModificable());
		res.setObligatorio(ori.isObligatorio());
		res.setScriptAutorrellenable(AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
		res.setScriptEstado((AdapterUtils.generaScript(ori.getScriptSoloLectura()))); 
		res.setScriptValidacion((AdapterUtils.generaScript(ori.getScriptValidacion())));
		res.setSoloLectura(ori.isSoloLectura());
		return res;
	}
	
	private RPropiedadesCampo generarPropiedadesCampo(ComponenteFormularioCampoSelector ori) {
		RPropiedadesCampo res = new RPropiedadesCampo();
		res.setNoModificable(ori.isNoModificable());
		res.setObligatorio(ori.isObligatorio());
		res.setScriptAutorrellenable(AdapterUtils.generaScript(ori.getScriptAutorrellenable()));
		res.setScriptEstado((AdapterUtils.generaScript(ori.getScriptSoloLectura()))); 
		res.setScriptValidacion((AdapterUtils.generaScript(ori.getScriptValidacion())));
		res.setSoloLectura(ori.isSoloLectura());
		return res;
	}

	
	
	

	private RPropiedadesTextoNumero generaTextoNumero(ComponenteFormularioCampoTexto ct) {
		RPropiedadesTextoNumero res= new RPropiedadesTextoNumero();		
		res.setFormatoNumero(ct.getNumeroSeparador()==null?null:ct.getNumeroSeparador().toString());
		res.setNegativos(ct.isNumeroConSigno());
		res.setPrecisionDecimal(ct.getNumeroDigitosDecimales());
		res.setPrecisionEntera(ct.getNumeroDigitosEnteros());
		res.setRango(ct.isPermiteRango());
		res.setRangoDesde(ct.getNumeroRangoMinimo());
		res.setRangoHasta(ct.getNumeroRangoMaximo());		
		return res;
	}

	private RPropiedadesTextoNormal generaTextoNormal(ComponenteFormularioCampoTexto ct) {
		RPropiedadesTextoNormal res = new RPropiedadesTextoNormal();
		res.setMultilinea(ct.isNormalMultilinea());
		res.setTamanyoMax(ct.getNormalTamanyo());		
		return res;
	}

	private RPropiedadesTextoIdentificacion generaTextoIdentificacion(ComponenteFormularioCampoTexto componenteOrigen) {
		RPropiedadesTextoIdentificacion res = new RPropiedadesTextoIdentificacion();
		res.setCif(componenteOrigen.isIdentCif());
		res.setNie(componenteOrigen.isIdentNie());
		res.setNif(componenteOrigen.isIdentNif());
		res.setNss(componenteOrigen.isIdentNss());		
		return res;
	}

	private RPropiedadesTextoExpRegular generaExpresionRegular(String exp) {		
		if(StringUtils.isEmpty(exp)) {
			return null;			
		}
		RPropiedadesTextoExpRegular res = new RPropiedadesTextoExpRegular();
		res.setExpresionRegular(exp);		
		return res;
	}



	private RFormularioExterno generaFormularioExterno(GestorExternoFormularios formularioGestorExterno) {
		// TODO Auto-generated method stub
		return null;
	}



	private String generaObligatoriedad(TypeFormularioObligatoriedad obligatoriedad) {
		return obligatoriedad==null?null:obligatoriedad.toString();
	}



	private List<RPagoTramite> generarPagos(List<Tasa> tasas,String idioma) {
		List<RPagoTramite> lres = new ArrayList<RPagoTramite>();
		for (Tasa t : tasas) {
			RPagoTramite res = new RPagoTramite();
			res.setDescripcion(AdapterUtils.generarLiteralIdioma(t.getDescripcion(),idioma));
			res.setIdentificador(t.getIdentificador());
			res.setObligatoriedad(t.getObligatoriedad()==null?null: t.getObligatoriedad().toString());
			res.setScriptDependencia(AdapterUtils.generaScript(t.getScriptObligatoriedad()));
			res.setScriptPago(AdapterUtils.generaScript(t.getScriptPago()));
			res.setSimularPago(t.isSimulado());
			lres.add(res);
		}
		return lres;
	}


	private List<RAnexoTramite> generaAnexos(List<Documento> documentos, String idioma) {
		List<RAnexoTramite> lres = new ArrayList<RAnexoTramite>();
		for(Documento d : documentos) {
			RAnexoTramite res = new RAnexoTramite();
			res.setAyuda(generaAyudaFichero(d.getAyudaTexto(),d.getAyudaFichero(),d.getAyudaURL(),idioma));
			res.setDescripcion(AdapterUtils.generarLiteralIdioma(d.getDescripcion(), idioma));
			res.setIdentificador(d.getIdentificador());
			res.setObligatoriedad(d.getObligatoriedad()==null?null:d.getObligatoriedad().toString());
			res.setPresentacion(d.getTipoPresentacion()==null?null:d.getTipoPresentacion().toString());
			res.setScriptDependencia(AdapterUtils.generaScript(d.getScriptObligatoriedad()));

			RAnexoTramitePresentacionElectronica resPE = new RAnexoTramitePresentacionElectronica();
			resPE.setAnexarFirmado(d.isDebeAnexarFirmado());
			resPE.setConvertirPDF(d.isDebeConvertirPDF());
			resPE.setExtensiones(Arrays.asList(d.getExtensiones().split(AdapterUtils.SEPARADOR_EXTENSIONES)));
			resPE.setFirmar(d.isDebeFirmarDigitalmente());
			resPE.setInstancias(d.getNumeroInstancia());
			resPE.setScriptFirmantes(AdapterUtils.generaScript(d.getScriptFirmarDigitalmente()));
			resPE.setScriptValidacion(AdapterUtils.generaScript(d.getScriptValidacion()));
			resPE.setTamanyoMax(d.getTamanyoMaximo());
			resPE.setTamanyoUnidad(d.getTipoTamanyo()==null?null:d.getTipoTamanyo().toString());

			res.setPresentacionElectronica(resPE);
			lres.add(res);
		}
		return null;
	}


	private RAnexoTramiteAyuda generaAyudaFichero(Literal texto, Fichero fichero, String url, String idioma) {
		RAnexoTramiteAyuda res = new RAnexoTramiteAyuda();
		res.setFichero(restApiService.getReferenciaFichero(fichero==null?null:fichero.getCodigo()));
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

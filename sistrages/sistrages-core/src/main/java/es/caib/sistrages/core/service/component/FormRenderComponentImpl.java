package es.caib.sistrages.core.service.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCaptcha;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoCheckbox;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoOculto;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSeccionReutilizable;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioEtiqueta;
import es.caib.sistrages.core.api.model.ComponenteFormularioListaElementos;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.types.TypeAlineacionTexto;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeOrientacion;
import es.caib.sistrages.core.service.component.literales.Literales;
import es.caib.sistrages.core.service.repository.dao.FormularioInternoDao;
import es.caib.sistrages.core.service.repository.dao.SeccionReutilizableDao;

@Component("formRenderComponent")
public class FormRenderComponentImpl implements FormRenderComponent {

	private String lineSeparator;
	private final boolean debug = false;

	@Autowired
	FormularioInternoDao formIntDao;

	@Autowired
	SeccionReutilizableDao seccionDao;

	@Autowired
	Literales literales;

	public FormRenderComponentImpl() {
		super();
		setLineSeparator(System.getProperty("line.separator"));
	}

	@Override
	public String generaPaginaHTMLEditor(final Long pIdForm, final Long pPage, final String pIdComponente,
			final String pLang, final Boolean pMostrarOcultos, final String pContexto) {
		return paginaHTML(pIdForm, pPage, pIdComponente, pLang, pMostrarOcultos, pContexto, true);
	}

	@Override
	public String generaPaginaHTMLAsistente(final Long pPage, final String pLang) {
		return paginaHTML(null, pPage, null, pLang, true, null, false);
	}

	private String paginaHTML(final Long pIdForm, final Long pPage, final String pIdComponente, final String pLang,
			final boolean pMostrarOcultos, final String pContexto, final boolean pModoEdicion) {
		final StringBuilder html = new StringBuilder();
		DisenyoFormulario formulario = null;
		PaginaFormulario pagina = null;

		if (pModoEdicion) {
			formulario = formIntDao.getFormularioPaginasById(pIdForm);
			pagina = formIntDao.getContenidoPaginaById(formulario.getPaginas().get(pPage.intValue() - 1).getCodigo(), false);

		} else {
			pagina = formIntDao.getContenidoPaginaById(pPage, false);
		}

		escribeLinea(html, "<!doctype html>", 0);
		escribeLinea(html, "<html>", 0);

		if (pModoEdicion) {
			cabeceraHTML(html, pContexto);
		}

		escribeLinea(html, "<body>", 0);

		if (pModoEdicion) {
			escribeLinea(html, "<div id=\"imc-contenidor\" class=\"imc-contenidor\" >", 1);
		}

		if (formulario != null && formulario.isMostrarCabecera()) {
			cabeceraFormulario(html, trataLiteral(formulario.getTextoCabecera().getTraduccion(pLang)));
		}

		escribeLinea(html, "<form>", 2);
		escribeLinea(html, "<div id=\"imc-forms-formulari\" class=\"imc-forms-formulari imc-form\">", 3);

		escribeLinea(html, "<div class=\"imc-form-contingut\">", 4);

		cuerpoHTML(html, pagina, pLang, pModoEdicion, pMostrarOcultos, false, "", null, null);

		escribeLinea(html, "</div>", 4);
		escribeLinea(html, "</div>", 3);
		escribeLinea(html, "</form>", 2);

		if (pModoEdicion) {
			escribeLinea(html, "</div>", 1);
		}

		if (pModoEdicion) {
			scripts(html, pIdComponente);
		}

		escribeLinea(html, "</body>", 0);
		escribeLinea(html, "</html>", 0);

		return html.toString();
	}

	private void cabeceraHTML(final StringBuilder pOut, final String pContexto) {
		final String urlCssIni = "<link rel=\"stylesheet\" media=\"screen\" href=\"" + pContexto + "/forms/css/";
		final String urlCssFin = ".css\" />";

		final String urlJsIni = "<script src=\"" + pContexto + "/forms/js/";
		final String urlJsFin = ".js\"></script>";

		escribeLinea(pOut, "<head>", 0);
		escribeLinea(pOut, "<meta charset=\"utf-8\" />", 1);
		escribeLinea(pOut, "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />", 1);

		escribeLinea(pOut, urlCssIni, "imc-forms", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-forms--select", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-forms--taula-iframe", urlCssFin, 1);
		escribeLinea(pOut, urlCssIni, "imc-forms--edicio", urlCssFin, 1);

		escribeLinea(pOut, urlJsIni, "utils/modernizr-imc-0.3", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "utils/jquery-3.3.1.min", urlJsFin, 1);
		escribeLinea(pOut, urlJsIni, "imc-forms--edicio", urlJsFin, 1);

		escribeLinea(pOut, "<script type=\"text/javascript\">var APP_CAMPO_ID = \"data-codigo\";</script>", 1);
		escribeLinea(pOut, "<script type=\"text/javascript\">var APP_CAMPO_TIPO_SECCION = \"data-tipo-seccion\";</script>", 1);
		escribeLinea(pOut, "<script type=\"text/javascript\">var APP_CAMPO_SECCION_ID = \"data-seccion-id\";</script>", 1);
		escribeLinea(pOut, "<script type=\"text/javascript\">var APP_CAMPO_SECCION_FORM_ID = \"data-seccion-id-formulario\";</script>", 1);

		escribeLinea(pOut, "</head>", 0);

	}

	private void cabeceraFormulario(final StringBuilder pOut, final String textoCabecera) {
		escribeLinea(pOut, "<header class=\"imc-forms-cap\" id=\"imc-forms-cap\">", 2);
		escribeLinea(pOut, "<div class=\"imc--contingut\">", 3);
		escribeLinea(pOut, "<h2><span>", textoCabecera, "</span></h2>", 4);
		escribeLinea(pOut, "</div>", 3);
		escribeLinea(pOut, "</header>", 2);
	}

	private void cuerpoHTML(final StringBuilder pOut, final PaginaFormulario pPagina, final String pLang,
			final boolean pModoEdicion, final boolean pMostrarOcultos, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable, final Long idSeccion, final ComponenteFormulario pCF) {

		if (pPagina != null) {

			boolean ultimoCampoEsOculto = false;

			for (final LineaComponentesFormulario lc : pPagina.getLineas()) {

				ultimoCampoEsOculto = false;

				for (final ComponenteFormulario cf : lc.getComponentes()) {

					switch (cf.getTipo()) {
					case SECCION:
						campoSeccion(pOut, cf, pLang,  pModoEdicion , isTipoSeccionReutilizable, idSeccion, pCF);
						ultimoCampoEsOculto = false;
						break;
					case CAMPO_TEXTO:
						campoTexto(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case ETIQUETA:
						campoEtiqueta(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case CHECKBOX:
						campoCheckBox(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case SELECTOR:
						campoSelector(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case CAMPO_OCULTO:
						campoOculto(pOut, cf, pLang,   pModoEdicion, pMostrarOcultos, ultimoCampoEsOculto, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = true;
						break;
					case CAPTCHA:
						campoCaptcha(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case LISTA_ELEMENTOS:
						campoListaElementos(pOut, cf, pLang,   pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
						ultimoCampoEsOculto = false;
						break;
					case SECCION_REUTILIZABLE:
						campoSeccionReutilizable(pOut, cf, pLang,   pModoEdicion, pMostrarOcultos);
						ultimoCampoEsOculto = false;
						break;
					default:
						break;
					}

				}

				if (lc.getComponentes().isEmpty()) {
					escribeLinea(pOut, "<br/><br/>", 6);
				}

				escribeLinea(pOut, "<div class=\"imc-element imc-separador imc-sep-salt-carro\"",
						escribeCodigo("L" + String.valueOf(lc.getCodigo()), pModoEdicion),
						escribeId("L" + String.valueOf(lc.getCodigo())), " id=\"L", String.valueOf(lc.getCodigo()),
						"\"></div>", 5);

			}
		}
	}

	private void campoSeccion(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final Long idSeccion, final ComponenteFormulario seccion) {
		final ComponenteFormularioSeccion componente = (ComponenteFormularioSeccion) pCF;

		if (isTipoSeccionReutilizable) {
			escribeLinea(pOut, "<h4 ", escribeCodigo(idSeccion, pModoEdicion), escribeId(idSeccion.toString()),
					"class=\"imc-element imc-seccio\">", 5);
		} else {
			escribeLinea(pOut, "<h4 ", escribeCodigo(pCF.getCodigo(), pModoEdicion), escribeId(pCF.getIdComponente()),
				"class=\"imc-element imc-seccio\">", 5);
		}

		if (isTipoSeccionReutilizable) {
			escribeLinea(pOut, "<span class=\"imc-se-marca\">", ((ComponenteFormularioCampoSeccionReutilizable)seccion).getLetra(), "</span>", 6);
		} else {
			escribeLinea(pOut, "<span class=\"imc-se-marca\">", componente.getLetra(), "</span>", 6);
		}
		if (!pCF.isNoMostrarTexto() && pCF.getTexto() != null) {
			if (isTipoSeccionReutilizable) {
				escribeLinea(pOut, "<span class=\"imc-se-titol\">", trataLiteral(pCF == null || pCF.getTexto() == null ? "" : pCF.getTexto().getTraduccion(pLang)),
						"</span>", 6);
			} else {
				escribeLinea(pOut, "<span class=\"imc-se-titol\">", trataLiteral(pCF.getTexto().getTraduccion(pLang)),
							"</span>", 6);
			}

		}

		escribeLinea(pOut, "</h4>", 5);

	}

	private void campoSeccionReutilizable(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, boolean pMostrarOcultos) {
		final ComponenteFormularioCampoSeccionReutilizable componente = (ComponenteFormularioCampoSeccionReutilizable) pCF;
		SeccionReutilizable seccionReutilizable = seccionDao.getSeccionReutilizable(componente.getIdSeccionReutilizable());
		Long pIdForm = seccionReutilizable.getIdFormularioAsociado();
		DisenyoFormulario formulario = formIntDao.getFormularioPaginasById(pIdForm);
		// 2868
		if (!formulario.getPaginas().isEmpty()) {
			PaginaFormulario pagina = formIntDao.getContenidoPaginaById(formulario.getPaginas().get(0).getCodigo(), false);
			String dataSeccionReutilizable = " data-tipo-seccion=\"S\" data-seccion-id=\""+seccionReutilizable.getCodigo()+"\" data-seccion-id-formulario=\""+seccionReutilizable.getIdFormularioAsociado()+"\" " ;
			pintarLineaSeccionReutilizable(pOut, pCF, dataSeccionReutilizable); //Linea INICIO
			cuerpoHTML(pOut, pagina, pLang, pModoEdicion, pMostrarOcultos, true, dataSeccionReutilizable, componente.getCodigo(), pCF);
			pintarLineaSeccionReutilizable(pOut, pCF,dataSeccionReutilizable); //Línea FIN
		}

	}

	private void pintarLineaSeccionReutilizable(final StringBuilder pOut, final ComponenteFormulario pCF, String dataSeccionReutilizable) {
		escribeLinea(pOut, "<div class=\"imc-element imc-separador imc-sep-salt-carro imc-borde-seccion-reutilizable\" data-codigo=\""+pCF.getCodigo()+"\" data-id=\""+pCF.getCodigo()+"\" id=\" \" data-tipo-seccion=\"true\" "+dataSeccionReutilizable+" ></div>", 5);
	}

	private void campoTexto(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccion, final String dataSeccionReutilizable) {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) pCF;

		final StringBuilder estilo = new StringBuilder();
		// estilo.append("imc-el-name-").append(String.valueOf(campo.getCodigo()));

		String tipo = null;
		final StringBuilder elemento = new StringBuilder();

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			if (TypeAlineacionTexto.CENTRO.equals(campo.getAlineacionTexto())) {
				estilo.append(" imc-el-centre");
			} else if (TypeAlineacionTexto.DERECHA.equals(campo.getAlineacionTexto())) {
				estilo.append(" imc-el-dreta");
			}
		}

		if (TypeCampoTexto.NORMAL.equals(campo.getTipoCampoTexto())) {
			if (campo.isNormalMultilinea()) {
				tipo = "textarea";
				Integer nfilas = campo.getNormalNumeroLineas();
				if (nfilas == null) {
					nfilas = 1;
				}
				estilo.append(" imc-el-files-").append(String.valueOf(nfilas));

				elemento.append("<textarea id=\"").append(campo.getIdComponente()).append("\" cols=\"20\" rows=\"")
						.append(nfilas).append(dataSeccionReutilizable).append("\"></textarea>");

			} else {
				tipo = "text";
			}
		} else if (TypeCampoTexto.FECHA.equals(campo.getTipoCampoTexto())) {
			tipo = "date";
		} else if (TypeCampoTexto.HORA.equals(campo.getTipoCampoTexto())) {
			tipo = "time";
		} else {
			tipo = "text";
		}

		escribeLinea(pOut, "<div", escribeId(campo.getIdComponente())+" " + dataSeccionReutilizable, escribeCodigo(pCF.getCodigo(), true),
				escribeObligatorio(campo, pModoEdicion), escribeTieneScripts(campo, pModoEdicion),
				" class=\"imc-element ", estilo.toString(), "\" data-type=\"", tipo, "\">", 5);

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			escribeLinea(pOut, "<div class=\"imc-el-etiqueta\"><label for=\"", String.valueOf(campo.getIdComponente()),
					"\">", trataLiteral(campo.getTexto().getTraduccion(pLang)), "</label></div>", 6);
		}

		if (tipo != null && !"textarea".equals(tipo)) {
			// Cambiamos en modo editor a tipo text para que no saque el calendario al hacer
			// clic
			if (pModoEdicion && "date".equals(tipo)) {
				tipo = "text";
			}
			elemento.append("<input id=\"").append(campo.getIdComponente()).append("\" type=\"" + tipo + "\" "+dataSeccionReutilizable+" />");
		}

		escribeLinea(pOut, "<div class=\"imc-el-control\">", elemento.toString(), "</div>", 6);

		escribeLinea(pOut, "</div>", 5);

	}


	/**
	 * Para pintar una captcha
	 *
	 * @param pOut
	 * @param pCF
	 * @param pLang
	 * @param pModoEdicion
	 */
	private void campoListaElementos(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioListaElementos campo = (ComponenteFormularioListaElementos) pCF;


		 escribeLinea(pOut, " <fieldset", escribeId(campo.getIdComponente())+ " " +dataSeccionReutilizable, escribeCodigo(pCF.getCodigo(), pModoEdicion),
	                escribeObligatorio(campo, pModoEdicion), escribeTieneScripts(campo, pModoEdicion),
	                "class=\"imc-element imc-el-taula\" data-type=\"table\">", 5);

        String textoEtiqueta = null;
        if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
            textoEtiqueta = trataLiteral(campo.getTexto().getTraduccion(pLang));
        } else {
            textoEtiqueta = "&nbsp;";
        }

        escribeLinea(pOut, "<legend>", textoEtiqueta, "</legend>", 6);

        escribeLinea(pOut, "<div class=\"imc-el-taula-botonera\">", 6);
        escribeLinea(pOut, "<div class=\"imc--controls\">", 7);
        escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-afegix\"><span>"
                +literales.getLiteral("componente", "listaelementos.botones.anyadir", pLang)+"</span></button>", 8);
        escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-modifica\"><span>"
                +literales.getLiteral("componente", "listaelementos.botones.modificar", pLang)+"</span></button>", 8);
        if (!pModoEdicion) {
               escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-consulta\"><span>"
              +literales.getLiteral("componente", "listaelementos.botones.consultar", pLang)+"</span></button>", 8);
        }
        escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-elimina\"><span>"
                +literales.getLiteral("componente", "listaelementos.botones.eliminar", pLang)+"</span></button>", 8);
        escribeLinea(pOut, "</div>", 7);

        escribeLinea(pOut, "<div class=\"imc--posicio\">", 7);
        escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-puja\"><span>"
                +literales.getLiteral("componente", "listaelementos.botones.subir", pLang)+"</span></button>", 8);
        escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-baixa\"><span>"
                +literales.getLiteral("componente", "listaelementos.botones.bajar", pLang)+"</span></button>", 8);
        escribeLinea(pOut, "</div>", 7);

        escribeLinea(pOut, "</div>", 6);



        if (!pModoEdicion) {
               escribeLinea(pOut, "<div class=\"imc-el-taula-cerca\">", 6);
               escribeLinea(pOut, "<button type=\"button\" class=\"imc-bt-cerca\"><span>Cerca</span></button>", 7);
               escribeLinea(pOut, "</div>", 6);
        }

        escribeLinea(pOut, "<div class=\"imc-el-taula-elements\"></div>", 6);
        escribeLinea(pOut, "</fieldset>", 5);

	}

	/**
	 * Para pintar una captcha
	 *
	 * @param pOut
	 * @param pCF
	 * @param pLang
	 * @param pModoEdicion
	 */
	private void campoCaptcha(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioCampoCaptcha campo = (ComponenteFormularioCampoCaptcha) pCF;

		final StringBuilder estilo = new StringBuilder();

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		escribeLinea(pOut, "<div", escribeId(campo.getIdComponente())+ " " +dataSeccionReutilizable, escribeCodigo(pCF.getCodigo(), pModoEdicion),
				escribeTieneScripts(campo, pModoEdicion), "class=\"imc-element imc-el-captcha", estilo.toString(),
				"\" data-type=\"captcha\">", 5);

		escribeLinea(pOut, "<div class=\"imc-el-etiqueta\">", 6);
		escribeLinea(pOut, "<label for=\"CAPTCHA\">" + trataLiteral(campo.getTexto().getTraduccion(pLang)) + "</label>",
				7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);
		escribeLinea(pOut, "<div class=\"imc--img\">", 7);
		escribeLinea(pOut, "<img src=\"\" alt=\"\">", 8);
		escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "<input id=\"", String.valueOf(campo.getIdComponente()), "\" name=\" "+dataSeccionReutilizable+" " ,
				String.valueOf(campo.getIdComponente()) + "\"  type=\"text\">", 7);
		escribeLinea(pOut, "<button type=\"button\"><span>"+literales.getLiteral("componente", "captcha.refrescarImagen", pLang)+"</span></button>", 7);
		escribeLinea(pOut, "</div>", 6);//Refresca imagen
		escribeLinea(pOut, "</div>", 5);

	}

	private void campoCheckBox(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioCampoCheckbox campo = (ComponenteFormularioCampoCheckbox) pCF;

		final StringBuilder estilo = new StringBuilder();
		String texto = "";

		if (campo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(campo.getNumColumnas());
		}

		// estilo.append(" imc-el-name-").append(String.valueOf(campo.getCodigo()));

		if (!campo.isNoMostrarTexto() && campo.getTexto() != null) {
			texto = "<label for=\"" + campo.getIdComponente() + "\">"
					+ trataLiteral(campo.getTexto().getTraduccion(pLang)) + "</label>";
		}
		escribeLinea(pOut, "<div", escribeId(campo.getIdComponente())+" " +dataSeccionReutilizable, escribeCodigo(pCF.getCodigo(), pModoEdicion),
				escribeTieneScripts(campo, pModoEdicion), "class=\"imc-element imc-el-check", estilo.toString(),
				"\" data-type=\"check\">", 5);

		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);
		escribeLinea(pOut, "<div class=\"imc-input-check\">", 7);
		escribeLinea(pOut, "<input id=\"", String.valueOf(campo.getIdComponente()), "\" "+dataSeccionReutilizable+" type=\"checkbox\">", 8);
		escribeLinea(pOut, texto, 8);

		escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "</div>", 5);

	}

	private void campoSelector(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) pCF;

		switch (campo.getTipoCampoIndexado()) {
		case DINAMICO:
			campoSelectorDinamico(pOut, campo, pLang, pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
			break;
		case DESPLEGABLE:
			campoSelectorDesplegable(pOut, campo, pLang, pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
			break;
		case MULTIPLE:
			campoSelectorMultiple(pOut, campo, pLang, pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
			break;
		case UNICA:
			campoSelectorUnica(pOut, campo, pLang, pModoEdicion, isTipoSeccionReutilizable, dataSeccionReutilizable);
			break;
		default:
			break;
		}

	}

	private void campoSelectorDinamico(final StringBuilder pOut, final ComponenteFormularioCampoSelector pCampo,
			final String pLang, final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {

		final StringBuilder estilo = new StringBuilder();
		String texto = "";

		if (pCampo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(pCampo.getNumColumnas());
		}

		// estilo.append(" imc-el-name-").append(String.valueOf(pCampo.getCodigo()));

		if (!pCampo.isNoMostrarTexto() && pCampo.getTexto() != null) {
			texto = "<div class=\"imc-el-etiqueta\"><label for=\"" + pCampo.getIdComponente() + " "+dataSeccionReutilizable+" \">"
					+ trataLiteral(pCampo.getTexto().getTraduccion(pLang)) + "</label></div>";
		}

		escribeLinea(pOut, "<div", escribeId(pCampo.getIdComponente()), escribeCodigo(pCampo.getCodigo(), pModoEdicion),
				escribeObligatorio(pCampo, pModoEdicion), escribeTieneScripts(pCampo, pModoEdicion),
				" class=\"imc-element ", estilo.toString(), "\" "+dataSeccionReutilizable+" data-type=\"select\">", 5);

		escribeLinea(pOut, texto, 6);

		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);

		// Importate, la siguiente linea no cambia por dos razones:
		// - Se deja así para que parezca en el editor un desplegable
		// - Se produce un error con los tipos textarea (y habría que revisar el js)
		// No se escribe lo siguiente escribeLinea(pOut, "<textarea id=\"" +
		// pCampo.getIdComponente() + "\" cols=\"\" rows=\"\" />", 8);

		// TODO De momento ñapa para poder mostrar en editor
		if (pModoEdicion) {
			escribeLinea(pOut, "<input id=\"" + pCampo.getIdComponente() + "\" cols=\"\" rows=\"\" "+dataSeccionReutilizable+" />", 8);
		} else {
			escribeLinea(pOut, "<textarea id=\"" + pCampo.getIdComponente() + "\" cols=\"\" rows=\"\" "+dataSeccionReutilizable+" />", 8);
		}

		// escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "</div>", 5);
	}

	private void campoSelectorDesplegable(final StringBuilder pOut, final ComponenteFormularioCampoSelector pCampo,
			final String pLang, final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {

		final StringBuilder estilo = new StringBuilder();
		String texto = "";

		if (pCampo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(pCampo.getNumColumnas());
		}

		// estilo.append(" imc-el-name-").append(String.valueOf(pCampo.getCodigo()));

		if (!pCampo.isNoMostrarTexto() && pCampo.getTexto() != null) {
			texto = "<div class=\"imc-el-etiqueta\"><label for=\"" + pCampo.getIdComponente() + "\">"
					+ trataLiteral(pCampo.getTexto().getTraduccion(pLang)) + "</label></div>";
		}

		escribeLinea(pOut, "<div", escribeId(pCampo.getIdComponente()) , escribeCodigo(pCampo.getCodigo(), pModoEdicion),
				escribeObligatorio(pCampo, pModoEdicion), escribeTieneScripts(pCampo, pModoEdicion),
				" class=\"imc-element imc-el-selector", estilo.toString(), "\" "+dataSeccionReutilizable+" data-type=\"select\">", 5);

		escribeLinea(pOut, texto, 6);

		escribeLinea(pOut, "<div class=\"imc-el-control\">", 6);
		escribeLinea(pOut, "<div class=\"imc-select imc-opcions\">", 7);

		escribeLinea(pOut, "<a class=\"imc-select\" tabindex=\"0\" href=\"javascript:;\" style=\"\"></a>", 8);
		escribeLinea(pOut, "<input id=\"", String.valueOf(pCampo.getIdComponente()), "\" "+dataSeccionReutilizable+" type=\"hidden\">", 8);

		escribeLinea(pOut, "</div>", 7);
		escribeLinea(pOut, "</div>", 6);
		escribeLinea(pOut, "</div>", 5);
	}

	private void campoSelectorMultiple(final StringBuilder pOut, final ComponenteFormularioCampoSelector pCampo,
			final String pLang, final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {

		final StringBuilder estilo = new StringBuilder();

		if (pCampo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(pCampo.getNumColumnas());
		}

		// estilo.append(" imc-el-name-").append(String.valueOf(pCampo.getCodigo()));

		escribeLinea(pOut, "<fieldset", escribeId(pCampo.getIdComponente()) ,
				escribeCodigo(pCampo.getCodigo(), pModoEdicion), escribeObligatorio(pCampo, pModoEdicion),
				escribeTieneScripts(pCampo, pModoEdicion), " class=\"imc-element", estilo.toString(),
				"\" "+dataSeccionReutilizable+" data-type=\"check-list\">", 6);

		if (!pCampo.isNoMostrarTexto() && pCampo.getTexto() != null) {
			escribeLinea(pOut, "<legend class=\"imc-label\">", trataLiteral(pCampo.getTexto().getTraduccion(pLang)),
					"</legend>", 7);
		}

		if (pModoEdicion) {
			escribeLinea(pOut, "<ul>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-check\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".a\" "+dataSeccionReutilizable+" checked=\"checked\" type=\"checkbox\"><label for=\"",
					String.valueOf(pCampo.getIdComponente()), ".a\">Opc. A</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-check\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".b\" "+dataSeccionReutilizable+" type=\"checkbox\"><label for=\"", String.valueOf(pCampo.getIdComponente()),
					".b\">Opc. B</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-check\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".c\" "+dataSeccionReutilizable+" type=\"checkbox\"><label for=\"", String.valueOf(pCampo.getIdComponente()),
					".c\">Opc. C</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "</ul>", 7);
		} else {
			escribeLinea(pOut, "<ul>", 7);
			escribeLinea(pOut, "</ul>", 7);
		}
		escribeLinea(pOut, "</fieldset>", 6);
	}

	private void campoSelectorUnica(final StringBuilder pOut, final ComponenteFormularioCampoSelector pCampo,
			final String pLang, final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {

		final StringBuilder estilo = new StringBuilder();

		if (pCampo.getNumColumnas() > 1) {
			estilo.append(" imc-el-").append(pCampo.getNumColumnas());
		}

		// estilo.append(" imc-el-name-").append(String.valueOf(pCampo.getCodigo()));

		String orientacion;
		if (pCampo.getOrientacion().equals(TypeOrientacion.HORIZONTAL.toString())) {
			orientacion = "imc-el-horizontal";
		} else {
			orientacion = "imc-el-vertical";
		}
		escribeLinea(pOut, "<fieldset", escribeId(pCampo.getIdComponente()),
				escribeCodigo(pCampo.getCodigo(), pModoEdicion), escribeObligatorio(pCampo, pModoEdicion),
				escribeTieneScripts(pCampo, pModoEdicion), " class=\"imc-element " + orientacion, estilo.toString(),
				"\" "+dataSeccionReutilizable+" data-type=\"radio-list\">", 6);

		if (!pCampo.isNoMostrarTexto() && pCampo.getTexto() != null) {
			escribeLinea(pOut, "<legend class=\"imc-label\">", trataLiteral(pCampo.getTexto().getTraduccion(pLang)),
					"</legend>", 7);
		}

		if (pModoEdicion) {
			escribeLinea(pOut, "<ul>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-radio\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".a\" checked=\"checked\" type=\"radio\"><label for=\"", String.valueOf(pCampo.getIdComponente()),
					".a\">Opc. A</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-radio\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".b\" type=\"radio\"><label for=\"", String.valueOf(pCampo.getIdComponente()),
					".b\">Opc. B</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "<li>", 7);
			escribeLinea(pOut, "<div class=\"imc-input-radio\"><input id=\"", String.valueOf(pCampo.getIdComponente()),
					".c\" type=\"radio\"><label for=\"", String.valueOf(pCampo.getIdComponente()),
					".c\">Opc. C</label></div>", 8);
			escribeLinea(pOut, "</li>", 7);
			escribeLinea(pOut, "</ul>", 7);
		} else {
			escribeLinea(pOut, "<ul>", 7);
			escribeLinea(pOut, "</ul>", 7);
		}

		escribeLinea(pOut, "</fieldset>", 6);
	}

	private void campoEtiqueta(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioEtiqueta componente = (ComponenteFormularioEtiqueta) pCF;
		final StringBuilder estilo = new StringBuilder();

		switch (componente.getTipoEtiqueta()) {
		case INFO:
			estilo.append("imc-missatge-en-linia-info");
			break;
		case WARNING:
			estilo.append("imc-missatge-en-linia-alerta");
			break;
		case ERROR:
			estilo.append("imc-missatge-en-linia-error");
			break;
		case ETIQUETA:
			estilo.append("imc-missatge-en-linia-etiqueta");
			break;
		}

		escribeLinea(pOut, "<div class=\"imc-element imc-missatge-en-linia imc-missatge-en-linia-icona-sup ",
				estilo.toString(), "\" ", escribeId(pCF.getIdComponente()),
				escribeCodigo(pCF.getCodigo(), pModoEdicion), "><p>", 5);
		if (pCF.getTexto() != null) {
			escribeLinea(pOut, trataLiteral(pCF.getTexto().getTraduccion(pLang)), 6);
		}
		escribeLinea(pOut, "</p></div>", 5);
	}

	private void campoOculto(final StringBuilder pOut, final ComponenteFormulario pCF, final String pLang,
			final boolean pModoEdicion, final boolean pMostrarOcultos, final boolean ultimoCampoEsOculto, final boolean isTipoSeccionReutilizable, final String dataSeccionReutilizable) {
		final ComponenteFormularioCampoOculto campo = (ComponenteFormularioCampoOculto) pCF;

		final StringBuilder elemento = new StringBuilder();

		String mostrar = null;
		if (pMostrarOcultos) {
			mostrar = "visible";
		} else {
			mostrar = "hidden";
		}
		String more = null;
		if (ultimoCampoEsOculto) {
			more = "imc-el-hidden-more";
		} else {
			more = "";
		}

		elemento.append("<input id=\"").append(campo.getIdComponente()).append("\" "+dataSeccionReutilizable+" type=\"hidden\"/>");

		escribeLinea(pOut, "<div", escribeId(campo.getIdComponente()), escribeCodigo(pCF.getCodigo(), pModoEdicion),
				escribeTieneScripts(campo, pModoEdicion), " class=\"imc-element imc-el-hidden ", more ,
				"\" data-type=\"hidden\" data-hidden=\"" , mostrar, "\" "+dataSeccionReutilizable +" >", elemento.toString(), "</div>", 5);

	}

	private void scripts(final StringBuilder pOut, final String pIdComponente) {
		escribeLinea(pOut, "<script type=\"text/javascript\">", 1);
		escribeLinea(pOut,
				"function rcEditarComponente(id) {parent.seleccionarElementoCommand([{name:'id', value:id}]);}", 2);
		escribeLinea(pOut,
				"function rcEditarComponente2(id,tipoSeccion,seccionid,seccionFormId) {parent.seleccionarElementoCommand([{name:'id', value:id},{name:'tipoSeccion', value:tipoSeccion},{name:'seccionId', value:seccionid},{name:'seccionFormId', value:seccionFormId}]);}", 2);
/*
		escribeLinea(pOut,
				"function rcEditarComponente(id) {parent.seleccionarElementoCommand([{name:'id', value:id},{name:'data-tipo-seccion', value:data-tipo-seccion},{name:'data-seccion-id', value:data-seccion-id},{name:'data-seccion-id-formulario', value:data-seccion-id-formulario}]);}", 2);
**/
		escribeLinea(pOut, "var idComponente=\"" + pIdComponente + "\";", 2);
		escribeLinea(pOut, "</script>", 1);
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6,
			final String pTexto7, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		pOut.append(pTexto7);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6,
			final String pTexto7, final String pTexto8, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		pOut.append(pTexto7);
		pOut.append(pTexto8);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6,
			final String pTexto7, final String pTexto8, final String pTexto9, final String pTexto10, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		pOut.append(pTexto7);
		pOut.append(pTexto8);
		pOut.append(pTexto9);
		pOut.append(pTexto10);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private void escribeLinea(final StringBuilder pOut, final String pTexto1, final String pTexto2,
			final String pTexto3, final String pTexto4, final String pTexto5, final String pTexto6,
			final String pTexto7, final String pTexto8, final String pTexto9, final String pTexto10,
			final String pTexto11, final int pNtab) {
		if (debug) {
			pOut.append(StringUtils.leftPad("", pNtab, "\t"));
		}
		pOut.append(pTexto1);
		pOut.append(pTexto2);
		pOut.append(pTexto3);
		pOut.append(pTexto4);
		pOut.append(pTexto5);
		pOut.append(pTexto6);
		pOut.append(pTexto7);
		pOut.append(pTexto8);
		pOut.append(pTexto9);
		pOut.append(pTexto10);
		pOut.append(pTexto11);
		if (debug) {
			pOut.append(getLineSeparator());
		}
	}

	private String escribeId(final String pId) {
		return escribeId(pId, null);
	}

	private String escribeId(final String pId, final String pOrden) {
		String id = null;
		if (pOrden == null) {
			id = " data-id=\"" + pId + "\"";
		} else {
			id = " data-id=\"" + pId + "." + pOrden + "\"";
		}
		return id;
	}

	private String escribeCodigo(final Long pCodigo, final boolean pModoEdicion) {
		return escribeCodigo(String.valueOf(pCodigo), null, pModoEdicion);
	}

	private String escribeCodigo(final String pCodigo, final boolean pModoEdicion) {
		return escribeCodigo(pCodigo, null, pModoEdicion);
	}

	private String escribeObligatorio(final ComponenteFormularioCampo comp, final boolean pModoEdicion) {
		String res = "";
		if (comp.isObligatorio() && pModoEdicion) {
			res = "data-obligatori=\"s\"";
		}
		return res;
	}

	private String escribeCodigo(final String pCodigo, final String pOrden, final boolean pModoEdicion) {
		String id = "";
		if (pModoEdicion) {
			if (pOrden == null) {
				id = " data-codigo=\"" + pCodigo + "\"";
			} else {
				id = " data-codigo=\"" + pCodigo + "." + pOrden + "\"";
			}
		}
		return id;
	}

	private String escribeTieneScripts(final ComponenteFormularioCampo campo, final boolean pModoEdicion) {
		String res = "";
		if (pModoEdicion && (campo.getScriptAutorrellenable() != null || campo.getScriptSoloLectura() != null
				|| campo.getScriptValidacion() != null || ((campo instanceof ComponenteFormularioCampoSelector)
						&& ((ComponenteFormularioCampoSelector) campo).getScriptValoresPosibles() != null))) {
			res = " data-script=\"s\" ";
		}
		return res;
	}

	private String trataLiteral(final String pLiteral) {
		return (pLiteral == null ? "?" : pLiteral);
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(final String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

}

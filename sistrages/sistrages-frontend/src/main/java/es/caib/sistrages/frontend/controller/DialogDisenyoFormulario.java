package es.caib.sistrages.frontend.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import es.caib.sistrages.core.api.exception.ErrorNoControladoException;
import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampo;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoTexto;
import es.caib.sistrages.core.api.model.ComponenteFormularioSeccion;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.ParametroDominio;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorListaFija;
import es.caib.sistrages.core.api.model.comun.ConstantesDisenyo;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeCampoTexto;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeListaValores;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilCoreApi;
import es.caib.sistrages.core.api.util.UtilDisenyo;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * Disenyo formulario.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogDisenyoFormulario extends DialogControllerBase {

	@Inject
	FormularioInternoService formIntService;

	@Inject
	TramiteService tramiteService;

	@Inject
	DominioService dominioService;

	/** Id formulario **/
	private String id;

	/** Id. formulario paso. **/
	private String idFormulario;

	/**
	 * id tramite.
	 */
	private String idTramiteVersion;

	/** Formulario. **/
	private DisenyoFormulario formulario;

	private int paginaActual;

	private String posicionamiento;

	/** Componente editado (copia original). **/
	private ObjetoFormulario objetoFormularioEdit;

	/** Traducciones editado (cuando se llama a editar traducciones). **/
	private Literal traduccionesEdit;

	/** Url ventana propiedades **/
	private String panelPropiedadesUrl;

	/** Url detalle componente. **/
	private String detalleComponenteUrl;

	/** Url iframe. **/
	private String urlIframe;

	/**
	 * Indica si esta 'colapsado' el panel de propiedades (a true colapsado).
	 **/
	private boolean visiblePropiedades = false;

	/**
	 * Indica si esta 'colapsado' el panel de scripts (a true colapsado).
	 **/
	private boolean visibleScripts = true;

	/**
	 * Indica si esta 'colapsado' el panel de estilos (a true colapsado).
	 **/
	private boolean visibleEstilos = true;

	/**
	 * Codigo componente destino al pedir confirmacion de cambios.
	 **/
	private String codigoObjFormularioDestino = null;

	private List<Integer> ncolumnas;

	private TramiteVersion tramiteVersion;

	private List<String> idiomas;
	private String idioma;

	/**
	 * Inicializacion.
	 **/
	public void init() {

		// TITULO ??
		// setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// Recuperacion formulario
		recuperarFormulario(id);

		// recupera tramite version
		if (StringUtils.isNotEmpty(idTramiteVersion)) {
			tramiteVersion = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
			idiomas = UtilTraducciones.getIdiomasSoportados(tramiteVersion);
			if (!idiomas.isEmpty()) {
				if (idiomas.contains(UtilJSF.getIdioma().toString())) {
					idioma = UtilJSF.getIdioma().toString();
				} else {
					idioma = idiomas.get(0);
				}
			}
		}

		panelPropiedadesUrl = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
		urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();
		paginaActual = 1;
		posicionamiento = "D";
	}

	/**
	 * Recupera formulario
	 *
	 * @param idForm
	 **/
	private void recuperarFormulario(final String idForm) {
		formulario = formIntService.getFormularioInternoCompleto(Long.parseLong(idForm));
	}

	/**
	 * Editar componente.
	 **/
	public void editarComponente() {

		final FacesContext context = FacesContext.getCurrentInstance();
		final Map<String, String> map = context.getExternalContext().getRequestParameterMap();

		String idComponente = null;

		if (StringUtils.isNotEmpty(map.get("id"))) {
			idComponente = map.get("id");
		}

		// Verificamos si ha habido cambios en el componente
		if (objetoFormularioEdit != null && StringUtils.isNotEmpty(idComponente)
				&& !objetoFormularioEdit.getCodigo().equals(Long.valueOf(idComponente.replace("L", "")))) {
			ObjetoFormulario ofOriginal = null;

			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				ofOriginal = formulario.getPaginas().get(paginaActual - 1).getLinea(objetoFormularioEdit.getCodigo());
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				ofOriginal = formulario.getPaginas().get(paginaActual - 1)
						.getComponente(objetoFormularioEdit.getCodigo());
			}

			if (!UtilCoreApi.equalsModelApi(ofOriginal, objetoFormularioEdit)) {
				// Guardamos componente destino
				codigoObjFormularioDestino = idComponente;
				// Invocamos a boton para que dispare ventana de confirmacion
				final RequestContext contextReq = RequestContext.getCurrentInstance();
				contextReq.execute("PF('confirmationButton').jq.click();");
				return;
			}

			// Cambiamos a nuevo componente
			cambiarEdicionComponente(idComponente);
		} else if (objetoFormularioEdit == null) {
			// Cambiamos a nuevo componente
			cambiarEdicionComponente(idComponente);
		}

	}

	/**
	 * Cambiar edicion componente.
	 *
	 * @param idComponente
	 *            the id componente
	 */
	private void cambiarEdicionComponente(final String idComponente) {
		limpiaSeleccion();

		if (idComponente != null) {
			ObjetoFormulario cf = null;
			if (idComponente.contains("L")) {
				cf = formulario.getPaginas().get(paginaActual - 1).getLinea(Long.parseLong(idComponente.substring(1)));
			} else {
				cf = formulario.getPaginas().get(paginaActual - 1).getComponente(Long.parseLong(idComponente));
			}

			// Buscamos nuevo componente
			if (cf != null) {
				objetoFormularioEdit = (ObjetoFormulario) UtilCoreApi.cloneModelApi(cf);
				// Verificamos que no haya problemas en la serialización
				if (!UtilCoreApi.equalsModelApi(cf, objetoFormularioEdit)) {
					throw new FrontException("Componente clonado no coincide");
				}
			}
		}

		String pagina = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
		detalleComponenteUrl = null;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof ComponenteFormulario) {
				switch (((ComponenteFormulario) objetoFormularioEdit).getTipo()) {
				case CAMPO_TEXTO:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				case SELECTOR:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				case ETIQUETA:
					pagina = "/secure/app/dialogDisenyoFormularioEtiqueta.xhtml";
					break;
				case SECCION:
					pagina = "/secure/app/dialogDisenyoFormularioSeccion.xhtml";
					break;
				case CHECKBOX:
					pagina = "/secure/app/dialogDisenyoFormularioComponente.xhtml";
					break;
				// TODO PENDIENTE
				default:
					break;
				}

				detalleComponenteUrl = "/secure/app/dialogDisenyoFormularioComponente"
						+ ((ComponenteFormulario) objetoFormularioEdit).getTipo() + ".xhtml";
			} else if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				pagina = "/secure/app/dialogDisenyoFormularioLinea.xhtml";
			}
		}

		panelPropiedadesUrl = pagina;
		generaNumColumnas();
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Indica el toogle.
	 *
	 * @param event
	 */
	public void onToggle(final ToggleEvent event) {
		if ("VISIBLE".equals(event.getVisibility().name())) {

			if ("panelPropietats".equals(event.getComponent().getId())) {
				setVisiblePropiedades(false);
				setVisibleScripts(true);
				setVisibleEstilos(true);
			}

			if ("panelScripts".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(false);
				setVisibleEstilos(true);
			}

			if ("panelEstilos".equals(event.getComponent().getId())) {
				setVisiblePropiedades(true);
				setVisibleScripts(true);
				setVisibleEstilos(false);
			}
		}

	}

	/**
	 * Confirmacion cambios.
	 **/
	public void confirmacionCambios() {
		// Guarda cambios
		aplicarCambios();
		// Cambia panel propiedades a nuevo componente seleccionado
		cambiarEdicionComponente(codigoObjFormularioDestino);
		codigoObjFormularioDestino = null;
	}

	/**
	 * Aplicar cambios.
	 **/
	public void aplicarCambios() {

		if (objetoFormularioEdit != null) {
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			final LineaComponentesFormulario linea = pagina.getLineaComponente(objetoFormularioEdit.getCodigo());

			if (objetoFormularioEdit instanceof ComponenteFormulario) {
				if (!linea.cabenComponentes((ComponenteFormulario) objetoFormularioEdit)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("warning.componente.sinespacio"), true);
					return;
				}

				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
					if (TypeListaValores.DOMINIO.equals(campo.getTipoListaValores()) && campo.getCodDominio() == null) {
						UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.dominio"),
								true);
						return;
					} else if (TypeListaValores.DOMINIO.equals(campo.getTipoListaValores())
							&& campo.getCodDominio() != null) {
						final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
						if (!TypeDominio.LISTA_FIJA.equals(dominio.getTipo())) {
							if (StringUtils.isEmpty(campo.getCampoDominioCodigo())) {
								UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.codigo"), true);
								return;
							} else if (StringUtils.isEmpty(campo.getCampoDominioDescripcion())) {
								UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.descripcion"), true);
								return;
							} else if (!dominio.getParametros().isEmpty()
									&& campo.getListaParametrosDominio().isEmpty()) {
								UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
										UtilJSF.getLiteral("warning.dominio.parametros"), true);
								return;
							}
						}
					}

				}

				final boolean isDuplicado = formIntService.isIdElementoFormularioDuplicated(Long.valueOf(id),
						objetoFormularioEdit.getCodigo(),
						((ComponenteFormulario) objetoFormularioEdit).getIdComponente());
				if (isDuplicado) {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("warning.identificador.duplicado"), true);
					return;
				}

				final ComponenteFormulario cfOriginal = pagina.getComponente(objetoFormularioEdit.getCodigo());

				// TODO PENDIENTE GUARDAR (ver como hacerlo, ¿beanutils?¿metodos
				// particulares
				// por tipo componente?) De momento no dejamos cambiar codigo
				// para permitir
				// dejar seleccionando
				try {
					BeanUtils.copyProperties(cfOriginal, objetoFormularioEdit);
				} catch (final Exception e) {
					throw new ErrorNoControladoException(e);
				}

				final ComponenteFormulario cfUpdate = (ComponenteFormulario) formIntService
						.updateComponenteFormulario(cfOriginal);

				// si es campo selector con dominio damos de alta el dominio si
				// en dominios
				// empleados no lo está ya
				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
					if (TypeListaValores.DOMINIO.equals(campo.getTipoListaValores()) && campo.getCodDominio() != null
							&& !dominioService.tieneTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo())) {
						dominioService.addTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo());
					}
				}

				try {
					BeanUtils.copyProperties(objetoFormularioEdit, cfUpdate);
					BeanUtils.copyProperties(cfOriginal, cfUpdate);
				} catch (final Exception e) {
					throw new ErrorNoControladoException(e);
				}

				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));

				// Refresca iframe formulario
				// TODO Pasarle componente destino para posicionarse
				urlIframe = "FormRenderServlet?ts=" + System.currentTimeMillis();
			}
		}
	}

	/**
	 * Descartar cambios.
	 **/
	public void descartarCambios() {
		// Pasamos a componente destino
		cambiarEdicionComponente(codigoObjFormularioDestino);
		codigoObjFormularioDestino = null;

	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	private void returnDialogoTraducciones(final SelectEvent event) {
		try {
			final DialogResult respuesta = (DialogResult) event.getObject();
			// Solo tiene sentido cambios para edicion
			if (!respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				final Literal traduccionesMod = (Literal) respuesta.getResult();
				BeanUtils.copyProperties(traduccionesEdit, traduccionesMod);
			} else if (respuesta.isCanceled() && respuesta.getModoAcceso() == TypeModoAcceso.EDICION) {
				traduccionesEdit = null;
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new FrontException("Error estableciendo traducciones", e);
		}

	}

	public void returnDialogoTraduccionesTexto(final SelectEvent event) {
		returnDialogoTraducciones(event);
		if (traduccionesEdit != null && ((ComponenteFormulario) objetoFormularioEdit).getTexto() == null) {
			((ComponenteFormulario) objetoFormularioEdit).setTexto(traduccionesEdit);
		}

	}

	/**
	 * Editar texto componente.
	 */
	public void editarTraduccionesTexto() {
		if (((ComponenteFormulario) objetoFormularioEdit).getTexto() == null) {
			traduccionesEdit = UtilTraducciones.getTraduccionesPorDefecto();
		} else {
			traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getTexto();
		}
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso),
				((ComponenteFormulario) objetoFormularioEdit).getTexto(), idiomas, idiomas);
	}

	public void returnDialogoTraduccionesAyuda(final SelectEvent event) {
		returnDialogoTraducciones(event);
		if (traduccionesEdit != null && ((ComponenteFormulario) objetoFormularioEdit).getAyuda() == null) {
			((ComponenteFormulario) objetoFormularioEdit).setAyuda(traduccionesEdit);
		}

	}

	/**
	 * Editar ayuda componente.
	 */
	public void editarTraduccionesAyuda() {
		if (((ComponenteFormulario) objetoFormularioEdit).getAyuda() == null) {
			traduccionesEdit = UtilTraducciones.getTraduccionesPorDefecto();
		} else {
			traduccionesEdit = ((ComponenteFormulario) objetoFormularioEdit).getAyuda();
		}
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), traduccionesEdit, idiomas, idiomas);
	}

	public boolean isComponenteCampo() {
		return objetoFormularioEdit instanceof ComponenteFormularioCampo;
	}

	public boolean isCampoTextoNormal() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.NORMAL.equals(campo.getTipoCampoTexto());
	}

	public boolean isCampoExprRegular() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.EXPRESION.equals(campo.getTipoCampoTexto());
	}

	public boolean isCampoNumero() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.NUMERO.equals(campo.getTipoCampoTexto());
	}

	public boolean isCampoIdentificacion() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.ID.equals(campo.getTipoCampoTexto());
	}

	public boolean isCampoTelefono() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		return TypeCampoTexto.TELEFONO.equals(campo.getTipoCampoTexto());
	}

	public boolean isLVFija() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		return TypeListaValores.FIJA.equals(campo.getTipoListaValores());
	}

	public boolean isLVDominio() {
		boolean resultado = false;

		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			resultado = TypeListaValores.DOMINIO.equals(campo.getTipoListaValores());
		}

		return resultado;
	}

	public boolean isLVScript() {
		boolean res = false;
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			res = TypeListaValores.SCRIPT.equals(campo.getTipoListaValores());
		}
		return res;
	}

	public boolean isDominioLF() {
		boolean resultado = true;
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = TypeDominio.LISTA_FIJA.equals(dominio.getTipo());
		}

		return resultado;
	}

	public boolean isDominioTieneParametros() {
		boolean resultado = false;
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = !dominio.getParametros().isEmpty();
		}

		return resultado;
	}

	public String getIdentificadorDominio() {
		String resultado = null;

		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

		if (campo.getCodDominio() != null) {
			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
			resultado = dominio.getIdentificador();
		}

		return resultado;
	}

	/**
	 * Cancelar.
	 **/
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Abre dialogo para propiedades formulario.
	 **/
	public void abrirPropiedadesFormulario() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id);
		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), this.idTramiteVersion);

		Map<String, Object> mochilaDatos = null;

		// limpiamos mochila
		UtilJSF.getSessionBean().limpiaMochilaDatos();

		// metemos datos en la mochila
		mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		mochilaDatos.put(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO, idiomas);

		UtilJSF.openDialog(DialogPropiedadesFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 950,
				460);
	}

	public int getNumeroPaginas() {
		return formulario.getPaginas().size();
	}

	public void moverPaginaIzq() {
		paginaActual--;
		if (paginaActual < 1) {
			paginaActual = 1;
		}

		limpiaSeleccion();
	}

	public void moverPaginaDer() {
		paginaActual++;
		if (paginaActual > getNumeroPaginas()) {
			paginaActual = getNumeroPaginas();
		}
		limpiaSeleccion();
	}

	public boolean getHabilitadoBtnPagIzq() {
		return paginaActual > 1;
	}

	public boolean getHabilitadoBtnPagDer() {
		return paginaActual < getNumeroPaginas();
	}

	public boolean getHabilitadoBtnObjIzq() {
		final int orden = getOrden();

		return orden != 0 ? orden > 1 : false;
	}

	public boolean getHabilitadoBtnObjDer() {
		final int orden = getOrden();

		return orden != 0 ? orden < ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA && orden < numObjetosLinea() : false;
	}

	private int getOrden() {
		int orden = 0;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				orden = ((LineaComponentesFormulario) objetoFormularioEdit).getOrden();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				orden = ((ComponenteFormulario) objetoFormularioEdit).getOrden();
			}
		}
		return orden;
	}

	private int numObjetosLinea() {
		int numObj = 0;
		if (objetoFormularioEdit != null) {
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);

			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				numObj = pagina.getLineas().size();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				numObj = pagina.getLineaComponente(objetoFormularioEdit.getCodigo()).getComponentes().size();
			}
		}
		return numObj;
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			final DisenyoFormulario formularioNuevo = (DisenyoFormulario) respuesta.getResult();

			recuperarFormulario(id);

			// formulario.setMostrarCabecera(formularioNuevo.isMostrarCabecera());
			// formulario.setTextoCabecera(formularioNuevo.getTextoCabecera());
			paginaActual = 1;
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	public void insertaCampoTexto() {
		insertaCampo(TypeObjetoFormulario.CAMPO_TEXTO);
	}

	public void insertaCheckBox() {
		insertaCampo(TypeObjetoFormulario.CHECKBOX);
	}

	public void insertaSelector() {
		insertaCampo(TypeObjetoFormulario.SELECTOR);
	}

	private void insertaCampo(final TypeObjetoFormulario tipoCampo) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		final Long idPagina = pagina.getCodigo();
		LineaComponentesFormulario linea = null;
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"),
					true);
		} else {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				linea = (LineaComponentesFormulario) objetoFormularioEdit;
			} else {
				final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
				linea = pagina.getLineaComponente(campo.getCodigo());
				ordenSeleccionado = campo.getOrden();
			}

			orden = UtilDisenyo.ordenInsercionComponente(linea, ordenSeleccionado, posicionamiento);

			if (orden != null) {
				final ObjetoFormulario componente = formIntService.addComponenteFormulario(tipoCampo, idPagina,
						linea.getCodigo(), orden, posicionamiento);

				// actualizamos modelo
				pagina.getLineas().get(linea.getOrden() - 1).getComponentes().add(orden - 1,
						(ComponenteFormulario) componente);

				// actualizamos orden
				for (int i = orden + 1; i <= linea.getComponentes().size(); i++) {
					linea.getComponentes().get(i - 1).setOrden(i);
				}

				limpiaSeleccion();
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("warning.componente.sinespacio"), true);
			}

		}
	}

	public void insertaSeccion() {
		insertaLineaComponenteBloque(TypeObjetoFormulario.SECCION);
	}

	public void insertaEtiqueta() {
		insertaLineaComponenteBloque(TypeObjetoFormulario.ETIQUETA);
	}

	public void insertaLinea() {
		insertaLineaComponenteBloque(TypeObjetoFormulario.LINEA);
	}

	private void insertaLineaComponenteBloque(final TypeObjetoFormulario tipoCampo) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		LineaComponentesFormulario lineaSeleccionada = null;
		Integer ordenLinea = null;
		Long idLineaSeleccionada = null;

		if (objetoFormularioEdit == null && !pagina.getLineas().isEmpty()) {
			// mostrar aviso, hay que seleccionar una linea o componente
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"),
					true);
		} else {
			// hay algo seleccionado
			if (objetoFormularioEdit != null) {

				if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
					lineaSeleccionada = (LineaComponentesFormulario) objetoFormularioEdit;
				} else {
					lineaSeleccionada = pagina.getLineaComponente(objetoFormularioEdit.getCodigo());
				}

				idLineaSeleccionada = lineaSeleccionada.getCodigo();

			}

			ordenLinea = UtilDisenyo.ordenInsercionLinea(pagina, lineaSeleccionada, posicionamiento);

			final ObjetoFormulario componente = formIntService.addComponenteFormulario(tipoCampo, pagina.getCodigo(),
					idLineaSeleccionada, ordenLinea, posicionamiento);

			// actualizamos modelo
			pagina.getLineas().add(ordenLinea - 1, (LineaComponentesFormulario) componente);

			// actualizamos orden
			for (int i = ordenLinea + 1; i <= pagina.getLineas().size(); i++) {
				pagina.getLineas().get(i - 1).setOrden(i);
			}

			limpiaSeleccion();
		}

	}

	public void validarEliminarObjetoFormulario() {
		if (objetoFormularioEdit != null) {
			final RequestContext contextReq = RequestContext.getCurrentInstance();
			contextReq.execute("PF('confirmarEliminar').jq.click();");
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"));
		}
	}

	public void eliminarObjetoFormulario() {
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			formIntService.removeLineaFormulario(objetoFormularioEdit.getCodigo());

			// actualizamos modelo
			final List<LineaComponentesFormulario> lineasPagina = formulario.getPaginas().get(paginaActual - 1)
					.getLineas();

			final LineaComponentesFormulario lineaSeleccionada = (LineaComponentesFormulario) objetoFormularioEdit;
			lineasPagina.remove(lineaSeleccionada.getOrden() - 1);

			for (int i = lineaSeleccionada.getOrden(); i <= lineasPagina.size(); i++) {
				lineasPagina.get(i - 1).setOrden(i);
			}

		} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
			formIntService.removeComponenteFormulario(objetoFormularioEdit.getCodigo());

			// actualizamos modelo
			final ComponenteFormulario componenteSeleccionado = (ComponenteFormulario) objetoFormularioEdit;

			final LineaComponentesFormulario linea = formulario.getPaginas().get(paginaActual - 1)
					.getLineaComponente(componenteSeleccionado.getCodigo());
			linea.getComponentes().remove(componenteSeleccionado.getOrden() - 1);
			for (int i = componenteSeleccionado.getOrden(); i <= linea.getComponentes().size(); i++) {
				linea.getComponentes().get(i - 1).setOrden(i);
			}

			if (TypeObjetoFormulario.SECCION.equals(componenteSeleccionado.getTipo())
					|| TypeObjetoFormulario.ETIQUETA.equals(componenteSeleccionado.getTipo())) {
				objetoFormularioEdit = linea;
				eliminarObjetoFormulario();
			}
		}

		limpiaSeleccion();
	}

	public void cambiarCampoTextoMultilinea() {
		final ComponenteFormularioCampoTexto campo = (ComponenteFormularioCampoTexto) objetoFormularioEdit;
		if (campo.isNormalMultilinea()) {
			final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
			final LineaComponentesFormulario linea = pagina.getLineaComponente(objetoFormularioEdit.getCodigo());
			final ComponenteFormularioCampoTexto campoNuevo = new ComponenteFormularioCampoTexto();
			try {
				BeanUtils.copyProperties(campoNuevo, campo);
			} catch (IllegalAccessException | InvocationTargetException e) {
			}
			campoNuevo.setNumColumnas(ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA);

			if (linea.cabenComponentes(campoNuevo)) {
				campo.setNumColumnas(ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA);
				campo.setNormalNumeroLineas(1);
			} else {
				campo.setNormalMultilinea(false);
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("warning.componente.sinespacio"));
			}

		} else {
			campo.setNormalNumeroLineas(null);
		}
	}

	private void generaNumColumnas() {
		if (objetoFormularioEdit == null) {
			setNcolumnas(null);
		} else {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				setNcolumnas(null);
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {

				final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
				int inicio = 1;
				int fin = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA
						- pagina.getLineaComponente(objetoFormularioEdit.getCodigo()).columnasComponentes()
						+ ((ComponenteFormulario) objetoFormularioEdit).getNumColumnas();

				if (objetoFormularioEdit instanceof ComponenteFormularioSeccion) {
					inicio = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
					fin = ConstantesDisenyo.NUM_MAX_COMPONENTES_LINEA;
				} else if (objetoFormularioEdit instanceof ComponenteFormularioCampoTexto) {
					if (((ComponenteFormularioCampoTexto) objetoFormularioEdit).isNormalMultilinea()) {
						inicio = ConstantesDisenyo.TAM_MIN_COMPONENTE_MULTILINEA;
					} else {
						inicio = 1;
					}

				}

				setNcolumnas(generaListaNumColumnas(inicio, fin));
			}

		}
	}

	private List<Integer> generaListaNumColumnas(final int min, final int max) {
		setNcolumnas(new ArrayList<>());
		for (int i = min; i <= max; i++) {
			getNcolumnas().add(i);
		}
		return getNcolumnas();
	}

	private void limpiaSeleccion() {
		panelPropiedadesUrl = "/secure/app/dialogDisenyoFormularioVacio.xhtml";
		objetoFormularioEdit = null;
	}

	public void moverObjetoIzq() {
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			moverLinea(ConstantesDisenyo.POSICIONAMIENTO_PREVIO);
		} else {
			moverComponente(ConstantesDisenyo.POSICIONAMIENTO_PREVIO);
		}
	}

	public void moverObjetoDer() {
		if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
			moverLinea(ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR);
		} else {
			moverComponente(ConstantesDisenyo.POSICIONAMIENTO_POSTERIOR);
		}
	}

	private void moverLinea(final String pPosicion) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"),
					true);
		} else {
			final LineaComponentesFormulario linea = (LineaComponentesFormulario) objetoFormularioEdit;
			ordenSeleccionado = linea.getOrden();

			orden = UtilDisenyo.ordenMoverLinea(pagina, ordenSeleccionado, pPosicion);

			if (orden != null) {
				final LineaComponentesFormulario lineaSeleccionado = pagina.getLineas().get(ordenSeleccionado - 1);
				final LineaComponentesFormulario lineaCambiable = pagina.getLineas().get(orden - 1);

				formIntService.updateOrdenLineaFormulario(lineaSeleccionado.getCodigo(), orden);
				formIntService.updateOrdenLineaFormulario(lineaCambiable.getCodigo(), ordenSeleccionado);

				// actualizamos modelo
				pagina.getLineas().remove(lineaSeleccionado);
				pagina.getLineas().add(orden - 1, lineaSeleccionado);
				lineaSeleccionado.setOrden(orden);
				lineaCambiable.setOrden(ordenSeleccionado);
				linea.setOrden(orden);
			}
		}
	}

	private void moverComponente(final String pPosicion) {
		final PaginaFormulario pagina = formulario.getPaginas().get(paginaActual - 1);
		LineaComponentesFormulario linea = null;
		Integer ordenSeleccionado = null;
		Integer orden = null;

		if (objetoFormularioEdit == null) {
			// mostrar aviso, hay que seleccionar una linea o componente
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.componente.seleccionado"),
					true);
		} else {
			final ComponenteFormulario campo = (ComponenteFormulario) objetoFormularioEdit;
			linea = pagina.getLineaComponente(campo.getCodigo());
			ordenSeleccionado = campo.getOrden();

			orden = UtilDisenyo.ordenMoverComponente(linea, ordenSeleccionado, pPosicion);

			if (orden != null) {
				final ComponenteFormulario componenteSeleccionado = linea.getComponentes().get(ordenSeleccionado - 1);
				final ComponenteFormulario componenteCambiable = linea.getComponentes().get(orden - 1);

				formIntService.updateOrdenComponenteFormulario(componenteSeleccionado.getCodigo(), orden);
				formIntService.updateOrdenComponenteFormulario(componenteCambiable.getCodigo(), ordenSeleccionado);

				// actualizamos modelo
				linea.getComponentes().remove(componenteSeleccionado);
				linea.getComponentes().add(orden - 1, componenteSeleccionado);
				componenteSeleccionado.setOrden(orden);
				componenteCambiable.setOrden(ordenSeleccionado);
				campo.setOrden(orden);

			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("warning.componente.sinespacio"), true);
			}

		}
	}

	public void abrirListaValoresFijaCIN() {
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			// Muestra dialogo
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			Map<String, Object> mochilaDatos = null;

			// limpiamos mochila
			UtilJSF.getSessionBean().limpiaMochilaDatos();

			// metemos datos en la mochila
			mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO, idiomas);
			if (!campo.getListaValorListaFija().isEmpty()) {
				mochilaDatos.put(Constantes.CLAVE_MOCHILA_LVFCIN, campo.getListaValorListaFija().stream()
						.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));
			}

			UtilJSF.openDialog(DialogListaValoresFijaCIN.class, TypeModoAcceso.valueOf(modoAcceso), null, true, 800,
					350);
		}
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	@SuppressWarnings("unchecked")
	public void returnDialogoListaValoresFijaCIN(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
				// Muestra dialogo
				final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

				if (respuesta.getResult() == null) {
					campo.getListaValorListaFija().clear();
				} else {
					campo.setListaValorListaFija((List<ValorListaFija>) respuesta.getResult());
				}
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	public void abrirBusquedaDominio() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.TRAMITE.toString(), String.valueOf(tramiteVersion.getIdTramite()));
		UtilJSF.openDialog(DialogBusquedaDominio.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 1200, 420);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	@SuppressWarnings("unchecked")
	public void returnDialogoBusquedaDominio(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			campo.setCodDominio(((Dominio) respuesta.getResult()).getCodigo());

			campo.setCampoDominioCodigo(null);
			campo.setCampoDominioDescripcion(null);
			if (!campo.getListaParametrosDominio().isEmpty()) {
				campo.getListaParametrosDominio().clear();
			}

			if (!dominioService.tieneTramiteVersion(campo.getCodDominio(), tramiteVersion.getCodigo())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.alta.dominio.empleado"));
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	public void consultaDominio() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

		final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(campo.getCodDominio()));
		params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
		if (TypeAmbito.AREA.equals(dominio.getAmbito())) {
			final Area area = tramiteService.getAreaTramite(tramiteVersion.getIdTramite());
			params.put(TypeParametroVentana.AREA.toString(), area.getCodigo().toString());
		}
		UtilJSF.openDialog(DialogDominio.class, TypeModoAcceso.CONSULTA, params, true, 770, 680);
	}

	public void pingDominio() {
		final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
		final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(campo.getCodDominio()));
		params.put(TypeParametroVentana.AMBITO.toString(), dominio.getAmbito().toString());
		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 770, 600);
	}

	private String getIdComponente() {
		String idComponente = null;
		if (objetoFormularioEdit != null) {
			if (objetoFormularioEdit instanceof LineaComponentesFormulario) {
				idComponente = "L" + objetoFormularioEdit.getCodigo();
			} else if (objetoFormularioEdit instanceof ComponenteFormulario) {
				idComponente = ((ComponenteFormulario) objetoFormularioEdit).getIdComponente();
			}
		}
		return idComponente;
	}

	public void abrirParametrosDominioCIN() {
		if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
			// Muestra dialogo
			final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;
			Map<String, Object> mochilaDatos = null;

			// limpiamos mochila
			UtilJSF.getSessionBean().limpiaMochilaDatos();

			final Dominio dominio = dominioService.loadDominio(campo.getCodDominio());

			mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_PARAMDOM, dominio.getParametros().stream()
					.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			// metemos datos en la mochila
			if (!campo.getListaParametrosDominio().isEmpty()) {
				mochilaDatos.put(Constantes.CLAVE_MOCHILA_PRDCIN, campo.getListaParametrosDominio().stream()
						.map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));
			}

			UtilJSF.openDialog(DialogParametrosDominioCIN.class, TypeModoAcceso.valueOf(modoAcceso), null, true, 800,
					350);
		}
	}

	@SuppressWarnings("unchecked")
	public void returnDialogoParametrosDominioCIN(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
				// Muestra dialogo
				final ComponenteFormularioCampoSelector campo = (ComponenteFormularioCampoSelector) objetoFormularioEdit;

				if (respuesta.getResult() == null) {
					campo.getListaParametrosDominio().clear();
				} else {
					campo.setListaParametrosDominio((List<ParametroDominio>) respuesta.getResult());
				}
			}
		} else {
			UtilJSF.doValidationFailed();
		}

	}

	/**
	 * Editar script
	 */
	public void editarDialogScript(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));
		maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormulario);
		maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.COMPONENTE.toString(), this.objetoFormularioEdit.getCodigo().toString());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptAutorrelleno(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptAutorrellenable((Script) respuesta.getResult());
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptSoloLectura(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptSoloLectura((Script) respuesta.getResult());
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoScriptValidacion(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampo) {
					((ComponenteFormularioCampo) objetoFormularioEdit)
							.setScriptValidacion((Script) respuesta.getResult());
				}
				break;
			default:
				break;
			}
		}
	}

	/*
	 * Retorno dialogo del script de valores posibles.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptValores(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				if (objetoFormularioEdit instanceof ComponenteFormularioCampoSelector) {
					((ComponenteFormularioCampoSelector) objetoFormularioEdit)
							.setScriptValoresPosibles((Script) respuesta.getResult());
				}
				break;
			default:
				break;
			}
		}
	}

	public void abrirDialogoEstructura() {
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(formulario.getCodigo()));

		UtilJSF.openDialog(DialogEstructuraFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 450,
				460);
	}

	// -- Getters / Setters

	/**
	 * Obtiene el valor de opcionUrl.
	 *
	 * @return el valor de opcionUrl
	 */
	public String getPanelPropiedadesUrl() {
		return panelPropiedadesUrl;
	}

	/**
	 * Set opcion url.
	 *
	 * @param opcion
	 */
	public void setPanelPropiedadesUrl(final String opcion) {
		this.panelPropiedadesUrl = opcion;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getUrlIframe() {
		final String idComponente = getIdComponente();

		return urlIframe + "&id=" + id + "&page=" + paginaActual + "&lang=" + idioma
				+ (idComponente != null ? "&idComponente=" + idComponente : "");
	}

	public void setUrlIframe(final String urlIframe) {
		this.urlIframe = urlIframe;
	}

	public DisenyoFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(final DisenyoFormulario formulario) {
		this.formulario = formulario;
	}

	public ObjetoFormulario getObjetoFormularioEdit() {
		return objetoFormularioEdit;
	}

	public void setComponentEdit(final ObjetoFormulario objetoFormularioEdit) {
		this.objetoFormularioEdit = objetoFormularioEdit;
	}

	/**
	 * @return the visiblePropiedades
	 */
	public boolean isVisiblePropiedades() {
		return visiblePropiedades;
	}

	/**
	 * @param visiblePropiedades
	 *            the visiblePropiedades to set
	 */
	public void setVisiblePropiedades(final boolean visiblePropiedades) {
		this.visiblePropiedades = visiblePropiedades;
	}

	/**
	 * @return the visibleScripts
	 */
	public boolean isVisibleScripts() {
		return visibleScripts;
	}

	/**
	 * @param visibleScripts
	 *            the visibleScripts to set
	 */
	public void setVisibleScripts(final boolean visibleScripts) {
		this.visibleScripts = visibleScripts;
	}

	/**
	 * @return the visibleEstilos
	 */
	public boolean isVisibleEstilos() {
		return visibleEstilos;
	}

	/**
	 * @param visibleEstilos
	 *            the visibleEstilos to set
	 */
	public void setVisibleEstilos(final boolean visibleEstilos) {
		this.visibleEstilos = visibleEstilos;
	}

	public String getDetalleComponenteUrl() {
		return detalleComponenteUrl;
	}

	public void setDetalleComponenteUrl(final String detalleComponenteUrl) {
		this.detalleComponenteUrl = detalleComponenteUrl;
	}

	public Literal getTraduccionesEdit() {
		return traduccionesEdit;
	}

	public void setTraduccionesEdit(final Literal traduccionesEdit) {
		this.traduccionesEdit = traduccionesEdit;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(final int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public String getPosicionamiento() {
		return posicionamiento;
	}

	public void setPosicionamiento(final String posicionamiento) {
		this.posicionamiento = posicionamiento;
	}

	public List<Integer> getNcolumnas() {
		return ncolumnas;
	}

	public void setNcolumnas(final List<Integer> ncolumnas) {
		this.ncolumnas = ncolumnas;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramite) {
		this.idTramiteVersion = idTramite;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the idFormulario
	 */
	public String getIdFormulario() {
		return idFormulario;
	}

	/**
	 * @param idFormulario
	 *            the idFormulario to set
	 */
	public void setIdFormulario(final String idFormulario) {
		this.idFormulario = idFormulario;
	}
}

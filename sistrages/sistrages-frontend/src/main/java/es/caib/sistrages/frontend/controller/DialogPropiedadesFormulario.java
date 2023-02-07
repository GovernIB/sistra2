package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.ModelApi;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.types.TypeScriptFormulario;
import es.caib.sistrages.core.api.service.FormateadorFormularioService;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.util.UtilJSON;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogPropiedadesFormulario extends DialogControllerBase {

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** id */
	private String id;

	/** Id. formulario tramite. **/
	private String idFormularioTramite;

	/** Id. tramite version. **/
	private String idTramiteVersion;

	/** Indica si hay cambios **/
	private boolean cambios = false;

	/** Datos iniciales **/
	private DisenyoFormulario dataI;
	/**
	 * Datos elemento.
	 */
	private DisenyoFormulario data;

	private PaginaFormulario paginaSeleccionada;

	private String literal;

	private PlantillaFormulario plantillaSeleccionada;

	private List<String> idiomas;

	private boolean editar;

	@Inject
	FormularioInternoService formIntService;

	@Inject
	private FormateadorFormularioService fmtService;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicialización.
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		editar = true;
		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			idiomas = (List<String>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
		}

		if (id == null) {
			data = new DisenyoFormulario();
			dataI = new DisenyoFormulario();

		} else {
			dataI = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			literal = data.getTextoCabecera() != null
					? data.getTextoCabecera().getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas)
					: null;
		}

		if (data.getPaginas().size() < 2) {
			this.data.setPermitirGuardarSinFinalizar(false);
		}
	}

	/**
	 * Guardar.
	 */
	public void guardar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
		case EDICION:
			if (!contienePlantillaPorDefecto()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.planilla.porDefecto"));
				return;
			}
			if (!contienePaginaFinal()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.pagina.sinFinal"));
				return;
			}

			// comprobamos si se han modificado propiedades
			if (dataI.isMostrarCabecera() != data.isMostrarCabecera()
					|| dataI.isPermitirAccionesPersonalizadas() != data.isPermitirAccionesPersonalizadas()
					|| dataI.isPermitirGuardarSinFinalizar() != data.isPermitirGuardarSinFinalizar()) {
				cambios = true;
			}

			if (cambios) {
				tramiteService.actualizarFechaTramiteVersion(Long.parseLong(idTramiteVersion),
						UtilJSF.getSessionBean().getUserName(), "Modificación formulario");
			}

			// comprobamos si se han modificado paginas

			formIntService.updateFormularioInterno(data);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}

		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(new Object[] { data, cambios });
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
	}

	private boolean contienePlantillaPorDefecto() {
		boolean contiene;
		if (data.getPlantillas() == null || data.getPlantillas().isEmpty()) {
			contiene = true;
		} else {
			contiene = false;
			for (final PlantillaFormulario plantilla : data.getPlantillas()) {
				if (plantilla != null && plantilla.isPorDefecto()) {
					contiene = true;
					break;
				}
			}
		}
		return contiene;
	}

	private boolean contienePaginaFinal() {
		boolean contiene;
		if (data.getPaginas() == null || data.getPaginas().isEmpty()) {
			contiene = true;
		} else {
			contiene = false;
			for (PaginaFormulario pagina : data.getPaginas()) {
				if (pagina != null && pagina.isPaginaFinal()) {
					contiene = true;
					break;
				}
			}
		}
		return contiene;
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO);
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("propiedadesFormularioDialog");
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			Literal literalesI = dataI.getTextoCabecera();
			final Literal literales = (Literal) respuesta.getResult();
			if (this.isCambioLiterales(literalesI, literales)) {
				cambios = true;
			}
			data.setTextoCabecera(literales);
			setLiteral(literales.getTraduccion(UtilJSF.getSessionBean().getLang(), idiomas));
		}
	}

	/**
	 * Editar texto de cabecera.
	 *
	 *
	 */
	public void editarTextoCabecera() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.valueOf(modoAcceso), data.getTextoCabecera(), getIdiomas(),
				getIdiomas());
	}

	/**
	 * Editar script
	 */
	public void editarDialogScriptPlantilla(final String tipoScript, final Script script) {
		final Map<String, String> maps = new HashMap<>();
		maps.put(TypeParametroVentana.TIPO_SCRIPT_FORMULARIO.toString(),
				UtilJSON.toJSON(TypeScriptFormulario.fromString(tipoScript)));
		maps.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		maps.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormularioTramite);
		maps.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);
		maps.put(TypeParametroVentana.PARAMETRO_DISENYO.toString(),
				TypeParametroVentana.PARAMETRO_DISENYO_TRAMITE.toString());

		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(script));

		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.valueOf(modoAcceso), maps, true, 700);

	}

	/**
	 * Retorno dialogo del script de personalizacion.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoScriptPlantilla(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				data.setScriptPlantilla((Script) respuesta.getResult());
				if (dataI != null && data != null) {
					if (this.isCambioScripts(data.getScriptPlantilla(), dataI.getScriptPlantilla())) {
						cambios = true;
					}
				} else if (dataI == null) {
					if (data != null) {
						cambios = true;
					}
				} else {
					if (dataI != null) {
						cambios = true;
					}
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Baja la pagina de posición.
	 */
	public void bajarPagina() {
		editar = false;
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		// final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);
		final int posicion = this.paginaSeleccionada.getOrden() - 1;
		if (posicion >= this.data.getPaginas().size() - 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverabajo"));
			return;
		}

		final PaginaFormulario pagina = this.data.getPaginas().remove(posicion);
		this.data.getPaginas().add(posicion + 1, pagina);
		this.data.getPaginas().get(posicion + 1).setOrden(this.data.getPaginas().get(posicion + 1).getOrden() + 1);
		this.data.getPaginas().get(posicion).setOrden(this.data.getPaginas().get(posicion).getOrden() - 1);
		cambios = true;
	}

	/**
	 * Sube la pagina de posición.
	 */
	public void subirPagina() {
		editar = false;
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		// final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);
		final int posicion = this.paginaSeleccionada.getOrden() - 1;
		if (posicion <= 0) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.moverarriba"));
			return;
		}

		final PaginaFormulario pagina = this.data.getPaginas().remove(posicion);

		this.data.getPaginas().add(posicion - 1, pagina);
		this.data.getPaginas().get(posicion - 1).setOrden(this.data.getPaginas().get(posicion - 1).getOrden() - 1);
		this.data.getPaginas().get(posicion).setOrden(this.data.getPaginas().get(posicion).getOrden() + 1);
		cambios = true;
	}

	private void intercambioOrden(List<PaginaFormulario> paginas, int posicion, boolean subir) {
		if (subir) {

		}

	}

	public void nuevaPagina() {
		editar = false;
		final PaginaFormulario pagina = new PaginaFormulario();
		pagina.setOrden(data.getPaginas().size() + 1);
		pagina.setIdentificador("P" + pagina.getOrden());
		this.data.getPaginas().add(pagina);
		asignaPaginaFinal(data.getPaginas());
		cambios = true;

		if (data.getPaginas().size() > 1) {
			this.data.setPermitirGuardarSinFinalizar(true);
		}
	}

	/** Consultar pagina. **/
	public void consultarPagina() {

		abrirPagina(TypeModoAcceso.CONSULTA);
	}

	/** Editar pagina. **/
	public void editarPagina() {
		abrirPagina(TypeModoAcceso.valueOf(modoAcceso));
	}

	/** Abrir dialog pagina formulario. **/
	public void abrirPagina(final TypeModoAcceso modo) {

		if (!verificarFilaSeleccionada(paginaSeleccionada)) {
			return;
		}
		if (!editar) {
			if (!contienePlantillaPorDefecto()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.planilla.porDefecto"));
				return;
			}
			if (!contienePaginaFinal()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.pagina.noFinal"));
				return;
			}
			formIntService.updateFormularioInterno(data);
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			editar = true;
			if (this.paginaSeleccionada.getCodigo() == null) {
				for (PaginaFormulario pag : data.getPaginas()) {
					if (pag.getOrden() == paginaSeleccionada.getOrden()) {
						paginaSeleccionada.setCodigo(pag.getCodigo());
					}
				}
			}
		}
		final Map<String, String> params = new HashMap<>();
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		UtilJSF.getSessionBean().getMochilaDatos().put(Constantes.CLAVE_MOCHILA_FORMULARIO,
				UtilJSON.toJSON(this.paginaSeleccionada));

		params.put(TypeParametroVentana.FORMULARIO_ACTUAL.toString(), this.idFormularioTramite);
		params.put(TypeParametroVentana.FORM_INTERNO_ACTUAL.toString(), this.id);
		params.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);

		UtilJSF.openDialog(DialogPaginaFormulario.class, modo, params, true, 430, 190);
	}

	/**
	 * Return dialogo pagina.
	 *
	 * @param event the event
	 */
	public void returnDialogoPagina(final SelectEvent event) {
		PaginaFormulario pagina = null;
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
			case EDICION:
				Object[] obj = (Object[]) respuesta.getResult();
				pagina = (PaginaFormulario) obj[0];

				// Muestra dialogo
				// final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);

				final int posicion = this.paginaSeleccionada.getOrden() - 1;
				this.data.getPaginas().get(posicion).setPaginaFinal(pagina.isPaginaFinal());
				this.data.getPaginas().get(posicion).setScriptValidacion(pagina.getScriptValidacion());
				paginaSeleccionada.setIdentificador(pagina.getIdentificador());
				paginaSeleccionada.setPaginaFinal(pagina.isPaginaFinal());
				paginaSeleccionada.setScriptNavegacion(pagina.getScriptNavegacion());
				paginaSeleccionada.setScriptValidacion(pagina.getScriptValidacion());
				if ((boolean) obj[1]) {
					cambios = (boolean) obj[1];
				}
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/**
	 * elimina la pagina de posición.
	 */
	public void eliminarPagina() {
		editar = false;
		if (!verificarFilaSeleccionada(paginaSeleccionada))
			return;

		// siempre tiene que haber una pagina
		if (data.getPaginas().size() == 1) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("warning.numeroPaginasMinimo"));
			return;
		}

		final int posicion = this.data.getPaginas().indexOf(this.paginaSeleccionada);

		this.data.getPaginas().remove(posicion);
		if (!data.getPaginas().isEmpty()) {
			asignaPaginaFinal(data.getPaginas());
			cambiarOrdenPaginas(data.getPaginas());
		}

		// comprobamos si se borrado menor a 2
		if (data.getPaginas().size() < 2) {
			this.data.setPermitirGuardarSinFinalizar(false);
		}

		paginaSeleccionada = null;
		cambios = true;
	}

	public void nuevaPlantilla() {
		UtilJSF.openDialog(DialogPlantillaFormulario.class, TypeModoAcceso.ALTA, null, true, 430, 230);
	}

	/**
	 * Return dialogo plantilla.
	 *
	 * @param event the event
	 */
	public void returnDialogoPlantilla(final SelectEvent event) {
		PlantillaFormulario plantilla = null;
		final DialogResult respuesta = (DialogResult) event.getObject();

		if (!respuesta.isCanceled()) {
			switch (respuesta.getModoAcceso()) {
			case ALTA:
				plantilla = (PlantillaFormulario) respuesta.getResult();

				if (formateadorDuplicado(plantilla)) {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (plantilla.isPorDefecto()) {
					plantillaLimpiarPorDefecto();
				}

				this.data.getPlantillas().add(plantilla);
				cambios = true;
				break;
			case EDICION:
				plantilla = (PlantillaFormulario) respuesta.getResult();

				if (formateadorDuplicado(plantilla)) {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.valor.duplicated"));
					return;
				}

				if (!plantillaSeleccionada.isPorDefecto() && plantilla.isPorDefecto()) {
					plantillaLimpiarPorDefecto();
				}

				// Muestra dialogo
				final int posicion = this.data.getPlantillas().indexOf(this.plantillaSeleccionada);
				this.data.getPlantillas().remove(posicion);
				this.data.getPlantillas().add(posicion, plantilla);
				// comprobamos si se ha modificado
				if (!plantillaSeleccionada.getIdentificador().equals(plantilla.getIdentificador())) {
					cambios = true;
				}
				if (plantillaSeleccionada.getIdFormateadorFormulario() != plantilla.getIdFormateadorFormulario()) {
					cambios = true;
				}
				if (!plantillaSeleccionada.getDescripcion().equals(plantilla.getDescripcion())) {
					cambios = true;
				}
				if (plantillaSeleccionada.isPorDefecto() != plantilla.isPorDefecto()) {
					cambios = true;
				}
				this.plantillaSeleccionada = plantilla;
				break;
			case CONSULTA:
				// No hay que hacer nada
				break;
			}
		}
	}

	/** Consultar plantilla. **/
	public void consultarPlantilla() {
		abrirPlantilla(TypeModoAcceso.CONSULTA);
	}

	/** Editar plantilla. **/
	public void editarPlantilla() {
		abrirPlantilla(TypeModoAcceso.valueOf(modoAcceso));
	}

	/** Abrir dialog de plantilla formulario. **/
	private void abrirPlantilla(final TypeModoAcceso modo) {

		if (!verificarFilaSeleccionada(plantillaSeleccionada)) {
			return;
		}

		// Abrimos seleccion ficheros
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.DATO.toString(), UtilJSON.toJSON(this.plantillaSeleccionada));
		UtilJSF.openDialog(DialogPlantillaFormulario.class, modo, params, true, 430, 230);
	}

	/**
	 *
	 * elimina la plantilla
	 */
	public void eliminarPlantilla() {
		if (!verificarFilaSeleccionada(plantillaSeleccionada))
			return;

		final int posicion = this.data.getPlantillas().indexOf(this.plantillaSeleccionada);

		this.data.getPlantillas().remove(posicion);
		plantillaSeleccionada = null;
		cambios = true;
	}

	private void asignaPaginaFinal(List<PaginaFormulario> paginas) {
		boolean esFinal = false;
		paginas.sort((o1, o2) -> o1.compareTo(o2));
		for (PaginaFormulario pag : paginas) {
			if (pag.isPaginaFinal()) {
				esFinal = true;
			}
		}
		if (!esFinal) {
			paginas.sort((o1, o2) -> o1.compareTo(o2));

			// Collections.sort(paginas, Collections.reverseOrder());
			paginas.get(paginas.size() - 1).setPaginaFinal(true);
			addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.pagina.final"));
		}

	}

	private void cambiarOrdenPaginas(List<PaginaFormulario> paginas) {
		paginas.sort((o1, o2) -> o1.compareTo(o2));
		int i = 1;
		for (PaginaFormulario pag : paginas) {
			pag.setOrden(i);
			i++;
		}
		data.setPaginas(paginas);

	}

	/**
	 * Limpia la propiedad de por defecto.
	 */
	private void plantillaLimpiarPorDefecto() {

		for (final PlantillaFormulario plantilla : data.getPlantillas()) {
			plantilla.setPorDefecto(false);
		}

	}

	private boolean formateadorDuplicado(final PlantillaFormulario pPlantillaEditada) {
		boolean duplicado = false;

		for (final PlantillaFormulario plantilla : data.getPlantillas()) {
			if (plantilla.getIdentificador().equals(pPlantillaEditada.getIdentificador())
					&& !plantilla.getCodigo().equals(pPlantillaEditada.getCodigo())) {
				duplicado = true;
				break;
			}
		}

		return duplicado;
	}

	/**
	 * Deshabilitar script plantilla.
	 *
	 * @return true, if successful
	 */
	public boolean getDeshabilitarScriptPlantilla() {
		return data.getPlantillas() != null ? this.data.getPlantillas().size() <= 1 : true;
	}

	public String getDescripcionFormateador(final Long pIdFmt) {
		return fmtService.getFormateadorFormulario(pIdFmt).getIdentificador();
	}

	public void editarFicheroPlantilla() {

		if (!verificarFilaSeleccionada(plantillaSeleccionada)) {
			return;
		}

		// En caso de la plantilla este sin guardar, guardamos (se ha pedido
		// confirmacion)
		if (plantillaSeleccionada.getCodigo() <= 0) {
			formIntService.updateFormularioInterno(data);
			// Refrescamos datos
			data = formIntService.getFormularioInternoPaginas(Long.valueOf(id));
			// Seleccionamos plantilla
			for (final PlantillaFormulario p : data.getPlantillas()) {
				if (p.getIdentificador().equals(plantillaSeleccionada.getIdentificador())) {
					plantillaSeleccionada = p;
				}
			}
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(plantillaSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogPlantillaIdiomaFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 430,
				170);
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionada(final ModelApi filaseleccionada) {
		boolean filaSeleccionada = true;

		if (filaseleccionada == null) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Sin implementar.
	 */
	public void sinImplementar() {
		addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param pId el nuevo valor de id
	 */
	public void setId(final String pId) {
		this.id = pId;
	}

	/**
	 * Obtiene el valor de data.
	 *
	 * @return el valor de data
	 */
	public DisenyoFormulario getData() {
		return data;
	}

	/**
	 * Establece el valor de data.
	 *
	 * @param data el nuevo valor de data
	 */
	public void setData(final DisenyoFormulario data) {
		this.data = data;
	}

	/**
	 * Obtiene el valor de literal.
	 *
	 * @return el valor de literal
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Establece el valor de literal.
	 *
	 * @param literal el nuevo valor de literal
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	public PaginaFormulario getPaginaSeleccionada() {
		return paginaSeleccionada;
	}

	public void setPaginaSeleccionada(final PaginaFormulario paginaSeleccionada) {
		this.paginaSeleccionada = paginaSeleccionada;
	}

	public PlantillaFormulario getPlantillaSeleccionada() {
		return plantillaSeleccionada;
	}

	public void setPlantillaSeleccionada(final PlantillaFormulario plantillaSeleccionada) {
		this.plantillaSeleccionada = plantillaSeleccionada;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	public FormateadorFormularioService getFmtService() {
		return fmtService;
	}

	public void setFmtService(final FormateadorFormularioService fmtService) {
		this.fmtService = fmtService;
	}

	/**
	 * @return the idFormularioTramite
	 */
	public String getIdFormularioTramite() {
		return idFormularioTramite;
	}

	/**
	 * @param idFormularioTramite the idFormularioTramite to set
	 */
	public void setIdFormularioTramite(final String idFormularioTramite) {
		this.idFormularioTramite = idFormularioTramite;
	}

	/**
	 * @return the idTramiteVersion
	 */
	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion the idTramiteVersion to set
	 */
	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	public boolean isEditar() {
		return editar;
	}

	public boolean isMod() {
		if (dataI.getPaginas().size() == data.getPaginas().size()) {
			for (PaginaFormulario pag : data.getPaginas()) {
				getPaginaSeleccionada();
			}
		} else {
			cambios = true;
		}
		return cambios;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	/**
	 * @return the cambios
	 */
	public final boolean isCambios() {
		return cambios;
	}

	/**
	 * @param cambios the cambios to set
	 */
	public final void setCambios(boolean cambios) {
		this.cambios = cambios;
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}

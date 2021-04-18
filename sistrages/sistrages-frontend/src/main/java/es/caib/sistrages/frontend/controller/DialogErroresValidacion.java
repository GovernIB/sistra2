package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeErrorValidacion;
import es.caib.sistrages.core.api.service.DominioService;
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
public class DialogErroresValidacion extends DialogControllerBase {
	private List<ErrorValidacion> listaErrores;

	private ErrorValidacion filaSeleccionada;

	/** Idiomas json. **/
	private String idiomas;

	/** Id tramite version. **/
	private String idTramiteVersion;

	private String idTramite;

	/** Idiomas. **/
	private List<String> idiomasObligatorios;

	private List<Dominio> listaDominios;

	@Inject
	TramiteService tramiteService;

	@Inject
	DominioService dominioService;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

		if (!mochilaDatos.isEmpty()) {
			setListaErrores((List<ErrorValidacion>) mochilaDatos.get(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION));
		}

		if (getListaErrores() == null) {
			setListaErrores(new ArrayList<>());
		}

		UtilJSF.getSessionBean().limpiaMochilaDatos(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION);

		if (modo == TypeModoAcceso.EDICION) {
			final boolean hayErroresLiteral = listaErrores.stream()
					.anyMatch(e -> TypeErrorValidacion.LITERALES.equals(e.getTipo())
							|| TypeErrorValidacion.FORMATEADOR.equals(e.getTipo()));
			if (StringUtils.isNotEmpty(idiomas) && hayErroresLiteral) {
				idiomasObligatorios = UtilTraducciones.getIdiomas(idiomas);
			}

			final boolean hayErroresDominio = listaErrores.stream()
					.anyMatch(e -> TypeErrorValidacion.DOMINIOS.equals(e.getTipo()));
			if (StringUtils.isNotEmpty(idTramite) && hayErroresDominio) {
				listaDominios = dominioService.listDominio(Long.valueOf(idTramite), null);
			}
		}

	}

	public void corregir() {
		if (verificarFilaSeleccionada()) {
			if (TypeErrorValidacion.LITERALES.equals(filaSeleccionada.getTipo())) {
				corregirLiteral();
			} else if (TypeErrorValidacion.LITERALES_HTML.equals(filaSeleccionada.getTipo())) {
				corregirLiteralHTML();
			} else if (TypeErrorValidacion.SCRIPTS.equals(filaSeleccionada.getTipo())) {
				corregirScript();
			} else if (TypeErrorValidacion.DOMINIOS.equals(filaSeleccionada.getTipo())) {
				corregirDominio();
			} else if (TypeErrorValidacion.DATOS_REGISTRO.equals(filaSeleccionada.getTipo())) {
				corregirDatosRegistro();
			} else if (TypeErrorValidacion.FORMATEADOR.equals(filaSeleccionada.getTipo())) {
				corregirFormateador();
			}

		}
	}

	/**
	 * cerrar.
	 */
	public void cerrar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			if (respuesta.getResult() instanceof Literal) {
				tramiteService.updateLiteral((Literal) respuesta.getResult());
			} else if (respuesta.getResult() instanceof Script) {
				tramiteService.updateScript((Script) respuesta.getResult());
			}
		}
	}

	public void validar() {
		listaErrores = tramiteService.validarVersionTramite(Long.valueOf(idTramiteVersion),
				UtilJSF.getSessionBean().getLang());
	}

	private boolean verificarFilaSeleccionada() {
		boolean isFilaSeleccionada = true;
		if (this.filaSeleccionada == null) {
			isFilaSeleccionada = false;
		}
		return isFilaSeleccionada;
	}

	private void corregirLiteral() {
		Literal literal = null;
		if (filaSeleccionada.getItem() instanceof Literal) {
			literal = (Literal) filaSeleccionada.getItem();
		}
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, literal, getIdiomasObligatorios(),
				getIdiomasObligatorios());
	}

	private void corregirLiteralHTML() {
		Literal literal = null;
		if (filaSeleccionada.getItem() instanceof Literal) {
			literal = (Literal) filaSeleccionada.getItem();
		}
		UtilTraducciones.openDialogTraduccionHTML(TypeModoAcceso.EDICION, literal, getIdiomasObligatorios(),
				getIdiomasObligatorios(), false);
	}

	private void corregirScript() {
		Script script = null;
		if (filaSeleccionada.getItem() instanceof Script) {
			script = (Script) filaSeleccionada.getItem();

			cargarDialogScript(script, filaSeleccionada.getParams());

		}

	}

	private void cargarDialogScript(final Script pScript, final String pParams) {
		final Map<String, String> params = (Map<String, String>) UtilJSON.fromJSON(pParams, Map.class);

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_SCRIPT, UtilJSON.toJSON(pScript));
		UtilJSF.openDialog(DialogScript.class, TypeModoAcceso.EDICION, params, true, 700);

	}

	private void corregirDominio() {
		Dominio dominioError = null;
		Dominio dominioAux = null;
		if (filaSeleccionada.getItem() instanceof Dominio) {
			dominioError = (Dominio) filaSeleccionada.getItem();

			if (dominioError != null
					&& (StringUtils.isNotEmpty(dominioError.getIdentificador()) || dominioError.getCodigo() != null)) {

				// cargamos dominio
				if (StringUtils.isNotEmpty(dominioError.getIdentificador())) {
					dominioAux = dominioService.loadDominio(dominioError.getIdentificador());
				} else if (dominioError.getCodigo() != null) {
					dominioAux = dominioService.loadDominio(dominioError.getCodigo());
				}
				// esto es por un error con el final de la variable dominio
				final Dominio dominio = dominioAux;

				// existe en la app
				if (dominio != null) {
					// miramos si pertenece a nuestra global/entidad/area
					if (listaDominios.stream().anyMatch(d -> dominio.getCodigo().equals(d.getCodigo()))) {
						// asociar
						dominioService.addTramiteVersion(dominio.getCodigo(), Long.valueOf(idTramiteVersion));
						addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.alta.dominio.empleado"));

					} else {
						if (TypeAmbito.ENTIDAD.equals(dominio.getAmbito())) {
							addMessageContext(TypeNivelGravedad.ERROR,
									UtilJSF.getLiteral("error.dominio.pertence.entidad"));
						} else if (TypeAmbito.AREA.equals(dominio.getAmbito())) {
							addMessageContext(TypeNivelGravedad.ERROR,
									UtilJSF.getLiteral("error.dominio.pertence.area"));
						}
					}
				} else {
					addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.dominio.noexiste"));
				}
			}
		}

	}

	private void corregirDatosRegistro() {
		TramitePasoRegistrar paso = null;
		if (filaSeleccionada.getItem() instanceof TramitePasoRegistrar) {
			paso = (TramitePasoRegistrar) filaSeleccionada.getItem();

			final Map<String, String> map = new HashMap<>();
			map.put(TypeParametroVentana.ID.toString(), paso.getCodigo().toString());
			map.put(TypeParametroVentana.TRAMITEVERSION.toString(), idTramiteVersion);

			UtilJSF.openDialog(DialogDefinicionVersionRegistrarTramite.class, TypeModoAcceso.EDICION, map, true, 950,
					450);
		}
	}

	private void corregirFormateador() {
		if (StringUtils.isNotEmpty(filaSeleccionada.getParams())) {
			final Map<String, String> params = (Map<String, String>) UtilJSON.fromJSON(filaSeleccionada.getParams(),
					Map.class);

			Map<String, Object> mochilaDatos = null;
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();
			mochilaDatos.put(Constantes.CLAVE_MOCHILA_IDIOMASXDEFECTO, idiomasObligatorios);

			UtilJSF.openDialog(DialogPropiedadesFormulario.class, TypeModoAcceso.valueOf(modoAcceso), params, true, 950,
					490);
		}
	}

	public List<ErrorValidacion> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(final List<ErrorValidacion> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public String getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(final String idiomas) {
		this.idiomas = idiomas;
	}

	public List<String> getIdiomasObligatorios() {
		return idiomasObligatorios;
	}

	public void setIdiomasObligatorios(final List<String> idiomasObligatorios) {
		this.idiomasObligatorios = idiomasObligatorios;
	}

	public ErrorValidacion getFilaSeleccionada() {
		return filaSeleccionada;
	}

	public void setFilaSeleccionada(final ErrorValidacion filaSeleccionada) {
		this.filaSeleccionada = filaSeleccionada;
	}

	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public List<Dominio> getListaDominios() {
		return listaDominios;
	}

	public void setListaDominios(final List<Dominio> listaDominios) {
		this.listaDominios = listaDominios;
	}

}

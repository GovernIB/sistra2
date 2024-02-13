package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConsultaGeneralService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de consulta general.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewConsultaGeneral extends ViewControllerBase {

	/** Filtro. */
	private String filtro;

	/** Checks **/
	private boolean checkDominio = true;
	private boolean checkConf = true;
	private boolean checkGFE = true;
	private boolean checkAmbitoGlobal = true;
	private boolean checkAmbitoEntidad = true;
	private boolean checkAmbitoArea = true;

	private String height = "100%";

	/** Lista de datos. */
	private List<ConsultaGeneral> listaDatos;

	/** Dato seleccionado en la lista. */
	private ConsultaGeneral datoSeleccionado;

	/** FormateadorFormularioService. */
	@Inject
	private ConsultaGeneralService consultaService;

	/** SecurityService. */
	@Inject
	private SecurityService securityService;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 */
	public void init() {
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		filtrar();
	}

	public void consultarDato() {
		if (datoSeleccionado != null) {
			if (datoSeleccionado.getAmbito() == TypeAmbito.GLOBAL) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewConsultaGeneral.error.datoGlobal"));
			} else if (datoSeleccionado.getAmbito() == TypeAmbito.ENTIDAD
					&& UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
						UtilJSF.getLiteral("viewConsultaGeneral.error.datoEntidad"));
			} else {
				switch (datoSeleccionado.getTipo()) {
				case CONFIG_AUTENTICACION:
					consultarDatoConfAut();
					break;
				case DOMINIO:
					consultarDatoDominio();
					break;
				case GFE:
					consultarDatoGFE();
					break;
				}
			}
		}
	}

	/**
	 * Suponiendo que solo entrar un adm entidad o desar <br />
	 * Si es:
	 * <ul>
	 * <li>Adm. entidad: Se abre en modo edicion</li>
	 * <li>Desar: Si tiene permiso se abrirá en modo edición sino en modo
	 * consulta</lli>
	 * </ul>
	 */
	private void consultarDatoGFE() {
		TypeModoAcceso modoAccesoDlg;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(datoSeleccionado.getIdArea());

			if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
				if (permisos.contains(TypeRolePermisos.CONSULTA)) {
					modoAccesoDlg = TypeModoAcceso.CONSULTA;
				} else {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("viewConsultaGeneral.error.datoArea"));
					return;
				}
			} else {
				modoAccesoDlg = TypeModoAcceso.EDICION;
			}
		} else {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		if (this.datoSeleccionado.getIdArea() != null) {
			params.put(TypeParametroVentana.AREA.toString(), this.datoSeleccionado.getIdArea().toString());
		}
		UtilJSF.openDialog(DialogFormularioExterno.class, modoAccesoDlg, params, true, 490, 215);
	}

	/**
	 * Suponiendo que solo entrar un adm entidad o desar <br />
	 * Si es:
	 * <ul>
	 * <li>Adm. entidad: Se abre en modo edicion</li>
	 * <li>Desar: Si tiene permiso se abrirá en modo edición sino en modo
	 * consulta</lli>
	 * </ul>
	 */
	private void consultarDatoDominio() {

		TypeModoAcceso modoAccesoDlg;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(datoSeleccionado.getIdArea());

			if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
				if (permisos.contains(TypeRolePermisos.CONSULTA)) {
					modoAccesoDlg = TypeModoAcceso.CONSULTA;
				} else {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("viewConsultaGeneral.error.datoArea"));
					return;
				}
			} else {
				modoAccesoDlg = TypeModoAcceso.EDICION;
			}
		} else {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));

		params.put(TypeParametroVentana.AMBITO.toString(), this.datoSeleccionado.getAmbito().toString());

		if (this.datoSeleccionado.getAmbito() == TypeAmbito.AREA) {
			params.put("AREA", this.datoSeleccionado.getIdArea().toString());
		}
		if (this.datoSeleccionado.getAmbito() == TypeAmbito.ENTIDAD) {
			params.put("ENTIDAD", UtilJSF.getIdEntidad().toString());
		}

		UtilJSF.openDialog(DialogDominio.class, modoAccesoDlg, params, true, 770, 670);
	}

	/**
	 * Suponiendo que solo entrar un adm entidad o desar <br />
	 * Si es:
	 * <ul>
	 * <li>Adm. entidad: Se abre en modo edicion</li>
	 * <li>Desar. Solo se abre si tiene permisos (por tema de contraseñas)</lli>
	 * </ul>
	 */
	private void consultarDatoConfAut() {
		TypeModoAcceso modoAccesoDlg;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(datoSeleccionado.getIdArea());

			if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
				if (permisos.contains(TypeRolePermisos.CONSULTA)) {
					modoAccesoDlg = TypeModoAcceso.CONSULTA;
				} else {
					UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
							UtilJSF.getLiteral("viewConsultaGeneral.error.datoArea"));
					return;
				}
			} else {
				modoAccesoDlg = TypeModoAcceso.EDICION;
			}
		} else {
			modoAccesoDlg = TypeModoAcceso.EDICION;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		if (this.datoSeleccionado.getAmbito() == TypeAmbito.AREA) {
			params.put(TypeParametroVentana.AREA.toString(), this.datoSeleccionado.getIdArea().toString());
		}
		if (this.datoSeleccionado.getAmbito() == TypeAmbito.ENTIDAD) {
			params.put(TypeParametroVentana.ENTIDAD.toString(), UtilJSF.getIdEntidad().toString());
		}
		params.put(TypeParametroVentana.AMBITO.toString(), this.datoSeleccionado.getAmbito().toString());
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, modoAccesoDlg, params, true, 550, 195);

	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);

		if (!verificarChecksVacios()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("viewConsultaGeneral.error.seleccionAmbitoyElemento"));
		} else {
			buscar();
		}
	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("consultaGeneral");
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

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// Quitamos seleccion de dato
		datoSeleccionado = null;
		listaDatos = consultaService.listar(filtro, UtilJSF.getIdioma(), null, null, checkAmbitoGlobal,
				checkAmbitoEntidad, checkAmbitoArea, checkDominio, checkConf, checkGFE);

	}

	private boolean verificarChecksVacios() {
		return (checkAmbitoGlobal || checkAmbitoEntidad || checkAmbitoArea) && (checkConf || checkDominio || checkGFE);
	}

	// ------- GETTERS / SETTERS --------------------------------

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * Obtiene el valor de filtro.
	 *
	 * @return el valor de filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * Establece el valor de filtro.
	 *
	 * @param filtro el nuevo valor de filtro
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public List<ConsultaGeneral> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final List<ConsultaGeneral> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public ConsultaGeneral getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @return the checkDominio
	 */
	public boolean isCheckDominio() {
		return checkDominio;
	}

	/**
	 * @param checkDominio the checkDominio to set
	 */
	public void setCheckDominio(boolean checkDominio) {
		this.checkDominio = checkDominio;
	}

	/**
	 * @return the checkGFE
	 */
	public boolean isCheckGFE() {
		return checkGFE;
	}

	/**
	 * @param checkGFE the checkGFE to set
	 */
	public void setCheckGFE(boolean checkGFE) {
		this.checkGFE = checkGFE;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(ConsultaGeneral datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the checkConf
	 */
	public boolean isCheckConf() {
		return checkConf;
	}

	/**
	 * @param checkConf the checkConf to set
	 */
	public void setCheckConf(boolean checkConf) {
		this.checkConf = checkConf;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the checkAmbitoGlobal
	 */
	public boolean isCheckAmbitoGlobal() {
		return checkAmbitoGlobal;
	}

	/**
	 * @param checkAmbitoGlobal the checkAmbitoGlobal to set
	 */
	public void setCheckAmbitoGlobal(boolean checkAmbitoGlobal) {
		this.checkAmbitoGlobal = checkAmbitoGlobal;
	}

	/**
	 * @return the checkAmbitoEntidad
	 */
	public boolean isCheckAmbitoEntidad() {
		return checkAmbitoEntidad;
	}

	/**
	 * @param checkAmbitoEntidad the checkAmbitoEntidad to set
	 */
	public void setCheckAmbitoEntidad(boolean checkAmbitoEntidad) {
		this.checkAmbitoEntidad = checkAmbitoEntidad;
	}

	/**
	 * @return the checkAmbitoArea
	 */
	public boolean isCheckAmbitoArea() {
		return checkAmbitoArea;
	}

	/**
	 * @param checkAmbitoArea the checkAmbitoArea to set
	 */
	public void setCheckAmbitoArea(boolean checkAmbitoArea) {
		this.checkAmbitoArea = checkAmbitoArea;
	}

}

/*
 *
 */
package es.caib.sistrages.frontend.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.Traduccion;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.AvisoEntidadService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

/**
 * DialogTramiteControlAcceso.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class DialogTramiteControlAcceso extends DialogControllerBase {

	/** Service. **/
	@Inject
	private AvisoEntidadService avisoEntidadService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** System service. **/
	@Inject
	private SystemService systemService;

	/** Security service. **/
	@Inject
	private SecurityService securityService;

	/** Idiomas del tramite version. **/
	private List<String> idiomas;

	/** Id elemento a tratar. */
	private Long id;

	/** Tramite. **/
	private Tramite tramite;

	/** Tramite version. */
	private TramiteVersion tramiteVersion;

	/** Aviso entidad. **/
	private AvisoEntidad avisoEntidad;

	private String portapapeles;

	private String errorCopiar;
	private List<AvisoEntidad> avisos;

	private boolean isNuevo;

	private boolean eliminarAviso;

	/**
	 * Inicializacion.
	 */
	public void init() {
		isNuevo = false;
		tramiteVersion = tramiteService.getTramiteVersion(id);
		idiomas = UtilTraducciones.getIdiomas(tramiteVersion.getIdiomasSoportados());
		tramite = tramiteService.getTramite(tramiteVersion.getIdTramite());
		eliminarAviso = false;
		avisos = avisoEntidadService
				.getAvisoEntidadByTramite(tramiteVersion.getCodigo() + "#" + tramiteVersion.getNumeroVersion());
		if (avisos == null || avisos.isEmpty()) {
			crearAvisoEntidad();
		} else {
			setAvisoEntidad(avisos.get(0));
		}

	}

	public boolean deshabilitarEdicionDebug() {
		final TypeEntorno entornoActual = TypeEntorno.fromString(UtilJSF.getEntorno());
		if (entornoActual.equals(TypeEntorno.PREPRODUCCION) || entornoActual.equals(TypeEntorno.PRODUCCION)) {
			if (UtilJSF.getSessionBean().getActiveRole() != TypeRoleAcceso.ADMIN_ENT) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void eliminarAviso() {
		AvisoEntidad avisoEntidadAux = avisoEntidad;
		avisoEntidad = new AvisoEntidad();
		avisoEntidad.setCodigo(avisoEntidadAux.getCodigo());
		avisoEntidad.setTipo(avisoEntidadAux.getTipo());
		avisoEntidad.setListaSerializadaTramites(avisoEntidadAux.getListaSerializadaTramites());
		eliminarAviso = true;
	}

	/**
	 * Genera vacío el aviso entidad.
	 */
	private void crearAvisoEntidad() {
		isNuevo = true;
		avisoEntidad = new AvisoEntidad();
		avisoEntidad.setTipo(TypeAvisoEntidad.TRAMITE_VERSION);
		avisoEntidad.setBloqueado(false);
		avisoEntidad.setListaSerializadaTramites(tramiteVersion.getCodigo() + "#" + tramiteVersion.getNumeroVersion());
	}

	/**
	 * Editar descripcion.
	 */
	public void editarMensajeDesactivacion() {

		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, this.avisoEntidad.getMensaje(), tramiteVersion);

	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			this.avisoEntidad.setMensaje(literales);
		}
	}

	/**
	 * Borra el aviso entidad
	 */
	public void eliminarAvisoEntidad() {
		if (avisoEntidad.getCodigo() != null) {
			avisoEntidadService.removeAvisoEntidad(avisoEntidad.getCodigo());
		}

		crearAvisoEntidad();
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// Comprobamos que el mensaje de aviso está correcto.
		if (!checkMensajeAviso()) {
			return;
		}
		if (avisoEntidad.getTipo().equals(TypeAvisoEntidad.LISTA) && isModificado() && !eliminarAviso) {
			String[] viejaList = avisoEntidad.getListaSerializadaTramites().split(";");
			String nuevaList = "";
			for (String ident : viejaList) {
				if (!ident.equals(tramiteVersion.getCodigo() + "#" + tramiteVersion.getNumeroVersion())) {
					if (nuevaList.isEmpty()) {
						nuevaList += ident;
					} else {
						nuevaList += ";" + ident;
					}
				}
			}

			if (nuevaList.isEmpty()) {
				avisoEntidad.setTipo(TypeAvisoEntidad.TRAMITE_VERSION);
				avisoEntidad.setListaSerializadaTramites(
						tramiteVersion.getCodigo() + "#" + tramiteVersion.getNumeroVersion());
				avisoEntidadService.updateAvisoEntidad(avisoEntidad);
			} else {
				AvisoEntidad avisoEntidadAux = avisoEntidad;
				crearAvisoEntidad();
				Literal lit = new Literal();
				for (Traduccion tradAux : avisoEntidadAux.getMensaje().getTraducciones()) {
					Traduccion trad = new Traduccion(tradAux.getIdioma(), tradAux.getLiteral());
					lit.add(trad);
				}
				avisoEntidad.setMensaje(lit);
				avisoEntidad.setFechaInicio(avisoEntidadAux.getFechaInicio());
				avisoEntidad.setFechaFin(avisoEntidadAux.getFechaFin());
				avisoEntidad.setBloqueado(avisoEntidadAux.isBloqueado());
				avisoEntidadAux.setListaSerializadaTramites(nuevaList);
				AvisoEntidad old = avisoEntidadService.getAvisoEntidad(avisoEntidadAux.getCodigo());
				avisoEntidadAux.setMensaje(old.getMensaje());
				avisoEntidadAux.setFechaInicio(old.getFechaInicio());
				avisoEntidadAux.setFechaFin(old.getFechaFin());
				avisoEntidadAux.setBloqueado(old.isBloqueado());
				avisoEntidadService.updateAvisoEntidad(avisoEntidadAux);
			}
		} else if (eliminarAviso) {
			if (avisoEntidad.getTipo().equals(TypeAvisoEntidad.LISTA)) {
				String[] viejaList = avisoEntidad.getListaSerializadaTramites().split(";");
				String nuevaList = "";
				for (String ident : viejaList) {
					if (!ident.equals(tramiteVersion.getCodigo() + "#" + tramiteVersion.getNumeroVersion())) {
						if (nuevaList.isEmpty()) {
							nuevaList += ident;
						} else {
							nuevaList += ";" + ident;
						}
					}
				}

				if (nuevaList.isEmpty()) {
					avisoEntidadService.removeAvisoEntidad(avisoEntidad.getCodigo());
				} else {
					AvisoEntidad old = avisoEntidadService.getAvisoEntidad(avisoEntidad.getCodigo());
					old.setListaSerializadaTramites(nuevaList);
					avisoEntidadService.updateAvisoEntidad(old);
				}
			} else {
				avisoEntidadService.removeAvisoEntidad(avisoEntidad.getCodigo());
			}
			if (avisoEntidad.getMensaje() != null) {
				AvisoEntidad avisoEntidadAux = avisoEntidad;
				crearAvisoEntidad();
				Literal lit = new Literal();
				for (Traduccion tradAux : avisoEntidadAux.getMensaje().getTraducciones()) {
					Traduccion trad = new Traduccion(tradAux.getIdioma(), tradAux.getLiteral());
					lit.add(trad);
				}
				avisoEntidad.setMensaje(lit);
				avisoEntidad.setFechaInicio(avisoEntidadAux.getFechaInicio());
				avisoEntidad.setFechaFin(avisoEntidadAux.getFechaFin());
				avisoEntidad.setBloqueado(avisoEntidadAux.isBloqueado());
			} else {
				avisoEntidad = new AvisoEntidad();
			}
		}
		tramiteService.updateTramiteVersionControlAcceso(tramiteVersion, avisoEntidad, UtilJSF.getIdEntidad());
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(this.tramiteVersion);
		UtilJSF.closeDialog(result);
	}

	/**
	 * Comprueba que el mensaje de aviso está correcto: <br />
	 * - Que si están rellenas las fechas, la fecha fin sea posterior a fecha ini.
	 * <br />
	 * - Que si no está relleno el mensaje, tampoco estén rellenos el resto de
	 * datos.
	 *
	 * @return
	 */
	private boolean checkMensajeAviso() {

		boolean retorno;
		if (avisoEntidad.getFechaFin() != null && avisoEntidad.getFechaInicio() != null
				&& avisoEntidad.getFechaFin().before(avisoEntidad.getFechaInicio())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("dialogTramiteControlAcceso.error.fechas"));
			retorno = false;
		} else if (isVacio(avisoEntidad.getMensaje()) && (avisoEntidad.isBloqueado()
				|| avisoEntidad.getFechaFin() != null || avisoEntidad.getFechaInicio() != null)) {
			addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogTramiteControlAcceso.error.mensaje"));
			retorno = false;
		} else {
			retorno = true;
		}
		return retorno;
	}

	private boolean isModificado() {
		boolean modif = false;
		if (!isNuevo && !avisoEntidad.equals(avisoEntidadService.getAvisoEntidad(avisoEntidad.getCodigo()))) {
			modif = true;
		}
		return modif;
	}

	/**
	 * Comprueba que un literal no tiene traducciones.
	 *
	 * @param mensaje
	 * @return
	 */
	private boolean isVacio(final Literal mensaje) {
		boolean vacio = false;
		if (mensaje == null || mensaje.getTraducciones() == null || mensaje.getTraducciones().isEmpty()) {
			vacio = true;
		}
		return vacio;
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("tramiteControlAccesoDialog");
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

	/**
	 * Obtiene el valor de id.
	 *
	 * @return el valor de id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el valor de id.
	 *
	 * @param id el nuevo valor de id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el valor de tramiteVersion.
	 *
	 * @return el valor de tramiteVersion
	 */
	public TramiteVersion getTramiteVersion() {
		return tramiteVersion;
	}

	/**
	 * Establece el valor de tramiteVersion.
	 *
	 * @param tramiteVersion el nuevo valor de tramiteVersion
	 */
	public void setTramiteVersion(final TramiteVersion tramiteVersion) {
		this.tramiteVersion = tramiteVersion;
	}

	/**
	 * @return the avisoEntidad
	 */
	public AvisoEntidad getAvisoEntidad() {
		return avisoEntidad;
	}

	/**
	 * @param avisoEntidad the avisoEntidad to set
	 */
	public void setAvisoEntidad(final AvisoEntidad avisoEntidad) {
		this.avisoEntidad = avisoEntidad;
	}

	/**
	 * @return the idiomas
	 */
	public List<String> getIdiomas() {
		return idiomas;
	}

	/**
	 * @param idiomas the idiomas to set
	 */
	public void setIdiomas(final List<String> idiomas) {
		this.idiomas = idiomas;
	}

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

}

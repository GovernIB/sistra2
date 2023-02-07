package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;

import es.caib.sistrages.core.api.model.VariableArea;

import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.service.VariablesAreaService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.DialogResultMessage;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogVariablesArea extends DialogControllerBase {

	/** Servicio. */
	@Inject
	private VariablesAreaService vaService;

	/** Servicio Tramite. */
	@Inject
	private TramiteService tramiteService;

	/** Id elemento a tratar. */
	private String id;

	/** Id area. */
	private String idArea;

	/** Datos elemento. */
	private VariableArea data;

	/** Parametro modo importar **/
	private String modoImportar;

	private String portapapeles;

	private String errorCopiar;

	private boolean dr;

	private boolean gfe;

	private boolean er;

	private String messageConfirmacion;

	private String identInicial;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new VariableArea();
		} else {
			if (id != null) {
				data = vaService.loadVariableArea(Long.parseLong(id));
			}

			if (modo == TypeModoAcceso.EDICION) {
				messageConfirmacion = "";

				dr = false;
				er = false;
				gfe = false;
				if (!vaService.envioRemotoByVariable(data).isEmpty()) {
					er = true;
				}

				if (!vaService.dominioByVariable(data).isEmpty()) {
					dr = true;
				}

				if (!vaService.gfeByVariable(data).isEmpty()) {
					gfe = true;
				}

				if (er || dr || gfe) {
					if (er) {
						messageConfirmacion += UtilJSF.getLiteral("cabecera.opciones.envios_remotos");
					}

					if (dr) {
						if (!er) {
							messageConfirmacion += UtilJSF.getLiteral("botones.dominios");
						} else {
							messageConfirmacion += ", " + UtilJSF.getLiteral("botones.dominios");
						}
					}

					if (gfe) {
						if (!er && !dr) {
							messageConfirmacion += UtilJSF.getLiteral("botones.sgeArea");
						} else {
							messageConfirmacion += ", " + UtilJSF.getLiteral("botones.sgeArea");
						}
					}
				}
				messageConfirmacion = UtilJSF.getLiteral("variable.area.asociada",
						new Object[] { messageConfirmacion });
			}
		}
		identInicial = data.getIdentificador();

	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {
		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		data.setArea(tramiteService.getArea(Long.valueOf(idArea)));

		switch (acceso) {
		case ALTA:
			if (!verificarGuardar()) {
				return;
			}
			Long idConf = vaService.addVariableArea(this.data);
			this.data.setCodigo(idConf);
			break;
		case EDICION:
			if (!verificarGuardar()) {
				return;
			}
			vaService.updateVariableArea(this.data);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		if (mostrarConfirmacion()) {
			DialogResultMessage msg = new DialogResultMessage();
			msg.setNivel(TypeNivelGravedad.WARNING);
			msg.setMensaje(messageConfirmacion);
			result.setMensaje(msg);
		}
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data);
		UtilJSF.closeDialog(result);
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

	/**
	 * Abre dialogo de dominios.
	 *
	 */
	public void dominios() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));

		UtilJSF.openDialog(DialogVariablesAreaDominios.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Abre dialogo de formularios externos.
	 *
	 */
	public void formsExternos() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));

		UtilJSF.openDialog(DialogVariablesAreaFormulariosExternos.class, TypeModoAcceso.CONSULTA, params, true, 770,
				400);
	}

	/**
	 * Abre dialogo de enviosRemotos.
	 *
	 */
	public void enviosRemotos() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.data.getCodigo()));

		UtilJSF.openDialog(DialogVariablesAreaEnviosRemotos.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {
		if (data.getIdentificador() == null || data.getIdentificador().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getDescripcion() == null || data.getDescripcion().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (data.getUrl() == null || data.getUrl().isEmpty()) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.obligatorio"));
			return false;
		}

		if (modoAcceso.equals(TypeModoAcceso.ALTA.toString()) || modoAcceso.equals(TypeModoAcceso.EDICION.toString())) {
			boolean existe = vaService.loadVariableAreaByIdentificador(this.data.getIdentificador(),
					this.data.getArea().getCodigo()) != null ? true : false;

			if (existe && isIdentModificado()) {
				addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
				return false;
			}
		}

		return true;
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the modoImportar
	 */
	public String getModoImportar() {
		return modoImportar;
	}

	/**
	 * @param modoImportar the modoImportar to set
	 */
	public void setModoImportar(String modoImportar) {
		this.modoImportar = modoImportar;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("variablesAreaDialog");
	}

	/**
	 * @return the data
	 */
	public final VariableArea getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(VariableArea data) {
		this.data = data;
	}

	/**
	 * @return the idArea
	 */
	public final String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public final void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the dr
	 */
	public final boolean isDr() {
		return dr;
	}

	/**
	 * @param dr the dr to set
	 */
	public final void setDr(boolean dr) {
		this.dr = dr;
	}

	/**
	 * @return the gfe
	 */
	public final boolean isGfe() {
		return gfe;
	}

	/**
	 * @param gfe the gfe to set
	 */
	public final void setGfe(boolean gfe) {
		this.gfe = gfe;
	}

	/**
	 * @return the er
	 */
	public final boolean isEr() {
		return er;
	}

	/**
	 * @param er the er to set
	 */
	public final void setEr(boolean er) {
		this.er = er;
	}

	/**
	 * @return the messageConfirmacion
	 */
	public final String getMessageConfirmacion() {
		return messageConfirmacion;
	}

	/**
	 * @param messageConfirmacion the messageConfirmacion to set
	 */
	public final void setMessageConfirmacion(String messageConfirmacion) {
		this.messageConfirmacion = messageConfirmacion;
	}

	public final boolean isIdentModificado() {
		if (!this.data.getIdentificador().equals(identInicial)) {
			return true;
		} else {
			return false;
		}
	}

	public final boolean mostrarConfirmacion() {
		if (TypeModoAcceso.valueOf(modoAcceso).equals(TypeModoAcceso.EDICION)
				&& (!this.data.getIdentificador().equals(identInicial)) && (er || dr || gfe)) {
			return true;
		} else {
			return false;
		}
	}
}

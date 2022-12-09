package es.caib.sistrages.frontend.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogEntidad extends DialogControllerBase {

	/**
	 * Servicio.
	 */
	@Inject
	private EntidadService entidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/**
	 * Datos elemento.
	 */
	private Entidad data;

	/**
	 * literal para mostrar.
	 */
	private String literal;

	private String portapapeles;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);

		UtilJSF.checkSecOpenDialog(modo, id);

		if (modo == TypeModoAcceso.ALTA) {
			data = new Entidad();
			data.setDiasPreregistro(30);
		} else {
			if (id != null) {
				data = entidadService.loadEntidad(Long.valueOf(id));
				if (data != null && data.getNombre() != null) {
					literal = data.getNombre().getTraduccion(UtilJSF.getSessionBean().getLang());
				}
			}
		}
	}

	/**
	 * Retorno dialogo de los botones de traducciones.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setNombre(literales);
			literal = literales.getTraduccion(UtilJSF.getSessionBean().getLang());
		}
	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarNombre() {
		UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getNombre(), null, null);
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (entidadService.existeCodigoDIR3(data.getCodigoDIR3(), data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogEntidad.error.codigoDIR3repetido"));
			return;
		}


		if (entidadService.existeEntidad(data.getIdentificador(), data.getCodigo())) {
			addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.identificador.repetido"));
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			entidadService.addEntidad(data);
			break;
		case EDICION:
			entidadService.updateEntidadSuperAdministrador(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
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

	public void mensaje() {
		addMessageContext(TypeNivelGravedad.INFO, "Atento", "Ojo al dato.");
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Entidad getData() {
		return data;
	}

	public void setData(final Entidad data) {
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
	 * @param literal
	 *            el nuevo valor de literal
	 */
	public void setLiteral(final String literal) {
		this.literal = literal;
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("entidadDialog");
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}
}

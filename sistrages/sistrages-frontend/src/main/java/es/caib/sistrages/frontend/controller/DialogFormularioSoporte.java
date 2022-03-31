package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.FormularioSoporte;
import es.caib.sistrages.core.api.model.Literal;
import es.caib.sistrages.core.api.model.types.TypeFormularioSoporte;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilTraducciones;

@ManagedBean
@ViewScoped
public class DialogFormularioSoporte extends DialogControllerBase {

	@Inject
	private EntidadService entidadService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	/** Datos elemento. */
	private FormularioSoporte data;

	private String tipoIncidencia;

	private String descripcion;

	private Boolean listaCorr;

	private Boolean respInc;

	private Boolean adminArea;

	private String emails;

	/**
	 * Inicialización.
	 */
	public void init() {
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		if (modo == TypeModoAcceso.ALTA) {
			data = new FormularioSoporte();

		} else {
			if (id != null) {
				data = entidadService.loadOpcionFormularioSoporte(Long.valueOf(id));
				if (data != null && data.getDescripcion() != null) {
					descripcion = data.getDescripcion().getTraduccion(UtilJSF.getSessionBean().getLang());
				}

				if (data != null && data.getTipoIncidencia() != null) {
					tipoIncidencia = data.getTipoIncidencia().getTraduccion(UtilJSF.getSessionBean().getLang());
				}

				if (data != null && data.getTipoDestinatario() != null) {
					for (TypeFormularioSoporte tipoDest : data.getTipoDestinatario()) {
						if (tipoDest.equals(TypeFormularioSoporte.LISTA_DE_EMAILS)) {
							listaCorr = true;
						}
						if (tipoDest.equals(TypeFormularioSoporte.RESPONSABLE_DE_INCIDENCIAS)) {
							respInc = true;
						}
						if (tipoDest.equals(TypeFormularioSoporte.ADMINISTRADOR_AREA)) {
							adminArea = true;
						}
					}
					tipoIncidencia = data.getTipoIncidencia().getTraduccion(UtilJSF.getSessionBean().getLang());
				}
			}
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoDescripcion(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setDescripcion(literales);
			descripcion = literales.getTraduccion(UtilJSF.getSessionBean().getLang());
		}

	}

	/**
	 * Retorno dialogo de los botones de propiedades.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoTipoIncidencia(final SelectEvent event) {
		final DialogResult respuesta = (DialogResult) event.getObject();
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != TypeModoAcceso.CONSULTA) {
			final Literal literales = (Literal) respuesta.getResult();
			data.setTipoIncidencia(literales);
			tipoIncidencia = literales.getTraduccion(UtilJSF.getSessionBean().getLang());
		}
	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarDescripcion() {
		if (data.getDescripcion() == null) {
			UtilTraducciones.openDialogTraduccionAlta();
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getDescripcion(), null, null);
		}

	}

	/**
	 * Editar descripcion del dominio.
	 *
	 *
	 */
	public void editarTipoIncidencia() {
		if (data.getTipoIncidencia() == null) {
			UtilTraducciones.openDialogTraduccionAlta();
		} else {
			UtilTraducciones.openDialogTraduccion(TypeModoAcceso.EDICION, data.getTipoIncidencia(), null, null);
		}
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		if (listaCorr && data.getListaEmails() != null && data.getListaEmails().isEmpty()) {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFormularioSoporte.error.emailVacio"));
			return;
		}

		// Si no pone de tipo emails, se quitan los emails.
		if (!listaCorr) {
			data.setListaEmails("");
		}

		// Se añaden los tipos
		if (listaCorr || respInc || adminArea) {
			List<TypeFormularioSoporte> lista = new ArrayList<>();
			if (listaCorr) {
				lista.add(TypeFormularioSoporte.LISTA_DE_EMAILS);
			}
			if (respInc) {
				lista.add(TypeFormularioSoporte.RESPONSABLE_DE_INCIDENCIAS);
			}
			if (adminArea) {
				lista.add(TypeFormularioSoporte.ADMINISTRADOR_AREA);
			}
			data.setTipoDestinatario(lista);
		} else {
			addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("dialogFormularioSoporte.error.noTipo"));
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			entidadService.addOpcionFormularioSoporte(UtilJSF.getIdEntidad(), data);
			break;
		case EDICION:
			entidadService.updateOpcionFormularioSoporte(data);
			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
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
	 * @return the data
	 */
	public FormularioSoporte getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(final FormularioSoporte data) {
		this.data = data;
	}

	public String getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(final String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the listaEmails
	 */
	public final Boolean getListaCorr() {
		return listaCorr;
	}

	/**
	 * @param listaEmails the listaEmails to set
	 */
	public final void setListaCorr(Boolean listaCorr) {
		this.listaCorr = listaCorr;
	}

	/**
	 * @return the respInc
	 */
	public final Boolean getRespInc() {
		return respInc;
	}

	/**
	 * @param respInc the respInc to set
	 */
	public final void setRespInc(Boolean respInc) {
		this.respInc = respInc;
	}

	/**
	 * @return the adminArea
	 */
	public final Boolean getAdminArea() {
		return adminArea;
	}

	/**
	 * @param adminArea the adminArea to set
	 */
	public final void setAdminArea(Boolean adminArea) {
		this.adminArea = adminArea;
	}

	/**
	 * @return the emails
	 */
	public final String getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public final void setEmails(String emails) {
		this.emails = emails;
	}

}

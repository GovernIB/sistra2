package es.caib.sistramit.core.service.model.flujo;

import java.util.Date;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.Persona;
import es.caib.sistramit.core.api.model.flujo.types.TypeDocumento;

/**
 * Datos de un justificante de registro accesibles desde los demás pasos. Será
 * creado en el paso Registrar. Se podrá consultar en el paso Guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosDocumentoJustificante extends DatosDocumento {
	/**
	 * Indica si es un preregistro o registro.
	 */
	private TypeSiNo preregistro = TypeSiNo.NO;
	/**
	 * Número de registro.
	 */
	private String numeroRegistro;
	/**
	 * Fecha de registro.
	 */
	private Date fechaRegistro;
	/**
	 * Asunto.
	 */
	private String asunto;
	/**
	 * Solicitante.
	 */
	private Persona solicitante;

	/**
	 * Indica si tiene version imprimible o no.
	 */
	private TypeSiNo tieneVersionImprimible = TypeSiNo.NO;

	/**
	 * Indica numero de registro del justificante.
	 *
	 * @return numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Indica numero de registro del justificante.
	 *
	 * @param pNumeroRegistro
	 *            numeroRegistro a establecer
	 */
	public void setNumeroRegistro(final String pNumeroRegistro) {
		numeroRegistro = pNumeroRegistro;
	}

	/**
	 * Indica fecha de registro del justificante.
	 *
	 * @return fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Indica fecha de registro del justificante.
	 *
	 * @param pFechaRegistro
	 *            fechaRegistro a establecer
	 */
	public void setFechaRegistro(final Date pFechaRegistro) {
		fechaRegistro = pFechaRegistro;
	}

	/**
	 * Indica asunto de registro del justificante.
	 *
	 * @return asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Indica asunto de registro del justificante.
	 *
	 * @param pAsunto
	 *            asunto a establecer
	 */
	public void setAsunto(final String pAsunto) {
		asunto = pAsunto;
	}

	/**
	 * Indica método de acceso del solicitante.
	 *
	 * @return solicitante
	 */
	public Persona getSolicitante() {
		return solicitante;
	}

	/**
	 * Indica método de acceso del solicitante.
	 *
	 * @param pSolicitante
	 *            solicitante a establecer
	 */
	public void setSolicitante(final Persona pSolicitante) {
		solicitante = pSolicitante;
	}

	/**
	 * Instancia un nuevo datos documento justificante de
	 * DatosDocumentoJustificante.
	 */
	public DatosDocumentoJustificante() {
		super();
		this.setTipo(TypeDocumento.JUSTIFICANTE);
	}

	/**
	 * Método de acceso a preregistro.
	 *
	 * @return preregistro
	 */
	public TypeSiNo getPreregistro() {
		return preregistro;
	}

	/**
	 * Método para establecer preregistro.
	 *
	 * @param pPreregistro
	 *            preregistro a establecer
	 */
	public void setPreregistro(final TypeSiNo pPreregistro) {
		preregistro = pPreregistro;
	}

	/**
	 * Método de acceso a tieneVersionImprimible.
	 *
	 * @return tieneVersionImprimible
	 */
	public TypeSiNo getTieneVersionImprimible() {
		return tieneVersionImprimible;
	}

	/**
	 * Método para establecer tieneVersionImprimible.
	 *
	 * @param pTieneVersionImprimible
	 *            tieneVersionImprimible a establecer
	 */
	public void setTieneVersionImprimible(final TypeSiNo pTieneVersionImprimible) {
		tieneVersionImprimible = pTieneVersionImprimible;
	}

}

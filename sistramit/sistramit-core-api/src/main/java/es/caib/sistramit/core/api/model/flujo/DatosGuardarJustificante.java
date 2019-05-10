package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Datos del justificante en el paso guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosGuardarJustificante implements ModelApi {

	/**
	 * Indica si es preregistro.
	 */
	private TypeSiNo preregistro = TypeSiNo.NO;

	/**
	 * Número de registro.
	 */
	private String numero;

	/**
	 * Fecha de registro.
	 */
	private String fecha;

	/**
	 * Url acceso justificante.
	 */
	private TypeSiNo url = TypeSiNo.NO;

	/**
	 * Asunto.
	 */
	private String asunto;

	/**
	 * Solicitante.
	 */
	private Persona solicitante;

	/**
	 * Lista de formularios a registrar.
	 */
	private List<DocumentoRegistro> formularios = new ArrayList<>();

	/**
	 * Lista de anexos a registrar.
	 */
	private List<DocumentoRegistro> anexos = new ArrayList<>();

	/**
	 * Lista de pagos a registrar.
	 */
	private List<DocumentoRegistro> pagos = new ArrayList<>();

	/**
	 * Método de acceso a numero.
	 *
	 * @return numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Método para establecer numero.
	 *
	 * @param pNumero
	 *            numero a establecer
	 */
	public void setNumero(final String pNumero) {
		numero = pNumero;
	}

	/**
	 * Método de acceso a fecha.
	 *
	 * @return fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Método para establecer fecha.
	 *
	 * @param pFecha
	 *            fecha a establecer
	 */
	public void setFecha(final String pFecha) {
		fecha = pFecha;
	}

	/**
	 * Método de acceso a asunto.
	 *
	 * @return asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Método para establecer asunto.
	 *
	 * @param pAsunto
	 *            asunto a establecer
	 */
	public void setAsunto(final String pAsunto) {
		asunto = pAsunto;
	}

	/**
	 * Método de acceso a solicitante.
	 *
	 * @return solicitante
	 */
	public Persona getSolicitante() {
		return solicitante;
	}

	/**
	 * Método para establecer solicitante.
	 *
	 * @param pSolicitante
	 *            solicitante a establecer
	 */
	public void setSolicitante(final Persona pSolicitante) {
		solicitante = pSolicitante;
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
	 * Método de acceso a formularios.
	 *
	 * @return formularios
	 */
	public List<DocumentoRegistro> getFormularios() {
		return formularios;
	}

	/**
	 * Método de acceso a anexos.
	 *
	 * @return anexos
	 */
	public List<DocumentoRegistro> getAnexos() {
		return anexos;
	}

	/**
	 * Método de acceso a pagos.
	 *
	 * @return pagos
	 */
	public List<DocumentoRegistro> getPagos() {
		return pagos;
	}

	/**
	 * Método para establecer formularios.
	 *
	 * @param formularios
	 *            formularios a establecer
	 */
	public void setFormularios(final List<DocumentoRegistro> formularios) {
		this.formularios = formularios;
	}

	/**
	 * Método para establecer anexos.
	 *
	 * @param anexos
	 *            anexos a establecer
	 */
	public void setAnexos(final List<DocumentoRegistro> anexos) {
		this.anexos = anexos;
	}

	/**
	 * Método para establecer pagos.
	 *
	 * @param pagos
	 *            pagos a establecer
	 */
	public void setPagos(final List<DocumentoRegistro> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Método de acceso a url.
	 * 
	 * @return url
	 */
	public TypeSiNo getUrl() {
		return url;
	}

	/**
	 * Método para establecer url.
	 * 
	 * @param url
	 *            url a establecer
	 */
	public void setUrl(final TypeSiNo url) {
		this.url = url;
	}

}

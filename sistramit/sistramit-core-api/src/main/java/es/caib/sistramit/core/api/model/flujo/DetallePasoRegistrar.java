package es.caib.sistramit.core.api.model.flujo;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoFirma;
import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Detalle del paso "Registrar".
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DetallePasoRegistrar extends DetallePaso {

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
	 * Indica presentador. Por defecto el usuario que realiza el trámite. Se debe
	 * establecer siempre.
	 */
	private Persona presentador;

	/**
	 * Indica si existe representado.
	 */
	private Persona representado;

	/**
	 * Indica si se cumplen las condiciones para registrar (docs firmados, etc.).
	 */
	private TypeSiNo registrar = TypeSiNo.NO;

	/**
	 * Indica si se debe reintentar el registro o iniciarlo de nuevo.
	 */
	private TypeSiNo reintentar = TypeSiNo.NO;

	/**
	 * Instrucciones entrega presencial.
	 */
	private String instruccionesEntregaPresencial;

	/**
	 * Constructor.
	 */
	public DetallePasoRegistrar() {
		super();
		this.setTipo(TypePaso.REGISTRAR);
	}

	/**
	 * Método de acceso a presentador.
	 *
	 * @return presentador
	 */
	public Persona getPresentador() {
		return presentador;
	}

	/**
	 * Método para establecer presentador.
	 *
	 * @param pPresentador
	 *            presentador a establecer
	 */
	public void setPresentador(final Persona pPresentador) {
		presentador = pPresentador;
	}

	/**
	 * Método de acceso a representado.
	 *
	 * @return representado
	 */
	public Persona getRepresentado() {
		return representado;
	}

	/**
	 * Método para establecer representado.
	 *
	 * @param pRepresentado
	 *            representado a establecer
	 */
	public void setRepresentado(final Persona pRepresentado) {
		representado = pRepresentado;
	}

	/**
	 * Método de acceso a reintentar.
	 *
	 * @return reintentar
	 */
	public TypeSiNo getReintentar() {
		return reintentar;
	}

	/**
	 * Método para establecer reintentar.
	 *
	 * @param pReintentar
	 *            reintentar a establecer
	 */
	public void setReintentar(final TypeSiNo pReintentar) {
		reintentar = pReintentar;
	}

	/**
	 * Método de acceso a registrar.
	 *
	 * @return registrar
	 */
	public TypeSiNo getRegistrar() {
		return registrar;
	}

	/**
	 * Método para establecer registrar.
	 *
	 * @param registrar
	 *            registrar a establecer
	 */
	public void setRegistrar(TypeSiNo registrar) {
		this.registrar = registrar;
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
	 * Método para establecer formularios.
	 *
	 * @param formularios
	 *            formularios a establecer
	 */
	public void setFormularios(List<DocumentoRegistro> formularios) {
		this.formularios = formularios;
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
	 * Método para establecer anexos.
	 *
	 * @param anexos
	 *            anexos a establecer
	 */
	public void setAnexos(List<DocumentoRegistro> anexos) {
		this.anexos = anexos;
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
	 * Método para establecer pagos.
	 *
	 * @param pagos
	 *            pagos a establecer
	 */
	public void setPagos(List<DocumentoRegistro> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Método de acceso a instruccionesEntregaPresencial.
	 *
	 * @return instruccionesEntregaPresencial
	 */
	public String getInstruccionesEntregaPresencial() {
		return instruccionesEntregaPresencial;
	}

	/**
	 * Método para establecer instruccionesEntregaPresencial.
	 *
	 * @param instruccionesEntregaPresencial
	 *            instruccionesEntregaPresencial a establecer
	 */
	public void setInstruccionesEntregaPresencial(String instruccionesEntregaPresencial) {
		this.instruccionesEntregaPresencial = instruccionesEntregaPresencial;
	}

	/**
	 * Calcula si estan firmados todos los documentos.
	 *
	 * @return boolean
	 */
	public boolean verificarFirmas() {
		return verificarFirmas(formularios) && verificarFirmas(anexos);
	}

	/**
	 * Verifica si estan firmados los documentos.
	 *
	 * @param res
	 * @param docsFormulario
	 * @return
	 */
	private boolean verificarFirmas(List<DocumentoRegistro> docsFormulario) {
		boolean res = true;
		for (final DocumentoRegistro doc : docsFormulario) {
			if (doc.getFirmar() == TypeSiNo.SI) {
				for (final Firma fi : doc.getFirmas()) {
					if (fi.getEstadoFirma() == TypeEstadoFirma.NO_FIRMADO) {
						res = false;
						break;
					}
				}
			}
		}
		return res;
	}

	/**
	 * Busca documento de registro en formularios, anexos, pagos,...
	 *
	 * @param idDocumento
	 *            id documento
	 * @param instancia
	 *            instancia
	 * @return documento registro o nulo si no lo encuentra
	 */
	public DocumentoRegistro buscarDocumentoRegistro(String idDocumento, int instancia) {
		DocumentoRegistro res = null;
		res = buscarDocumentoRegistro(formularios, idDocumento, instancia);
		if (res == null) {
			res = buscarDocumentoRegistro(anexos, idDocumento, instancia);
		}
		if (res == null) {
			res = buscarDocumentoRegistro(pagos, idDocumento, instancia);
		}
		return res;
	}

	private DocumentoRegistro buscarDocumentoRegistro(List<DocumentoRegistro> listaDocsRegistro, String idDocumento,
			int instancia) {
		DocumentoRegistro res = null;
		for (final DocumentoRegistro dr : listaDocsRegistro) {
			if (dr.getId().equals(idDocumento) && dr.getInstancia() == instancia) {
				res = dr;
				break;
			}
		}
		return res;
	}

	@Override
	public String print() {
		final String ident = "    ";
		final int capacity = ConstantesNumero.N2 * ConstantesNumero.N1024;
		final StringBuffer strb = new StringBuffer(capacity);
		strb.append(ident).append("\n");
		strb.append(ident).append("ID paso:" + getId() + "\n");
		strb.append(ident).append("Tipo:" + getTipo() + "\n");
		strb.append(ident).append("Completado:" + getCompletado() + "\n");
		strb.append(ident).append("Sólo lectura:" + getSoloLectura() + "\n");
		strb.append(ident).append("Registrar:" + getRegistrar() + "\n");
		strb.append(ident).append("Reintentar:" + getReintentar() + "\n");
		strb.append(ident).append("Formularios: \n");
		printDocumentos(ident, strb, getFormularios());
		strb.append(ident).append("Anexos: \n");
		printDocumentos(ident, strb, getAnexos());
		strb.append(ident).append("Pagos: \n");
		printDocumentos(ident, strb, getPagos());
		return strb.toString();
	}

	private void printDocumentos(final String ident, final StringBuffer strb, List<DocumentoRegistro> docsr) {
		for (final DocumentoRegistro dr : docsr) {
			strb.append(ident).append("  - " + dr.getId() + "-" + dr.getInstancia() + " - Firmar:" + dr.getFirmar()
					+ " - Firmado:" + dr.getFirmado() + "\n");
		}
	}

}

package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.api.model.flujo.types.TypeResultadoRegistro;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;

/**
 *
 * Datos de un documento de un paso en persistencia.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DocumentoPasoPersistencia implements Serializable {
	/**
	 * Id documento.
	 */
	private String id;
	/**
	 * Instancia documento (1 para todos excepto para los anexos multiinstancia).
	 */
	private int instancia;
	/**
	 * Tipo documento (formulario, anexo, pago, justificante y variable flujo).
	 */
	private TypeDocumentoPersistencia tipo;
	/**
	 * Estado documento (vacío, rellenado ok, rellenado ko).
	 */
	private TypeEstadoDocumento estado;

	/**
	 * Fichero asociado al documento.
	 */
	private ReferenciaFichero fichero;

	/**
	 * Para formularios indica la vista pdf.
	 */
	private ReferenciaFichero formularioPdf;
	/**
	 * Nombre fichero asociado al anexo.
	 */
	private String anexoNombreFichero;
	/**
	 * Para anexos genéricos indica una descripción personalizada.
	 */
	private String anexoDescripcionInstancia;
	/**
	 * Para anexos indica si se ha anexado firmado.
	 */
	private TypeSiNo anexoAnexadoFirmado;
	/**
	 * Para pagos indica el justificante de pago en pdf.
	 */
	private ReferenciaFichero pagoJustificantePdf;
	/**
	 * Para pagos indica el nif del sujeto pasivo.
	 */
	private String pagoNifSujetoPasivo;
	/**
	 * Para pagos indica el identificador del pago.
	 */
	private String pagoIdentificador;
	/**
	 * Para pagos incorrectos indica el estado incorrecto.
	 */
	private TypeEstadoPagoIncorrecto pagoEstadoIncorrecto;
	/**
	 * Para pagos incorrectos indica el codigo de error de pasarela de pagos.
	 */
	private String pagoErrorPasarela;
	/**
	 * Para pagos incorrectos indica el mensaje de error de pasarela de pagos.
	 */
	private String pagoMensajeErrorPasarela;
	/**
	 * Para registro, indica el resultado del registro.
	 */
	private TypeResultadoRegistro registroResultado;
	/**
	 * Para registro, indica es un preregistro.
	 */
	private TypeSiNo registroPreregistro;
	/**
	 * Para registro, indica numero registro.
	 */
	private String registroNumeroRegistro;
	/**
	 * Para registro, indica fecha registro.
	 */
	private Date registroFechaRegistro;

	/**
	 * Para registro, indica id sesión registro.
	 */
	private String registroIdSesion;

	/** Para registro, indica registro nif interesado. */
	private String registroNifPresentador;

	/** Para registro, indica nombre y apellidos interesado. */
	private String registroNombrePresentador;

	/**
	 * Lista de firmas del documento.
	 */
	private final Map<Long, List<FirmaDocumentoPersistencia>> firmas = new HashMap<>();

	/**
	 * Crea clase vacía (uso en bucles).
	 *
	 * @return DocumentoPasoPersistencia
	 */
	public static DocumentoPasoPersistencia createDocumentoPersistencia() {
		final DocumentoPasoPersistencia doc = new DocumentoPasoPersistencia();
		return doc;
	}

	/**
	 * Método de acceso a id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método para establecer id.
	 *
	 * @param id
	 *               id a establecer
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Método de acceso a instancia.
	 *
	 * @return instancia
	 */
	public int getInstancia() {
		return instancia;
	}

	/**
	 * Método para establecer instancia.
	 *
	 * @param instancia
	 *                      instancia a establecer
	 */
	public void setInstancia(final int instancia) {
		this.instancia = instancia;
	}

	/**
	 * Método de acceso a tipo.
	 *
	 * @return tipo
	 */
	public TypeDocumentoPersistencia getTipo() {
		return tipo;
	}

	/**
	 * Método para establecer tipo.
	 *
	 * @param tipo
	 *                 tipo a establecer
	 */
	public void setTipo(final TypeDocumentoPersistencia tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método de acceso a estado.
	 *
	 * @return estado
	 */
	public TypeEstadoDocumento getEstado() {
		return estado;
	}

	/**
	 * Método para establecer estado.
	 *
	 * @param estado
	 *                   estado a establecer
	 */
	public void setEstado(final TypeEstadoDocumento estado) {
		this.estado = estado;
	}

	/**
	 * Método de acceso a fichero.
	 *
	 * @return fichero
	 */
	public ReferenciaFichero getFichero() {
		return fichero;
	}

	/**
	 * Método para establecer fichero.
	 *
	 * @param fichero
	 *                    fichero a establecer
	 */
	public void setFichero(final ReferenciaFichero fichero) {
		this.fichero = fichero;
	}

	/**
	 * Método de acceso a formularioPdf.
	 *
	 * @return formularioPdf
	 */
	public ReferenciaFichero getFormularioPdf() {
		return formularioPdf;
	}

	/**
	 * Método para establecer formularioPdf.
	 *
	 * @param formularioPdf
	 *                          formularioPdf a establecer
	 */
	public void setFormularioPdf(final ReferenciaFichero formularioPdf) {
		this.formularioPdf = formularioPdf;
	}

	/**
	 * Método de acceso a anexoNombreFichero.
	 *
	 * @return anexoNombreFichero
	 */
	public String getAnexoNombreFichero() {
		return anexoNombreFichero;
	}

	/**
	 * Método para establecer anexoNombreFichero.
	 *
	 * @param anexoNombreFichero
	 *                               anexoNombreFichero a establecer
	 */
	public void setAnexoNombreFichero(final String anexoNombreFichero) {
		this.anexoNombreFichero = anexoNombreFichero;
	}

	/**
	 * Método de acceso a anexoDescripcionInstancia.
	 *
	 * @return anexoDescripcionInstancia
	 */
	public String getAnexoDescripcionInstancia() {
		return anexoDescripcionInstancia;
	}

	/**
	 * Método para establecer anexoDescripcionInstancia.
	 *
	 * @param anexoDescripcionInstancia
	 *                                      anexoDescripcionInstancia a establecer
	 */
	public void setAnexoDescripcionInstancia(final String anexoDescripcionInstancia) {
		this.anexoDescripcionInstancia = anexoDescripcionInstancia;
	}

	/**
	 * Método de acceso a pagoJustificantePdf.
	 *
	 * @return pagoJustificantePdf
	 */
	public ReferenciaFichero getPagoJustificantePdf() {
		return pagoJustificantePdf;
	}

	/**
	 * Método para establecer pagoJustificantePdf.
	 *
	 * @param pagoJustificantePdf
	 *                                pagoJustificantePdf a establecer
	 */
	public void setPagoJustificantePdf(final ReferenciaFichero pagoJustificantePdf) {
		this.pagoJustificantePdf = pagoJustificantePdf;
	}

	/**
	 * Método de acceso a pagoNifSujetoPasivo.
	 *
	 * @return pagoNifSujetoPasivo
	 */
	public String getPagoNifSujetoPasivo() {
		return pagoNifSujetoPasivo;
	}

	/**
	 * Método para establecer pagoNifSujetoPasivo.
	 *
	 * @param pagoNifSujetoPasivo
	 *                                pagoNifSujetoPasivo a establecer
	 */
	public void setPagoNifSujetoPasivo(final String pagoNifSujetoPasivo) {
		this.pagoNifSujetoPasivo = pagoNifSujetoPasivo;
	}

	/**
	 * Método de acceso a pagoNumeroAutoliquidacion.
	 *
	 * @return pagoNumeroAutoliquidacion
	 */
	public String getPagoIdentificador() {
		return pagoIdentificador;
	}

	/**
	 * Método para establecer pagoNumeroAutoliquidacion.
	 *
	 * @param pagoNumeroAutoliquidacion
	 *                                      pagoNumeroAutoliquidacion a establecer
	 */
	public void setPagoIdentificador(final String pagoNumeroAutoliquidacion) {
		this.pagoIdentificador = pagoNumeroAutoliquidacion;
	}

	/**
	 * Método de acceso a pagoEstadoIncorrecto.
	 *
	 * @return pagoEstadoIncorrecto
	 */
	public TypeEstadoPagoIncorrecto getPagoEstadoIncorrecto() {
		return pagoEstadoIncorrecto;
	}

	/**
	 * Método para establecer pagoEstadoIncorrecto.
	 *
	 * @param pagoEstadoIncorrecto
	 *                                 pagoEstadoIncorrecto a establecer
	 */
	public void setPagoEstadoIncorrecto(final TypeEstadoPagoIncorrecto pagoEstadoIncorrecto) {
		this.pagoEstadoIncorrecto = pagoEstadoIncorrecto;
	}

	/**
	 * Método de acceso a pagoErrorPasarela.
	 *
	 * @return pagoErrorPasarela
	 */
	public String getPagoErrorPasarela() {
		return pagoErrorPasarela;
	}

	/**
	 * Método para establecer pagoErrorPasarela.
	 *
	 * @param pagoErrorPasarela
	 *                              pagoErrorPasarela a establecer
	 */
	public void setPagoErrorPasarela(final String pagoErrorPasarela) {
		this.pagoErrorPasarela = pagoErrorPasarela;
	}

	/**
	 * Método de acceso a pagoMensajeErrorPasarela.
	 *
	 * @return pagoMensajeErrorPasarela
	 */
	public String getPagoMensajeErrorPasarela() {
		return pagoMensajeErrorPasarela;
	}

	/**
	 * Método para establecer pagoMensajeErrorPasarela.
	 *
	 * @param pagoMensajeErrorPasarela
	 *                                     pagoMensajeErrorPasarela a establecer
	 */
	public void setPagoMensajeErrorPasarela(final String pagoMensajeErrorPasarela) {
		this.pagoMensajeErrorPasarela = pagoMensajeErrorPasarela;
	}

	/**
	 * Método de acceso a registroPreregistro.
	 *
	 * @return registroPreregistro
	 */
	public TypeSiNo getRegistroPreregistro() {
		return registroPreregistro;
	}

	/**
	 * Método para establecer registroPreregistro.
	 *
	 * @param registroPreregistro
	 *                                registroPreregistro a establecer
	 */
	public void setRegistroPreregistro(final TypeSiNo registroPreregistro) {
		this.registroPreregistro = registroPreregistro;
	}

	/**
	 * Método de acceso a registroNumeroRegistro.
	 *
	 * @return registroNumeroRegistro
	 */
	public String getRegistroNumeroRegistro() {
		return registroNumeroRegistro;
	}

	/**
	 * Método para establecer registroNumeroRegistro.
	 *
	 * @param registroNumeroRegistro
	 *                                   registroNumeroRegistro a establecer
	 */
	public void setRegistroNumeroRegistro(final String registroNumeroRegistro) {
		this.registroNumeroRegistro = registroNumeroRegistro;
	}

	/**
	 * Método de acceso a registroFechaRegistro.
	 *
	 * @return registroFechaRegistro
	 */
	public Date getRegistroFechaRegistro() {
		return registroFechaRegistro;
	}

	/**
	 * Método para establecer registroFechaRegistro.
	 *
	 * @param registroFechaRegistro
	 *                                  registroFechaRegistro a establecer
	 */
	public void setRegistroFechaRegistro(final Date registroFechaRegistro) {
		this.registroFechaRegistro = registroFechaRegistro;
	}

	/**
	 * Obtiene ficheros de persistencia para el formulario: xml y pdf.
	 *
	 * @param pXml
	 *                    Indica si se obtiene el fichero correspondiente al xml
	 * @param pPdf
	 *                    Indica si se obtiene el fichero correspondiente al pdf
	 * @param pFirmas
	 *                    Indica si se obtienen los ficheros de las firmas (de todos
	 *                    los ficheros)
	 * @return Lista de referencias de fichero
	 */
	public List<ReferenciaFichero> obtenerReferenciasFicherosFormulario(final boolean pXml, final boolean pPdf,
			final boolean pFirmas) {
		final List<ReferenciaFichero> res = new ArrayList<>();
		if (pXml && this.getFichero() != null) {
			res.add(this.getFichero());
		}
		if (pPdf && this.getFormularioPdf() != null) {
			res.add(this.getFormularioPdf());
		}
		// Recuperamos todas las firmas asociadas a los ficheros
		if (pFirmas) {
			if (this.getFichero() != null) {
				for (final FirmaDocumentoPersistencia firma : this.obtenerFirmasFichero(this.getFichero().getId())) {
					if (firma.getFirma() != null) {
						res.add(firma.getFirma());
					}
				}
			}
			if (this.getFormularioPdf() != null) {
				for (final FirmaDocumentoPersistencia firma : this
						.obtenerFirmasFichero(this.getFormularioPdf().getId())) {
					if (firma.getFirma() != null) {
						res.add(firma.getFirma());
					}
				}
			}

		}
		return res;
	}

	/**
	 * Obtiene ficheros de persistencia para el anexo: fichero y firmas.
	 *
	 * @param pFichero
	 *                     Indica si se obtiene el fichero correspondiente al anexo
	 * @param pFirmas
	 *                     Indica si se obtienen los ficheros de las firmas (de
	 *                     todos los ficheros)
	 * @return Lista de referencias de fichero
	 */
	public List<ReferenciaFichero> obtenerReferenciasFicherosAnexo(final boolean pFichero, final boolean pFirmas) {

		final List<ReferenciaFichero> res = new ArrayList<>();

		if (pFichero && this.getFichero() != null) {
			res.add(this.getFichero());
		}

		if (pFirmas && this.getFichero() != null) {
			for (final FirmaDocumentoPersistencia firma : this.obtenerFirmasFichero(this.getFichero().getId())) {
				if (firma.getFirma() != null) {
					res.add(firma.getFirma());
				}
			}
		}

		return res;
	}

	/**
	 * Añade firma de fichero.
	 *
	 * @param fdp
	 *                Firma
	 */
	public void addFirma(final FirmaDocumentoPersistencia fdp) {
		if (this.firmas.get(fdp.getFichero().getId()) == null) {
			this.firmas.put(fdp.getFichero().getId(), new ArrayList<FirmaDocumentoPersistencia>());
		}
		this.firmas.get(fdp.getFichero().getId()).add(fdp);
	}

	/**
	 * Elimina firmas del fichero.
	 *
	 * @param idFichero
	 *                      Id fichero
	 */
	public void removeFirmas(final Long idFichero) {
		if (idFichero != null && this.firmas.containsKey(idFichero)) {
			this.firmas.remove(idFichero);
		}
	}

	/**
	 * Elimina firmas de todos los ficheros.
	 *
	 */
	public void removeFirmasFicheros() {
		for (final Long idFichero : this.firmas.keySet()) {
			removeFirmas(idFichero);
		}
	}

	/**
	 * Elimina firma de fichero.
	 *
	 * @param fdp
	 *                Firma
	 */
	public void removeFirma(final FirmaDocumentoPersistencia fdp) {
		if (this.firmas.get(fdp.getFichero().getId()) != null) {
			// Borramos firma
			this.firmas.get(fdp.getFichero().getId()).remove(fdp);
			// Si el fichero no contiene firmas eliminamos elemento de la map
			if (this.firmas.get(fdp.getFichero().getId()).size() == 0) {
				this.firmas.remove(fdp.getFichero().getId());
			}
		}
	}

	/**
	 * Obtiene la lista de las firmas para todos los ficheros del documento.
	 *
	 * @return lista de las firmas para todos los ficheros del documento.
	 */
	public List<FirmaDocumentoPersistencia> obtenerFirmasFicheros() {
		final List<FirmaDocumentoPersistencia> res = new ArrayList<>();
		for (final Long idFichero : this.firmas.keySet()) {
			res.addAll(obtenerFirmasFichero(idFichero));
		}
		return res;
	}

	/**
	 * Método de acceso a firma de un fichero para un firmante.
	 *
	 * @param codigoFichero
	 *                          Parámetro codigo fichero
	 * @param nif
	 *                          Parámetro nif firmante
	 * @return firma (nulo si no esta firmado)
	 */
	public FirmaDocumentoPersistencia obtenerFirmaFichero(final Long codigoFichero, final String nif) {
		FirmaDocumentoPersistencia res = null;
		final List<FirmaDocumentoPersistencia> firmasFichero = obtenerFirmasFichero(codigoFichero);
		for (final FirmaDocumentoPersistencia fdp : firmasFichero) {
			if (nif.equals(fdp.getNif())) {
				res = fdp;
				break;
			}
		}
		return res;
	}

	/**
	 * Método de acceso a firmas de un fichero.
	 *
	 * @param codigoFichero
	 *                          Parámetro codigo fichero
	 * @return firmas
	 */
	public List<FirmaDocumentoPersistencia> obtenerFirmasFichero(final Long codigoFichero) {
		final List<FirmaDocumentoPersistencia> res = new ArrayList<>();
		final List<FirmaDocumentoPersistencia> lista = this.firmas.get(codigoFichero);
		if (lista != null) {
			for (final FirmaDocumentoPersistencia firma : lista) {
				res.add(firma);
			}
		}
		return res;
	}

	/**
	 * Obtiene referencias ficheros pago.
	 *
	 * @return ficheros pago
	 */
	public List<ReferenciaFichero> obtenerReferenciasFicherosPago() {
		final List<ReferenciaFichero> res = new ArrayList<>();
		if (this.getFichero() != null) {
			res.add(this.getFichero());
		}
		if (this.getPagoJustificantePdf() != null) {
			res.add(this.getPagoJustificantePdf());
		}
		return res;
	}

	/**
	 * Método de acceso a registroResultado.
	 *
	 * @return registroResultado
	 */
	public TypeResultadoRegistro getRegistroResultado() {
		return registroResultado;
	}

	/**
	 * Método para establecer registroResultado.
	 *
	 * @param registroResultado
	 *                              registroResultado a establecer
	 */
	public void setRegistroResultado(final TypeResultadoRegistro registroResultado) {
		this.registroResultado = registroResultado;
	}

	/**
	 * Método de acceso a firmas.
	 *
	 * @return firmas
	 */
	public Map<Long, List<FirmaDocumentoPersistencia>> getFirmas() {
		return firmas;
	}

	/**
	 * Método de acceso a registroIdSesion.
	 *
	 * @return registroIdSesion
	 */
	public String getRegistroIdSesion() {
		return registroIdSesion;
	}

	/**
	 * Método para establecer registroIdSesion.
	 *
	 * @param registroIdSesion
	 *                             registroIdSesion a establecer
	 */
	public void setRegistroIdSesion(final String registroIdSesion) {
		this.registroIdSesion = registroIdSesion;
	}

	/**
	 * Método de acceso a registroNifPresentador.
	 *
	 * @return registroNifPresentador
	 */
	public String getRegistroNifPresentador() {
		return registroNifPresentador;
	}

	/**
	 * Método para establecer registroNifPresentador.
	 *
	 * @param registroNifPresentador
	 *                                   registroNifPresentador a establecer
	 */
	public void setRegistroNifPresentador(final String registroNifPresentador) {
		this.registroNifPresentador = registroNifPresentador;
	}

	/**
	 * Método de acceso a registroNombrePresentador.
	 *
	 * @return registroNombrePresentador
	 */
	public String getRegistroNombrePresentador() {
		return registroNombrePresentador;
	}

	/**
	 * Método para establecer registroNombrePresentador.
	 *
	 * @param registroNombrePresentador
	 *                                      registroNombrePresentador a establecer
	 */
	public void setRegistroNombrePresentador(final String registroNombrePresentador) {
		this.registroNombrePresentador = registroNombrePresentador;
	}

	/**
	 * Método de acceso a anexoAnexadoFirmado.
	 * 
	 * @return anexoAnexadoFirmado
	 */
	public TypeSiNo getAnexoAnexadoFirmado() {
		return anexoAnexadoFirmado;
	}

	/**
	 * Método para establecer anexoAnexadoFirmado.
	 * 
	 * @param anexoAnexadoFirmado
	 *                                anexoAnexadoFirmado a establecer
	 */
	public void setAnexoAnexadoFirmado(final TypeSiNo anexoAnexadoFirmado) {
		this.anexoAnexadoFirmado = anexoAnexadoFirmado;
	}

}

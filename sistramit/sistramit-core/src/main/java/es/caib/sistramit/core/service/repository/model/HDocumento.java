package es.caib.sistramit.core.service.repository.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.RepositoryException;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoDocumento;
import es.caib.sistramit.core.api.model.flujo.types.TypeEstadoPagoIncorrecto;
import es.caib.sistramit.core.service.model.flujo.DocumentoPasoPersistencia;
import es.caib.sistramit.core.service.model.flujo.FirmaDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.ReferenciaFichero;
import es.caib.sistramit.core.service.model.flujo.types.TypeDocumentoPersistencia;
import es.caib.sistramit.core.service.model.flujo.types.TypeFirmaDigital;
import es.caib.sistramit.core.service.model.flujo.types.TypeResultadoRegistro;

/**
 * Mapea tabla STT_DOCPTR.
 */
@Entity
@Table(name = "STT_DOCPTR")
@SuppressWarnings("serial")
public class HDocumento implements IModelApi {

	/** Atributo codigo. */
	@Id
	@SequenceGenerator(name = "STT_DOCPTR_SEQ", sequenceName = "STT_DOCPTR_SEQ", allocationSize = ConstantesNumero.N1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STT_DOCPTR_SEQ")
	@Column(name = "DTP_CODIGO", unique = true, nullable = false, precision = ConstantesNumero.N10, scale = 0)
	private Long codigo;

	/** Atributo tramite persistencia. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DTP_CODPTR")
	private HPaso paso;

	/** Atributo id. */
	@Column(name = "DTP_DOCIDE")
	private String id;

	/** Atributo intancia. */
	@Column(name = "DTP_DOCINS")
	private Timestamp instanciaTimeStamp;

	/** Atributo tipo. */
	@Column(name = "DTP_DOCTIP")
	private String tipo;

	/** Atributo estado. */
	@Column(name = "DTP_ESTADO")
	private String estado;

	/** Atributo fichero. */
	@Column(name = "DTP_FICHERO")
	private Long fichero;

	/** Atributo fichero clave. */
	@Column(name = "DTP_FICCLA")
	private String ficheroClave;

	/** Atributo formulario pdf. */
	@Column(name = "DTP_FORPDF")
	private Long formularioPdf;

	/** Atributo formulario pdf clave. */
	@Column(name = "DTP_FORPDC")
	private String formularioPdfClave;

	/** Atributo anexo nombre fichero. */
	@Column(name = "DTP_ANENFI")
	private String anexoNombreFichero;

	/** Atributo anexo descripcion instancia. */
	@Column(name = "DTP_ANEDES")
	private String anexoDescripcionInstancia;

	/** Atributo pago justificante pdf. */
	@Column(name = "DTP_PAGJUS")
	private Long pagoJustificantePdf;

	/** Atributo pago justificante pdf clave. */
	@Column(name = "DTP_PAGJUC")
	private String pagoJustificantePdfClave;

	/** Atributo pago nif sujeto pasivo. */
	@Column(name = "DTP_PAGNIF")
	private String pagoNifSujetoPasivo;

	/** Atributo pago que indica identificador del pago. */
	@Column(name = "DTP_PAGIDE")
	private String pagoIdentificador;

	/** Atributo pago estado incorrecto. */
	@Column(name = "DTP_PAGEST")
	private String pagoEstadoIncorrecto;

	/** Atributo pago error pasarela. */
	@Column(name = "DTP_PAGERR")
	private String pagoErrorPasarela;

	/** Atributo pago mensaje error pasarela. */
	@Column(name = "DTP_PAGERM")
	private String pagoMensajeErrorPasarela;

	/** Atributo registro resultado. */
	@Column(name = "DTP_REGRES")
	private String registroResultado;

	/** Atributo registro preregistro. */
	@Column(name = "DTP_REGPRE")
	private String registroPreregistro;

	/** Atributo registro numero registro. */
	@Column(name = "DTP_REGNUM")
	private String registroNumeroRegistro;

	/** Atributo registro fecha registro. */
	@Column(name = "DTP_REGFEC")
	private Date registroFechaRegistro;

	/** Atributo firmas. */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "documentoPersistente", orphanRemoval = true)
	@OrderBy("fecha ASC")
	private List<HFirma> firmas = new ArrayList<>(0);

	/**
	 * Obtiene el atributo codigo de HDocumento.
	 *
	 * @return el atributo codigo
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Asigna el atributo codigo de HDocumento.
	 *
	 * @param pCodigo
	 *            el nuevo valor para codigo
	 */
	public void setCodigo(final Long pCodigo) {
		codigo = pCodigo;
	}

	/**
	 * Método para obtener el campo paso.
	 *
	 * @return the paso
	 */
	public HPaso getPaso() {
		return paso;
	}

	/**
	 * Método para settear el campo paso.
	 *
	 * @param pPaso
	 *            el campo paso a settear
	 */
	public void setPaso(final HPaso pPaso) {
		paso = pPaso;
	}

	/**
	 * Método para obtener el campo tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Método para settear el campo tipo.
	 *
	 * @param pTipo
	 *            el campo tipo a settear
	 */
	public void setTipo(final String pTipo) {
		tipo = pTipo;
	}

	/**
	 * Método para obtener el campo estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Método para settear el campo estado.
	 *
	 * @param pEstado
	 *            el campo estado a settear
	 */
	public void setEstado(final String pEstado) {
		estado = pEstado;
	}

	/**
	 * Método para obtener el campo id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Método para settear el campo id.
	 *
	 * @param pId
	 *            el campo id a settear
	 */
	public void setId(final String pId) {
		id = pId;
	}

	/**
	 * Método para obtener el campo instanciaTimeStamp.
	 *
	 * @return the instanciaTimeStamp
	 */
	public Timestamp getInstanciaTimeStamp() {
		return instanciaTimeStamp;
	}

	/**
	 * Método para settear el campo instanciaTimeStamp.
	 *
	 * @param pInstanciaTimeStamp
	 *            el campo instanciaTimeStamp a settear
	 */
	public void setInstanciaTimeStamp(final Timestamp pInstanciaTimeStamp) {
		instanciaTimeStamp = pInstanciaTimeStamp;
	}

	/**
	 * Método para obtener el campo anexoDescripcionInstancia.
	 *
	 * @return the anexoDescripcionInstancia
	 */
	public String getAnexoDescripcionInstancia() {
		return anexoDescripcionInstancia;
	}

	/**
	 * Método para settear el campo anexoDescripcionInstancia.
	 *
	 * @param pAnexoDescripcionInstancia
	 *            el campo anexoDescripcionInstancia a settear
	 */
	public void setAnexoDescripcionInstancia(final String pAnexoDescripcionInstancia) {
		anexoDescripcionInstancia = pAnexoDescripcionInstancia;
	}

	/**
	 * Método para obtener el campo fichero.
	 *
	 * @return the fichero
	 */
	public Long getFichero() {
		return fichero;
	}

	/**
	 * Método para settear el campo fichero.
	 *
	 * @param pFichero
	 *            el campo fichero a settear
	 */
	public void setFichero(final Long pFichero) {
		fichero = pFichero;
	}

	/**
	 * Método para obtener el campo formularioPdf.
	 *
	 * @return the formularioPdf
	 */
	public Long getFormularioPdf() {
		return formularioPdf;
	}

	/**
	 * Método para settear el campo formularioPdf.
	 *
	 * @param pFormularioPdf
	 *            el campo formularioPdf a settear
	 */
	public void setFormularioPdf(final Long pFormularioPdf) {
		formularioPdf = pFormularioPdf;
	}

	/**
	 * Método para obtener el campo pagoJustificantePdf.
	 *
	 * @return the pagoJustificantePdf
	 */
	public Long getPagoJustificantePdf() {
		return pagoJustificantePdf;
	}

	/**
	 * Método para settear el campo pagoJustificantePdf.
	 *
	 * @param pPagoJustificantePdf
	 *            el campo pagoJustificantePdf a settear
	 */
	public void setPagoJustificantePdf(final Long pPagoJustificantePdf) {
		pagoJustificantePdf = pPagoJustificantePdf;
	}

	/**
	 * Método para obtener el campo pagoErrorPasarela.
	 *
	 * @return the pagoErrorPasarela
	 */
	public String getPagoErrorPasarela() {
		return pagoErrorPasarela;
	}

	/**
	 * Método para settear el campo pagoErrorPasarela.
	 *
	 * @param pPagoErrorPasarela
	 *            el campo pagoErrorPasarela a settear
	 */
	public void setPagoErrorPasarela(final String pPagoErrorPasarela) {
		pagoErrorPasarela = pPagoErrorPasarela;
	}

	/**
	 * Añade firma a lista firmas.
	 *
	 * @param firma
	 *            Parámetro firma
	 */
	public void addFirma(final HFirma firma) {
		firma.setDocumentoPersistente(this);
		firmas.add(firma);
	}

	/**
	 * Elimina firma de lista firmas.
	 *
	 * @param firma
	 *            Parámetro firma
	 */
	public void removeFirma(final HFirma firma) {
		getFirmas().remove(firma);
	}

	/**
	 * Método para obtener el campo firmas.
	 *
	 * @return the firmas
	 */
	public List<HFirma> getFirmas() {
		return firmas;
	}

	/**
	 * Método para settear el campo firmas.
	 *
	 * @param pFirmas
	 *            el campo firmas a settear
	 */
	public void setFirmas(final List<HFirma> pFirmas) {
		firmas = pFirmas;
	}

	/**
	 * Método para obtener el campo anexoNombreFichero.
	 *
	 * @return the anexoNombreFichero
	 */
	public String getAnexoNombreFichero() {
		return anexoNombreFichero;
	}

	/**
	 * Método para settear el campo anexoNombreFichero.
	 *
	 * @param pAnexoNombreFichero
	 *            el campo anexoNombreFichero a settear
	 */
	public void setAnexoNombreFichero(final String pAnexoNombreFichero) {
		anexoNombreFichero = pAnexoNombreFichero;
	}

	/**
	 * Método para obtener el campo ficheroClave.
	 *
	 * @return the ficheroClave
	 */
	public String getFicheroClave() {
		return ficheroClave;
	}

	/**
	 * Método para settear el campo ficheroClave.
	 *
	 * @param pFicheroClave
	 *            el campo ficheroClave a settear
	 */
	public void setFicheroClave(final String pFicheroClave) {
		ficheroClave = pFicheroClave;
	}

	/**
	 * Método para obtener el campo formularioPdfClave.
	 *
	 * @return the formularioPdfClave
	 */
	public String getFormularioPdfClave() {
		return formularioPdfClave;
	}

	/**
	 * Método para settear el campo formularioPdfClave.
	 *
	 * @param pFormularioPdfClave
	 *            el campo formularioPdfClave a settear
	 */
	public void setFormularioPdfClave(final String pFormularioPdfClave) {
		formularioPdfClave = pFormularioPdfClave;
	}

	/**
	 * Método para obtener el campo pagoJustificantePdfClave.
	 *
	 * @return the pagoJustificantePdfClave
	 */
	public String getPagoJustificantePdfClave() {
		return pagoJustificantePdfClave;
	}

	/**
	 * Método para settear el campo pagoJustificantePdfClave.
	 *
	 * @param pPagoJustificantePdfClave
	 *            el campo pagoJustificantePdfClave a settear
	 */
	public void setPagoJustificantePdfClave(final String pPagoJustificantePdfClave) {
		pagoJustificantePdfClave = pPagoJustificantePdfClave;
	}

	/**
	 * Método para obtener el campo pagoMensajeErrorPasarela.
	 *
	 * @return the pagoMensajeErrorPasarela
	 */
	public String getPagoMensajeErrorPasarela() {
		return pagoMensajeErrorPasarela;
	}

	/**
	 * Método para settear el campo pagoMensajeErrorPasarela.
	 *
	 * @param pPagoMensajeErrorPasarela
	 *            el campo pagoMensajeErrorPasarela a settear
	 */
	public void setPagoMensajeErrorPasarela(final String pPagoMensajeErrorPasarela) {
		pagoMensajeErrorPasarela = pPagoMensajeErrorPasarela;
	}

	/**
	 * Método para obtener el campo pagoEstadoIncorrecto.
	 *
	 * @return the pagoEstadoIncorrecto
	 */
	public String getPagoEstadoIncorrecto() {
		return pagoEstadoIncorrecto;
	}

	/**
	 * Método para settear el campo pagoEstadoIncorrecto.
	 *
	 * @param pPagoEstadoIncorrecto
	 *            el campo pagoEstadoIncorrecto a settear
	 */
	public void setPagoEstadoIncorrecto(final String pPagoEstadoIncorrecto) {
		pagoEstadoIncorrecto = pPagoEstadoIncorrecto;
	}

	/**
	 * Método de acceso a registroPreregistro.
	 *
	 * @return registroPreregistro
	 */
	public String getRegistroPreregistro() {
		return registroPreregistro;
	}

	/**
	 * Método para establecer registroPreregistro.
	 *
	 * @param pRegistroPreregistro
	 *            registroPreregistro a establecer
	 */
	public void setRegistroPreregistro(final String pRegistroPreregistro) {
		registroPreregistro = pRegistroPreregistro;
	}

	/**
	 * Devuelve un nuevo objeto HDocumento.
	 *
	 * @return un nuevo objeto HDocumento
	 */
	public static HDocumento getNewHDocumento() {
		return new HDocumento();
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
	 * @param pRegistroNumeroRegistro
	 *            registroNumeroRegistro a establecer
	 */
	public void setRegistroNumeroRegistro(final String pRegistroNumeroRegistro) {
		registroNumeroRegistro = pRegistroNumeroRegistro;
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
	 * @param pRegistroFechaRegistro
	 *            registroFechaRegistro a establecer
	 */
	public void setRegistroFechaRegistro(final Date pRegistroFechaRegistro) {
		registroFechaRegistro = pRegistroFechaRegistro;
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
	 * @param pPagoNifSujetoPasivo
	 *            pagoNifSujetoPasivo a establecer
	 */
	public void setPagoNifSujetoPasivo(final String pPagoNifSujetoPasivo) {
		pagoNifSujetoPasivo = pPagoNifSujetoPasivo;
	}

	/**
	 * Método de acceso a pagoNumAutoliquidacion.
	 *
	 * @return pagoNumAutoliquidacion
	 */
	public String getPagoIdentificador() {
		return pagoIdentificador;
	}

	/**
	 * Método para establecer pagoNumAutoliquidacion.
	 *
	 * @param pPagoNumAutoliquidacion
	 *            pagoNumAutoliquidacion a establecer
	 */
	public void setPagoIdentificador(final String pPagoNumAutoliquidacion) {
		pagoIdentificador = pPagoNumAutoliquidacion;
	}

	public static DocumentoPasoPersistencia toModel(final HDocumento hDocumento, final int pInstancia) {
		final DocumentoPasoPersistencia documento = new DocumentoPasoPersistencia();
		documento.setAnexoDescripcionInstancia(hDocumento.getAnexoDescripcionInstancia());
		documento.setEstado(TypeEstadoDocumento.fromString(hDocumento.getEstado()));
		documento.setId(hDocumento.getId());
		documento.setInstancia(pInstancia);
		documento.setPagoEstadoIncorrecto(TypeEstadoPagoIncorrecto.fromString(hDocumento.getPagoEstadoIncorrecto()));

		documento.setPagoErrorPasarela(hDocumento.getPagoErrorPasarela());
		documento.setPagoMensajeErrorPasarela(hDocumento.getPagoMensajeErrorPasarela());
		documento.setPagoNifSujetoPasivo(hDocumento.getPagoNifSujetoPasivo());
		documento.setPagoIdentificador(hDocumento.getPagoIdentificador());
		documento.setTipo(TypeDocumentoPersistencia.fromString(hDocumento.getTipo()));

		documento.setAnexoNombreFichero(hDocumento.getAnexoNombreFichero());

		establecerFirmasDocumentoPasoPersistencia(hDocumento, documento);

		if (hDocumento.getFichero() != null) {
			documento.setFichero(new ReferenciaFichero(hDocumento.getFichero(), hDocumento.getFicheroClave()));
		}
		if (hDocumento.getFormularioPdf() != null) {
			documento.setFormularioPdf(
					new ReferenciaFichero(hDocumento.getFormularioPdf(), hDocumento.getFormularioPdfClave()));
		}
		if (hDocumento.getPagoJustificantePdf() != null) {
			documento.setPagoJustificantePdf(new ReferenciaFichero(hDocumento.getPagoJustificantePdf(),
					hDocumento.getPagoJustificantePdfClave()));
		}

		documento.setRegistroResultado(TypeResultadoRegistro.fromString(hDocumento.getRegistroResultado()));
		documento.setRegistroPreregistro(TypeSiNo.fromString(hDocumento.getRegistroPreregistro()));
		documento.setRegistroNumeroRegistro(hDocumento.getRegistroNumeroRegistro());
		documento.setRegistroFechaRegistro(hDocumento.getRegistroFechaRegistro());

		return documento;

	}

	public static HDocumento fromModel(final DocumentoPasoPersistencia documento) {
		final HDocumento d = new HDocumento();
		establecerCamposDocumento(documento, d);
		return d;
	}

	public static void fromModel(HDocumento d, final DocumentoPasoPersistencia documento) {
		establecerCamposDocumento(documento, d);
	}

	private static void establecerCamposDocumento(final DocumentoPasoPersistencia documento, final HDocumento d) {
		d.setAnexoDescripcionInstancia(documento.getAnexoDescripcionInstancia());
		d.setEstado(documento.getEstado().toString());
		d.setId(documento.getId());
		d.setAnexoNombreFichero(documento.getAnexoNombreFichero());
		if (documento.getPagoEstadoIncorrecto() != null) {
			d.setPagoEstadoIncorrecto(documento.getPagoEstadoIncorrecto().toString());
		} else {
			d.setPagoEstadoIncorrecto(null);
		}
		d.setPagoErrorPasarela(documento.getPagoErrorPasarela());
		d.setPagoMensajeErrorPasarela(documento.getPagoMensajeErrorPasarela());
		d.setTipo(documento.getTipo().toString());
		if (documento.getFichero() != null) {
			d.setFichero(documento.getFichero().getId());
			d.setFicheroClave(documento.getFichero().getClave());
		} else {
			d.setFichero(null);
			d.setFicheroClave("");
		}
		if (documento.getFormularioPdf() != null) {
			d.setFormularioPdf(documento.getFormularioPdf().getId());
			d.setFormularioPdfClave(documento.getFormularioPdf().getClave());
		} else {
			d.setFormularioPdf(null);
			d.setFormularioPdfClave(null);
		}
		if (documento.getPagoJustificantePdf() != null) {
			d.setPagoJustificantePdf(documento.getPagoJustificantePdf().getId());
			d.setPagoJustificantePdfClave(documento.getPagoJustificantePdf().getClave());
		} else {
			d.setPagoJustificantePdf(null);
			d.setPagoJustificantePdfClave(null);
		}
		d.setPagoNifSujetoPasivo(documento.getPagoNifSujetoPasivo());
		d.setPagoIdentificador(documento.getPagoIdentificador());
		if (documento.getRegistroResultado() != null) {
			d.setRegistroResultado(documento.getRegistroResultado().toString());
		} else {
			d.setRegistroResultado(null);
		}
		if (documento.getRegistroPreregistro() != null) {
			d.setRegistroPreregistro(documento.getRegistroPreregistro().toString());
		} else {
			d.setRegistroPreregistro(null);
		}
		d.setRegistroNumeroRegistro(documento.getRegistroNumeroRegistro());
		d.setRegistroFechaRegistro(documento.getRegistroFechaRegistro());
	}

	/**
	 * Método para Establecer firmas documento paso persistencia de la clase
	 * HModelFactory.
	 *
	 * @param hDocumento
	 *            Parámetro h documento
	 * @param documento
	 *            Parámetro documento
	 */
	public static void establecerFirmasDocumentoPasoPersistencia(final HDocumento hDocumento,
			final DocumentoPasoPersistencia documento) {
		for (final HFirma firma : hDocumento.getFirmas()) {
			final FirmaDocumentoPersistencia f = FirmaDocumentoPersistencia.createFirmaDocumentoPersistencia();
			f.setNif(firma.getNif());
			f.setNombre(firma.getNombre());

			if (firma.getFirma() != null) {
				final ReferenciaFichero ref = ReferenciaFichero.createNewReferenciaFichero();
				ref.setId(firma.getFirma());
				ref.setClave(firma.getFirmaClave());
				f.setFirma(ref);
				if (firma.getTipoFirma() != null) {
					f.setTipoFirma(TypeFirmaDigital.fromString(firma.getTipoFirma()));
				}
			}
			f.setFecha(firma.getFecha());

			final ReferenciaFichero refFichero = ReferenciaFichero.createNewReferenciaFichero();
			refFichero.setId(firma.getFichero());
			final String clave = obtenerClaveFicheroFirma(hDocumento, firma);
			refFichero.setClave(clave);
			f.setFichero(refFichero);

			documento.addFirma(f);
		}
	}

	/**
	 * Método para Obtener clave fichero firma de la clase HModelFactory.
	 *
	 * @param hDocumento
	 *            Parámetro h documento
	 * @param firma
	 *            Parámetro firma
	 * @return el string
	 */
	private static String obtenerClaveFicheroFirma(final HDocumento hDocumento, final HFirma firma) {
		String clave = null;
		if ((hDocumento.getFichero() != null) && hDocumento.getFichero().equals(firma.getFichero())) {
			clave = hDocumento.getFicheroClave();
		} else if ((hDocumento.getFormularioPdf() != null)
				&& hDocumento.getFormularioPdf().equals(firma.getFichero())) {
			clave = hDocumento.getFormularioPdfClave();
		} else if ((hDocumento.getPagoJustificantePdf() != null)
				&& hDocumento.getPagoJustificantePdf().equals(firma.getFichero())) {
			clave = hDocumento.getPagoJustificantePdfClave();
		}
		if (clave == null) {
			throw new RepositoryException("No se ha encontrado clave acceso para fichero " + hDocumento.getFichero());
		}
		return clave;
	}

	/**
	 * Método de acceso a registroResultado.
	 *
	 * @return registroResultado
	 */
	public String getRegistroResultado() {
		return registroResultado;
	}

	/**
	 * Método para establecer registroResultado.
	 *
	 * @param registroResultado
	 *            registroResultado a establecer
	 */
	public void setRegistroResultado(String registroResultado) {
		this.registroResultado = registroResultado;
	}

}

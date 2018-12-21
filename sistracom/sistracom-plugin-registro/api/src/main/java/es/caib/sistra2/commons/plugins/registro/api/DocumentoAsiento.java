package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeDocumental;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeFirmaAsiento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeOrigenDocumento;
import es.caib.sistra2.commons.plugins.registro.api.types.TypeValidez;

/**
 * Documento anexo al asiento
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DocumentoAsiento implements Serializable {

	/** contenido del fichero */
	private byte[] contenidoFichero;

	/** nombre del documento de registro */
	private String nombreFichero;

	/** titulo del documento de registro */
	private String tituloDoc;

	/** tipo documental del documento */
	private String tipoDocumental;

	/** validez del documento */
	private TypeValidez validez;

	/** tipo de documento (formulari/adjunto a formulario/fichero tecnico) */
	private TypeDocumental tipoDocumento;

	/** fecha de captura del documento */
	private Date fechaCaptura;

	/** modo de firma (sin firma/attached/detached) */
	private TypeFirmaAsiento modoFirma;

	/** nombre del fichero de firma anexado para firma detached */
	private String nombreFirmaAnexada;

	/** contenido del fichero de firma para detached */
	private byte[] contenidoFirma;

	/** origen del documento (ciudadano/administracion) */
	private TypeOrigenDocumento origenDocumento;

	public byte[] getContenidoFichero() {
		return contenidoFichero;
	}

	public void setContenidoFichero(byte[] contenidoFichero) {
		this.contenidoFichero = contenidoFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getTituloDoc() {
		return tituloDoc;
	}

	public void setTituloDoc(String tituloDoc) {
		this.tituloDoc = tituloDoc;
	}

	public String getTipoDocumental() {
		return tipoDocumental;
	}

	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	public TypeValidez getValidez() {
		return validez;
	}

	public void setValidez(TypeValidez validez) {
		this.validez = validez;
	}

	public TypeDocumental getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TypeDocumental tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public TypeFirmaAsiento getModoFirma() {
		return modoFirma;
	}

	public void setModoFirma(TypeFirmaAsiento modoFirma) {
		this.modoFirma = modoFirma;
	}

	public String getNombreFirmaAnexada() {
		return nombreFirmaAnexada;
	}

	public void setNombreFirmaAnexada(String nombreFirmaAnexada) {
		this.nombreFirmaAnexada = nombreFirmaAnexada;
	}

	public byte[] getContenidoFirma() {
		return contenidoFirma;
	}

	public void setContenidoFirma(byte[] contenidoFirma) {
		this.contenidoFirma = contenidoFirma;
	}

	public TypeOrigenDocumento getOrigenDocumento() {
		return origenDocumento;
	}

	public void setOrigenDocumento(TypeOrigenDocumento origenDocumento) {
		this.origenDocumento = origenDocumento;
	}

}

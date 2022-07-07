package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos del asiento registral
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class AsientoRegistral implements Serializable {

	/** Datos origen del asiento */
	private DatosOrigen datosOrigen;

	/** Datos destino del asiento */
	private DatosDestino datosDestino;

	/** Datos asunto. */
	private DatosAsunto datosAsunto;

	/** Interesados. */
	private List<Interesado> interesados = new ArrayList();

	/** Documentos. */
	private List<DocumentoAsiento> documentosRegistro = new ArrayList();

	public DatosOrigen getDatosOrigen() {
		return datosOrigen;
	}

	public void setDatosOrigen(final DatosOrigen datosOrigen) {
		this.datosOrigen = datosOrigen;
	}

	public DatosDestino getDatosDestino() {
		return datosDestino;
	}

	public void setDatosDestino(final DatosDestino datosDestino) {
		this.datosDestino = datosDestino;
	}

	public List<Interesado> getInteresados() {
		return interesados;
	}

	public void setInteresados(final List<Interesado> interesados) {
		this.interesados = interesados;
	}

	public List<DocumentoAsiento> getDocumentosRegistro() {
		return documentosRegistro;
	}

	public void setDocumentosRegistro(final List<DocumentoAsiento> documentosRegistro) {
		this.documentosRegistro = documentosRegistro;
	}

	public DatosAsunto getDatosAsunto() {
		return datosAsunto;
	}

	public void setDatosAsunto(final DatosAsunto datosAsunto) {
		this.datosAsunto = datosAsunto;
	}

}

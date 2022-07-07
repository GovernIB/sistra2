package es.caib.sistra2.commons.plugins.registro.envio;

import java.util.ArrayList;
import java.util.List;

import es.caib.sistra2.commons.plugins.registro.api.DatosAsunto;
import es.caib.sistra2.commons.plugins.registro.api.DatosDestino;
import es.caib.sistra2.commons.plugins.registro.api.DatosOrigen;
import es.caib.sistra2.commons.plugins.registro.api.DocumentoAsiento;
import es.caib.sistra2.commons.plugins.registro.api.Interesado;

public class RAsientoRegistral {

	public RAsientoRegistral() {
		// Constructor vacio
	}

	public RAsientoRegistral(DatosOrigen datosOrigen, DatosDestino datosDestino, DatosAsunto datosAsunto,
			List<Interesado> interesados, List<DocumentoAsiento> documentosRegistro) {
		super();
		this.datosOrigen = datosOrigen;
		this.datosDestino = datosDestino;
		this.datosAsunto = datosAsunto;
		this.interesados = interesados;
		this.documentosRegistro = documentosRegistro;
	}

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
